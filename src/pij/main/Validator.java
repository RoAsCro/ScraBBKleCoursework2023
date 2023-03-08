package pij.main;

import java.io.*;

public class Validator {

	public static final int MAX_MAGNITUDE = 26;

	public static final int MIN_MAGNITUDE = 12;

	private static final int MIN_PREMIUM_VALUE = -9;

	private static final int MAX_PREMIUM_VALUE = 99;

	public static Board loadFile(String fileName) {
		File file = new File(fileName);
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

			// Check first line exists
			String lineOne = reader.readLine();
			if (lineOne == null){
				invalidFile();
				return null;
			}
			// Check first line consists only of digits
			for (int i = 0 ; i < lineOne.length() ; i++) {
				if (!Character.isDigit(lineOne.charAt(i))) {
					invalidFile();
					return null;
				}
			}
			// Check the declared magnitude is within bounds
			int magnitude;
			if ((magnitude = Integer.parseInt(lineOne)) < MIN_MAGNITUDE || magnitude > MAX_MAGNITUDE) {
				invalidFile();
				return null;
			}

			Tile[][] grid = new Tile[magnitude][magnitude];
			for (int yCoord = 0; yCoord < magnitude; yCoord++) {
				String row = reader.readLine();
				//Check a given row exists
				if (row == null) {
					invalidFile();
					return null;
				}
				StringBuilder tileText = new StringBuilder();
				int xCoord = 0;
				for (int i = 0; i < row.length(); i++) {
					// Check the current character is a valid character
					if (xCoord >= magnitude) {
						invalidFile();
						return null;
					}
					char currentCharacter = row.charAt(i);
					tileText.append(currentCharacter);
					if (currentCharacter == '.' || currentCharacter == ')' || currentCharacter == '}') {
						Tile tile = tileFactory(tileText.toString());
						if (tile != null) {
							grid[xCoord][yCoord] = tile;
							xCoord++;
							tileText = new StringBuilder();
						} else {
							invalidFile();
							return null;
						}
					}
				}
				// Check whether line is too short
				if (xCoord != magnitude) {
					invalidFile();
					return null;
				}
			}
			// Check there are not extra lines at the end of the file
			if (reader.readLine() != null){
				invalidFile();
				return null;
			}
			return new Board(magnitude, grid);
		} catch (FileNotFoundException ex) {
			System.out.print("This is not a valid file. ");
			return null;
			
		} catch (IOException ex) {
			return null;
		}
	}

	private static Tile tileFactory(String tileString){
		if (tileString.equals(".")) {
			return new NullTile();
		}
		int tileValue;
		try {
			tileValue = Integer.parseInt(tileString.substring(1, tileString.length() - 1));
		} catch (NumberFormatException e) {
			return null;
		}
		if (tileValue > MAX_PREMIUM_VALUE || tileValue < MIN_PREMIUM_VALUE) {
			return null;
		}
		Tile returnTile = null;
		switch (tileString.charAt(0)) {
			case '{' -> {
				if (tileString.charAt(tileString.length() - 1) == '}') {
					returnTile = new BonusWordTile(tileValue);
				}
			}
			case '(' -> {
				if (tileString.charAt(tileString.length() - 1) == ')')
					returnTile = new BonusLetterTile(tileValue);
			}
		}
		return returnTile;
	}

	private static void invalidFile(){
		System.out.println("File is not a valid format");
	}
	
	public static boolean inputValidation(String input, String[] validInputs) {
		for (String s : validInputs) 
			if (s.equals(input))
				return false;
		return true;
	}

}
