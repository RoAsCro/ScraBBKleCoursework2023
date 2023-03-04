package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.Board;
import pij.main.HumanPlayer;
import pij.main.Player;

public class HumanPlayerTest {

    @Test
    public void testGetName() {
        Board board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
        Player player = new HumanPlayer(board);
        Assertions.assertEquals(player.getName(), "Human");

    }

}
