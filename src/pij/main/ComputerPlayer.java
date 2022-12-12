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
		reader.conditionalPrevious((tile) -> {return LetterTile.class.isInstance(tile);}, (x, y) -> {});
		currentX = reader.getX();
		currentX = reader.getY();
		
		testWords(new LinkedList<>(getRack()), new LinkedList<LetterTile>(), reader);
		
		return false;
	}
	
	private boolean testWords(LinkedList<LetterTile> rack, LinkedList<LetterTile> currentWord, BoardReader reader) {
		if (rack.isEmpty())
			return false;
		
		System.out.println(currentWord.size());
		System.out.println(rack.size());
		
		for (LetterTile l : rack) {
			
			Word word = new Word();
			currentWord.push(l);
			if (board.constructWord(reader.getX(), reader.getY(), reader.getDirection(), new LinkedList<LetterTile>(currentWord), word)) {
				if (Validator.lookupWord(word.toString())) {
					System.out.println(reader.getX() + ", " + reader.getY());
					System.out.println(word.toString());
					
					board.placeTiles(reader.getX(), reader.getY(), 'r', currentWord);
					return true;
				}
			}
			LinkedList<LetterTile> listThree = new LinkedList<LetterTile>(rack);
			listThree.remove(l);
			reader.previous();
			if (testWords(listThree, currentWord, reader)) {
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
