package pij.main;

/**
 * A CharacterTile is a Tile that represent a character and can be checked against a character
 * to see if it matches. CharacterTiles may also be present in a Player's tile rack.
 *
 * @author Roland Crompton
 */
public interface CharacterTile extends Tile {

    /**
     * Returns the character this Tile represents.
     *
     * @return the character this Tile represents.
     */
    char getChar();

    /**
     * Checks whether the given character matches the character this Tile represents
     *
     * @param character the character for this Tile to be checked against
     * @return true if the character matches, false otherwise
     */
    boolean matchChar(char character);

}
