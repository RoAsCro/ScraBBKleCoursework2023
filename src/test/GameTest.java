package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pij.main.*;

import java.util.List;

public class GameTest {
    Game game;
    Board board;

    public static Bag riggedBag = new Bag();

    @BeforeEach
    public void setUp() {
        board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
        TestUtility.loadDictionary();
        List<Player> players = List.of(new MockComputerPlayer(board, "One"), new MockComputerPlayer(board, "Two"));
        game = new Game(players, board);
    }

    @Test
    public void testEndingDraw() {
        riggedBag = new Bag(new int[]{});
        board.placeTile(new ScraBBKleCoordinate(board.getCentre(), board.getCentre()), new LetterTile("A", 1));
        game.run();
        Assertions.assertEquals("It's a draw!", game.checkVictory());
    }

    @Test
    public void testEndingWinner() {
        riggedBag = new Bag(new int[]{1});
        board.placeTile(new ScraBBKleCoordinate(board.getCentre(), board.getCentre()), new LetterTile("A", 1));
        game.run();
        Assertions.assertEquals("The one player wins!", game.checkVictory());
    }

//    @Test void testEnding

}
