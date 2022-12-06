package pij.main;

import java.io.*;

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
	
	public Board(int magnitude, Tile[][] grid) {
		this.MAGNITUDE = magnitude;
		this.CENTRE = magnitude / 2;
		this.grid = grid;
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
		
		//Word constructed of the input letters and any letters with which the new word intersects.
		String fullWord = "";
		
		//Total score earned through placing the tiles.
		double runningValue = 0;
		
		//Multipliers, starting with 1, to be factored into the runningValue.
		int multiplier = 1;
		
		//Notes where the tiles are to be placed, assuming the placement is successful.
		int[][] locations = new int[wordLength][2];
			
		for (int i = 0; i < wordLength;) {
			
			Tile targetTile;
			//Checks the tile is not being placed outside the board
			if ((targetTile = tileAt(x, y)) == null) {
				System.out.println("That word does not fit on the board.");
				return false;
			}
			
			LetterTile placementTile = tiles[i];
			
			int targetValue = targetTile.getValue();
			
			//If the space is not occupied, add the space's value to the multiplier or multiply the the score of the tile being multiplied
			if (targetTile.getClass() != LetterTile.class) {
				
				//Check this move does not form two words
				//If the direction = r, xInc = 1 and yInc = 0, vice versa if direction = d.
				//Therefore if direction = r this will check the tiles above and below, and to the right and left id direction = d.
				//There should never be a LetterTile in one of these spaces.
				Tile higher = tileAt(x + yInc, y + xInc);
				Tile lower = tileAt(x - yInc, y - xInc);
				if ((higher != null && higher.getClass() == LetterTile.class)
						|| (lower != null && lower.getClass() == LetterTile.class)) {
					System.out.println(
							"You cannot form more than one word in one move, or have two adjacent letters that do not form a word.");
					return false;
				}
				
				int tileValue = placementTile.getValue();
				
				locations[i][0] = x;
				locations[i][1] = y;
				
				fullWord += placementTile.getChar();
				i++;
				
				if (targetTile.getText().charAt(0) == '(') {
					runningValue += targetValue * tileValue;
				} else {
					runningValue += tileValue;
					if (targetTile.getText().charAt(0) == '{')
						multiplier *= targetValue;
				}					
			//If the space on the board is already occupied, add the letter and its score to the word and score.	
			} else {
				intersection = true;
				LetterTile letterTile = (LetterTile) targetTile;
				fullWord += letterTile.getChar();
				runningValue += targetValue;
			}
			
			//COULD MOVE THESE TO THE IF STATEMENT
			x = x + xInc;
			y = y + yInc;
			
			
		}
		
		//Check word is in dictionary.
		System.out.println(fullWord);
		if (!Validator.lookupWord(fullWord)) {
			System.out.println("Word not in dictionary.");
			return false;
		}
		

		
		//Check word is either the first word being placed OR that it intersects with a pre-existing word.
		//THIS MUST BE THE LAST CHECK because startState is turned off by all the conditionals below evaluating to false.
		if (!intersection) {
			if (!startState) {
				System.out.println("Your word must cross another word");
				return false;
			} else if (!((y == CENTRE && (CENTRE <= x-1 && CENTRE >= x-1 - wordLength))
						|| (x == CENTRE && (CENTRE <= y-1 && CENTRE >= y-1 - wordLength)))) {
				System.out.println("Your word must cross over the centre tile.");
				return false;
			} else
				startState = false;
			
		}
		

		runningValue *= multiplier;
		
		move.updateScore(runningValue);
		int i = 0;
		for (int[] coords : locations) {
			grid[coords[0]][coords[1]] = tiles[i];
			i++;
		}
		
		return true;
	}
	
}
