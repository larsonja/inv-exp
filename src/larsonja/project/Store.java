package larsonja.project;

import java.util.HashMap;

public final class Store {
	
	private String date;
	private HashMap<Integer, Catalogue> yearsStock = new HashMap<Integer, Catalogue>();
	
	/*
	 * use buffered reader to read all the info to take it from a file
	 * store info through this one to have all the info backup-able
	 * 
	 * have old changes recorded to files (anything over 1 week old)
	 * to prevent f u c k e r y 
	 * 
	 * be able to:
	 * add a year
	 * add a month's catalogue
	 * return a year of stock
	 * return a set year/month's stock
	 * store to file
	 * restore from file
	 * 	will have immutable catalogue and items afterwards
	 *  otherwise would have to have a restore in the item class to get them back
	 *  might be worth it to look into this though, it's a nice feature
	 * 
	 * need an orders class as well to determine orders based on standard deviation
	 * this will extend the item class with the deviation or set amount
	 * depending on input
	 * 
	 * shits good
	 */
	
}
