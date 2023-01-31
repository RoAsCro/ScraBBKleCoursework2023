package pij.main;

import java.io.*;
import java.util.*;

public class Validator {
	
	private static TreeSet<String> dictionary = new TreeSet<>();

	private static TreeSet<String> prefixDictionary = new TreeSet<>((o1, o2) -> {
		if (o1.contains("!")) {
			o1 = o1.replace("!", "");
			if (o2.length() >= o1.length()) {
				o2 = o2.substring(0, o1.length());
//				System.out.println(o1);
//				System.out.println(o2);
			}
		}
		return o1.compareTo(o2);
	});

	private static TreeSet<String> comboDictionary = new TreeSet<>();

	public static TreeSet<String> dictionaryThree = new TreeSet<>((o1, o2) -> {
				int z = o1.length() - o2.length();
				if (z == 0) {
					return o1.compareTo(o2);
				}
				return z;
			});
	public static TreeSet<String> dictionaryTwo = new TreeSet<>((o1, o2) -> {
		int z = o1.length() - o2.length();
		if (!(z == 0))
			return z;

		if (o2.matches(o1))
			return 0;
		if (o1.contains(".")){
			System.out.println(o1);
			System.out.println(o2);
			StringBuilder builder1 = new StringBuilder(o1);
			StringBuilder builder2 = new StringBuilder(o2);

			for (int i = 0; i < o1.length(); i++) {

				int index = o1.indexOf(".");
				builder1.replace(index, index+1, "");
				o1 = builder1.toString();
				builder2.replace(index, index+1, "");
				o2 = builder2.toString();


			}
			return o1.compareTo(o2);
//			int x = o1.length();
//			int y = o2.length();
//			for (int i = 0; i < o1.length(); i++) {
//				char c1 = o1.charAt(i);
//				char c2;
//				if (i < y)
//					c2 = o2.charAt(i);
//				else
//					return 1;
//				if (c1 != c2)
//					return (int) c1 - c2;
//			}
//
//			if (x == y)
//				return 0;
//			if (x > y)
//				return 1;
//			System.out.println("v");
//			return -1;

		}
		return o1.compareTo(o2);

//		int x = o1.length();
//		int y = o2.length();
//		for (int i = 0; i < o1.length(); i++) {
//			char c1 = o1.charAt(i);
//			char c2;
//			if (i < y)
//				c2 = o2.charAt(i);
//			else
//				return 1;
//			if (c1 != c2)
//				return (int) c1 - c2;
//		}
//
//		if (x == y)
//			return 0;
//		if (x > y)
//			return 1;
//		return -1;



		//return o1.compareTo(o2);
//		int z = o1.length() - o2.length();
//		if (z == 0) {
//			return o1.compareTo(o2);
//		}
//		return z;
	});
	
	public static void loadDictionary(File file) {
		
		dictionary = new TreeSet<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			
			while ((line = reader.readLine()) != null) {
				dictionary.add(line);
				char[] chars = line.toCharArray();
				Arrays.sort(chars);
				String orderedLine = new String(chars);
				comboDictionary.add(orderedLine);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		dictionaryTwo.addAll(dictionary);
//		dictionaryThree.addAll(dictionary);
		prefixDictionary.addAll(dictionary);


	}
	
	public static void loadDictionary() {
		loadDictionary(new File("../resources/wordlist.txt"));
	}

	public static boolean lookupCombo(String word) {
		//System.out.println("SIZE: " + comboDictionary.size());
		char[] chars = word.toCharArray();
		Arrays.sort(chars);
		String orderedWord = new String(chars);

		Iterator<String> iter = comboDictionary.iterator();
			//System.out.println(newWord);
			//System.out.println(subTree);


			while (iter.hasNext()) {

				String s = iter.next();
//				System.out.println(newWord);
//				System.out.println(s);
				if (s.matches(word)) {
					System.out.println(s);
					return true;
				}
			}
		return comboDictionary.contains(orderedWord);
	}

	public static boolean lookupPrefix(String prefix) {
		return prefixDictionary.contains(prefix.toLowerCase() + "!");
	}

	public static boolean lookupWord(String word) {
//		if (word.contains(" ")){
//
////			TreeSet<String> test = new TreeSet<>((o1, o2) -> {
////				int z = o1.length() - o2.length();
////				if (z == 0) {
////					return o1.compareTo(o2);
////				}
////				return z;
////			});
////			test.addAll(dictionary);
//
//
////			SortedSet<String> subTree = dictionaryThree.tailSet(word.replace(' ', 'a').toLowerCase());
////			subTree = subTree.headSet((word.replace(' ', 'z') + "a").toLowerCase());
////
//			String newWord = word.replace(' ', '.').toLowerCase();
////			//System.out.println(newWord);
////			//return dictionaryTwo.contains(newWord);
////			Iterator<String> iter = subTree.iterator();
////			//System.out.println(newWord);
////			//System.out.println(subTree);
////
////
////			while (iter.hasNext()){
////
////				String s = iter.next();
//////				System.out.println(newWord);
//////				System.out.println(s);
////				if (s.matches(newWord)) {
////					System.out.println("Yes");
////					return true;
////				}
////			}
//			//System.out.println(".");
//			return dictionaryTwo.contains(newWord);
//		}
		//System.out.println("x");
		return dictionary.contains(word.toLowerCase());
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
