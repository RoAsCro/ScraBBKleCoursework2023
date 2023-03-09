package pij.main;

import java.util.LinkedList;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static pij.main.ScraBBKleUtil.*;


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

    private Coordinate currentCoordinate;

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
        this(board, board.getCentre(), direction);
    }

    public BoardReader(Board board, Coordinate coord, char direction) {
        this(board, coord.getX(), coord.getY(), direction);
    }

    public BoardReader(BoardReader reader) {
        this(reader.board, reader.currentCoordinate, reader.getDirection());
    }

    public BoardReader(Board board, int x, int y, char direction) {
        this.board = board;
        //xInc and yInc use the integer value of 'd' or 'r' to determine how to iterate across the grid.
        this.xInc = (direction - LOWER_D_ASCII_VALUE) / DIRECTION_DIVISOR;
        this.yInc = (direction - LOWER_R_ASCII_VALUE) / DIRECTION_DIVISOR * -1;
        this.currentCoordinate = (new Coordinate(x, y));
    }

    public Coordinate getCoord() {
        return this.currentCoordinate;
    }

    public char getDirection() {
        return (char) (Math.abs(this.xInc) * DIRECTION_DIVISOR + LOWER_D_ASCII_VALUE);
    }

    /**
     * Moves the reader to the next tile according to its current location and increment.
     *
     * @return the AbstractBoardTile at the next location. If this is out of bounds, returns null.
     */
    public AbstractBoardTile next() {
        return board.tileAt(currentCoordinate =
                new Coordinate(currentCoordinate.getX() + xInc, currentCoordinate.getY() + yInc));
    }

    public AbstractBoardTile getCurrent() {
        return this.board.tileAt(currentCoordinate);
    }

    /**
     * Moves the reader in the opposite direction to its direction.
     *
     * @return the AbstractBoardTile at the previous location. If this is out of bounds, returns null.
     */
    public AbstractBoardTile previous() {
        reverse();
        AbstractBoardTile tile = next();
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
    public AbstractBoardTile conditionalNext(Predicate<AbstractBoardTile> condition, Consumer<Coordinate> method) {
        AbstractBoardTile currentTile = board.tileAt(currentCoordinate);
        while (currentTile != null && condition.test(currentTile)) {
            method.accept(currentCoordinate);
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
    public AbstractBoardTile conditionalPrevious(Predicate<AbstractBoardTile> condition, Consumer<Coordinate> method) {
        reverse();
        AbstractBoardTile tile = conditionalNext(condition, method);
        reverse();
        return tile;
    }

    public void set(Coordinate coord) {
        currentCoordinate = coord;
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
     * Carries out a breadth first search of the board.
     *
     * @return
     */
    public TreeSet<Coordinate> breadthFirstSearch() {
        TreeSet<Coordinate> allTiles = new TreeSet<>();
        LinkedList<Coordinate> foundTiles = new LinkedList<>();
        set(this.board.getCentre());
        Coordinate currentCoord = this.currentCoordinate;
        foundTiles.add(currentCoord);
        allTiles.add(currentCoord);
        do {
            currentCoord = foundTiles.poll();
            set(currentCoord);
            for (int j = 0; j < 2; j++) {
                next();
                for (int i = 0; i < 2; i++) {
                    currentCoord = this.currentCoordinate;
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
