import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

//pianei to dataset
//check on confidence-relevance je based on set of rules + make prediction
//entity(string) conf (double), relevance(boolean), predicted(boolean), after classification(boolean), gold(boolean)
// if predicted=true je gold= false je classification=false +1 chancetoFalse
public class EvaluateTagger {

	
	 public static void main(String args[])
	  {
	  try{
	  // Open the file that is the first 
	  // command line parameter
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
	  br.readLine();//header
	  while ((strLine = br.readLine()) != null)   {
	  // Print the content on the console
		 String[] param = strLine.split(",");
		 if(param.length<2) break;
		 String entity=param[0];
		 double conf_1=Double.parseDouble(param[1]);
		 double conf_rel_1=Double.parseDouble(param[2]);
		 String tagger=param[3];
		 String relevant=param[27];
		 String classification = "";
		 
		
		if (relevant.toLowerCase().equals("true"))
			 relevantTRUE++;
		 else
			 relevantFALSE++;
		//default
//classification=tagger;	
		/*
if(conf_1>=0.0 && conf_rel_1 >=0.0)
{ classification="TRUE";
trueClass++;//
}
/*
		if((conf_1 >= 247) && (conf_rel_1 >= 0.106626) && (conf_1 >= 618))
		 { classification="TRUE";
		 trueClass++;
		 }
		else if ((conf_1 >= 368)  && (conf_1 >= 6606))
		{ classification="TRUE";
		 trueClass++;
		 }
			
		/*
		conf_1 <= 0.08855: FALSE (519.0/43.0)

conf_1 <= 0.5786: FALSE (315.0/120.0)
*/
	/*
	 * (conf_1 >= 0.8599) =>  relevant=TRUE (41.0/14.0)
(conf_1 <= 0.6386) =>  relevant=TRUE (19.0/7.0)
 =>  relevant=FALSE (118.0/34.0)
	 //conf_1 >= 0.11531) and (conf_1 >= 0.52195)*/
		//(conf_1 >= 0.0942) and (conf_1 >= 0.51569)
		
		/*
		 *
		 
		 < 0.6552755	-> false
	< 0.7512669999999999	-> true
	>= 0.7512669999999999	-> false

	 if( (conf_1 >= 0.59068))
				{
			 classification="TRUE";
		 }
		/*(conf_1 >= 0.8599) => relevant=true (41.0/14.0)
(conf_1 <= 0.6386) => relevant=true (20.0/7.0)
		 * conf_rel_1 > -1 AND
conf_1 <= 0.51925: false (281.0/104.0)
		 */
		 
		/*if((conf_1 >= 0.9))  { classification="TRUE";
		 trueClass++;
		 }
		
		*.
		*conf_1 >= 0.908428) and (conf_1 <= 1.27339) and (conf_rel_1 <= 0.01373) => relevant=false (35.0/15.0)
 => relevant=true (360.0/47.0).
 '(0.5206-inf)'       true
'(-inf--0.5]'        true
'(0.094525-0.5206]'  false
'(0.05291-0.094525]' false
'(-0.5-0.05291]'     false


		 if( (conf_1 >= 0.98) && (conf_1 >= 0.99)  )
		 { classification="TRUE";
		 trueClass++;
		 }
	/*	 
conf_1 <= -1) => relevant=true (143.0/0.0)
(conf_1 >= 0.08822) and (conf_1 >= 0.16237) and (conf_1 >= 0.5866) => relevant=true (26.0/4.0)
 => relevant=false (834.0/163.0)
 
 
 < 0.06873	-> false
	< 0.07208500000000001	-> true
	< 0.09304000000000001	-> false
	< 0.09592999999999999	-> true
	< 0.09894	-> false
	< 0.101305	-> true
	< 0.10417	-> false
	< 0.10495499999999999	-> true
	< 0.12299	-> false
	< 0.127275	-> true
	< 0.132145	-> false
	< 0.135765	-> true
	< 0.146895	-> false
	< 0.15295999999999998	-> true
	< 0.184645	-> false
	< 0.20654499999999998	-> true
	< 0.23208499999999999	-> false
	< 0.27302499999999996	-> true
	< 0.500355	-> false
	>= 0.500355	-> true
	
	
	< 0.21506952861893558	-> false
	< 0.2683441380035261	-> true
	< 0.3003280678373753	-> false
	< 0.30912398286173404	-> true
	< 0.35242803672238127	-> false
	< 0.3613346635079575	-> true
	< 0.3914273784084199	-> false
	< 0.4252443201146108	-> true
	< 0.4781590947175099	-> false
	< 0.5107213386851361	-> true
	< 0.5267334034566262	-> false
	< 0.5925487599865806	-> true
	< 0.6912634121095582	-> false
	>= 0.6912634121095582	-> true
	else 
	*/
		
		if ((conf_1 >= 0.504545) && (conf_1 >= 0.669033))	classification="FALSE";
		else if ( (conf_1 >= 0.674189) && (conf_1 <= 0.723798) )classification="FALSE";
		else 
			classification="TRUE";
		/*
		else if(conf_1	< 0.06621)	classification="TRUE";
		else if(conf_1	< 0.068155)	classification="FALSE";
		else if(conf_1	< 0.07188)	classification="TRUE";
		else if(conf_1	< 0.09328)	classification="FALSE";
		else if(conf_1	< 0.097165)	classification="TRUE";
		else if(conf_1	< 0.5174650000000001)	classification="FALSE";
		else if(conf_1	< 0.52647)	classification="TRUE";
		else if(conf_1	>= 0.52647)	classification="FALSE";
		/*
		if(conf_1 < 381.5)	classification="FALSE";
		else if(conf_1	< 403.5)	classification="TRUE";
		else if(conf_1	< 437.0)	classification="FALSE";
		else if(conf_1	< 475.5)	classification="TRUE";
		else if(conf_1	< 1711.5)	classification="FALSE";
		else if(conf_1	< 2522.0)	classification="TRUE";
		else if(conf_1	< 3146.5)	classification="FALSE";
		else if(conf_1	< 3357.0)	classification="TRUE";
		else if(conf_1	< 11769.0)	classification="FALSE";
		else if(conf_1	>= 11769.0)	classification="TRUE";
		 	
		/*
			 if(conf_1 < 0.6125499999999999)	classification="FALSE";
			else if(conf_1 <  0.62915)	classification="TRUE";
			else if(conf_1 <  0.6366499999999999)	classification="FALSE";
			else if(conf_1 <  0.6537999999999999)	classification="TRUE";
			else if(conf_1 <  0.65695)	classification="FALSE";
			else if(conf_1 <  0.66215)	classification="TRUE";
			else if(conf_1 <  0.6676)	classification="FALSE";
			else if(conf_1 <  0.6721)	classification="TRUE";
			else if(conf_1 <  0.67425)	classification="FALSE";
			else if(conf_1 <  0.6759)	classification="TRUE";
			else if(conf_1 <  0.68695)	classification="FALSE";
			else if(conf_1 <  0.6934499999999999)	classification="TRUE";
			else if(conf_1 <  0.69775)	classification="FALSE";
			else if(conf_1 <  0.72045)	classification="TRUE";
			else if(conf_1 <  0.7247)	classification="FALSE";
			else if(conf_1 <  0.7317)	classification="TRUE";
			else if(conf_1 <  0.7319)	classification="FALSE";
			else if(conf_1 <  0.7414499999999999)	classification="TRUE";
			else if(conf_1 <  0.7499)	classification="FALSE";
			else if(conf_1 <  0.7554)	classification="TRUE";
			else if(conf_1 <  0.7612)	classification="FALSE";
			else if(conf_1 <  0.82805)	classification="TRUE";
			else if(conf_1 <  0.8461000000000001)	classification="FALSE";
			else if(conf_1 <  0.86085)	classification="TRUE";
			else if(conf_1 <  0.86175)	classification="FALSE";
			else if(conf_1 >= 0.86175)	classification="TRUE";
		*/
		 if (classification.equals(tagger))
			 nochange++;
		 else
		 {
			 
			if(classification.equals(relevant))
				 correctchange++;
			 else 
				 wrongchange++;
		 }
		 
		 if (classification.equals("TRUE"))
			 classificationTRUE++;
		 else
			 classificationFALSE++;
		  
		 
		 if(classification.equals("TRUE"))
		 {
			if( relevant.toLowerCase().equals(("true")))
					TP++;
			else
				FP++;
			 
		 }
		 else
		 if( relevant.toLowerCase().equals("FALSE"))
					TN++;
			else
				FN++;
			 
		 
		 
		 
			 
	  }
	  System.out.println(relevantTRUE+ " " + relevantFALSE);
	  System.out.println(classificationTRUE+ " " + classificationFALSE);
	  System.out.println (nochange+" "+correctchange+" "+ wrongchange +" "+trueClass+ " " +falsClass);
	  System.out.println("TP= "+TP+" FP=" + FP+ " TN= "+ TN + " FN= " +FN );
	  //Close the input stream
	  in.close();
	    }catch (Exception e){//Catch exception if any
	  System.err.println("Error: " + e.getMessage());
	  }
	 }
	
}
