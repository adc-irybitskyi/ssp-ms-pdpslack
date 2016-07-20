package pdp.api.rest.dto;

public class ActionResponse {
	private Channel channel;

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@Override public String toString() {
		return "ActionResponse{" +
				"channel=" + channel +
				'}';
	}
}
