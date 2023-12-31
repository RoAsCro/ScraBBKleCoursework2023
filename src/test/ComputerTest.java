package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.*;
import pij.main.players.ComputerPlayer;
import pij.main.tiles.LetterTile;

import java.util.LinkedList;
import java.util.List;

public class ComputerTest {

    private Move turn(ComputerPlayer cpu, Bag bag) {
        cpu.draw(bag);
        Move move = cpu.turn();
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
    public void testTurn() {
        Board board = TestUtility.loadBoardFromTestBoards("TestBoard.txt");
        TestUtility.loadDictionary();

        // Test passing
        // ...when no tiles
        ComputerPlayer cpu = new ComputerPlayer(board);
        Assertions.assertTrue(cpu.turn().isPass());
        // ...when no valid moves
        Bag riggedBag = new Bag(new int[]
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1});
        TestUtility.writeOnBoard(board, "X", 'd');
        cpu.draw(riggedBag);
        Assertions.assertTrue(cpu.turn().isPass());
        cpu.removeTiles(List.of(new LetterTile("Z", 10)));

        TestUtility.writeOnBoard(
                new Coordinate(board.getCentre().getX(), 8), board, "XX", 'r');

        // Test placing prefix
        riggedBag = new Bag(new int[]
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        turn(cpu, riggedBag);
        board.print();
        Assertions.assertEquals("A1", board.tileAt(6, 7).toString());

        // Find move that involves placing tiles between tiles
        TestUtility.writeOnBoard(new Coordinate(4, 7), board, "F", 'r');
        riggedBag = new Bag(new int[]
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        turn(cpu, riggedBag);
        board.print();
        Assertions.assertEquals("L1", board.tileAt(5, 7).toString());

        // Test placing vertically
        riggedBag = new Bag(new int[]
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        turn(cpu, riggedBag);
        Assertions.assertEquals("O1", board.tileAt(4, 6).toString());

        // Test placing suffix
        TestUtility.writeOnBoard(new Coordinate(5, 8), board, "X", 'r');
        TestUtility.writeOnBoard(new Coordinate(6, 6), board, "X", 'r');
        TestUtility.writeOnBoard(new Coordinate(9, 8), board, "S", 'r');
        riggedBag = new Bag(new int[]
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        turn(cpu, riggedBag);
        Assertions.assertEquals("O1", board.tileAt(9, 9).toString());

        // Test use of blank tiles and finding best move
        TestUtility.writeOnBoard(new Coordinate(10, 7), board, "X", 'r');
        cpu.removeTiles(cpu.getRack());
        riggedBag = new Bag(new int[]
                {1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2});
        turn(cpu, riggedBag);
        board.print();
        Assertions.assertEquals("t3", board.tileAt(0, 7).toString());

    }

    @Test
    public void testTurnTwo() {
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
            riggedBag = new Bag(new int[]{1, 1, 1, 1, 1, 1, 1});
            move = turn(cpu, riggedBag);

            cpu.removeTiles(list);

            board.print();
            count++;
        } while (!move.isPass() && count < 24);
        Assertions.assertEquals(23, count);
    }

}
