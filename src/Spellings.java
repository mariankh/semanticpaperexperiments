import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

import com.fasterxml.jackson.databind.util.Annotations;


//run this instead of get non recognised entities
//run this using wekafiles
//wekaa11.csv and a1 for example

class SpellingPerReview
{
	int text_review_id;
	int text_words;
	int text_spelings;
	Vector<Annotator> annotators = new Vector<Annotator>();
}
 
public class Spellings {

	//we need to create our objects to return the output files of answering each of the questions.
	
	public static String getLine(String file, int line) throws IOException
	{
		FileInputStream fs= new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fs));
		for(int i = 0; i < line; ++i)
		  br.readLine();
		String lineIWant = br.readLine();
		return lineIWant;
	}
	private static HashMap<String, HashMap<String, Integer>> addCatToAnsewers(HashMap<String,HashMap<String, Integer>> row 
			, String key, Entityb e) {
		for(String category1 : e.categories)
		{	
			String category=category1;
			//System.out.println(category);
			if(row.get(category)!=null && row.get(category).get(key)!=null )
		{	
			row.get(category).put(key, row.get(category).get(key)+1);
		}
	else
		if(row.get(category)!=null)
		{
			row.get(category).put(key,1);
		}
		else
		{
			HashMap<String,Integer> n = new HashMap<String,Integer>();
			n.put(key,1) ;
			row.put(category,n );
		}
	
	}
		return row;	
	}

public static HashMap<String,HashMap<String, Integer>>  addEntityToAnsewers(HashMap<String,HashMap<String, Integer>> row , String key, Entityb e)
	
	{	if(row.get(e.entity)!=null && row.get(e.entity).get(key)!=null )
		{	
			row.get(e.entity).put(key, row.get(e.entity).get(key)+1);
		}
	else
		if(row.get(e.entity)!=null)
		{
			row.get(e.entity).put(key,1);
		}
		else
		{
			HashMap<String,Integer> n = new HashMap<String,Integer>();
			n.put(key,1) ;
			row.put(e.entity,n );
		}
	return row;
	}

	public static HashMap<String,HashMap<String, Integer>>  addrowToAnsewers(HashMap<String,HashMap<String, Integer>> row , String key, Entityb e)
	
	{	if(row.get(e.dbpdclass)!=null && row.get(e.dbpdclass).get(key)!=null )
		{	
			row.get(e.dbpdclass).put(key, row.get(e.dbpdclass).get(key)+1);
		}
	else
		if(row.get(e.dbpdclass)!=null)
		{
			row.get(e.dbpdclass).put(key,1);
		}
		else
		{
			HashMap<String,Integer> n = new HashMap<String,Integer>();
			n.put(key,1) ;
			row.put(e.dbpdclass,n );
		}
	return row;
	}
	public static void main(String args[]) throws IOException{
		Vector<TextReview> textreviews=new Vector<TextReview>();
		 Vector<Dataset> datasets = new Vector<Dataset>();
		 Vector <Entityb> entities = new Vector<Entityb>();
		Vector<Annotator> annotators = new Vector<Annotator>();
		Vector<Annotationb> annotations = new Vector<Annotationb>();
		//fill up annotators
		for (int i=0; i <9; i++)
		{
			Annotator a = new Annotator();
			a.id=i;
			a.FN=0; a.FP=0;a.TP=0;a.TN=0;
			annotators.add(a);
		}
		GetPreRecall.constractObjects(args[0], textreviews, datasets, annotators, annotations, entities);
		
		/*Question 1 : enties not recognised by each annotator per class + all per class that exist */
		/* format :
		 * annotator	class1	class2	class3	class4
			1	20	25	35	5
			2	3	18	28	20
			3	14	15	15	15
			entities available	20	80	40	20

		 */
		
		PrintWriter writerecognised = new PrintWriter("spellings.entities.recognised.relevant."+args[0], "UTF-8");
		PrintWriter writenotrecognised = new PrintWriter("spellings.entities.notrecognised.relevant"+args[0], "UTF-8");
		PrintWriter recnotrelevant = new PrintWriter("spellings.entities.recognised.notrelevant."+args[0], "UTF-8");
		PrintWriter notrecnotrelevant = new PrintWriter("spellings.entities.notrecognised.notrelevant"+args[0], "UTF-8");
		HashMap<String,HashMap<String, Integer>> relevant = new HashMap<String,HashMap<String, Integer>> ();
		getSpellingsPerEntity (textreviews,"relevant",relevant,annotators,writerecognised,writenotrecognised);
		//getSpellingsPerEntity (textreviews,"notrelevant",relevant,annotators,recnotrelevant,notrecnotrelevant);
		
	}
		/*Question2:
		 * get classes and categories of those recognized per annotator
		 *  [id, entity, class, annotator] recognised.classes.dataset
		 *  [id, entity, category, annotator]recognised.categories.dataset
		 */
		
		/*Question3:
		 * get spellings of not recognized and recongized
		 *  [spellings line by line] spellings.notrecongised
		 *  [id,no_of_spellings]nospellings.notrecognised
		 *  [spellings line by line]speelings.recognised
		 *  [id,no_of_spellings] nospellings.notrecognised
		 */
	
		/*Question4:
		 * get
		 * 	
		 */
	
	private static void getSpellingsPerEntity(Vector<TextReview> textreviews,
			String string, HashMap<String, HashMap<String, Integer>> relevant,
			Vector<Annotator> annotators,
			PrintWriter writerecognised,PrintWriter writenotrecognised) {
		/*F P  is the set of irrelevant pairs in
		T S , in other words the pairs in T S that do not match the pairs in
		GS .
		F N is the set of false negatives denoting the pairs that are not recognised by
		T S , yet appear in GS
		*/
		int FP=0;// irrelevant
		int TP=0;
		int FN=0; // not recognised but are relevant
		Vector<SpellingPerReview> spellingsreviews= new Vector<SpellingPerReview>();
		for (TextReview t : textreviews )
		{
			SpellingPerReview sr = new SpellingPerReview();
			int entnotrecognised=0;
			int recognised=0;
			Annotator ar = null;
			sr.text_words=t.no_words;
			sr.text_spelings=t.no_spellings;
			for(Entityb e : t.entities)
			{
				int condition=(string.equals("relevant")?1:0);
				
						int count = 0;
						
						for (Annotationb a : e.annotations)
						{
							 ar = annotators.get(a.annotator_id);
							 if (e.relevant>0)
							{
							
							if (a.parameter1>=ar.parameter1 && a.parameter2>=ar.parameter2)
							
							{
								annotators.get(a.annotator_id).TP++;
								
							}
							else annotators.get(a.annotator_id).FN++;
							
							}
							 else
								 annotators.get(a.annotator_id).FP++;
						}
						
				
					
					
			}
			System.out.print(t.id+","+t.no_words+","+t.no_spellings+","+t.entities.size()+",");
			for (int i=0; i<annotators.size(); i++)
			{
				Annotator annot=annotators.get(i);
				//System.out.println(i);
				System.out.print(annot.TP+",");
			}
			for (int i=0; i<annotators.size(); i++)
			{
				Annotator annot=annotators.get(i);
				//System.out.println(i);
				System.out.print(annot.FP+",");
			}
			for (int i=0; i<annotators.size(); i++)
			{
				Annotator annot=annotators.get(i);
				//System.out.println(i);
				System.out.print(annot.FN+",");
			}
			System.out.println("");
			for (int i=0; i<annotators.size(); i++)
			{
				annotators.get(i).TP=0;
				annotators.get(i).FP=0;
				annotators.get(i).TN=0;
				annotators.get(i).FN=0;
				//System.out.println(i);
			
			}
			
			
		
			
		}
		// TODO Auto-generated method stub
		//writenotrecognised.close();
		writerecognised.close();
	}
	private static void getCategoriesrecognised(Vector<TextReview> textreviews,
		String string, HashMap<String, HashMap<String, Integer>> relevant,
		Vector<Annotator> annotators, PrintWriter writerrelevantclasses) {
	// TODO Auto-generated method stub
	
		for (TextReview t : textreviews )
		{
			for(Entityb e : t.entities)
			{
				int condition=(string.equals("relevant")?1:0);
				
					if(e.relevant==condition)
				{
					double count=0;
					for (Annotationb a : e.annotations)
					{
						//System.out.println(e.entity + " " + a.parameter1);
						if(a.parameter1>0)
						{
							relevant= addCatToAnsewers(relevant,""+a.annotator_id,e); //recognised by annotatorx
							count+=1;
							//System.out.println(a.parameter1);
						}
					}
					relevant= addCatToAnsewers(relevant,"entities",e);
					if(count==0)
					{
						relevant= addCatToAnsewers(relevant,"none",e);
					}
					else if (count==13)
					{
						relevant= addCatToAnsewers(relevant,"all",e);
					}
				}
			}
		}
		for(Entry<String, HashMap<String, Integer>> entry : relevant.entrySet()){
			writerrelevantclasses.print( "," + entry.getKey());
		}
		writerrelevantclasses.println();
		for (Annotator a : annotators)
		{
			writerrelevantclasses.print(a.id);
			
			for(Entry<String, HashMap<String, Integer>> entry : relevant.entrySet()){
				//System.out.println(a.id);
				writerrelevantclasses.print( "," + entry.getValue().get(""+a.id));
		}
			writerrelevantclasses.println();
		
		}
		writerrelevantclasses.print("entities");
		
		for(Entry<String, HashMap<String, Integer>> entry : relevant.entrySet()){
			writerrelevantclasses.print( "," + entry.getValue().get("entities"));
		}
		writerrelevantclasses.println();
		writerrelevantclasses.print("all");
		
		for(Entry<String, HashMap<String, Integer>> entry : relevant.entrySet()){
			writerrelevantclasses.print( "," + entry.getValue().get("all"));
		}
		writerrelevantclasses.println();
		writerrelevantclasses.print("none");
		
		for(Entry<String, HashMap<String, Integer>> entry : relevant.entrySet()){
			writerrelevantclasses.print( "," + entry.getValue().get("none"));
		}		
		writerrelevantclasses.close();
}

	private static int ReviewInVector(String string,
			Vector<TextReview> textreviews) {
	int count=0;
		for(TextReview t : textreviews){
			if (t.text_review.equals(string))
				return count;
			count++;
		}
		return -1;
	}


}