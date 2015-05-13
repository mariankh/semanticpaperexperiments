package fr.eurecom.nerd.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Dandelion {
	public static void main(String[] args) throws IOException, JSONException {
		// TODO Auto-generated method stub
		
		ArrayList<String>	str_entities = new ArrayList();
		str_entities= runDandelion("Scchumacher+won+the+race+in+Indianapolis elengance oer all Angelina Jolie" );
		
		  for (String e  : str_entities)
			{
				String label =e.substring(e.lastIndexOf("/")+1,e.length());
				e= e.replace(" ","_");
				System.out.println(e);
				

		 }
}

	static Set<String> doDandelion(String string) throws IOException, JSONException {
		// TODO Auto-generated method stub
			Set<String> tagValues = new HashSet<String>();
			string=string.replace(" ","+");
        URL url = new URL("http://api.dandelion.eu/datatxt/nex/v1/?lang=en&text='"+string+"'&$app_id=b4f76826&$app_key=5fbf53e3b3d566a04f1d9a85edf9a82a");
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
        JSONArray jArray = jObject.getJSONArray("annotations");
        for (int i = 0; i < jArray.length(); i++) {
            JSONObject jObj = jArray.getJSONObject(i);
     //  System.out.println(i + " title : " + jObj.getString("title"));
             
             tagValues.add(jObj.getString("uri"));
     //   in.close();
    
		
		}
        return tagValues;
							
	}
	
	public static ArrayList<String> runDandelion(String string) throws IOException, JSONException {
		// TODO Auto-generated method stub
		ArrayList<String> tagValues = new ArrayList();
			string=string.replace(" ","+");
        URL url = new URL("http://api.dandelion.eu/datatxt/nex/v1/?lang=en&text='"+string+"'&$app_id=b4f76826&$app_key=5fbf53e3b3d566a04f1d9a85edf9a82a");
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
       // System.out.println(input);  
        JSONObject jObject = new JSONObject(input);
        JSONArray jArray = jObject.getJSONArray("annotations");
        for (int i = 0; i < jArray.length(); i++) {
            JSONObject jObj = jArray.getJSONObject(i);
     //  System.out.println(i + " title : " + jObj.getString("title"));
             
             tagValues.add(jObj.getString("uri")+"~"+jObj.getString("confidence"));
     //   in.close();
    
		
		}
        return tagValues;
							
	}
}
