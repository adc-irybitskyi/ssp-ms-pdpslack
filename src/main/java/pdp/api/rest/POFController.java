package pdp.api.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class POFController {
	private static final Logger LOGGER = LoggerFactory.getLogger(POFController.class);

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "PDP Slack API";
	}

	@RequestMapping(value = {"/pof"}, method = RequestMethod.POST)
	@ResponseBody
	public String pofAction(@RequestBody Object body) {
		LOGGER.error("pofAction:" + body);
		return "ok";
	}
}
