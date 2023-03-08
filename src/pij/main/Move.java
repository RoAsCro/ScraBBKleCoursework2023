package pij.main;

import java.util.*;
import java.util.function.Predicate;

import static pij.main.Player.RACK_SIZE;


public class Move {

	public static final int LOWER_A_CHAR_INT = 97;
	public static final int LOWER_Z_CHAR_INT = 122;
	private static final int ALL_LETTERS_BONUS = 70;

	private String input;

	private boolean pass;
	
	private char direction;
	
	private List<LetterTile> tiles = new LinkedList<>();

	private Coordinate startCoordinate;
	
	private final Player PLAYER;

	private final Board BOARD;

	private Word word = new Word();

	private Predicate<Tile> isLetter = LetterTile.class::isInstance;
	
	public Move(Player player, Board board) {
		pass = false;
		this.PLAYER = player;
		this.BOARD = board;
	}


	public Word getWord() {
		return word;
	}

	public boolean tryMove() {
		if (this.input == null)
			return false;
		return tryMove(this.input);
	}

	public boolean tryMove(String input) {
		if (!validateInput(input)) {
			//System.out.println(input);
			System.out.println("That is not a valid move.");
			return false;
		}
		if (pass) {
			//System.out.println("Pass");
			return true;
		}
		if (!checkPlacable()) {
			System.out.println("Word cannot be placed there.");
			return false;
		}
		if (!lookupWord()) {
			System.out.println("Word not in dictionary.");
			return false;
		}

		updateScore(this.word.getScore());
		confirmMove();
		return true;
	}

	public boolean lookupWord(){
		LetterTile letterTile;
		List<LetterTile> wordTiles = this.word.getTiles();
		if ((letterTile = wordTiles.stream().filter(t->!Character.isLetter(t.getChar())).findFirst().orElse(null)) != null) {
			int  index = wordTiles.indexOf(letterTile);
			LetterTile original = wordTiles.get(index);
			for (int i = LOWER_A_CHAR_INT; i <= LOWER_Z_CHAR_INT; i++) {
				wordTiles.set(index, new LetterTile("" + ((char) i), letterTile.getValue()));
				if (lookupWord()) {
					return true;
				} else
					wordTiles.set(index, original);
			}
		} else return Dictionary.lookupWord(this.word.toString());
		return false;
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
		ArrayList<LetterTile> moveTiles = new ArrayList<>();
		ArrayList<LetterTile> playerRack = new ArrayList<>(this.PLAYER.getRack());
		for (char c : chars) {
			LetterTile letterTile = playerRack.stream().filter(t->t.matchChar(c)).findFirst().orElse(null);
			if (letterTile == null) {
				return false;
			}
			playerRack.remove(letterTile);
			moveTiles.add(new LetterTile(""+c, letterTile.getValue()));
		}

		setAll(new Coordinate(x.charAt(0), Integer.parseInt(y)), direction.charAt(0), moveTiles);
		this.input = input;
		return true;
	}

	private void confirmMove() {
		LinkedList<LetterTile> wordTiles = this.word.getTiles();
		BoardReader reader = new BoardReader(this.BOARD, this.startCoordinate, this.direction);
		reader.conditionalNext((tile) -> !wordTiles.isEmpty(), (c) -> this.BOARD.placeTile(c, wordTiles.poll()));
	}

	public void setAll(Coordinate coordinate, char direction, List<LetterTile> tiles) {
		pass = false;
		this.startCoordinate = coordinate;
		this.tiles = tiles;
		this.direction = direction;
	}
	
	public LinkedList<LetterTile> getTiles() {
		LinkedList<LetterTile> list = new LinkedList<>();
		Collections.addAll(list, tiles.toArray(new LetterTile[0]));
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
	
	public void updateScore(Double score) {
		this.PLAYER.updateScore(score);
	}

	public boolean checkPlacable() {
		// xInc and yInc use the integer value of chars 'd' and 'r' to determine how to iterate
		// across the grid.
		int xInc = (this.direction - 100) / 14;
		int yInc = (this.direction - 114) / 14 * -1;
		int wordLength = getTiles().size();

		//If there is a letter directly behind the one specified in the move, return false
		if (this.BOARD.tileAt(this.startCoordinate.getX() - xInc, this.startCoordinate.getY() - yInc) instanceof LetterTile) {
			System.out.println("Please use the position of the first letter in the word as the input location.");
			return false;
		}
		if (!constructWord()) {
			return false;
		}

		// Check word is either the first word being placed OR that it intersects with a
		// pre-existing word.
		// THIS MUST BE THE LAST CHECK because startState is turned off by all the
		// conditionals below evaluating to false.
		if (!(this.word.getTiles().size() > wordLength)) {
			int startX = this.startCoordinate.getX();
			int startY = this.startCoordinate.getY();
			int centre = this.BOARD.getCentre().getX();

			if (!this.BOARD.getStartState()) {
				System.out.println("Your word must cross another word");
				return false;
			} else if (!(startX + xInc * (wordLength - 1) >= centre && startX <= centre
					&& startY + yInc * (wordLength - 1) >= centre && startY <= centre))
//					((startY == centre && startY + yInc == centre && (centre <= startX + wordLength - 1 && centre >= startX))
//					|| (startX == centre && startX + xInc == centre && (centre <= startY + wordLength - 1 && centre >= startY))))
			{
				System.out.println("Your word must cross over the centre tile.");
				return false;
			}
		}
		return true;
	}

	private boolean constructWord() {
		resetWord();
		LinkedList<LetterTile> tileQueue = getTiles();
		BoardReader reader = new BoardReader(this.BOARD, this.startCoordinate, this.direction);
		Tile currentTile;
		// Check it's possible to place the word on the board
		do {
			// Try placing tiles in rack
			reader.conditionalNext((tile) -> (!(isLetter.test(tile)) && !tileQueue.isEmpty()), (c) -> {
				// Test there are no words parallel to this one
				reader.turn();
				boolean parallelWord = isLetter.test(reader.next());
				reader.previous();
				if (isLetter.test(reader.previous())) {
					parallelWord = true;
				}
				if (!parallelWord) {
					reader.next();
					reader.turn();
					this.word.addLetter(tileQueue.poll());
					this.word.addLetter(BOARD.tileAt(c));
				} else {
					reader.set(new Coordinate(-2, -2));
				}
			});
			// Gather existing letter tiles from the board
			currentTile = reader.conditionalNext(isLetter, (c) -> this.word.addLetter(BOARD.tileAt(c)));

		} while (!tileQueue.isEmpty() && currentTile != null);

		// Check all letters have been placed
		if ((!tileQueue.isEmpty() && currentTile == null)) {
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