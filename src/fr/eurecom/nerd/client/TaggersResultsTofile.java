package fr.eurecom.nerd.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONException;
import org.xml.sax.SAXException;

import com.textrazor.AnalysisException;

import fr.eurecom.nerd.client.schema.Entity;
import fr.eurecom.nerd.client.type.DocumentType;
import fr.eurecom.nerd.client.type.ExtractorType;

public class TaggersResultsTofile {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws AnalysisException 
	 * @throws JSONException 
	 * @throws NumberFormatException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public static void main(String[] args) throws IOException, AnalysisException, NumberFormatException, JSONException, ParserConfigurationException, SAXException {
		// TODO Auto-generated method stub
		 BufferedReader br = new BufferedReader(new FileReader(args[0]));
		 PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(args[0]+"_"+args[1]+".txt", true)));
		    String line;
		    NERD nerd = new NERD("7mvm8gdsfaoa47gumksiin5kaqbab9kq");
		    List<Entity>  entities = new ArrayList<Entity>();
		    int linecount=0;
		   while ((line = br.readLine()) != null) {
			   while(linecount< Integer.parseInt(args[3]))
			   {
				   line = br.readLine();
				   linecount++;
			   }
			   if(args.length<4)args[4]="0";
			   out = new PrintWriter(new BufferedWriter(new FileWriter(args[0]+"_"+args[1]+"_"+args[2]+"_"+args[4]+".txt", true)));
			   String review="";
				String[] elements = line.split(",");
			if( elements.length>0 )
					 review=elements[1];
			elements[1] = elements[1].replaceAll("[^a-zA-Z0-9] ", " ");
			elements[1] = elements[1].replaceAll("&#039;", "");
			elements[1] = elements[1].replaceAll("&#038", "");
			//elements[1] = elements[1].replace(" ","+");
			System.out.println(elements[1]);
			  Set<String>	str_entities = new HashSet<String>();
			  
			  
			  if(args[1].equals("1"))
			   {str_entities = new HashSet<String>();
				   str_entities.addAll( Spotlight.SpotlightText(elements[1],args[2]))  ;
			   }
			   else  if(args[1].equals("2"))
			   {str_entities = new HashSet<String>();
			  
				   str_entities.addAll(AlchemyAPIDriver.getAlchemyentities(elements[1]));
			   }
			   else  if(args[1].equals("21"))
			   {str_entities = new HashSet<String>();
				   str_entities.addAll(AlchemyNEW.runAlchemy(elements[1]));
			   }
			 
			   else  if(args[1].equals("3"))
			   {entities = new ArrayList<Entity>();str_entities = new HashSet<String>();
				   entities.addAll(nerd.annotate(ExtractorType.YAHOO, 
		                   DocumentType.PLAINTEXT,
		                   elements[1]));

				    for (Entity e : entities)
				  {
				    	if(e.getUri()!=null)
					  str_entities.add(e.getUri().toLowerCase());
				  }

			   }
			  
			   else  if(args[1].equals("4"))
			   {entities = new ArrayList<Entity>();str_entities = new HashSet<String>();
				   entities.addAll(nerd.annotate(ExtractorType.ZEMANTA, 
		                   DocumentType.PLAINTEXT,
		                   elements[1]));

				    for (Entity e : entities)
				  {
				    	
					  str_entities.add(e.getUri().toLowerCase());
				  }

			   }
			 
			   else  if(args[1].equals("6"))
			   {str_entities = new HashSet<String>();
			   elements[1] = elements[1].replace("+"," ");
				   str_entities.addAll(TestTextRazor.getEntities(elements[1]));
			   }
			  //customised
			   else  if(args[1].equals("61"))
			   {str_entities = new HashSet<String>();
				   str_entities.addAll(TestTextRazor.getEntities1(elements[1],Double.parseDouble(args[2]),Double.parseDouble(args[4])));
			   }
			   else  if(args[1].equals("7"))
			   {str_entities = new HashSet<String>();
				   str_entities.addAll(ApiCallSample.Wikiannotate(elements[1]));
			   }
			  
			   else  if(args[1].equals("8"))
			   {str_entities = new HashSet<String>();
				   str_entities.addAll(TagHTTP.runTagME(elements[1],Double.parseDouble(args[2]),Integer.parseInt(args[4])));
			   }
			   else  if(args[1].equals("9"))
			   {str_entities = new HashSet<String>();
				   str_entities.addAll(WikipediaMiner.runWikipediaMiner(elements[1],Double.parseDouble(args[2])));
			   }
			  
			   else  if(args[1].equals("10"))
			   {str_entities = new HashSet<String>();
				   str_entities.addAll(Lupedia.runLupedia(elements[1]));
			   }
			   else  if(args[1].equals("11"))
			   {str_entities = new HashSet<String>();
				   str_entities.addAll(Dandelion.runDandelion(elements[1]));
			   }
			   else  if(args[1].equals("12"))
			   {str_entities = new HashSet<String>();
				   str_entities.addAll(THD.runTHD(elements[1]));
			   }
		   
			   else  if(args[1].equals("13"))
			   {str_entities = new HashSet<String>();
				  str_entities.addAll(ZEMANTA.runZEMANTA(elements[1]));
			   }
		   
			out.print(elements[0]+","+elements[1]+"[");
			
			//out.write(e.getIdEntity() +"\t" + e.getExtractorType() +"\t"+ e.getLabel() +"\t" + e.getUri() +"\t"+ e.getExtractor());
			  for (String e  : str_entities)
				{
					 e =e.substring(e.lastIndexOf("/")+1,e.length());
					e= e.replace(" ","_");
					System.out.println(e);
					out.print(e+",");

			 }
			  out.println("]");out.close();
			  str_entities = new HashSet<String>();
		   }
	}

}
