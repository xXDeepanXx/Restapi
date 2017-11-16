package ila.restclient.creditmantri;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"source",
"phone_home",
"email_id",
"password",
"utm_source",
"utm_medium",
"utm_content",
"utm_term",
"utm_campaign",
"ip"
})
public class Login {

@JsonProperty("source")
public String source;
@JsonProperty("phone_home")
public String phoneHome;
@JsonProperty("email_id")
public String emailId;
@JsonProperty("password")
public String password;
@JsonProperty("utm_source")
public String utmSource;
@JsonProperty("utm_medium")
public String utmMedium;
@JsonProperty("utm_content")
public String utmContent;
@JsonProperty("utm_term")
public String utmTerm;
@JsonProperty("utm_campaign")
public String utmCampaign;
@JsonProperty("ip")
public String ip;

/**
* No args constructor for use in serialization
* 
*/
public Login() {
}

/**
* 
* @param utmContent
* @param emailId
* @param utmTerm
* @param source
* @param utmMedium
* @param phoneHome
* @param utmCampaign
* @param utmSource
* @param password
* @param ip
*/


public Login(String user,String password) {

Boolean phone_no;

 try  
  {  
     Double.parseDouble(user);
     phone_no =  true;
  }  
  catch(NumberFormatException nfe)  
  {  
    phone_no =  false;  
  }  
 

if (phone_no)
{this.phoneHome = user;}
else
{this.emailId = user;}
this.password = password;

this.source = "Website";
this.utmSource = "email";
this.utmCampaign = "fb";
this.ip = "192.168.0.02";

}




public Login(String source, String phoneHome, String emailId, String password, String utmSource, String utmMedium, String utmContent, String utmTerm, String utmCampaign, String ip) {
super();
this.source = source;
this.phoneHome = phoneHome;
this.emailId = emailId;
this.password = password;
this.utmSource = utmSource;
this.utmMedium = utmMedium;
this.utmContent = utmContent;
this.utmTerm = utmTerm;
this.utmCampaign = utmCampaign;
this.ip = ip;
}

}