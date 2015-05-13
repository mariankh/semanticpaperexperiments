import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;


public class Clean1 {

	 public static void main(String args[])
	  {
	  try{
	  // Open the file that is the first 
	  // command line parameter
	  FileInputStream fstream = new FileInputStream(args[0]);
	  // Get the object of DataInputStream
	  DataInputStream in = new DataInputStream(fstream);
	  BufferedReader br = new BufferedReader(new InputStreamReader(in));
	  String strLine = br.readLine();//header
	  
	  FileWriter fstrem = new FileWriter("train"+args[0]);
	  BufferedWriter out = new BufferedWriter(fstrem);
	  out.write(strLine+"\n");
	  
	  //Close the output stream
	 
	  while ((strLine = br.readLine()) != null)   {
		  
		  
		  String[] param = strLine.split(",");
		  if(Double.parseDouble(param[1]) >=0)
			  out.write(strLine+"\n");
	  }
	  out.close();
	  }
	  catch (Exception e )
	  {
		  System.out.println(e);
	  }
	  }
	
	  
}
