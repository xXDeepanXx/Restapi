package ila.restclient.creditmantri;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"oic",
"leadId",
"answers"
})
public class RawAnswer {

@JsonProperty("oic")
private Integer oic = 9999;
@JsonProperty("leadId")
private Integer leadId;
@JsonProperty("answers")
private String answers;

@JsonProperty("leadId")
public void setLeadId(Integer leadId) {
this.leadId = leadId;
}
@JsonProperty("answers")
public void setrawAnswers(String rawAnswer) {
this.answers = rawAnswer;
}

}