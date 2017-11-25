package ila.restclient.creditmantri;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StaticQslug {
	Properties prop = new Properties();
    InputStream input = null;
    StaticQslug() {
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

    
    public String getQuestionSlug(String QuestionId)
    {
    	return prop.getProperty(QuestionId);
    }
}
