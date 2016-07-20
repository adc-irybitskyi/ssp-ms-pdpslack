package pdp.api.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericResponse {
	private Boolean ok;

	public Boolean getOk() {
		return ok;
	}

	public void setOk(Boolean ok) {
		this.ok = ok;
	}

	@Override public String toString() {
		return "GenericResponse{" +
				"ok=" + ok +
				'}';
	}
}
