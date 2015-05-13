package fr.eurecom.nerd.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AlchemyNEW {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException, JSONException {
		// TODO Auto-generated method stub
		String text= "god I hate this album and every song on it I hate westlife and their simpering boyband plastic pop pap In Britian";
		Set<String>	str_entities = new HashSet<String>();
		text=text.replace(" ","+");
		str_entities= runAlchemy(text);
		
		  for (String e  : str_entities)
			{
				String label =e.substring(e.lastIndexOf("/")+1,e.length());
				e= e.replace(" ","_");
				System.out.println(e);
				

		 }
	}
				 static Set<String> runAlchemy(String string) throws IOException, JSONException {
		// TODO Auto-generated method stub
					Set<String> tagValues = new HashSet<String>();
		        URL yahoo = new URL("http://access.alchemyapi.com/calls/text/TextGetRankedNamedEntities?apikey=b1dfe1b117afb5396431a53cfc10527ed9e6f522&text='"+string+"'&outputMode=json");
		        URLConnection yc = yahoo.openConnection();
		        BufferedReader in = new BufferedReader(
		                                new InputStreamReader(
		                                yc.getInputStream()));


		        String inputLine="";
		        String input="";

		        while ((inputLine = in.readLine()) != null)
		        {
		        	input+="" + inputLine;
		        	 System.out.println(inputLine);
		        }
		           
		            JSONObject jObject = new JSONObject(input);
		            JSONArray jArray = jObject.getJSONArray("entities");
		            for (int i = 0; i < jArray.length(); i++) {
		                JSONObject jObj = jArray.getJSONObject(i);
		            //    System.out.println(i + " title : " + jObj.getString("title"));
		              System.out.println(jObj.getString("relevance"));
		              double relevance= Double.parseDouble(jObj.getString("relevance"));
		              JSONObject disambiguated = jObj.optJSONObject("disambiguated");
		              if(disambiguated!=null)
		            	  tagValues.add(disambiguated.optString("dbpedia")+"~" + relevance);
		            	  
		              
		              //   tagValues.add(jObj.getString("relevance"));
		        }
		        in.close();
		    
				return tagValues;
				}
					
	
	
	
	

}
