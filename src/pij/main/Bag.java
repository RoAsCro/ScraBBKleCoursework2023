package pij.main;

import java.util.ArrayList;

public class Bag {
	//private Tile[] bag = new Tile[100];
	private ArrayList<Tile> bag = new ArrayList<>();
	
	public Bag() {
		tileGenerator("A", 1, 9);
		tileGenerator("B", 3, 2);
		tileGenerator("C", 3, 2);
		tileGenerator("D", 2, 4);
		tileGenerator("E", 1, 12);
		tileGenerator("F", 4, 2);
		tileGenerator("G", 2, 3);
		tileGenerator("H", 4, 2);
		tileGenerator("I", 1, 9);
		tileGenerator("J", 8, 1);
		tileGenerator("K", 5, 1);
		tileGenerator("L", 1, 4);
		tileGenerator("M", 3, 2);
		tileGenerator("N", 1, 6);
		tileGenerator("O", 1, 8);
		tileGenerator("P", 3, 2);
		tileGenerator("Q", 10, 1);
		tileGenerator("R", 1, 6);
		tileGenerator("S", 1, 4);
		tileGenerator("T", 1, 6);
		tileGenerator("U", 1, 4);
		tileGenerator("V", 4, 2);
		tileGenerator("W", 4, 2);
		tileGenerator("X", 8, 1);
		tileGenerator("Y", 4, 2);
		tileGenerator("Z", 10, 1);
		tileGenerator(" ", 0, 2);
	}
	
	private void tileGenerator(String tileText, int tileValue, int quantity) {
		for (int i = 0; i  < quantity; i++) {
			bag.add(new Tile(tileText, tileValue));
		}
	}
	
//	public Tile draw() {
//		
//	}
	
}
