import org.apache.commons.httpclient.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.commons.httpclient.HttpStatus;  
import org.apache.commons.httpclient.methods.PostMethod;  
  



import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Spotlight {

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
   
	 */

  
  public static  ArrayList<String> SpotlightText(String text)
   {   String result="";
   System.out.println("calling dbpedia "); 
   ArrayList<String> entities_for_annotator = new ArrayList();
      HttpClient httpclient = new HttpClient();  
      BufferedReader bufferedreader = null;  
         
      PostMethod postmethod = new PostMethod("http://spotlight.dbpedia.org/rest/annotate");  
      postmethod.addParameter("text",text);  
      postmethod.addParameter("confidence","0.0");  
      postmethod.addParameter("similarityScore","0,0");  

   //   postmethod.addParameter("params2", "bbb");  
      
    try{  
      int rCode = httpclient.executeMethod(postmethod);  
  
      if(rCode == HttpStatus.SC_NOT_IMPLEMENTED) {  
        System.err.println("The Post postmethod is not implemented by this URI");  
        postmethod.getResponseBodyAsString();   
      } else {  
        bufferedreader = new BufferedReader(new InputStreamReader(postmethod.getResponseBodyAsStream()));  
        String readLine; 
       
        while(((readLine = bufferedreader.readLine()) != null)) {  
        	System.out.println(readLine);
        	if(readLine.contains("http://dbpedia.org/resource"))
			{
				
        		String[] resources = readLine.split("title");
				final Pattern p = Pattern.compile("http://dbpedia.org/resource/(.*)"+'"'+" target");
				for (String s : resources)
				{
					
					java.util.regex.Matcher m = p.matcher(s);
					while(m.find()){
						String b =  m.group(1);
						System.out.println(b);
						entities_for_annotator.add(b);
					}
				}
			}
        	
      }  
      //  System.out.println(result);  
        
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
    return entities_for_annotator;
   }
    private static final Pattern TAG_REGEX = Pattern.compile("http://www.dbpedia.org/(.+?),");

    static ArrayList<String> getTagValues(final String str) {
        final ArrayList<String> tagValues = new ArrayList<String>();
        final Matcher matcher = TAG_REGEX.matcher(str);
        while (matcher.find()) {
            tagValues.add("http://www.dbpedia.org/"+matcher.group(1).substring(0,matcher.group(1).length()-1));
            System.out.println("http://www.dbpedia.org/"+matcher.group(1).substring(0,matcher.group(1).length()-1));
        }
        return tagValues;
    }
 
}


