package pij.main;

import pij.main.tiles.LetterTile;
import pij.main.tiles.WildTile;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * The pool of CharacterTiles to be used in a game of ScraBBKle.
 * Also acts as a static CharacterTile factory.
 * <p></>
 * This class uses the standard Scrabble values for tiles and the standard quantities of each tile
 * as found at <a href="https://scrabble.hasbro.com/en-us/faq">...</a> on 06/03/2023.
 *
 * @author Roland Crompton
 */
public class Bag {
    /**
     * A two-dimensional array of the standard letter tiles and their values in Scrabble.
     */
    private static final TreeMap<String, Integer> ALPHABET = new TreeMap<>() {
        {
            put("A", 1); put("B", 3); put("C", 3); put("D", 2);
            put("E", 1); put("F", 4); put("G", 2); put("H", 4);
            put("I", 1); put("J", 8); put("K", 5); put("L", 1);
            put("M", 3); put("N", 1); put("O", 1); put("P", 3);
            put("Q", 10); put("R", 1); put("S", 1); put("T", 1);
            put("U", 1); put("V", 4); put("W", 4); put("X", 8);
            put("Y", 4); put("Z", 10);
        }
    };

    /**
     * ArrayList storing the contents of the bag.
     */
    private final List<CharacterTile> bag = new ArrayList<>();

    /**
     * Standard constructor for a game of ScraBBKle using the standard Scrabble quantities of each letter.
     * See the website linked above for the quantities of each letter.
     */
    public Bag() {
        this(new int[]
                {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1, 2});
    }

    /**
     * Customisable constructor. Takes an integer array 'quantities' of up to 27 values.
     * The bag will be instantiated with x of each letter where x is the value
     * at quantities[n] where n is that letter's position in the alphabet minus 1.
     * The number of wildcard tiles will be the value at quantities[27].
     * <p></>
     * If their are fewer than 27 values in quantities, there will be 0 of tiles of that position.
     *
     * @param quantities the array determining the quantity of each tile to be used.
     */
    public Bag(int[] quantities) {
        String[] alphabet = ALPHABET.keySet().toArray(new String[0]);
        for (int i = 0; i < quantities.length; i++) {
            String tileText;
            if (i < ALPHABET.size()) {
                tileText = alphabet[i];
            } else {
                tileText = " ";
            }
            generateTiles(tileText, this.bag, quantities[i]);
        }
    }

    /**
     * Returns a random tile from the bag, and deletes that tile. Returns null if
     * the bag is empty.
     *
     * @return a random AbstractBoardTile or null if bag is empty.
     */
    public CharacterTile draw() {
        int size = this.bag.size();
        if (size == 0)
            return null;
        int randomNumber = (int) (Math.random() * size);
        CharacterTile tile = this.bag.get(randomNumber);
        this.bag.remove(randomNumber);
        return tile;
    }

    /**
     * Factory method for generating CharacterTiles using the standard tile values.
     *
     * @param tileText the text of the Tile to be generated
     * @param tileList the list for generated Tiles to be added to
     * @param quantity the number of Tiles to be generated
     */
    public static void generateTiles(String tileText, List<CharacterTile> tileList, int quantity) {
        IntConsumer consumer = null;
        if (ALPHABET.containsKey(tileText)) {
            consumer = i -> tileList.add(new LetterTile(tileText, ALPHABET.get(tileText)));
        } else if (tileText.equals(" ")) {
            consumer = i -> tileList.add(new WildTile());
        }
        if (consumer != null) {
            IntStream.range(0, quantity).forEach(consumer);
        }

    }

    /**
     * Tests if the bag is empty.
     *
     * @return true if the bag is empty. False otherwise.
     */
    public boolean isEmpty() {
        return this.bag.isEmpty();
    }

}
