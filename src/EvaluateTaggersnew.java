import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

//pianei to dataset
//check on confidence-relevance je based on set of rules + make prediction
//entity(string) conf (double), relevance(boolean), predicted(boolean), after classification(boolean), gold(boolean)
// if predicted=true je gold= false je classification=false +1 chancetoFalse
public class EvaluateTaggersnew {

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

	  br.readLine();//header
	  int linecounter=0;
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
		 double conf_rel_9=Double.parseDouble(param[19]);
		 double conf_10=Double.parseDouble(param[19]);
		 double conf_rel_10=Double.parseDouble(param[20]);
		 double conf_11=Double.parseDouble(param[21]);
		 double conf_rel_11=Double.parseDouble(param[22]);
		 double conf_12=Double.parseDouble(param[23]);
		 double conf_rel_12=Double.parseDouble(param[24]);
		 double conf_13=Double.parseDouble(param[25]);
		 double conf_rel_13=Double.parseDouble(param[26]);
		
	//	 String tagger=param[3];
		 String relevant=param[27];
		 String classification = "";
		 String classification1 = "";
		 String classification2 = "";
		 String classification3 = "";
		
		if (relevant.toLowerCase().equals("true"))
			 relevantTRUE++;
		 else
			 relevantFALSE++;
		
		if((conf_2 >= 0.902554) && (conf_rel_2 <= 0.055501)) {
			
		}else
			classification2="TRUE";
		
		if(conf_1 < 381.5)	classification1="FALSE";
		else if(conf_1	< 403.5)	classification1="TRUE";
		else if(conf_1	< 437.0)	classification1="FALSE";
		else if(conf_1	< 475.5)	classification1="TRUE";
		else if(conf_1	< 1711.5)	classification1="FALSE";
		else if(conf_1	< 2522.0)	classification1="TRUE";
		else if(conf_1	< 3146.5)	classification1="FALSE";
		else if(conf_1	< 3357.0)	classification1="TRUE";
		else if(conf_1	< 11769.0)	classification1="FALSE";
		else if(conf_1	>= 11769.0)	classification1="TRUE";


	if(conf_3 < 2.6E-4)	classification3="TRUE";
		else if(conf_3	< 0.063605)	classification3="FALSE";
		else if(conf_3	< 0.06621)	classification3="TRUE";
		else if(conf_3	< 0.068155)	classification3="FALSE";
		else if(conf_3	< 0.07188)	classification3="TRUE";
		else if(conf_3	< 0.09328)	classification3="FALSE";
		else if(conf_3	< 0.097165)	classification3="TRUE";
		else if(conf_3	< 0.5174650000000001)	classification3="FALSE";
		else if(conf_3	< 0.52647)	classification3="TRUE";
		else if(conf_3	>= 0.52647)	classification3="FALSE";

		
		//if( conf_12>-1 )classification3="TRUE";
		//if (conf_10>-1)classification1="TRUE";
		//if (conf_2>-1)classification2="TRUE";
		if (( classification1.equals("TRUE")||classification2.equals("TRUE"))
				
			&&
			(classification1.equals("TRUE")||classification3.equals("TRUE") )
				&&
			(classification1.equals("TRUE")|| conf_8>-1 )
				&&
			(classification2.equals("TRUE")||classification3.equals("TRUE"))
				&&
			(classification2.equals("TRUE") || conf_8>-1 )
				&&
			(classification3.equals("TRUE") || conf_8>-1 )
			||  conf_10>-1)
			classification="TRUE";
		
		/*	if ((conf_2 >= 0.901428) && (conf_1 >= 118) )classification="TRUE";
		else if(		(conf_7 >= 0.06706) && (conf_8 >= 0.040653) && (conf_2 >= 0.925889)) classification="TRUE";
		else if(		(conf_rel_1 >= 0.084249) && (conf_7 >= 0.03821) )classification="TRUE"; //=> relevant=true (100.0/44.0)
		else if(		(conf_3 <= -1) && (conf_1 <= -1) && (conf_7 <= -1) && (conf_8 <= -1) && (conf_10 <= -1) && (conf_9 <= -1) && (conf_2 <= -1) && (conf_5 <= -1) && (conf_12 <= -1) && (conf_11 <= -1) && (conf_4 <= -1) && (conf_6 <= -1) )classification="TRUE";// => relevant=true (66.0/1.0)

				
	
		if ( (conf_12>-1 )
				 && (conf_10>-1  || conf_13>-1)
				|| 
				(conf_8>-1 && conf_2>-1))
				
			classification="TRUE";
/*
	if ((conf_1>-1 && conf_2>-1)
			|| (conf_2>-1 && conf_8>-1)
			|| (conf_1 >-1 && conf_8>-1))
		classification="TRUE";
		if (conf_9< 0.16805250000000002 || conf_9 >= 0.975 && conf_13>-1)
			classification="TRUE";
	//		classification="TRUE";
//	 if (conf_8>-1)
	//		classification="TRUE";
		//if(conf_8>-1)
			//	classification="TRUE";
		
	
	/*	

	 
		//1 default
	if((conf_1>-1 && conf_7>-1) || (conf_10>0.6 && conf_2>0.1) || (conf_10>0.6 && conf_7>-1) 
		//(conf_2>0.1 && conf_1>0.935) || (conf_10>0.6 && conf_7>0.3)|| (conf_7>0.3 && conf_1>-1) || (conf_10>0.6 && conf_1>-1 ))
	 || (conf_13>0.3)
			){ classification="TRUE";
		 trueClass++;
		 }
		
		/*
		System.out.println(conf_13);
		System.out.println(relevant);
		//linecounter++;
	*/
		/*
		 * 
		 * (conf_2 >= 0.901428) && (conf_rel_1 >= 0.07461) => relevant=true (87.0/10.0)
(conf_8 >= 0.067657) && (conf_5 >= 0.08494) => relevant=true (109.0/40.0)
(conf_1 >= 255) && (conf_7 >= 0.05471) && (conf_rel_1 >= 0.098053) && (conf_6 <= 0.10053) => relevant=true (13.0/1.0)
(conf_3 <= -1) && (conf_1 <= -1) && (conf_8 <= -1) && (conf_7 <= -1) && (conf_10 <= -1) && (conf_9 <= -1) && (conf_2 <= -1) && (conf_11 <= -1) && (conf_5 <= -1) && (conf_12 <= -1) && (conf_4 <= -1) && (conf_6 <= -1) => relevant=true (66.0/1.0)
(conf_1 >= 80) && (conf_5 >= 0.05208) && (conf_rel_1 >= 0.119751) => relevant=true (14.0/3.0)
(conf_1 >= 85) && (conf_1 >= 16122) => relevant=true (5.0/0.0)
 => relevant=false (1442.0/89.0)
 
		(conf_8 >= 0.188641) && (conf_2 >= 0.92175) => relevant=true (126.0/27.0)
(conf_rel_1 >= 0.064418) && (conf_8 >= 0.075963) && (conf_1 >= 513) => relevant=true (63.0/26.0)
(conf_rel_1 >= 0.08108) && (conf_2 >= 1.0534) => relevant=true (14.0/0.0) 
		
		if((conf_8 >= 0.188641) && (conf_2 >= 0.92175))
		 { classification="TRUE";
		 trueClass++;
		 }
		 if( (conf_rel_1 >= 0.064418) && (conf_8 >= 0.075963) && (conf_1 >= 513))
		 { classification="TRUE";
		 trueClass++;
		 }
		 if((conf_rel_1 >= 0.08108) && (conf_2 >= 1.0534) )
		 { classification="TRUE";
		 trueClass++;
		 }

		/*
		(conf_7 >= 0.901428) && (conf_13 >= 0.229742) => relevant=true (102.0/18.0)
				(conf_13 >= 0.067657) && (conf_10 >= 0.08924) => relevant=true (75.0/31.0)
				(conf_rel_7 >= 0.025452) => relevant=true (28.0/11.0)
				(conf_rel_1 >= 0.09192) && (conf_11 >= 0.0384) => relevant=true (27.0/9.0)
				 => relevant=false (1504.0/165.0)

			
				 (conf_10 >= 0) && (conf_9 >= 0) && (conf_10 <= 0.7392) => relevant=true (96.0/0.0)
(conf_10 >= 0.7494) && (conf_7 >= 0.19958) => relevant=true (39.0/8.0)
(conf_1 >= 1305) && (conf_7 >= 0.07283) && (conf_10 >= 0.6527) => relevant=true (11.0/2.0)
(conf_1 >= 1065) && (conf_1 >= 6606) && (conf_rel_1 >= 0.063212) && (conf_1 <= 13386) => relevant=true (10.0/1.0)
		*/
		/*
		 * (conf_7 >= 0.06706) && (conf_rel_1 >= 0.05778) => relevant=true (172.0/54.0)
(conf_7 >= 0.08325) && (conf_10 >= 0.7362) => relevant=true (30.0/9.0)
(conf_rel_1 >= 0.080108) && (conf_7 >= 0.03425) => relevant=true (35.0/17.0)
(conf_1 >= 16122) => relevant=true (8.0/0.0)
 
 
 
(conf_2 >= 0.901428) && (conf_rel_1 >= 0.07461) => relevant=true (87.0/10.0)
(conf_8 >= 0.067657) && (conf_5 >= 0.08494) => relevant=true (109.0/40.0)
(conf_1 >= 255) && (conf_7 >= 0.05471) && (conf_rel_1 >= 0.098053) && (conf_6 <= 0.10053) => relevant=true (13.0/1.0)
(conf_3 <= -1) && (conf_1 <= -1) && (conf_8 <= -1) && (conf_7 <= -1) && (conf_10 <= -1) && (conf_9 <= -1) && (conf_2 <= -1) && (conf_11 <= -1) && (conf_5 <= -1) && (conf_12 <= -1) && (conf_4 <= -1) && (conf_6 <= -1) => relevant=true (66.0/1.0)
(conf_1 >= 80) && (conf_5 >= 0.05208) && (conf_rel_1 >= 0.119751) => relevant=true (14.0/3.0)
(conf_1 >= 85) && (conf_1 >= 16122) => relevant=true (5.0/0.0


		 *
		 *conf_1 <= 244 &&
conf_11 <= 0.525: false (1349.0/168.0)

conf_1 > 6489 &&
conf_12 <= -1 &&
conf_1 > 11582: true (18.0)

conf_rel_1 <= 0.095469 &&
conf_rel_11 <= -1: false (161.0/33.0)

conf_13 <= -1 &&
conf_rel_11 > -1 &&
conf_rel_1 <= 0.069313: false (19.0/4.0)
/
 * */
 /*
		// on test set
			if( (conf_2 >=0 )
					&& (conf_8 >=0 )
						&& (conf_10 >=0 )
							)
								
			
			*

if ((conf_2 >= 0.901016) && (conf_8 >= 0.221231) )classification="TRUE";
if( (conf_8 >= 0.040653) && (conf_10 >= 0.6103) )classification="TRUE";
if ( (conf_2 >= 0.901428) && (conf_rel_2 >= 0.128661))classification="TRUE"; */
		/*
		if ( (conf_10 >= 0.608) && (conf_rel_2 >= 0.072256)) classification="TRUE";
		else if ((conf_1 <= -1) && (conf_8 <= -1) && (conf_2 <= -1) && (conf_9 <= -1) && (conf_10 <= 0.6208) && (conf_11 <= -1) && (conf_rel_12 <= 0.512141) && (conf_13 <= -1) && (conf_3 <= -1) && (conf_10 <= -1)) classification="TRUE";
		else if ((conf_10 >= 0.6077) && (conf_8 >= 0.19194) && (conf_rel_1 >= 0.089502)) classification="TRUE";
		else if ( (conf_8 >= 0.17401) && (conf_rel_1 >= 0.079745) && (conf_1 <= 320) && (conf_1 >= 270) )classification="TRUE";
		else if ( (conf_10 >= 0.6081) && (conf_1 >= 58) && (conf_1 <= 1622) ) classification="TRUE";
*/
		
			
			
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
