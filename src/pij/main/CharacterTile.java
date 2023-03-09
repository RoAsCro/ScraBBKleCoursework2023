package pij.main;

/**
 * A CharacterTile is a Tile that holds a character and can be checked against a character
 * to see if it matches. CharacterTiles may also be present in a Player's tile rack.
 *
 * @author Roland Crompton
 */
public interface CharacterTile extends Tile {

    char getChar();

    boolean matchChar(char c);

}
