package test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import pij.main.Bag;
import pij.main.Board;
import pij.main.HumanPlayer;
import pij.main.LetterTile;
import pij.main.Tile;
import pij.main.Validator;

import java.io.*;
import java.util.*;

public class ScrabbleTest {
	
	@Test
	public void testTile() {
		Tile blankTile = new Tile(".", 0);
		Tile premiumTile = new Tile("(3)", 3);
		LetterTile letterTile = new LetterTile("A", 1);
		
		//Tests the getText method.
		assertEquals(blankTile.getText(), " . ");
		assertEquals(premiumTile.getText(), "(3)");
		assertEquals(letterTile.getChar(), 'A');
		
		//Tests the getValue method.
		assertEquals(blankTile.getValue(), 0);
		assertEquals(premiumTile.getValue(), 3);
		assertEquals(letterTile.getValue(), 1);
	}
	
	@Test
	public void testBag() {
		
		Bag bag = new Bag();
		int finalTileReached = 0;
		for (int i = 0; i < 100; i++) {
			LetterTile tile = bag.draw();
			if (tile.getChar() == ' ') finalTileReached++;
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
		Board board = Validator.loadFile("./resources/testBoard.txt");
		
		Tile lower = board.tileAt(0,0);
		Tile upper = board.tileAt(14,14);
		
		//Tests the lower and upper bounds of the grid.
		assertEquals(Tile.class, lower.getClass());
		assertEquals(Tile.class, upper.getClass());
		
		//Tests the correct tile is being referenced by getText
		assertEquals("{7}", lower.getText());
		assertEquals("(50)", board.tileAt(5,1).getText());
		
		LetterTile t = new LetterTile("T", 1);
		LetterTile e = new LetterTile("E", 1);
		LetterTile s = new LetterTile("S", 1);
		LetterTile t2 = new LetterTile("T", 1);
		LetterTile[] test = new LetterTile[] {t, e, s, t2};
		LetterTile[] tT = new LetterTile[] {t};
		LetterTile[] te = new LetterTile[] {t, e};
		
		assertFalse(board.placeWord(0, 0, 'd', test));
		assertFalse(board.placeWord(7, 3, 'd', test));
		assertTrue(board.placeWord(7, 4, 'd', test));
		for (int i = 4; i < 8; i++) {
			assertEquals(board.tileAt(7, i).getClass(), LetterTile.class);
			assertEquals(board.tileAt(7-3+i, 4).getClass(), Tile.class);
		}
		assertFalse(board.placeWord(0, 0, 'd', test));
		assertFalse(board.placeWord(0, 5, 'r', test));
		assertTrue(board.placeWord(4, 5, 'r', test));

		
		//Tests the placement of words on a blank board
//		assertTrue(board.placeWord(0, 0, 'r', test));
//		for (int i = 0; i < 4; i++) {
//			assertEquals(board.tileAt(i, 0).getClass(), LetterTile.class);
//			assertEquals(board.tileAt(0, i+1).getClass(), Tile.class);
//		}
//		assertTrue(board.placeWord(0, 14, 'r', test));
//		assertTrue(board.placeWord(14, 0, 'r', tT));
//		assertTrue(board.placeWord(14, 14, 'r', tT));
//		assertFalse(board.placeWord(14, 15, 'r', tT));
//		assertFalse(board.placeWord(15, 0, 'r', tT));
//		assertFalse(board.placeWord(14, 0, 'r', test));
//		assertFalse(board.placeWord(14, 0, 'r', te));
//		assertFalse(board.placeWord(100, 0, 'r', test));
//		
//		
//		
//		board = new Board(file);
//		assertTrue(board.placeWord(0, 0, 'd', test));
//		assertTrue(board.placeWord(14, 0, 'd', test));
//		assertTrue(board.placeWord(0, 14, 'd', tT));
//		assertTrue(board.placeWord(14, 14, 'd', tT));
//		assertFalse(board.placeWord(15, 14, 'd', tT));
//		assertFalse(board.placeWord(0, 15, 'd', tT));
//		assertFalse(board.placeWord(0, 14, 'd', test));
//		assertFalse(board.placeWord(0, 14, 'd', te));
//		assertFalse(board.placeWord(0, 100, 'd', test));
		
		
	}
	
	@Test
	public void testPlayer() {
		Bag bag = new Bag();
		HumanPlayer human = new HumanPlayer();
		
		
		
	}
	
	
}
