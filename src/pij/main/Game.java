package pij.main;

import java.util.LinkedList;

public class Game {
	
	int passes = 0;
	Player activePlayer;
	HumanPlayer human;
	ComputerPlayer computer;
	Board board;
	Bag bag;
	
	
	public void run() {
		boolean go = true;
		String input = "";
		bag = new Bag();
		Validator.loadDictionary();
		
		System.out.println("Welcome to ScraBBKle!");
		while (go) {
			System.out.println("Would you like to _l_oad a board or use the _d_efault board?");
			System.out.print("Please enter your choice (l/d): ");
			input = System.console().readLine();
			if (!Validator.inputValidation(input, new String[]{"d", "l"})) {
				System.out.println("That is not a valid input.");
				System.out.println();
				continue;
			}
			go = false;
			System.out.println();
		}
		String file = "";
		board = null;
		
		do {
			if (input.equals("l")) {
				System.out.print("Please enter the file name of the board: ");
				file = System.console().readLine();
			} else {
				file = "defaultBoard.txt";
			}
			file = "../resources/" + file;
			board = Validator.loadFile(file);
		} while (board == null);
		
		human = new HumanPlayer();
		computer = new ComputerPlayer();
		Player[] players = new Player[] {human, computer};
		int currentPlayer = 0;
		go = true;
		while (go) {
			activePlayer = players[currentPlayer];
			board.print();
			Move move;
			do {
				move = activePlayer.turn(this);
			} while (!board.placeWord(move));
			
			activePlayer.removeTiles(move.getTiles());
			
			System.out.println();
			if (move.isPass()) {
				passes++;
				System.out.println("Player passed their turn.");
			} else {
				passes = 0;
//				String direction = "right";	
//				if (move.getDirection() == 'd')
//					direction = "down";
//				 
//				System.out.println("The move is:	Word: " + move.getTiles().toString() + " at position "
//						+ (char) (move.getX() + 97) + (move.getY() + 1) + ", direction: " + direction);
				System.out.println(move.toString());
				System.out.println();
				System.out.println("The result is: ");
				
			}
			
			System.out.println("Human player score:	" + human.getScore());
			System.out.println("Computer player score: " + computer.getScore());
			System.out.println();
			
			if ((passes >= 4) || (bag.isEmpty() && (human.getRack().isEmpty() || computer.getRack().isEmpty())))
				go = false;
			
			currentPlayer = Math.abs(currentPlayer - 1);
		}			
	}
	
	/**
	 * Goes through every tile on the board and checks if a word can be made from it, then places the word there if valid.
	 * 
	 * @return true if a word is successfully placed. False if not.
	 */
	public boolean parseBoard(Move move) {
		BoardReader reader = new BoardReader(board, 'r');
		if (reader.depthFirstSearch((x, y) -> {
			if (testWords(new LinkedList<>(activePlayer.getRack()), new LinkedList<LetterTile>(),
					new BoardReader(board, x, y, reader.getDirection()), move)) {
				return true;
			}
			return false;
		}))
			return true;
				
		return false;
	}
	
	/**
	 * Attempts to create a word at the target location using every combination of the tiles in the input rack.
	 * Step 1: Check rack is empty. If so, return false.
	 * Step 2: For each letter in the rack:
	 * 	-add that letter to the currentWord
	 * 	-see if that word can be placed on the board, and construct a Word object from it. If the word cannot be placed, stop looking in that direction.
	 * 	-see if that word is in the dictionary, if so, place it on the board and return true
	 * 	-if not in the dictionary, create a new rack that is the same is the old rack except the letter in question
	 * 	-call testWords again	
	 * 	-move the reader backwards
	 * 	-call testWords again
	 * 	-if that returns false, move the reader forwards
	 * 	-remove the letter in question from the currentWord
	 * Step 3: return false
	 * 	
	 * @param rack
	 * @param currentWord
	 * @param reader
	 * @return
	 */
	private boolean testWords(LinkedList<LetterTile> rack, LinkedList<LetterTile> currentWord, BoardReader reader, Move move) {
		if (rack.isEmpty())
			return false;
		BoardReader readerTwo = new BoardReader(reader);
		if ((!LetterTile.class.isInstance(readerTwo.previous()))) {
			readerTwo.next();
		} else {
			readerTwo.conditionalPrevious((tile) -> {return LetterTile.class.isInstance(tile);}, (x, y) -> {});
			readerTwo.next();
		}

		for (LetterTile l : rack) {
			if (WildTile.class.isInstance(l)) {
				WildTile w = (WildTile) l;
				for (String[] letter : Bag.getAlphabet()) {
					w.setTempText(letter[0].charAt(0));
				}
			}
			Word word = new Word();
			currentWord.push(l);

			if (board.constructWord(readerTwo.getX(), readerTwo.getY(), readerTwo.getDirection(),
					new LinkedList<LetterTile>(currentWord), word)) {
				boolean inDictionary = false;
				if (WildTile.class.isInstance(l)) {
					WildTile w = (WildTile) l;
					for (String[] letter : Bag.getAlphabet()) {
						w.setTempText(letter[0].toLowerCase().charAt(0));
						if (Validator.lookupWord(word.toString())) {
							inDictionary = true;
							break;
						}
					}
				} else
					if (Validator.lookupWord(word.toString()))
						inDictionary = true;
				if (inDictionary) {
					//activePlayer.removeTiles(currentWord);
					move.setAll(readerTwo.getX(), readerTwo.getY(), readerTwo.getDirection(), currentWord.toArray(new LetterTile[0]));
					return true;
				} 
			} 
			else {
				return false;
			}
			for (int i = 0; i < 2; i++) {
				LinkedList<LetterTile> newRack = new LinkedList<LetterTile>(rack);
				newRack.remove(l);
				if (testWords(newRack, new LinkedList<LetterTile>(currentWord), reader, move)) {
					return true;
				}
				reader.previous();
			}
			reader.next();
			reader.next();
			currentWord.pop();
		}
		return false;
	}
}
