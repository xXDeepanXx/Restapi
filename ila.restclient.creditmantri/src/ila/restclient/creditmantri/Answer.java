package ila.restclient.creditmantri;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"questionId",
"questionSlug",
"answer",
"answerSlug"
})
public class Answer {
	Properties prop = new Properties();
    InputStream input = null;
    Answer() {
    try {

    	input = new FileInputStream("src/ila/restclient/creditmantri/main/resources/config.properties");
        
        // load a properties file
        prop.load(input);
        } catch (IOException ex) {
        ex.printStackTrace();
        System.out.println("error");
    } finally {
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();  
            }
        }
    }

  }

	

@JsonProperty("questionId")
private Integer questionId;
@JsonProperty("questionSlug")
private String questionSlug;
@JsonProperty("answer")
private String answer;
@JsonProperty("answerSlug")
private String answerSlug;

@JsonProperty("questionId")
public Integer getQuestionId() {
return questionId;
}

@JsonProperty("questionId")
public void setQuestionId(Integer questionId) {
this.questionId = questionId;
}

@JsonProperty("questionSlug")
public String getQuestionSlug() {
return questionSlug;
}

@JsonProperty("questionSlug")
public void setQuestionSlug(String questionSlug) {
this.questionSlug = questionSlug;
}

@JsonProperty("answer")
public String getAnswer() {
return answer;
}


@JsonProperty("answer")
public void setAnswer(String answer) {
this.answer = answer;
}
@JsonProperty("answerSlug")
public void setAnswerSlug(String answerSlug) {
this.answerSlug = answerSlug;
}

public void setAnswer(String questionSlug,String answer) {

this.setAnswer(answer);
this.setAnswerSlug(answer);
this.setQuestionSlug(questionSlug);
try {
this.setQuestionId(Integer.parseInt(prop.getProperty(questionSlug)));
}catch (Exception e) {
	//e.printStackTrace();
	MyLogger.log(Level.WARNING, "Invalid Question Slug : " + questionSlug + "    Skipping... " );
	//System.out.println(questionSlug);}
	this.setQuestionSlug(null);
	this.setAnswer(null);
	this.setAnswerSlug(null);


	}
}

public static String toString(List<Answer> answer) {
String answerlist = null ;
	for (int i=0;i<answer.size();i++)
	{ if (answerlist == null) {answerlist = "[" +answer.get(i).getQuestionSlug()+":"+ answer.get(i).getAnswer()+"]";}
	else {
		answerlist.concat("[" +answer.get(i).getQuestionSlug()+":"+ answer.get(i).getAnswer()+"]");
	}//answerlist = answerlist + answer.get(i).getAnswer();
	}
	System.out.println(answerlist);
	return answerlist;
}


}