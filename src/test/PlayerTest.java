package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.*;

import java.util.List;

public class PlayerTest {
    @Test
    public void testPlayer() {
        Board board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
        Player player = new ComputerPlayer(board);

        // Test score methods
        Assertions.assertEquals(0, player.getScore());
        player.updateScore(2);
        Assertions.assertEquals(2, player.getScore());

        // Test getBoard
        Assertions.assertEquals(board.getCentre().getX(), player.getBoard().getCentre().getX());

        // getRack and draw
        Bag bag = new Bag(new int[]{1});
        Assertions.assertTrue(player.getRack().isEmpty());
        player.draw(bag);
        Assertions.assertEquals('A', player.getRack().get(0).getChar());

        // Remove
        List<LetterTile> list = List.of(new LetterTile("A", 1));
        player.removeTiles(list);
        Assertions.assertTrue(player.getRack().isEmpty());

    }

}
