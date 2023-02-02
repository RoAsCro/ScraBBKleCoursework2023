package pij.main;

public class ScraBBKleCoordinate implements Comparable<ScraBBKleCoordinate> {
    private final int x;
    private final int y;

    private final static int MAX_BOARD_SIZE = 26;

    private final static int ASCII_OF_LOWER_A = 97;

    public ScraBBKleCoordinate(int x, int y) {
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
    public ScraBBKleCoordinate(char x, int y) {
        this.x = ((int) x) - ASCII_OF_LOWER_A;
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
        return "(" + x + ", " + y + ")";
    }

    @Override
    public int hashCode() {
        return x * MAX_BOARD_SIZE + y;
    }

    @Override
    public int compareTo(ScraBBKleCoordinate c) {
        return hashCode() - c.hashCode();
    }
}
