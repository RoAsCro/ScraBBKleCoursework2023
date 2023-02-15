package pij.main;

import java.util.LinkedList;

public class Game {
	
	int passes = 0;
	Player activePlayer;
	Board board;
	Bag bag;
	LinkedList<Player> players;

	public Game(LinkedList<Player> players, Board board){
		this.players = players;
		this.board = board;
	}

	
	public void run() {
		boolean go = true;
//		String input = "";
		bag = new Bag();
		Validator.loadDictionary();

		for (Player p : this.players) {
			p.draw(bag);
		}

		go = true;
		while (go) {
			activePlayer = players.poll();
			players.add(activePlayer);
			//board.print();
			Move move;
			do {
				move = activePlayer.turn(this.bag);
			} while (board.getStartState());

			activePlayer.removeTiles(move.getTiles());
			
			System.out.println();
			if (move.isPass()) {
				passes++;
				System.out.println("Player passed their turn.");
			} else {
				passes = 0;
				System.out.println();
				System.out.println("The result is: ");
			}
			boolean emptyRack = false;
			for (Player p : players) {
				System.out.println(p.getScore());
				if (p.getRack().isEmpty())
					emptyRack = true;
			}
			
			if ((passes >= 4) || (bag.isEmpty() && emptyRack))
				go = false;
		}			
	}
	
//	/**
//	 * Goes through every tile on the board and checks if a word can be made from it, then places the word there if valid.
//	 *
//	 * @return true if a word is successfully placed. False if not.
//	 */
//	public boolean parseBoard(Move move) {
//		BoardReader reader = new BoardReader(board, 'r');
//		if (reader.depthFirstSearch((x, y) -> {
//			if (testWords(new LinkedList<>(activePlayer.getRack()), new LinkedList<LetterTile>(),
//					new BoardReader(board, x, y, reader.getDirection()), move)) {
//				return true;
//			}
//			return false;
//		}))
//			return true;
//
//		return false;
//	}
//
//	/**
//	 * Attempts to create a word at the target location using every combination of the tiles in the input rack.
//	 * Step 1: Check rack is empty. If so, return false.
//	 * Step 2: For each letter in the rack:
//	 * 	-add that letter to the currentWord
//	 * 	-see if that word can be placed on the board, and construct a Word object from it. If the word cannot be placed, stop looking in that direction.
//	 * 	-see if that word is in the dictionary, if so, place it on the board and return true
//	 * 	-if not in the dictionary, create a new rack that is the same is the old rack except the letter in question
//	 * 	-call testWords again
//	 * 	-move the reader backwards
//	 * 	-call testWords again
//	 * 	-if that returns false, move the reader forwards
//	 * 	-remove the letter in question from the currentWord
//	 * Step 3: return false
//	 *
//	 * @param rack
//	 * @param currentWord
//	 * @param reader
//	 * @return
//	 */
//	private boolean testWords(LinkedList<LetterTile> rack, LinkedList<LetterTile> currentWord, BoardReader reader, Move move) {
//		if (rack.isEmpty())
//			return false;
//		BoardReader readerTwo = new BoardReader(reader);
//
//		//If the reader is currently in the middle of a word on the board, it will reverse until it reaches the beginning of the word
//		if ((!LetterTile.class.isInstance(readerTwo.previous()))) {
//			readerTwo.next();
//		} else {
//			readerTwo.conditionalPrevious((tile) -> {return LetterTile.class.isInstance(tile);}, (x, y) -> {});
//			readerTwo.next();
//		}
//
//		for (LetterTile l : rack) {
//			if (WildTile.class.isInstance(l)) {
//				WildTile w = (WildTile) l;
//				for (String[] letter : Bag.getAlphabet()) {
//					w.setTempText(letter[0].charAt(0));
//				}
//			}
//
//			Word word = new Word();
//			currentWord.push(l);
//			//Make a word with at the current location with the current tiles
//			if (board.constructWord(readerTwo.getX(), readerTwo.getY(), readerTwo.getDirection(),
//					new LinkedList<LetterTile>(currentWord), word)) {
//				boolean inDictionary = false;
//				//Unless I'm mistaken, the current implementation will not allow for multiple WildTile's in a word
//				if (WildTile.class.isInstance(l)) {
//					WildTile w = (WildTile) l;
//					for (String[] letter : Bag.getAlphabet()) {
//						w.setTempText(letter[0].toLowerCase().charAt(0));
//						if (Validator.lookupWord(word.toString())) {
//							inDictionary = true;
//							break;
//						} else
//							w.setTempText(' ');
//					}
//				} else
//					if (Validator.lookupWord(word.toString()))
//						inDictionary = true;
//				if (inDictionary) {
//					//activePlayer.removeTiles(currentWord);
//					move.setAll(readerTwo.getX(), readerTwo.getY(), readerTwo.getDirection(), currentWord.toArray(new LetterTile[0]));
//					return true;
//				}
//			}
//			else {
//				return false;
//			}
//			for (int i = 0; i < 2; i++) {
//				LinkedList<LetterTile> newRack = new LinkedList<LetterTile>(rack);
//				newRack.remove(l);
//				if (testWords(newRack, new LinkedList<LetterTile>(currentWord), reader, move)) {
//					return true;
//				}
//				reader.previous();
//			}
//			reader.next();
//			reader.next();
//			currentWord.pop();
//		}
//		return false;
//	}
}
