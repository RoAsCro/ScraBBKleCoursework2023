package pij.main;

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
		
	}
	
	private Word constructWord(int x, int y, int xInc, int yInc, LetterTile[] letters) {
		Word word = new Word();
		board.constructWord(x, y, xInc, yInc, letters, word);
		return word;
		
	}
	
}
