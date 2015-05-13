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


//run this instead of get non recognised entities
//run this using wekafiles
//wekaa11.csv and a1 for example
/*
 * What is the reason for the low precision and recall?

Is the low precision and recall related to spelling mistakes in text reviews?
there is a correlation between spelling mistakes in text reviews and the precision and / or recall (testing this now...)

i need spelling mistakes per review,
I need number of words per review
I need  FP per review
I need FN per review
I need TP per review

then sort based on spellings/word
/*F P  is the set of irrelevant pairs in
T S , in other words the pairs in T S that do not match the pairs in
GS .
F N is the set of false negatives denoting the pairs that are not recognised by
T S , yet appear in GS
*/
 
class Filterout1 {
	
	public boolean spellings=false;
	
	public Vector<String> entities= new Vector<String>();
	public Vector<String> classes= new Vector<String>();
	public Vector<String> categories= new Vector<String>();
	 public Filterout1 (String file) throws IOException
	 {
		 FileInputStream fs= new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fs));
		String strLine;
		while (( strLine = br.readLine()) != null)   {
			String[] par_set = strLine.split(":");
			if(par_set[0].equals("entities"))
			{
				String[] entities = par_set[1].split(",");
				for (String e : entities)
					this.entities.add(e);
			}
			if(par_set[0].equals("classes"))
			{
				String[] classes = par_set[1].split(",");
				for (String e : classes)
					this.classes.add(e);
			}
			if(par_set[0].equals("categories"))
			{
				String[] categories = par_set[1].split(",");
				for (String e : categories)
					this.categories.add(e);
			}
			if(par_set[0].equals("spellings"))
			{
				if (par_set[1].equals("true"))
					spellings=true;
				if (par_set[1].equals("false"))
					spellings=false;
			}
		}
		
	 }
}
public class GetSpellings { // +filterout

	/*
	 * input : filter_settings.txt
		parameters:n1p1, n1p2 ..nNp1,nNp2 
		entities:a,.,.,N
		classes:a,.,.,N
		categories:a,.,.,N
		spellings:true/false
	 */
	public static String getLine(String file, int line) throws IOException
	{
		FileInputStream fs= new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fs));
		for(int i = 0; i < line; ++i)
		  br.readLine();
		String lineIWant = br.readLine();
		return lineIWant;
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
		//JazzySpellChecker jazzy = new JazzySpellChecker();
		Filterout1 filterout = new Filterout1(args[1]);
		constractObjects(args[0], textreviews, datasets, annotators, annotations, entities);
		
		/*Question 1 : enties not recognised by each annotator per class + all per class that exist */
		/* format :
		 * annotator	class1	class2	class3	class4
			1	20	25	35	5
			2	3	18	28	20
			3	14	15	15	15
			entities available	20	80	40	20

		 */
		
		PrintWriter writeresults = new PrintWriter("p.r."+args[0], "UTF-8");
		
		for (TextReview t : textreviews)
		{		
			for(Entityb e : t.entities)
			{
				for (Annotationb a : e.annotations)
				{
					
					Annotator ar = annotators.get(a.annotator_id);
					if(ar.id==0)System.out.println("dbpedia)"+ar.parameter1+a.parameter1);
					if(ar.id==a.annotator_id) //just checking if we get the correct annotator id
					{
						/*F P  is the set of irrelevant pairs in
T S , in other words the pairs in T S that do not match the pairs in
GS .
F N is the set of false negatives denoting the pairs that are not recognised by
T S , yet appear in GS
*/
						
						if(e.relevant>0)
						{
							
							if (a.parameter1>=ar.parameter1 && a.parameter2>=ar.parameter2)
							{
								for (String c  : e.categories)
								{
									if( filterout.categories.contains(c )) { ar.FN++; break;}
								}
								
								if(filterout.classes.contains(e.dbpdclass) || filterout.entities.contains(e.entity))
								{
									ar.FN++; break;
								}
								
								ar.TP++;
								
								
							}
							else
								ar.FN++;
						}
						else
						{
							if (a.parameter1>=ar.parameter1 && a.parameter2>=ar.parameter2)
								{
								
								for (String c  : e.categories)
								{
									if( filterout.categories.contains(c )) { ar.TN++; break;}
								}
								
								if(filterout.classes.contains(e.dbpdclass) || filterout.entities.contains(e.entity))
								{
									ar.TN++; break;
								}
								
								ar.FP++;
								}
								else 
									ar.TN++;
							
						}
					}
					
				}
			}
		}
		
		for(Annotator a: annotators)
		{
			double precision=0, recall=0, accuracy=10;
			double div = 0;
		try{	 div = (a.TP + a.FP); a.precision= a.TP / div; } catch (Exception e) { a.precision=0.0;}
			
		try{ div = (a.TP + a.FN);	a.recall = a.TP / div; } catch (Exception e) { a.recall=0.0;}
		try{ div = (a.TP +a.FP +a.TN +a.FN);		a.accuracy= (a.TN + a.TP) / div; } catch (Exception e) {a.accuracy=0.0;}
		
		System.out.println(a.id+" "+a.TP+" "+a.FP+" "+a.TN+" "+a.FN+" "+a.precision+" "+a.recall+" "+a.accuracy);
		//System.out.println(a.precision+" "+ a.recall);
		}
		
	writeresults.close();
	}

	static void constractObjects(String files, Vector<TextReview> textreviews,
			Vector<Dataset> datasets, Vector <Annotator> annotators,
			Vector <Annotationb> annotations,Vector <Entityb> entities ) throws IOException {
		 FileInputStream fstream = new FileInputStream(files);
		  // Get the object of DataInputStream
		  DataInputStream in = new DataInputStream(fstream);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
	
		 String strLine= ""; //title
		 int id=0;
		 
		 
		while ((strLine = br.readLine()) != null)   {
			 String[] columns = strLine.split(",");
			 if (columns.length<1) break;
			 Entityb entity = new Entityb();

			// System.out.println(strLine);
			 entity.text_review_id=Integer.parseInt(columns[0]);
			 entity.id= Integer.parseInt(columns[1]);
			 entity.entity=columns[2];
			 
			 
			 entity.relevant=0;
			 if(columns[3].equals("1"))
			 {
				// System.out.println(entity.entity + " " + columns[3]);
				 entity.relevant=1;
			 }
			 entity.accepted=0;
			 if(columns[4].equals("1"))
				 entity.accepted=1;
			 
			 //check if not already in vector
			 if (ReviewInVector(columns[0],textreviews)<0)
			 {
				 TextReview review= new TextReview();
					 
				 review.id=Integer.parseInt(columns[0]);
				
				 review.no_words=Integer.parseInt(columns[5]);
				 
				 review.no_spellings=Integer.parseInt(columns[6]);
				 String[] misspelled = new String[200];
				 misspelled= columns[7].split("-|\\[|\\]");
				
				 for (String m : misspelled)
				 {
					 review.misspelled.add(m);
				 }
				 textreviews.add(review);
			 }
			 //System.out.println(ReviewInVector(columns[5],textreviews));
			TextReview review =textreviews.get(ReviewInVector(columns[0],textreviews));
			 
			 entity.dbpedia= Integer.parseInt(columns[10]);
			 entity.dbpdclass=columns[11];
			 entity.wikipedia=Integer.parseInt(columns[8]);
			 String[] wikicategories = new String[11];
			 wikicategories= columns[9].split("-|\\[|\\]");
			 for (String m : wikicategories)
			 {
				m=m.replace("\"", "-");
				m=m.replace("-- class=--extiw", "");
				m=m.replace("-", "");
				if(m.length()>3)
				 entity.categories.add(m);
			 }
			int step=12;
			 for(Annotator a : annotators)
			 {
			
				 Annotationb annot= new Annotationb();
				annot.annotator_id=a.id;
				annot.parameter1= Double.parseDouble(columns[step]);
				annot.parameter2= Double.parseDouble(columns[step+1]);
				entity.annotations.add(annot);
				step=step+2;
				//System.out.println(annot.parameter1);
			 }
			entities.add(entity);
			review.entities.add(entity);
			
		 }
	}
	private static int ReviewInVector(String id,
			Vector<TextReview> textreviews) {
	int count=0;
		for(TextReview t : textreviews){
			if (t.id == Integer.parseInt(id))
				return count;
			count++;
		}
		return -1;
	}
}