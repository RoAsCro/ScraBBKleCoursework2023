package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.Dictionary;

public class DictionaryTest {
    @Test
    public void testLookup() {
        TestUtility.loadDictionary();
        // Normal words
        Assertions.assertFalse(Dictionary.lookupWord("a"));
        Assertions.assertTrue(Dictionary.lookupWord("aa"));

    }


}
