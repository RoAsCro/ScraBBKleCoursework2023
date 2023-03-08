package pij.main;

import java.util.LinkedList;

public class Word {
	
	private double baseScore = 0;
	
	private double multiplier = 1;

	private double score = 0;
	
	private final LinkedList<LetterTile> word = new LinkedList<>();
	
	public double getScore() {
		return score;
	}

	
	@Override
	public String toString() {
		StringBuilder stringWord = new StringBuilder();
		for (LetterTile letter : this.word) {
			stringWord.append(letter.getChar());
		}
		return stringWord.toString();
	}

	public void addPoints(double points) {
		this.score += points;
	}

	public void increaseBaseScore(int value) {
		baseScore += value;
	}

	public void increaseMultiplier(int value) {
		multiplier *= value;
	}

	public void finalise() {
		this.score = this.baseScore * this.multiplier;
	}
	
	public void addLetter(Tile tile) {
		tile.addToWord(this);
	}
	
	public LinkedList<LetterTile> getTiles() {
		return word;
	}


}
