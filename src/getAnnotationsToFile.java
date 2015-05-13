/*
 * TODO fix space replace _
 * remove empty
 * 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.xml.sax.SAXException;

import com.textrazor.AnalysisException;
import com.textrazor.NetworkException;

import fr.eurecom.nerd.client.AlchemyAPIDriver;
import fr.eurecom.nerd.client.Dandelion;
import fr.eurecom.nerd.client.Lupedia;
import fr.eurecom.nerd.client.SpotlightJsonTest;
import fr.eurecom.nerd.client.THD;
import fr.eurecom.nerd.client.TagHTTP;
import fr.eurecom.nerd.client.TestTextRazor;
import fr.eurecom.nerd.client.WikipediaMiner;
import fr.eurecom.nerd.client.ZEMANTA;

/*
 * This is a new attempt to create a file that has the properties of 
 * dataset,review_id,entity,relevant,accepeted,entity_text,count,spellings,[misspelled_words],dbpedia,[dbcategory],[wiki?],[wiki_categories],conf1_rnf_1	conf_rel_1	conf_2	conf_rel_2	conf_3	conf_rel_3	conf_4	conf_rel_4	conf_5	conf_rel_5	conf_6	conf_rel_6	conf_7	conf_rel_7	conf_8	conf_rel_8	conf_9	conf_rel_9	conf_10	conf_rel_10	conf_11	conf_rel_11	conf_12	conf_rel_12	conf_13	conf_rel_13
 *
 * IMPORTANT - this program must have all the entities for a review to work correctly
 * 
 * input dataset; [text_id, text_entities, [annotations]
 * output dataset.filled
 */
public class getAnnotationsToFile {
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
	int linecounter=0;
	int existIndbpedia=0;
	int notIndbpedia=0;
	int wikiIs=0, wikiNot=0;
	SortedMap<String, Integer> map = new TreeMap<String, Integer>();
	SortedMap<String, Integer> wiki_categories_ident = new TreeMap<String, Integer>();
	SortedMap<String, Integer> wiki_categories_notident= new TreeMap<String, Integer>();
	Vector<TextReview> textreviews=new Vector<TextReview>();
	Vector<Dataset> datasets = new Vector<Dataset>();
	Vector <Entityb> entities = new Vector<Entityb>();
	Vector<Annotator> annotators = new Vector<Annotator>();
	Vector<Annotationb> annotations = new Vector<Annotationb>();
	
	public String getAnnotators(String dataset, String settings)
			throws IOException, AnalysisException, JSONException, 
			ParserConfigurationException, SAXException {
		
			String returndataset="_build";
		
		// TODO Auto-generated method stub;
		
		File file = new File(returndataset);
		
		String out = "";
		  //Read File Line By Line
		
		
			//fill up annotators
			fillupAnnotators();
			annotators=setParameters(settings,annotators);
			buildGolddataset(dataset);
			//start annotating text reviews
			System.out.println("start");
			annotateTextReviews();
			
			
	//	  System.out.println("review_id,entity_id,entity_text,relevant,accepeted,count,spellings,[misspelled_words],dbpedia,[dbcategory],[wiki?],[wikicategory],"
		//		+ "conf_1,conf_rel_1,conf_2,conf_rel_2,conf_3,conf_rel_3,conf_4,conf_rel_4,conf_5,conf_rel_5,conf_6,conf_rel_6,conf_7,conf_rel_7,conf_8,conf_rel_8,conf_9,conf_rel_9");
		 
		  for (TextReview t: textreviews)
		  {
			  for (Entityb e: t.entities)
			  { 	out+=(""+t.id+",");
			  		out+=(""+e.id+","+ e.entity+","+e.relevant+","+e.accepted+",");
			  		out+=(""+t.no_words+","+t.no_spellings+","+ArrayToString(t.misspelled)+",");
			  		out+=(""+e.wikipedia+","+VectorToString(e.categories)+","+e.dbpedia+","+e.dbpdclass+",");
				  
				  for (Annotator a : annotators)
				  {
					  System.out.println("annotator"+a.id +"dataset"+ out);
					  Annotationb an = getAnnotationOfAnnotator(e.annotations,a.id);
					  out+=(an.parameter1+","+an.parameter2+",");
				  }
				  out+="\n";  
			  }
			 
		  }
		  
		
		  try(PrintWriter pout = new PrintWriter(new BufferedWriter(new FileWriter(dataset+returndataset, true)))) {
			    pout.println(out);
			}catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}
		
		  return returndataset;
				
		
		
	}
	private  Annotationb getAnnotationOfAnnotator(Vector<Annotationb> annotations2,int id) {
	
		for(Annotationb b:annotations2)
		{
			if(b.annotator_id==id)
			{
				return b;
			}
		}
		Annotationb nu= new Annotationb();
		nu.parameter1=-1;
		nu.parameter2=-1;
		return nu;
		// TODO Auto-generated method stub
		
	}
	private String ArrayToString(ArrayList<String> array) {
		String string="[";
		for(String s : array)
		{
			string=string+s+"-";
		}
		string=string.substring(0, string.length()-1);
		string=string+ "]";	
		// TODO Auto-generated method stub
		return  string;
	}
	private String VectorToString(Vector<String> array) {
		String string="[";
		for(String s : array)
		{
			string=string+s+"-";
		}
		string=string.substring(0, string.length()-1);
		string=string+ "]";	
		// TODO Auto-generated method stub
		return  string;
	}
	private void annotateTextReviews() throws AnalysisException, IOException, JSONException, ParserConfigurationException, SAXException {
		//in this step for each of the text reviews - we get the responses from the annotators.
		// for each of the responses, we check if there is an entity for the text review, if it is, we add the parameters for the annotation
		// if is not, we create it and add the parameters for the annotator.

		String[] annotators_names = {"dbpediaspotlight","TextRazor","TagME","WM", "Lupedia","Dandelion","THD",
	            "Zemanta","AlchemyAPI"};
		for( TextReview t : textreviews)
		{
			
			
			ArrayList<String> dbpedia = SpotlightJsonTest.getEntitiesForText(t.text_review);
			ArrayList<String> TextRazor = TestTextRazor.getEntities(t.text_review);
			ArrayList<String> TagMe = TagHTTP.runTagME(t.text_review,0,0);
			ArrayList<String> WM = WikipediaMiner.runWikipediaMiner(t.text_review, 0.0);
			ArrayList<String> lupedia = Lupedia.runLupedia(t.text_review);
			ArrayList<String> dand = Dandelion.runDandelion(t.text_review);
			ArrayList<String> Thd = THD.runTHD(t.text_review);
			ArrayList<String> Zem = ZEMANTA.runZEMANTA(t.text_review);
			ArrayList<String> Alchemy = AlchemyAPIDriver.getAlchemyentities(t.text_review);
			
			ArrayList<ArrayList<String>> annotators_list= new ArrayList();
			annotators_list.add(dbpedia);
			annotators_list.add(TextRazor);
			annotators_list.add(dbpedia);
			annotators_list.add(WM);
			annotators_list.add(lupedia);
			annotators_list.add(dand);
			annotators_list.add(Thd);
			annotators_list.add(Zem);
			annotators_list.add(Alchemy);
			
			int annot_c=0;
			for (ArrayList<String> annotation :  annotators_list)
			{
				for (String a : annotation)
				{
					String entity_uri= (a.split("~"))[0];
					String[] temp = entity_uri.split("/");
					String entity_label =temp[temp.length-1];
					entity_label=entity_label.replace("%28", "(");
					entity_label=entity_label.replace("%29", ")");
					int entity_id= EntityInReview(entity_label,t);
					if((!entity_label.equals("")&&(!entity_label.equals(" "))&&(!entity_label.equals(null))))
						if((!t.equals("")&&(!t.equals(" "))&&(!t.equals(null))))
					{
					if (entity_id == -1)
					{
						entity_id= t.entities.size()==0?0:t.entities.size()+1;
						Entityb ent = new Entityb();
						ent.relevant=0;
						ent.text_review_id=t.id;
						ent.id=entity_id;
						ent.entity=entity_label;
						ent.wikipedia=!testDbpedia.isInWiki(entity_label).isEmpty()?1:0;
						ent.relevant=relevantToReview(ent.entity, t);
						ent.accepted=acceptedASrelevantToReview(ent.entity, t);
						ent.categories=testDbpedia.isInWiki(entity_label);
						ent.dbpedia=!(testDbpedia.isInDbPedia(entity_label).equals("no"))?1:0;
						ent.dbpdclass=testDbpedia.isInDbPedia(entity_label);
						Annotationb annotationb= new Annotationb();
						annotationb.annotator_id=annot_c;
						System.out.println(a +" " + annot_c);
						annotationb.parameter1=Double.parseDouble((a.split("~"))[1]);
						annotationb.parameter2=(a.split("~").length<3)?0:Double.parseDouble((a.split("~"))[2]);
						ent.annotations.add(annotationb);
						t.entities.addElement(ent);
					}
					else
					{
						Entityb ent = t.entities.get(entity_id);
						System.out.println("**got it" +ent.entity +" - "+ ent.id);
						Annotationb annotationb= new Annotationb();
						annotationb.annotator_id=annot_c;
						annotationb.parameter1=Double.parseDouble((a.split("~"))[1]);
						annotationb.parameter2=(a.split("~").length<3)?0:Double.parseDouble((a.split("~"))[2]);
						ent.annotations.add(annotationb);
					}
					}
				}
				annot_c++;	
			}
			System.out.println("everything works up to here");
		}
		
	}
	private int acceptedASrelevantToReview(String accepted_entity,TextReview t) {
		for(Entityb entity : t.entities)
		{
			if(entity!=null){
			if((entity.entity!=null))
				if(entity.entity.toLowerCase().equals(accepted_entity.toLowerCase()))
			//if(DbpediaSameAS.getSameAs(entity.entity).contains(accepted_entity))
				return 1;
			}
		}
		return 0;
	}
	
	private int relevantToReview(String accepted_entity,TextReview t) {
		for(Entityb entity : t.entities)
		{
			if(entity!=null){
			if((entity.entity!=null)){
			if(DbpediaSameAS.getSameAsRedirect(entity.entity)!=null)
			if(accepted_entity!=null)
			if(DbpediaSameAS.getSameAsRedirect(entity.entity).contains(accepted_entity))
				return 1;
			if(entity!=null) if(accepted_entity!=null)
			if((entity.entity!=null) )
			if (DbpediaSameAS.getSameAsRedirect(accepted_entity)!=null)
				if(DbpediaSameAS.getSameAsRedirect(accepted_entity).contains(entity.entity))
					return 1;
			}
			}
		}
		return 0;
	}
	
	
	private int EntityInReview(String entity_label, TextReview t) {
		int count=0;
		try{
		for (Entityb e: t.entities)
		{
			if(e!=null){
			if(e.entity.equals(entity_label))return count;
			if(e.entity!=null)
			if(DbpediaSameAS.getSameAsRedirect(e.entity)!=null) 
				if(entity_label!=null)
				if
					(DbpediaSameAS.getSameAsRedirect(e.entity).contains(entity_label))
				return count;
			if(entity_label!=null)
				if(e.entity!=null)
			if(DbpediaSameAS.getSameAsRedirect(entity_label)!=null)
				if
					(DbpediaSameAS.getSameAsRedirect(entity_label).contains(e.entity))
				return count;
			count++;
		}
		}
		}
		catch(Exception e)
		{
			System.out.println("exception catched");
		}
		return -1;
		
	}
	private void buildGolddataset(String dataset) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
 
		DataInputStream in = new DataInputStream( new FileInputStream(dataset));
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
		  String strLine;  
		  while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
			 String[] param = strLine.split(",");
			 String set = param[0];
			 String id=param[1];
			 String text=param[2];
			 int i=3;
			 Set<String> entities = new HashSet();
		//STEP1.1 ://get the entities from humans and correct them to dbpedia recourses -> create objects
			 while(i<param.length) {
				 if (!param[i].equals(""))
				 {
					String tempentity = (param[i].replace("]", ""));
					String[] temp=tempentity.split("/");
					if (tempentity.length()>4)
					tempentity = testDbpedia.getDbPediaEntityString(temp[temp.length-1]); 	
					if(tempentity!=null)
						if (tempentity.length()>2 && !tempentity.equals(""))
						entities.add(tempentity);
				 }
				 i++;
			 }
			 
			 //now we have the id, text review, and correct entities. add them into objects
			 TextReview text_review = new TextReview();
			 int prefix=0;
			 if(set.equals("a1.csv")) prefix=100000;
			 if(set.equals("r1.csv")) prefix=200000;
			if(set.equals("t1.csv")) prefix=300000;
			 if(set.equals("a2.csv")) prefix=110000;
			 if(set.equals("r2.csv")) prefix=220000;
			if(set.equals("t2.csv")) prefix=330000;
			 text_review.id=prefix + Integer.parseInt(id);
			 text_review.text_review=text;
			 String[] t = text_review.text_review.split(" ");
			 text_review.no_words=t.length;
			 JazzySpellChecker jazzy = new JazzySpellChecker();
			 text_review.misspelled = jazzy.getMisspelledWords(text);
			 text_review.no_spellings=text_review.misspelled.size();
			 int e_id=0;
			 for (String e : entities)
			 {
				 Entityb entity = new Entityb();
				 String[] temp = e.split("/");
				 e =temp[temp.length-1];
				e=e.replace("%28", "(");
				e=e.replace("%29", ")");
				 entity.entity=e;
				 entity.relevant=1 ; //golden dataset
				 entity.accepted=1 ; //golden dataset
				 entity.text_review_id=text_review.id;
				 entity.id=e_id;
				 entity.wikipedia=!testDbpedia.isInWiki(e).isEmpty()?1:0;
				entity.categories=testDbpedia.isInWiki(e);
				 entity.dbpedia=!(testDbpedia.isInDbPedia(e)==null)?1:0;
				 entity.dbpdclass=testDbpedia.isInDbPedia(e);
				 if(entity.wikipedia==1)
				 {
					 text_review.entities.add(entity);
					 e_id++;
				 }
			 }
			 textreviews.add(text_review); 
		  }
		
	}
	private Vector<Annotator> setParameters(String settings,
			Vector<Annotator> annotators2) throws NumberFormatException, IOException {
		
				FileInputStream fs= new FileInputStream(settings);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
				String strLine;
				while (( strLine = br.readLine()) != null)   {
					String[] par_set = strLine.split(":");
					if(par_set[0].equals("parameters"))
					{
						String[] annotParam = par_set[1].split(",");
						int annot=0;
						for (Annotator a : annotators2)
						{
							a.parameter1 =Double.parseDouble(annotParam[annot]);
							a.parameter2 = Double.parseDouble(annotParam[annot+1]);
							annot+=2;
							
						}
						
					}
				}
				return annotators2;
			}
	
	private void fillupAnnotators() {
		// TODO Auto-generated method stub
		String[] annotators_names = {"dbpediaspotlight","TextRazor","TagME","WM", "Lupedia","Dandelion","THD",
		                             "Zemanta","AlchemyAPI"};
		for (int i=0; i <9; i++)
		{
			Annotator a = new Annotator();
			a.id=i;
			a.name=annotators_names[i];
			annotators.add(a);
		}
	}

}
