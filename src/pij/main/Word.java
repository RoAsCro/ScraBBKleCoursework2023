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

	public void addTile(Tile tile) {
		tile.addToSequence(this);
	}

	public LinkedList<LetterTile> getTiles() {
		return word;
	}

	public boolean lookupWord(){
		LetterTile letterTile;
//		List<LetterTile> wordTiles = this.word.getTiles();
		if ((letterTile = this.word.stream().filter(t->!Character.isLetter(t.getChar())).findFirst().orElse(null)) != null) {
			int  index = this.word.indexOf(letterTile);
			LetterTile original = this.word.get(index);
			for (int i = LOWER_A_ASCII_VALUE; i <= LOWER_Z_ASCII_VALUE; i++) {
				this.word.set(index, new LetterTile("" + ((char) i), letterTile.getValue()));
				if (lookupWord()) {
					return true;
				} else
					this.word.set(index, original);
			}
		} else return Dictionary.lookupWord(toString());
		return false;
	}


}
