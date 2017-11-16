package ila.restclient.creditmantri;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Build_url 

{
	Properties prop = new Properties();
    InputStream input = null;
    Build_url() {
    try {

    	input = new FileInputStream("src/ila/restclient/creditmantri/main/resources/offerid.properties");
        prop.load(input);
        } catch (IOException ex) {
        ex.printStackTrace();
        System.out.println("error loading offerid.properties");
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

		
	public String geturl(String method,String type)
	{
		String tempurl = null;
		switch (method)
		{
		case "login":
			return "/login";
			
		case "Vpan":
			return  "/verify/pan";
		
		case "ProductList":
			return  "/product-list";
			
		case "SaveAnswer":
			return "/eligibility/save/answer";
		
		case "SaveAnswers":
			return "/eligibility/save/answers";
		case "OfferEligibilityForm":
			tempurl ="/eligibility-form/"  + Integer.parseInt(prop.getProperty(type));
			break;
		case "OfferEligibilityCheck":
			tempurl =  "/eligibility-check/" + Integer.parseInt(prop.getProperty(type));
			break;
		case "OfferStage":
			tempurl =  "/offerstage/update/" + type;
			break;
			
		default:
			return "error building URL: Invalid method";
		
		}
		//System.out.println("test:" + id);
			return tempurl;
		

	}
}
