package fr.eurecom.nerd.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.zemanta.api.Zemanta;

public class ZEMANTA {
	public static void main(String[] args) throws IOException, JSONException {
		// TODO Auto-generated method stub
		
		ArrayList<String>	str_entities = new ArrayList<String>();
		str_entities= runZEMANTA("Scchumacher+won+the+race+in+Indianapolis elengance oer all Angelina Jolie" );
		 
		  for (String e  : str_entities)
			{
				String label =e.substring(e.lastIndexOf("/")+1,e.length());
				e= e.replace(" ","_");
				System.out.println(e);
				

		 }
}

	public static ArrayList<String> runZEMANTA(String string) throws ClientProtocolException, IOException, JSONException {
		 final String API_SERVICE_URL = "http://api.zemanta.com/services/rest/0.0/";
		 ArrayList<String> tagValues = new ArrayList<String>();
	      String apiKey = "6dv52wk4t39gwpytofsfvvro";
	      String text = string;
		
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
	      parameters.put("format", "json");
	      String input = zem.getRawData(parameters);	
	      
	     
          JSONObject jObjec = new JSONObject(input);
         JSONObject jObject = jObjec.getJSONObject("markup");
          JSONArray jArray = jObject.getJSONArray("links");
          for (int i = 0; i < jArray.length(); i++) {
        	  String relevance = (jArray.getJSONObject(i).getString("relevance"));
        	  String conf = (jArray.getJSONObject(i).getString("confidence"));
        	  JSONArray jTarget = jArray.getJSONObject(i).getJSONArray("target");
        	  for (int j = 0; j < jTarget.length(); j++) {
              JSONObject jObj = jTarget.getJSONObject(j);
          //    System.out.println(i + " title : " + jObj.getString("title"));
            if(jObj.getString("type").equals("wikipedia"))
            {
         //   System.out.println(jObj.getString("url")+"relevance" +relevance);
           // double relevance= Double.parseDouble(jObj.getString("relevance"));
          //  JSONObject disambiguated = jObj.optJSONObject("disambiguated");
         //   if(disambiguated!=null)
         // 	  tagValues.add(disambiguated.optString("dbpedia")+"~" + relevance);
        	String url=jObj.getString("url").replace("%28", "");
        	url=url.replace("%29", "");
        	  tagValues.add(jObj.getString("url")+"~" + relevance+"~" + conf);
            //   tagValues.add(jObj.getString("relevance"));
            }
      }  }
		return tagValues;
    
		}
			





}
