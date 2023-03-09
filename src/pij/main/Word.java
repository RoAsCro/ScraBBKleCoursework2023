package pij.main;

import java.util.LinkedList;

/**
 * A list of Tiles combined to form a scored word. Stores the tiles that
 * form the Word as well as a score that
 *
 * @author Roland Crompton
 */
public class Word implements TileSequence {

	private int baseScore = 0;

	private int multiplier = 1;

	private int score = 0;

	private final LinkedList<LetterTile> word = new LinkedList<>();

	public int getScore() {
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

	public void addTile(AbstractBoardTile tile) {
		tile.addToSequence(this);
	}

	public LinkedList<LetterTile> getTiles() {
		return word;
	}


}
