package test;

import pij.main.*;

import java.io.File;
import java.util.LinkedList;

public class TestUtility {




    public static void loadDictionary() {
        Validator.loadDictionary(new File("./resources/wordlist.txt"));
    }

    public static Bag riggedBag() {
        return new Bag(new int[] { 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
    }

    public static void writeOnBoard(Board board, String word, char direction) {
        writeOnBoard(new ScraBBKleCoordinate(board.getCentre(), board.getCentre()), board, word, direction);
    }
    public static void writeOnBoard(ScraBBKleCoordinate coord, Board board, String word, char direction) {
        LinkedList<LetterTile> list = new LinkedList<>();
        for (char c : word.toCharArray()) {
            list.add(new LetterTile(c + "", 1));
        }
        board.placeTiles(coord.getX(), coord.getY(), direction, list);
    }
}
