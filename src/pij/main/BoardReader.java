package pij.main;

import java.util.LinkedList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static pij.main.ScraBBKleUtil.*;


/**
 * A reader for iterating across a Board.
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
     * The BoardReader's current location as a Coordinate
     */
    private Coordinate currentCoordinate;

    /**
     * Describes how the BoardReader moves along the x-axis.
     * 1 if the current direction is horizontal. 0 if it's vertical.
     */
    private int xInc;

    /**
     * Describes how the BoardReader moves along the y-axis.
     * 1 if the current direction is vertical. 0 if it's horizontal.
     */
    private int yInc;

    /**
     * Constructs a BoardReader over a given Board, initially at the given Coordinate,
     * and facing the given direction.
     *
     * @param board the Board the reader will read
     * @param coord the initial location of the reader on the board
     * @param direction the initial direction of the reader
     */
    public BoardReader(Board board, Coordinate coord, char direction) {
        this.board = board;
        //xInc and yInc use the integer value of 'd' or 'r' to determine how to iterate across the grid.
        this.xInc = (direction - LOWER_D_ASCII_VALUE) / DIRECTION_DIVISOR;
        this.yInc = (direction - LOWER_R_ASCII_VALUE) / DIRECTION_DIVISOR * -1;
        this.currentCoordinate = coord;
    }

    /**
     * Carries out a breadth first search of the Board, returning a SortedSet of every Coordinate
     * containing a CharacterTile. Assumes that all words placed on a Board intersect
     * with one another according to the rules of ScraBBKle.
     *
     * @return a SortedSet of the Coordinates of every CharacterTile on the Board
     */
    public SortedSet<Coordinate> breadthFirstSearch() {
        SortedSet<Coordinate> allTiles = new TreeSet<>();
        LinkedList<Coordinate> foundTiles = new LinkedList<>();
        allTiles.add(this.board.getCentre());
        foundTiles.add(this.board.getCentre());
        do {
            Coordinate currentCoord = foundTiles.poll();
            for (int j = 0; j < 2; j++) {
                setCoordinate(currentCoord);
                next();
                for (int i = 0; i < 2; i++) {
                    if (getCurrent() instanceof CharacterTile && !allTiles.contains(this.currentCoordinate)) {
                        foundTiles.add(this.currentCoordinate);
                        allTiles.add(this.currentCoordinate);
                    }
                    previous();
                    previous();
                }
                turn();
            }
        } while (!foundTiles.isEmpty());
        return allTiles;
    }

    /**
     * Starting at the current Tile, iterates across the board in the reader's direction
     * stopping at the first Tile that is either null or does not fulfil the given condition.
     * Performs a Consumer method on every Coordinate location it iterates,
     * including the first, but not including the last.
     *
     * @param condition the condition the tile must fulfil
     * @param method the operation performed on each tile
     * @return the first tile that is either null or does not fulfil the given condition
     */
    public BoardTile conditionalNext(Predicate<BoardTile> condition, Consumer<Coordinate> method) {
        BoardTile currentTile = this.board.tileAt(this.currentCoordinate);
        while (currentTile != null && condition.test(currentTile)) {
            method.accept(this.currentCoordinate);
            currentTile = next();
        }
        return currentTile;
    }

    /**
     * Starting at the current Tile, iterates across the board in the opposite direction to the
     * reader's current direction, stopping at the first Tile that is either
     * null or does not fulfil the given condition.
     * Performs a Consumer method on every Coordinate location it iterates,
     * including the first, but not including the last.
     *
     * @param condition the condition the tile must fulfil
     * @param method the operation performed on each tile
     * @return the first tile that is either null or does not fulfil the given condition
     */
    public BoardTile conditionalPrevious(Predicate<BoardTile> condition, Consumer<Coordinate> method) {
        reverse();
        BoardTile tile = conditionalNext(condition, method);
        reverse();
        return tile;
    }

    /**
     * Returns the current location of the BoardReader as a Coordinate
     *
     * @return the current coordinate of the BoardReader
     */
    public Coordinate getCoord() {
        return this.currentCoordinate;
    }


    /**
     * Returns the Tile at the BoardReader;s current location.
     *
     * @return the BoardTile at the reader's current location
     */
    public BoardTile getCurrent() {
        return this.board.tileAt(currentCoordinate);
    }

    /**
     * Returns the direction the BoardReader is facing as a character, either 'd' or 'r'.
     *
     * @return r if the reader is facing right, d if it is facing down
     */
    public char getDirection() {
        return (char) (Math.abs(this.xInc) * DIRECTION_DIVISOR + LOWER_D_ASCII_VALUE);
    }

    /**
     * Moves the reader to the next Tile according to its current location and direction.
     *
     * @return the BoardTile at the next location. Null if this is out of bounds of the Board
     */
    public BoardTile next() {
        return this.board.tileAt(this.currentCoordinate =
                new Coordinate(this.currentCoordinate.getX() + xInc,
                        this.currentCoordinate.getY() + yInc));
    }

    /**
     * Moves the reader in the opposite direction to its direction.
     *
     * @return the BoardTile at the previous location. Null if this is out of bounds of the Board
     */
    public BoardTile previous() {
        reverse();
        BoardTile tile = next();
        reverse();
        return tile;
    }

    /**
     * Reverses the direction of the reader.
     */
    private void reverse() {
        this.xInc = -this.xInc;
        this.yInc = -this.yInc;
    }

    /**
     * Sets the reader's location to the given Coordinate.
     *
     * @param coord the Coordinate location for the Reader to be set to
     */
    public void setCoordinate(Coordinate coord) {
        this.currentCoordinate = coord;
    }

    /**
     * Switches the direction of the reader from down to right, or right to down.
     */
    public void turn() {
        this.xInc += this.yInc;
        this.yInc = this.xInc - this.yInc;
        this.xInc = this.xInc - this.yInc;
    }

}
