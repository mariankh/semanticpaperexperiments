import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * Det: Determiners
PreDet: Pre-determiners (search for PreDet in data/wndict.lsp for instances)
PostDet: Post-determiners (search for PostDet in data/wndict.lsp for instances)
NUM: numbers
C: Clauses
I: Inflectional Phrases
V: Verb and Verb Phrases
N: Noun and Noun Phrases
NN: noun-noun modifiers
P: Preposition and Preposition Phrases
PpSpec: Specifiers of Preposition Phrases (search for PpSpec 
  in data/wndict.lsp for instances)
A: Adjective/Adverbs
Have: have
Aux:  Auxilary verbs, e.g. should, will, does, ...
Be:   Different forms of be: is, am, were, be, ... 
COMP:  Complementizer
VBE: be used as a linking verb. E.g., I am hungry
V_N	verbs with one argument (the subject), i.e., intransitive verbs
V_N_N verbs with two arguments, i.e., transitive verbs
V_N_I verbs taking small clause as complement*/


public class readMiniparResults {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		//read file line by line
		// on > ( new sentence on ) end of sentence
		// for each break on tab,
		// 0 number of word
		// 1 word 
		// 2 ~
		// 3 POS
		


		
	    BufferedReader br = new BufferedReader(new FileReader("results.txt"));
	    String line;
	    int word_count=0;
	    int word_count_sum=0;
	    int i=0;
	    String [] tokens = null ;
	    Map<String, Integer> POStags = new HashMap<String, Integer>();
	   while ((line = br.readLine()) != null) {
		//   System.out.println(line);
		  if (i<24);
		   if ( line.equals("> ("))  {
			   // initialize counters
			   word_count=0;POStags.clear();
		   }
		   if ((line.equals(")"))) //end print results
		   {
			   // keep coutners
			   if (! tokens[0].matches("^-?[0-9]+(\\.[0-9]+)?$")) {
				   word_count=Integer.parseInt(tokens[3]);
			   
			   }
			   else
				   word_count=Integer.parseInt(tokens[0]);
			   
			   System.out.print(word_count);
			   // print always in same format
			   /**
			    * PreDet=5, THAT=17, Subj=3, PostDet=3, PpSpec=2, Prep=342, A=729, have=12, As=4, C=2, SentAdjunct=17, 
			    * N=1494, Det=316, VBE=55, U=749, V=304, COMP=13, Aux=56, be=10}

			    */
			 //  System.out.println(POStags);
			   System.out.println( " " + POStags.get("PreDet")+ " " + POStags.get("THAT")+ " " + POStags.get("Subj")+ " " + POStags.get("PostDet")
					   + " " + POStags.get("PpSpec")+ " " + POStags.get("Prep")+ " " + POStags.get("A")+ " " + POStags.get("have")+ " " 
					   + POStags.get("As")+ " " + POStags.get("C")+ " " + POStags.get("SentAdjunct")+ " " + POStags.get("N")
					   + " " + POStags.get("Det")+ " " + POStags.get("VBE")+ " " + POStags.get("U")+ " " + POStags.get("V")
					   + " " + POStags.get("COMP")+ " " + POStags.get("Aux")+ " " + POStags.get("be")); 
		   }
		   else { //parse lines
			  tokens = line.split("\t");
			   
			 if (! tokens[0].matches("^-?[0-9]+(\\.[0-9]+)?$")) { // || ! tokens[3].matches("^-?[0-9]+(\\.[0-9]+)?$")
				   //we have an E something. that means that the next sentence must be parsed differently 
				
			 }
			
			  else
			  {
			
				//  System.out.print(tokens[0]+" ");
				// System.out.println(tokens[2].split(" ")[tokens[2].split(" ").length-1]);
				
				String key = tokens[2].split(" ")[tokens[2].split(" ").length-1];
				POStags.put(key, (POStags.get(key)==null ? new Integer(1): POStags.get(key).intValue()+new Integer(1)));
			  }
			 
		   }
			i++;
	   }
	  // System.out.println( "map = " + POStags );
	}

}
