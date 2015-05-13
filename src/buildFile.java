import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
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
 class Entityb{
	 public int id;
	 public String entity;
	 public Vector<String> categories = new Vector<String>();
	 public int relevant=0;
	 public	int accepted=0;
	 public	int text_review_id=0;
	 public String dbpdclass;
	 public int dbpedia=0;
	 public int wikipedia=0;
	 Vector<Annotationb> annotations = new Vector<Annotationb>();
	
}

class Category{
	public Category(String m) {
	this.name=m;
		// TODO Auto-generated constructor stub
	}
	public int id;
	public String name;
}

class Annotator{
	public int id;
	public String name;
	public int opensource=0;
	public double parameter1=0;
	public	double parameter2=0;
	int TP=0;
	int TN=0;
	int FP=0;
	int FN=0;
	public double precision=0;
	public double recall=0;
	public double accuracy=0;
}

class Annotationb{
	public int annotator_id;
	public double parameter1=0;
	public double parameter2=0;
	public double time=0;
}

class TextReview{
	public int id=0;
	public int dataset=0;
	public String text_review;
	public int no_words=0;
	public int item_id;
	public int no_spellings=0;
	public int sentiment=0; //1 positive, -1 negative, 0 neutral
	public Vector<Entityb> entities = new Vector<Entityb>();
	public ArrayList<String> misspelled =  new ArrayList();
}
class Item{
	public int item_id;
	public Dataset dataset;
	public ItemFeatures features;
}

class Dataset{
	public Domain domain;
	public int size;
}

class  ItemFeatures {

}

class Domain{
	public String name;
	public String context;
}

public class buildFile {

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
			System.out.println(category);
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
	public static Vector<Annotator> getParameters(String file, 	Vector<Annotator> annotators) throws IOException
	{
		FileInputStream fs= new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fs));
		String strLine;
		while (( strLine = br.readLine()) != null)   {
			String[] par_set = strLine.split(":");
			if(par_set[0].equals("parameters"))
			{
				String[] annotParam = par_set[1].split(",");
				int annot=0;
				for (Annotator a : annotators)
				{
					a.parameter1 =Double.parseDouble(annotParam[annot]);
					a.parameter2 = Double.parseDouble(annotParam[annot+1]);
					annot+=2;
					
				}
				
			}
		}
		return annotators;
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
			annotators.add(a);
		}
		annotators=getParameters(args[1],annotators);
		GetPreRecall.constractObjects(args[0], textreviews, datasets, annotators, annotations, entities);
		
		/*Question 1 : enties not recognised by each annotator per class + all per class that exist */
		/* format :
		 * annotator	class1	class2	class3	class4
			1	20	25	35	5
			2	3	18	28	20
			3	14	15	15	15
			entities available	20	80	40	20

		 */
		
		PrintWriter writerelevant = new PrintWriter("clases.relevant."+args[0], "UTF-8");
		PrintWriter writerelcate = new PrintWriter("categories.relevant."+args[0], "UTF-8");
		PrintWriter writenotrelcate = new PrintWriter("categories.notrelevant."+args[0], "UTF-8");
		PrintWriter writenotrelevant = new PrintWriter("clases.notrelevant."+args[0], "UTF-8");
		HashMap<String,HashMap<String, Integer>> relevant = new HashMap<String,HashMap<String, Integer>> ();
		HashMap<String,HashMap<String, Integer>> notrelevant = new HashMap<String,HashMap<String, Integer>> ();
		HashMap<String,HashMap<String, Integer>> catrel = new HashMap<String,HashMap<String, Integer>> ();
		HashMap<String,HashMap<String, Integer>> catnot = new HashMap<String,HashMap<String, Integer>> ();
		//key = dbclass - > (annotator, count)
		getClassesrecognised (textreviews,"relevant",relevant,annotators,writerelevant);
		getClassesrecognised (textreviews,"notrelevant",notrelevant,annotators,writenotrelevant);
		getCategoriesrecognised(textreviews,"relevant",catrel,annotators,writerelcate);
		getCategoriesrecognised(textreviews,"notrelevant",catnot,annotators,writenotrelcate);
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
							Annotator ar = annotators.get(a.annotator_id);
							if(ar.id==a.annotator_id) //just checking if we get the correct annotator id
							if(a.parameter1>= ar.parameter1 || a.parameter2>=ar.parameter2)
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

	
		private static void getClassesrecognised(Vector<TextReview> textreviews,
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
							Annotator ar = annotators.get(a.annotator_id);
							if(ar.id==a.annotator_id) //just checking if we get the correct annotator id
						
							if(a.parameter1>= ar.parameter1 || a.parameter2>=ar.parameter2)
							{
								relevant= addrowToAnsewers(relevant,""+a.annotator_id,e); //recognised by annotatorx
								count+=1;
								//System.out.println(a.parameter1);
							}
						}
						relevant= addrowToAnsewers(relevant,"entities",e);
						if(count==0)
						{
							relevant= addrowToAnsewers(relevant,"none",e);
						}
						else if (count==13)
						{
							relevant= addrowToAnsewers(relevant,"all",e);
						}
					}
				}
			}
			 
			/*for(Entry<String, HashMap<String, Integer>> entry : row.entrySet()){
				System.out.print("here"+entry.getKey()+",");
				for ( Entry<String, Integer> e : entry.getValue().entrySet())
				{
					System.out.print(e.getKey()+","+ e.getValue()+",");
				}
				System.out.println();
			}
			*/
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


	public static void main1(String args[])
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
	  int nochange=0;
	  int correctchange=0;
	  int wrongchange=0;
	  int trueClass=0;
	  int falsClass=0;
	  int relevantTRUE=0;
	  int relevantFALSE=0;
	  int classificationTRUE=0;
	  int classificationFALSE=0;
	  int TP=0, FP=0, TN = 0, FN=0;
	  int t1 = 0,t2=0,t3=0,t4=0,t5=0,t6=0,t7=0,t8=0,t9=0,t10=0,t11=0,t12=0,t13=0;
	  br.readLine();//header
	  int linecounter=0;
	  int existIndbpedia=0;
		 int notIndbpedia=0;
		 int wikiIs=0, wikiNot=0;
		 SortedMap<String, Integer> map = new TreeMap<String, Integer>();
		 SortedMap<String, Integer> wiki_categories_ident = new TreeMap<String, Integer>();
		 SortedMap<String, Integer> wiki_categories_notident= new TreeMap<String, Integer>();
		 
	System.out.println("dataset,review_id,entity,relevant,accepeted,entity_text,count,spellings,[misspelled_words],dbpedia,[dbcategory],[wiki?],[wikicategory],"
			+ "conf_1,conf_rel_1,conf_2,conf_rel_2,conf_3,conf_rel_3,conf_4,conf_rel_4,conf_5,conf_rel_5,conf_6,conf_rel_6,conf_7,conf_rel_7,conf_8,conf_rel_8,conf_9,conf_rel_9,conf_10,conf_rel_10,conf_11,conf_rel_11,conf_12,conf_rel_12,conf_13,conf_rel_13");
			
	  while ((strLine = br.readLine()) != null)   {
	  // Print the content on the console
		 String[] param = strLine.split(",");
		 String dataset=args[1];
		
		 String id=param[0];
		 String entity=param[1];
		
		 String text= getLine(dataset,Integer.parseInt(id));
		 String[] entity_entry = text.split(",");
		//System.out.print(strLine);
		
		 text=entity_entry[1];
		 
		 double conf_1=Double.parseDouble(param[2]);
		 double conf_rel_1=Double.parseDouble(param[3]);
		 double conf_2=Double.parseDouble(param[4]);
		 double conf_rel_2=Double.parseDouble(param[5]);
		 double conf_3=Double.parseDouble(param[6]);
		 double conf_rel_3=Double.parseDouble(param[7]);
		 double conf_4=Double.parseDouble(param[8]);
		 double conf_rel_4=Double.parseDouble(param[9]);
		 double conf_5=Double.parseDouble(param[10]);
		 double conf_rel_5=Double.parseDouble(param[11]);
		 double conf_6=Double.parseDouble(param[12]);
		 double conf_rel_6=Double.parseDouble(param[13]);
		 double conf_7=Double.parseDouble(param[14]);
		 double conf_rel_7=Double.parseDouble(param[15]);
		 double conf_8=Double.parseDouble(param[16]);
		 double conf_rel_8=Double.parseDouble(param[17]);
		 double conf_9=Double.parseDouble(param[18]);
		 double conf_rel_9=Double.parseDouble(param[19]);
		 double conf_10=Double.parseDouble(param[20]);
		 double conf_rel_10=Double.parseDouble(param[21]);
		 double conf_11=Double.parseDouble(param[22]);
		 double conf_rel_11=Double.parseDouble(param[23]);
		 double conf_12=Double.parseDouble(param[24]);
		 double conf_rel_12=Double.parseDouble(param[25]);
		 double conf_13=Double.parseDouble(param[26]);
		 double conf_rel_13=Double.parseDouble(param[27]);
		
		//System.out.println("start");
		 
		  //String tagger = param[3];
		 String relevant=param[28];
		 JazzySpellChecker jazzySpellChecker = new JazzySpellChecker();
		
		 List<String> misspelledwords = jazzySpellChecker.getMisspelledWords(text);
		  String misspellings=("[");
		   for (String m : misspelledwords)
		   {
			   misspellings+=(m+"-");
			   
		   }
		   misspellings+=("]");
		   
			 String dbreturn=testDbpedia.isInDbPedia(entity);
			 
			 String dbclass="";
			 if(!dbreturn.equals("")) dbclass+=("1,["+ dbreturn+"]");
			 else
				 dbclass+=("0,[]");
			
			 String wikicategories="";
			
			 Vector<String> p_categories=testDbpedia.isInWiki(entity);
			 
			 if(!p_categories.isEmpty())
			 { wikicategories+=("1,[");
				
				 for( String s : p_categories)
				 {
					 s=s.replace(",", "");
					 wikicategories+=(s +"-");
				 }
				 wikicategories+=("],");
			 }
			 else
			 {
				
				 wikicategories+=("0,[],");
			 }
			 String accepted="0";
		 System.out.println(""+dataset+","+id+","+entity+","+relevant+","+accepted+","+text+","+checkEntities.countWords(text)+","+jazzySpellChecker.countMisspelledWords(text)+","+
				 misspellings+","+dbclass+","+wikicategories
				 +""+conf_1+","+conf_rel_1+","+conf_2+","+conf_rel_2+","+conf_3+","+conf_rel_3+","+conf_4+","+conf_rel_4+","+
				 conf_5+","+conf_rel_5+","+conf_6+","+conf_rel_6+","+conf_7+","+conf_rel_7+","+conf_8+","+conf_rel_8+","+conf_9+","+
				 conf_rel_9+","+conf_10+","+conf_rel_10+","+conf_11+","+conf_rel_11+","+conf_12+","+conf_rel_12+","+conf_13
				 +","+conf_rel_13+"");
			
		 // if entity is recongised by only one tagger ++ the tagger
		 
		/*	 if( relevant.toLowerCase().equals(("true")))	
			 {
				 if (conf_1<=-1 && conf_2 <=-1 && conf_3<=-1 && conf_4 <=-1 && conf_5<=-1 && conf_6 <= -1 && conf_7<=-1 && conf_8 <=-1
				&& conf_9<=-1 && conf_10 <=-1 && conf_11<=-1 && conf_12 <=-1 && conf_13<=-1 )
				{t1++;
					if(param[0].equals("none of the above"))
					{
					}
				else
				System.out.println(param[0]+","+""+param[1]);
				}
			 }
			//System.out.println(t1);
		*/
		
	  }
	  } catch (Exception e)
	  	{System.out.println("an exception was thrown!" + e);
	  	}	
	  }
}