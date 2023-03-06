package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.*;

public class HumanPlayerTest {

    @Test
    public void testGetName() {
        Board board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
        Player player = new HumanPlayer(board);
        Assertions.assertEquals(player.getName(), "Human");

    }

    @Test
    public void testPlaceWildWord() {
        Board board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
        TestUtility.loadDictionary();
        Assertions.assertTrue(Dictionary.lookupWord("da"));
        Bag riggedBag = new Bag(new int[]{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
        board.placeTile(board.getCentre(), new LetterTile("A", 1));
        Player player = new SingleMovePlayer(board, "d,h7,d");
        player.draw(riggedBag);

        player.turn(riggedBag);
        Assertions.assertEquals("d3", board.tileAt(new Coordinate('h', 7)).toString());
        board.print();
    }

}
