package test;

import com.sun.source.tree.Tree;
import org.junit.jupiter.api.Test;
import pij.main.*;

import java.io.File;
import java.util.*;

public class TestUtility {

    private static final Comparator<String> PREFIX_COMPARATOR = (o1, o2) -> {
        int len1 = o1.length();
        int len2 = o2.length();
        if (!(len1 == len2))
            return len1 - len2;

        char[] chars = o1.toCharArray();
        Arrays.sort(chars);
        String orderedLine = new String(chars);
        char[] chars2 = o2.toCharArray();
        Arrays.sort(chars2);
        String orderedLine2 = new String(chars2);
        int comboCheck = orderedLine.compareTo(orderedLine2);
        if (comboCheck != 0) {
            return comboCheck;
        }

        int index = o1.indexOf(" ");
        if (index != -1) {
            o1 = o1.substring(0, index);
            o2 = o2.substring(0, index);
        }
        return o1.compareTo(o2);
    };


    @Test
    public void read(){
        loadDictionary();
        Validator.compare("s v ");
//        TreeSet<String> set = new TreeSet<>(PREFIX_COMPARATOR);
//        set.addAll(Validator.getDictionary());
//        String strng = set.first();
//        int i = 1;
//        int j = 0;
//        for (String s : set) {
//            if (s.length() == i) {
//                char[] chars = s.toCharArray();
//                Arrays.sort(chars);
//                String orderedLine = new String(chars);
//                char[] chars2 = strng.toCharArray();
//                Arrays.sort(chars2);
//                String orderedLine2 = new String(chars2);
//                if (orderedLine.compareTo(orderedLine2) == 0) {
//                    j++;
//                } else {
//                    System.out.println(j);
//                    strng = s;
//                    j = 1;
//                }
//            } else {
//                System.out.println(j);
//                System.out.println("i: " + i);
//                j = 1;
//                strng = s;
//                i++;
//            }
//        }
    }


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
        BoardReader reader = new BoardReader(board, coord, direction);
        reader.conditionalNext((tile) -> !list.isEmpty(), (x, y) -> board.placeTile(new ScraBBKleCoordinate(x, y), list.poll()));
    }
}
