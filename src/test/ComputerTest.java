package test;

import org.junit.jupiter.api.*;
import pij.main.*;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import static junit.framework.Assert.assertEquals;


public class ComputerTest {

    public void loadWithLetters(LinkedList<LetterTile> tiles, int x, String letter) {
        for (int i = 0; i < x; i++) {
            tiles.add(new LetterTile(letter, 1));
        }
    }


    @Test
    public void testComboFinder(){
        Bag riggedBag = new Bag(new int[] { 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
        Board board = Validator.loadFile("./resources/testBoard.txt");
        ComputerPlayer cpu = new ComputerPlayer(board);
        cpu.draw(riggedBag);
        ArrayList<LinkedList<String>> words = new ArrayList<>();
        for (int i = 0; i < 7 ; i++)
            words.add(new LinkedList<String>());

        cpu.allCombos(new LinkedList<>(cpu.getRack()), new StringBuilder(), words, 0);
        int count = 0;
        for (LinkedList<String> s : words) {
            count += s.size();
        }
        //System.out.println(count);
        assertEquals(count, 13699);
        cpu.testWordsFindCombos();

    }
    @Test
    public void testComputer(){
        Board board = Validator.loadFile("./resources/testBoard.txt");


        Validator.loadDictionary(new File("./resources/wordlist.txt"));
        LinkedList<LetterTile> tiles = new LinkedList<>();
        loadWithLetters(tiles, 1, "X");

        board.placeTiles(board.getCentre(), board.getCentre(), 'd', tiles);
        loadWithLetters(tiles, 2, "X");
        board.placeTiles(board.getCentre(), 8, 'r', tiles);

        //System.out.println(board.tileAt(7,7));

        Bag riggedBag = new Bag(new int[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
        Move move;
        ComputerPlayer cpu = new ComputerPlayer(board);
        move = cpu.turn(riggedBag);
        cpu.removeTiles(move.getTiles());

        assertEquals(board.tileAt(6,7).toString(), "A1");

        loadWithLetters(tiles, 1, "F");
        board.placeTiles(4, 7, 'r', tiles);
        riggedBag = new Bag(new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
        move = cpu.turn(riggedBag);
        cpu.removeTiles(move.getTiles());

        assertEquals(board.tileAt(5,7).toString(), "L1");

        riggedBag = new Bag(new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
        move = cpu.turn(riggedBag);
        cpu.removeTiles(move.getTiles());


        loadWithLetters(tiles, 1, "X");
        board.placeTiles(5, 8, 'r', tiles);
        loadWithLetters(tiles, 1, "X");
        board.placeTiles(6, 6, 'r', tiles);
        loadWithLetters(tiles, 1, "S");
        board.placeTiles(9, 8, 'r', tiles);

        riggedBag = new Bag(new int[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
        move = cpu.turn(riggedBag);
        cpu.removeTiles(move.getTiles());




        board.print();




        System.out.println(Validator.lookupWord("FE"));


    }




}
