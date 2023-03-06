package pij.main;

import java.io.*;

public class Validator {

	private static final int MAX_MAGNITUDE = 26;

	private static final int MIN_MAGNITUDE = 12;

	private static final int MIN_PREMIUM_VALUE = -9;

	private static final int MAX_PREMIUM_VALUE = 99;

	private static final String[] VALID_BOARD_CHARACTERS = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "(", ")", "{", "}", ".", "-"};
	
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
			// Check the declared maginitude is within bounds
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
				StringBuilder tileValue = new StringBuilder();
				int xCoord = 0;
				for (int i = 0; i < row.length(); i++) {
					char current = row.charAt(i);

					tileText.append(current);
					// Check the current character is a valid character
					if (!inputValidation("" + current, VALID_BOARD_CHARACTERS) || xCoord >= magnitude) {
						invalidFile();
						return null;
					}

					if (current == '.' || current == ')' || current == '}') {
						// Check the tile has a value if it's a premium tile
						if (current != '.') {
							if (tileValue.isEmpty()) {
								invalidFile();
								return null;
							}
							// Check the characters collected as the tileValue are digits or a dash
							for (int c = 0 ; c < tileValue.length() ; c++) {
								char currentChar = tileValue.charAt(c);
								if (!Character.isDigit(currentChar) && !(currentChar == '-' && c == 0)) {
									invalidFile();
									return null;
								}
							}
						}  else
							tileValue.append('0');

						int finalValue = Integer.parseInt(tileValue.toString());
						// Check the tile's value is in the proper range
						if (finalValue < MIN_PREMIUM_VALUE || finalValue > MAX_PREMIUM_VALUE) {
							invalidFile();
							return null;
						}

						Tile tile = new Tile(tileText.toString(), finalValue);
						grid[xCoord][yCoord] = tile;
						tileText = new StringBuilder();
						tileValue = new StringBuilder();
						xCoord++;
					} else if (current != '(' && current != '{') {
						tileValue.append(current);
					}

				}
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

	private static void invalidFile(){
		System.out.println("File is not a valid format");
	}
	
	public static boolean inputValidation(String input, String[] validInputs) {
		for (String s : validInputs) 
			if (s.equals(input))
				return true;
		return false;
	}

}
