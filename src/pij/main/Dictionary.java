package pij.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A dictionary for looking up words in ScraBBKle. Should only be used through its  static methods.
 *
 * @author Roland Crompton
 */
public final class Dictionary {

    /**
     * A TreeSet containing all the possible words. Must be loaded on program start.
     */
    private static final SortedSet<String> DICTIONARY = new TreeSet<>();

    /**
     * Private constructor. This class should not be instantiated.
     */
    private Dictionary() {
    }

    /**
     * Loads the default dictionary for ScraBBKle.
     */
    public static void loadDictionary() {
        loadDictionary(new File("../resources/wordlist.txt"));
    }

    /**
     * Loads a dictionary from the given file, storing it in the dictionary field.
     *
     * @param file the File to be loaded from
     */
    public static void loadDictionary(File file) {
        DICTIONARY.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                DICTIONARY.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Looks up a given String in the dictionary.
     *
     * @param word the word to be looked up
     * @return true if the dictionary contains the word, false otherwise
     */
    public static boolean lookupWord(String word) {
        return DICTIONARY.contains(word.toLowerCase());
    }

}
