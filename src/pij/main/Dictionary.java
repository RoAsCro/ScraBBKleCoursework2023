package pij.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

public final class Dictionary{

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

    private static final TreeSet<String> dictionary = new TreeSet<>();
    private static final TreeSet<String> suffixDictionary = new TreeSet<>(PREFIX_COMPARATOR);
    private static final TreeSet<String> prefixDictionary = new TreeSet<>(PREFIX_COMPARATOR);

    private Dictionary(){}

    public static void loadDictionary(File file) {

        dictionary.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                dictionary.add(line);
                suffixDictionary.add((new StringBuilder(line)).reverse().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        prefixDictionary.addAll(dictionary);
    }

    public static void loadDictionary() {
        loadDictionary(new File("../resources/wordlist.txt"));
    }

    public static boolean lookupWord(String word) {
        return dictionary.contains(word.toLowerCase());
    }

    public static boolean lookupPrefixSuffix(String word){
        boolean prefix = prefixDictionary.contains(word.toLowerCase());
        boolean suffix = suffixDictionary.contains(new StringBuilder(word).reverse().toString().toLowerCase());
        return prefix && suffix;
    }

}
