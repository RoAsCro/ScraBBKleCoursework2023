package pij.main;

import java.util.ArrayList;

public abstract class Player {
	/** an array of tiles a player has available to them */
	private ArrayList<Tile> tileRack = new ArrayList<>();
	
	/**
	 * Returns the player's tile rack.
	 * 
	 * @return the player's tile rack.
	 */
	public ArrayList<Tile> getRack() {
		return tileRack;
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
