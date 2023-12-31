package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pij.main.*;
import pij.main.tiles.LetterTile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class GameTest {
    Game game;
    Board board;
    Method check = Game.class.getDeclaredMethod("checkVictory");


    public static Bag riggedBag = new Bag();

    public GameTest() throws NoSuchMethodException {
    }


    @BeforeEach
    public void setUp() {
        this.board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
        TestUtility.loadDictionary();
        List<Player> players = List.of(new MockComputerPlayer(this.board, "One"),
                new MockComputerPlayer(this.board, "Two"));
        this.game = new Game(players, this.board);
        check.setAccessible(true);
    }

    @Test
    public void testPasses() {
        this.board.placeTile(this.board.getCentre(),
                new LetterTile("A", 1));
        HumanPlayerTest.SingleMovePlayer playerOne =
                new HumanPlayerTest.SingleMovePlayer(this.board, ",,");
        List<Player> players = List.of(playerOne,
                new HumanPlayerTest.SingleMovePlayer(this.board, ",,"));
        Game gameTwo = new Game(players, this.board);
        gameTwo.run();
        Assertions.assertEquals(2, playerOne.getMoves());
    }

    @Test
    public void testEndingDraw() throws InvocationTargetException, IllegalAccessException {
        riggedBag = new Bag(new int[]{});
        this.board.placeTile(this.board.getCentre(),
                new LetterTile("A", 1));
        this.game.run();
        Assertions.assertEquals("It's a draw!",
                this.check.invoke(this.game));
    }

    @Test
    public void testEndingWinner() throws InvocationTargetException, IllegalAccessException {
        riggedBag = new Bag(new int[]{1});
        this.board.placeTile(this.board.getCentre(),
                new LetterTile("A", 1));
        this.game.run();
        Assertions.assertEquals("The one player wins!",
                this.check.invoke(this.game));
    }

    @Test
    public void testEndingWithDeduction() throws InvocationTargetException, IllegalAccessException {
        riggedBag = new Bag(new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        this.board.placeTile(this.board.getCentre(),
                new LetterTile("A", 1));
        this.game.run();
        Assertions.assertEquals("The two player wins!",
                this.check.invoke(this.game));
    }

    @Test
    public void testScoringSevenTiles() {
        TestUtility.loadTestDictionary();
        this.board = TestUtility.loadBoardFromTestBoards("blankBoard.txt");
        List<Player> players = List.of(new MockComputerPlayer(this.board, "One"),
                new MockComputerPlayer(this.board, "Two"));
        this.game = new Game(players, this.board);

        riggedBag = new Bag(new int[]{20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        this.board.placeTile(this.board.getCentre(),
                new LetterTile("A", 1));
        this.board.placeTile(new Coordinate(7, 8),
                new LetterTile("A", 1));

        this.game.run();
        Assertions.assertEquals(84, players.get(0).getScore());
        Assertions.assertEquals(6, players.get(1).getScore());

    }

}
