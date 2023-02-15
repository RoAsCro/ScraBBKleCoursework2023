package test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import pij.main.*;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;


public class WildTest {

    public class TestPlayer extends Player {
        public TestPlayer(Board board) {
            super(board);
        }

        @Override
        public Move turn(Bag bag) {

            return null;
        }
    }

    @Test
    public void testThis(){
        Validator.loadDictionary(new File("./resources/wordlist.txt"));

        String x = "a.b";
        StringBuilder a = new StringBuilder("[a]");
        a.insert(2,'?');
        System.out.println("ab".matches(x));
        System.out.println("acb".matches(x));
        System.out.println("vabb".matches(x));
        System.out.println("accb".matches(x));

        TreeSet<String> tree = new TreeSet<>();
        tree.add("a");
        tree.add("aa");
        tree.add("aab");
        tree.add("a");
        tree.add("aba");
        tree.add("b");
        SortedSet<String> treeTwo = tree.tailSet("aa");
        treeTwo = treeTwo.headSet("b");
        System.out.println(treeTwo);
        System.out.println("abc".substring(0,2));

        TreeSet<String> test = new TreeSet<>((o1, o2) -> {
            int z = o1.length() - o2.length();
            if (z == 0) {
                return o1.compareTo(o2);
            }
            return z;
        }
        );
        test.addAll(tree);
        System.out.println(tree);
        System.out.println(test);
        treeTwo = test.headSet("b");
        System.out.println(treeTwo);

        long start = System.nanoTime();
        //System.out.println(Validator.dictionaryTwo.contains("x."));
        long end = System.nanoTime();
        System.out.println(end-start);

         start = System.nanoTime();

        //System.out.println(Validator.dictionaryThree.contains("aa.aaaof"));
        TreeSet<String> stringTree = new TreeSet<>((o1, o2) -> {
            int z = o1.length() - o2.length();
            if (!(z == 0))
                return z;
            if (o2.matches(o1))
                return 0;
            if (o1.contains(".")) {
                System.out.println(o1);
                System.out.println(o2);
                StringBuilder builder1 = new StringBuilder(o1);
                StringBuilder builder2 = new StringBuilder(o2);

                for (int i = 0; i < o1.length(); i++) {

                    int index = o1.indexOf(".");
                    builder1.replace(index, index + 1, "");
                    o1 = builder1.toString();
                    builder2.replace(index, index + 1, "");
                    o2 = builder2.toString();

                }
            }
                return o1.compareTo(o2);
        });
        stringTree.add("a");
        stringTree.add("c");
        stringTree.add("bb");
        stringTree.add("b");
        stringTree.add("aa");
        String line = "badef";
        char[] chars = line.toCharArray();
       // for (int i = 0 ; i < 13669 ; i++)
//            System.out.println(Validator.lookupCombo(".*a.*d.*e.*i.*l.*u.*y.*"));

//        System.out.println(Validator.lookupWord("agh"));
        start = System.nanoTime();
        long stop = System.nanoTime();
        System.out.println(stop - start);

        start = System.nanoTime();
        System.out.println(Validator.lookupWord("aghast"));
        stop = System.nanoTime();
        System.out.println(stop - start);

//        Arrays.sort(chars);
//        System.out.println(new String(chars));
//        String orderedLine = chars.toString();

       // System.out.println(stringTree.contains("a."));

//        Iterator<String> iter = Validator.dictionaryTwo.iterator();
//        while (iter.hasNext()) {
//            if (iter.next().matches("x.")) {
//                System.out.println("True");
//                break;
//            }
//        }






    }

    @Test
    public void testDefaultWild(){
        WildTile wild = new WildTile();
        assertEquals(wild.getChar(), '.');

    }

    @Test
    public void testWildMove(){
        Bag rigged = new Bag(new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2000});
    }
}
