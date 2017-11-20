package ila.restclient.creditmantri;

import static io.restassured.RestAssured.given;

import java.util.logging.Level;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestHTTP {
	static String authtoken;
	public int statuscode;
	Response response ;
	public RestHTTP(String user,String password)  {
		response = null;
		RestAssured.basePath = "/otprequest";
		RestAssured.baseURI  = "http://cmol-api2.creditmantri.in/api/v1";
		try {
			 response = 
					(Response)
					given().
					contentType("application/json").
					header("ApiVersion","v2").
					body(new Login(user,password)).
					when().
					post();
					//then().
					//statusCode(200);
					
					RestHTTP.authtoken = "Bearer " + response.getHeaders().getValue("Authorization");
					//System.out.println(response.asString());
					//RestHTTP.authtoken = temptoken;
	}
		catch (Exception e) {}
		MyLogger.log(Level.INFO, RestHTTP.authtoken);
		this.statuscode = response.getStatusCode();
	}
	
	@Deprecated
	public void RedddstHTTP(String user,String password)  {
		Response response = null;
		RestAssured.basePath = "/login";
		
		RestAssured.baseURI  = "http://cmol-api2.creditmantri.in/api/v1";
		try {
			 response = 
					(Response)
					given().
					contentType("application/json").
					body(new Login(user,password)).
					when().
					post();
					//then().
					//statusCode(200);
					
					RestHTTP.authtoken = "Bearer " + response.getHeaders().getValue("Authorization");
					System.out.println(response.asString());
					
	}
		catch (Exception e) {}
		System.out.println(RestHTTP.authtoken);
		this.statuscode = response.getStatusCode();
	}
	
	
	public String DoPost(String call,String type,String payload) {
		
		RestAssured.basePath = new Build_url().geturl(call, type);
		
		RestAssured.baseURI  = "http://cmol-api2.creditmantri.in/api/v1";
		response = 
		given().
	    contentType("application/json").
	    header("Authorization", authtoken).
    	body(payload).
        when().
        post();
		return response.asString();
	}
	
	

}
