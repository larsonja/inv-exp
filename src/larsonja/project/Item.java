package larsonja.project;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Item {
	/*
	 * This class represents an item to be used in the inventory system.
	 * Name as well as a measurement value.
	 * 
	 * @author Jake Larson
	 * 
	 * Free for use in public and free applications, for other uses contact
	 * the author at larsonjakea@gmail.com regarding permissions
	 */
	
	private final Integer id;
	private final String date;
	private String url;
	private final String name;
	private String measure;
	private Integer setAmount;
	private static ArrayList<String> measurements = new ArrayList<String>();
	private static Integer count = -1;

	/**
	 * Constructor for the Item class
	 * 
	 * @param name - the name of the item being added as a string
	 * @param measure - the unit of measurement for the item
	 * @throws IllegalArgumentException - when the measurement isn't in the default list
	 * 		- consider adding the measurement type with the setMeasure method
	 */
	public Item(String name, String measure) throws IllegalArgumentException{
		count = count + 1;
		this.id = count;
		this.name = name.toLowerCase();
		
		if(measurements.isEmpty()){
			measurements.add("rolls");
			measurements.add("inches");
			measurements.add("cm");
			measurements.add("m");
			measurements.add("box");
			measurements.add("each");
			measurements.add("package");
			measurements.add("boxes");
			measurements.add("container");
			measurements.add("lbs");
			measurements.add("kg");
			measurements.add("tube");
			measurements.add("jug");
		} 
		if(measurements.contains(measure.toLowerCase())){
			this.measure = measure.toLowerCase();
		} else {
			throw new IllegalArgumentException("Measurement type not correct, either add it or change it");
		}
		
		DateFormat dateformat = new SimpleDateFormat("dd-MMM-yy HH:mm:ss");
		Date date = new Date();
		this.date = dateformat.format(date);
	}
	
	/**
	 * Method to get the stored name of the item
	 * @return - the name
	 */
	public String getName(){
		String s = new String(this.name);
		return s;
	}
	
	/**
	 * Method to return the date and time the item was added to the database
	 * @return - the initialization date of the item
	 */
	public String getDate(){
		String s = new String(this.date);
		return s;
	}
	
	/**
	 * Method to add a url for the item
	 * @param url - the full url of the web page associated to the item
	 * @return true if the url was added correctly, false otherwise
	 */
	public boolean setUrl(String url){
		boolean result = false;
		
		//can't handle error handling properly because of how urls are typed
		//should suggest to just copy-paste from the address bar
		this.url = url;
		if( this.url.equals(url)){
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Method to return the url of the Item called on
	 * @return - url
	 */
	public String getUrl(){
		String s = new String(this.url);
		return s;
	}
	
	/**
	 * Method to set the measurement type of an item
	 * @param measurement - the name of the measurement
	 * @return - true if set correctly, false otherwise
	 */
	public boolean setMeasure(String measurement){
		boolean result = false;
		
		if(measurements.contains(measurement.toLowerCase())){
			this.measure = measurement.toLowerCase();
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Method to return the measurement of the item called on
	 * @return - the measure
	 */
	public String getMeasure(){
		String s = new String(this.measure);
		return s;
	}
	
	/**
	 * Method to add a measurement option to the list of available measurements
	 * @param measurement - the measurement to be added
	 * @return - true if added correctly or already containing the measurement, otherwise false
	 */
	public boolean addMeasurement(String measurement){
		boolean result = false;
		
		if(!measurements.contains(measurement.toLowerCase())){
			measurements.add(measurement);
			result = true;
		} else {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Method for getting the unique id of an item
	 * @return the id
	 */
	public Integer getId(){
		Integer result = this.id;
		return result;
	}
	
	public Integer getSetAmount(){
		Integer result = this.setAmount;
		return result;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((measure == null) ? 0 : measure.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		Item other = (Item) obj;
		if (measure == null) {
			if (other.measure != null)
				return false;
		} else if (!measure.equals(other.measure))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
}
