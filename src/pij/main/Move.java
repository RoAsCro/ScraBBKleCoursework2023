package pij.main;

import pij.main.Tiles.LetterTile;

import java.util.*;
import java.util.function.Predicate;

import static pij.main.Player.RACK_SIZE;
import static pij.main.ScraBBKleUtil.LOWER_D_ASCII_VALUE;
import static pij.main.ScraBBKleUtil.LOWER_R_ASCII_VALUE;
import static pij.main.ScraBBKleUtil.LOWER_A_ASCII_VALUE;
import static pij.main.ScraBBKleUtil.LOWER_Z_ASCII_VALUE;
import static pij.main.ScraBBKleUtil.DIRECTION_DIVISOR;



public class Move {
	private static final int ALL_LETTERS_BONUS = 70;

	private boolean pass;
	
	private char direction;
	
	private List<CharacterTile> tiles = new LinkedList<>();

	private Coordinate startCoordinate;
	
	private final Player PLAYER;

	private final Board BOARD;

	private Word word = new Word();

	private final static Predicate<BoardTile> IS_LETTER = CharacterTile.class::isInstance;
	
	public Move(Player player, Board board) {
		pass = false;
		this.PLAYER = player;
		this.BOARD = board;
	}


	public Word getWord() {
		return word;
	}

	public boolean tryMove(String input) {
		if (!validateInput(input)) {
			//System.out.println(input);
			System.out.println("That is not a valid move.");
			return false;
		}

		return tryMove();
	}

	public boolean tryMove() {
		if (pass) {
			//System.out.println("Pass");
			return true;
		}
		if (!checkPlacable()) {
			System.out.println("Word cannot be placed there.");
			return false;
		}
		if (!this.word.lookupWord()) {
			System.out.println("Word not in dictionary.");
			return false;
		}

		updateScore(this.word.getScore());
		confirmMove();
		return true;
	}

	public boolean validateInput(String input) {
		if (input.equals(",,")) {
			pass = true;
			return true;
		} else
			pass = false;

		String[] movesToTest = input.split(",");

		if (movesToTest.length != 3) {
			return false;
		}
		String letters = movesToTest[0];
		String location = movesToTest[1];
		String direction = movesToTest[2];

		if (location.length() < 2 || location.length() > 3)
			return false;

		String x = location.substring(0,1);
		String y = location.substring(1);

		int yLength = y.length();
		//Check the x coordinate is a letter, th
		if (!Character.isLetter(x.charAt(0)) || !Character.isDigit(y.charAt(0))
				|| !Character.isDigit(y.charAt(yLength - 1))
				|| (!direction.equals("r") && !direction.equals("d"))
				|| letters.length() < 1) {
			return false;
		}
		char[] chars = letters.toCharArray();
		//Check the player has the required tiles
		ArrayList<CharacterTile> moveTiles = new ArrayList<>();
		ArrayList<CharacterTile> playerRack = new ArrayList<>(this.PLAYER.getRack());
		for (char c : chars) {
			CharacterTile characterTile = playerRack.stream().filter(t->t.matchChar(c)).findFirst().orElse(null);
			if (characterTile == null) {
				return false;
			}
			playerRack.remove(characterTile);
			moveTiles.add(characterTile);
		}

		setAll(new Coordinate(x.charAt(0), Integer.parseInt(y)), direction.charAt(0), moveTiles);
		return true;
	}

	private void confirmMove() {
		LinkedList<LetterTile> wordTiles = this.word.getTiles();
		BoardReader reader = new BoardReader(this.BOARD, this.startCoordinate, this.direction);
		reader.conditionalNext((tile) -> !wordTiles.isEmpty(), (c) -> this.BOARD.placeTile(c, wordTiles.poll()));
	}

	public void setAll(Coordinate coordinate, char direction, List<CharacterTile> tiles) {
		pass = false;
		this.startCoordinate = coordinate;
		this.tiles = tiles;
		this.direction = direction;
	}
	
	public LinkedList<CharacterTile> getTiles() {
		LinkedList<CharacterTile> list = new LinkedList<>();
		Collections.addAll(list, tiles.toArray(new CharacterTile[0]));
		return list;
	}
	
	public boolean isPass() {
		return pass;
	}
	
	@Override
	public String toString() {
		return "The move is:	Word: " +
				String.join("", this.tiles.stream().map(t->""+t.getChar()).toList())
				+ " at position "
				+ startCoordinate + ", direction: " +
				(direction == 'd' ? "down" : "right");
	}
	
	public void updateScore(int score) {
		this.PLAYER.updateScore(score);
	}

	public boolean checkPlacable() {
		// xInc and yInc use the integer value of chars 'd' and 'r' to determine how to iterate
		// across the grid.
		int xInc = (this.direction - LOWER_D_ASCII_VALUE) / DIRECTION_DIVISOR;
		int yInc = (this.direction - LOWER_R_ASCII_VALUE) / DIRECTION_DIVISOR * -1;

		//If there is a letter directly behind the one specified in the move, return false
		if (this.BOARD.tileAt(this.startCoordinate.getX() - xInc,
				this.startCoordinate.getY() - yInc) instanceof CharacterTile) {
			System.out.println("Please use the position of the first letter in the word as the input location.");
			return false;
		}
		if (!constructWord()) {
			return false;
		}

		// Check word is either the first word being placed OR that it intersects with a
		// pre-existing word.
		int wordLength = getTiles().size();
		if (!(this.word.getTiles().size() > wordLength)) {
			int startX = this.startCoordinate.getX();
			int startY = this.startCoordinate.getY();
			int centre = this.BOARD.getCentre().getX();

			if (!this.BOARD.getStartState()) {
				System.out.println("Your word must cross another word");
				return false;
			} else if (!(startX + xInc * (wordLength - 1) >= centre && startX <= centre
					&& startY + yInc * (wordLength - 1) >= centre && startY <= centre)) {
				System.out.println("Your word must cross over the centre tile.");
				return false;
			}
		}
		return true;
	}

	private boolean constructWord() {
		resetWord();
		LinkedList<CharacterTile> tileQueue = getTiles();
		BoardReader reader = new BoardReader(this.BOARD, this.startCoordinate, this.direction);
		BoardTile currentTile;
		// Check it's possible to place the word on the board
		do {
			// Try placing tiles in rack
			reader.conditionalNext((tile) -> (!(IS_LETTER.test(tile)) && !tileQueue.isEmpty()), (c) -> {
				// Test there are no words parallel to this one
				reader.turn();
				boolean parallelWord = IS_LETTER.test(reader.next());
				reader.previous();
				if (IS_LETTER.test(reader.previous())) {
					parallelWord = true;
				}
				if (!parallelWord) {
					reader.next();
					reader.turn();
					CharacterTile characterTile = tileQueue.poll();
					this.word.addTile(characterTile);
					this.word.addTile(BOARD.tileAt(c));
				} else {
					reader.set(new Coordinate(-2, -2));
				}
			});
			// Gather existing letter tiles from the board
			currentTile = reader.conditionalNext(IS_LETTER, (c) -> this.word.addTile(BOARD.tileAt(c)));

		} while (!tileQueue.isEmpty() && currentTile != null);

		// Check all letters have been placed
		if ((!tileQueue.isEmpty())) {
			resetWord();
			return false;
		}
		this.word.finalise();
		if (getTiles().size() == RACK_SIZE) {
			this.word.addPoints(ALL_LETTERS_BONUS);
		}
		return true;
	}

	public void resetWord() {
		this.word = new Word();
	}
}