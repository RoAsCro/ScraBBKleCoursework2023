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
	
	public Move(int x, int y, char direction, LetterTile[] tiles, Player player) {
		PASS = false;
		this.X = x;
		this.Y = y;
		this.TILES = tiles;
		this.PLAYER = player;
		this.DIRECTION = direction;
	}
	
	public Move(String input, Player player) {
		this.PLAYER = player;
		String x = "0";
		String y = "0";
		String direction = "d";
		ArrayList<LetterTile> tiles = new ArrayList<>();
		
		if (input.equals(",,"))
			PASS = true;
		else {
			PASS = false;
			
			String[] movesToTest = input.split(",");
			
			ArrayList<LetterTile> rack = new ArrayList<>(player.getRack());
			
			if (movesToTest.length == 3) {
				
				String letters = movesToTest[0];
				String location = movesToTest[1];
				direction = movesToTest[2].toLowerCase();
				
				if (location.length() < 2 || location.length() > 3)
					valid = false;
				else {
					x = location.substring(0,1).toLowerCase();
					y = location.substring(1);
					
					int yLength = y.length();
					//Check the x coord is a letter
					if (!Character.isLetter(x.charAt(0)) || !Character.isDigit(y.charAt(0))
							|| !Character.isDigit(y.charAt(yLength - 1))
							|| !Validator.inputValidation(direction, new String[] { "r", "d" })
							|| letters.length() < 1) {
						valid = false;
					}
//					//Check the y coord is a number
//					else if (!Character.isDigit(y.charAt(0)) || !Character.isDigit(y.charAt(yLength - 1))) {
//						valid = false;
//					}
//					//Check the direction is a valid direction
//					else if (!Validator.inputValidation(direction, new String[] {"r", "d"})) {
//						valid = false;
//					} 
//					//Check there is at least one letter
//					else if (letters.length() < 1) {
//						valid = false;
//					}
					else {
						char[] chars = letters.toCharArray();
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
					}
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
	
	public LinkedList<LetterTile> getTiles() {
		LinkedList<LetterTile> list = new LinkedList<>();
		for (LetterTile l : TILES)
			list.add(l);
		return list;
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