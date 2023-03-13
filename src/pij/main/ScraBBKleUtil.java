package pij.main;

import pij.main.Tiles.BonusLetterTile;
import pij.main.Tiles.BonusWordTile;
import pij.main.Tiles.NullBoardTile;

import java.io.*;

public final class ScraBBKleUtil {

	public static final int MAX_MAGNITUDE = 26;

	public static final int MIN_MAGNITUDE = 12;

	private static final int MIN_PREMIUM_VALUE = -9;

	private static final int MAX_PREMIUM_VALUE = 99;
	public static final int UPPER_A_ASCII_VALUE = 65;
	public static final int LOWER_A_ASCII_VALUE = 97;
	public static final int LOWER_D_ASCII_VALUE = 100;
	public static final int LOWER_R_ASCII_VALUE = 114;
	public static final int LOWER_Z_ASCII_VALUE = 122;
	public static final int DIRECTION_DIVISOR = 14;

	private ScraBBKleUtil(){}

	public static Board loadFile(String fileName) {
		File file = new File(fileName);
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

			// Check first line exists
			String lineOne = reader.readLine();
			if (lineOne == null){
				return null;
			}
			// Check first line consists only of digits
			for (int i = 0 ; i < lineOne.length() ; i++) {
				if (!Character.isDigit(lineOne.charAt(i))) {
					return null;
				}
			}
			// Check the declared magnitude is within bounds
			int magnitude;
			if ((magnitude = Integer.parseInt(lineOne)) < MIN_MAGNITUDE || magnitude > MAX_MAGNITUDE) {
				return null;
			}

			BoardTile[][] grid = new BoardTile[magnitude][magnitude];
			for (int yCoord = 0; yCoord < magnitude; yCoord++) {
				String row = reader.readLine();
				//Check a given row exists
				if (row == null) {
					return null;
				}
				StringBuilder tileText = new StringBuilder();
				int xCoord = 0;
				for (int i = 0; i < row.length(); i++) {
					// Check the current character is a valid character
					if (xCoord >= magnitude) {
						return null;
					}
					char currentCharacter = row.charAt(i);
					tileText.append(currentCharacter);
					if (currentCharacter == '.' || currentCharacter == ')' || currentCharacter == '}') {
						BoardTile tile = tileFactory(tileText.toString());
						if (tile != null) {
							grid[xCoord][yCoord] = tile;
							xCoord++;
							tileText = new StringBuilder();
						} else {
							return null;
						}
					}
				}
				// Check whether line is too short
				if (xCoord != magnitude) {
					return null;
				}
			}
			// Check there are not extra lines at the end of the file
			if (reader.readLine() != null){
				return null;
			}
			return new Board(magnitude, grid);
		} catch (IOException ex) {
			return null;
		}
	}

	private static BoardTile tileFactory(String tileString){
		if (tileString.equals(".")) {
			return new NullBoardTile();
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
		BoardTile returnTile = null;
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

}
