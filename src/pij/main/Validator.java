package pij.main;

import java.io.*;
import java.util.*;

public class Validator {
	
	private static TreeSet<String> dictionary = new TreeSet<>();
	
	public static void loadDictionary(File file) {
		
		dictionary = new TreeSet<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			
			while ((line = reader.readLine()) != null) {
				dictionary.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void loadDictionary() {
		loadDictionary(new File("../resources/wordlist.txt"));
	}
	
	public static boolean lookupWord(String word) {
		if (dictionary.contains(word.toLowerCase()))
			return true;
		return false;
	}
	public static TreeSet<String> lookupRack(String letters) {
		TreeSet<String> tree = new TreeSet<>();
		Iterator<String> iter = dictionary.iterator();
		StringBuilder regex = new StringBuilder(letters);
		regex.insert(0,".*[");
		regex.insert(regex.length(),"].*");
//		for (int i = 0; i < regex.length() ; i++) {
//			regex.insert(i + i + 1, '?');
//		}
		String regexString = regex.toString().toLowerCase();
		System.out.println(regexString);
		while (iter.hasNext()){
			String s = iter.next();
			if (s.matches(regexString))
				tree.add(s);
		}
		return tree;
	}

	public static TreeSet<String> lookupSet(String word, TreeSet<String> tree) {
		//System.out.println(tree.size());
		TreeSet<String> newTree = new TreeSet<>();
		TreeSet<String> oldTree = new TreeSet<>(tree);
		String lowerWord = word.toLowerCase();
		oldTree.remove(lowerWord);
		Iterator<String> iter = oldTree.iterator();
		while (iter.hasNext()){
			String s = iter.next();
			if (s.matches(".+" + lowerWord + ".+")){
				newTree.add(s);
			}
		}
		//System.out.println(tree.size());
		return newTree;
	}
	public static int lookupWordTwo(String word) {
		Iterator<String> iter = dictionary.iterator();
		String lowerWord = word.toLowerCase();
		if (dictionary.contains(lowerWord))
				return 0;
		while (iter.hasNext()){
			String s = iter.next();
			if (s.matches(".+" + lowerWord + ".+")){
				return 1;
			}
		}
		return -1;
	}
	
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
					// TODO: 18/01/2023  - else board file is not valid

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
	public static void main(String args[]) {
		loadDictionary(new File("./resources/wordlist.txt"));
		System.out.println(lookupWord("zygote"));
		System.out.println(lookupWord("adwdaw"));
		System.out.println(lookupWord("cat"));
	}
//	public static void dictionary() {
//		File file = new File("./resources/wordlist.txt");
//		try (Scanner scanner = new Scanner(file)){
//			//Hashtable<String> table = new Hashtable<>();
//			TreeSet<String> tree = new TreeSet<>();
//			String search = "zymosis";
//			//int i = 0;
//			long a = System.nanoTime();
//			//System.out.println(scanner.findWithinHorizon(search, 2974773));
////			while (scanner.hasNext()) {
////				
//////				if (scanner.next().equals("zzzs")) {
//////					
//////					System.out.println("Found");
//////					break;
//////				}
////				//System.out.println("A: " + scanner.next());
////			}
//			scanner.close();
//			long b = System.nanoTime();
//			System.out.println(b-a);
//
//			
//			BufferedReader reader = new BufferedReader(new FileReader(file));
//			int j = 0;
//			String line;
//			String[] array = new String[267753];
//			
//			while ((line = reader.readLine()) != null) {
//				array[j] = line;
//				tree.add(line);
////				if (line.equals(search)) {
////					System.out.println("Found");
////					break;
////				}
//				//System.out.println("B: " + line);
//				j++;
//			}
//			a = System.nanoTime();
//			System.out.println(tree.contains(search));
//			b = System.nanoTime();
//			System.out.println(b-a);
//			
//			a = System.nanoTime();
//			boolean go = true;
//			int f = 267752;
//			int g = 0;
//			int h = f;
//			int n = 0;
//			char c = search.charAt(0);
//			while (n < search.length()) {
//				h = g + ((f - g) / 2);
//				//System.out.println(array[h]);
//				//System.out.println(search.charAt(n));
//				if (search.equals(array[h])) {
//						System.out.println("Found!");
//						break;
//				}
//				if (array[h].length() > n) {
//					if (search.charAt(n) == array[h].charAt(n)) {
//						n++;
//					}
//					else if (search.charAt(n)  < array[h].charAt(n)) {
//						//System.out.println("B");
//						f = h;
//					} else if (search.charAt(n)  > array[h].charAt(n)) {
//						//System.out.println("A");
//						g = h;
//					}
//				} else g++;	
//			}
//			b = System.nanoTime();
//			long x = b-a;
//			System.out.println(b-a);
//			reader.close();
//			
//			BufferedReader newreader = new BufferedReader(new FileReader(file));
//			a = System.nanoTime();
//			while ((line = newreader.readLine()) != null) {		
//				if (line.equals(search)) {
//					//System.out.println("Found");
//					break;
//				}
//				//System.out.println("B: " + line);
//			}
//			b = System.nanoTime();
//			long y = b-a;
//			System.out.println(b-a);
//			System.out.println();
//			System.out.println((y-x));
//			reader.close();
//			
////			String x = scanner.findWithinHorizon("aard", 300);
////			System.out.print(x);
//			
//			
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException ex) {
//			System.out.print("Woo");
//		}
//	}
	
}
