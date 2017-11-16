package ila.restclient.creditmantri;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"oic"
})
public class OfferForm {

@JsonProperty("oic")
public String oic = "9999";

/**
* No args constructor for use in serialization
* 
*/
public OfferForm() {
}

/**
* 
* @param oic
*/
public OfferForm(String oic) {
super();
this.oic = oic;
}

}