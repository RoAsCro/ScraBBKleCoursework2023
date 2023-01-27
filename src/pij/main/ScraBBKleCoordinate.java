package pij.main;

public class ScraBBKleCoordinate implements Comparable<ScraBBKleCoordinate> {
    private final int x;
    private final int y;

    private final static int MAXBOARDSIZE = 26;

    ScraBBKleCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
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
        return x * MAXBOARDSIZE + y;
    }

    @Override
    public int compareTo(ScraBBKleCoordinate c) {
        return hashCode() - c.hashCode();
    }
}
