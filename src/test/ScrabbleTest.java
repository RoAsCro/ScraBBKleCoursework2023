package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import pij.main.*;

import java.io.*;
import java.util.*;

public class ScrabbleTest {

	@Test
	public void testTile() {
		Tile blankTile = new Tile(".", 0);
		Tile premiumTile = new Tile("(3)", 3);
		LetterTile letterTile = new LetterTile("A", 1);
		WildTile w = null;

		Validator.loadDictionary(new File("./resources/wordlist.txt"));
		assertFalse(LetterTile.class.isInstance(w));

		// Tests the getText method.
		assertEquals(blankTile.toString(), " . ");
		assertEquals(premiumTile.toString(), "(3)");
		assertEquals(letterTile.getChar(), 'A');

		// Tests the getValue method.
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
			if (tile.getChar() == ' ')
				finalTileReached++;
			// System.out.println(tile.getText());
		}
		// Tests whether the final tile in the bag is reached by the draw() method
		assertEquals(2, finalTileReached);
		// Tests whether the bag has 100 tiles in it and attempting to draw an
		// additional tile returns a null value
		assertNull(bag.draw());

		// Tests functionality of custom bag constructor
		bag = new Bag(new int[] { 1 });
		assertEquals('A', bag.draw().getChar());

		bag = new Bag(new int[] { 0, 1 });
		assertEquals('B', bag.draw().getChar());
		for (int i = 0; i < 3; i++) {

		}

	}

//	@Test
//	public void testMove() {
//		Bag riggedBag = new Bag(new int[] { 6, 0, 1 });
//		HumanPlayer human = new HumanPlayer();
//		human.draw(riggedBag);
//
//		// Tests for invalid moves
//		Move move = new Move("", human);
//		assertFalse(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move(",,", human);
//		assertTrue(move.isValid());
//		assertTrue(move.isPass());
//
//		// Tests that tiles are in the player's rack
//		move = new Move("B,a1,r", human);
//		assertFalse(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move("CC,a1,r", human);
//		assertFalse(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move("AAAAAAA,a1,r", human);
//		assertFalse(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move("A,a1,r", human);
//		assertTrue(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move("C,a1,r", human);
//		assertTrue(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move("AA,a1,r", human);
//		assertTrue(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move("AC,a1,r", human);
//		assertTrue(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move("ACA,a1,r", human);
//		assertTrue(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move(",a1,r", human);
//		assertFalse(move.isValid());
//		assertFalse(move.isPass());
//
//		// Tests reaction to improperly formatted inputs
//		move = new Move(",,,", human);
//		assertFalse(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move("A,a3,r,d", human);
//		assertFalse(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move(",,,,,", human);
//		assertFalse(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move(".awfa.2df3w", human);
//		assertFalse(move.isValid());
//		assertFalse(move.isPass());
//
//		// Tests the check that the coordinates are correct
//		move = new Move("A,11,r", human);
//		assertFalse(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move("A,,r", human);
//		assertFalse(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move("A,a,r", human);
//		assertFalse(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move("A,a1a,r", human);
//		assertFalse(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move("A,222,r", human);
//		assertFalse(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move("ACA,a1,r", human);
//		assertTrue(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move("ACA,A1,r", human);
//		assertTrue(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move("ACA,p23,r", human);
//		assertTrue(move.isValid());
//		assertFalse(move.isPass());
//
//		// Tests the direction check
//		move = new Move("A,a1,", human);
//		assertFalse(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move("A,a1,R", human);
//		assertTrue(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move("A,a1,r", human);
//		assertTrue(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move("A,a1,d", human);
//		assertTrue(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move("A,a1,q", human);
//		assertFalse(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move("A,a1,3", human);
//		assertFalse(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move("A,a1,,", human);
//		assertFalse(move.isValid());
//		assertFalse(move.isPass());
//
//		move = new Move("A,a1,.", human);
//		assertFalse(move.isValid());
//		assertFalse(move.isPass());
//
//	}

//	@Test
//	public void testPlayer() {
//		Bag bag = new Bag();
//		HumanPlayer human = new HumanPlayer();
//		// Test draw function
//		human.draw(bag);
//		ArrayList<LetterTile> tilesOne = new ArrayList<>(human.getRack());
//		assertEquals(tilesOne.size(), 7);
//		human.draw(bag);
//		ArrayList<LetterTile> tilesTwo = new ArrayList<>(human.getRack());
//		assertTrue(tilesOne.containsAll(tilesTwo));
//
//		human.removeTiles(new LinkedList<LetterTile>(tilesOne));
//		assertEquals(human.getRack().size(), 0);
//	}

	@Test
	public void testBoard() {
		// File file = new File("./resources/testBoard.txt");
		Board board = Validator.loadFile("./resources/testBoard.txt");
		Bag riggedBag = new Bag(new int[] { 1, 1, 1, 1, 1, 1, 1 });
		HumanPlayer human = new HumanPlayer(board);
		Validator.loadDictionary(new File("./resources/wordlist.txt"));

		Bag riggedBag2 = new Bag(
				new int[] { 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0 });
		HumanPlayer human2 = new HumanPlayer(board);
		human2.draw(riggedBag2);
		Board board2 = Validator.loadFile("./resources/testBoard.txt");
//		Move move2 = new Move("VEIN,g8,r", human2);
//		assertTrue(move2.isValid());
//		assertTrue(board2.placeWord(move2));

		System.out.println("Dict: " + Validator.lookupWord("vein"));

		Tile lower = board.tileAt(0, 0);
		Tile upper = board.tileAt(14, 14);
		Tile outOfBounds = board.tileAt(-1, -1);
		Tile outOfBoundsTwo = board.tileAt(15, 15);

		// Tests the lower and upper bounds of the grid, plus tileAt function.
		assertEquals(Tile.class, lower.getClass());
		assertEquals(Tile.class, upper.getClass());
		assertEquals(null, outOfBounds);
		assertEquals(null, outOfBoundsTwo);
		assertFalse(Tile.class.isInstance(outOfBounds));
		assertFalse(Tile.class.isInstance(outOfBoundsTwo));

		// Tests the correct tile is being referenced by getText
		assertEquals("{7}", lower.toString());
		assertEquals("(50)", board.tileAt(5, 1).toString());

		// Tests initial word placement
		human.draw(riggedBag);
		Move move = new Move(human, board);
        assertFalse(move.validateInput("FACE,a1,r"));


        move = new Move(human, board);
        assertFalse(move.validateInput("FACE,d1,r"));

        move = new Move(human, board);
        assertFalse(move.validateInput("FACE,c4,d"));

        move = new Move(human, board);
        assertTrue(move.validateInput("FACE,e8,r"));


		board = Validator.loadFile("./resources/testBoard.txt");


        move = new Move(human, board);
        assertTrue(move.validateInput("FACE,h8,r"));


        // Tests intersection test
        move = new Move(human, board);
        assertFalse(move.validateInput("FACE,a1,r"));

        move = new Move(human, board);
        assertFalse(move.validateInput("FACE,a1,d"));

        move = new Move(human, board);
        assertTrue(move.validateInput("CE,i8,d"));

        move = new Move(human, board);
        assertTrue(move.validateInput("FAC,k5,d"));

        move = new Move(human, board);
        assertTrue(move.validateInput("FCE,j6,r"));

        move = new Move(human, board);
        assertTrue(move.validateInput("F,i7,d"));

        move = new Move(human, board);
        assertTrue(move.validateInput("D,k5,d"));

        move = new Move(human, board);
        assertTrue(move.validateInput("BADG,m2,d"));


//		// Tests not forming two words at once
        move = new Move(human, board);
        assertFalse(move.validateInput("FAE,l4,d"));

//		// Tests double intersection
        board.print();
        move = new Move(human, board);
        assertTrue(move.validateInput("DE,k3,d"));

        move = new Move(human, board);
        assertTrue(move.validateInput("FE,j4,r"));

//		// Tests out of bounds right

        move = new Move(human, board);
        assertTrue(move.validateInput("AB,m5,r"));

        move = new Move(human, board);
        assertFalse(move.validateInput("ED,k3,r"));

        move = new Move(human, board);
        assertFalse(move.validateInput("CE,o6,r"));


//		// Tests dictionary is taking into account all letters

        move = new Move(human, board);
        assertFalse(move.validateInput("BADGE,o5,d"));
//
//		// Tests out of bounds down
//		move = new Move("ADGE,o5,d", human);
//		assertTrue(board.placeWord(move));
//
//		move = new Move("FAC,l9,r", human);
//		assertTrue(board.placeWord(move));
//
//		move = new Move("ACED,l9,d", human);
//		assertTrue(board.placeWord(move));
//
//		move = new Move("EAF,l13,r", human);
//		assertTrue(board.placeWord(move));
//
//		move = new Move("ACED,o13,d", human);
//		assertFalse(board.placeWord(move));
//
//		move = new Move("AD,o13,d", human);
//		assertTrue(board.placeWord(move));
//
//		move = new Move("FA,m15,r", human);
//		assertTrue(board.placeWord(move));
//
//		// Tests appending a suffix

//		move = new Move("D,i11,d", human);
//		assertFalse(board.placeWord(move));
//
//		move = new Move("D,i10,d", human);
//		assertFalse(board.placeWord(move));
//
//		move = new Move("D,i9,d", human);
//		assertFalse(board.placeWord(move));
//
//		move = new Move("D,i8,d", human);
//		assertFalse(board.placeWord(move));
//
//		move = new Move("D,i7,d", human);
//		assertTrue(board.placeWord(move));
//
//		// Tests appending a prefix to a word already on the board and ending at the
//		// edge
//
//		move = new Move("A,n7,r", human);
//		assertTrue(board.placeWord(move));
//
//		move = new Move("AG,j12,r", human);
//		assertTrue(board.placeWord(move));
//
//		move = new Move("CED,j12,d", human);
//		assertTrue(board.placeWord(move));
//
//		move = new Move("CAGE,f15,r", human);
//		assertTrue(board.placeWord(move));
//
//		move = new Move("BA,h13,d", human);
//		assertTrue(board.placeWord(move));

		board.print();

//		//board = Validator.loadFile("./resources/testBoard.txt");
//		ComputerPlayer cpu = new ComputerPlayer(board);
//		Bag riggedBagTwo = new Bag(/*new int[]{0,3,1,1,0,1,1}*/);
//		//board.grid[7][7] = new LetterTile("T",1);
//		//cpu.turn(riggedBagTwo);
//		board.print();

	}

	@Test
	public void genericTest() {
		Board board = Validator.loadFile("./resources/testBoard.txt");
		Validator.loadDictionary(new File("./resources/wordlist.txt"));
		ComputerPlayer cpu = new ComputerPlayer(board);
		Bag riggedBagTwo = new Bag(new int[] { 1, 1, 1, 1, 1, 1, 0 });
		board.grid[7][7] = new LetterTile("A", 1);
//		board.grid[6][6] = new LetterTile("X", 1);
//		board.grid[6][8] = new LetterTile("X", 1);
		board.grid[8][7] = new LetterTile("A", 1);
		board.grid[7][6] = new LetterTile("A", 1);
		board.grid[7][5] = new LetterTile("A", 1);
		LinkedList<LetterTile> ltlt = new LinkedList<>(cpu.getRack());
		System.out.println("X     :" + ltlt.size());
		ltlt.poll();

//		for (int i =0; i < 1000; i++) {
//			riggedBagTwo = new Bag(new int[]{4,3,0,0,0,0,0});
//			if (cpu.turn(riggedBagTwo).isPass()) {
//				for (LetterTile t : cpu.getRack()) {
//					System.out.println(t.getChar());
//				}
//				break;
//			}
//			board.print();
//		}
//		cpu.turn(riggedBagTwo);
//		board.print();

	}

	@Test
	public void testComputerPlayer() {
		Board board = Validator.loadFile("./resources/testBoard.txt");
		Validator.loadDictionary(new File("./resources/reducedwordlist.txt"));
		ComputerPlayer cpu = new ComputerPlayer(board);
		//Game game = new Game();

		Bag riggedBagTwo = new Bag(new int[] { 0, 1, 0, 0, 0, 0, 0 });
		board.grid[7][7] = new LetterTile("B", 1);
		board.grid[7][5] = new LetterTile("X", 1);
		board.grid[8][7] = new LetterTile("D", 1);

		cpu.draw(riggedBagTwo);
		riggedBagTwo = new Bag(new int[] { 100, 0, 0, 0, 0, 0, 0 });
		cpu.draw(riggedBagTwo);

//		for (int i =0; i < 1000; i++) {
//			cpu.draw(riggedBagTwo);
//			if (cpu.turn(game).isPass()) {
//				break;
//			}
//		}
//		assertTrue(LetterTile.class.isInstance(board.tileAt(7, 8)));
	}

//	@Test
//	public void playExample() {
//		Board board = Validator.loadFile("./resources/testBoard.txt");
//		Bag bag = new Bag();
//		ComputerPlayer cpu = new ComputerPlayer(board);
//		Validator.loadDictionary(new File("./resources/wordlist.txt"));
//		board.grid[4][7] = new LetterTile("B", 1);
//		board.grid[5][7] = new LetterTile("I", 1);
//		board.grid[6][7] = new LetterTile("R", 1);
//		board.grid[7][7] = new LetterTile("K", 1);
//		board.grid[8][7] = new LetterTile("B", 1);
//		board.grid[9][7] = new LetterTile("E", 1);
//		board.grid[10][7] = new LetterTile("C", 1);
//		board.grid[11][7] = new LetterTile("K", 1);
//		for (int i = 0; i < 1000; i++) {
//			board.print();
//			bag = new Bag();
//			if (cpu.turn(bag).isPass()) {
//				for (LetterTile t : cpu.getRack()) {
//					System.out.println(t.getChar());
//				}
//				break;
//			}
//		}
//		board.print();
//	}

}
