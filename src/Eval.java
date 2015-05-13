

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import fr.eurecom.nerd.client.AlchemyAPIDriver;
import fr.eurecom.nerd.client.ApiCallSample;
import fr.eurecom.nerd.client.NERD;
import fr.eurecom.nerd.client.TestTextRazor;
import fr.eurecom.nerd.client.schema.Entity;
import fr.eurecom.nerd.client.type.DocumentType;
import fr.eurecom.nerd.client.type.ExtractorType;

public class Eval {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        String text = "yan Gosling is everywhere these days and I couldnt be happier";
        NERD nerd = new NERD("7mvm8gdsfaoa47gumksiin5kaqbab9kq");
      //7mvm8gdsfaoa47gumksiin5kaqbab9kq
        //1q03a8k1beahilavmgqm0csdk5j0g2eg
        //read reviews one by one from file
        // get json results for all taggers
        //print idEntity-label-extractorType-uri-reviewid_counter_taggerid into a new separate file named by review_id
        
        int correct =0;
        int avail_correct=0;
        int all_guessed=0;
        BufferedReader br = new BufferedReader(new FileReader("results.txt"));
        String line;

       while ((line = br.readLine()) != null) {
         try{
            String[] elements = line.split(",");
        String review=elements.length > 1 ?elements[1] : " " ;
        Set<String> availentities = new HashSet<String>();
           Set<String> str_entities_final = new HashSet<String>();
        for (int i=2; i< elements.length; i++)
        {
            String[] segments = elements[i].split("/");
            String idStr = segments[segments.length-1];
             String str= idStr.toLowerCase().replaceAll("\\([^\\(]*\\)", "");
             str = str.charAt(str.length()-1)==('_') ? str.substring(0,str.length()-1):  str;
            availentities.add(str.toLowerCase());
        }
              List<Entity>  entities = new ArrayList<Entity>();
              Set<String>    str_entities = new HashSet<String>();
      System.out.println("tagging with dbpedia " +  elements[0]);
       System.out.println("tagging withdbpedia  " + elements[1]);
       for (String a : availentities)
       {
           System.out.println(a);
           avail_correct++;
       }
       if(args[1].equals("1"))
       {
           str_entities.addAll( Spotlight.SpotlightText(elements[1],args[2]))  ;
       }
       else  if(args[1].equals("2"))
       {
           str_entities.addAll(AlchemyAPIDriver.getAlchemyentities(elements[1]));
       }
       else  if(args[1].equals("3"))
       {
           entities.addAll(nerd.annotate(ExtractorType.YAHOO, 
                   DocumentType.PLAINTEXT,
                   elements[1]));

            for (Entity e : entities)
          {
                
              str_entities.add(e.getUri().toLowerCase());
          }

       }
       else  if(args[1].equals("4"))
       {
           entities.addAll(nerd.annotate(ExtractorType.ZEMANTA, 
                   DocumentType.PLAINTEXT,
                   elements[1]));

            for (Entity e : entities)
          {
                
              str_entities.add(e.getUri().toLowerCase());
          }

       }
       else  if(args[1].equals("5"))
       {
           str_entities.addAll(ApiCallSample.Wikiannotate(elements[1]));
       }
       else  if(args[1].equals("6"))
       {
           str_entities.addAll(TestTextRazor.getEntities(elements[1]));
       }
     
     
     
       System.out.print("To Include [ "); 
       for (String e  : str_entities)
        {
             String uri = e;
    
             String[] segments = uri.split("/");
             String idStr = segments[segments.length-1];
             String str= idStr.toLowerCase().replaceAll("\\([^\\(]*\\)", "");
             if(!str.equals(""))
             {
             str = str.charAt(str.length()-1)==('_') ? str.substring(0,str.length()-1):  str;
             str_entities_final.add(str);
             }
            
        }
        
        for (String str  : str_entities_final)
        {

             if(availentities.contains(str))
             {
                
                 System.out.print(" " + str); 
                 correct++;
             }
             all_guessed++;
        }
         }
               catch(Exception e )
               {
                   System.out.println(e);
                 
               }  
         System.out.println("] "); 

       } 
       br.close();
       System.out.println("RESULTS :");
       System.out.println("correct :" + correct);
       System.out.println("avail_correct :" + avail_correct);
       System.out.println("all_guessed :" + all_guessed);
       
       try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(args[0], true)));
            out.println(correct+ "\t" + all_guessed + "\t" + avail_correct);
            out.close();
        } catch (IOException e) {
            //oh noes!
        }
       
    }

}


