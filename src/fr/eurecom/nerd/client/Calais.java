package fr.eurecom.nerd.client;

import java.io.IOException;


public class Calais {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		CalaisRestClient client = new CalaisRestClient("tuywj7yythawtbhqxp64fsza");
	   CalaisResponse response = (CalaisResponse) client.analyze("Prosecutors at the trial of former Liberian President Charles Taylor");

	    System.out.println("hey is this working?");
	    for (CalaisObject entity : response.getEntities()) {
	        System.out.println(entity.getField("_type") + ":" 
	                           + entity.getField("name"));
	      }
	    

	}

}
