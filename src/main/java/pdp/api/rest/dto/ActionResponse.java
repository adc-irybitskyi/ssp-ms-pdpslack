package pdp.api.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionResponse {
	private List<Action> actions;

	@JsonProperty("callback_id")
	private String callbackId;

	private Channel channel;

	public List<Action> getActions() {
		return actions;
	}

	public String getCallbackId() {
		return callbackId;
	}

	public void setCallbackId(String callbackId) {
		this.callbackId = callbackId;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@Override public String toString() {
		return "ActionResponse{" +
				"actions=" + actions +
				", callbackId=" + callbackId +
				", channel=" + channel +
				'}';
	}
}
