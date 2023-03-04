package test;

import pij.main.*;

public class MockComputerPlayer extends ComputerPlayer {


    public MockComputerPlayer(Board board) {
        super(board);
    }

    @Override
    public String getName() {
        return "Mock";
    }

    @Override
    public Move turn(Bag bag) {
        getRack().clear();
        draw(GameTest.riggedBag);
        Move move = super.turn(bag);
        return move;
    }
}
