package pdp.api.rest.dto;

import java.util.List;

public class AttachmentBuilder {
	private String text;
	private String callbackId;
	private String fallback;
	private List<Action> actions;

	public AttachmentBuilder setText(String text) {
		this.text = text;
		return this;
	}

	public AttachmentBuilder setCallbackId(String callbackId) {
		this.callbackId = callbackId;
		return this;
	}

	public AttachmentBuilder setFallback(String fallback) {
		this.fallback = fallback;
		return this;
	}

	public AttachmentBuilder setActions(List<Action> actions) {
		this.actions = actions;
		return this;
	}

	public Attachment createAttachment() {
		return new Attachment(text, callbackId, fallback, actions);
	}
}