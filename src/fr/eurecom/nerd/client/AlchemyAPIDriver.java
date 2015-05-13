package fr.eurecom.nerd.client;




import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.awt.List;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
	public class AlchemyAPIDriver {

		   public static void main(String[] args)
	        throws IOException, SAXException,
	               ParserConfigurationException, XPathExpressionException
	    {
	        // Create an AlchemyAPI object.
	        AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromFile("api_key.txt");

	        // Extract a ranked list of named entities for a web URL.
	        Document doc;

	        // Extract a ranked list of named entities from a text string.
	        ArrayList<String> test = getAlchemyentities(
	          "Hello there, my name is Bob Jones.  I live in the United States of America  and Morristown along with Ted Kennedy." );
	        for (String t : test)
	        {
	        	System.out.println(t);
	        }
	    }
		   
		   public static ArrayList<String> getAlchemyentities( String text)
		   {
			   AlchemyAPI alchemyObj = null;
			try {
				alchemyObj = AlchemyAPI.GetInstanceFromFile("api_key.txt");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ArrayList<String> taged_entities = new ArrayList<String>();
		        // Extract a ranked list of named entities for a web URL.
		        Document doc = null;
		        try {
					doc = alchemyObj.TextGetRankedNamedEntities(text);
					//System.out.println(doc.getFirstChild().getNodeName());
					NodeList entities = doc.getElementsByTagName("entity");
					//System.out.println("woop");
				
					//System.out.println(entities.getLength());
					for (int i=0; i<entities.getLength(); i++) {
						String entity_db="";
						String relevance="";
						 NodeList entity = entities.item(i).getChildNodes();
							//System.out.println(entity.item(3).getTextContent() +entity.getLength());
							relevance=entity.item(3).getTextContent() +entity.getLength();
							if(entity.getLength()>=11)
							{
								NodeList disamb = entity.item(9).getChildNodes();
								for (int d=0; d<disamb.getLength(); d++)
								{
									if(disamb.item(d).getNodeName().equals("dbpedia")) {
										//System.out.println(disamb.item(d).getTextContent());
										entity_db=disamb.item(d).getTextContent();
										break;
									}
								}
							}
							
							
							//System.out.println(disambig.item(1).getTextContent() );
						if(!entity_db.equals("")) 
						{
							taged_entities.add(entity_db+"~"+relevance+"~"+0);
						}
					}
					
				} catch (XPathExpressionException | IOException | SAXException
						| ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					
				}
		        return taged_entities;
		   }

	    // utility method
	    private static ArrayList<String> getStringFromDocument(Document doc) {
	        try {
	            DOMSource domSource = new DOMSource(doc);
	            StringWriter writer = new StringWriter();
	            StreamResult result = new StreamResult(writer);

	            TransformerFactory tf = TransformerFactory.newInstance();
	            Transformer transformer = tf.newTransformer();
	            transformer.transform(domSource, result);
	            System.out.println(writer.toString());	
	            

	           return normalize(getTagValues(writer.toString()));
	        } catch (TransformerException ex) {
	            ex.printStackTrace();
	            return null;
	        }
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
		private static final Pattern TAG_REGEX = Pattern.compile("<dbpedia>(.+?)</dbpedia>");

	    private static ArrayList<String> getTagValues(final String str) {
	        final ArrayList<String> tagValues = new ArrayList<String>();
	        final Matcher matcher = TAG_REGEX.matcher(str);
	        while (matcher.find()) {
	            tagValues.add(matcher.group(1));
	        }
	        return tagValues;
	    }


}
