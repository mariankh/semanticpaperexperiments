package fr.eurecom.nerd.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.eurecom.nerd.client.schema.Entity;
import fr.eurecom.nerd.client.type.DocumentType;
import fr.eurecom.nerd.client.type.ExtractorType;

public class NerdDriver {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
	    String text = "yan Gosling is everywhere these days and I couldnt be happier";
	    NERD nerd = new NERD("7mvm8gdsfaoa47gumksiin5kaqbab9kq");
	  //7mvm8gdsfaoa47gumksiin5kaqbab9kq
	    //1q03a8k1beahilavmgqm0csdk5j0g2eg
	    //read reviews one by one from file
	    // get json results for all taggers
	    //print idEntity-label-extractorType-uri-reviewid_counter_taggerid into a new separate file named by review_id
	    BufferedReader br = new BufferedReader(new FileReader("results.txt"));
	    String line;
	    int i=41;
	   while ((line = br.readLine()) != null) {
		   if (i<72){
			   try{
				 
			  System.out.println(line);
			  List<Entity>  entities = new ArrayList<Entity>();
	 
	   System.out.println("tagging with DBPEDIA_SPOTLIGHT  " +  i);
	    entities.addAll(nerd.annotate(ExtractorType.DBPEDIA_SPOTLIGHT, 
                      DocumentType.PLAINTEXT,
                      line));
	    System.out.println("tagging with EXTRACTIV  " +  i);
	    entities.addAll(nerd.annotate(ExtractorType.EXTRACTIV, 
                DocumentType.PLAINTEXT,
                line));
	    System.out.println("tagging with OPENCALAIS  " +  i);
	    entities.addAll(nerd.annotate(ExtractorType.OPENCALAIS, 
                DocumentType.PLAINTEXT,
                line));
	    System.out.println("tagging with ZEMANTA  " +  i);
	    entities.addAll(nerd.annotate(ExtractorType.ZEMANTA, 
                DocumentType.PLAINTEXT,
                line));
	 System.out.println("tagging with WIKIMETA  " +  i);
	    entities.addAll(nerd.annotate(ExtractorType.WIKIMETA, 
                DocumentType.PLAINTEXT,
                line));
	    System.out.println("tagging with YAHOO  " +  i);
	    entities.addAll(nerd.annotate(ExtractorType.YAHOO, 
                DocumentType.PLAINTEXT,
                line));
	    System.out.println("tagging with ALCHEMYAPI  " +  i);
		  entities.addAll(nerd.annotate(ExtractorType.ALCHEMYAPI, 
	                     DocumentType.PLAINTEXT,
	                   line));
		  List<Entity>  NERentities = new ArrayList<Entity>();
		    System.out.println("tagging with COMBINED  " +  i);
		    NERentities.addAll(nerd.annotate(ExtractorType.COMBINED, 
	                DocumentType.PLAINTEXT,
	                line));
		    for (Entity e  : NERentities)
    		{e.setExtractor("NERD");
    		entities.add(e);
    		}
    		
	    FileWriter fstream = new FileWriter(""+i, true);
        BufferedWriter out = new BufferedWriter(fstream);
	    		for (Entity e  : entities)
	    		{
	    			System.out.println(e.getIdEntity() +"\t" + e.getExtractorType() +"\t"+ e.getLabel() +"\t" + e.getUri() +"\t"+ e.getExtractor());
	    			out.write(e.getIdEntity() +"\t" + e.getExtractorType() +"\t"+ e.getLabel() +"\t" + e.getUri() +"\t"+ e.getExtractor());
	    			out.write("\n");

	    		}


	        
	       out.close();
		   }
		   
			   catch(Exception e )
			   {
				   System.out.println(e);
				 
			   }  
	  }
		   i++;
	   } 
	   br.close();
	}

}
