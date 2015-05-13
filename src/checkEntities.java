import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

//takes as input a list of entities
//for each entity returns a line of the format below
// entity,spellmistake(0,1),indbpedia(0/1),inWIki(0/1),[categor1-category2..categoryN]

public class checkEntities {

	public static int countWords (String in) {
		   String trim = in.trim();
		   if (trim.isEmpty()) return 0;
		   return trim.split("\\s+").length; //separate string around spaces
		}
	 public static void main(String args[])
	  {
	  try{
	  // Open the file that is the first 
	  // comm&& line parameter
	  FileInputStream fstream = new FileInputStream(args[0]);
	  // Get the object of DataInputStream
	  DataInputStream in = new DataInputStream(fstream);
	  BufferedReader br = new BufferedReader(new InputStreamReader(in));
	  String strLine;
	  //Read File Line By Line
	
	  while ((strLine = br.readLine()) != null)   {
	  // Print the content on the console
		 String[] aray = strLine.split(",");
		 String[] entity_text=aray[1].split("\t");
		 String entity= entity_text[0];
		 String text=entity_text[1];
		 System.out.print(entity+",");
		 
		 JazzySpellChecker jazzySpellChecker = new JazzySpellChecker();
		  
		  int count = jazzySpellChecker.countMisspelledWords(text);
		  System.out.print(text+",");
		  System.out.print(countWords(text)+",");
		  System.out.print(count+",");
		  

		   List<String> misspelledwords = jazzySpellChecker.getMisspelledWords(text);
		   System.out.print("[");
		   for (String m : misspelledwords)
		   {
			   System.out.print(m+"-");
			   
		   }
		   System.out.print("],");
		 String dbreturn=testDbpedia.isInDbPedia(entity);
			 if(!dbreturn.equals(""))
			 {
				 System.out.print("1,["+ dbreturn+"],");
			 }
			 else
			 {
				 System.out.print("0,[],");
			 }
			 
			
			 Vector<String> p_categories=testDbpedia.isInWiki(entity);
			 if(!p_categories.isEmpty())
			 { System.out.print("1,[");
				
				 for( String s : p_categories)
				 {
					System.out.print(s +"-");
				 }
				 System.out.print("],");
			 }
			 else
			 {
				
				 System.out.print("0,[],");
			 }
			 
			System.out.println("");
			 
			 
	  }
	 
	 
	    }catch (Exception e){//Catch exception if any
	  System.err.println("Error: " + e.getMessage());
	  }
	 }
	
}

