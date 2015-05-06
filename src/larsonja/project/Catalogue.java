package larsonja.project;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Catalogue {
	/*
	 * This class represents a catalogue to be used in an inventory system.
	 * 
	 * @author Jake Larson
	 * 
	 */
	
	public static void main(String args[]) throws IOException{
		
		double count;
		double desired; // column 3 for BB, 4 for DC
		int flag;
		List<String> record = new ArrayList<String>(); 
		String row;
		String name;
		String check;
		String location;
		
		Scanner in = new Scanner(System.in);
		
		System.out.println("Please enter DC for Deep Cove or BB for Brooksbank: ");
		location = in.nextLine();
		System.out.println("You have entered "+location);
		
		if(location == "BB"){ //sets flag as column to use in CSV
			flag = 3;
		} else {
			flag = 4;
		}
		
		do{
			System.out.println("Please enter the name of the file exactly as it appears for your CSV: ");
			name = in.nextLine();
			System.out.println("You have entered "+name);
			
			System.out.println("Is this correct? Y/N");
			check = in.nextLine();		
		} while (check.toLowerCase() != "y");
		in.close();
		
		name.concat(".csv"); //add the file extension onto the end
		
		BufferedReader reader = new BufferedReader(new FileReader(name));
		
		while((row = reader.readLine()) != null){
			if((row.startsWith(",") == false)){ //ignore anything that starts with a comma
				record.add(row);
			}
		}
		reader.close();
		//now all my rows should be in the lines, so use that to examine and operate on
		
		
		
	}
	
	ArrayList<Item> Inventory = new ArrayList<Item>();

	
}
