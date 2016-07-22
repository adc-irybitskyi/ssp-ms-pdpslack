package pdp.api.rest;

import static pdp.api.rest.Token.TOKEN;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pdp.api.rest.dto.ActionBuilder;
import pdp.api.rest.dto.AttachmentBuilder;
import pdp.api.rest.dto.MessageBuilder;

@Controller()
@RequestMapping("/buyer")
public class BuyerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BuyerController.class);
	private static final String SLACK_API_URL = "https://slack.com/api/";

	private Map<String, Boolean> approvedMap = new HashMap<>();

	private String token = TOKEN;

	private String incomingHookUrl = "https://hooks.slack.com/services/T1Q7NUZUM/B1U0W997U/XqDVY82OUFMgSMGKxpVXaP" + "sZ";

	private RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private ObjectMapper objectMapper;

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "PDP Slack Buyer API";
	}

	// https://api.slack.com/docs/slack-button
	// https://slack.com/oauth/pick_reflow?scope=incoming-webhook%2Ccommands%2Cbot,chat:write:user&client_id=58260985973.61132192129&team=T1Q7NUZUM
	@RequestMapping(value = { "/token" }, method = RequestMethod.POST)
	@ResponseBody
	String setToken(@RequestBody String token) {
		LOGGER.info("setToken:" + token);
		this.token = token;
		return token;
	}

	@RequestMapping(value = { "/token" }, method = RequestMethod.GET)
	@ResponseBody
	String getToken() {
		return token;
	}

	/*
	 * @RequestMapping(value = { "/pof" }, method = RequestMethod.POST) @ResponseBody String buyerSendPof(@RequestBody String body) { LOGGER.info("buyerSendPof body:" + body);
	 * //chat.postMessage // &channel=11 // &text=Buyer uploaded Proof Of Funds Document with comments 'Send you one billion saving account statement' // &attachments=[ // {
	 * "text": "Please approve Proof Of Funds Document http://buyer1-pof.box.com", // "fallback":"You are unable to approve Proof Of Funds", // "callback_id":"pof", //
	 * "actions":[{"name":"approve","text":"Approve","type":"button","value":"approve"},{"name":"decline","text":"Decline","type":"button","value":"decline","confirm":{"title":
	 * "Are you sure?","text":"Provided Proof Of Funds Documents are invalid?","ok_text":"Yes","dismiss_text":"No"}}]}]
	 * 
	 * String urlBase = SLACK_API_URL + "chat.postMessage";
	 * 
	 * StringBuilder sb = new StringBuilder(SLACK_API_URL);
	 * 
	 * MultiValueMap<String, String> params = new LinkedMultiValueMap<>(); params.set("token", token); params.set("channel", "11"); params.set("text",
	 * "Buyer uploaded Proof Of Funds Document with comments 'Send you one billion saving account statement'"); params.set("attachments", "[" + toAttachment() + "]");
	 * 
	 * UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlBase).queryParams(params); String url = builder.build().toUri().toString(); LOGGER.info(
	 * "Composed before decode: " + url);
	 * 
	 * 
	 * //restTemplate.getForObject(url, Void.class);
	 * 
	 * try { url = URLDecoder.decode(url, "UTF-8"); } catch (UnsupportedEncodingException e) { throw new RuntimeException("url decoding", e); } LOGGER.info(
	 * "Composed after decode: " + url);
	 * 
	 * // ResponseEntity<GenericResponse> result = restTemplate.exchange(sb.toString(), // HttpMethod.GET, // null, // GenericResponse.class); // ResponseEntity<String> result =
	 * restTemplate.exchange(sb.toString(), HttpMethod.GET, null, String.class); String result = restTemplate.getForObject(url, String.class); LOGGER.info("buyerSendPof url: " +
	 * sb.toString()); LOGGER.info("buyerSendPof result2: " + result); return "ok"; }
	 */

	@RequestMapping(value = { "/pof" }, method = RequestMethod.POST)
	@ResponseBody
	@Deprecated
	// TODO: delete it
	String buyerSendPof() {
		/*
		 * TODO: "confirm": { "text": "Provided Proof Of Funds Documents are invalid?", "title": "Are you sure?", "ok_text": "Yes", "dismiss_text": "No"
		 */

		ResponseEntity<String> result = restTemplate.exchange(incomingHookUrl, HttpMethod.POST,
				toEntity(new MessageBuilder().setChannel("#11").setText("Buyer uploaded Proof Of Funds Document with comments 'Send you one billion saving account statement'")
						.setUsername("mlhbot")
						.setAttachments(Collections.singletonList(new AttachmentBuilder().setText("Please approve Proof Of Funds Document http://buyer1-pof.box.com")// TODO: Fix it
								.setFallback("You are unable to approve Proof Of Funds").setCallbackId("pof")
								.setActions(Arrays.asList(new ActionBuilder().setName("approve").setText("Approve").setValue("approve").createAction(),
										new ActionBuilder().setName("decline").setText("Decline").setValue("decline").createAction()))
								.createAttachment()))
						.createMessage()),
				String.class);
		LOGGER.info("buyerSendPof result: " + result.getBody());
		return "ok";
	}

	@RequestMapping(value = { "/pof/approve" }, method = RequestMethod.POST)
	@ResponseBody
	String buyerApprovePof(String channel) {
		ResponseEntity<String> result = restTemplate.exchange(incomingHookUrl, HttpMethod.POST,
				toEntity(new MessageBuilder().setChannel(channel)// TODO: use it
						.setText("Please Approve Proof Of Funds Document").setUsername("mlhbot")
						.setAttachments(Collections.singletonList(new AttachmentBuilder().setText("Please approve Proof Of Funds Document")// TODO: Add link to approved document
								.setFallback("You are unable to approve Proof Of Funds").setCallbackId("approve-pof")
								.setActions(Arrays.asList(new ActionBuilder().setName("approve").setText("Approve").setValue("approve").createAction(),
										new ActionBuilder().setName("decline").setText("Decline").setValue("decline").createAction()))
								.createAttachment()))
						.createMessage()),
				String.class);
		LOGGER.info("buyerApprovePof result: " + result.getBody());
		return "ok";
	}

	@RequestMapping(value = { "/final/approve" }, method = RequestMethod.POST)
	@ResponseBody
	String finalApprove(String channel) {
		ResponseEntity<String> result = restTemplate.exchange(incomingHookUrl, HttpMethod.POST,
				toEntity(new MessageBuilder().setChannel(channel)// TODO: use it
						.setText("Please do final Approve").setUsername("mlhbot")
						.setAttachments(Collections.singletonList(new AttachmentBuilder().setText("Please do final approve")// TODO: Add link to approved document
								.setFallback("You are unable to do final approve.").setCallbackId("final-approve")
								.setActions(Arrays.asList(new ActionBuilder().setName("final_approve").setText("Final Approve").setValue("approve").createAction(),
										new ActionBuilder().setName("decline").setText("Decline").setValue("decline").createAction()))
								.createAttachment()))
						.createMessage()),
				String.class);
		LOGGER.info("buyerApprovePof result: " + result.getBody());
		return "ok";
	}

	private <T> HttpEntity toEntity(T contents) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		String str;
		try {
			str = objectMapper.writeValueAsString(contents);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("toEntity", e);
		}
		return new HttpEntity(str, headers);

	}
}
