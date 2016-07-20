package pdp.api.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller()
@RequestMapping("/buyer")
public class BuyerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BuyerController.class);

	private Map<String, Boolean> approvedMap = new HashMap<>();

	private String token;

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
		return "ok";
	}
}
