package pij.main;

import java.util.*;

	

public class Move {
	
	private boolean valid = true;
	
	private final boolean PASS;
	
	private final char DIRECTION;
	
	private final LetterTile[] TILES;
	
	private final int X;
	
	private final int Y;
	
	private final Player PLAYER;
	
	public Move(String input, Player player) {
		this.PLAYER = player;
		String x = "0";
		String y = "0";
		String direction = "d";
		ArrayList<LetterTile> tiles = new ArrayList<>();
		
		if (input.equals(",,"))
			PASS = true;
		else {
			
			String[] movesToTest = input.split(",");
			ArrayList<LetterTile> rack = new ArrayList<>(player.getRack());
			PASS = false;
			
			if (movesToTest.length == 3) {
				x = movesToTest[1].substring(0,1).toLowerCase();
				y = movesToTest[1].substring(1);
				direction = movesToTest[2].toLowerCase();
				
				int yLength = y.length();
				//Check the x coord is a letter
				if (x.toUpperCase() == x.toLowerCase()) {
					valid = false;
				}
				//Check the y coord is no larger than 2 and no smaller than one, and is a number
				else if (yLength > 2 || yLength < 1 || !Character.isDigit(y.charAt(0)) || !Character.isDigit(y.charAt(yLength - 1))) {
					valid = false;
				}
				//Check the direction is a valid direction
				else if (!Validator.inputValidation(direction, new String[] {"r", "d"})) {
					valid = false;
				}
				char[] chars = movesToTest[0].toCharArray();
				int counter = 0;
				int target = chars.length;
				
				//Check the player has the required tiles
				for (char c : chars) {
					for (LetterTile t : rack) {
						char tChar = t.getChar();
						if (tChar == c 
								|| (Character.isLowerCase(c) && WildTile.class.isInstance(t))) {
							tiles.add(t);
							rack.remove(t);
							counter++;
							if (WildTile.class.isInstance(t)) {
								WildTile w = (WildTile) t;
								w.setTempText(c);
							}
							break;
						}
					}
				}
				if (counter != target) {
					valid = false;
				}
			
			}else
				valid = false;
		} 
		if (!this.valid || PASS) {
			this.X = 0;
			this.Y = 0;
			this.DIRECTION = 'd';
			this.TILES = new LetterTile[0];
		} else {
			this.X = x.charAt(0) - 97;
			this.Y = Integer.parseInt(y) - 1;
			this.DIRECTION = direction.charAt(0);
			this.TILES = tiles.toArray(new LetterTile[0]);
		}

	}
	
	
	public boolean isValid() {
		return valid;
	}


	public int getX() {
		return X;
	}


	public int getY() {
		return Y;
	}


	public LetterTile[] getTiles() {
		return TILES;
	}


	public char getDirection() {
		return DIRECTION;
	}
	
	public boolean isPass() {
		return PASS;
	}
	
	@ Override
	public String toString() {
		return "The move is:	Word: " + TILES.toString() + " at position "
				+ (char) (X + 97) + (Y + 1) + ", direction: " +
				(DIRECTION == 'd' ? "Down" : "Right");
	}
	
	public void updateScore(Double score) {
		this.PLAYER.updateScore(score);
	}
}