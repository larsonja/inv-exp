package larsonja.project;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


public class Catalogue {
	/*
	 * This class represents a catalogue to be used in an inventory system.
	 * 
	 * 
	 * @author Jake Larson
	 * 
	 * Free for use in public and free applications, for other uses contact
	 * the author at larsonjakea@gmail.com regarding permissions
	 */
	
	private String date;
	private HashMap<Integer,Item> Inventory = new HashMap<Integer,Item>();
	private HashMap<Integer,Integer> Stock = new HashMap<Integer,Integer>();

	/**
	 * method to add an item to the inventory and stock lists
	 * @param item - Item to add to the list
	 * @return - true if added correctly to both, false otherwise
	 */
	public boolean addItem(Item item){
		
		boolean result = false;
		
		Inventory.put(item.getId(), item);
		Stock.put(item.getId(), -1);
		if(Inventory.containsKey(item.getId()) && Stock.containsKey(item.getId())){
			result = true;
		}
		
		return result; 
	}
	
	/**
	 * method to add a stock value to an item by their id
	 * @param id - id of item to add stock value to
	 * @param stock - the value of stock
	 * @return - true if value is added correctly, false otherwise
	 */
	public boolean addStock(Integer id, Integer stock){
		boolean result = false;
		
		if(Stock.containsKey(id)){
			Stock.remove(id);
			Stock.put(id, stock);
			result = true;
		} else if(!Stock.containsKey(id)){
			if(Inventory.containsKey(id)){
				Stock.put(id, stock);
				result = true;
			}
		}
		
		return result;
	}
	
	/**
	 * method to add a stock value to an item from their item directly
	 * @param item - the item to have the value of stock attributed to
	 * @param stock - the value of stock
	 * @return - true if value is added correctly, false otherwise
	 */
	public boolean addStock(Item item, Integer stock){
		boolean result = false;
		
		if(Stock.containsKey(item.getId())){
			Stock.remove(item.getId());
			Stock.put(item.getId(),stock);
			result = true;
		} else if(!Stock.containsKey(item.getId())){
			if(Inventory.containsKey(item.getId())){
				Stock.put(item.getId(),stock);
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * method ot remove an item from the catalogue
	 * @param item - the item to be removed
	 * @return - true if removed correclty, false otherwise
	 */
	public boolean removeItem(Item item){
		boolean result = false;
		
		if(Inventory.containsKey(item.getId())){
			Inventory.remove(item.getId());
			if(Stock.containsKey(item.getId())){
				Stock.remove(item.getId());
				result = true;
			}
		} else if (Stock.containsKey(item.getId())){
			Stock.remove(item.getId());
			result = true;
		}
		
		return result;
	}
	
	/**
	 * method to return an item from it's id
	 * @param id - the id of the item to get
	 * @return - the item
	 */
	public Item getItem(Integer id){
		return Inventory.get(id);
	}
	
	/**
	 * method to return the stock value of an item by it's id
	 * @param id - the id of the item
	 * @return - the value of stock
	 */
	public Integer getStock(Integer id){
		return Stock.get(id);
	}
	
	/**
	 * method to return the stock value of an item
	 * @param item - the item who's stock you want
	 * @return - the stock value
	 */
	public Integer getStock(Item item){
		return Stock.get(item.getId());
	}
	
	/**
	 * method for setting the date of the catalogue
	 * @param date - date in format dd/mm/yyyy
	 * @return - true if date added correctly, otherwise false
	 */
	public boolean setDate(String date){
		boolean result = false;
		String tempString = "";
		if(date.contains("-")){
			tempString = date.replace('-','/');
			tempString = tempString.trim();
		} else if (date.contains(".")){
			tempString = date.replace('.','/');
			tempString = tempString.trim();
		} else if (date.contains(" ")){
			tempString = date.trim();
			tempString = tempString.replace(' ','/');
		}
		
		if(tempString.length() != 0){
			this.date = tempString;
			result = true;
		}
		
		return result;
	}
	
	/**
	 * method to return a list of items included in the catalogue
	 * @return - a list of items
	 */
	public List<Item> getList(){
		Collection<Item> c = Inventory.values();
		ArrayList<Item> result = new ArrayList<Item>();
		
		for( Item i : c){
			result.add(i);
		}
		
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((Inventory == null) ? 0 : Inventory.hashCode());
		result = prime * result + ((Stock == null) ? 0 : Stock.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Catalogue other = (Catalogue) obj;
		if (Inventory == null) {
			if (other.Inventory != null)
				return false;
		} else if (!Inventory.equals(other.Inventory))
			return false;
		if (Stock == null) {
			if (other.Stock != null)
				return false;
		} else if (!Stock.equals(other.Stock))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	}
}
