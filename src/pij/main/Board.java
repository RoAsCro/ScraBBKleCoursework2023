package pij.main;

/**
 * A board on which the game of ScraBBKle is played.
 * A board's dimensions are S x S where S is between 12 and 26.
 * 
 * @author Roland Crompton
 *
 */
public class Board {
	
	/** A two-dimensional array of tiles representing the board. */
	private Tile[][] grid;
	
	
	
	/** The size of the board's axes. */
	private final int MAGNITUDE;
	
	/** The coordinates of the centre tile of the board.
	 * This will always be the same for both x and y axes.
	 * If the board has an even-numbered magnitude, it will be (magnitude / 2)
	 * If the board has an odd-numbered magnitude, it will be (magnitude / 2 + 1).
	 */
	private final int CENTRE;

	/** True if no tiles have been placed yet */
	private boolean startState = true;
	
	private WordOperation addLetter = (word, tiles, i) -> {
		word.addLetter(tiles[i]);
	};
	
	private WordOperation setText = (word, tiles, i) -> {
		WildTile wild = (WildTile) tiles[i];
		wild.setText();
	};
	
	private WordOperation placeTile = (word, tiles, i) -> {
		
	};
	
	private Condition aboveBelow = (x, y, xInc, yInc) -> {
		Tile higher = tileAt(x + yInc, y + xInc);
		Tile lower = tileAt(x - yInc, y - xInc);
		if (LetterTile.class.isInstance(higher) || LetterTile.class.isInstance(lower)) {
			System.out.println(
					"You cannot form more than one word in one move, or have two adjacent letters that do not form a word.");
			return false;
		}
		return true;
	};
	
	Condition alwaysTrue = (x, y, xInc, yInc) -> {return true;};
	
	public Board(int magnitude, Tile[][] grid) {
		this.MAGNITUDE = magnitude;
		this.CENTRE = magnitude / 2;
		this.grid = grid;
	}
	
	public int getCentre() {
		return CENTRE;
	}
	
	/**
	 * Returns the tile at a given set of x,y coordinates on the board.
	 * 
	 * @param x x coordinate.
	 * @param y y coordinate.
	 * @return Tile at given coordinates. Null if tile out of bounds.
	 */
	public Tile tileAt(int x, int y) {
		if (x < 0 || y < 0 || x >= MAGNITUDE || y >= MAGNITUDE)
			return null;
		else
			return grid[x][y];
			
	}

	/**
	 * Prints the board on the console
	 */
	public void print() {
		char xLabel = 'a';
		int yLabel = 1;
		System.out.print("   ");
		for (int i = 0; i < MAGNITUDE; i++) {
			System.out.print(" " + xLabel + " ");
			xLabel++;
		}
		
		System.out.println();
		
		for (int yCoord = 0; yCoord < MAGNITUDE; yCoord++) {
			System.out.print(yLabel + "  ");
			yLabel++;
			for (int xCoord = 0; xCoord < MAGNITUDE; xCoord++) {
				System.out.print(tileAt(xCoord, yCoord).getText());	
			}
			System.out.println();
		}

	}
	
	public boolean constructWord(int x, int y, int xInc, int yInc, LetterTile[] tiles, Word word) {
		if (boardIter(x, y, xInc, yInc, tiles, aboveBelow, addLetter, addLetter, word, new LetterTile("A", 1))) {
			return true;
		}
		return false;
	}
	
	
	public boolean boardIter(int x, int y, int xInc, int yInc, LetterTile[] tiles, Condition failCondition,
			WordOperation method, WordOperation methodTwo, Word word, Tile type) {

		Tile targetTile = tileAt(x, y);
		int wordLength = tiles.length;

		for (int i = 0; i < wordLength || LetterTile.class.isInstance(targetTile);) {
			if (targetTile == null) {
				System.out.println("That word does not fit on the board.");
				return false;
			}
			if (!type.getClass().isInstance(targetTile)) {
				if (!failCondition.test(x, y, xInc, yInc))
					return false;
				method.execute(word, tiles, i);
				i++;
			}
			methodTwo.execute(word, grid[x], y);
			x += xInc;
			y += yInc;
			targetTile = tileAt(x, y);
		}
		System.out.println("DDDDDD");
		return true;
	}
	
	public boolean placeWord(Move move) {
		if (move.isPass())
			return true;
		int x = move.getX();
		int y = move.getY();
		char direction = move.getDirection();
		LetterTile[] tiles = move.getTiles();
		
		int wordLength = tiles.length;
		
		//xInc and yInc use the integer value of 'd' or 'r' to determine how to iterate across the grid.
		int xInc = (direction - 100) / 14;
		int yInc = (direction - 114) / 14 * - 1;
		
		//Test for if the word intersects with a pre-existing word on the board.
		boolean intersection = false;
		
		//The word will store all the placed word's information
		Word word = new Word();
				
//		while (LetterTile.class.isInstance(tileAt(x - xInc, y - yInc))) {
//			x -= xInc;
//			y -= yInc;
//		}
		
		if (LetterTile.class.isInstance(tileAt(x-xInc, y-yInc))) {
			System.out.println("Please use the position of the first letter in the word as the input location.");
			return false;
		}
		
		if (!constructWord(x, y, xInc, yInc, tiles, word))
			return false;
		
		if (word.getTiles().length > wordLength) {
			intersection = true;
		}

		int startX = x;
		int startY = y;

//		
//		Tile targetTile = tileAt(x, y);
//		for (int i = 0; i < wordLength || LetterTile.class.isInstance(targetTile);) {
//
//			if (targetTile == null) {
//				System.out.println("That word does not fit on the board.");
//				return false;
//			}
//			
//			if (!LetterTile.class.isInstance(targetTile)) {
//				LetterTile placementTile = tiles[i];
//				
//				//Check this move does not form two words
//				//If the direction = r, xInc = 1 and yInc = 0, vice versa if direction = d.
//				//Therefore if direction = r this will check the tiles above and below, and to the right and left id direction = d.
//				//There should never be a LetterTile in one of these spaces.
//				Tile higher = tileAt(x + yInc, y + xInc);
//				Tile lower = tileAt(x - yInc, y - xInc);
//				if (LetterTile.class.isInstance(higher) || LetterTile.class.isInstance(lower)) {
//					System.out.println(
//							"You cannot form more than one word in one move, or have two adjacent letters that do not form a word.");
//					return false;
//				}
//				word.addLetter(placementTile);
//				i++;
//			} else 
//				intersection = true;
//			
//			word.addLetter(targetTile);
//			x += xInc;
//			y += yInc;
//			targetTile = tileAt(x, y);
//		}

		//Check word is in dictionary.
		if (!Validator.lookupWord(word.getWord())) {
			System.out.println("Word not in dictionary.");
			return false;
		}
		
		//Check word is either the first word being placed OR that it intersects with a pre-existing word.
		//THIS MUST BE THE LAST CHECK because startState is turned off by all the conditionals below evaluating to false.
		if (!intersection) {
			if (!startState) {
				System.out.println("Your word must cross another word");
				return false;
			} else if (!((y == CENTRE && (CENTRE <= x + wordLength - 1 && CENTRE >= startX))
					|| (x == CENTRE && (CENTRE <= y + wordLength - 1 && CENTRE >= startY)))) {
				System.out.println("Your word must cross over the centre tile.");
				return false;
			} else
				startState = false;
			
		}
		
		move.updateScore(word.getScore());

		LetterTile[] letters = word.getTiles();
		boardIter(x, y, xInc, yInc, tiles, alwaysTrue, addLetter, new PlaceTile(), word, new LetterTile("A", 1));
			

//		for (LetterTile letter : letters) {
//			
//			//CONSIDER PUTTING THIS IN THE PLAYER'S REMOVETILE METHOD
//			if (WildTile.class.isInstance(letter)) {
//				WildTile wild = (WildTile) letter;
//				wild.setText();
//			}
//			grid[startX][startY] = letter;
//			startX += xInc;
//			startY += yInc;
//		}
		
		return true;
	}
	

	
}
