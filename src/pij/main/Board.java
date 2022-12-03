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
		
		for (Tile[] yTile : grid) {
			System.out.print(yLabel + "  ");
			yLabel++;
			for (Tile xTile : yTile) {
				System.out.print(xTile.getText());
			}
			System.out.println();
		}
		
	
	}
	
	public boolean placeWord(int x, int y, char direction, String word) {
		int xInc = (direction - 100) / 14;
		int yInc = (direction - 114) / 14 * - 1;
			
		for (int i = 0; i < word.length();) {
			if (x > magnitude - 1 || y > magnitude - 1)
				return false;
			if (grid[x][y].getClass() == Tile.class) {
				i++;
			}
			x = x + xInc;
			y = y + yInc;
			
		}
		return true;
	}
	
}
