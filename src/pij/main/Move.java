package pij.main;

import java.util.*;

	

public class Move {
	
	private boolean valid = true;
	
	private final boolean PASS;
	
	private final char DIRECTION;
	
	private final LetterTile[] TILES;
	
	private final int X;
	
	private final int Y;
	
	public Move(String input, ArrayList<LetterTile> rack) {
		String x = "0";
		String y = "0";
		String direction = "d";
		ArrayList<LetterTile> tiles = new ArrayList<>();
		
		if (input.equals(",,"))
			PASS = true;
		else {
			PASS = false;
			String[] movesToTest = input.split(",");
			if (movesToTest.length == 3) {
				x = movesToTest[1].substring(0,1).toLowerCase();
				y = movesToTest[1].substring(1);
				direction = movesToTest[2].toLowerCase();
				
				int yLength = y.length();
				if (x.toUpperCase() == x.toLowerCase()) {
					valid = false;
				}
				if (yLength > 2 || yLength < 1 || !Character.isDigit(y.charAt(0)) || !Character.isDigit(y.charAt(yLength - 1))) {
					valid = false;
				}
				
				if (!Validator.inputValidation(direction, new String[] {"r", "d"})) {
					System.out.print("D");
					valid = false;
				}
				char[] chars = movesToTest[0].toCharArray();
				int counter = 0;
				int target = chars.length;
				
				for (char c : chars) {
					for (LetterTile t : rack) {
						System.out.println(t.getChar());
						if (t.getChar() == c) {
							tiles.add(t);
							rack.remove(t);
							counter++;
							break;
						}
					}
				}
				if (counter != target) {
					valid = false;
				}
			
			}
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
}