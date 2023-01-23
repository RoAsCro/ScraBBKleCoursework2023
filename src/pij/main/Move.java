package pij.main;

import java.util.*;

	

public class Move {
	
	private boolean valid = true;
	
	private boolean pass;
	
	private char direction;
	
	private LetterTile[] tiles;
	
	private int x;
	
	private int y;
	
	private final Player PLAYER;

	private final Board BOARD;
	
	public Move(Player player, Board board) {
		pass = false;
		this.PLAYER = player;
		this.BOARD = board;
	}
	
	public Move(String input, Player player, Board board) {
		this.BOARD = board;
		this.PLAYER = player;
		String x = "0";
		String y = "0";
		String direction = "d";
		ArrayList<LetterTile> tiles = new ArrayList<>();
		
		if (input.equals(",,"))
			pass = true;
		else {
			pass = false;
			
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
		if (!this.valid || pass) {
			this.x = 0;
			this.y = 0;
			this.direction = 'd';
			this.tiles = new LetterTile[0];
		} else {
			this.x = x.charAt(0) - 97;
			this.y = Integer.parseInt(y) - 1;
			this.direction = direction.charAt(0);
			this.tiles = tiles.toArray(new LetterTile[0]);
		}

	}
	
	public void setAll(int x, int y, char direction, LetterTile[] tiles) {
		pass = false;
		this.x = x;
		this.y = y;
		this.tiles = tiles;
		this.direction = direction;
	}
	
	public boolean isValid() {
		return valid;
	}


	public int getX() {
		return x;
	}


	public int getY() {
		return y;
	}
	
	public LinkedList<LetterTile> getTiles() {
		LinkedList<LetterTile> list = new LinkedList<>();
		for (LetterTile l : tiles)
			list.add(l);
		return list;
	}


	public char getDirection() {
		return direction;
	}
	
	public boolean isPass() {
		return pass;
	}


	
	@ Override
	public String toString() {
		return "The move is:	Word: " + tiles.toString() + " at position "
				+ (char) (x + 97) + (y + 1) + ", direction: " +
				(direction == 'd' ? "Down" : "Right");
	}
	
	public void updateScore(Double score) {
		this.PLAYER.updateScore(score);
	}
}