package pij.main;

import java.util.ArrayList;

public abstract class Player {
	/** an array of tiles a player has available to them */
	private ArrayList<Tile> tileRack = new ArrayList<>();
	
	/**
	 * Prints the tile rack.
	 */
	public void printRack() {
		System.out.println(tileRack.toString());
	}
	/**
	 * Adds a tile to the tile rack.
	 * 
	 * @param tile tile to be added.
	 */
	public void draw(Tile tile) {
		tileRack.add(tile);
	}
}
