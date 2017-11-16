package ila.restclient.creditmantri;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"oic",
"leadId"
})
public class OfferCheck {

@JsonProperty("oic")
public Integer oic = 9999;
@JsonProperty("leadId")
public Integer leadId;

/**
* No args constructor for use in serialization
* 
*/
public OfferCheck() {
}

/**
* 
* @param oic
* @param leadId
*/
public void SetLeadId(Integer leadId) {
//super();
this.leadId = leadId;
}


}