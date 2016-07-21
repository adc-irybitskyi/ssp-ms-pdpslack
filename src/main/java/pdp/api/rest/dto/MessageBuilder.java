package pdp.api.rest.dto;

import java.util.List;

public class MessageBuilder {
	private String text;
	private String channel;
	private String username;
	private List<Attachment> attachments;

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

	public MessageBuilder setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
		return this;
	}

	public Message createMessage() {
		Message m = new Message(text, channel, username);
		m.setAttachments(attachments);
		return m;
	}
}