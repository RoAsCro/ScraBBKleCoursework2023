package pij.main;

public class BoardReader {
	private final Board board;
	private int initialX;
	private int initialY;
	private int currentX;
	private int currentY;
	private int xInc;
	private int yInc;
	
	public BoardReader(Board board, int x, int y, char direction) {
		this.board = board;
		//xInc and yInc use the integer value of 'd' or 'r' to determine how to iterate across the grid.
		this.xInc = (direction - 100) / 14;
		this.yInc = (direction - 114) / 14 * - 1;
		this.initialX = this.currentX = x;
		this.initialY = this.currentY = y;
	}
	
	public Tile next() {
		return board.tileAt(currentX += xInc, currentY += yInc);
	}
	
	public Tile previous() {
		reverse();
		Tile tile = next();
		reverse();
		return tile;
	}
	
	public Tile operativeNext(TileOperation method) {
		return new Tile("A", 1);
	}
	
	public Tile conditionalNext(Check condition, TileOperation method) {
		Tile currentTile = board.tileAt(currentX, currentY);
		while (board.tileAt(currentX, currentY) != null && condition.check(currentTile)) {
			method.execute(currentX, currentY);
			currentTile = next();
		}
		return currentTile;
	}
	
	public Tile conditionalPrevious(Check condition, TileOperation method) {
		reverse();
		Tile tile = conditionalNext(condition, method);
		reverse();
		return tile;
	}
	
	public void reset() {
		currentX = initialX;
		currentY = initialY;
	}
	
	private void reverse() {
		this.xInc = -xInc;
		this.yInc = -yInc;
	}
	
}
