package pij.main;

import java.io.*;
import java.util.*;

public class Validator {
	
	private static TreeSet<String> dictionary = new TreeSet<>();
	@Deprecated
	private static final Comparator<String> PREFIX_COMPARATOR_OLD = (o1, o2) -> {
		if (o1.contains(" ")) {
			o1 = o1.replace(" ", "");
			if (o2.length() >= o1.length()) {
				o2 = o2.substring(0, o1.length());
			}
		}

		return o1.compareTo(o2);
	};
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

	private static final Comparator<String> PRE_LEN_ANA_COMPARATOR = (o1, o2) -> {
		int len1 = o1.length();
		int len2 = o2.length();
		if (!(len1 == len2))
			return len1 - len2;

		char[] chars = o1.toCharArray();
		Arrays.sort(chars);
		String orderedLine = new String(chars);
		char[] chars2 = o2.toCharArray();
		Arrays.sort(chars2);
		String orderedLine2 = new String(chars2);
		int comboCheck = orderedLine.compareTo(orderedLine2);
		if (comboCheck != 0) {
			return comboCheck;
		}

		int index = o1.indexOf(" ");
		if (index != -1) {
			o1 = o1.substring(0, index);
			o2 = o2.substring(0, index);
		}
		return o1.compareTo(o2);
	};


	private static TreeSet<String> prefixDictionary = new TreeSet<>(PREFIX_COMPARATOR);

	private static TreeSet<String> suffixDictionary = new TreeSet<>(PREFIX_COMPARATOR);

	private static TreeSet<String> anagramDictionary = new TreeSet<>(PRE_LEN_ANA_COMPARATOR);
	private static TreeSet<String> anagramDictionary2 = new TreeSet<>(PRE_LEN_ANA_COMPARATOR);

	private static TreeSet<String> comboDictionary = new TreeSet<>();
	public static TreeSet<String> lengthDictionary = new TreeSet<>((o1, o2) -> {
		int z = o1.length() - o2.length();
		if (z == 0) {
			return o1.compareTo(o2);
		}
		return z;
		});
	
	public static void loadDictionary(File file) {
		
		dictionary = new TreeSet<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			
			while ((line = reader.readLine()) != null) {
				dictionary.add(line);
				suffixDictionary.add((new StringBuilder(line)).reverse().toString());
				anagramDictionary2.add((new StringBuilder(line)).reverse().toString());
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
		lengthDictionary.addAll(dictionary);
		prefixDictionary.addAll(dictionary);
		anagramDictionary.addAll(dictionary);



	}
	
	public static void loadDictionary() {
		loadDictionary(new File("../resources/wordlist.txt"));
	}

	public static TreeSet<String > getDictionary() {
		return dictionary;
	}

	public static void compare(String s) {
		s = s.toLowerCase();
		int dLength = dictionary.subSet(s.replaceAll(" ", "a"), true, s.replaceAll(" ", "z"), true).size();
		int pLength = prefixDictionary.subSet(s.replaceAll(" ", "a"), true, s.replaceAll(" ", "z"), true).size();
		int aLength = anagramDictionary.subSet(s.replaceAll(" ", "a"), true, s.replaceAll(" ", "z"), true).size();
		int a2Length = anagramDictionary2.subSet(new StringBuilder(s.replaceAll(" ", "a")).reverse().toString(), true, new StringBuilder(s.replaceAll(" ", "z")).reverse().toString(), true).size();

		System.out.println(dLength);
		System.out.println(pLength);
		System.out.println(aLength);
		System.out.println(a2Length);

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
		return prefixDictionary.contains(prefix.toLowerCase() + " ");
	}

	public static boolean lookupWord(String word) {
		if (word.contains(" ")){

			String newWord = word.toLowerCase();
			//System.out.println(newWord);
//			String head = newWord.substring(0, word.indexOf(" ") + 1);
//			String tail = (new StringBuilder(newWord.substring(word.lastIndexOf(" ")))).reverse().toString();
			boolean prefix = prefixDictionary.contains(newWord);
			boolean suffix = suffixDictionary.contains(new StringBuilder(newWord).reverse().toString());

			//System.out.println(suffix);
			//System.out.println(prefix);
			if ((!suffix || !prefix)) {
				return
						false;
			}
			SortedSet<String> subDictionary = dictionary.subSet(newWord.replace(" ", "a"), newWord.replace(" ", "z") + "a");
			//System.out.println(subDictionary);
			return lookupWildWord(word, subDictionary);
			//return lookupWildWord(word, dictionary);
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
			if (s.equals(input))
				return true;
		return false;
	}
	public static void main(String[] args) {
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
