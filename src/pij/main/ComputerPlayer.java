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
		return move;
	}

	private boolean parseBoard() {
		int currentX = board.getCentre();
		int currentY = currentX;
		char direction = 'r';
		BoardReader reader = new BoardReader(board, currentX, currentY, direction);
		reader.conditionalPrevious((tile) -> {return LetterTile.class.isInstance(tile);}, (x, y) -> {});
		currentX = reader.getX();
		currentX = reader.getY();
		
		
		testWords(new LinkedList<>(getRack()), new LinkedList<LetterTile>(), reader);
		
//		for (LetterTile l : getRack()) {
//			Word word = new Word();
//			LinkedList<LetterTile> list = new LinkedList<>();
//			list.add(l);
//			if (board.constructWord(currentX, currentY, direction, list, word)) {
//				if (Validator.lookupWord(word.toString())) {
//					return true;
//				}
//			}
//		}
		return false;
	}
	
	private boolean testWords(LinkedList<LetterTile> rack, LinkedList<LetterTile> currentWord, BoardReader reader) {
		//LinkedList<LetterTile> listThree = (LinkedList<LetterTile>) list.clone();
//		if (list.isEmpty())
//			return false;
//		
		reader.previous();
		
		for (LetterTile l : rack) {
			Word word = new Word();
			currentWord.push(l);
			if (board.constructWord(reader.getX(), reader.getY(), reader.getDirection(), currentWord, word)) {
				if (Validator.lookupWord(word.toString())) {
					System.out.println(word.toString());
					return true;
				}
			}
			LinkedList<LetterTile> listThree = (LinkedList<LetterTile>) rack.clone();
			listThree.remove(l);
			if (testWords(listThree, currentWord, reader)) {
				return true;
			}
			currentWord.pop();
		}
		reader.next();
		return false;
	}
	
	private Word constructWord(int x, int y, int xInc, int yInc, LetterTile[] letters) {
		Word word = new Word();
		//board.constructWord(x, y, xInc, yInc, letters, word);
		return word;
		
	}
	
}
