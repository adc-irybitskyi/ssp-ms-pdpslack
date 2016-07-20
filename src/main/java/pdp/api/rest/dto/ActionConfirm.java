package pdp.api.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionConfirm {
	/*
	      "confirm": {
        "text": "Provided Proof Of Funds Documents are invalid?",
        "title": "Are you sure?",
        "ok_text": "Yes",
        "dismiss_text": "No"
      }
	 */
}
