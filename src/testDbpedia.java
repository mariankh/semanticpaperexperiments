import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import java.util.regex.Pattern;
import java.io.*;

public class testDbpedia {
    public static void main(String[] args) throws IOException  {
    	
    	int indbpedia=0;
    	int wiki=0;
    	FileInputStream fstream = new FileInputStream("allentities.txt");
    	BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
    	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime()));
		int count=0;
    	    String line;
    	    while ((line = br.readLine()) != null) {
    	       // process the line.
    	    	wiki++;
    	    //	System.out.print(wiki+ " ");
    	    	if (!(isInDbPedia(line)=="no"))
    	    	{
    	    		indbpedia++;
    	    	}
    	    	else
    	    	{
    	    		System.out.println(count+ " "+line);
    	    	count++;
    	    	}
    	    }
    	
    System.out.println();
   	System.out.println("dbpedia:" + indbpedia);
    	 //System.out.println(getDbPediaEntityString(" "));
    	System.out.println("wiki:" +wiki);
    	cal = Calendar.getInstance();
    	System.out.println(dateFormat.format(cal.getTime()));
       
    }
    public static String getDbPediaEntityString(String string) throws IOException {

		if(DbPedia(string).equals("" ))
		{
			
			if(!DbPedia(modified1(string)).equals("" ))
			{
				//System.out.println("modified- >"+DbPedia(modified1(string)).equals("" ));
			return 
					modified1(string);
			}
			
			if(!DbPedia(modified(string)).equals("" ))
			{
				//System.out.println("modified- >");
			return 
						modified(string);
			}
			
			else if (!DbPedia(modified2(string)).equals("" ))
			{//System.out.println("modified2- >");
				return 
						modified2(string);
			}
		}
		else
		{
			return string;
		}
		
		return string;
	}
    

	private static String modified1(String string) {
		char[] letters = string.toCharArray();
		for (int i=0; i < letters.length;  i++)
		{
			if (i == 0) letters[i]=Character.toUpperCase(letters[i]);
			if (letters[i] == ' ') letters[i] = '_';
			//if (letters[i] == '_') letters[i+1]=Character.toUpperCase(letters[i+1]);
	
		}
		return new String(letters);
	}
	public static String isInDbPedia(String string) throws IOException {
		if(DbPedia(string).equals("no"))
		{if(!DbPedia(modified1(string)).equals("no"))
			return 
					DbPedia(modified1(string));
		else if (!DbPedia(modified(string)).equals("no"))
			return 
					DbPedia(modified(string));
			else if (!DbPedia(modified2(string)).equals("no"))
				return 
						DbPedia(modified2(string));
		}
		else
			
			return DbPedia(string);
		
		return DbPedia(string);
	}
	public static Vector<String> isInWiki(String string) {
		if(Wikipedia(string).isEmpty())
		{
			if(!Wikipedia(modified1(string)).isEmpty())
			return 
					Wikipedia(modified1(string));
			else if (!Wikipedia(modified(string)).isEmpty())
				return 
						Wikipedia(modified(string));
			else if (!Wikipedia(modified2(string)).isEmpty())
				return 
						Wikipedia(modified2(string));
		}
		else
			return Wikipedia(string);
		return Wikipedia(string);
	}

	private static String modified(String string) {
		//if first char or before _ to UpperCase
		char[] letters = string.toCharArray();
		for (int i=0; i < letters.length;  i++)
		{
			if (i == 0) letters[i]=Character.toUpperCase(letters[i]);
			if (letters[i] == ' ') letters[i] = '_';
			if(i+1<letters.length)
				if (letters[i] == '_') letters[i+1]=Character.toUpperCase(letters[i+1]);
	
		}
		return new String(letters);
		
	}
private static String modified2(String string) {
	
	string=modified(string);
	// try to catch articles and turn them into lower case
	//System.out.println("to modify2 " + string);
	String[] words = string.split("_");
	//not the first word
	for (int i=0; i< words.length; i++)
	{
		if (( words[i].toCharArray().length<4) && (i!=0))
		{
		//	System.out.println("small word" + words[i]);
			 char[] letters = words[i].toCharArray();
			 for (int j=0; j<letters.length; j++)
			 {
				 letters[j]=Character.toLowerCase(letters[j]);
				// System.out.println("here "+letters[j]);
			 }
			
			 words[i]= new String(letters);
	//	System.out.println(new String(letters));
		}
	}
		String re = "";
		
		for (String w : words)
		{
			re= (re.equals("")) ?w:re+"_"+w;
			
		}
		//System.out.println("improved "  +  re);
		return re;
	}
	public static String DbPedia(String string) throws IOException {
		 URL yahoo = null;
		String returnstr ="";
		//System.out.println("requesting dbpedia with string" + string) ;
		try {
			yahoo = new URL("http://dbpedia.org/page/"+string);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			
			//System.out.println("here");
			e.printStackTrace();
			
		}
	        URLConnection yc = null;
			try {
				yc = yahoo.openConnection();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			e.printStackTrace();
			}
			HttpURLConnection httpConn = (HttpURLConnection) yc;
			InputStream is;
			if (httpConn.getResponseCode() >= 400) {
			    is = httpConn.getErrorStream();
			} else {
			    is = httpConn.getInputStream();
			}

			BufferedReader reader = new BufferedReader(new  InputStreamReader(is));   
			
			  String inputLine;
		        try {
					while ((inputLine = reader.readLine()) != null) 
					{
					//
						if(inputLine.indexOf("An Entity of Type")>-1)
						{
							final Pattern pattern = Pattern.compile(">(.+?)\\</a");
				    final java.util.regex.Matcher matcher = pattern.matcher(inputLine);
				    matcher.find();
				    returnstr =matcher.group(1);
						}
						if(inputLine.indexOf("The requested entity is unknown")>-1)
						{					
							returnstr= "no";
						}
					}	
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					//System.out.println(e1.getMessage());
				}
		        
		        

	        
	        try {
				reader.close();
			} catch (IOException e) {
				//System.out.println("here");
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			}
	       // System.out.println("return" + returnstr);
			return returnstr;
	        
	}
	
	
	

	public static Vector<String> Wikipedia(String string) {
		 URL yahoo = null;
		 Vector<String> categories = new Vector<String>();
		 //System.out.println("is ths called>");
			String returnstr ="";
		try {
			yahoo = new URL("http://en.wikipedia.org/wiki/"+string);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			
			//System.out.println("here");
		//	e.printStackTrace();
			
		}
	        URLConnection yc = null;
			try {
				yc = yahoo.openConnection();
			} catch (IOException e) {
				// TOD O Auto-generated catch block
				
		//		e.printStackTrace();
			}
			 BufferedReader in = null;
				try {
					in = new BufferedReader(
					                        new InputStreamReader(
					                        yc.getInputStream()));
				} catch (IOException e) {
					// TODO Auto-generated catch blockSystem.out.println("here");
				//	e.printStackTrace();
					return categories;
					
				}
				  String inputLine;
			        try {
						while ((inputLine = in.readLine()) != null) 
						{
							//System.out.println(inputLine);
							if(inputLine.contains("/wiki/Category"))
							{
						//		final Pattern pattern = Pattern.compile(">(.+?)\\</a");
					  //  matcher.find();
							//"<Resource URI=(.+?) support")
							
							
						    final Pattern p = Pattern.compile("/wiki/Category:(.+?) t");
						    java.util.regex.Matcher m = p.matcher(inputLine);
						    while(m.find()){
						        String b =  m.group(1);
						      String cat= b.substring(0, b.length()-1);
						       if(!cat.contains("extiw")&& (!cat.equals(string)))
						       {
						    	 //  System.out.println("" +b.substring(0, b.length()-1));
								      
						        categories.add(cat);
						       }
						    }
							}
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			        
			        

			        
			        try {
						in.close();
					} catch (IOException e) {
						System.out.println("here");
						// TODO Auto-generated catch block
					//	e.printStackTrace();
					}
					return categories;
			        
			}
			
}
