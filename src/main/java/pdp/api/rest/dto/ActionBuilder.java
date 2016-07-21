package pdp.api.rest.dto;

public class ActionBuilder {
	private String name;
	private String text;
	private String value;

	public ActionBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public ActionBuilder setText(String text) {
		this.text = text;
		return this;
	}

	public ActionBuilder setValue(String value) {
		this.value = value;
		return this;
	}

	public Action createAction() {
		return new Action(name, text, value);
	}
}