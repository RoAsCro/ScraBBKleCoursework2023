package test;

import pij.main.Bag;
import pij.main.Board;
import pij.main.Move;
import pij.main.Player;

public class SingleMovePlayer extends Player {

    private final String moveString;

    public SingleMovePlayer(Board board, String move) {
        super(board);
        this.moveString = move;

    }

    @Override
    public String getName() {
        return "SINGLE";
    }

    @Override
    public Move turn(Bag bag) {
        Move move = new Move(this, getBoard());
        move.tryMove(this.moveString);
        draw(bag);
        return move;
    }
}
