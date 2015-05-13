package fr.eurecom.nerd.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class filteroutDoublicates {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String notrel_file="notrel";
		String rel_file="rel";
		Set<String> notrel=readFiletoSet(notrel_file);
		Set<String> rel=readFiletoSet(rel_file);
		Set<String> nondublicates = getnotrel(notrel,rel);
		for (String s : nondublicates)
		{
			System.out.print(s+",");
		}
		System.out.println();
	}

	private static Set<String> getnotrel(Set<String> notrel, Set<String> rel) {
		Set<String> ret = new HashSet<String>();
		for (String s : notrel)
		{
			if (!rel.contains(s))
			{
				ret.add(s);
			}
		}
		return ret;
	}

	private static Set<String> readFiletoSet(String rel_file) throws IOException {
		FileInputStream fstream = new FileInputStream(rel_file);
		  // Get the object of DataInputStream
		  DataInputStream in = new DataInputStream(fstream);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
		  String strLine;
		  //Read File Line By Line
		  Set<String> ret = new HashSet();
		  while ((strLine = br.readLine()) != null)   {
			 ret.add(strLine);
		  }
		return ret;
	}
}
