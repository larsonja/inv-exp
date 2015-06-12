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
	/*
	 * want format to be:
	 * name, unit, flag, desiredA, desiredB, count1, count2, count3, etc...
	 * flag can be used for whatever, but this one will be used as an override on the trend stuff being done later
	 */
	BufferedReader catalogueReader;
	Writer catalogueWriter;
	ArrayList<String> catalogueArray; //each string will be one line of text, where index is the line of the file originally ended by a null character
	// TODO should add a grouping of ITEMs that will be used for comparison and searching
	
	
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
				
				for(int i = 0;  line != null; i++){
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
	 * Needs to manipulate file
	 * add item /done
	 * remove item /done - way easier
	 * delete item (one removes it from view (hides it but keeps the info) and the other fully deletes it) TODO add flag to deal with this
	 * flag an item
	 */
	
	
	public boolean removeItem(Item item){
		boolean result = false;
		String itemName = item.getName();
		int minArray = 0;
		int maxArray = this.catalogueArray.size() - 1; //because arrays start at 0
		
		int indexRemoveAt = binary_search(this.catalogueArray, itemName, minArray, maxArray);
		
		for(int index = indexRemoveAt ; index < maxArray - 1 ; index++){
			this.catalogueArray[index] = this.catalogueArray[index + 1];
		} //will override the indexRemoveAt variable then keeps everything else, moving it down
		
		result = true;
		return result;
	}
	
	/**
	 * Method to add an item to a catalogue
	 * @param item - the item to be added
	 * @return true if the item is added correctly, false otherwise
	 */
	public boolean addItem(Item item){
		boolean result = false;
		
		String itemName = item.getName();
		int minArray = 0;
		int maxArray = this.catalogueArray.length - 1; //because arrays start at 0
		
		int indexAddAt = binary_search(this.catalogueArray, itemName, minArray, maxArray);
		
		//Now have the index it should be added at so start moving things down
		String itemString = item.toString();
		
		//TODO look over this because it's wrong
		String tempLineA = this.catalogueArray[indexAddAt]; //preload it 
		String tempLineB;
		
		for(int index = indexAddAt ; indexAddAt < maxArray ; index++){
			
			tempLineB = this.catalogueArray[index+1];
			
			this.catalogueArray[index+1] = tempLineA;
			
			tempLineA = tempLineB;
			
		}
		this.catalogueArray[indexAddAt] = itemString; //item is now in the correct array
		
		result = true;
		return result;
	}
	
	/**
	 * Method to find index of where a word should go in an array of strings by lexigraphical ordering
	 * @param field - the array of strings to search through
	 * @param KEY - the string you're trying to add
	 * @param MIN - the min index
	 * @param MAX - the max index
	 * @return the index at which your KEY is, -1 if key not found
	 */
	private int binary_search(String[] field, String KEY, int MIN, int MAX){ //TODO think there's an error here re: comparing full item string to just the name/using startsWith
		
		if(MAX < MIN){ //because i want this to return where it would go, i then need to return the previous MID value here
			return -1;
		} else {
			int MID = (MAX + MIN)/2; //will truncate value effectively rounding down always
			
			if( field[MID].compareTo(KEY) > 0){
				--MID;
				return binary_search(field, KEY, MIN, MID);
			} else if ( field[MID].compareTo(KEY) < 0){
				++MID;
				return binary_search(field, KEY, MID, MAX);
			} else {
				 //key is found
				return MID;
			}
			
		}
	}
}	