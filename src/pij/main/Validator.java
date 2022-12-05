package pij.main;

import java.io.*;
import java.util.*;

public class Validator {
	
	public static Board loadFile(String fileName) {
		File file = new File(fileName);
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
						tileValue = (tileValue * 10) + current - '0';
					}
				}
			}
			return new Board(magnitude, grid);
		} catch (FileNotFoundException ex) {
			System.out.print("This is not a valid file. ");
			return null;
			
		} catch (IOException ex) {
			return null;
		}
	}
	
	public static boolean inputValidation(String input, String[] validInputs) {
		for (String s : validInputs) 
			if (input.equals(s))
				return true;
		return false;
	}
	
	public static void dictionary() {
		File file = new File("./resources/wordlist.txt");
		try (Scanner scanner = new Scanner(file)){
			String search = "zymosis";
			//int i = 0;
			long a = System.nanoTime();
			//System.out.println(scanner.findWithinHorizon(search, 2974773));
//			while (scanner.hasNext()) {
//				
////				if (scanner.next().equals("zzzs")) {
////					
////					System.out.println("Found");
////					break;
////				}
//				//System.out.println("A: " + scanner.next());
//			}
			scanner.close();
			long b = System.nanoTime();
			System.out.println(b-a);

			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			int j = 0;
			String line;
			String[] array = new String[267753];
			
			while ((line = reader.readLine()) != null) {
				array[j] = line;
//				if (line.equals(search)) {
//					System.out.println("Found");
//					break;
//				}
				//System.out.println("B: " + line);
				j++;
			}
			a = System.nanoTime();
			boolean go = true;
			int f = 267752;
			int g = 0;
			int h = f;
			int n = 0;
			char c = search.charAt(0);
			while (n < search.length()) {
				h = g + ((f - g) / 2);
				System.out.println(array[h]);
				System.out.println(search.charAt(n));
				if (search.equals(array[h])) {
						System.out.println("Found!");
						break;
				}
				if (array[h].length() > n) {
					if (search.charAt(n) == array[h].charAt(n)) {
						n++;
					}
					else if (search.charAt(n)  < array[h].charAt(n)) {
						System.out.println("B");
						f = h;
					} else if (search.charAt(n)  > array[h].charAt(n)) {
						System.out.println("A");
						g = h;
					}
				} else g++;	
			}
			b = System.nanoTime();
			long x = b-a;
			System.out.println(b-a);
			reader.close();
			
			BufferedReader newreader = new BufferedReader(new FileReader(file));
			a = System.nanoTime();
			while ((line = newreader.readLine()) != null) {		
				if (line.equals(search)) {
					//System.out.println("Found");
					break;
				}
				//System.out.println("B: " + line);
			}
			b = System.nanoTime();
			long y = b-a;
			System.out.println(b-a);
			System.out.println();
			System.out.println((y-x));
			reader.close();
			
//			String x = scanner.findWithinHorizon("aard", 300);
//			System.out.print(x);
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException ex) {
			System.out.print("Woo");
		}
	}
	
}
