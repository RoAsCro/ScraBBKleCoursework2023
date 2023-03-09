package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pij.main.*;
import pij.main.Tiles.LetterTile;

public class HumanPlayerTest {

    private Board board;

    public static class SingleMovePlayer extends Player {

        private String moveString;

        public SingleMovePlayer(Board board, String move) {
            super(board);
            this.moveString = move;

        }

        @Override
        public String getName() {
            return "SINGLE";
        }

        @Override
        public Move turn(Bag bag) {
            Move move = new Move(this, getBoard());
            move.tryMove(this.moveString);
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

}
