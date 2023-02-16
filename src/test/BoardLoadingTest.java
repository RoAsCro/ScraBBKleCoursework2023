package test;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import pij.main.*;
public class BoardLoadingTest {


    public Board loadBoardFromTestBoards(String fileName) {
        return Validator.loadFile("./resources/TestBoards/" + fileName);
    }

    @Test
    public void testBoardSizes() {
        //Smallest Possible Board
        Board boardOne = loadBoardFromTestBoards("minBoard.txt");
        assertNotNull(boardOne);
        assertNotNull(boardOne.tileAt(11,11));
        assertNull(boardOne.tileAt(12,0));
        assertNull(boardOne.tileAt(0,12));

        //Largest Possible Board
        boardOne = loadBoardFromTestBoards("bigBoard.txt");
        assertNotNull(boardOne);
        assertNotNull(boardOne.tileAt(25,25));
        assertNull(boardOne.tileAt(26,0));
        assertNull(boardOne.tileAt(0,26));

        //Blank File
        assertNull(loadBoardFromTestBoards("notABoard.txt"));

        //File with a number at the top but no lines below it
        assertNull(loadBoardFromTestBoards("numberNoLinesBoard.txt"));

        //Board is too small
        assertNull(loadBoardFromTestBoards("tooSmallBoard.txt"));

        //Board is too big
        assertNull(loadBoardFromTestBoards("tooBigBoard.txt"));

        //Number at the top does not match the number of rows (too few)
        assertNull(loadBoardFromTestBoards("rowMatchFew.txt"));

        //Number at the top does not match the number of rows (too many)
        assertNull(loadBoardFromTestBoards("rowMatchMany.txt"));

        //Number at the top does not match row length (too few)
        assertNull(loadBoardFromTestBoards("colMatchFew.txt"));

        //Number at the top does not match row length (too many)
        assertNull(loadBoardFromTestBoards("colMatchMany.txt"));

        //First line is not a number
        assertNull(loadBoardFromTestBoards("notANumber.txt"));

        //First line is a negative number
        assertNull(loadBoardFromTestBoards("negativeInt.txt"));

        //First line is a float
        assertNull(loadBoardFromTestBoards("float.txt"));
    }

    @Test
    public void testTileLoading() {

        //Max sized premium tile
        Board boardOne = loadBoardFromTestBoards("maxPremium.txt");
        assertNotNull(boardOne);
        assertEquals(99, boardOne.tileAt(0,0).getValue());

        //Min sized premium tile
        boardOne = loadBoardFromTestBoards("minPremium.txt");
        assertNotNull(boardOne);
        assertEquals(-9, boardOne.tileAt(0,0).getValue());

        //Premium tile value 0
        boardOne = loadBoardFromTestBoards("zeroPremium.txt");
        assertNotNull(boardOne);
        assertEquals(0, boardOne.tileAt(0,0).getValue());

        //Premium tile value too great
        assertNull(loadBoardFromTestBoards("largePremium.txt"));

        //Premium tile value too small
        assertNull(loadBoardFromTestBoards("smallPremium.txt"));

        //Premium tile value not integer
        assertNull(loadBoardFromTestBoards("floatPremium.txt"));

        //Invalid characters
        assertNull(loadBoardFromTestBoards("badChar.txt"));

        //Spaces
        assertNull(loadBoardFromTestBoards("badSpace.txt"));
    }

}
