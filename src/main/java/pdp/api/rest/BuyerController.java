package pdp.api.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import pdp.api.rest.dto.ActionBuilder;
import pdp.api.rest.dto.AttachmentBuilder;
import pdp.api.rest.dto.GenericResponse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller()
@RequestMapping("/buyer")
public class BuyerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BuyerController.class);
	private static final String SLACK_API_URL = "https://slack.com/api/";

	private Map<String, Boolean> approvedMap = new HashMap<>();

	private String token;

	private RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private ObjectMapper objectMapper;

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "PDP Slack Buyer API";
	}

	//https://api.slack.com/docs/slack-button
	//https://slack.com/oauth/pick_reflow?scope=incoming-webhook%2Ccommands%2Cbot&client_id=58260985973.61132192129&team=T1Q7NUZUM
	@RequestMapping(value = {"/token"},  method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE )
	@ResponseBody
	String setToken(@RequestBody String token) {
		LOGGER.info("setToken:" + token);
		this.token = token;
		return token;
	}

	@RequestMapping(value = {"/pof"},  method = RequestMethod.POST)
	@ResponseBody
	String buyerSendPof(@RequestBody String body) {
		LOGGER.info("buyerSendPof body:" + body);
		//chat.postMessage
		// ?token=xoxp-58260985973-58272005666-61562401281-54e3b07cee
		// &channel=11
		// &text=Buyer uploaded Proof Of Funds Document with comments 'Send you one billion saving account statement'
		// &attachments=[
		// { "text": "Please approve Proof Of Funds Document http://buyer1-pof.box.com",
		// "fallback":"You are unable to approve Proof Of Funds",
		// "callback_id":"pof",
		// "actions":[{"name":"approve","text":"Approve","type":"button","value":"approve"},{"name":"decline","text":"Decline","type":"button","value":"decline","confirm":{"title":"Are you sure?","text":"Provided Proof Of Funds Documents are invalid?","ok_text":"Yes","dismiss_text":"No"}}]}]
		StringBuilder sb = new StringBuilder(SLACK_API_URL)
				.append("chat.postMessage")
				.append("?token=" + token)
				.append("&channel=" + "11")
				.append("&text=" + "Buyer uploaded Proof Of Funds Document with comments 'Send you one billion saving account statement'")
				.append("&attachments=[" + toAttachment() + "]");
		ResponseEntity<GenericResponse> result = restTemplate.exchange(sb.toString(),
				HttpMethod.GET,
				null,
				GenericResponse.class);
		LOGGER.info("buyerSendPof result: " + result.getBody());
		return "ok";
	}

	private String toAttachment() {
		try {
			//TODO: use autowired objectMapper field
			return new ObjectMapper().writeValueAsString(
					new AttachmentBuilder()
							.setText("Please approve Proof Of Funds Document http://buyer1-pof.box.com")//TODO: Fix it
							.setFallback("You are unable to approve Proof Of Funds")
							.setCallbackId("pof")
							.setActions(Collections.singletonList(new ActionBuilder()
								.setName("approve")
								.setText("Approve")
								.setValue("approve")
								.createAction()))
							.createAttachment());//TODO: Add Decline button
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Can't create new attachment json", e);
		}
	}
//
//	public static void main(String[] args) throws Exception{
//		System.out.println(new BuyerController().toAttachment());
//	}
}
