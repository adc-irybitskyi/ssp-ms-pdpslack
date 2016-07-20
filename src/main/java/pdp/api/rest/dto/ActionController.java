package pdp.api.rest.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller("/action")
class ActionController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActionController.class);

	private Map<String, Boolean> approvedMap = new HashMap<>();

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "PDP Slack Action API";
	}

	//https://api.slack.com/apps/A1T3W5N3T/interactive-messages
	@RequestMapping(value = {"/pof"},  method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE )
	@ResponseBody
	String pofAction(@RequestBody final MultiValueMap<String, String > formVars) {
		String payload = formVars.getFirst("payload");
		LOGGER.info("pofAction payload:" + payload);
		//TODO: send message to PDP Chat with message 'Your Proof Of Funds were approved. Now you’re ready to make an offer.'
		return "Buyer's Proof Of Funds is approved. Now he's ready to make an offer.";
	}
}
