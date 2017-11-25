package ila.restclient.creditmantri;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"oic",
"leadId",
"answers"
})
public class SaveAnswer {

@JsonProperty("oic")
private Integer oic = 9999;
@JsonProperty("leadId")
private Integer leadId;
@JsonProperty("answers")
private List<Answer> answers = new ArrayList<Answer>();

@JsonProperty("answers")
private String rawAnswer;


@JsonProperty("oic")
public Integer getOic() {
return oic;
}

@JsonProperty("oic")
public void setOic(Integer oic) {
this.oic = oic;
}

@JsonProperty("leadId")
public Integer getLeadId() {
return leadId;
}

@JsonProperty("leadId")
public void setLeadId(Integer leadId) {
this.leadId = leadId;
}

@JsonProperty("answers")
public List<Answer> getAnswers() {
return answers;
}

@JsonProperty("answers")
public void setAnswers(List<Answer> answer) {
	this.answers = answer;
}

@JsonProperty("answers")
public void setrawAnswers(String rawAnswer) {
this.rawAnswer = rawAnswer;
}

@Deprecated
public void set(String question,String answer) {
	List<Answer> temp = new ArrayList<Answer>(); 
	Answer answer1 = new Answer();
	if (question.isEmpty()|| question != null)
	{answer1.setAnswer(question, answer);
	temp.add(answer1);
	System.out.println(temp);
	this.setAnswers(temp);
	}
	
}


}

