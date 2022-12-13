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
		parseBoard();
		Move move = new Move(",,", this);
		return move;
	}

	private boolean parseBoard() {
		int currentX = board.getCentre();
		int currentY = currentX;
		char direction = 'r';
		BoardReader reader = new BoardReader(board, currentX, currentY, direction);
		reader.depthFirstSearch((x, y) -> {
			if (testWords(new LinkedList<>(getRack()), new LinkedList<LetterTile>(),
					new BoardReader(board, x, y, reader.getDirection()))) {
				return true;
			}
			return false;
		});
		
//		testWords(new LinkedList<>(getRack()), new LinkedList<LetterTile>(), reader);
		
		return false;
	}
	
	/**
	 * Attempts to create a word at the target location using every combination of the tiles in the input rack.
	 * Step 1: Check rack is empty. If so, return false.
	 * Step 2: For each letter in the rack:
	 * 	-add that letter to the currentWord
	 * 	-see if that word can be placed on the board, and construct a Word object from it
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
		if (rack.isEmpty())
			return false;
		
		for (LetterTile l : rack) {
			
			Word word = new Word();
			currentWord.push(l);
			
			if (board.constructWord(reader.getX(), reader.getY(), reader.getDirection(), new LinkedList<LetterTile>(currentWord), word)) {
				if (Validator.lookupWord(word.toString())) {
					//
					System.out.println(reader.getX() + ", " + reader.getY());
					System.out.println(word.toString());
					System.out.println(reader.getDirection());
					System.out.println(currentWord.size());
					//
					board.placeTiles(reader.getX(), reader.getY(), reader.getDirection(), currentWord);
					return true;
				}
			}
			LinkedList<LetterTile> newRack = new LinkedList<LetterTile>(rack);
			newRack.remove(l);
			if (testWords(newRack, currentWord, reader)) {
				return true;
			}
			reader.previous();
			if (testWords(newRack, currentWord, reader)) {
				return true;
			}
			reader.next();

			currentWord.pop();
		}
		
		return false;
	}
	
	private Word constructWord(int x, int y, int xInc, int yInc, LetterTile[] letters) {
		Word word = new Word();
		//board.constructWord(x, y, xInc, yInc, letters, word);
		return word;
		
	}
	
}
