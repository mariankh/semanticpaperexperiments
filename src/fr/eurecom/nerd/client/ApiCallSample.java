package fr.eurecom.nerd.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * 
 * Sample Java Api Caller for Wikimeta
 * 
 * Thanks Christophe Desclaux - Eurecom
 * 
 * www.wikimeta.org www.wikimeta.com
 * Update April 2012
 * 
 */
public class ApiCallSample {

	/**
	 * @param args
	 * @throws JSONException 
	 */
	public static void main(String[] args) throws JSONException {
		// TODO Auto-generated method stub
		
		
		/*
		 * Sample of Caller
		 * 
		 * give adress in construtor or leave blank to use the default one
		 */
		WikiMetaExtractor SampleCaller = new WikiMetaExtractor("http://www.wikimeta.com/wapi/service");
		
		 /* 
		 *     obtain your free API key at http://my.wikimeta.com/amember/login
		 */ 
		
		String toannotate = "Obama rules the globe Cyprus, AMERICA";
		System.out.println(Wikiannotate(toannotate));
	}
	
	public static Set<String>  Wikiannotate(String text) throws JSONException
	{
		WikiMetaExtractor SampleCaller = new WikiMetaExtractor("http://www.wikimeta.com/wapi/service");
		String apikey = "467498073843"; // define your API key here 
		String toannotate = text;
		 String	result = SampleCaller.getResult( apikey, WikiMetaExtractor.Format.JSON, toannotate, "EN",0);
		 Set<String> tagValues = new HashSet<String>();
	    //    System.out.println(result);  
	        JSONObject jObject = new JSONObject(result);
	        JSONArray jArray1 = jObject.getJSONArray("document");
	        	
	        JSONArray jArray=jArray1.getJSONObject(2).getJSONArray("Named Entities");
	        for (int i=0; i<jArray.length(); i++)
	        {
	        	JSONObject jObj = jArray.getJSONObject(i);
	        //	 System.out.println(i + " LINKEDDATA : " + jObj.getString("LINKEDDATA"));
	        	  tagValues.add(jObj.getString("LINKEDDATA")+"~"+jObj.getString("confidenceScore"));
	        	
	        }
	            //JSONObject jObj = jArray.getJSONObject(i);
	     //  System.out.println(i + " title : " + jObj.getString("title"));
	             
	         
	     //   in.close();
	    
			
	
	        return tagValues;
							
		}
	
	  private static ArrayList<String> normalize(ArrayList<String> tagValues) {
		    final ArrayList<String> tagValuesNew = new ArrayList<String>();
		  for (String e: tagValues)
			{
				System.out.println("here" +e );
				e= e.replace("\"","");
				e= e.replace("dbpedia.org/resource","en.wikipedia.org/wiki");
				e= e.replace("dbpedia.org/resource","en.wikipedia.org/wiki");
				e= e.replace("dpedia.org/page","en.wikipedia.org/wiki");
				e= e.replace("www.rottentomatoes.com/celebrity","en.wikipedia.org/wiki");
				e=e.toLowerCase();
				tagValuesNew.add(e);
				System.out.println(e );
			}
			
			return tagValuesNew;
		}
		 
	 private static final Pattern TAG_REGEX = Pattern.compile("http://www.dbpedia.org/(.+?),");

	    private static ArrayList<String> getTagValues(final String str) {
	        final ArrayList<String> tagValues = new ArrayList<String>();
	        final Matcher matcher = TAG_REGEX.matcher(str);
	        while (matcher.find()) {
	            tagValues.add("http://www.dbpedia.org/"+matcher.group(1).substring(0,matcher.group(1).length()-1));
	        }
	        return tagValues;
	    }

		public static Collection<? extends String> Wikiannotate1(String text,
				int threshold) {
			WikiMetaExtractor SampleCaller = new WikiMetaExtractor("http://www.wikimeta.com/wapi/service");
			String apikey = "467498073843"; // define your API key here
			String toannotate = text;
			 String	result = SampleCaller.getResult1( apikey, WikiMetaExtractor.Format.JSON, toannotate, "EN",threshold);
		System.out.println(result);
			 
			 
			 return 	normalize(getTagValues(result));
	
		}

}