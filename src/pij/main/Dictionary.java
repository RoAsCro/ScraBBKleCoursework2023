package pij.main;

import java.io.*;
import java.util.*;

public class Dictionary{

    private static int LOWER_A_CHAR_INT = 97;
    private static int LOWER_Z_CHAR_INT = 122;
    public static final LinkedList<Character> WILD_CHARACTERS = new LinkedList<>();

    private static final Comparator<String> PREFIX_COMPARATOR = (o1, o2) -> {
        int len1 = o1.length();
        int len2 = o2.length();
        if (!(len1 == len2))
            return len1 - len2;

        char[] chars = o1.toCharArray();
        Arrays.sort(chars);
        String orderedLine = new String(chars);
        char[] chars2 = o2.toCharArray();
        Arrays.sort(chars2);
        String orderedLine2 = new String(chars);
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

    private static TreeSet<String> dictionary = new TreeSet<>();
    private static TreeSet<String> suffixDictionary = new TreeSet<>(PREFIX_COMPARATOR);
    private static TreeSet<String> prefixDictionary = new TreeSet<>(PREFIX_COMPARATOR);

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
//        System.out.println(word);
//        if (word.contains(" ")){
//            WILD_CHARACTERS.clear();
//
//            String newWord = word.toLowerCase();
//            boolean prefix = prefixDictionary.contains(newWord);
//            boolean suffix = suffixDictionary.contains(new StringBuilder(newWord).reverse().toString());
//
//            if ((!suffix || !prefix)) {
//                return false;
//            }
//            SortedSet<String> subDictionary = dictionary.subSet(newWord.replace(" ", "a"), newWord.replace(" ", "z") + "a");
//            return lookupWildWord(word, subDictionary);
//        }
        return dictionary.contains(word.toLowerCase());
    }

    public static boolean lookupPrefixSuffix(String word){
        boolean prefix = prefixDictionary.contains(word.toLowerCase());
        boolean suffix = suffixDictionary.contains(new StringBuilder(word).reverse().toString().toLowerCase());
        return prefix && suffix;
    }

    public static boolean lookupWildWord(String word, SortedSet<String> subDictionary) {

        for (int i = LOWER_A_CHAR_INT; i <= LOWER_Z_CHAR_INT; i++) {
            String newWord = word.replaceFirst(" ", ((char) i) + "");
            if (newWord.contains(" "))
                if (lookupWildWord(newWord, subDictionary)) {
                    WILD_CHARACTERS.addFirst((char) i);
                    return true;
                }
            if (subDictionary.contains(newWord.toLowerCase())) {
                WILD_CHARACTERS.addFirst((char) i);
                return true;
            }
        }
        return false;
    }

}
