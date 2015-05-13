package fr.eurecom.nerd.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class SplitFile2Vars {

		/**
		 * @param args
		 * @throws IOException 
		 */
		public static void main(String[] args) throws IOException {
			// TODO Auto-generated method stub
			
			BufferedReader d_reader = new BufferedReader(new FileReader(args[0]));
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(args[0]+"_"+args[1]+"_"+args[2], true)));
			 String line;
			while ((line = d_reader.readLine()) != null) {
				String[] elements = line.split(",");
				out.print(elements[0]+","+elements[1]+",");
				for (int i=2; i< elements.length; i++)
				{	
					if(elements[i].length()>3)
					{
						String[] entity_conf = elements[i].split("~");
						if(entity_conf.length>1)
						{
						Double.parseDouble(entity_conf[1]);
						if (Double.parseDouble(entity_conf[1])> Double.parseDouble(args[1]) && Double.parseDouble(entity_conf[2])> Double.parseDouble(args[2]))
							out.print(entity_conf[0]+",");
						}
					}
					else
						out.print(elements[i]+"");
				}
				out.println("");
			}
			out.close();
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

