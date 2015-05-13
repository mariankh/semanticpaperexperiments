import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

//pianei to dataset
//check on confidence-relevance je based on set of rules + make prediction
//entity(string) conf (double)=0, relevance(boolean), predicted(boolean), after classification(boolean), gold(boolean)
// if predicted=true je gold= false je classification=false +1 chancetoFalse
public class AnalyzeResults {

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
	  while ((strLine = br.readLine()) != null)   {
	  // Print the content on the console
		 String[] param = strLine.split(",");
		 String entity=param[0];
		 double conf_1=Double.parseDouble(param[1]);
		 double conf_rel_1=Double.parseDouble(param[2]);
		 double conf_2=Double.parseDouble(param[3]);
		 double conf_rel_2=Double.parseDouble(param[4]);
		 double conf_3=Double.parseDouble(param[5]);
		 double conf_rel_3=Double.parseDouble(param[6]);
		 double conf_4=Double.parseDouble(param[7]);
		 double conf_rel_4=Double.parseDouble(param[8]);
		 double conf_5=Double.parseDouble(param[9]);
		 double conf_rel_5=Double.parseDouble(param[10]);
		 double conf_6=Double.parseDouble(param[11]);
		 double conf_rel_6=Double.parseDouble(param[12]);
		 double conf_7=Double.parseDouble(param[13]);
		 double conf_rel_7=Double.parseDouble(param[14]);
		 double conf_8=Double.parseDouble(param[15]);
		 double conf_rel_8=Double.parseDouble(param[16]);
		 double conf_9=Double.parseDouble(param[17]);
		 double conf_rel_9=Double.parseDouble(param[18]);
		 double conf_10=Double.parseDouble(param[19]);
		 double conf_rel_10=Double.parseDouble(param[20]);
		 double conf_11=Double.parseDouble(param[21]);
		 double conf_rel_11=Double.parseDouble(param[22]);
		 double conf_12=Double.parseDouble(param[23]);
		 double conf_rel_12=Double.parseDouble(param[24]);
		 double conf_13=Double.parseDouble(param[25]);
		 double conf_rel_13=Double.parseDouble(param[26]);
		
		//System.out.println("start");
		 
		  String tagger = param[3];
		 String relevant=param[27];
		 
		 // if entity is recongised by only one tagger ++ the tagger
		 
		 
		 if( relevant.toLowerCase().equals(("true")))	
		 {
			 if (conf_1<=-1 && conf_2 <=-1 && conf_3<=-1 && conf_4 <=-1 && conf_5<=-1 && conf_6 <= -1 && conf_7<=-1 && conf_8 <=-1
			&& conf_9<=-1 && conf_10 <=-1 && conf_11<=-1 && conf_12 <=-1 && conf_13<=-1 )
			{t1++;
			// System.out.println(param[0]+" "+ testDbpedia.isInDbPedia("http://dbpedia.org/page/"+param[0]));
			 String dbreturn=testDbpedia.isInDbPedia(param[0]);
			 if(!dbreturn.equals(""))
			 {
				 existIndbpedia++;
				 System.out.println(dbreturn);
				 if (map.containsKey(dbreturn)) {
			            int i = map.get(dbreturn);
			            i++;
			            map.put(dbreturn, i);
			            //System.out.println("increment");
			        }
			        else {
			            map.put(dbreturn, 1);
			        }
			 }
			 else
				 notIndbpedia++;

			 Vector<String> p_categories=testDbpedia.isInWiki(entity);
			 if(!p_categories.isEmpty())
			 {
				 wikiIs++;
				 for( String s : p_categories)
				 {
				 if (wiki_categories_notident.containsKey(s)) {
			            int i = wiki_categories_notident.get(s);
			            i++;
			            wiki_categories_notident.put(s, i);
			            //System.out.println("increment");
			        }
			        else {
			        	wiki_categories_notident.put(s, 1);
			        }
				 }
			 }
			 else
			 {
				 wikiNot++;
		//		 System.out.println(param[0]);
			 }
			 }
		

	
		 
		 
			 
	  }
	
	  System.out.println(t1);
	 System.out.println("exist= " + existIndbpedia);
 System.out.println("not= " + notIndbpedia);
	 System.out.println("wikiIs= " +wikiIs +" wikiNOt= "+wikiNot);
	  //Close the input stream
	 Set<String> words = map.keySet();
	 Collection<Integer> counts = map.values();
	 		
	  for (String w : words)
		  {System.out.print(w + ",");
		  System.out.println(map.get(w).intValue());
		  }
	  
	  Set<String> categ = wiki_categories_notident.keySet();
		
		  for (String w : categ)
			  {System.out.print(w + ",");
			  System.out.println(wiki_categories_notident.get(w).intValue());
			  }
	  
	  
	  
	  }
	  in.close();
	    }catch (Exception e){//Catch exception if any
	  System.err.println("Error: " + e.getMessage());
	  }
	 }
	
}

/**
test1:
if( relevant.toLowerCase().equals(("true")))	
	 
if (conf_1>-1 && conf_2 <=-1 && conf_3>-1 && conf_4 <=-1 && conf_5<=-1 && conf_6 <= -1 && conf_7<=-1 && conf_8 <=-1
		 && conf_9<=-1 && conf_10 <=-1 && conf_11<=-1 && conf_12 <=-1 && conf_13<=-1 )
	 {t1++;
	 System.out.println(param[0]);
	 }
else  if (conf_1<=-1 && conf_2 >-1 && conf_3<-1 && conf_4 <=-1 && conf_5<=-1 && conf_6 <= -1 && conf_7<=-1 && conf_8 <=-1
		 && conf_9<=-1 && conf_10 <=-1 && conf_11<=-1 && conf_12 <=-1 && conf_13<=-1 )
{t2++;
System.out.println(param[0]);
}
else  if (conf_1<=-1 && conf_2 <=-1 && conf_3>-1 && conf_4 <=-1 && conf_5<=-1 && conf_6 <= -1 && conf_7<=-1 && conf_8 <=-1
		 && conf_9<=-1 && conf_10 <=-1 && conf_11<=-1 && conf_12 <=-1 && conf_13<=-1 )
{t3++;
System.out.println(param[0]);
}
else  if (conf_1<=-1 && conf_2 <=-1 && conf_3<=-1 && conf_4 >-1 && conf_5<=-1 && conf_6 <= -1 && conf_7<=-1 && conf_8 <=-1
		 && conf_9<=-1 && conf_10 <=-1 && conf_11<=-1 && conf_12 <=-1 && conf_13<=-1 )
{t4++;
System.out.println(param[0]);
}
else  if (conf_1<=-1 && conf_2 <=-1 && conf_3<=-1 && conf_4 <=-1 && conf_5>-1 && conf_6 <= -1 && conf_7<=-1 && conf_8 <=-1
		 && conf_9<=-1 && conf_10 <=-1 && conf_11<=-1 && conf_12 <=-1 && conf_13<=-1 )
{t5++;
System.out.println(param[0]);
}
else  if (conf_1<=-1 && conf_2 <=-1 && conf_3 <= -1 && conf_4 <=-1 && conf_5<=-1 && conf_6 > -1 && conf_7<=-1 && conf_8 <=-1
		 && conf_9<=-1 && conf_10 <=-1 && conf_11<=-1 && conf_12 <=-1 && conf_13<=-1 )
	  {t6++;
	 System.out.println(param[0]);
	 }
else  if (conf_1<=-1 && conf_2 <=-1 && conf_3<=-1 && conf_4 <=-1 && conf_5<=-1 && conf_6 <= -1 && conf_7>-1 && conf_8 <=-1
		 && conf_9<=-1 && conf_10 <=-1 && conf_11<=-1 && conf_12 <=-1 && conf_13<=-1 )
{t7++;
System.out.println(param[0]);
}
else  if (conf_1<=-1 && conf_2 <=-1 && conf_3<=-1 && conf_4 <=-1 && conf_5<=-1 && conf_6 <= -1 && conf_7<=-1 && conf_8 >-1
		 && conf_9<=-1 && conf_10 <=-1 && conf_11<=-1 && conf_12 <=-1 && conf_13<=-1 )
{t8++;
System.out.println(param[0]);
}
else  if (conf_1<=-1 && conf_2 <=-1 && conf_3<=-1 && conf_4 <=-1 && conf_5<=-1 && conf_6 <= -1 && conf_7<=-1 && conf_8 <=-1
		 && conf_9>-1 && conf_10 <=-1 && conf_11<=-1 && conf_12 <=-1 && conf_13<=-1 )
{t9++;
System.out.println(param[0]);
}
else  if (conf_1<=-1 && conf_2 <=-1 && conf_3<=-1 && conf_4 <=-1 && conf_5<=-1 && conf_6 <= -1 && conf_7<=-1 && conf_8 <=-1
		 && conf_9<=-1 && conf_10 >-1 && conf_11<=-1 && conf_12 <=-1 && conf_13<=-1 )
{t10++;
System.out.println(param[0]);
}
else  if (conf_1<=-1 && conf_2 <=-1 && conf_3<=-1 && conf_4 <=-1 && conf_5<=-1 && conf_6 <= -1 && conf_7<=-1 && conf_8 <=-1
		 && conf_9<=-1 && conf_10 <=-1 && conf_11>-1 && conf_12 <=-1 && conf_13<=-1 )
{t11++;
System.out.println(param[0]);
}
else  if (conf_1<=-1 && conf_2 <=-1 && conf_3<=-1 && conf_4 <=-1 && conf_5<=-1 && conf_6 <= -1 && conf_7<=-1 && conf_8 <=-1
		 && conf_9<=-1 && conf_10 <=-1 && conf_11<=-1 && conf_12 >-1 && conf_13<=-1 )
{t12++;
System.out.println(param[0]);
}
else  if (conf_1<=-1 && conf_2 <=-1 && conf_3<=-1 && conf_4 <=-1 && conf_5<=-1 && conf_6 <= -1 && conf_7<=-1 && conf_8 <=-1
		 && conf_9<=-1 && conf_10 <=-1 && conf_11<=-1 && conf_12 <=-1 && conf_13>-1 )
{t13++;
System.out.println(param[0]);
}
 System.out.println(t1 + " " + t2 +"  "+ t3+ " " + t4 +"  "+ t5 + " " + t6  + " " + t7 +"  "+ t8 + " " + t9 +"  "+ t10 +" " + t11 +" " +t12  + " " + t13);
	  
*/