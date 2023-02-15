package pij.main;

import java.util.*;

public class Dictionary{


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
        String orderedLine2 = new String(chars);
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
    private Comparator<String> comparator = (o1, o2) -> {
        if (o1.contains("1")) {
            if (o1.replaceAll("1", ".").matches(o2))
                return 0;
            return 1;
        }
        else if (o1.contains("2")) {
            if (o1.replaceAll("2", ".").matches(o2))
                return 0;
            return -1;
        }

        return o1.compareTo(o2);
    };




//    public boolean search(String word) {
//        String newWord = word.toLowerCase();
//        if (newWord.contains(" ")){
//            while (newWord.contains(" ")){
//
//            }
//
//            SortedSet<String> tree = Validator.getDictionary().subSet(newWord.replaceAll(" ", "a"), newWord.replaceAll(" ", "z") + "a");
//            if (tree.contains(newWord.replace(" ", "1"))){
//                return true;
//            } else if (tree.contains(newWord.replace(" ", "2"))) {
//                return true;
//            }
//
//        }
//
//        return Validator.lookupWord(newWord);
//    }

    public boolean searchIter(TreeSet<String > tree, String word) {

        return false;
    }

}
