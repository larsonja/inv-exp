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
	//private double amountOrders; //currently unused but will be used later
	

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
	 * Method to return the desired amount at A
	 * @return - will return the amount if > 0, otherwise returns 0
	 */
	public double getAmountA(){
		double d = this.desiredAmountA - this.count;
		if(d > 0){
			return d;
		} else {
			return 0;
		}
	}
	
	/**
	 * Method to return the desired amount at A
	 * @return - will return the amount if > 0, otherwise returns 0
	 */
	public double getAmountB(){
		double d = this.desiredAmountB - this.count;
		if(d > 0){
			return d;
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
