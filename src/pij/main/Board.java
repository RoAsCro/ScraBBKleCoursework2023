package pij.main;

import java.io.*;

/**
 * A board on which the game of ScraBBKle is played.
 * A board is S x S where S is between 12 and 26.
 * 
 * @author Roland Crompton
 *
 */
public class Board {
	
	/** A two-dimensional array of tiles representing the board. */
	private Tile[][] grid;
	private int magnitude;
	
	public Board(File file) {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			magnitude = Integer.parseInt(reader.readLine());
			grid = new Tile[magnitude][magnitude];
			String row;
			for (int y = 0; y < magnitude; y++) {
				row = reader.readLine();
				String tileText = "";
				int tileValue = 0;
				int x = 0;
				
				for (int i = 0; i < row.length(); i++) {
					char current = row.charAt(i);
					tileText += current;
					
					if (current == '.' || current == ')' || current == '}') {	
						Tile tile = new Tile(tileText, tileValue);
						grid[x][y] = tile;
						tileText = "";
						tileValue = 0;
						x++;
					} else if (current != '(' && current != '{') {
						tileValue = (tileValue * 10) + current;
					}
				}
			}
			
		} catch (FileNotFoundException ex) {
			
			System.out.print("File not found");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Returns the tile at a given set of x,y coordinates on the board.
	 * 
	 * @param x x coordinate.
	 * @param y y coordinate.
	 * @return Tile at given coordinates.
	 */
	public Tile tileAt(int x, int y) {
		return grid[x][y];
	}

	/**
	 * Prints the board on the console
	 */
	public void print() {
		char xLabel = 'a';
		int yLabel = 1;
		System.out.print("   ");
		for (int i = 0; i < magnitude; i++) {
			System.out.print(" " + xLabel + " ");
			xLabel++;
		}
		
		System.out.println();
		
		for (int yCoord = 0; yCoord < magnitude; yCoord++) {
			System.out.print(yLabel + "  ");
			yLabel++;
			for (int xCoord = 0; xCoord < magnitude; xCoord++) {
				System.out.print(grid[xCoord][yCoord].getText());	
			}
			System.out.println();
		}

	}
	
	public boolean placeWord(int x, int y, char direction, LetterTile[] tiles) {
		//xInc and yInc use the integer value of 'd' or 'r' to determine how to iterate across the grid.
		int xInc = (direction - 100) / 14;
		int yInc = (direction - 114) / 14 * - 1;
		
		//Test for if the word intersects with a pre-existing word on the board.
		boolean intersection = false;
		
		//Word constructed of the input letters and any letters with which the new word intersects.
		String fullWord = "";
		
		int wordLength = tiles.length;
		double runningValue = 0;
		int multiplier = 1;
		int[][] locations = new int[wordLength][2];
			
		for (int i = 0; i < wordLength;) {
			
			if (x > magnitude - 1 || y > magnitude - 1)
				return false;
			
			LetterTile placementTile = tiles[i];
			Tile targetTile = grid[x][y];
			int targetValue = targetTile.getValue();
			
			if (targetTile.getClass() == Tile.class) {
				int tileValue = placementTile.getValue();
				
				locations[i][0] = x;
				locations[i][1] = y;
				
				fullWord += placementTile.getChar();
				i++;
				
				if (targetTile.getText().charAt(0) == '(') {
					runningValue += targetValue * tileValue;
				} else {
					runningValue += tileValue;
					if (targetTile.getText().charAt(0) == '{') {
						multiplier *= targetValue;
						
					}
				}
					
				
			} else {
				intersection = true;
				LetterTile letterTile = (LetterTile) targetTile;
				fullWord += letterTile.getChar();
				runningValue += targetValue;
			}
			
			x = x + xInc;
			y = y + yInc;
			
		}
		/*
		 * @ TODO  
		 * if (fullWord not in dictionary)
		 * 		return false
		 */
		
		/*
		 * @ TODO
		 * if (not intersection AND Not first word)
		 * 		return false
		 */
		
		runningValue *= multiplier;
		
		/*
		 * @ TODO
		 *  Add runningValue to player score
		 * 
		 */
		int i = 0;
		for (int[] coords : locations) {
			grid[coords[0]][coords[1]] = tiles[i];
			i++;
		}
		
		return true;
	}
	
}
