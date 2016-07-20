package pdp.api.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import pdp.api.rest.dto.ActionResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

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

	//https://api.slack.com/apps/A1T3W5N3T/interactive-messages
	@RequestMapping(value = {"/pof"},  method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE )
	@ResponseBody
	String pofAction(@RequestBody final MultiValueMap<String, String > formVars) {
		String payload = formVars.getFirst("payload");
		LOGGER.info("pofAction payload:" + payload);
		ActionResponse response;
		try {
			response = mapper.readValue(payload, ActionResponse.class);
		} catch (IOException e) {
			throw new RuntimeException("json formatter", e);
		}
		LOGGER.info("pofAction response:" + response);
		if (isApproved.apply(response)) {
			//TODO: send message to PDP Chat with message 'Your Proof Of Funds were approved. Now you’re ready to make an offer.'
			return "Buyer's Proof Of Funds is approved. Now he's ready to make an offer.";
		} else {
			//TODO: send message to PDP Chat with message 'Your Proof Of Funds were declined. Please provide new Proof of Funds document(s).'
			return "Proof Of Funds is declined. Buyer should provide new Proof of Funds documents.";
		}
	}

	private Function<ActionResponse, Boolean> isApproved = x -> x.getActions().stream().filter(a -> "approve".equals(a.getValue())).findAny().isPresent();

	public static void main(String[] args) throws Exception {
		String payload = "{\"actions\":[{\"name\":\"approve\",\"value\":\"approve\"}],\"callback_id\":\"pof\",\"team\":{\"id\":\"T1Q7NUZUM\",\"domain\":\"pdpslack\"},\"channel\":{\"id\":\"C1QJB8QMS\",\"name\":\"11\"},\"user\":{\"id\":\"U1Q8005KL\",\"name\":\"yury\"},\"action_ts\":\"1469046981.756851\",\"message_ts\":\"1469046975.000004\",\"attachment_id\":\"1\",\"token\":\"hPW7iJhJ96foXiYINFoMIs6Z\",\"original_message\":{\"type\":\"message\",\"user\":\"U1Q8005KL\",\"text\":\"Buyer uploaded Proof Of Funds Document with comments 'Send you one billion saving account statement'\",\"bot_id\":\"B1TG6L4U8\",\"attachments\":[{\"callback_id\":\"pof\",\"fallback\":\"You are unable to approve Proof Of Funds\",\"text\":\"Please approve Proof Of Funds Document <http:\\/\\/buyer1-pof.box.com>\",\"id\":1,\"actions\":[{\"id\":\"1\",\"name\":\"approve\",\"text\":\"Approve\",\"type\":\"button\",\"value\":\"approve\",\"style\":\"\"},{\"id\":\"2\",\"name\":\"decline\",\"text\":\"Decline\",\"type\":\"button\",\"value\":\"decline\",\"style\":\"\",\"confirm\":{\"text\":\"Provided Proof Of Funds Documents are invalid?\",\"title\":\"Are you sure?\",\"ok_text\":\"Yes\",\"dismiss_text\":\"No\"}}]}],\"ts\":\"1469046975.000004\"},\"response_url\":\"https:\\/\\/hooks.slack.com\\/actions\\/T1Q7NUZUM\\/61668710642\\/Uoaur3cKibnKffezoDw8oxvk\"}";
		System.out.println(new ObjectMapper().readValue(payload, ActionResponse.class));
	}
}
