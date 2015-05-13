package fr.eurecom.nerd.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

public class PrepareDatasetForExperiment {
// for each review in a review file, output a document in a folder tags
	
	/**
	 * @param args
	 * @throws AnalysisException 
	 * @throws JSONException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public static void main(String[] args) throws IOException, AnalysisException, JSONException, ParserConfigurationException, SAXException {
		// TODO Auto-generated method stub
	    String text = "yan Gosling is everywhere these days and I couldnt be happier";
	    NERD nerd = new NERD("7mvm8gdsfaoa47gumksiin5kaqbab9kq");
	  //7mvm8gdsfaoa47gumksiin5kaqbab9kq
	    //1q03a8k1beahilavmgqm0csdk5j0g2eg
	    //read reviews one by one from file
	    // get json results for all taggers
	    //print idEntity-label-extractorType-uri-reviewid_counter_taggerid into a new separate file named by review_id
	    
	    int correct =0;
	    int avail_correct=0;
	    int all_guessed=0;
	    BufferedReader br = new BufferedReader(new FileReader("reviews.txt"));
	    String line;

	   while ((line = br.readLine()) != null) {

			String[] elements = line.split("\t");
	//	String review=elements.length > 1 ?elements[3] : " " ;
		
			  List<Entity>  entities = new ArrayList<Entity>();
			  Set<String>	str_entities = new HashSet<String>();
			   List<String> str_entities_final =new ArrayList<String>();
		//	  System.out.println(review);
	  System.out.println("tagging with dbpedia " +  line);

	String review=line;
	//  str_entities.addAll( Spotlight.SpotlightText(review,"0.0"))  ;
		//str_entities.addAll(AlchemyAPIDriver.getAlchemyentities(review));
		///str_entities.addAll(ApiCallSample.Wikiannotate(review));
	//	str_entities.addAll(TestTextRazor.getEntities(review));
	 str_entities.addAll(TagHTTP.doTagME(review,0.0, 0));
	
	try{  
	 
	  str_entities.addAll(Dandelion.doDandelion(review));
	}
	catch(Exception e ){};
	for (String e  : str_entities)
		{
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("taggedReviews/"+elements[0], true)));
		    //out.write(e.getIdEntity() +"\t" + e.getExtractorType() +"\t"+ e.getLabel() +"\t" + e.getUri() +"\t"+ e.getExtractor());
			
			String label =e.substring(e.lastIndexOf("/")+1,e.length());
			out.println("-"+"\t"+"-"+"\t"+label+"\t"+e+"\t"+"-");
		    out.close();
		
	   
		}
		
	   
	}
}
}