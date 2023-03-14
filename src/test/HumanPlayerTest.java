package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pij.main.*;
import pij.main.Players.HumanPlayer;
import pij.main.Tiles.LetterTile;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HumanPlayerTest {

    private Board board;

    public static class SingleMovePlayer extends HumanPlayer {

        private String moveString;

        private int moves = 0;

        public SingleMovePlayer(Board board, String move) {
            super(board);
            this.moveString = move;

        }

        @Override
        public String getName() {
            return "SINGLE";
        }

        public int getMoves() {
            return moves;
        }

        @Override
        public Move turn(Bag bag) {
            moves++;
            Move move = new Move(getBoard());
            validateInput(this.moveString, move);
            move.tryMove();
            draw(bag);
            return move;
        }

        public void setMoveString(String moveString) {
            this.moveString = moveString;
        }
    }



    @BeforeEach
    public void setUp(){
        this.board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
        TestUtility.loadDictionary();
    }


    @Test
    public void testGetName() {
        Player player = new HumanPlayer(this.board);
        Assertions.assertEquals(player.getName(), "Human");

    }

    @Test
    public void testValidateInput() {
        Board board = TestUtility.loadBoardFromTestBoards("testBoard.txt");
        HumanPlayer player = new HumanPlayer(board);
        Move move = new Move(board);
        // No tiles
        Assertions.assertFalse(player.validateInput("A,f8,r", move));

        // Has the tiles
        Bag riggedBag = new Bag(new int[]{1});
        player.draw(riggedBag);
        Assertions.assertTrue(player.validateInput("A,f8,r", new Move(board)));

        // Pass
        Assertions.assertTrue(player.validateInput(",,", new Move(board)));
        Assertions.assertTrue(move.isPass());

        // Direction wrong
        Assertions.assertFalse(player.validateInput("A,f8,f", new Move(board)));

        // Wrong formats
        Assertions.assertFalse(player.validateInput("A, f8,r", new Move(board)));
        Assertions.assertFalse(player.validateInput("A,f8 r", new Move(board)));

    }

    @Test
    public void testPlaceWord() {
        SingleMovePlayer player = new SingleMovePlayer(this.board, "AA,a1,d");
        Bag riggedBag = new Bag(new int[]{7});
        player.draw(riggedBag);
        player.turn(riggedBag);
        Assertions.assertNotEquals(1, board.tileAt(new Coordinate('a', 1)).getValue());

        player.setMoveString(",,");
        player.turn(riggedBag);
        player.setMoveString("AA,h8,d");
        player.turn(riggedBag);
        Assertions.assertEquals(1, board.tileAt(board.getCentre()).getValue());

        player.setMoveString("A,g8,r");
        player.turn(riggedBag);
        Assertions.assertEquals(1, board.tileAt(new Coordinate('g', 8)).getValue());

        player.setMoveString("A,i9,r");
        player.turn(riggedBag);
        Assertions.assertNotEquals(1, board.tileAt(new Coordinate('i', 9)).getValue());

        player.setMoveString("A,h9,r");
        player.turn(riggedBag);
        Assertions.assertEquals(1, board.tileAt(new Coordinate('i', 9)).getValue());
        board.print();

    }

    @Test
    public void testPlaceWildWord() {
        Bag riggedBag = new Bag(new int[]{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
        this.board.placeTile(this.board.getCentre(), new LetterTile("A", 1));
        SingleMovePlayer player = new SingleMovePlayer(this.board, "d,h7,d");
        player.draw(riggedBag);

        player.turn(riggedBag);
        Assertions.assertEquals("d3", this.board.tileAt(new Coordinate('h', 7)).toString());

        player.turn(riggedBag);
        Assertions.assertEquals("d3", this.board.tileAt(new Coordinate('h', 9)).toString());

        player.setMoveString("q,h7,d");
        player.turn(riggedBag);
        Assertions.assertNotEquals("d3", this.board.tileAt(new Coordinate('h', 10)).toString());

        player.setMoveString(" ,h7,d");
        player.turn(riggedBag);
        board.print();

        Assertions.assertNotEquals("a3", this.board.tileAt(new Coordinate('h', 10)).toString());

        Assertions.assertEquals(' ', player.getRack().get(0).getChar());



    }

    @Test
    public void testMoreWordPlacement() {
        SingleMovePlayer human = new SingleMovePlayer(board, ",,");
        Bag riggedBag = new Bag(new int[] { 1, 1, 1, 1, 1, 1, 1 });
        // Tests initial word placement
        human.draw(riggedBag);
        Move move;
        human.setMoveString("FACE,a1,r");
        human.turn(riggedBag);
        assertTrue(board.getStartState());

        human.setMoveString("FACE,d1,r");
        human.turn(riggedBag);
        assertTrue(board.getStartState());

        human.setMoveString("FACE,c4,d");
        human.turn(riggedBag);
        assertTrue(board.getStartState());

        human.setMoveString("FACE,h7,r");
        human.turn(riggedBag);
        assertTrue(board.getStartState());

        human.setMoveString("FACE,i8,d");
        human.turn(riggedBag);
        assertTrue(board.getStartState());

        human.setMoveString("FACE,h8,d");
        human.turn(riggedBag);
        assertFalse(board.getStartState());

        board = TestUtility.loadBoardFromTestBoards("testBoard.txt");

        human = new SingleMovePlayer(board, "FACE,h5,d");
        riggedBag = new Bag(new int[] { 1, 1, 1, 1, 1, 1, 1 });
        human.draw(riggedBag);
        human.turn(riggedBag);
        assertFalse(board.getStartState());

        board = TestUtility.loadBoardFromTestBoards("testBoard.txt");

        human = new SingleMovePlayer(board, "FACE,e8,r");
        riggedBag = new Bag(new int[] { 1, 1, 1, 1, 1, 1, 1 });
        human.draw(riggedBag);
        human.turn(riggedBag);
        assertFalse(board.getStartState());

        board = TestUtility.loadBoardFromTestBoards("testBoard.txt");

        human = new SingleMovePlayer(board, "FACE,h8,r");
        riggedBag = new Bag(new int[] { 1, 1, 1, 1, 1, 1, 1 });
        human.draw(riggedBag);
        human.turn(riggedBag);
        assertFalse(board.getStartState());

        // Tests intersection test

        human.setMoveString("FACE,a1,r");
        human.turn(riggedBag);
        Assertions.assertNotEquals(1, board.tileAt(0, 0).getValue());

        human.setMoveString("FACE,a1,d");
        human.turn(riggedBag);
        Assertions.assertNotEquals(1, board.tileAt(0, 0).getValue());

        human.setMoveString("CE,i8,d");
        human.turn(riggedBag);
        Assertions.assertNotEquals(3, board.tileAt(9, 8).getValue());

        human.setMoveString("FAC,k5,d");
        human.turn(riggedBag);
        Assertions.assertEquals(4, board.tileAt(new Coordinate('k', 5)).getValue());

        human.setMoveString("FCE,j6,r");
        human.turn(riggedBag);
        Assertions.assertEquals(4, board.tileAt(new Coordinate('j', 6)).getValue());

        human.setMoveString("F,i7,d");
        human.turn(riggedBag);
        Assertions.assertEquals(4, board.tileAt(new Coordinate('i', 7)).getValue());

        human.setMoveString("D,k5,d");
        human.turn(riggedBag);
        Assertions.assertEquals(2, board.tileAt(new Coordinate('k', 9)).getValue());

        human.setMoveString("BADG,m2,d");
        human.turn(riggedBag);
        Assertions.assertEquals(3, board.tileAt(new Coordinate('m', 2)).getValue());

		// Tests not forming two words at once
        human.setMoveString("FAE,l4,d");
        human.turn(riggedBag);
        Assertions.assertNotEquals(4, board.tileAt(new Coordinate('l', 4)).getValue());

		// Tests double intersection
        human.setMoveString("DE,k3,d");
        human.turn(riggedBag);
        Assertions.assertEquals(2, board.tileAt(new Coordinate('k', 3)).getValue());

        human.setMoveString("FE,j4,r");
        human.turn(riggedBag);
        Assertions.assertEquals(4, board.tileAt(new Coordinate('j', 4)).getValue());

    	// Tests out of bounds right

        human.setMoveString("AB,m5,r");
        human.turn(riggedBag);
        Assertions.assertEquals(3, board.tileAt(new Coordinate('o', 5)).getValue());

        human.setMoveString("ADGE,m2,r");
        human.turn(riggedBag);
        Assertions.assertNotEquals(1, board.tileAt(new Coordinate('n', 2)).getValue());

		// Tests dictionary is taking into account all letters

        human.setMoveString("BADGE,o5,d");
        human.turn(riggedBag);
        Assertions.assertNotEquals(1, board.tileAt(new Coordinate('o', 6)).getValue());

        // Tests out of bounds down
        human.setMoveString("ADGE,o5,d");
        human.turn(riggedBag);
        Assertions.assertEquals(1, board.tileAt(new Coordinate('o', 6)).getValue());

        human.setMoveString("AD,k9,r");
        human.turn(riggedBag);
        Assertions.assertEquals(1, board.tileAt(new Coordinate('l', 9)).getValue());

        human.setMoveString("CED,l9,d");
        human.turn(riggedBag);
        Assertions.assertEquals(3, board.tileAt(new Coordinate('l', 10)).getValue());

        human.setMoveString("AD,l12,r");
        human.turn(riggedBag);
        Assertions.assertEquals(1, board.tileAt(new Coordinate('m', 12)).getValue());

        human.setMoveString("CED,m12,d");
        human.turn(riggedBag);
        Assertions.assertEquals(3, board.tileAt(new Coordinate('m', 13)).getValue());

        human.setMoveString("FA,k14,r");
        human.turn(riggedBag);
        Assertions.assertEquals(4, board.tileAt(new Coordinate('k', 14)).getValue());

        human.setMoveString("ACE,k14,d");
        human.turn(riggedBag);
        Assertions.assertNotEquals(1, board.tileAt(new Coordinate('k', 15)).getValue());

        board.print();
    }

}
