package fr.eurecom.nerd.client;


import org.apache.commons.httpclient.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.commons.httpclient.HttpStatus;  
import org.apache.commons.httpclient.methods.PostMethod;  
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
  
import java.io.BufferedReader;  
import java.io.IOException;
import java.io.InputStreamReader;  
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class SpotlightNew {

	/**
	 * curl -H "Content-Type:application/json" -XPOST 'http://www.foo.com/foo'
	 *  -d '{"rootURL": "http://www.subway.com"}'
	 *  
	 *  
	 *  curl http://spotlight.dbpedia.org/rest/annotate \
  --data-urlencode "text=President Obama called Wednesday on Congress to extend a tax break
  for students included in last year's economic stimulus package, arguing
  that the policy provides more generous assistance." \
  --data "confidence=0.2" \
  --data "support=20"
	 * @return 
	 * @return 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
  
	 */
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException{
		Set<String>	str_entities = new HashSet<String>();
		str_entities= SpotlightText("This is a random text with Angelina Jolie ","0.3");
	for (String e  : str_entities)
	{
		 e =e.substring(e.lastIndexOf("/")+1,e.length());
		e= e.replace(" ","_");
		System.out.println(e);
		

	}
	}
		
	
  
  public static  Set<String> SpotlightText(String text, String confidence) throws ParserConfigurationException, SAXException, IOException {  
	  String result="";
    	

      HttpClient httpclient = new HttpClient();  
      BufferedReader bufferedreader = null;  
         
      PostMethod postmethod = new PostMethod("http://spotlight.dbpedia.org/rest/annotate");  
      postmethod.addParameter("text",text);  
      postmethod.addParameter("confidence",confidence); 

      
    try{  
      int rCode = httpclient.executeMethod(postmethod);  
  
      if(rCode == HttpStatus.SC_NOT_IMPLEMENTED) {  
        System.err.println("The Post postmethod is not implemented by this URI");  
        postmethod.getResponseBodyAsString();  
      } else {  
        bufferedreader = new BufferedReader(new InputStreamReader(postmethod.getResponseBodyAsStream()));  
        String readLine; 
       
        while(((readLine = bufferedreader.readLine()) != null)) {  
          result=result+readLine;
      }  
    System.out.println(result);  
        
      }  
    } catch (Exception e) {  
      System.err.println(e);  
    } finally {  
      postmethod.releaseConnection();  
      if(bufferedreader != null) try { bufferedreader.close();  }
        catch (Exception fe)  
		 {  
		    fe.printStackTrace();  
		 }  
    }  
    return  getTagValues(result);

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
    private static final Pattern TAG_REGEX = Pattern.compile("<Resource URI=(.+?) support");
    public static Set<java.lang.String> getTagValues(java.lang.String str) throws ParserConfigurationException, SAXException, IOException {

    	
    	Set<String> tagValues = new HashSet<String>();
    	 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         DocumentBuilder builder = factory.newDocumentBuilder();
         InputSource is = new InputSource(new StringReader(str));
          Document doc = builder.parse(is);

          NodeList nodeList = doc.getElementsByTagName("Resource");
     	 String ent="";
          String conf="";
          String sim="";
         for (int i = 0; i < nodeList.getLength(); i++) {        
        
              Node node = nodeList.item(i);
        NamedNodeMap nl = node.getAttributes();
            
             
            ent= ""+ nl.getNamedItem("URI").getTextContent();
            conf= ""+nl.getNamedItem("support").getTextContent();
            sim= ""+ nl.getNamedItem("similarityScore").getTextContent();
       	 tagValues.add(ent+"~"+conf+"~"+sim);
         
       
         }

 		return tagValues;
 		
 
   
    }
 
}


