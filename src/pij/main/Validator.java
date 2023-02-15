package pij.main;

import java.io.*;
import java.util.*;

public class Validator {

	private static final Comparator<String> PREFIX_COMPARATOR = (o1, o2) -> {
		int len1 = o1.length();
		int len2 = o2.length();
		if (!(len1 == len2))
			return len1 - len2;
		int index = o1.indexOf(" ");
		if (index != -1) {
			o1 = o1.substring(0, index);
			o2 = o2.substring(0, index);
		}
		return o1.compareTo(o2);
	};

	private static TreeSet<String> dictionary = new TreeSet<>();
	private static TreeSet<String> prefixDictionary = new TreeSet<>(PREFIX_COMPARATOR);
	private static TreeSet<String> suffixDictionary = new TreeSet<>(PREFIX_COMPARATOR);

	public static void loadDictionary(File file) {
		
		dictionary = new TreeSet<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			
			while ((line = reader.readLine()) != null) {
				dictionary.add(line);
				suffixDictionary.add((new StringBuilder(line)).reverse().toString());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		prefixDictionary.addAll(dictionary);
	}
	
	public static void loadDictionary() {
		loadDictionary(new File("../resources/wordlist.txt"));
	}

	public static boolean lookupWord(String word) {
		if (word.contains(" ")){

			String newWord = word.toLowerCase();
			boolean prefix = prefixDictionary.contains(newWord);
			boolean suffix = suffixDictionary.contains(new StringBuilder(newWord).reverse().toString());

			if ((!suffix || !prefix)) {
				return false;
			}
			SortedSet<String> subDictionary = dictionary.subSet(newWord.replace(" ", "a"), newWord.replace(" ", "z") + "a");
			return lookupWildWord(word, subDictionary);
			}
		return dictionary.contains(word.toLowerCase());
	}


	public static boolean lookupWildWord(String word, SortedSet<String> subDictionary) {

		for (int i = 97; i <= 122; i++) {
			String newWord = word.replaceFirst(" ", ((char) i) + "");
			if (newWord.contains(" "))
				if (lookupWildWord(newWord, subDictionary))
					return true;
			if (subDictionary.contains(newWord.toLowerCase()))
					return true;
		}
		return false;
	}
	
	public static Board loadFile(String fileName) {
		File file = new File(fileName);
		Tile[][] grid;
		int magnitude;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

			String lineOne = reader.readLine();
			char[] lineOneChars = lineOne.toCharArray();

			for (int i = 0 ; i < lineOneChars.length ; i++) {
				char currentChar = lineOneChars[i];
				if (!Character.isDigit(currentChar)) {
					System.out.println("File not correctly formatted.");
					return null;
				}
			}

			if ((magnitude = Integer.parseInt(lineOne)) < 12 || magnitude > 26) {
				System.out.println("File not correctly formatted.");
				return null;
			}


			grid = new Tile[magnitude][magnitude];
			String row;
			for (int yCoord = 0; yCoord < magnitude; yCoord++) {
				row = reader.readLine();
				String tileText = "";
				int tileValue = 0;
				int xCoord = 0;
				
				for (int i = 0; i < row.length(); i++) {
					char current = row.charAt(i);
					tileText += current;

					if (current == '.' || current == ')' || current == '}') {
						Tile tile = new Tile(tileText, tileValue);
						grid[xCoord][yCoord] = tile;
						tileText = "";
						tileValue = 0;
						xCoord++;
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
			if (s.equals(input))
				return true;
		return false;
	}
	
}
