package pij.main;

import pij.main.tiles.BonusLetterTile;
import pij.main.tiles.BonusWordTile;
import pij.main.tiles.NullBoardTile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Utility class for ScraBBKle. Should only be used through its static methods.
 * <p></p>
 * Allows for safely loading Boards from files while validating the files
 * and contains various static integers used throughout the ScraBBKle program.
 *
 * @author Roland Crompton
 */
public final class ScraBBKleUtil {

	/** Divisor used throughout the program for converting direction input to a usable integer.*/
	public static final int DIRECTION_DIVISOR = 14;
	/** The ASCII value of a lowercase 'a'. Used for converting characters to integers.*/
	public static final int LOWER_A_ASCII_VALUE = 97;
	/**
	 * The ASCII value of a lowercase 'd'. Used for converting direction input to
	 * a usable integer.
	 */
	public static final int LOWER_D_ASCII_VALUE = 100;
	/**
	 * The ASCII value of a lowercase 'r'. Used for converting direction input to
	 * a usable integer.
	 */
	public static final int LOWER_R_ASCII_VALUE = 114;
	/** The ASCII value of a lowercase 'z'. Used for looping through alphabetic characters.*/
	public static final int LOWER_Z_ASCII_VALUE = 122;
	/** The maximum size of a Board.*/
	public static final int MAX_MAGNITUDE = 26;
	/** The minimum size of a Board.*/
	public static final int MIN_MAGNITUDE = 12;
	/** The maximum value of a premium tile.*/
	private static final int MAX_PREMIUM_VALUE = 99;
	/** The minimum value of a premium tile.*/
	private static final int MIN_PREMIUM_VALUE = -9;

	/**
	 * Private constructor. This class should not be instantiated.
	 */
	private ScraBBKleUtil(){}

	/**
	 * Loads a Board from a file. Checks it for errors and returns null if there are any errors
	 * or there's an IO problem.
	 *
	 * @param fileName the qualified file location
	 * @return the loaded Board if loading is successful, null if not
	 */
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
			return new Board(grid);
		} catch (IOException ex) {
			return null;
		}
	}

	/**
	 * Helper method for generating the initial Tiles on a Board.
	 * Generates BonusLetterTiles, BonusWordTiles, and NullTiles
	 *
	 * @param tileString the String of a Tile loaded from a file
	 * @return the Tile created from the String, null if the String isn't formatted correctly
	 */
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
