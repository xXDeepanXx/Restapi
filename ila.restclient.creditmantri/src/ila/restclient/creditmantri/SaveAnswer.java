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
public void SetPersonalDefault() {
	Answer city = new Answer();
	Answer dob = new Answer();
	Answer gender = new Answer();
	Answer pin = new Answer();
	
	List<Answer> temp = new ArrayList<Answer>(); 
	city.setAnswer("city", "Chennai");
	city.setQuestionId(1);
	temp.add(city);
	dob.setAnswer("dob","20-02-1987");
	dob.setQuestionId(2);
	temp.add(dob);
	gender.setAnswer("gender","Male");
	gender.setQuestionId(5);
	temp.add(gender);
	pin.setAnswer("residentialPincode","600017");
	pin.setQuestionId(50);
	temp.add(pin);
	this.setAnswers(temp);
			}


@Deprecated
public void SetSalariedDefault() {
	List<Answer> temp = new ArrayList<Answer>(); 
	Answer answer1 = new Answer();
	Answer answer2 = new Answer();
	Answer answer3 = new Answer();
	Answer answer4 = new Answer();
	Answer answer5 = new Answer();
	Answer answer6 = new Answer();
	Answer answer7 = new Answer();
	answer1.setAnswer("employmentType", "salaried");
	answer1.setQuestionId(3);
	answer2.setAnswer("employmentType:salaried__nth","50000");
	answer2.setQuestionId(4);
	answer3.setAnswer("employmentType:salaried__salaryAccount","HDFC");
	answer3.setQuestionId(5);
	answer4.setAnswer("employmentType:salaried__companyType","publicLtd");
	answer4.setQuestionId(137);
	answer5.setAnswer("employmentType:salaried__company","CREDITMANTRI");
	answer5.setQuestionId(16);
	answer6.setAnswer("employmentType:salaried__joiningDate","02-2015");
	answer6.setQuestionId(17);
	answer7.setAnswer("employmentType:salaried__workExp","10");
	answer7.setQuestionId(18);
	temp.add(answer1);
	temp.add(answer2);
	temp.add(answer3);
	temp.add(answer4);
	temp.add(answer5);
	temp.add(answer6);
	temp.add(answer7);
	this.setAnswers(temp);
}
public void set(String question,String answer) {
	List<Answer> temp = new ArrayList<Answer>(); 
	Answer answer1 = new Answer();
	answer1.setAnswer(question, answer);
	temp.add(answer1);
	System.out.println(temp);
	this.setAnswers(temp);
	
	
}



}