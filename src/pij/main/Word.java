package pij.main;

import java.util.LinkedList;

public class Word {
	private double score = 0;
	
	private double multiplier = 1;
	
	private LinkedList<LetterTile> word = new LinkedList<>();
	
	public double getScore() {
		return score * multiplier;
	}
	
	public String getWord() {
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
	
	public void addLetter(LetterTile letter) {
		word.add(letter);
	}
	
}
