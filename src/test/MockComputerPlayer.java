package test;

import pij.main.*;
import pij.main.Players.ComputerPlayer;

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
    public Move turn(Bag bag) {
        Move move = super.turn(bag);
        return move;
    }
}
