package larsonja.project;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
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
		
		String itemName;
		double count;
		double desiredA; // column 3 for BB, 4 for DC
		double desiredB;
		Double locationOrders;
		
		int flag;
		List<String> Inventory = new ArrayList<String>(); 
		List<String> Ordering = new ArrayList<String>();
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
		} while (check.toLowerCase().equals("y") == false);
		in.close(); //am done taking input so close my reader
		
		name = name.concat(".csv"); //add the file extension onto the end of the file name
		try{
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(name)); //start reading from my file
		
		
		while((row = reader.readLine()) != null){
			if((row.startsWith(",") == false)){ //ignore anything that starts with a comma
				Inventory.add(row);
			}
		}
		reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//now all my rows should be in the lines, so use that to examine and operate on
		
		Iterator<String> iterator = Inventory.iterator();
		String tempRow;
		
		//need to make a new file
		Writer writer;
		try {
		    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("inventoryOutput.csv"), "utf-8"));
		    
		    //stuff to write
		    while(iterator.hasNext()){
				
				tempRow = iterator.next();
				
				if(tempRow.startsWith("//")){
					//do nothing to it and just print
					Ordering.add(tempRow);
					
				} else if(tempRow.startsWith(",")){
					//actually do nothing at all with the line, it's considered empty
				} else {
					//parse normally and put into new CSV
					String[] parts = tempRow.split(",");
					itemName = parts[0];
					count = Double.valueOf(parts[2]);
					
					Item currentItem = new Item(itemName, count, Double.valueOf(parts[3]), Double.valueOf(parts[4]));
					/*
					 * creating the item here is redundant as we could just do the calculations and it would be simple enough
					 * however the goal of this project is to be a functional core of a bigger project that will be build off of this.
					 * Hence why we create the object here. otherwise you can just deal with the calculations after this comment
					 */
					
					desiredA = currentItem.getAmountA();
					desiredB = currentItem.getAmountB();
					//this gives me the amount, not check if it wants the A or B values
					if(flag == 3){
						locationOrders = desiredA;
					} else {
						locationOrders = desiredB;
					}
					
					String result = itemName;
					result = result.concat(","); //comma for CSV file format
					result = result.concat(parts[1]); //the units of measurement
					result = result.concat(","); //comma for the CSV 
					result = result.concat(locationOrders.toString()); //the actual orders
					//my string is now formatted, add to list of strings
					
					Ordering.add(result); //again a little redundant but will be used in future most likely
				}
			}
		    
		    //have Ordering list full of my outputs so need to print that to the file and then we'll be good
		    Iterator<String> iteratorB = Ordering.iterator();
		    
		    while(iteratorB.hasNext()){
		    	//print it all to the file
		    	String toPrint = iteratorB.next();
		    	
		    	writer.write(toPrint);
		    	writer.write(System.getProperty("line.separator"));
		    }//iterates through and prints it into the new file
		    
		    writer.close(); //done writing so the file closes 
		    
		} catch (IOException e) {
		  e.printStackTrace();
		}
	}
}
