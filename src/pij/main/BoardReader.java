package pij.main;

import java.util.LinkedList;
import java.util.TreeSet;

/**
 * A reader for iterating across a ScraBBKle Board object.
 * The reader can travel across the board in the specified direction, returning Tiles and/or carrying
 * out given operations on tiles as it passes over them.
 *
 * @author Roland Crompton
 */
public class BoardReader {
    /**
     * The Board over which the reader iterates .
     */
    private final Board board;

    /**
     * The current x coordinate of the reader.
     */
    private int currentX;

    /**
     * The current y coordinate of the reader.
     */
    private int currentY;

    /**
     * 1 if the current direction is horizontal. 0 if it's vertical.
     */
    private int xInc;

    /**
     * 1 if the current direction is vertical. 0 if it's horizontal.
     */
    private int yInc;

    /**
     * A TreeSet for storing every visited tile on the board during a search of the board.
     */
    private TreeSet<Integer> tileTree = new TreeSet<>();

    public BoardReader(Board board, char direction) {
        this(board, board.getCentre(), board.getCentre(), direction);
    }

    public BoardReader(Board board, ScraBBKleCoordinate coord, char direction) {
        this(board, coord.getX(), coord.getY(), direction);
    }

    public BoardReader(BoardReader reader) {
        this(reader.board, reader.currentX, reader.currentY, reader.getDirection());
    }

    public BoardReader(Board board, int x, int y, char direction) {
        this.board = board;
        //xInc and yInc use the integer value of 'd' or 'r' to determine how to iterate across the grid.
        this.xInc = (direction - 100) / 14;
        this.yInc = (direction - 114) / 14 * -1;
        this.currentX = x;
        this.currentY = y;
    }

    public int getX() {
        return currentX;
    }

    public int getY() {
        return currentY;
    }

    public char getDirection() {
        return (char) (Math.abs(this.xInc) * 14 + 100);
    }

    /**
     * Moves the reader to the next tile according to its current location and increment.
     *
     * @return the Tile at the next location. If this is out of bounds, returns null.
     */
    public Tile next() {
        return board.tileAt(currentX += xInc, currentY += yInc);
    }

    public Tile getCurrent() {
        return this.board.tileAt(this.currentX, this.currentY);
    }

    /**
     * Moves the reader in the opposite direction to its direction.
     *
     * @return the Tile at the previous location. If this is out of bounds, returns null.
     */
    public Tile previous() {
        reverse();
        Tile tile = next();
        reverse();
        return tile;
    }

    /**
     * Starting at the current tile, iterates across the board in the reader's direction
     * until it comes across a tile that is either null or does not fulfil the given condition.
     * Performs a method on every tile.
     *
     * @param condition the condition the tile must fulfil.
     * @param method    the operation performed on each tile.
     * @return the first tile that is either null or does not fulfil the given condition.
     */
    public Tile conditionalNext(Check condition, TileOperation method) {
        Tile currentTile = board.tileAt(currentX, currentY);
        while (currentTile != null && condition.check(currentTile)) {
            method.execute(currentX, currentY);
            currentTile = next();
        }
        return currentTile;
    }

    /**
     * Carries out the same operation as conditionalNext, but in the opposite direction.
     *
     * @param condition the condition the tile must fulfil.
     * @param method    the operation performed on each tile.
     * @return the first tile that is either null or does not fulfil the given condition.
     */
    public Tile conditionalPrevious(Check condition, TileOperation method) {
        reverse();
        Tile tile = conditionalNext(condition, method);
        reverse();
        return tile;
    }

    /**
     * Sets the current coordinates to the given (x, y).
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    public void set(int x, int y) {
        currentX = x;
        currentY = y;
    }

    public void set(ScraBBKleCoordinate coord) {
        set(coord.getX(), coord.getY());
    }

    /**
     * Reverses the direction of the reader.
     */
    private void reverse() {
        this.xInc = -this.xInc;
        this.yInc = -this.yInc;
    }

    /**
     * Switches the direction of the reader.
     */
    public void turn() {
        this.xInc += this.yInc;
        this.yInc = xInc - yInc;
        this.xInc = xInc - yInc;
    }

    /**
     * Initialises depthFirstSearch(int, int, BooleanTileOperation) starting at the centre of the board.
     *
     * @param method the method to be passed into the DFS.
     * @return whether the DFS was successful.
     */
    public boolean depthFirstSearch(BooleanTileOperation method) {
        set(board.getCentre(), board.getCentre());
        tileTree = new TreeSet<>();
        return depthFirstSearch(currentX, currentY, method);
    }

    /**
     * Carries out a depth first search of LetterTiles on the Board. The specified operation will be carried out
     * on every non-letter tile adjacent to a letter tile.
     *
     * @param x      the starting x coordinate.
     * @param y      the starting y coordinate.
     * @param method a boolean method carried out on the non-letter tiles.
     * @return true if at any stage the method returns true. False if it never does.
     */
    private boolean depthFirstSearch(int x, int y, BooleanTileOperation method) {
        set(x, y);
        int treeRef = x * (board.getCentre() + 1) * 2 + y;
        Tile tile = board.tileAt(x, y);

        if (this.tileTree.contains(treeRef)) {
            previous();
            return false;
        }

        if (!LetterTile.class.isInstance(tile)) {
            //if (tile != null) tile.setText(" o ");
            if (method.execute(x, y))
                return true;
            previous();
            return false;
        }
        this.tileTree.add(treeRef);

        for (int i = 0; i < 4; i++) {
            next();
            if (depthFirstSearch(currentX, currentY, method))
                return true;
            reverse();
            if (i % 2 != 0)
                turn();
        }
        previous();
        //System.out.println("TT: " + tileTree.size());
        return false;
    }

    /**
     * Carries out a breadth first search of the board.
     *
     * @return
     */
    public TreeSet<ScraBBKleCoordinate> breadthFirstSearch() {
        TreeSet<ScraBBKleCoordinate> allTiles = new TreeSet<>();
        LinkedList<ScraBBKleCoordinate> foundTiles = new LinkedList<>();
        set(this.board.getCentre(), this.board.getCentre());
        ScraBBKleCoordinate currentCoord = new ScraBBKleCoordinate(this.currentX, this.currentY);
        foundTiles.add(currentCoord);
        allTiles.add(currentCoord);
        do {
            currentCoord = foundTiles.poll();
            set(currentCoord.getX(), currentCoord.getY());
            for (int j = 0; j < 2; j++) {
                next();
                for (int i = 0; i < 2; i++) {
                    currentCoord = new ScraBBKleCoordinate(this.currentX, this.currentY);
                    if (getCurrent() instanceof LetterTile && !allTiles.contains(currentCoord)) {
                        foundTiles.add(currentCoord);
                        allTiles.add(currentCoord);
                    }
                    previous();
                    previous();
                }
                next();
                next();
                next();
                turn();
            }

        } while (!foundTiles.isEmpty());

        return allTiles;
    }

}
