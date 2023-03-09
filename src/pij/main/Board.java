package pij.main;

import java.util.stream.IntStream;

/**
 * A board on which the game of ScraBBKle is played. A board's dimensions are S
 * x S where S is between 12 and 26.
 * 
 * @author Roland Crompton
 *
 */
public class Board {

	/** A two-dimensional array of tiles representing the board. */
	private final BoardTile[][] grid;

	/** The size of the board's axes. */
	private final int MAGNITUDE;

	/**
	 * The coordinates of the centre tile of the board. This will always be the same
	 * for both x and y axes. If the board has an even-numbered magnitude, it will
	 * be (magnitude / 2) If the board has an odd-numbered magnitude, it will be
	 * (magnitude / 2 + 1).
	 */
	private final int CENTRE;


	public Board(int magnitude, BoardTile[][] grid) {
		this.MAGNITUDE = magnitude;
		this.CENTRE = (magnitude - 1) / 2;
		this.grid = grid;
	}

	public Coordinate getCentre() {
		return new Coordinate(this.CENTRE, this.CENTRE);
	}

	public boolean getStartState() {
		return !(tileAt(getCentre()) instanceof LetterTile);
	}

	private boolean inBounds(Coordinate coord) {
		int x = coord.getX();
		int y = coord.getY();
		return (x >= 0 && x < this.MAGNITUDE && y >= 0 && y < this.MAGNITUDE);
	}

	/**
	 * Returns the tile at a given set of x,y coordinates on the board.
	 * 
	 * @param x x coordinate.
	 * @param y y coordinate.
	 * @return BoardTile at given coordinates. Null if tile out of bounds.
	 */
	public BoardTile tileAt(int x, int y) {
		if (!inBounds(new Coordinate(x, y)))
			return null;
		else
			return grid[x][y];
	}

	public BoardTile tileAt(Coordinate coordinate) {
		if (!inBounds(new Coordinate(coordinate.getX(), coordinate.getY())))
			return null;
		else
			return grid[coordinate.getX()][coordinate.getY()];
	}

	/**
	 * Prints the board on the console
	 */
	public void print() {
		char xLabel = 'a';
		int yLabel = 1;
		System.out.print("   ");
		IntStream.range(0, MAGNITUDE).forEach(i->System.out.format("%-4c", xLabel+i));

		System.out.println();
		IntStream.range(0, MAGNITUDE).forEach(i-> {
			System.out.format("%-3d", yLabel+i);
			IntStream.range(0, MAGNITUDE)
					.forEach(j->System.out.format("%-4s", tileAt(j,i).toString()));
			System.out.println();
		});
	}

	/**
	 *
	 *
	 * @param coord
	 * @param tile
	 * @return
	 */
	public boolean placeTile(Coordinate coord, BoardTile tile) {
		if (!inBounds(coord))
			return false;
		grid[coord.getX()][coord.getY()] = tile;
		return true;
	}

}
