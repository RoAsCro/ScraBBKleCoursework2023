package pij.main.Players;

import pij.main.*;

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
		moves = new MoveFinder(getBoard(), this.getRack(), false).findMoves();
		Move bestMove = new Move(getBoard());
//		bestMove.tryMove(",,");
		for (Move m : moves) {
			if (m.getWord().getScore() > bestMove.getWord().getScore()) {
				bestMove = m;
			}
		}
		bestMove.tryMove();
		draw(bag);
		return bestMove;
	}

}
