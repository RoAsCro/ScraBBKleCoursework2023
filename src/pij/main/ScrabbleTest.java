package pij.main;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.*;

public class ScrabbleTest {
	
	
	
	@Test
	public void testBag() {
		
		Bag bag = new Bag();
		int finalTileReached = 0;
		for (int i = 0; i < 100; i++) {
			Tile tile = bag.draw();
			if (tile.getText().equals(" ")) finalTileReached++;
			//System.out.println(tile.getText());
		}
		//Tests whether the final tile in the bag is reached by the draw() method
		assertEquals(2, finalTileReached);
		//Tests whether the bag has 100 tiles in it and attempting to draw an additional tile returns a null value
		assertNull(bag.draw());	
	}
	
	@Test
	public void testBoard() {

		File file = new File("./resources/testBoard.txt");
		Board board = new Board(file);
		
		Tile lower = board.tileAt(0,0);
		Tile upper = board.tileAt(14,14);
		
		//Tests the lower and upper bounds of the grid.
		assertEquals(Tile.class, lower.getClass());
		assertEquals(Tile.class, upper.getClass());
		
		//Tests the correct tile is being referenced by getText
		assertEquals("{7}", lower.getText());
		assertEquals("(50)", board.tileAt(5,1).getText());
		
		
		
	}
	
}
