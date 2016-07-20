package pdp.api.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class POFController {
	private static final Logger LOGGER = LoggerFactory.getLogger(POFController.class);

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "PDP Slack API";
	}

	@RequestMapping(value = {"/pof"}, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void pofAction(@RequestBody Object body) {
		LOGGER.error("pofAction:" + body);
//		return "ok";
	}
}
