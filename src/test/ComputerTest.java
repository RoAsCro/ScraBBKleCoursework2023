package test;

import org.junit.jupiter.api.*;

import pij.main.*;
import pij.main.Players.ComputerPlayer;
import pij.main.Tiles.LetterTile;

import java.util.LinkedList;

public class ComputerTest {

    private Move turn(ComputerPlayer cpu, Bag bag) {
        cpu.draw(bag);
        Move move = cpu.turn(bag);
        cpu.removeTiles(move.getTiles());
        return move;
    }

    @Test
    public void testGetName() {
        Board board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
        Player player = new ComputerPlayer(board);
        Assertions.assertEquals(player.getName(), "Computer");

    }


    @Test
    public void testComputer(){
        Board board = TestUtility.loadBoardFromTestBoards("TestBoard.txt");
        TestUtility.loadDictionary();
        TestUtility.writeOnBoard(board, "X", 'd');
        TestUtility.writeOnBoard(new Coordinate(board.getCentre().getX(), 8), board, "XX", 'r');

        // Test placing prefix
        Bag riggedBag = new Bag(new int[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
        ComputerPlayer cpu = new ComputerPlayer(board);
        turn(cpu, riggedBag);
        board.print();
        Assertions.assertEquals("A1", board.tileAt(6,7).toString());

        // Find move that involves placing tiles between tiles
        TestUtility.writeOnBoard(new Coordinate(4, 7), board, "F", 'r');
        riggedBag = new Bag(new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
        turn(cpu, riggedBag);
        board.print();
        Assertions.assertEquals( "L1", board.tileAt(5,7).toString());

        // Test placing vertically
        riggedBag = new Bag(new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
        turn(cpu, riggedBag);
        board.print();
        Assertions.assertEquals( "O1", board.tileAt(4,6).toString());

        // Test placing suffix
        TestUtility.writeOnBoard(new Coordinate(5, 8), board, "X", 'r');
        TestUtility.writeOnBoard(new Coordinate(6, 6), board, "X", 'r');
        TestUtility.writeOnBoard(new Coordinate(9, 8), board, "S", 'r');
        riggedBag = new Bag(new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
        turn(cpu, riggedBag);
        board.print();
        Assertions.assertEquals("O1", board.tileAt(9,9).toString());

        // Test use of blank tiles and finding best move
        TestUtility.writeOnBoard(new Coordinate(10, 7), board, "X", 'r');
        cpu.removeTiles(cpu.getRack());
        riggedBag = new Bag(new int[] { 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 });
        turn(cpu, riggedBag);
        board.print();
        Assertions.assertEquals("t3", board.tileAt(0,7).toString());

    }

    @Test
    public void testGameplay() {
        Board board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
        TestUtility.loadDictionary();
        ComputerPlayer cpu = new ComputerPlayer(board);
        TestUtility.writeOnBoard(board, "A", 'd');

        Move move;
        // By removing the tiles in list, the computer will behave deterministically
        LinkedList<CharacterTile> list = new LinkedList<>();
        list.add(new LetterTile("A", 1));
        list.add(new LetterTile("B", 3));
        list.add(new LetterTile("C", 3));
        list.add(new LetterTile("D", 2));
        list.add(new LetterTile("E", 1));
        list.add(new LetterTile("F", 4));
        list.add(new LetterTile("G", 2));

        int count = 0;
        Bag riggedBag;
        do {
            riggedBag = new Bag(new int[]{1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
            move = turn(cpu, riggedBag);

            cpu.removeTiles(list);

            board.print();
            count++;
        } while (!move.isPass() && count < 24);
        Assertions.assertEquals(23, count);
    }

}
