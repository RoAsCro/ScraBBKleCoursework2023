package test;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import pij.main.*;
public class BoardLoadingTest {

    @Test
    public void testBoardSizes() {
        //Smallest Possible Board
        assertNotNull(Validator.loadFile("./resources/TestBoards/minBoard.txt"));

        //LargestPossibleBoard
        assertNotNull(Validator.loadFile("./resources/TestBoards/bigBoard.txt"));

        //Blank File
        assertNull(Validator.loadFile("./resources/TestBoards/notABoard.txt"));

        //File with a number at the top but no lines below it
        assertNull(Validator.loadFile("./resources/TestBoards/numberNoLinesBoard.txt"));

        //Board is too small
        assertNull(Validator.loadFile("./resources/TestBoards/tooSmallBoard.txt"));

        //Board is too big
        assertNull(Validator.loadFile("./resources/TestBoards/tooBigBoard.txt"));

        //Number at the top does not match the number of rows (too few)

        //Number at the top does not match the number of rows (too many)

        //Number at the top does not match row length (too few)

        //Number at the top does not match row length (too many)

        //First line is not a number

        //First line is

        //


    }
}
