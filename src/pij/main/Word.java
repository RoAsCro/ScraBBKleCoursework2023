package pij.main;

import pij.main.tiles.LetterTile;

import java.util.LinkedList;

import static pij.main.ScraBBKleUtil.LOWER_A_ASCII_VALUE;
import static pij.main.ScraBBKleUtil.LOWER_Z_ASCII_VALUE;

/**
 * A list of Tiles combined to form a scored word. Stores the Tiles that
 * form the actual word that this TileSequence represents,
 * as well as a score that reflects the values of its tiles.
 * <p><p/>
 * This implementation of TileSequence supports looking up words with wildcard characters
 * through the implementation of WildTiles.
 *
 * @author Roland Crompton
 */
public class Word implements TileSequence {

    /**
     * A list of BoardTiles that make up the String this Word represents.
     */
    private final LinkedList<BoardTile> word = new LinkedList<>();

    /**
     * The base score value of the Word before multiplication.
     */
    private int baseScore = 0;

    /**
     * A multiplier to be applied to a base score when the Word is finalised.
     */
    private int multiplier = 1;

    /**
     * The final score after the Word is finalised.
     */
    private int score = 0;

    /**
     * Adds points to the finalised Word.
     * Used for adding the bonus for placing all seven Tiles from a Player's rack in one move.
     *
     * @param points the points to be added to the score
     */
    public void addPoints(int points) {
        this.score += points;
    }

    /**
     * Adds the given Tile to the Word, calling that Tile's addToSequence method on this
     * TileSequence.
     *
     * @param tile the tile to be added
     */
    public void addTile(Tile tile) {
        tile.addToSequence(this);
    }

    /**
     * Finalises the Word's score. This involves setting the score to the base score
     * multiplied by the multiplier. Note that this will undo any points added using
     * addPoints.
     */
    public void finaliseScore() {
        this.score = this.baseScore * this.multiplier;
    }

    /**
     * Returns the finalised score of the Word after multiplication.
     *
     * @return the finalised score
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the Tiles that make up the String this Word represents
     * and that are to be added to a Board.
     *
     * @return a LinkedList of Tiles that are to be added to a Board
     */
    public LinkedList<BoardTile> getTiles() {
        return word;
    }

    /**
     * Adds the given value to the base score.
     *
     * @param value the integer value to be added to the base score
     */
    public void increaseBaseScore(int value) {
        baseScore += value;
    }

    /**
     * Multiplies the current multiplier by the given integer.
     *
     * @param value the integer to be added to the multiplier total
     */
    public void increaseMultiplier(int value) {
        multiplier *= value;
    }

    /**
     * Looks up the word this TileSequence represents in the ScraBBKle dictionary.
     * If the Word contains Tiles whose text is the wildcard space " ", the Word will look up
     * every possible combination of letter characters those spaces could be.
     * When this is the case, the Word will find the first combination of characters that
     * is in the dictionary and change the wildcard letters to be fit the word found.
     * <p></p>
     * The Dictionary must be loaded earlier in the program for this to work.
     *
     * @return true if the word is in the dictionary, false otherwise
     */
    public boolean lookupWord() {
        BoardTile boardTile;
        if ((boardTile = this.word.stream()
                .filter(t -> !Character.isLetter(t.toString()
                        .charAt(0))).findFirst().orElse(null)) != null) {
            int index = this.word.indexOf(boardTile);
            BoardTile original = this.word.get(index);
            for (int i = LOWER_A_ASCII_VALUE; i <= LOWER_Z_ASCII_VALUE; i++) {
                this.word.set(index, new LetterTile("" + ((char) i), boardTile.getValue()));
                if (lookupWord()) {
                    return true;
                } else
                    this.word.set(index, original);
            }
        } else {
            return Dictionary.lookupWord(toString());
        }
        return false;
    }

    /**
     * The Word as a String, formed from the Tiles that are to be placed on a Board.
     *
     * @return a String representation of the Word
     */
    @Override
    public String toString() {
        StringBuilder stringWord = new StringBuilder();
        for (BoardTile letter : this.word) {
            stringWord.append(letter.toString().charAt(0));
        }
        return stringWord.toString();
    }

}
