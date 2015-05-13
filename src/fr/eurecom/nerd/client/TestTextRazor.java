package fr.eurecom.nerd.client;

import java.util.ArrayList;

import com.textrazor.AnalysisException;
import com.textrazor.NetworkException;
import com.textrazor.TextRazor;
import com.textrazor.annotations.AnalyzedText;
import com.textrazor.annotations.Entity;
import com.textrazor.annotations.Sentence;
import com.textrazor.annotations.Word;


public class TestTextRazor {

	/**
	 * @param args
	 * @throws NetworkException 
	 */
	public static void main(String[] args) throws NetworkException, AnalysisException {
		
		getEntities("Barclays misled shareholders and the public RBS about one of the biggest investments in the bank's history, a BBC Panorama investigation has found.");
		
		}
		
		// Use a custom rule to match 'Company' type entities
		
		

	public static ArrayList<String> getEntities1(String text, Double confidence, Double relevance)throws NetworkException, AnalysisException {
		
		// Sample request, showcasing a couple of TextRazor features
		//String API_KEY = "be54ff90e0b1fedc4b54c0990961cb51dd90ab839b62004caf2cb836";
		String API_KEY="d2b543fe57378152d183df02046d9cffc384e47d26e9b48d52db4ea2";
		TextRazor client = new TextRazor(API_KEY);
	
		client.addExtractor("words");
		client.addExtractor("entities");
		client.addExtractor("entailments");
		client.addExtractor("senses");
		client.addExtractor("entity_companies");
	
		String rules = "entity_companies(CompanyEntity) :- entity_type(CompanyEntity, 'Company').";
		
		client.setRules(rules);
		
		AnalyzedText response = client.analyze(text);
		 final ArrayList<String> tagValues = new ArrayList<String>();
		for (Sentence sentence : response.getResponse().getSentences()) {
			for (Word word : sentence.getWords()) {
				
				for (Entity entity : word.getEntities()) {
					if(entity.getConfidenceScore()> confidence && entity.getRelevanceScore()> relevance )
					tagValues.add(entity.getWikiLink());
				}
				
						
			}
		}
		return normalize(tagValues);
		// Use a custom rule to match 'Company' type entities
		
		
		
	}
	public static ArrayList<String> getEntities (String text) throws NetworkException, AnalysisException {
		
		// Sample request, showcasing a couple of TextRazor features
		String API_KEY = "d2b543fe57378152d183df02046d9cffc384e47d26e9b48d52db4ea2";
		
		TextRazor client = new TextRazor(API_KEY);
	
		client.addExtractor("words");
		client.addExtractor("entities");
		client.addExtractor("entailments");
		client.addExtractor("senses");
		client.addExtractor("entity_companies");
	
		String rules = "entity_companies(CompanyEntity) :- entity_type(CompanyEntity, 'Company').";
		
		client.setRules(rules);
		
		AnalyzedText response = client.analyze(text);
		 final ArrayList<String> tagValues = new ArrayList<String>();
		for (Sentence sentence : response.getResponse().getSentences()) {
			for (Word word : sentence.getWords()) {
				
				for (Entity entity : word.getEntities()) {
					String[] temp = entity.getWikiLink().split("/");
					String last= temp[temp.length-1];
					//System.out.println(last);
					tagValues.add(last+"~"+entity.getConfidenceScore()+"~"+ entity.getRelevanceScore());
				}
				
						
			}
		}
		return tagValues;
		// Use a custom rule to match 'Company' type entities
		
		
		
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
}
