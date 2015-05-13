import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class CorrectSpellingswithJazzy {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String input_file="a1.csv";
		String output_file="output";
		
		correctSpellings (input_file, output_file);
	}

	private static void correctSpellings(String input_file, String output_file) throws IOException {
		// TODO Auto-generated method stub

		  BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream
				  									(new FileInputStream(input_file))));
		 
		String strLine;
		String out;
		while ((strLine = br.readLine()) != null)   {
			String[] elements = strLine.split(",");
			JazzySpellChecker jazzy = new JazzySpellChecker();
			System.out.println(elements[2]);
			String [] words = elements[2].split(" ");
			String to_correct="";
			int count=0;
			String outcorrected="";
			for (String w : words)
			{
				to_correct=to_correct+" "+ w;
				
				if(count==10)
				{
					outcorrected=outcorrected + " " + jazzy.getCorrectedText(to_correct);
					count=0;
					to_correct="";
				}
				count++;
			}
			if(count!=0)
			{
				outcorrected=outcorrected + " " + jazzy.getCorrectedText(to_correct);
			}
			System.out.println(outcorrected);
			out = elements[0]+","+elements[1]+","+ outcorrected;
			for (int i=3; i<elements.length; i++)
			{
				out = out + elements[i]+ ",";
			}
			try(PrintWriter pout = new PrintWriter(new BufferedWriter(new FileWriter(output_file, true)))) {
			    pout.println(out);
			}catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}
		}

	}
}
