package pij.main;

import java.io.*;

public class Validator {
	
	public static Board loadFile(String fileName) {
		File file = new File("../resources/" + fileName);
		Tile[][] grid;
		int magnitude;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			magnitude = Integer.parseInt(reader.readLine());
			grid = new Tile[magnitude][magnitude];
			String row;
			for (int y = 0; y < magnitude; y++) {
				row = reader.readLine();
				String tileText = "";
				int tileValue = 0;
				int x = 0;
				
				for (int i = 0; i < row.length(); i++) {
					char current = row.charAt(i);
					tileText += current;
					
					if (current == '.' || current == ')' || current == '}') {	
						Tile tile = new Tile(tileText, tileValue);
						grid[x][y] = tile;
						tileText = "";
						tileValue = 0;
						x++;
					} else if (current != '(' && current != '{') {
						tileValue = (tileValue * 10) + current;
					}
				}
			}
			return new Board(magnitude, grid);
		} catch (FileNotFoundException ex) {
			System.out.println("File Not Found.");
			return null;
			
		} catch (IOException ex) {
			return null;
		}
	}
	
}
