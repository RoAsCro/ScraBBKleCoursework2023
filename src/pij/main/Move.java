package pij.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import static pij.main.Player.RACK_SIZE;
import static pij.main.ScraBBKleUtil.*;


/**
 * A class that contains and validates a ScraBBKle move.
 *
 * @author Roland Crompton
 */
public class Move {

	/**
	 * The bonus earned for playing all seven tiles in one move.
	 */
	private static final int ALL_LETTERS_BONUS = 70;

	/**
	 * A Predicate checking a given BoardTile is a CharacterTile
	 */
	private final static Predicate<BoardTile> IS_LETTER = CharacterTile.class::isInstance;

	/**
	 * The Player making this Move.
	 */
	private final Player player;

	/**
	 * The Board on which this Move is being played.
	 */
	private final Board board;

	/**
	 * Stores whether the Move is a pass.
	 */
	private boolean pass;

	/**
	 * The Coordinate representing the origin of the word being played with the Move.
	 */
	private Coordinate startCoordinate;

	/**
	 * The direction of the Move expressed as a character, 'r' for right, 'd' for down
	 */
	private char direction;

	/**
	 * A List containing the Tiles the Player is using for this Move.
	 */
	private List<CharacterTile> playerTiles = new LinkedList<>();

	/**
	 * The word that will be formed using this Move
	 */
	private Word word = new Word();

	/**
	 * Constructor taking a Player and a Board.
	 *
	 * @param player the Player making this Move
	 * @param board the Board on which this Move is being made
	 */
	public Move(Player player, Board board) {
		this.pass = true;
		this.player = player;
		this.board = board;
	}

	/**
	 * Checks if the Move is placable on the Board, constructing the Move's Word in the process.
	 * If there is a reason the Move is not placable, prints a message on the console saying why.
	 *
	 * @return true if the Move is placable, false otherwise
	 */
	public boolean checkPlacable() {
		// xInc and yInc use the integer value of chars 'd' and 'r' to determine how to iterate
		// across the grid.
		int xInc = (this.direction - LOWER_D_ASCII_VALUE) / DIRECTION_DIVISOR;
		int yInc = (this.direction - LOWER_R_ASCII_VALUE) / DIRECTION_DIVISOR * -1;

		// If there is a letter directly behind the origin Coordinate, return false
		if (this.board.tileAt(this.startCoordinate.getX() - xInc,
				this.startCoordinate.getY() - yInc) instanceof CharacterTile) {
			System.out.println("Please use the position of the first letter in the word as the input location.");
			return false;
		}
		// Tries to construct a Word from the Player's tiles and the Tiles on the Board
		if (!constructWord()) {
			return false;
		}

		// Check word is either the first word being placed or that it intersects with a
		// pre-existing word.
		int wordLength = getTiles().size();
		if (!(this.word.getTiles().size() > wordLength)) {
			int startX = this.startCoordinate.getX();
			int startY = this.startCoordinate.getY();
			int centre = this.board.getCentre().getX();

			if (!this.board.getStartState()) {
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

	/**
	 * Makes the Move, placing the Tiles on the Board.
	 */
	private void confirmMove() {
		LinkedList<BoardTile> wordTiles = new LinkedList<>(this.word.getTiles());
		BoardReader reader = new BoardReader(this.board, this.startCoordinate, this.direction);
		reader.conditionalNext((tile) -> !wordTiles.isEmpty(), (c) -> this.board.placeTile(c, wordTiles.poll()));
	}

	/**
	 * Attempts to construct a Word with the Player's tiles and the Tiles on the Board.
	 *
	 * @return true if the Word can be constructed, false otherwise
	 */
	private boolean constructWord() {
		resetWord();
		LinkedList<CharacterTile> tileQueue = getTiles();
		BoardReader reader = new BoardReader(this.board, this.startCoordinate, this.direction);
		BoardTile currentTile;
		// Check it's possible to place the word on the board
		do {
			// Try placing tiles in Player's rack
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
					this.word.addTile(board.tileAt(c));
				} else {
					reader.setCoordinate(new Coordinate(-2, -2));
				}
			});
			// Gather existing letter tiles from the board
			currentTile = reader.conditionalNext(IS_LETTER, (c) -> this.word.addTile(board.tileAt(c)));

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

	/**
	 * Returns the Tiles being used in the Move belonging to the Player
	 *
	 * @return a LinkedList of Tiles from the Player's rack to be used in the Move
	 */
	public LinkedList<CharacterTile> getTiles() {
		LinkedList<CharacterTile> list = new LinkedList<>();
		Collections.addAll(list, playerTiles.toArray(new CharacterTile[0]));
		return list;
	}

	/**
	 * Returns the Word this Move is attempting to form.
	 *
	 * @return the Word this move is attempting to form
	 */
	public Word getWord() {
		return this.word;
	}

	/**
	 * Returns whether the Move is a pass or not.
	 *
	 * @return true if the move is a pass, false otherwise
	 */
	public boolean isPass() {
		return pass;
	}

	/**
	 * Resets this Move's Word.
	 */
	public void resetWord() {
		this.word = new Word();
	}

	/**
	 * Sets the Coordinate, direction, and set of Tiles to be used in the Move.
	 *
	 * @param coordinate the Coordinate location of the first tile in the Move
	 * @param direction the direction the Move is being played in, 'd' or 'r'
	 * @param playerTiles the set of Tiles belonging to the Player to be used in the Move
	 */
	public void setAll(Coordinate coordinate, char direction, List<CharacterTile> playerTiles) {
		this.pass = false;
		this.startCoordinate = coordinate;
		this.playerTiles = playerTiles;
		this.direction = direction;
	}

	/**
	 * Goes through all checks on the information stored in the Move to check it's possible to
	 * make this Move. Then, if it is possible, places the Tiles on the Board and updates the
	 * Player's score. If a move is not possible, will print the reason on the console.
	 *
	 * @return true if the move is possible, false otherwise.
	 */
	public boolean tryMove() {
		if (pass) {
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

	/**
	 * Returns a String representing all the details of the Move in a human-readable format.
	 *
	 * @return a String representing this Move
	 */
	@Override
	public String toString() {
		return "The move is:	Word: "
				+ String.join("", this.playerTiles.stream().map(t->""+t.getChar()).toList())
				+ " at position "
				+ startCoordinate + ", direction: " +
				(direction == 'd' ? "down" : "right");
	}

	/**
	 * Checks if the input String has been formatted correctly and uses Tiles the Player
	 * actually has. Then sets this Move's fields accordingly.
	 *
	 * @param input the String representing the move the Player wants to make
	 * @return true if the input String is valid, false otherwise
	 */
	@Deprecated
	public boolean validateInput(String input) {
		if (input.equals(",,")) {
			this.pass = true;
			return true;
		} else
			this.pass = false;

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
		// Check the x coordinate is a letter, th y coordinate is a number,
		// the direction is either right or down, and there is at least one letter being played
		if (!Character.isLetter(x.charAt(0)) || !Character.isDigit(y.charAt(0))
				|| !Character.isDigit(y.charAt(yLength - 1))
				|| (!direction.equals("r") && !direction.equals("d"))
				|| letters.length() < 1) {
			return false;
		}
		char[] chars = letters.toCharArray();
		// Check the player has the required tiles
		ArrayList<CharacterTile> moveTiles = new ArrayList<>();
		ArrayList<CharacterTile> playerRack = new ArrayList<>(this.player.getRack());
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

	/**
	 * Updates the Player's score.
	 *
	 * @param score the score to be added to the Player's score
	 */
	private void updateScore(int score) {
		this.player.updateScore(score);
	}

}