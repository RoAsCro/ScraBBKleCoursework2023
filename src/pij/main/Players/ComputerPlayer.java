package pij.main.Players;

import pij.main.*;

import java.util.LinkedList;
import java.util.List;

/**
 * A computer implementation of Player. Makes Moves automatically using the MoveFinder
 * based on its tile rack and its Board, finding every possible Move and playing the highest
 * scoring one.
 *
 * @author Roland Crompton
 */
public class ComputerPlayer extends Player {

	/** The ComputerPlayer's name. */
	private static final String NAME = "Computer";

	/** The ComputerPlayer's name. */
	public ComputerPlayer(Board board) {
		super(board);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public Move turn() {
		List<Move> moves = MoveFinder.findMoves(getBoard(), this.getRack(), false);
		Move bestMove = new Move(getBoard());
		for (Move m : moves) {
			if (m.getWord().getScore() > bestMove.getWord().getScore()) {
				bestMove = m;
			}
		}
		bestMove.tryMove();
		return bestMove;
	}

}
