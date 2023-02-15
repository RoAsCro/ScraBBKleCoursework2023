package pij.main;

import java.util.LinkedList;

public class Word {
	
	private double baseScore = 0;
	
	private double multiplier = 1;

	private double score = 0;
	
	private LinkedList<LetterTile> word = new LinkedList<>();
	
	public double getScore() {
		return score;
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

	public void finalise() {
		this.score = this.baseScore * this.multiplier;
	}
	
	public void addLetter(Tile tile) {
		int value = tile.getValue();
		if (tile instanceof LetterTile letter) {
			baseScore += value;
			word.add(letter);
		} else {
			if (tile.toString().charAt(0) == '(') {
				int letterValue = word.getLast().getValue();
				baseScore += (value * letterValue) - letterValue;
				
			} else if (tile.toString().charAt(0) == '{') {
				this.multiplier *= value;
			}
		}
	}
	
	public LinkedList<LetterTile> getTilesTwo() {
		return word;
	}
}
