package fr.eurecom.nerd.client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.*;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;



public class Lupedia {

	/**
	 * @param args
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
		public static void main(String[] args) throws IOException, JSONException, ParserConfigurationException, SAXException {
				// TODO Auto-generated method stub
			
			ArrayList<String> str_entities = new ArrayList();
		str_entities= runLupedia("England+the+most+manufactured+piece+of+rubbish+Jolie+anyware+in+the+world+god+I+hate+this+album+and+every+song+on+it+I+hate+westlife+and+their+simpering+boyband+plastic+pop+pap+In+Britan");
		
		  for (String e  : str_entities)
			{
			//	 e =e.substring(e.lastIndexOf("/")+1,e.length());
		//	e= e.replace(" ","_");
				System.out.println(e);
				
			}
		
		}

	public static ArrayList<String> runLupedia(String string) throws IOException, JSONException, ParserConfigurationException, SAXException {
		// TODO Auto-generated method stub
		ArrayList<String> tagValues = new ArrayList();
			string=string.replace(" ","+");
		//System.out.println(string);
        URL url = new URL("http://lupedia.ontotext.com/lookup/text2xml?lookupText='"+string+"'&threshold="+0);
        URLConnection yc = url.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                yc.getInputStream()));
        String inputLine="";
        String input="";

        while ((inputLine = in.readLine()) != null)
        {
        	input+=" " + inputLine;
          //  System.out.println(inputLine);
           
        }
       // System.out.println(input);
        input=input.trim();
      
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(input));
         Document doc = builder.parse(is);

         NodeList nodeList = doc.getElementsByTagName("lookups");
    	 String ent="";
         String conf="";
        for (int i = 0; i < nodeList.getLength(); i++) {                
             Node node = nodeList.item(i);
             NodeList nl = node.getChildNodes();
           
            for (int j=0; j< nl.getLength(); j++)
            {
            	 String name = nl.item(j).getNodeName();
            	 if(name.equals("instanceUri"))
            		 ent= nl.item(j).getTextContent();
            	 if(name.equals("weight"))
            		 conf=(nl.item(j).getTextContent());
        
            }
            
            String e =ent;
            e=e.substring(e.lastIndexOf("/")+1,e.length());
			e= e.replace(" ","_");
       	 tagValues.add(e+"~"+conf+"~"+0);
       	  ent="";
          conf="";
        }

		return tagValues;



							
	}


}
