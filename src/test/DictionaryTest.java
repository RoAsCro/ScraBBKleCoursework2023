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
        // Wildcard words
        Assertions.assertTrue(Dictionary.lookupWord(" a"));
        Assertions.assertFalse(Dictionary.lookupWord(" "));
        Assertions.assertFalse(Dictionary.lookupWord("   Q"));

        Assertions.assertTrue(Dictionary.WILD_CHARACTERS.isEmpty());
        Dictionary.lookupWord(" a");
        Assertions.assertFalse(Dictionary.WILD_CHARACTERS.isEmpty());
        Assertions.assertEquals('a', Dictionary.WILD_CHARACTERS.get(0));

    }


}
