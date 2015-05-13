package fr.eurecom.nerd.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WikipediaMiner {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException, JSONException {
		// TODO Auto-generated method stub
		
		ArrayList<String>	str_entities =  new ArrayList();
		str_entities= runWikipediaMiner(",Neuronium;s best psychotronic work  Though not nearly as well known as some of their German or French counterparts (Tangerine Dream" ,0.0);
		
		  for (String e  : str_entities)
			{
				// e =e.substring(e.lastIndexOf("/")+1,e.length());
				//e= e.replace(" ","_");
				System.out.println(e);
				

		 }
}

	public static ArrayList<String> runWikipediaMiner(String string, double d) throws IOException, JSONException {
		// TODO Auto-generated method stub
			ArrayList<String> tagValues = new ArrayList();
			string=string.replace(" ","+");
        URL url = new URL("http://wikipedia-miner.cms.waikato.ac.nz/services/wikify?source='"+string+"'&minProbability="+d+"&responseFormat=json");
        URLConnection yc = url.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                yc.getInputStream()));
        String inputLine="";
        String input="";

        while ((inputLine = in.readLine()) != null)
        {
        	input+="" + inputLine;
           
        }
       System.out.println(input);  
        JSONObject jObject = new JSONObject(input);
        JSONArray jArray = jObject.getJSONArray("detectedTopics");
        for (int i = 0; i < jArray.length(); i++) {
            JSONObject jObj = jArray.getJSONObject(i);
     //  System.out.println(i + " title : " + jObj.getString("title"));
            String e =jObj.getString("title");
            e=e.substring(e.lastIndexOf("/")+1,e.length());
			e= e.replace(" ","_");
             tagValues.add(e+"~"+jObj.getString("weight")+"~"+0);
     //   in.close();
    
		
		}
        return tagValues;
							
	}
}
