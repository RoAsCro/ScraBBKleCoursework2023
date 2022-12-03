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
		assertEquals(null, bag.draw());
		
	}
	
}
