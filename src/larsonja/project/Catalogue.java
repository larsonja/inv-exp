package larsonja.project; 

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


public class Catalogue {
	/*
	 * This class represents a catalogue to be used in an inventory system.
	 * 
	 * @author Jake Larson
	 * 
	 */
	
	File catalogueFile;
	/* TODO realize this should just be a group of items as a backbone, the inventory counts should be dealt with elsewhere
	 * as it's more of the functionality. Do this later though after basics are working then transition elsewhere
	 * want format to be: (brackets represent the item.toString representation)
	 * (name, flag, desiredA, desiredB), unit, notes, count1, count2, count3, etc...
	 * flag can be used for whatever, but  one will be used as an override on the trend stuff being done later
	 */
	BufferedReader catalogueReader;
	Writer catalogueWriter;
	ArrayList<String> catalogueArray; //each string will be one line of text, where index is the line of the file originally ended by a null character
	
	
	/**
	 * Constructor to make a data file for a location, will be formatted so methods can be used to return information given by the catalogue
	 * @param location - name of the location for the catalogue
	 * @throws IOException 	
	 */
	public Catalogue(String location) throws IOException, RuntimeException{

		// Will only create the file for a given location once
		
		String path = "\\data\\";
		path = path.concat(location);
		
		this.catalogueFile = new File(path); 
		this.catalogueArray = new ArrayList<String>();

		
		if (!this.catalogueFile.exists()){
			//create reader/writer
			try {
				this.catalogueWriter = new BufferedWriter(new FileWriter(path, false));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				this.catalogueReader = new BufferedReader(new FileReader(path));
			} catch (FileNotFoundException x) {
				x.printStackTrace();
			}
			
			//TODO add a header to the file to indicate what it is
			
			this.catalogueArray.add("Name,Unit,Flag,DesiredA,DesiredB,"); //this sets it up for the first count column to be added
			
			
		} else {
			//create reader for my file
			try {
				this.catalogueReader = new BufferedReader(new FileReader(path));
				
				String line = this.catalogueReader.readLine();
				
				for(@SuppressWarnings("unused")
				int i = 0;  line != null; i++){
					this.catalogueArray.add(line); 
					line = this.catalogueReader.readLine();
				} //fills catalogueArray with the file info so therefore we can now modify it line by line (should only need to make additions to the lines)
				
			} catch (FileNotFoundException x) {
				x.printStackTrace();
			}
			
			//create writer for my file
			try {
				this.catalogueWriter = new BufferedWriter(new FileWriter(path, true));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//TODO read header to make sure it's for the right thing
			
			
		}
		Collections.sort(catalogueArray); //makes sure it's sorted
		
		//have created my file, reader, and writer so the file is ready for manipulation.
	}
	
	/*
	 * delete item (one removes it from view (hides it but keeps the info) and the other fully deletes it) TODO add flag to deal with this
	 * find if the catalogue already has the item in it (uses the .equals)
	 * flag an item
	 */
	
	/**
	 * Method to remove an item from the catalogue
	 * @param item - the item to be removed
	 * @return 1 if successfully removed, 0 if not found, 2 if an error occured
	 * 	 */
	public int removeItem(Item item){
		int result = 2;
		String itemName = item.getName();
		
		Iterator<String> iterator = catalogueArray.iterator();
		
		while(iterator.hasNext()){ //will miss the first row but it's the title row so it's fine to skip
			if(iterator.next().startsWith(itemName)){
				catalogueArray.remove(iterator.next()); //removes it
				result = 1;
			}
		}
		
		Collections.sort(catalogueArray);
		result = 0;
		
		return result;
	}
	
	/**
	 * Method to add an item to a catalogue
	 * @param item - the item to be added
	 * @return true if the item is added correctly, false otherwise
	 */
	public boolean addItem(Item item){
		boolean result = false;
		
		String itemString = item.toString();
		
		String commaString = catalogueArray.get(2); // will be a row with the correct number of commas so use this to get comma count for new items
		int commaNumber = 0;
		int strLen = commaString.length();
		
		for( int i = 0; i < strLen - 1; i ++){
			if(commaString.charAt(i) == ','){
				commaNumber++;
			}
		}
		//now have the correct number of commas, of which the item in string rep should have 3 with none at the end
		commaNumber = commaNumber - 3; //sets it to the correct amount	
		
		for( int i = 0; i < commaNumber ; i++){
			itemString = itemString.concat(","); // adds commas
		}
		//now add to the list and sort
		
		catalogueArray.add(itemString);
		Collections.sort(catalogueArray);
		
		result = true;
		return result;
	}
	
	
	public boolean addItem(String itemString){
		boolean result = false;
		
		String commaString = catalogueArray.get(2);
		int commaNumber = 0;
		int strLen = commaString.length();
		
		for( int i= 0; i< strLen - 1; i++){
			if(commaString.charAt(i) == ','){
				commaNumber++;
			}
		}
		commaNumber = commaNumber - 3;
		
		for( int i = 0; i <commaNumber ; i++){
			itemString = itemString.concat("'");
		}
		
		catalogueArray.add(itemString);
		Collections.sort(catalogueArray);
		result = true;
		return result;
	}
	
	public boolean matches(Item item){
		//should be able to pull an item from an index of the catalogue		
	}
}	








