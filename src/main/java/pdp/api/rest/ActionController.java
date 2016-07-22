package pdp.api.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import pdp.api.rest.dto.ActionResponse;

@Controller
@RequestMapping("/action")
class ActionController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActionController.class);

	private Map<String, Boolean> approvedMap = new HashMap<>();

	@Autowired
	private ObjectMapper mapper;

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "PDP Slack Action API";
	}

	// https://api.slack.com/apps/A1T3W5N3T/interactive-messages
	@RequestMapping(value = { "/" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseBody
	String pofAction(@RequestBody final MultiValueMap<String, String> formVars) {
		String payload = formVars.getFirst("payload");
		if (payload == null) {
			LOGGER.error("!!!! pofAction payload is empty");
			return "payload is empty";
		}
		LOGGER.info("pofAction payload:" + payload);
		ActionResponse response;
		try {
			response = mapper.readValue(payload, ActionResponse.class);
		} catch (IOException e) {
			throw new RuntimeException("json formatter", e);
		}
		LOGGER.info("pofAction response:" + response);
		switch (response.getCallbackId()) {
		case "document-type":
			return documentType(response);
		case "approve-pof":
			return approvePOF(response);
		case "final-approve":
			return finalApprove(response);
		}
		return "Unknown callback_id: " + response.getCallbackId();
	}


	private Function<ActionResponse, Boolean> isPOF = x -> x.getActions().stream().filter(a -> "pof".equals(a.getValue())).findAny().isPresent();

	private String documentType(ActionResponse action) {
		if (isPOF.apply(action)) {
			return "Proof Of Funds document was uploaded.";
		} else {
			return "Other document was uploaded.";
		}
	}

	private String approvePOF(ActionResponse action) {
		if (isApproved.apply(action)) {
			return "Buyer's Proof Of Funds is approved.";
		} else {
			// TODO: send message to PDP Chat with message 'Your Proof Of Funds were declined. Please provide new Proof of Funds document(s).'
			return "Proof Of Funds is declined.";
		}
	}

	private String finalApprove(ActionResponse action) {
		if (isApproved.apply(action)) {
			// TODO: send message to PDP Chat with message 'Your Proof Of Funds were approved. Now you’re ready to make an offer.'
			return "Buyer is approved.";
		} else {
			// TODO: send message to PDP Chat with message 'Your Proof Of Funds were declined. Please provide new Proof of Funds document(s).'
			return "Buyer registration is declined";
		}

	}

	private Function<ActionResponse, Boolean> isApproved = x -> x.getActions().stream().filter(a -> "approve".equals(a.getValue())).findAny().isPresent();
}
