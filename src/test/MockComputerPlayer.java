package test;

import pij.main.Bag;
import pij.main.Board;
import pij.main.Move;
import pij.main.players.ComputerPlayer;

public class MockComputerPlayer extends ComputerPlayer {

    private final String myName;

    public MockComputerPlayer(Board board, String name) {
        super(board);
        this.myName = name;
    }

    @Override
    public String getName() {
        return this.myName;
    }

    @Override
    public void draw(Bag bag) {
        super.draw(GameTest.riggedBag);
    }

    @Override
    public Move turn() {
        return super.turn();
    }
}
