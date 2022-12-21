package pij.main;

import java.util.LinkedList;

/**
 * A board on which the game of ScraBBKle is played.
 * A board's dimensions are S x S where S is between 12 and 26.
 * 
 * @author Roland Crompton
 *
 */
public class Board {
	
	/** A two-dimensional array of tiles representing the board. */
	public Tile[][] grid;
	
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
	
	
	private Check isLetter = (tile) -> {return LetterTile.class.isInstance(tile);};
	
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
//		BoardReader reader = new BoardReader(this, 0, 0, 'd');
//		reader.conditionalNext((tile) -> {return true;}, (x, y) -> {System.out.print(tileAt(x, y).getText());});
		for (int yCoord = 0; yCoord < MAGNITUDE; yCoord++) {
			System.out.print(yLabel + "  ");
			yLabel++;
			for (int xCoord = 0; xCoord < MAGNITUDE; xCoord++) {
				System.out.print(tileAt(xCoord, yCoord).getText());	
			}
			System.out.println();
		}

	}
	/**
	 * 
	 * @param initialX
	 * @param initialY
	 * @param direction
	 * @param tiles
	 * @param word
	 * @return
	 */
	public boolean constructWord(int initialX, int initialY, char direction, LinkedList<LetterTile> tiles, Word word) {
		BoardReader reader = new BoardReader(this, initialX, initialY, direction);
		Tile currentTile = null;
		do {
			currentTile = reader.conditionalNext((tile) -> {
				return (!LetterTile.class.isInstance(tile) && !tiles.isEmpty());
			}, (x, y) -> {
				reader.turn();
				if (isLetter.check(reader.next())) {
					reader.set(-2, -2);
				} else {
					reader.previous();
					if (isLetter.check(reader.previous())) {
						reader.set(-2, -2);
					} else {
						reader.next();
						reader.turn();
						word.addLetter(tiles.poll());
						word.addLetter(tileAt(x, y));
					}
				}
			});
			currentTile = reader.conditionalNext(isLetter, (x, y) -> {
				word.addLetter(tileAt(x, y));
			});

		} while (!tiles.isEmpty() && currentTile != null);

		if ((!tiles.isEmpty() && currentTile == null) /* || word.getTiles().length <= tiles.size() */)
			return false;

		return true;
	}
	
	public void placeTiles(int initialX, int initialY, char direction, LinkedList<LetterTile> tiles) {
		BoardReader reader = new BoardReader(this, initialX, initialY, direction);
		reader.conditionalNext((tile) -> {
			return !tiles.isEmpty();
		}, (x, y) -> {
			grid[x][y] = tiles.poll();
		});
	}
	
	
	public boolean placeWord(Move move) {
		if (move.isPass())
			return true;
		int x = move.getX();
		int y = move.getY();
		char direction = move.getDirection();
		LinkedList<LetterTile> tiles = new LinkedList<LetterTile>(move.getTiles());
		int wordLength = tiles.size();

		// xInc and yInc use the integer value of 'd' or 'r' to determine how to iterate
		// across the grid.
		int xInc = (direction - 100) / 14;
		int yInc = (direction - 114) / 14 * -1;

		// The word will store all the placed word's information
		Word word = new Word();

		if (LetterTile.class.isInstance(tileAt(x - xInc, y - yInc))) {
			System.out.println("Please use the position of the first letter in the word as the input location.");
			return false;
		}
		if (!constructWord(x, y, direction, tiles, word)) {
			return false;
		}

		// Check word is in dictionary.
		if (!Validator.lookupWord(word.toString())) {
			System.out.println("Word not in dictionary.");
			return false;
		}

		// Check word is either the first word being placed OR that it intersects with a
		// pre-existing word.
		// THIS MUST BE THE LAST CHECK because startState is turned off by all the
		// conditionals below evaluating to false.
		if (!(word.getTiles().length > wordLength)) {
			if (!startState) {
				System.out.println("Your word must cross another word");
				return false;
			} else if (!((y == CENTRE && y + yInc == CENTRE && (CENTRE <= x + wordLength - 1 && CENTRE >= x))
					|| (x == CENTRE && x + xInc == CENTRE && (CENTRE <= y + wordLength - 1 && CENTRE >= y)))) {
				System.out.println("Your word must cross over the centre tile.");
				return false;
			} else
				startState = false;

		}

		move.updateScore(word.getScore());
		placeTiles(x, y, move.getDirection(), word.getTilesTwo());

		return true;
	}
	

	
}
