package pij.main;

import pij.main.Tiles.LetterTile;

import java.util.LinkedList;

import static pij.main.ScraBBKleUtil.LOWER_A_ASCII_VALUE;
import static pij.main.ScraBBKleUtil.LOWER_Z_ASCII_VALUE;

/**
 * A list of Tiles combined to form a scored word. Stores the tiles that
 * form the Word as well as a score that reflects the values of its tiles.
 * <p><p/>
 * This implementation of TileSequence supports looking up words with wildcard characters.
 *
 * @author Roland Crompton
 */
public class Word implements TileSequence {

	private int baseScore = 0;

	private int multiplier = 1;

	private int score = 0;

	private final LinkedList<BoardTile> word = new LinkedList<>();

	public int getScore() {
		return score;
	}


	@Override
	public String toString() {
		StringBuilder stringWord = new StringBuilder();
		for (BoardTile letter : this.word) {
			stringWord.append(letter.toString().charAt(0));
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

	public void addTile(Tile tile) {
		tile.addToSequence(this);
	}

	public LinkedList<BoardTile> getTiles() {
		return word;
	}

	public boolean lookupWord(){
		BoardTile boardTile;
		if ((boardTile = this.word.stream()
				.filter(t->!Character.isLetter(t.toString()
						.charAt(0))).findFirst().orElse(null)) != null) {
			int  index = this.word.indexOf(boardTile);
			BoardTile original = this.word.get(index);
			for (int i = LOWER_A_ASCII_VALUE; i <= LOWER_Z_ASCII_VALUE; i++) {
				this.word.set(index, new LetterTile("" + ((char) i), boardTile.getValue()));
				if (lookupWord()) {
					return true;
				} else
					this.word.set(index, original);
			}
		} else return Dictionary.lookupWord(toString());
		return false;
	}


}
