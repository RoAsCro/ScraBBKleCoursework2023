package pij.main;

import java.io.*;

public class Validator {

	private static final String[] VALID_BOARD_CHARACTERS = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "(", ")", "{", "}", ".", "-"};
	
	public static Board loadFile(String fileName) {
		File file = new File(fileName);
		Tile[][] grid;
		int magnitude;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

			String lineOne = reader.readLine();
			if (lineOne == null){
				invalidFile();
				return null;
			}

			for (int i = 0 ; i < lineOne.length() ; i++) {
				char currentChar = lineOne.charAt(i);
				if (!Character.isDigit(currentChar)) {
					invalidFile();
					return null;
				}
			}

			if ((magnitude = Integer.parseInt(lineOne)) < 12 || magnitude > 26) {
				invalidFile();
				return null;
			}


			grid = new Tile[magnitude][magnitude];
			String row;
			for (int yCoord = 0; yCoord < magnitude; yCoord++) {
				row = reader.readLine();
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

					if (!inputValidation("" + current, VALID_BOARD_CHARACTERS) || xCoord >= magnitude) {
						invalidFile();
						return null;
					}

					if (current == '.' || current == ')' || current == '}') {
						if (current != '.') {
							if (tileValue.isEmpty()) {
								invalidFile();
								return null;
							}
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
						if (finalValue < -9 || finalValue > 99) {
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
