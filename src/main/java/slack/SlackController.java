package slack;

import javax.validation.Valid;
import javax.xml.ws.Response;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static pdp.api.rest.Token.TOKEN;

@Controller
@EnableAutoConfiguration
@RestController
@RequestMapping(value = "/slack")
public class SlackController {

	private String token = TOKEN;
	RestTemplate restTemplate = new RestTemplate();

	@RequestMapping(value = "/channel/archive", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public Response archiveChannel(@Valid @RequestBody Channel channel) {
		StringBuilder url = new StringBuilder("https://slack.com/api/groups.archive?token=");
		url.append(token);
		url.append("&channel=");
		url.append(channel.getChannelId());
		this.getResponse(url.toString(), null, Channel.class, HttpMethod.POST);
		return null;
	}

	@RequestMapping(value = "/channel/create", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public String createChannel(@Valid @RequestBody Channel channel) {
		StringBuilder url = new StringBuilder("https://slack.com/api/groups.create?token=");
		url.append(token);
		url.append("&name=");
		url.append(channel.getChannelName());
		ResponseEntity<String> c = this.getResponse(url.toString(), null, String.class, HttpMethod.POST);
		return c.getBody();
	}

	@RequestMapping(value = "/channel/invite", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public Response inviteUser(@Valid @RequestBody Channel channel) {
		StringBuilder url = new StringBuilder("https://slack.com/api/channels.invite?token=");
		url.append(token);
		url.append("&channel=");
		// C1QDB400L
		url.append(channel.getChannelId());
		url.append("&user=");
		url.append(channel.getUser());
		this.getResponse(url.toString(), null, Channel.class, HttpMethod.POST);
		return null;
	}

	@RequestMapping(value = "/channel/kick", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public Response kickUser(@Valid @RequestBody Channel channel) {
		StringBuilder url = new StringBuilder("https://slack.com/api/channels.kick?token=");
		url.append(token);
		url.append("&channel=");
		url.append(channel.getChannelId());
		url.append("&user=");
		url.append(channel.getUser());
		this.getResponse(url.toString(), null, Channel.class, HttpMethod.POST);
		return null;
	}

	protected <T> ResponseEntity<T> getResponse(String url, T contents, Class<T> type, HttpMethod method) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return restTemplate.exchange(url, method, new HttpEntity<T>(contents, headers), type);
	}
}
