package fr.eurecom.nerd.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.apache.http.NameValuePair;

public class THD {
	public static void main(String[] args) throws IOException, JSONException {
		// TODO Auto-generated method stub
		
		ArrayList<String>	str_entities = new ArrayList();
		str_entities= runTHD("Scchumacher+won+the+race+in+Indianapolis+elengance+Angelina+Jolie" );
		 
		  for (String e  : str_entities)
			{System.out.println(e);
				String label =e.substring(e.lastIndexOf("/")+1,e.length());
				e= e.replace(" ","_");
				System.out.println(e);
				

		 }
}

	public static ArrayList<String> runTHD(String string) throws ClientProtocolException, IOException, JSONException {
		DefaultHttpClient client = new DefaultHttpClient();
	    HttpPost post = new HttpPost("http://entityclassifier.eu/thd/api/v2/extraction?apikey=8216c028b05f44babb1163eb677e8ed5&format=json&priority_entity_linking=true&provenance=thd&priority_entity_linking=true&entity_type=all");
	   post.setHeader("Content-Type", "application/json");
	   post.setEntity(new StringEntity(string));

       Scanner in = null;
       ArrayList<String> tagValues = new ArrayList();
         
           HttpResponse response = client.execute(post);
           
           System.out.println(response.getStatusLine());
           HttpEntity entity = response.getEntity();
          in = new Scanner(entity.getContent());
          String input="";

           while (in.hasNext())
           {
             System.out.println(in);
               input+=""+in.next();
           }
               
             //  System.out.println(input);  
               JSONArray jArray = (JSONArray) new JSONTokener(input).nextValue();
	            for (int i = 0; i < jArray.length(); i++) {
	                JSONObject jObj = jArray.getJSONObject(i);
	                JSONArray jObjEntities= jObj.optJSONArray("types");
	                if(jObjEntities!=null)
	                {
	                JSONObject jObj1 = jObjEntities.optJSONObject(0);
	                JSONObject confarray= jObj1.optJSONObject("confidence");
	                if(confarray!=null)
	                tagValues.add(jObj1.getString("entityURI")+"~"+confarray.optString("value")+"~"+0);
	           
	                }
	            }
      	return tagValues;

	}

}
