package pij.main.Players;

import pij.main.*;

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

	/**
	 * Constructs a new ComputerPlayer with the given Board.
	 *
	 * @param board the Board the ComputerPlayer will play on and use to decide on its Moves
	 */
	public ComputerPlayer(Board board) {
		super(board);
	}

	/**
	 * Returns the ComputerPlayer's name "Computer".
	 *
	 * @return the ComputerPlayer's name
	 */
	@Override
	public String getName() {
		return NAME;
	}

	/**
	 * Decides on and makes the Move for the ComputerPlayer. Finds all possible Moves
	 * using the MoveFinder, then finds the highest scoring Move and makes it.
	 *
	 * @return the Move the ComputerPlayer makes
	 */
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
