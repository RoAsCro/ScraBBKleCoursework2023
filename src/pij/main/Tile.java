package pij.main;

/**
 *
 * A tile has a value and some kind of character or character sequence.
 * All implementations of Tile should override toString.
 *
 * @author Roland Crompton
 *
 */
public interface Tile {

    int getValue();

    void addToSequence(TileSequence tileSequence);

    @Override
    String toString();


}
