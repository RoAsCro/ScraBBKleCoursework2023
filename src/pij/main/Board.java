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
	 * Loads a board from a file.
	 */
	public void load(File file) {
		
		//TODO 
	}
	/**
	 * Prints the board on the console
	 */
	public void print() {
		char xLabel = 'a';
		int yLabel = 1;
		System.out.print("\t");
		for (int i = 0; i < magnitude; i++) {
			System.out.print(xLabel + "\t");
			xLabel++;
		}
		
		System.out.println();
		
		for (Tile[] yTile : grid) {
			System.out.print(yLabel + "\t");
			yLabel++;
			for (Tile xTile : yTile) {
				System.out.print(xTile.getText() + "\t");
			}
			System.out.println();
		}
		
	
	}
	
}
