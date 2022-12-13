package pij.main;

import java.util.Iterator;
import java.util.TreeSet;

public class BoardReader {
	private final Board board;
	private char direction;
	private int initialX;
	private int initialY;
	private int currentX;
	private int currentY;
	private int xInc;
	private int yInc;
	private TreeSet<Integer> tileTree = new TreeSet<>();
	
	public BoardReader(Board board, int x, int y, char direction) {
		this.board = board;
		//xInc and yInc use the integer value of 'd' or 'r' to determine how to iterate across the grid.
		this.direction = direction;
		this.xInc = (direction - 100) / 14;
		this.yInc = (direction - 114) / 14 * - 1;
		this.initialX = this.currentX = x;
		this.initialY = this.currentY = y;
	}
	
	public int getX() {
		return currentX;
	}
	
	public int getY() {
		return currentY;
	}
	
	public char getDirection() {
		return this.direction;
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
		while (currentTile != null && condition.check(currentTile)) {
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
		this.xInc = -this.xInc;
		this.yInc = -this.yInc;
	}
	
	public void turn() {
		this.xInc += this.yInc;
		this.yInc = xInc - yInc;
		this.xInc = xInc - yInc;
	}
	
	public void depthFirstSearch(TileOperation method) {
		currentX = board.getCentre();
		currentY = board.getCentre();
		depthFirstSearch(currentX, currentY, method);
	}
	
	public boolean depthFirstSearch(int x, int y, TileOperation method) {

		int treeRef = x * (board.getCentre() + 1) * 2 + y;
		Tile tile = board.tileAt(x, y);

		if (this.tileTree.contains(treeRef)) {
			previous();
			return false;
		}

		if (!LetterTile.class.isInstance(tile)) {
			//if (tile != null) tile.setText(" o ");
			method.execute(x, y);
			previous();
			return false;
		}
		//tile.setText("X");
		this.tileTree.add(treeRef);	
		
		for (int i = 0; i < 4; i++) {
			next();
			depthFirstSearch(currentX, currentY, method);
			reverse();
			if (i % 2 != 0)
				turn();
		}
		previous();
		System.out.println("TT: " + tileTree.size());
		return true;
	}
	
}
