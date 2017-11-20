package ila.restclient.creditmantri;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;

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
	public String errmsg;
	static JsonParser jp = new JsonParser();
	static ObjectMapper mapper = new ObjectMapper();
	public IlaApi(String user,String password)
	{
	 request = new RestHTTP(user,password);
	  
	 if (request.statuscode > 400) 
	 { this.errmsg = jp.parse(request.response.asString()).getAsJsonObject().get("data").getAsJsonObject().get("errors").getAsJsonArray().get(0).getAsJsonObject().get("message").getAsString(); 
	 MyLogger.log(Level.SEVERE, this.errmsg);
	 this.statuscode = 9999;}	
	 else
	 {this.statuscode = request.statuscode;}
	}

public static String OfferEligibilityCheck(String type)   {
		
		String payload = null;
		
		
			OfferCheck oc = new OfferCheck();
			oc.SetLeadId(GetLeadId(type));
			try {
				payload = mapper.writeValueAsString(oc);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				MyLogger.log(Level.SEVERE, "OfferEligibilityCheck Json Error:" + payload);
				e.printStackTrace();
			}
			//System.out.println(payload);
			
		
		
		String response =  request.DoPost("OfferEligibilityCheck",type ,payload);
		//System.out.println(response);
		int statuscode = jp.parse(response).getAsJsonObject().get("statusCode").getAsInt();
		if (statuscode != 200)
		{	MyLogger.log(Level.SEVERE, "OfferEligibilityCheck Request Json :" + payload);
			MyLogger.log(Level.SEVERE, response);}
		return response;
		
	}

public static String EligibiltyForm(String type)  {
	String payload = null;
	
	
		try {
			payload = mapper.writeValueAsString(new OfferForm());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			MyLogger.log(Level.SEVERE, "EligibiltyForm Json Error:" + payload);
			e.printStackTrace();
		}
		
	String response =  request.DoPost("OfferEligibilityForm",type ,payload);
	int statuscode = jp.parse(response).getAsJsonObject().get("statusCode").getAsInt();
	if (statuscode != 200)
	{	MyLogger.log(Level.SEVERE, "OfferEligibilityForm Request Json :" + payload);
		MyLogger.log(Level.SEVERE, response);}
	return response;
	
	
}
	


public String AllOffers(String type)  {
	
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
	MyLogger.log(Level.INFO, "Approved Count: " + approvedcount +"| Offers: "+ approved.toString());
	MyLogger.log(Level.INFO, "Unapproved Count: " + unapprovedcount +"| Offers: "+ unapproved.toString());
	Display d= new Display();
	
	d.DisplayAllOffer(approved, unapproved);
	return response;
	
}



	public static int GetLeadId(String type)  {
		
		String response =  IlaApi.EligibiltyForm(type);
		int  leadid = jp.parse(response).getAsJsonObject().get("leadId").getAsInt();
		int statuscode = jp.parse(response).getAsJsonObject().get("statusCode").getAsInt();
		if (statuscode != 200)
		{	MyLogger.log(Level.SEVERE, response);}
		return leadid;
		
		
	}

public String DefaultData(String type,String rawData)
{
	RawAnswer raw = new RawAnswer();
	raw.setLeadId(GetLeadId(type));
	raw.setrawAnswers("<RawAnswer>");
	String payload = null;
	try {
		payload = mapper.writeValueAsString(raw);
	} catch (JsonProcessingException e) {
		MyLogger.log(Level.SEVERE, "DefaultData Json Error:" + payload);
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	payload = payload.replaceAll("\"<RawAnswer>\"", rawData);
	
	System.out.println("SAVING DEFAULT VALUES:    "+payload);
	String  response =  request.DoPost("SaveAnswers",type,payload);
	int statuscode = jp.parse(response).getAsJsonObject().get("statusCode").getAsInt();
	if (statuscode != 200)
	{	MyLogger.log(Level.SEVERE, "DefaultData Request Json :" + payload);
		MyLogger.log(Level.SEVERE, response);}
	//System.out.println(response);
	return response;
	
	}
public String MoveStageAll(String type)  {
	String response = "MOVING STAGE:    ";
	
	String temp = this.MoveStage(type,"personal");
	response = response + temp;
	temp = this.MoveStage(type,"income");
	response = response + temp;
	temp = this.MoveStage(type,"others");
	response = response + temp;
	System.out.print(response);
	return response;
	
	
}
		public JsonObject GetPrefilled(String type)  {
		
		String response =  IlaApi.EligibiltyForm(type);
		System.out.println(response);
		JsonObject  prefilled = jp.parse(response).getAsJsonObject().get("prefilledAnswers").getAsJsonObject();
		System.out.println(prefilled);
		return prefilled;
		
		
	}
	
	public void UpdateAnswer(String type,String question, String answer) 
	{
		String payload = null;
		SaveAnswer sa = new SaveAnswer();
		sa.setLeadId(GetLeadId(type));
		sa.set(question, answer);		
		
		try {
			payload = mapper.writeValueAsString(sa);
		} catch (JsonProcessingException e) {
			MyLogger.log(Level.SEVERE, "UpdateAnswer Json Error:" + payload);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String  response =  request.DoPost("SaveAnswer",type,payload);
		this.MoveStageAll(type);
	}
	public String UpdateAnswersList(String type,List<Answer> answer) 
		{
		SaveAnswer sa = new SaveAnswer();
		String payload = null;
		sa.setLeadId(GetLeadId(type));
		sa.setAnswers(answer);		
		
		
		try {
			payload = mapper.writeValueAsString(sa);
		} catch (JsonProcessingException e) {
			MyLogger.log(Level.SEVERE, "UpdateAnswerList Json Error:" + payload);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(payload);
		
		String  response =  request.DoPost("SaveAnswers",type,payload);
		return response;
		//System.out.println(response);
		//this.MoveStageAll(type);
		}
	
	private String MoveStage(String type,String Stage) 
	{
	String payload = null;
	OfferCheck ms = new OfferCheck();
	
	ms.SetLeadId(GetLeadId(type));
	
	try {
		payload = mapper.writeValueAsString(ms);
	} catch (JsonProcessingException e) {
		MyLogger.log(Level.SEVERE, "MoveStage Json Error:" + payload);
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//System.out.println(payload);
	
	String  response =  request.DoPost("OfferStage",Stage,payload);
	System.out.print(response);
	return response;
	}

	
}
