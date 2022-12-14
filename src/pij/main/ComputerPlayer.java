package pij.main;

import java.util.LinkedList;

public class ComputerPlayer extends Player {
	
	private Board board;
	
	public ComputerPlayer(Board board) {
		this.board =  board;
	}
	
	@Override
	public Move turn(Bag bag) {
		// TODO Auto-generated method stub
		draw(bag);	
		Move move = new Move(",,", this);
		//
		for (LetterTile lt : getRack()) {
			System.out.print(lt.getChar() + ", ");
		}
		if (!parseBoard()) {
			System.out.println("XXXXXX");
			
			//board.placeWord(move);
			return move;
		}
		
		return new Move("A,a1,r", this);
	}
	/**
	 * Goes through every tile on the board and checks if a word can be made from it, then places the word there if valid.
	 * 
	 * @return true if a word is successfully placed. False if not.
	 */
	private boolean parseBoard() {
		BoardReader reader = new BoardReader(board, 'r');
		if (reader.depthFirstSearch((x, y) -> {
			if (testWords(new LinkedList<>(getRack()), new LinkedList<LetterTile>(),
					new BoardReader(board, x, y, reader.getDirection()))) {
				return true;
			}
			return false;
		}))
			return true;
				
		return false;
	}
	
//	public boolean testWordsInit(LinkedList<LetterTile> rack, LinkedList<LetterTile> currentWord, BoardReader reader) {
//		Word word = new Word();
//		word = testWordsTwo(rack, currentWord, reader, word);
//		if (word.getTiles().length != 0) {
//			board.placeTiles(word.getX(), word.getY(), reader.getDirection(), word.getTilesTwo());
//			return true;
//		}
//		return false;
//	}
//	
//	public Word testWordsTwo(LinkedList<LetterTile> rack, LinkedList<LetterTile> currentWord, BoardReader reader, Word highest) {
//		if (rack.isEmpty())
//			return highest;
//		
//		for (LetterTile l : rack) {
//			
//			Word word = new Word();
//			currentWord.push(l);
//			
//			if (board.constructWord(reader.getX(), reader.getY(), reader.getDirection(), new LinkedList<LetterTile>(currentWord), word)) {
//				if (Validator.lookupWord(word.toString())) {
//					//
//					System.out.println(reader.getX() + ", " + reader.getY());
//					System.out.println(word.toString());
//					System.out.println(reader.getDirection());
//					System.out.println(currentWord.size());
//					//
////					removeTiles(currentWord.toArray(new LetterTile[0]));
////					board.placeTiles(reader.getX(), reader.getY(), reader.getDirection(), word.getTilesTwo());
//					if (word.getScore() > highest.getScore())
//						highest = word;
//				}
//			}
//			LinkedList<LetterTile> newRack = new LinkedList<LetterTile>(rack);
//			newRack.remove(l);
//			word = testWordsTwo(newRack, currentWord, reader, highest);
//			if (word.getScore() > highest.getScore()) {
//				highest = word;
//			}
//			reader.previous();
//			word = testWordsTwo(newRack, currentWord, reader, highest);
//			if (word.getScore() > highest.getScore()) {
//				highest = word;
//			}
//			reader.next();
//
//			currentWord.pop();
//		}
//		
//		return highest;
//	}
	
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
	private boolean testWords(LinkedList<LetterTile> rack, LinkedList<LetterTile> currentWord, BoardReader reader) {
		//System.out.print(reader.getX() + ", " + reader.getY());
		BoardReader readerTwo = new BoardReader(board, reader.getX(), reader.getY(), reader.getDirection());
		if (rack.isEmpty())
			return false;
		
		for (LetterTile l : rack) {
			
			Word word = new Word();
			currentWord.push(l);
			if ((!LetterTile.class.isInstance(readerTwo.previous()))) {
				readerTwo.next();
			}
			else {
				readerTwo.conditionalPrevious((tile) -> {return LetterTile.class.isInstance(tile);}, (x, y) -> {});
				readerTwo.next();
			}
			if (board.constructWord(readerTwo.getX(), readerTwo.getY(), readerTwo.getDirection(), new LinkedList<LetterTile>(currentWord), word)) {
				if (Validator.lookupWord(word.toString())) {
					//
					System.out.println(readerTwo.getX() + ", " + readerTwo.getY());
					System.out.println(word.toString());
					System.out.println(readerTwo.getDirection());
					System.out.println(currentWord.size());
					//
					removeTiles(currentWord.toArray(new LetterTile[0]));
					board.placeTiles(readerTwo.getX(), readerTwo.getY(), readerTwo.getDirection(), word.getTilesTwo());
					return true;
				} 
			} 
			else {
				return false;
			}
			for (int i = 0; i < 2; i++) {
				//System.out.print(word.toString()+ ", ");
				LinkedList<LetterTile> newRack = new LinkedList<LetterTile>(rack);
				newRack.remove(l);
				if (testWords(newRack, new LinkedList<LetterTile>(currentWord), reader)) {
					return true;
				}
				//System.out.print(":");
				reader.previous();

			}
			reader.next();
			reader.next();
			currentWord.pop();
		}
		//System.out.print(";");

		//System.out.println();

		return false;
	}
	
}
