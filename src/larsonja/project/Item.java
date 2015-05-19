package larsonja.project;

public class Item {
	/*
	 * This class represents an item to be used in a basic inventory system
	 * 
	 * @author Jake Larson
	 * 
	 */
	
	private double count;
	private String name;
	private double desiredAmountA;
	private double desiredAmountB;
	private int flag;	

	/**
	 * Constructor for the Item class
	 * 
	 * @param name - the name of the item
	 * @param count - the amount of the item
	 * @param desiredAmountA - the desired amount of the item for location A
	 * @param desiredAmountB - the desired amount of the item for location B
	 */
	public Item(String name, double count, double desiredAmountA, double desiredAmountB){
		this.name = name;
		this.count = count;
		this.desiredAmountA = desiredAmountA;
		this.desiredAmountB = desiredAmountB; 
		this.flag = 0; //default
	}
	
	/**
	 * Alt constructor to make an item without a count associated with it
	 * 
	 * @param name - the name of the item
	 * @param desiredAmountA - the desired amount of the item for location A
	 * @param desiredAmountB - the desired amount of the item for location B
	 */
	public Item(String name, double desiredAmountA, double desiredAmountB){
		this.name = name;
		this.desiredAmountA = desiredAmountA;
		this.desiredAmountB = desiredAmountB; 
		this.flag = 0; //default
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
	 * Method to return the count of the item called on
	 * @return - the count
	 */
	public double getCount(){
		double i = new Double(this.count);
		return i;
	}
	
	/**
	 * Method to set the count of an item
	 * @param count - value of the item counted
	 * @return true if count is set correctly, false otherwise
	 */
	public boolean setCount(double count){
		this.count = count;
		if(this.count == count)
			return true;
		else
			return false;
	}
	
	/**
	 * Method to set the count of an item
	 * @param - value of the item counted
	 * @return true if count is set correctly, false otherwise
	 */
	public boolean setCount(int count){
		this.count = (double) count;
		if(this.count == (double) count)
			return true;
		else
			return false;
	}
	
	/**
	 * Method to return the desired amount at A
	 * @return - will return the amount if > 0, otherwise returns 0
	 */
	public double getAmountA(){
		double result = this.desiredAmountA - this.count;
		if(result > 0){
			return result;
		} else {
			return 0;
		}
	}
	
	/**
	 * Method to return the desired amount at A
	 * @return - will return the amount if > 0, otherwise returns 0
	 */
	public double getAmountB(){
		double result = this.desiredAmountB - this.count;
		if(result > 0){
			return result;
		} else {
			return 0;
		}
	}
	
	/**
	 * Method to return both amounts if you aren't sure which you'll need
	 * @return - double array of size 2 with A in index 0 and B in index 2
	 */
	public double[] getAmounts(){
		double[] d = new double[2];
		d[0] = this.getAmountA();
		d[1] = this.getAmountB();		
		return d;
	}
	
	/**
	 * Method to get the flag of an item
	 * @return the flag value (high or low)
	 */
	public int getFlag(){
		int result = this.flag;
		return result;
	}
	
	/**
	 * Method to set the flag on the item as high
	 * @return true if flag set correctly as high
	 */
	public boolean setFlag(){
		this.flag = 1;
		if (this.flag == 1)
			return true;
		else
			return false;
	}
	
	/**
	 * Method to reset the flag on the item as low
	 * @return true if flag set correctly as low
	 */
	public boolean rmFlag(){
		this.flag = 0;
		if (this.flag == 0)
			return true;
		else
			return false;
	}
	
	public String toString(){
		String result = null;
		
		/*
		 * Count should be moved over into being tracked in the Catalogue class but was here for ease of use in development
		 * TODO look into this issue in the future
		 */
		
		String name = this.name;
		String flag = String.valueOf(this.getFlag());
		String desA = String.valueOf(this.desiredAmountA);
		String desB = String.valueOf(this.desiredAmountB);
		
		result = name;
		result = result.concat("," + flag);
		result = result.concat("," + desA);
		result = result.concat("," + desB);
		
		return result;		
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(count);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(desiredAmountA);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(desiredAmountB);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (Double.doubleToLongBits(count) != Double
				.doubleToLongBits(other.count))
			return false;
		if (Double.doubleToLongBits(desiredAmountA) != Double
				.doubleToLongBits(other.desiredAmountA))
			return false;
		if (Double.doubleToLongBits(desiredAmountB) != Double
				.doubleToLongBits(other.desiredAmountB))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
