package larsonja.project;

public class Item {
  /*
   * This class represents an item to be used in a basic inventory system
   * 
   * @author Jake Larson
   */

  private double  count;
  private String  name;
  private double  desiredAmountA;
  private double  desiredAmountB;
  private boolean flag;

  /**
   * Constructor for the Item class
   * 
   * @param name
   *          - the name of the item
   * @param count
   *          - the amount of the item
   * @param desiredAmountA
   *          - the desired amount of the item for location A
   * @param desiredAmountB
   *          - the desired amount of the item for location B
   */
  public Item(String name, double count, double desiredAmountA, double desiredAmountB) {
    this.name = name;
    this.count = count;
    this.desiredAmountA = desiredAmountA;
    this.desiredAmountB = desiredAmountB;
    this.flag = false; 
  }

  /**
   * Alt constructor to make an item without a count associated with it
   * 
   * @param name
   *          - the name of the item
   * @param desiredAmountA
   *          - the desired amount of the item for location A
   * @param desiredAmountB
   *          - the desired amount of the item for location B
   */
  public Item(String name, double desiredAmountA, double desiredAmountB) {
    this.name = name;
    this.desiredAmountA = desiredAmountA;
    this.desiredAmountB = desiredAmountB;
    this.flag = false; // default
  }

  public String getName() {
    return new String(this.name);
  }

  public double getCount() {
    return new Double(this.count);
  }

  /**
   * Method to set the count of an item
   * 
   * @param count
   *          - value of the item counted
   * @return true if count is set correctly, false otherwise
   */
  public boolean setCount(double count) {
    this.count = count;
    if (this.count == count)
      return true;
    else
      return false;
  }

  /**
   * Method to set the count of an item
   * 
   * @param - value of the item counted
   * @return true if count is set correctly, false otherwise
   */
  public boolean setCount(int count) {
    this.count = (double) count;
    if (this.count == (double) count)
      return true;
    else
      return false;
  }

  /**
   * Method to return the desired amount at A
   * 
   * @return - will return the amount if > 0, otherwise returns 0
   */
  public double getAmountA() {
    double result = this.desiredAmountA - this.count;
    if (result > 0) {
      return result;
    } else {
      return 0;
    }
  }

  /**
   * Method to return the desired amount at A
   * 
   * @return - will return the amount if > 0, otherwise returns 0
   */
  public double getAmountB() {
    double result = this.desiredAmountB - this.count;
    if (result > 0) {
      return result;
    } else {
      return 0;
    }
  }

  public double[] getAmounts() {
    double[] d = new double[2];
    d[0] = this.getAmountA();
    d[1] = this.getAmountB();
    return d;
  }

  public boolean getFlag() {
    return this.flag;
  }

  public boolean setFlag(boolean state) {
    this.flag = state;
    return true;
  }

  public String toString() {
    String result = null;

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

  public Item toItem(String strRep) {
    Item returnItem;
    String[] parts = strRep.split(",");

    returnItem = new Item(parts[0], Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));

    return returnItem;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    long temp;
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
    if (Double.doubleToLongBits(desiredAmountA) != Double.doubleToLongBits(other.desiredAmountA))
      return false;
    if (Double.doubleToLongBits(desiredAmountB) != Double.doubleToLongBits(other.desiredAmountB))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }

}
