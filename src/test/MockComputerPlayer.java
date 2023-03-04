package test;

import pij.main.*;

import java.util.ArrayList;

public class MockComputerPlayer extends ComputerPlayer {


    public MockComputerPlayer(Board board) {
        super(board);
    }

    @Override
    public String getName() {
        return "Mock";
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
