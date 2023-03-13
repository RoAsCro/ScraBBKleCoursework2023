package pij.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;

/**
 * A dictionary for looking up words in ScraBBKle.
 *
 * @author Roland Crompton
 */
public final class Dictionary{

    private static final TreeSet<String> dictionary = new TreeSet<>();

    private Dictionary(){}

    public static void loadDictionary(File file) {

        dictionary.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                dictionary.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadDictionary() {
        loadDictionary(new File("../resources/wordlist.txt"));
    }

    public static boolean lookupWord(String word) {
        return dictionary.contains(word.toLowerCase());
    }

}
