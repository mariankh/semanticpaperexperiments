
package fr.eurecom.nerd.client;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
 

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
 
/*
 * test for annotation by Spotlight
 * */
public class SpotlightJsonTest{
 
    public final static void main(String[] args) throws Exception {
    	
    	
    	String text="President+Obama+on+Monday+with+Angelina+Jolie";
    	getEntitiesForText(text);
    }

	public static  ArrayList<String> getEntitiesForText(String string) throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		string=string.replace(" ","+");
		 ArrayList<String> entities_for_annotator = new ArrayList();
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            httpclient.addRequestInterceptor(new HttpRequestInterceptor() {
 
                public void process(
                        final HttpRequest request,
                        final HttpContext context) throws HttpException, IOException {
                    if (!request.containsHeader("Accept-Encoding")) {
//                        request.addHeader("Accept", "application/json");
                        request.addHeader("Accept","text/xml");
                    }
                }
 
            });
 
          
			
            HttpGet httpget = new HttpGet("http://spotlight.dbpedia.org/rest/annotate?text="+string+"&confidence=0.0&support=0&spotter=CoOccurrenceBasedSelector&disambiguator=Default&policy=whitelist&types=&sparql=");
 
 
            //System.out.println("executing request " + httpget.getURI());
            HttpResponse response = httpclient.execute(httpget);
 
           // System.out.println("----------------------------------------");
            //System.out.println(response.getStatusLine());
//            System.out.println(response.getLastHeader("Content-Encoding"));
//            System.out.println(response.getLastHeader("Content-Length"));
         //   System.out.println("----------------------------------------");
 
            HttpEntity entity = response.getEntity();
 
            if (entity != null && response.getStatusLine().getStatusCode() == 200) {
            	
                String content = EntityUtils.toString(entity);
                System.out.println(content);
                String[] lines = content.split("\n");
                final Pattern p = Pattern.compile("<Resource URI="+'"'+"http://dbpedia.org/resource/(.*)"+'"' +" support(.*)similarityScore="+'"'+"(.*)"+'"'+ " per");
                for (String s : lines) {
                	System.out.println(s);
                	if(s.contains("Resource")) { //resource line
                		java.util.regex.Matcher m = p.matcher(s);
    					while(m.find()){
    						String b =  m.group(3);
    						String b1 =  m.group(1);
    					
    						entities_for_annotator.add(b1+"~"+b+"~"+"0");
    					}
                	}
                }
                	
               
    
//                dtest.parserXML(content);
//                System.out.println("Uncompressed size: "+content.length());
            }
 
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
		return entities_for_annotator;
	}
 
}