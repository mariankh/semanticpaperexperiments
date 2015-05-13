package fr.eurecom.nerd.client;
import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.io.*;

import org.json.*;
public class TagHTTP {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws JSONException 
	 */
	public static void main(String[] args) throws IOException, JSONException {
		// TODO Auto-generated method stub
		
		ArrayList<String>	str_entities = new ArrayList<String>();
		str_entities= runTagME("Scchumacher+won+the+race+in+Indianapolis" ,0.0,0);
		
		  for (String e  : str_entities)
			{
				String label =e.substring(e.lastIndexOf("/")+1,e.length());
				e= e.replace(" ","_");
				System.out.println(e);
				

		 }
	}
				 public static ArrayList<String> runTagME(String string, double j, int length) throws IOException, JSONException {
		// TODO Auto-generated method stub
					 ArrayList<String> tagValues = new ArrayList<String>();
		        URL yahoo = new URL("http://tagme.di.unipi.it/tag?&key=maRiA.Lanc.terZi.2014&text='"+string+"'&epsilon="+j+"&long_text="+length);
		        URLConnection yc = yahoo.openConnection();
		        BufferedReader in = new BufferedReader(
		                                new InputStreamReader(
		                                yc.getInputStream()));
		        String inputLine;

		        while ((inputLine = in.readLine()) != null)
		        {
		            System.out.println(inputLine);
		            JSONObject jObject = new JSONObject(inputLine);
		            JSONArray jArray = jObject.getJSONArray("annotations");
		            for (int i = 0; i < jArray.length(); i++) {
		                JSONObject jObj = jArray.getJSONObject(i);
		            //    System.out.println(i + " title : " + jObj.getString("title"));
		                 
		                 tagValues.add(jObj.getString("title")+"~"+jObj.getString("rho")+"~"+j);
		        }
		            
		        }
		        in.close();
		    
				return tagValues;
				}
				 static Set<String> doTagME(String string, double j, int length) throws IOException, JSONException {
						// TODO Auto-generated method stub
									Set<String> tagValues = new HashSet<String>();
						        URL yahoo = new URL("http://tagme.di.unipi.it/tag?&key=maRiA.Lanc.terZi.2014&text='"+string+"'&epsilon="+j+"&long_text="+length);
						        URLConnection yc = yahoo.openConnection();
						        BufferedReader in = new BufferedReader(
						                                new InputStreamReader(
						                                yc.getInputStream()));
						        String inputLine;

						        while ((inputLine = in.readLine()) != null)
						        {
						            System.out.println(inputLine);
						            JSONObject jObject = new JSONObject(inputLine);
						            JSONArray jArray = jObject.getJSONArray("annotations");
						            for (int i = 0; i < jArray.length(); i++) {
						                JSONObject jObj = jArray.getJSONObject(i);
						            //    System.out.println(i + " title : " + jObj.getString("title"));
						                 try{
						                 tagValues.add(jObj.getString("title"));
						                 }catch (Exception e)
						                 {
						                	 System.out.println("no title returned");
						                 }
						        }
						            
						        }
						        in.close();
						    
								return tagValues;
								}
									
	
	
	
	

}
