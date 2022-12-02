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
	 * Loads a board from a file.
	 */
	public void load(File file) {
		
		//TODO 
	}
	/**
	 * Prints the board on the console
	 */
	public void print() {
		
		for (Tile[] yTile : grid) {
			for (Tile xTile : yTile) {
				System.out.print(xTile.getText());
			}
			System.out.println();
		}
		
		//TODO 
	}
	
}
