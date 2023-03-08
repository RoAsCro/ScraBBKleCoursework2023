package pij.main;

public class Coordinate implements Comparable<Coordinate> {
    private final int x;
    private final int y;
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor that takes a char and an int that should correspond to what is displayed when a board is printed.
     * The x and y will then be stored as an x and y that will work with the two-dimensional array of the board.
     *
     * @param x a character between 'a' and 'z'.
     * @param y an integer between 1 and 26.
     */
    public Coordinate(char x, int y) {
        this.x = ((int) x) - ScraBBKleUtil.LOWER_A_ASCII_VALUE;
        this.y = y - 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "" + ((char) (x + ScraBBKleUtil.LOWER_A_ASCII_VALUE)) + (y + 1);
    }

    @Override
    public int hashCode() {
        return x * ScraBBKleUtil.MAX_MAGNITUDE + y;
    }

    @Override
    public int compareTo(Coordinate c) {
        return hashCode() - c.hashCode();
    }
}
