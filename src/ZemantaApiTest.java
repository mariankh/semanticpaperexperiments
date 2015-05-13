import java.util.HashMap;
 import com.zemanta.api.Zemanta;
 import com.zemanta.api.ZemantaResult;

 public class ZemantaApiTest {
  public static void main(String[] args) {

      final String API_SERVICE_URL = "http://api.zemanta.com/services/rest/0.0/";
					
      String apiKey = "6dv52wk4t39gwpytofsfvvro";
      String text = "The Phoenix Mars Lander has successfully deployed its USA";
	
      HashMap<String, String> parameters = new HashMap<String, String>();
      parameters.put("method", "zemanta.suggest");
      parameters.put("api_key", apiKey);
      parameters.put("text", text);
      parameters.put("format", "json");
      parameters.put("return_images", "0");
      parameters.put("personal_scope", "0");
      parameters.put("return_rdf_links", "0");
      parameters.put("return_categories", "0");
      parameters.put("return_articles", "0");
      parameters.put("return_keywords", "0");
    
      Zemanta zem = new Zemanta(apiKey, API_SERVICE_URL);	
/*	
      // Example 1: suggest
      ZemantaResult zemResult = zem.suggest(parameters);
      if(!zemResult.isError) {
	System.out.println(zemResult);
      }
   
     // Example 2: suggest markup
     ZemantaResult zemMarkup = zem.suggestMarkup(text);
     if(!zemMarkup.isError) {
    	 System.out.println("here");
    	 zemMarkup.getConfidenceSortedMarkup(true);
    
    	 
     }
	*/
   
     // Example 3: json or rdfxml

     parameters.put("format", "json");
     String resultJSON = zem.getRawData(parameters);	
     System.out.println(resultJSON);
     
    }
 }	

