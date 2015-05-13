import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import fr.eurecom.nerd.client.Reviews;
//**important: all files must be the correct number of lines***
//dataset and then annotator files before tuning!
//entity, annotator1Score1, anootator1sccore2 .. . . . .. . Relevant[yes,no]
/*
 * step1: read dataset file
	 	entity, relevant
		x, yes
 * step2 : read annotator1 
 * 		entity, annotator1, relevant
 * 		x	,	0.06		, yes
 * 		x3	,	994			, no
 * 
 */

class Reviews2 { 
	String id;
	public Set<Entity> entities;
	public Reviews2(String string, Set<Entity> availentities) {
		this.id=id;
		this.entities = availentities;
	}
}
	
class Annotation{
	double confidence=-1; //-1 shows that the entity was not there or the score was not available
	double relevance=-1;//-1 shows that the entity was not there or the score was not available
	int tagger=0;
	public Annotation(int tagger2, double conf, double rel) {
		// TODO Auto-generated constructor stub
		
		tagger=tagger2;
		confidence=conf;
		relevance=rel;
	}

}
class Entity {
	String name;
	Set <Annotation> annot;
	boolean relevant =false; // if it is in the gold standard
	double confidence=-1; //-1 shows that the entity was not there or the score was not available
	double relevance=-1;//-1 shows that the entity was not there or the score was not available
	String review_id ="";
	public Entity(String review_id, String name,double conf, double rel,boolean rele) {
		this.name=name;
		this.review_id=review_id;
		relevant=rele;
		confidence=conf;//to be used only by taggers for temporary storage
		relevance=rel; //to be used only by taggers for temporary storage
		annot= new HashSet();
	}
	 public void addAnnot( int tagger, double conf,double rel)
	 {
			 annot.add(new Annotation(tagger, conf, rel));
	 }
	
}

public class BuildWekaFile {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Reviews2> all_entities_per_review_d = new ArrayList<Reviews2>();
		ArrayList<Reviews2> all_entities_per_review_t = new ArrayList<Reviews2>();
		all_entities_per_review_d = readFileIntoList(args[0],all_entities_per_review_d, true);
			System.out.print("review_id,entity,conf_1,conf_rel_1,conf_2,conf_rel_2,conf_3,conf_rel_3,conf_4,conf_rel_4,conf_5,conf_rel_5,conf_6,conf_rel_6,conf_7,conf_rel_7,conf_8,conf_rel_8,conf_9,conf_rel_9,conf_10,conf_rel_10,conf_11,conf_rel_11,conf_12,conf_rel_12,conf_13,conf_rel_13,relevant");
				
		
	System.out.println();
		all_entities_per_review_d = addAnnotationstoDataset(all_entities_per_review_d,args[0],0);
		
		for (int i=1; i<args.length; i++)
		{
			all_entities_per_review_d = addAnnotationstoDataset(all_entities_per_review_d,args[i],i);
				
		}
	
		for (int i=00; i<all_entities_per_review_d.size(); i++) /* number of reviews*/ // for each review
		{
			for (Entity d : all_entities_per_review_d.get(i).entities)
			{
				int re=i+1;
				System.out.print(""+re+","+d.name+",");
				
				Annotation[] sorted_annots =sortAnnotations(d.annot);
				
				for (int j=1; j<sorted_annots.length; j++)
				{
					if(sorted_annots[j]!=null)
						System.out.print(sorted_annots[j].confidence + ","+ sorted_annots[j].relevance +",");	
					else
						throw new Exception("muahahaha"+j +" " + sorted_annots[j]);//System.out.print(sorted_annots[j].confidence + ","+ sorted_annots[j].relevance+ "," +"annot:"+sorted_annots[j].tagger);	
				}
				System.out.println(d.relevant);
			
			
			}
		}

		
	}


	private static Annotation[] sortAnnotations(Set<Annotation> annot) {
		Annotation[] sorted = new Annotation[14];
		for (int i=1; i<14; i++)
		{
			boolean found=false;
			for (Annotation a: annot)
			{
				if(a.tagger==i)
				{
					sorted[i]=a;
					found=true;
					break;
				}
			}
			if(!found){ // add-1
				{
				sorted[i]= new Annotation(i,-1,-1)	;
				}
			}
		}
		
		return sorted;
	}


	private static ArrayList<Reviews2> addAnnotationstoDataset(
			ArrayList<Reviews2> all_entities_per_review_d, String string, int id) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<Reviews2> all_entities_per_review_t = new ArrayList<Reviews2>();
		all_entities_per_review_t = readFileIntoList(string,all_entities_per_review_t, false);
		//Set<Entity> dataset = null ;
		Set<Entity> tagger = null;
		for (int i=0; i<all_entities_per_review_t.size(); i++) /* number of reviews*/ // for each review
		{
			//dataset = all_entities_per_review_d.get(i).entities; //get dataset entities
			tagger = all_entities_per_review_t.get(i).entities;  //get annotator entities
																 //if is there- in the dataset. --add tagger relevance and confidence values.
		
		/*Precision= true positive (correctly guessed) vs all_guessed
			recall= true positive vs all_possitive (all_available) */

			for(Entity s : tagger)
			{
				boolean in_tagger=false;
				//System.out.println(s.name);
				
	        	if(id>0)
	        	{
	           	boolean found = false;
				for (Entity d :  all_entities_per_review_d.get(i).entities) //in both
	        	{	//System.out.println(d.name);
	        		
		        	
		        	s.name=formatstring(s.name);
		        	d.name=formatstring(d.name);
		        //	d.relevant=false;
		        	
		//        		System.out.println("0000000000"+d.name+" " +s.name);
		        	if(d.name.equals("melodrama")) System.exit(1);
	        		if (d.name.equals(s.name))
	        		{
	        		in_tagger=true;
	        		found=true;
	        		d.addAnnot(id, s.confidence ,s.relevance);
	        		//all_entities_per_review_d.get(i).entities.add(d);
					break;
	        		}
		        }
		        	
				
				if(found!=true){ // means that is not in the golden dataset. add it with relevant = false.
					Entity add_on = new Entity(""+i,s.name, 0,0,false);
					add_on.addAnnot(id,s.confidence, s.relevance);
					/*while(add_on.annot.size()<id)
					{	
					
						if
						add_on.addAnnot(add_on.annot.size(),-1,-1);
						
						
					}*/
					
					all_entities_per_review_d.get(i).entities.add(add_on);
					
				}
	        	
	        	/*else {
	        		Entity add_on = new Entity(s.name, 0,0,true);
	        		while(add_on.annot.size()<=id)
					add_on.addAnnot(id,-1,-1);
					all_entities_per_review_d.get(i).entities.add(add_on);
	        	}*/
				
	        	}
				

			}
		
		}
		/*
		for (int i=0; i< 100; i++) // for each review
		{
			for (Entity d :  all_entities_per_review_d.get(i).entities) //in dataset but not in annotator
			{
				while(d.annot.size()<id)
				{	
					
					d.addAnnot(d.annot.size(),-1,-1);
				}
			}
		}
		*/
		return all_entities_per_review_d;

	}




	private static ArrayList<Reviews2> readFileIntoList(String string, ArrayList<Reviews2> all_entities_per_review_d, boolean relevant) throws IOException {
		BufferedReader d_reader = new BufferedReader(new FileReader(string));
		 Set<String> str_entities;
		 String line;
		 while ((line = d_reader.readLine()) != null) {
				String[] elements = line.split(",");
				 Set<Entity> availentities = new HashSet<Entity>();	
				 
			
			for (int i=2; i< elements.length; i++)
			{
				double conf =0;
				double rel =0;
			//	System.out.println( ele.ments[i]);
				if(elements[i].length()>2)
				{
					String[] entity_conf = elements[i].split("~");
					if(entity_conf.length>2)
					{
						conf= Double.parseDouble(entity_conf[1]);
						rel= Double.parseDouble(entity_conf[2]);
				//	System.out.println("here1" +entity_conf[0]+" "+ entity_conf[1]+" "+ entity_conf[2]+" " );
					}
					else if(entity_conf.length>1)
					{
						conf= Double.parseDouble(entity_conf[1]);
				//	System.out.println("here2" + entity_conf[0]+" "+ entity_conf[1]+" " );
					}
				
				String[] segments = entity_conf[0].split("/");
				String idStr = segments[segments.length-1];
				// String str= idStr.toLowerCase();
				String str= idStr;
				// if(entity_conf[0].indexOf("/")<0)
					//System.out.println(entity_conf[0]+"***********"+relevant);
				if(!str.equals(""))
				{
				
			        	//System.out.println(str);
				 //str = str.charAt(str.length()-1)==('_') ? str.substring(0,str.length()-1):  str;
				// System.out.println(str+"_");
			//	availentities.add(new Entity(str,conf,rel, relevant));
				 availentities.add(new Entity(""+i, formatstring(str),conf,rel, relevant));
				}
				
				else
				conf =0;
				rel =0;
				}
			}
			all_entities_per_review_d.add(new Reviews2(elements[0], availentities));
		 }
		return all_entities_per_review_d;
	}

	
	public static String formatstring(String in){
	  StringBuilder sb = new StringBuilder();
	  boolean capitalizeNext = false;
	   in = in.substring(0, 1).toUpperCase() + in.substring(1);
	in=in.replace("%28", "");
	in=in.replace("%29", "");
	in=in.replace("‎", "");
	if (in.substring(0, 1).equals("_"))
		in=in.substring(1);
  	
  	in=in.trim();
	  
	  for (char c:in.toCharArray()) {
	    if (c == '_') {
	      capitalizeNext = true;
	    } else {
	      if (capitalizeNext) {
	        sb.append("_"+ Character.toUpperCase(c));
	        capitalizeNext = false;
	      } else {
	        sb.append(c);
	      }
	    }
	  }
	  
	  return sb.toString();
	}
		
}

