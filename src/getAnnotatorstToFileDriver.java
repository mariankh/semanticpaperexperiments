import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONException;
import org.xml.sax.SAXException;

import com.textrazor.AnalysisException;


public class getAnnotatorstToFileDriver {

	public static void main(String[] args) throws IOException, AnalysisException, ParserConfigurationException, SAXException, JSONException {
		// TODO Auto-generated method stub
		 Timer timer = new Timer();
		 String dataset="a1.csv";
		 String settings="settings.txt";
		 
		 int count=0;
		 while(count<args.length)
		 {
			// getAnnotationsToFile get = new getAnnotationsToFile();
			 //get.getAnnotators(args[count], "settings.txt");
			  new SimpleThread(args[count],args[count]).start();
			 count++;
		 }
		 //timer.schedule(new GetAnnotators(args), 0, 500);}

	}
}
class SimpleThread extends Thread {
	String dataset="";
    public SimpleThread(String str,String dataset) {
        super(str);
        this.dataset=dataset;
    }
    public void run() {
    	getAnnotationsToFile get = new getAnnotationsToFile();
		 try {
			get.getAnnotators(this.dataset, "settings.txt");
		} catch (IOException | AnalysisException | JSONException
				| ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("DONE! " + getName());
    }
}
class GetAnnotators extends TimerTask {
		
		private String[] datasets;
		int count=0;
		public GetAnnotators(String[] args) {
			this.datasets=args;			
		}	
		public void run() {
			
			if(count<datasets.length)
			{
				System.out.println("Starting + "+datasets[count]);
				getAnnotationsToFile get = new getAnnotationsToFile();
				try {
					get.getAnnotators(datasets[count], "settings.txt");
					
				} catch (IOException | AnalysisException | JSONException
						| ParserConfigurationException | SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				count++;
			}
			else
				System.exit(0);
	    }
	 }

	 // And From your main() method or any other method
	
