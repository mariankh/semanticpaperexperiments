import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;


public class Average {
	
	public static void main (String args[]) throws IOException
	{
		//read file line by line
		//if column0 is the same - count = sum
		//if changes print column[0] previous, average
		BufferedReader br = new BufferedReader(new FileReader(args[0]));
		
		String header= br.readLine();
		String[] first = br.readLine().split(",");
		String col0= first[0];
		String col1 = first[1];
		int count=0;
		int sum=0;
		PrintWriter writer = new PrintWriter("spellings.average", "UTF-8");
		writer.println(header);
		String line;
		while ((line = br.readLine()) != null) {
		   // process the line.
			String[] columns = line.split(",");
			if(col0.equals(columns[0]))
			{
				count++;
				sum+=Integer.parseInt(columns[1]);
			}
			else 
			{
				if(count==0){count=1;sum=Integer.parseInt(col1);}
			double av=sum/count;
			writer.println(col0+","+ av);
			col0=columns[0];
			count=1;
			sum=Integer.parseInt(columns[1]);
			}
		}

		writer.close();
		br.close();
		
	}

}
