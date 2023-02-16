package test;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import pij.main.*;
public class BoardLoadingTest {




    @Test
    public void testBoardSizes() {
        //Smallest Possible Board
        Board boardOne = TestUtility.loadBoardFromTestBoards("minBoard.txt");
        assertNotNull(boardOne);
        assertNotNull(boardOne.tileAt(11,11));
        assertNull(boardOne.tileAt(12,0));
        assertNull(boardOne.tileAt(0,12));

        //Largest Possible Board
        boardOne = TestUtility.loadBoardFromTestBoards("bigBoard.txt");
        assertNotNull(boardOne);
        assertNotNull(boardOne.tileAt(25,25));
        assertNull(boardOne.tileAt(26,0));
        assertNull(boardOne.tileAt(0,26));

        //Blank File
        assertNull(TestUtility.loadBoardFromTestBoards("notABoard.txt"));

        //File with a number at the top but no lines below it
        assertNull(TestUtility.loadBoardFromTestBoards("numberNoLinesBoard.txt"));

        //Board is too small
        assertNull(TestUtility.loadBoardFromTestBoards("tooSmallBoard.txt"));

        //Board is too big
        assertNull(TestUtility.loadBoardFromTestBoards("tooBigBoard.txt"));

        //Number at the top does not match the number of rows (too few)
        assertNull(TestUtility.loadBoardFromTestBoards("rowMatchFew.txt"));

        //Number at the top does not match the number of rows (too many)
        assertNull(TestUtility.loadBoardFromTestBoards("rowMatchMany.txt"));

        //Number at the top does not match row length (too few)
        assertNull(TestUtility.loadBoardFromTestBoards("colMatchFew.txt"));

        //Number at the top does not match row length (too many)
        assertNull(TestUtility.loadBoardFromTestBoards("colMatchMany.txt"));

        //First line is not a number
        assertNull(TestUtility.loadBoardFromTestBoards("notANumber.txt"));

        //First line is a negative number
        assertNull(TestUtility.loadBoardFromTestBoards("negativeInt.txt"));

        //First line is a float
        assertNull(TestUtility.loadBoardFromTestBoards("float.txt"));
    }

    @Test
    public void testTileLoading() {

        //Max sized premium tile
        Board boardOne = TestUtility.loadBoardFromTestBoards("maxPremium.txt");
        assertNotNull(boardOne);
        assertEquals(99, boardOne.tileAt(0,0).getValue());

        //Min sized premium tile
        boardOne = TestUtility.loadBoardFromTestBoards("minPremium.txt");
        assertNotNull(boardOne);
        assertEquals(-9, boardOne.tileAt(0,0).getValue());

        //Premium tile value 0
        boardOne = TestUtility.loadBoardFromTestBoards("zeroPremium.txt");
        assertNotNull(boardOne);
        assertEquals(0, boardOne.tileAt(0,0).getValue());

        //Premium tile value too great
        assertNull(TestUtility.loadBoardFromTestBoards("largePremium.txt"));

        //Premium tile value too small
        assertNull(TestUtility.loadBoardFromTestBoards("smallPremium.txt"));

        //Premium tile value not integer
        assertNull(TestUtility.loadBoardFromTestBoards("floatPremium.txt"));

        //Invalid characters
        assertNull(TestUtility.loadBoardFromTestBoards("badChar.txt"));

        //Spaces
        assertNull(TestUtility.loadBoardFromTestBoards("badSpace.txt"));
    }

}
