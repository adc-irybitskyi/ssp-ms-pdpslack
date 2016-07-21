package pdp.api.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "text", "fallback", "callback_id", "actions" })
public class Attachment {
	private String text;

	@JsonProperty("callback_id")
	private String callbackId;

	private String fallback;

	private List<Action> actions;

	public Attachment() {
	}

	public Attachment(String text, String callbackId, String fallback, List<Action> actions) {
		this.text = text;
		this.callbackId = callbackId;
		this.fallback = fallback;
		this.actions = actions;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCallbackId() {
		return callbackId;
	}

	public void setCallbackId(String callbackId) {
		this.callbackId = callbackId;
	}

	public String getFallback() {
		return fallback;
	}

	public void setFallback(String fallback) {
		this.fallback = fallback;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}
}
