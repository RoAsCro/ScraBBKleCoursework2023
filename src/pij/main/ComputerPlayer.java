package pij.main;

import java.util.LinkedList;

/**
	 * The computer player
	 * 
	 * @author Roland Crompton
	 *
	 */
public class ComputerPlayer extends Player {
		private LinkedList<Move> moves = new LinkedList<>();
		public ComputerPlayer(Board board) {
			super(board);
		}

		@Override
	public Move turn(Game game) {
		draw(game.bag);

		for (LetterTile lt : getRack()) {
			System.out.print(lt.getChar() + ", ");
		}

		parseBoardTwo();
		Move bestMove = new Move(this, getBoard());
		bestMove.validateInput(",,");
		int i = 0;
		for (Move m : moves) {
			i++;

			if (m.getWord().getScore() > bestMove.getWord().getScore()) {
				bestMove = m;
			}
		}
		System.out.println(bestMove.getWord());
		bestMove.tryMove();

		//		if (parseBoard(move)) {
		//			getBoard().placeWord(move);
		//			return move;
		//		}

		return bestMove;
	}

	/**
	 * Goes through every tile on the board and checks if a word can be made from it, then places the word there if valid.
	 *
	 * @return true if a word is successfully placed. False if not.
	 */
	public boolean parseBoard(Move move) {
		BoardReader reader = new BoardReader(getBoard(), 'r');
		if (reader.depthFirstSearch((x, y) -> {
			if (testWords(new LinkedList<>(getRack()), new LinkedList<LetterTile>(),
					new BoardReader(getBoard(), x, y, reader.getDirection()), move)) {
				return true;
			}
			return false;
		}))
			return true;

		return false;
	}

	public void parseBoardTwo() {
		Move moveTwo = new Move(this, getBoard());
		BoardReader reader = new BoardReader(getBoard(), 'r');
		reader.depthFirstSearch((x, y) -> {
			testWordsTwo(new LinkedList<>(getRack()), new LinkedList<LetterTile>(),
					new BoardReader(getBoard(), x, y, reader.getDirection()));
			return false;
		});
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

		//If the reader is currently in the middle of a word on the board, it will reverse until it reaches the beginning of the word
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
			//////////////
//			StringBuilder builder = new StringBuilder();
//			String wordString = currentWord.toString();
//			wordString = wordString.replace("[", "");
//			wordString = wordString.replace(",", "");
//
//			builder.append(wordString);
//			builder.append(",");
//			builder.append((char)(readerTwo.getX() + 97));
//			builder.append((readerTwo.getY() + 1));
//			builder.append(",");
//			builder.append(readerTwo.getDirection());
//
//			if (move.validateInput(builder.toString())){
//
//			}
//				return true;
			//////////////

			//Attempt to make a word with at the current location with the current tiles
			//If the word doesn't meet the criteria for word placement, return false
			//If the word does meet the criteria, but is not in the dictionary, continue the loop.
			if (getBoard().constructWord(readerTwo.getX(), readerTwo.getY(), readerTwo.getDirection(),
					new LinkedList<LetterTile>(currentWord), word)) {


				boolean inDictionary = false;
				//Unless I'm mistaken, the current implementation will not allow for multiple WildTile's in a word
				if (WildTile.class.isInstance(l)) {
					WildTile w = (WildTile) l;
					for (String[] letter : Bag.getAlphabet()) {
						w.setTempText(letter[0].toLowerCase().charAt(0));
						if (Validator.lookupWord(word.toString())) {
							inDictionary = true;
							break;
						} else
							w.setTempText(' ');
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
			//Tries placing new tiles in front of the currently constructed word, then behind
			for (int i = 0; i < 2; i++) {
				LinkedList<LetterTile> newRack = new LinkedList<LetterTile>(rack);
				newRack.remove(l);
				if (testWords(newRack, new LinkedList<LetterTile>(currentWord), reader, move)) {
					return true;
				}
				reader.previous();
			}
			//The first next() undoes the previous() above, the pop() removes l added at the start of the loop.
			reader.next();
			reader.next();
			currentWord.pop();
		}
		return false;
	}


	private void testWordsTwo(LinkedList<LetterTile> rack, LinkedList<LetterTile> currentWord, BoardReader reader) {
		//System.out.println(move.getWord().getScore());

		if (rack.isEmpty())
			return;
		BoardReader readerTwo = new BoardReader(reader);

		//If the reader is currently in the middle of a word on the board, it will reverse until it reaches the beginning of the word
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
			Move newMove = new Move(this, getBoard());
			currentWord.push(l);
			//////////////
			StringBuilder builder = new StringBuilder();
			for (LetterTile lt : currentWord) {
				builder.append(lt.getChar());
			}
			builder.append(",");
			builder.append((char)(readerTwo.getX() + 97));
			builder.append((readerTwo.getY() + 1));
			builder.append(",");
			builder.append(readerTwo.getDirection());
			//System.out.println("S: " + builder);

			if (newMove.validateInput(builder.toString()) && newMove.checkPlacable()){

				if (Validator.lookupWord(newMove.getWord().toString())) {
					System.out.println("Found");
					System.out.println(newMove.getWord().toString());
					System.out.println(newMove.getTiles());
					moves.add(newMove);
					System.out.println(moves.size());
				}

			} else {
				//If move is not placable, stop searching here
				return;
			}

			//////////////

			//Tries placing new tiles in front of the currently constructed word, then behind
			for (int i = 0; i < 2; i++) {
				//System.out.println("....");
				LinkedList<LetterTile> newRack = new LinkedList<LetterTile>(rack);
				newRack.remove(l);
				testWordsTwo(newRack, new LinkedList<LetterTile>(currentWord), reader);
				reader.previous();
			}
			//The first next() undoes the previous() above, the pop() removes l added at the start of the loop.
			reader.next();
			reader.next();
			currentWord.pop();
		}
	}

}
