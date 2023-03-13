package pij.main;

import java.util.stream.IntStream;

/**
 * A board on which the game of ScraBBKle is played. A Board's dimensions are SxS
 * where S is an integer between 12 and 26.
 * A Board contains a grid of BoardTiles which it can print on the console.
 * 
 * @author Roland Crompton
 *
 */
public class Board {

	/**
	 * The coordinate of the centre tile of the board. The x and y of the
	 * coordinate will be the same. This will be (magnitude - 1) / 2.
	 */
	private final Coordinate centre;

	/** A two-dimensional array of BoardTiles representing the board. */
	private final BoardTile[][] grid;

	/** The size of the Board's axes. */
	private final int magnitude;

	/**
	 * Constructor taking a two-dimensional array of BoardTiles where the array and all
	 * the arrays it contains are of the same length. The Board's magnitude will be this length,
	 * and its centre will be the (magnitude - 1) / 2
	 *
	 * @param grid a two-dimensional array of BoardTiles where the array and all
	 *             the arrays it contains are of the same length
	 */
	public Board(BoardTile[][] grid) {
		this.magnitude = grid.length;
		this.centre = new Coordinate((this.magnitude - 1) / 2, (this.magnitude - 1) / 2);
		this.grid = grid;
	}

	/**
	 * Returns the coordinate at the centre of the Board.
	 *
	 * @return the coordinate at the centre of the Board
	 */
	public Coordinate getCentre() {
		return this.centre;
	}

	/**
	 * Checks whether a move has been made on this Board. As the first move
	 * in ScraBBKle always crosses the centre Tile of a Board, returns whether the centre
	 * Tile is a CharacterTile.
	 *
	 * @return true if the centre Tile is a CharacterTile, false otherwise
	 */
	public boolean getStartState() {
		return !(tileAt(getCentre()) instanceof CharacterTile);
	}

	/**
	 * Checks if a given Coordinate represents a location on the Board.
	 *
	 * @param coordinate the Coordinate to be checked
	 * @return true if the coordinate is less than 0 or greater than the Board's magnitude,
	 * false otherwise
	 */
	private boolean outOfBounds(Coordinate coordinate) {
		int x = coordinate.getX();
		int y = coordinate.getY();
		return (x < 0 || x >= this.magnitude || y < 0 || y >= this.magnitude);
	}

	/**
	 * Places a Tile on the Board. Returns whether placing the tile was successful.
	 *
	 * @param coordinate the Coordinate where the Tile is to be placed
	 * @param tile the Tile to be placed
	 * @return true if the Coordinate is on the Board, false otherwise
	 */
	public boolean placeTile(Coordinate coordinate, BoardTile tile) {
		if (outOfBounds(coordinate))
			return false;
		this.grid[coordinate.getX()][coordinate.getY()] = tile;
		return true;
	}

	/**
	 * Prints the board on the console.
	 */
	public void print() {
		char xLabel = 'a';
		int yLabel = 1;
		System.out.print("   ");
		IntStream.range(0, magnitude).forEach(i->System.out.format("%4s", " " + ((char) (xLabel+i)) + "  "));

		System.out.println();
		IntStream.range(0, magnitude).forEach(i-> {
			System.out.format("%-3d", yLabel+i);
			IntStream.range(0, magnitude)
					.forEach(j->{
						String print = tileAt(j, i).toString();
						int width = Math.min(print.length() + 1, 3);
						System.out.format("%" + width + "s", print);
						if (print.length() < 4)
							System.out.format("%" + (4 - width) + "s", "");
						});
			System.out.println();
		});
	}

	/**
	 * Returns the tile at a given set of x,y coordinates on the board.
	 *
	 * @param x the x coordinate
	 * @param y  the y coordinate
	 * @return the BoardTile at given coordinates. Null if Tile out of bounds
	 */
	public BoardTile tileAt(int x, int y) {
		return tileAt(new Coordinate(x, y));
	}

	/**
	 * Returns the tile at a given Coordinates on the board.
	 *
	 * @param coordinate the Coordinate to be checked
	 * @return the BoardTile at given Coordinate. Null if Tile is out of bounds
	 */
	public BoardTile tileAt(Coordinate coordinate) {
		if (outOfBounds(coordinate))
			return null;
		else
			return this.grid[coordinate.getX()][coordinate.getY()];
	}

}
