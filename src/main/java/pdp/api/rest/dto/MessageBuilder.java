package pdp.api.rest.dto;

public class MessageBuilder {
	private String text;
	private String channel;
	private String username;

	public MessageBuilder setText(String text) {
		this.text = text;
		return this;
	}

	public MessageBuilder setChannel(String channel) {
		this.channel = channel;
		return this;
	}

	public MessageBuilder setUsername(String username) {
		this.username = username;
		return this;
	}

	public Message createMessage() {
		return new Message(text, channel, username);
	}
}