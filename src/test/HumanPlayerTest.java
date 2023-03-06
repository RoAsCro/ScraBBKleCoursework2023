package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.*;

public class HumanPlayerTest {

    public class SingleMovePlayer extends Player {

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
        SingleMovePlayer player = new SingleMovePlayer(board, "d,h7,d");
//        Assertions.assertTrue();
        player.draw(riggedBag);

        player.turn(riggedBag);
        Assertions.assertEquals("d3", board.tileAt(new Coordinate('h', 7)).toString());

        player.turn(riggedBag);
        Assertions.assertEquals("d3", board.tileAt(new Coordinate('h', 9)).toString());

        player.setMoveString("q,h7,d");
        player.turn(riggedBag);
        Assertions.assertNotEquals("d3", board.tileAt(new Coordinate('h', 10)).toString());

        Assertions.assertEquals(' ', player.getRack().get(0).getChar());


    }

}
