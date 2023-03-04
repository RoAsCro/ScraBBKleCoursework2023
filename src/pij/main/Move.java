package pij.main;

import java.util.*;
import java.util.function.Predicate;


public class Move {

	private static final int RACK_SIZE = 7;

	private static final int ALL_LETTERS_BONUS = 70;

	private String input;

	private boolean valid = true;
	
	private boolean pass;
	
	private char direction;
	
	private LetterTile[] tiles = new LetterTile[0];
	
	private int x;
	
	private int y;
	
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
			System.out.println("Word cannot be places there.");

			return false;
		}
		if (!Validator.lookupWord(this.word.toString())) {
			System.out.println("Word not in dictionary.");
			return false;
		}

		updateScore(this.word.getScore());
		confirmMove();
		return true;
	}

	public boolean validateInput(String input) {
		//System.out.println("------------------------------");
		String x = "0";
		String y = "0";
		String direction = "d";
		ArrayList<LetterTile> tiles = new ArrayList<>();

		if (input.equals(",,"))
			pass = true;
		else {
			pass = false;

			String[] movesToTest = input.split(",");

			ArrayList<LetterTile> rack = new ArrayList<>(this.PLAYER.getRack());

			if (movesToTest.length == 3) {

				String letters = movesToTest[0];
				String location = movesToTest[1];
				direction = movesToTest[2].toLowerCase();

				if (location.length() < 2 || location.length() > 3)
					valid = false;
				else {
					System.out.println("length");
					x = location.substring(0,1);
					y = location.substring(1);

					int yLength = y.length();
					//Check the x coordinate is a letter
					if (!Character.isLetter(x.charAt(0)) || !Character.isDigit(y.charAt(0))
							|| !Character.isDigit(y.charAt(yLength - 1))
							|| !Validator.inputValidation(direction, new String[] { "r", "d" })
							|| letters.length() < 1) {
						System.out.println("direction");
						valid = false;
					}
					else {
						char[] chars = letters.toCharArray();
						int counter = 0;
						int target = chars.length;

						//Check the player has the required tiles
						for (char c : chars) {
							for (LetterTile t : rack) {
								char tChar = t.getChar();
								if (tChar == c
										|| (Character.isLowerCase(c) && t instanceof WildTile)) {
									tiles.add(t);
									rack.remove(t);
									counter++;
									if (t instanceof WildTile w) {
										w.setTempText(c);
									}
									break;
								}
							}
						}
						if (counter != target) {
							System.out.println("counter");
							valid = false;
						}
					}
				}
			}else {
				System.out.println("Here");
				valid = false;
			}
		}
		if (pass) {
			System.out.println("Pass");
			return true;
		}
		if (!this.valid) {
			System.out.println("Invalid");
			return false;
		} else {
			setAll(x.charAt(0) - 97, Integer.parseInt(y) - 1, direction.charAt(0), tiles.toArray(new LetterTile[0]));
			this.input = input;
			return true;
		}
	}

	private void confirmMove() {
		LinkedList<LetterTile> tiles = this.word.getTilesTwo();
		BoardReader reader = new BoardReader(this.BOARD, this.x, this.y, this.direction);
		reader.conditionalNext((tile) -> !tiles.isEmpty(), (x, y) -> this.BOARD.placeTile(new ScraBBKleCoordinate(x, y), tiles.poll()));


		//this.BOARD.placeTiles(this.x, this.y, this.direction, this.word.getTilesTwo());
	}

	public void setAll(int x, int y, char direction, LetterTile[] tiles) {
		pass = false;
		this.x = x;
		this.y = y;
		this.tiles = tiles;
		this.direction = direction;
	}
//
//	public boolean isValid() {
//		return valid;
//	}
//
//
//	public int getX() {
//		return x;
//	}
//
//
//	public int getY() {
//		return y;
//	}
	
	public LinkedList<LetterTile> getTiles() {
		LinkedList<LetterTile> list = new LinkedList<>();
		Collections.addAll(list, tiles);
		return list;
	}

//
//	public char getDirection() {
//		return direction;
//	}
	
	public boolean isPass() {
		return pass;
	}


	
	@ Override
	public String toString() {
		return "The move is:	Word: " + Arrays.toString(tiles) + " at position "
				+ (char) (x + 97) + (y + 1) + ", direction: " +
				(direction == 'd' ? "Down" : "Right");
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
		if (this.BOARD.tileAt(this.x - xInc, this.y - yInc) instanceof LetterTile) {
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
		if (!(this.word.getTiles().length > wordLength)) {
			int centre = this.BOARD.getCentre();
			if (!this.BOARD.getStartState()) {
				System.out.println("Your word must cross another word");
				return false;
			} else if (!((y == centre && y + yInc == centre && (centre <= x + wordLength - 1 && centre >= x))
					|| (x == centre && x + xInc == centre && (centre <= y + wordLength - 1 && centre >= y)))) {
				System.out.println("Your word must cross over the centre tile.");
				return false;
			}
		}
		return true;
	}

	private boolean constructWord() {
		resetWord();
		LinkedList<LetterTile> tileQueue = new LinkedList<>(getTiles());
		BoardReader reader = new BoardReader(this.BOARD, this.x, this.y, this.direction);
		Tile currentTile;
		// Check it's possible to place the word on the board
		do {
			// Try placing tiles in rack
			reader.conditionalNext((tile) -> (!(tile instanceof LetterTile) && !tileQueue.isEmpty()), (x, y) -> {
				// Test there are no words parallel to this one
				reader.turn();
				if (isLetter.test(reader.next())) {
					reader.set(-2, -2);
				} else {
					reader.previous();
					if (isLetter.test(reader.previous())) {
						reader.set(-2, -2);
					} else {
						reader.next();
						reader.turn();
						this.word.addLetter(tileQueue.poll());
						this.word.addLetter(BOARD.tileAt(x, y));
					}
				}
			});
			// Gather existing letter tiles from the board
			currentTile = reader.conditionalNext(isLetter, (x, y) -> this.word.addLetter(BOARD.tileAt(x, y)));

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