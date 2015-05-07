package larsonja.project;

import org.junit.Test;

/**
 * Basic testing of the Item class to ensure functionality
 * @author Jake
 *
 */


public class ItemTest {
	
	Item testItemA = new Item("test1", 0, 0, 0);
	Item testItemB = new Item("test2", 0, 0, 1);
	Item testItemC = new Item("test3", 1, 0, 0);
	Item testItemD = new Item("test4", 4, 1, 1);
	

	@Test
	public final void testItem() {
		assert( testItemA != null);
	}

	@Test
	public final void testGetName() {
		assert((testItemB.getName() == "test2") && (testItemD.getName() == "test4"));
	}

	@Test
	public final void testGetCount() {
		assert((testItemB.getCount() == 0) && (testItemD.getCount() == 4));
	}

	@Test
	public final void testGetAmountA() {
		assert((testItemD.getAmountA() == 3) && (testItemB.getAmountA() == 0));
	}

	@Test
	public final void testGetAmountB() {
		assert((testItemA.getAmountB() == 0) && (testItemB.getAmountB() == 1));
	}

	@Test
	public final void testGetAmounts() {
		double[] testValues = {0,0};
		double[] methodValues = testItemA.getAmounts();
		assert(methodValues.equals(testValues));
		
		double[] testValuesB= {3,3};
		methodValues = testItemD.getAmounts();
		assert(methodValues.equals(testValuesB));
	}

}