package pij.main;

import java.util.LinkedList;

public class Word {
	
	private double score = 0;
	
	private double multiplier = 1;
	
	private LinkedList<LetterTile> word = new LinkedList<>();
	
	public double getScore() {
		return score * multiplier;
	}
	
	
	@Override
	public String toString() {
		String stringWord = "";
		for (LetterTile letter : this.word) {
			stringWord += letter.getChar();
		}
		return stringWord;
	}
	
	public LetterTile[] getTiles() {
		return word.toArray(new LetterTile[0]);
	}
	
	public void addPoints(double points) {
		this.score += points;
	}
	
	public void increaseMultiplier(double multiplier) {
		this.multiplier *= multiplier;
	}
	
	public void addLetter(Tile tile) {
		int value = tile.getValue();
		if (LetterTile.class.isInstance(tile)) {
			LetterTile letter = (LetterTile) tile;
			score += value;
			word.add(letter);
		} else {
			if (tile.getText().charAt(0) == '(') {
				int letterValue = word.getLast().getValue();
				score += (value * letterValue) - letterValue;
				
			} else if (tile.getText().charAt(0) == '{') {
				this.multiplier *= value;
			}
		}
		//System.out.println(score);
	}
	
	public LinkedList<LetterTile> getTilesTwo() {
		return word;
	}
}
