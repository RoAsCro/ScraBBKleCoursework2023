package test;

import org.junit.jupiter.api.*;
import pij.main.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import static junit.framework.Assert.assertEquals;


public class ComputerTest {

    private Move turn (ComputerPlayer cpu, Bag bag) {
        Move move = cpu.turn(bag);
        cpu.removeTiles(move.getTiles());
        return move;
    }


    @Test
    public void testComboFinder(){
        Bag riggedBag = new Bag(new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
        Board board = Validator.loadFile("./resources/testBoard.txt");
        ComputerPlayer cpu = new ComputerPlayer(board);
        cpu.draw(riggedBag);
        ArrayList<ArrayList<String>> words = new ArrayList<>();
        for (int i = 0; i < 7 ; i++)
            words.add(new ArrayList<String>());

        //cpu.allCombos(new LinkedList<>(cpu.getRack()), new StringBuilder(), words, 0);
        int count = 0;
        for (ArrayList<String> s : words) {
            count += s.size();
        }
        //assertEquals(count, 13699);
        for (int i = 0; i < 100 ; i++)
            words.add(new ArrayList<String>());

        riggedBag = new Bag(new int[] { 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,2 });
        cpu = new ComputerPlayer(board);
        cpu.draw(riggedBag);
        for (ArrayList<String> s : words) {
            //System.out.println(s);
            count += s.size();
        }
        System.out.println(count);




    }
    @Test
    public void testComputer(){
        Board board = Validator.loadFile("./resources/testBoard.txt");
        Validator.loadDictionary(new File("./resources/wordlist.txt"));
        TestUtility.writeOnBoard(board, "X", 'd');
        TestUtility.writeOnBoard(new ScraBBKleCoordinate(board.getCentre(), 8), board, "XX", 'r');

        Bag riggedBag = new Bag(new int[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
        Move move;
        ComputerPlayer cpu = new ComputerPlayer(board);
        turn(cpu, riggedBag);

        assertEquals(board.tileAt(6,7).toString(), "A1");

        TestUtility.writeOnBoard(new ScraBBKleCoordinate(4, 7), board, "F", 'r');
        riggedBag = new Bag(new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
        turn(cpu, riggedBag);


        assertEquals(board.tileAt(5,7).toString(), "L1");

        riggedBag = new Bag(new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
        turn(cpu, riggedBag);


        TestUtility.writeOnBoard(new ScraBBKleCoordinate(5, 8), board, "X", 'r');
        TestUtility.writeOnBoard(new ScraBBKleCoordinate(6, 6), board, "X", 'r');
        TestUtility.writeOnBoard(new ScraBBKleCoordinate(9, 8), board, "S", 'r');

        riggedBag = new Bag(new int[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
        turn(cpu, riggedBag);
        TestUtility.writeOnBoard(new ScraBBKleCoordinate(10, 7), board, "X", 'r');
        cpu.removeTiles(cpu.getRack());
        riggedBag = new Bag(new int[] { 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 });
        turn(cpu, riggedBag);

        board.print();

    }
    @Test
    public void testWildCards(){
        Board board = Validator.loadFile("./resources/testBoard.txt");
        TestUtility.loadDictionary();

        TestUtility.writeOnBoard(board, "EEEEE", 'd');

        Bag riggedBag = new Bag(new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
        Move move;
        ComputerPlayer cpu = new ComputerPlayer(board);
        turn(cpu, riggedBag);


        board.print();
        //System.out.println(Validator.dictionaryTwo.contains("x."));


    }
    @Test
    public void testGameplay() {
        Board board = Validator.loadFile("./resources/testBoard.txt");
        TestUtility.loadDictionary();
        ComputerPlayer cpu = new ComputerPlayer(board);
        TestUtility.writeOnBoard(board, "A", 'd');

        Move move;
        LinkedList<LetterTile> listy = new LinkedList<>();
        listy.add(new LetterTile("A", 1));
        listy.add(new LetterTile("B", 3));
        listy.add(new LetterTile("C", 3));
        listy.add(new LetterTile("D", 2));
        listy.add(new LetterTile("E", 1));
        listy.add(new LetterTile("F", 4));
        listy.add(new LetterTile("G", 2));

        do {
            Bag riggedBag = new Bag(new int[] { 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
            move = cpu.turn(riggedBag);
            cpu.removeTiles(listy);
            board.print();
        } while (!move.isPass());



    }
    @Test
    public void testGameplayEndingInWild() {
        Board board = Validator.loadFile("./resources/testBoard.txt");
        TestUtility.loadDictionary();

        ComputerPlayer cpu = new ComputerPlayer(board);
        TestUtility.writeOnBoard(board, "A", 'd');


        Move move;
        LinkedList<LetterTile> listy = new LinkedList<>();
        listy.add(new LetterTile("A", 1));
        listy.add(new LetterTile("B", 3));
        listy.add(new LetterTile("C", 3));
        listy.add(new LetterTile("D", 2));
        listy.add(new LetterTile("E", 1));
        listy.add(new LetterTile("F", 4));
        listy.add(new LetterTile("G", 2));
        Bag riggedBag;

        for (int i = 0 ; i < 13 ; i++) {
            riggedBag = new Bag(new int[] { 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
            turn(cpu, riggedBag);
        }
        cpu.removeTiles(new LinkedList<LetterTile>(cpu.getRack()));
        riggedBag = new Bag(new int[] { 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 });
        turn(cpu, riggedBag);
        board.print();





    }


}
