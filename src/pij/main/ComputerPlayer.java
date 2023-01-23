package pij.main;
	/**
	 * The computer player
	 * 
	 * @author Roland Crompton
	 *
	 */
public class ComputerPlayer extends Player {

		public ComputerPlayer(Board board) {
			super(board);
		}

		@Override
	public Move turn(Game game) {
		// TODO Auto-generated method stub
		draw(game.bag);
		Move move = new Move(this, getBoard());
		//
		for (LetterTile lt : getRack()) {
			System.out.print(lt.getChar() + ", ");
		}
		if (game.parseBoard(move)) {
			getBoard().placeWord(move);
			return move;
		}
		return new Move(",,", this, getBoard());
	}
//	/**
//	 * Goes through every tile on the board and checks if a word can be made from it, then places the word there if valid.
//	 * 
//	 * @return true if a word is successfully placed. False if not.
//	 */
//	private boolean parseBoard() {
//		BoardReader reader = new BoardReader(board, 'r');
//		if (reader.depthFirstSearch((x, y) -> {
//			if (testWords(new LinkedList<>(getRack()), new LinkedList<LetterTile>(),
//					new BoardReader(board, x, y, reader.getDirection()))) {
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
//	private boolean testWords(LinkedList<LetterTile> rack, LinkedList<LetterTile> currentWord, BoardReader reader) {
//		if (rack.isEmpty())
//			return false;
//		BoardReader readerTwo = new BoardReader(reader);
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
//			Word word = new Word();
//			currentWord.push(l);
//
//			if (board.constructWord(readerTwo.getX(), readerTwo.getY(), readerTwo.getDirection(),
//					new LinkedList<LetterTile>(currentWord), word)) {
//				boolean inDictionary = false;
//				if (WildTile.class.isInstance(l)) {
//					WildTile w = (WildTile) l;
//					for (String[] letter : Bag.getAlphabet()) {
//						w.setTempText(letter[0].toLowerCase().charAt(0));
//						if (Validator.lookupWord(word.toString())) {
//							inDictionary = true;
//						}
//					}
//				} else
//					if (Validator.lookupWord(word.toString()))
//						inDictionary = true;
//				if (inDictionary) {
//					//
//					System.out.println(readerTwo.getX() + ", " + readerTwo.getY());
//					System.out.println(word.toString());
//					System.out.println(readerTwo.getDirection());
//					System.out.println(currentWord.size());
//					//
//					removeTiles(currentWord);
//					board.placeTiles(readerTwo.getX(), readerTwo.getY(), readerTwo.getDirection(), word.getTilesTwo());
//					return true;
//				} 
//			} 
//			else {
//				return false;
//			}
//			for (int i = 0; i < 2; i++) {
//				//System.out.print(word.toString()+ ", ");
//				LinkedList<LetterTile> newRack = new LinkedList<LetterTile>(rack);
//				newRack.remove(l);
//				if (testWords(newRack, new LinkedList<LetterTile>(currentWord), reader)) {
//					return true;
//				}
//				reader.previous();
//
//			}
//			reader.next();
//			reader.next();
//			currentWord.pop();
//		}
//		return false;
//	}

}
