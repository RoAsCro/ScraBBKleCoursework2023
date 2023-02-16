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
        assertNotNull(loadBoardFromTestBoards("minBoard.txt"));

        //Largest Possible Board
        assertNotNull(loadBoardFromTestBoards("bigBoard.txt"));

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

        //First line is a float


    }
}
