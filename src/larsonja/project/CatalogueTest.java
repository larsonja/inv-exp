package larsonja.project;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class CatalogueTest {
	
	//Create items
	Item testItemA = new Item("test1", 0, 0, 0);
	Item testItemB = new Item("test2", 0, 0, 1);
	Item testItemC = new Item("test3", 1, 0, 0);
	Item testItemD = new Item("test4", 4, 1, 1);

	//Create catalogue to test with using the items
	String testLocation = "testLocation" ;
	
	Catalogue testCata = new Catalogue(testLocation);
	
	
	
	
	@Test
	public final void testCatalogue() {
		testCata.addItem(testItemA);
		testCata.addItem(testItemB);
		testCata.addItem(testItemC);
		testCata.addItem(testItemD);
		
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testRemoveItem() {
		fail("Not yet implemented"); // TODO
	}

	/* Gets tested at the start, so unnecessary.
	@Test
	public final void testAddItem() {
		fail("Not yet implemented");
	}
	*/
}
