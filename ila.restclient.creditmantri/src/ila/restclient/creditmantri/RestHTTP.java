package ila.restclient.creditmantri;

import static io.restassured.RestAssured.given;


import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestHTTP {
	static String authtoken;
	public int statuscode;
	String temptoken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjI2ODI5NTgsImV4cCI6MTUxMDY4NDQ3OSwiY29udGV4dCI6eyJlbWFpbCI6InNyaWRoYXIua3Jpc2huYW5AY3JlZGl0bWFudHJpLmNvbSJ9fQ.DMUCdAEfycPym8EzjKunyB-FEmAPJhNyXT9hXI6xtMI";
	public RestHTTP(String user,String password)  {
		Response response = null;
		RestAssured.basePath = "/otprequest";
		//String requestData = "{\r\n" + 
		//		"  \"phone_home\": \"9962927658\"\r\n" + 
	//			" }";
		
		
		
		RestAssured.baseURI  = "http://cmol-api2.creditmantri.in/api/v1";
		//RestAssured.baseURI  = "http://cmol-api.creditmantri.in/api/v1";
		
		
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
		System.out.println(RestHTTP.authtoken);
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
					//System.out.println(response.asString());
					
	}
		catch (Exception e) {}
		System.out.println(RestHTTP.authtoken);
		this.statuscode = response.getStatusCode();
	}
	
	public void RestHTTP1(String user,String password)  {
		Response response = null;
		RestAssured.basePath = "/login";
		
		RestAssured.baseURI  = "http://cmol-api.creditmantri.in/api/v1";
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
					//System.out.println(response.asString());
					
	}
		catch (Exception e) {}
		System.out.println(RestHTTP.authtoken);
		this.statuscode = response.getStatusCode();
	}
	
	
	public String DoPost(String call,String type,String payload) {
		
		RestAssured.basePath = new Build_url().geturl(call, type);
		
		RestAssured.baseURI  = "http://cmol-api2.creditmantri.in/api/v1";
		Response response = 
		given().
	    contentType("application/json").
	    header("Authorization", authtoken).
    	body(payload.replaceAll("Escapeseq", "\"")).
        when().
        post();
		return response.asString();
	}
	
	

}
