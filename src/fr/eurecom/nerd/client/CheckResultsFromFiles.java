package fr.eurecom.nerd.client;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class CheckResultsFromFiles {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<Reviews> all_entities_per_review_d = new ArrayList<Reviews>();
		ArrayList<Reviews> all_entities_per_review_t = new ArrayList<Reviews>();
		all_entities_per_review_d = readFileIntoList(args[0],all_entities_per_review_d);
		all_entities_per_review_t = readFileIntoList(args[1],all_entities_per_review_t);
		

		Set<String> dataset = null ;
		Set<String> tagger = null;
		for (int i=0; i< 100; i++)
		{
			dataset = all_entities_per_review_d.get(i).entities;
			tagger = all_entities_per_review_t.get(i).entities;
			
		
		/*Precision= true positive (correctly guessed) vs all_guessed
			recall= true positive vs all_possitive (all_available) */
			int correctly_guessed=0;
			int all_guessed=tagger.size();
			int all_available=dataset.size();
			for(String s : tagger)
			{
				s=s.replace("%28", "");
	        	s=s.replace("%29", "");
	        	
				if( dataset.contains(s))
					correctly_guessed++;
			}
		System.out.println (i+"," +correctly_guessed+"," +all_guessed+"," +all_available);	

		}
	}
	


	private static ArrayList<Reviews> readFileIntoList(String string, ArrayList<Reviews> all_entities_per_review_d) throws IOException {
		BufferedReader d_reader = new BufferedReader(new FileReader(string));
		 Set<String> str_entities;
		 String line;
		 while ((line = d_reader.readLine()) != null) {
				String[] elements = line.split(",");
				 Set<String> availentities = new HashSet<String>();	
			
			for (int i=2; i< elements.length; i++)
			{
				String[] segments = elements[i].split("/");
				String idStr = segments[segments.length-1];
				 String str= idStr.toLowerCase().replaceAll("\\([^\\(]*\\)", "");
				 str = str.charAt(str.length()-1)==('_') ? str.substring(0,str.length()-1):  str;
				availentities.add(str.toLowerCase());
			}
			all_entities_per_review_d.add(new Reviews(elements[0], availentities));
		 }
		return all_entities_per_review_d;
	}
}
