package pij.main;

/**
 * A coordinate representing a location on a ScraBBKle Board. Holds an x and y value
 * representing the array index numbers of a Board, i.e. the top-leftmost coordinate on a
 * Board is (0, 0), and the bottom-rightmost is (m-1, m-1) where m is the Board's magnitude.
 *
 * @author Roland Crompton
 */
public class Coordinate implements Comparable<Coordinate> {
    /**
     * The x-axis value of the Coordinate.
     */
    private final int x;
    /**
     * The y-axis value of the Coordinate.
     */
    private final int y;

    /**
     * Constructor for the Coordinate. Takes an x and y value corresponding to the array index numbers
     * on a Board.
     *
     * @param x the index of the x-axis value
     * @param y the index of the y-axis value
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor that takes a char and an int that should correspond to what
     * is displayed when a board is printed. The x and y will then be stored
     * as an x and y that will work with the two-dimensional array of the board.
     *
     * @param x a character between 'a' and 'z'
     * @param y an integer between 1 and 26
     */
    public Coordinate(char x, int y) {
        this.x = ((int) x) - ScraBBKleUtil.LOWER_A_ASCII_VALUE;
        this.y = y - 1;
    }

    /**
     * Compares two Coordinates. The greater Coordinate is the one with the greater x value,
     * then the greater y value.
     *
     * @param c the Coordinate to be compared
     * @return a positive number if this Coordinate is greater than c, 0 if they're the same,
     * a negative number otherwise
     */
    @Override
    public int compareTo(Coordinate c) {
        return hashCode() - c.hashCode();
    }

    /**
     * Tests if a Coordinate is equal to this one. Two Coordinates are equal
     * if they have the same x and y value.
     *
     * @param o the Coordinate to be tested against this one
     * @return true if o is a Coordinate with the same x and y values, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Coordinate c) {
            return this.x == c.x && this.y == c.y;
        }
        return false;
    }

    /**
     * Returns the x-axis value.
     *
     * @return the x-axis value of the Coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * Returns the y-axis value.
     *
     * @return the y-axis value of the Coordinate
     */
    public int getY() {
        return this.y;
    }

    /**
     * Returns a hash code representation of this Coordinate according to its x and y values.
     *
     * @return a hash code based in this Coordinate's x and y values
     */
    @Override
    public int hashCode() {
        return this.x * ScraBBKleUtil.MAX_MAGNITUDE + y;
    }

    /**
     * Returns a String representing the Coordinate in a human-readable way in the format "cy"
     * where c is the character from a-z representing the x-axis value and y is the integer
     * from 1-26 representing the y-axis value.
     *
     * @return a String representation of the Coordinate
     */
    @Override
    public String toString() {
        return "" + ((char) (this.x + ScraBBKleUtil.LOWER_A_ASCII_VALUE)) + (this.y + 1);
    }

}
