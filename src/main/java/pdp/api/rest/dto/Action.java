package pdp.api.rest.dto;

public class Action {
	private String name;
	private String text;
	private String type = "button";
	private String value;

	public Action(String name, String text, String type, String value) {
		this.name = name;
		this.text = text;
		this.type = type;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override public String toString() {
		return "Action{" +
				"name='" + name + '\'' +
				", text='" + text + '\'' +
				", type='" + type + '\'' +
				", value='" + value + '\'' +
				'}';
	}
}
