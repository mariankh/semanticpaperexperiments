import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;


public class DbpediaSameAS {
	
	public static void main (String args[]) throws IOException
	{
		String entity_test="parov_stelar";
		String acccepted_entity_test="booty swing";
		//for each entity get a set of relevant entities
		String entity = testDbpedia.getDbPediaEntityString(entity_test);
		String accepted_entity= testDbpedia.getDbPediaEntityString(acccepted_entity_test);
		System.out.println(entity);
		System.out.println(accepted_entity);
		for (String e: getSameAs(entity))
		{
			System.out.println(e);
		}
		System.out.println(getSameAs(entity).contains(accepted_entity));
			
		//if the set contains the entitiy recognised by the annotator, mark as accepted
	}

	
	public static Set<String> getSameAs (String entity)
	{
		Set<String> sameAsEntities= new HashSet<String>();
		
		
		URL entity_url = null;
		try{
			entity_url = new URL("http://dbpedia.org/page/" + entity);
		}
		catch (MalformedURLException e) {
		}
		URLConnection entity_connection = null;
		try {
			entity_connection = entity_url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	//	System.out.println("DEBUG 1" + entity_url);
        BufferedReader in = null;
		try {
			in = new BufferedReader(
			                        new InputStreamReader(
			                        entity_connection.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch blockSystem.out.println("here");
			return null;
		}
	//	System.out.println("DEBUG 2");
		  String inputLine;
	      String returnstr;
	      try {
			while ((inputLine = in.readLine()) != null) 
			  {
				
					
					if(inputLine.contains("dbpedia-owl:wikiPageRedirects"))
					{
						//System.out.println(inputLine);
						final Pattern p = Pattern.compile("<small>dbpedia</small>:(.*)</a>");
						java.util.regex.Matcher m = p.matcher(inputLine);
						while(m.find()){
							String b =  m.group(1);
							sameAsEntities.add(b);
						}
					}
					if(inputLine.contains("owl:sameAs"))
					{
						//System.out.println(inputLine);
						final Pattern p = Pattern.compile(">http://(.*)dbpedia.org/resource/(.*)</a>");
						java.util.regex.Matcher m = p.matcher(inputLine);
						while(m.find()){
							String b =  m.group(2);
						//	System.out.println(b);
							sameAsEntities.add(b);
						}
					}
					if(inputLine.contains("foaf:primaryTopic"))
					{
						//System.out.println(inputLine);
						final Pattern p = Pattern.compile(">http://(.*)wikipedia.org/(.*)/(.*)</a>");
						java.util.regex.Matcher m = p.matcher(inputLine);
						while(m.find()){
							String b =  m.group(3);
						//	System.out.println(b);
							sameAsEntities.add(b);
						}
					}
					if(inputLine.contains("prov:wasDerivedFrom"))
					{
						//System.out.println(inputLine);
						final Pattern p = Pattern.compile(">http://(.*)wikipedia.org/(.*)/(.*)</a>");
						java.util.regex.Matcher m = p.matcher(inputLine);
						while(m.find()){
							String b =  m.group(3);
						//	System.out.println(b);
							sameAsEntities.add(b);
						}
					}
					
					
				}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
			in.close();
		} catch (IOException e) {
		}
		
		return sameAsEntities;
	}
	public static Set<String> getSameAsRedirect (String entity)
	{
		Set<String> sameAsEntities= new HashSet<String>();
		
		
		URL entity_url = null;
		try{
			entity_url = new URL("http://dbpedia.org/page/" + entity);
		}
		catch (MalformedURLException e) {
		}
		URLConnection entity_connection = null;
		try {
			entity_connection = entity_url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	//	System.out.println("DEBUG 1" + entity_url);
        BufferedReader in = null;
		try {
			in = new BufferedReader(
			                        new InputStreamReader(
			                        entity_connection.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch blockSystem.out.println("here");
			return null;
		}
	//	System.out.println("DEBUG 2");
		  String inputLine;
	      String returnstr;
	      try {
			while ((inputLine = in.readLine()) != null) 
			  {
				
					
					if(inputLine.contains("dbpedia-owl:wikiPageRedirects"))
					{
						//System.out.println(inputLine);
						final Pattern p = Pattern.compile("<small>dbpedia</small>:(.*)</a>");
						java.util.regex.Matcher m = p.matcher(inputLine);
						while(m.find()){
							String b =  m.group(1);
							sameAsEntities.add(b);
						}
					}
					
				}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
			in.close();
		} catch (IOException e) {
		}
		
		return sameAsEntities;
	}
}