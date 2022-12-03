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
	 * Fills all blank spaces in the player's tile rack with tiles from the given bag.
	 * 
	 * @param bag the bag to be drawn from.
	 */
	public void draw(Bag bag) {
		int size = tileRack.size();
		for (int i = 0; i < 7 - size; i++)
			tileRack.add(bag.draw());
	}
}
