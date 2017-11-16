package ila.restclient.creditmantri;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class IlaApi {
	static RestHTTP request;
	public int statuscode;
	static JsonParser jp = new JsonParser();
	static ObjectMapper mapper = new ObjectMapper();
	public IlaApi(String user,String password)
	{
	 request = new RestHTTP(user,password);
	 this.statuscode = request.statuscode;
	}

public static String OfferEligibilityCheck(String type) {
		
		String payload = null;
		
		try {
			OfferCheck oc = new OfferCheck();
			oc.SetLeadId(GetLeadId(type));
			payload = mapper.writeValueAsString(oc);
			System.out.println(payload);
			} 
		catch (JsonProcessingException e) {	e.printStackTrace();}
		
		
		String response =  request.DoPost("OfferEligibilityCheck",type ,payload);
		System.out.println(response);
		return response;
		
	}

public static String EligibiltyForm(String type) {
	String payload = null;
	
	try {
		payload = mapper.writeValueAsString(new OfferForm());
		} 
	catch (JsonProcessingException e) {	e.printStackTrace();}
	
	
	String response =  request.DoPost("OfferEligibilityForm",type ,payload);
	return response;
	
	
}
	


public String AllOffers(String type) {
	
	List<String> approved = new ArrayList<String>(),unapproved = new ArrayList<String>();
	String response = IlaApi.OfferEligibilityCheck(type);
	//System.out.println(response);
	String ProductName = jp.parse(response).getAsJsonObject().get("productName").getAsString();
	System.out.println("                   ******************"+ProductName+"********************");
	JsonObject offer = jp.parse(response).getAsJsonObject().getAsJsonObject("offers");
	JsonArray eligible = offer.getAsJsonArray("eligibleOffersList");
	JsonArray noteligible = offer.getAsJsonArray("notEligibleOffersList");
	
	int approvedcount = offer.get("eligibleOffersCount").getAsInt();
	int unapprovedcount = offer.get("notEligibleOffersCount").getAsInt();
	
	for(int i=0;i<approvedcount;i++)
	{
		approved.add(eligible.get(i).getAsJsonObject().getAsJsonObject().get("slug").getAsString());
	}
	
	for(int i=0;i<unapprovedcount;i++)
	{
		unapproved.add(noteligible.get(i).getAsJsonObject().getAsJsonObject().get("slug").getAsString());
	}
	
	Display d= new Display();
	d.DisplayAllOffer(approved, unapproved);
	return response;
	
}



	public static int GetLeadId(String type) {
		
		String response =  IlaApi.EligibiltyForm(type);
		//System.out.println(response);
		int  leadid = jp.parse(response).getAsJsonObject().get("leadId").getAsInt();
		//GenerateDatarecur(jp.parse(response).getAsJsonObject());
		//GenerateDatarecur(type,"Salaried");
		return leadid;
		
		
	}

public void DefaultData(String type,String rawData) throws JsonProcessingException
{
	RawAnswer raw = new RawAnswer();
	raw.setLeadId(GetLeadId(type));
	raw.setrawAnswers("<RawAnswer>");
	String payload = mapper.writeValueAsString(raw);
	
	payload = payload.replaceAll("\"<RawAnswer>\"", rawData);
	
	System.out.println("SAVING DEFAULT VALUES:    "+payload);
	String  response =  request.DoPost("SaveAnswers",type,payload);
	System.out.println(response);
	
	}
public void MoveStageAll(String type) throws JsonProcessingException {
	System.out.print("MOVING STAGE:    ");
	this.MoveStage(type,"personal");
	this.MoveStage(type,"income");
	this.MoveStage(type,"others");
	
}
	//@Deprecated
public static void Generatedata(String type, String EmploymentType) {
		
		String response =  IlaApi.EligibiltyForm(type);
		StaticQslug Qslug = new StaticQslug();
		List<Answer> GenerateAnswer = new ArrayList<Answer>();

		//EmploymentType = "salaried";
		JsonObject  questions = jp.parse(response).getAsJsonObject().get("form").getAsJsonObject().get("questions").getAsJsonObject();
		Iterator<String> formkeys = questions.keySet().iterator();
		while (formkeys.hasNext())
		{ String node =formkeys.next();
		//node.split("__");
		if ((node.toLowerCase().contains("employmenttype:"+EmploymentType))||!(node.toLowerCase().contains("employmenttype")))
		{//System.out.println(node);
		
		String questionid = questions.getAsJsonObject(node).getAsJsonObject("questionDetails").get("id").getAsString();
		//String serverrules = questions.getAsJsonObject(node).getAsJsonObject("rules").get("serverRules").getAsString();
		String questionlabel = questions.getAsJsonObject(node).getAsJsonObject("questionDetails").get("label").getAsString();
		JsonObject answers = questions.getAsJsonObject(node).getAsJsonObject("answers");
		//System.out.print(questionid + "     " + "          "+ Qslug.getQuestionSlug(questionid)+"     "+ChooseRandom(answers));
		System.out.print(Qslug.getQuestionSlug(questionid)+","+ChooseRandom(answers)+",");
		}		
		}
		System.out.println(Qslug.getQuestionSlug("3")+","+EmploymentType);
		//System.out.println(formkeys); 
		//System.out.println(formkeys.size()); 
		//System.out.println(form.;
		
		}@Deprecated
public static String ChooseRandom(JsonObject options) {
	
	if (options != null) {
	String option;
	List<String> choiceList = new ArrayList<String>(options.keySet());
	int item = new Random().nextInt(options.size()); 
	option = choiceList.get(item);
	return option;	
}else return null;
}
	
	public JsonObject GetPrefilled(String type) {
		
		String response =  IlaApi.EligibiltyForm(type);
		System.out.println(response);
		JsonObject  prefilled = jp.parse(response).getAsJsonObject().get("prefilledAnswers").getAsJsonObject();
		System.out.println(prefilled);
		return prefilled;
		
		
	}
	
	public void UpdateAnswer(String type,String question, String answer) throws JsonProcessingException 
	{
		String payload = null;
		SaveAnswer sa = new SaveAnswer();
		sa.setLeadId(GetLeadId(type));
		sa.set(question, answer);		
		
		payload = mapper.writeValueAsString(sa);
		String  response =  request.DoPost("SaveAnswer",type,payload);
		
	}
	public void UpdateAnswersList(String type,List<Answer> answer) throws JsonProcessingException 
		{
		SaveAnswer sa = new SaveAnswer();
		String payload = null;
		sa.setLeadId(GetLeadId(type));
		sa.setAnswers(answer);		
		
		
		payload = mapper.writeValueAsString(sa);
		System.out.println(payload);
		
		String  response =  request.DoPost("SaveAnswers",type,payload);
		System.out.println(response);
		this.MoveStageAll(type);
		}
	
	public void MoveStage(String type,String Stage) throws JsonProcessingException 
	{
	String payload = null;
	OfferCheck ms = new OfferCheck();
	
	ms.SetLeadId(GetLeadId(type));
	
	payload = mapper.writeValueAsString(ms);
	//System.out.println(payload);
	
	String  response =  request.DoPost("OfferStage",Stage,payload);
	System.out.print(response);
	}

	
}
