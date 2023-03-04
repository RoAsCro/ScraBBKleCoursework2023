package pij.main;

import java.util.LinkedList;
import java.util.List;

/**
	 * The computer player
	 * 
	 * @author Roland Crompton
	 *
	 */
public class ComputerPlayer extends Player {

	private static final String NAME = "Computer";
	private List<Move> moves = new LinkedList<>();
	public ComputerPlayer(Board board) {
		super(board);
	}


	@Override
	public String getName() {
		return NAME;
	}

		@Override
	public Move turn(Bag bag) {
		moves.clear();

		for (LetterTile lt : getRack()) {
			System.out.print(lt.getChar() + ", ");
		}
		System.out.println();
		long startTime = System.nanoTime();
//		parseBoardBreadth();
//		testWordsFindCombos();
		moves = new BoardParser(getBoard(), this, false).findMoves();
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		System.out.println(duration);

		Move bestMove = new Move(this, getBoard());
		bestMove.validateInput(",,");
		for (Move m : moves) {
			if (m.getWord().getScore() > bestMove.getWord().getScore()) {

				bestMove = m;
			}
		}
		System.out.println(bestMove.getWord());
		System.out.println(bestMove.getWord().getScore());
		System.out.println(moves.size());
		bestMove.tryMove();

		draw(bag);
		return bestMove;
	}

}
