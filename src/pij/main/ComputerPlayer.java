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

	private void parseBoard() {
		int currentX = board.getCentre();
		int currentY = currentX;
		char direction = 'r';
		BoardReader reader = new BoardReader(board, currentX, currentY, direction);
		reader.conditionalPrevious((tile) -> {return LetterTile.class.isInstance(tile);}, (x, y) -> {});
		currentX = reader.getX();
		currentX = reader.getY();
		
		for (LetterTile l : getRack()) {
			Word word = new Word();
			LinkedList<LetterTile> list = new LinkedList<>();
			list.add(l);
			if (board.constructWord(currentX, currentY, direction, list, word)) {
				Validator.lookupWord(word.toString());
			}
		}
	}
	
	private Word constructWord(int x, int y, int xInc, int yInc, LetterTile[] letters) {
		Word word = new Word();
		//board.constructWord(x, y, xInc, yInc, letters, word);
		return word;
		
	}
	
}
