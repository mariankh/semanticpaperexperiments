package fr.eurecom.nerd.client;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
 
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
 
/*
 * test for annotation by Spotlight
 * */
public class CopyOfSpotlightJsonTest{
 
    public final static void main(String[] args) throws Exception {
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
 
//            HttpGet httpget = new HttpGet("http://spotlight.dbpedia.org/rest/annotate?text=President+Obama+on+Monday+will+call+for+a+new+minimum+tax+rate+for+individuals+making+more+than+%241+million+a+year+to+ensure+that+they+pay+at+least+the+same+percentage+of+their+earnings+as+other+taxpayers%2C+according+to+administration+officials.&confidence=0.0&support=0&spotter=CoOccurrenceBasedSelector&disambiguator=Default&policy=whitelist&types=&sparql=");
            HttpGet httpget = new HttpGet("http://spotlight.dbpedia.org/rest/annotate?text=get+current+exchange+rates+result&confidence=0.0&support=0&spotter=CoOccurrenceBasedSelector&disambiguator=Default&policy=whitelist&types=&sparql=");
 
 
            // Execute HTTP request
            System.out.println("executing request " + httpget.getURI());
            HttpResponse response = httpclient.execute(httpget);
 
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
//            System.out.println(response.getLastHeader("Content-Encoding"));
//            System.out.println(response.getLastHeader("Content-Length"));
            System.out.println("----------------------------------------");
 
            HttpEntity entity = response.getEntity();
 
            if (entity != null && response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(entity);
                System.out.println(content);
                System.out.println("----------------------------------------");
//                DomTest dtest = new DomTest();
//                dtest.parserXML(content);
//                System.out.println("Uncompressed size: "+content.length());
            }
 
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
         //   httpclient.getConnectionManager().shutdown();
        }
    }
 
}