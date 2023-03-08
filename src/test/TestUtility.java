package test;

import pij.main.*;
import pij.main.Dictionary;

import java.io.File;
import java.util.*;

public class TestUtility {


    public static Board loadBoardFromTestBoards(String fileName) {
        return ScraBBKleUtil.loadFile("./resources/TestBoards/" + fileName);
    }

    public static void loadDictionary() {
        Dictionary.loadDictionary(new File("./resources/wordlist.txt"));
    }

    public static void loadTestDictionary() {
        Dictionary.loadDictionary(new File("./resources/testWordList.txt"));
    }

    public static Bag riggedBag() {
        return new Bag(new int[] { 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
    }

    public static void writeOnBoard(Board board, String word, char direction) {
        writeOnBoard(board.getCentre(), board, word, direction);
    }
    public static void writeOnBoard(Coordinate coord, Board board, String word, char direction) {
        LinkedList<LetterTile> list = new LinkedList<>();
        for (char c : word.toCharArray()) {
            list.add(new LetterTile(c + "", 1));
        }
        BoardReader reader = new BoardReader(board, coord, direction);
        reader.conditionalNext((tile) -> !list.isEmpty(), (c) -> board.placeTile(c, list.poll()));
    }
}
