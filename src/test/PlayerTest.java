package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.Bag;
import pij.main.Board;
import pij.main.CharacterTile;
import pij.main.Player;
import pij.main.Players.ComputerPlayer;
import pij.main.Tiles.LetterTile;
import pij.main.Tiles.WildTile;

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
        // remove a single tile
        List<CharacterTile> list = List.of(new LetterTile("A", 1));
        player.removeTiles(list);
        Assertions.assertTrue(player.getRack().isEmpty());

        // remove a single WildTile
        bag = new Bag(new int[]
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1});
        player.draw(bag);
        list = List.of(new LetterTile("a", 3));
        player.removeTiles(list);
        Assertions.assertTrue(player.getRack().isEmpty());

        // remove a single tile when there is more than one that will match
        bag = new Bag(new int[]{2, 1});
        player.draw(bag);
        list = List.of(new LetterTile("A", 1));
        player.removeTiles(list);
        Assertions.assertEquals(2, player.getRack().size());
        player.removeTiles(list);
        Assertions.assertEquals(1, player.getRack().size());
        player.removeTiles(list);
        Assertions.assertEquals(1, player.getRack().size());

        // remove a WildTile when none match
        list = List.of(new WildTile());
        player.removeTiles(list);
        Assertions.assertEquals(1, player.getRack().size());

        // remove multiple tiles
        bag = new Bag(new int[]{1, 1});
        player.draw(bag);
        list = List.of(new LetterTile("A", 1),
                new LetterTile("B", 1),
                new LetterTile("B", 1));
        player.removeTiles(list);
        Assertions.assertEquals(0, player.getRack().size());

    }

}
