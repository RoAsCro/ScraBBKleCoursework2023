package test;

import org.junit.jupiter.api.Test;
import pij.main.*;

import static junit.framework.Assert.*;

public class BagTest {

    @Test
    public void testBag(){
        Bag bag = new Bag(new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
        //Tests draw() on an empty bag
        assertNull(bag.draw());
        //Tests isEmpty() on an empty bag
        assertTrue(bag.isEmpty());

        bag = new Bag(new int[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
        //Tests isEmpty() on a bag with tiles
        assertFalse(bag.isEmpty());
        //Tests the above constructor worked and the draw function
        assertEquals(new LetterTile("A", 1).toString(),  bag.draw().toString());
        assertNull(bag.draw());
        assertTrue(bag.isEmpty());


        bag = new Bag(new int[] {1});
        //Tests the above constructor
        assertEquals(new LetterTile("A", 1).toString(),  bag.draw().toString());
        assertNull(bag.draw());

        bag = new Bag(new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
        //Tests the above constructor
        assertEquals(new LetterTile("Z", 10).toString(),  bag.draw().toString());
        assertNull(bag.draw());

        bag = new Bag(new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
        //Tests the above constructor for WildTiles
        assertEquals(new WildTile().toString(),  bag.draw().toString());
        assertNull(bag.draw());

        bag = new Bag();
        //Test the default constructor has tiles
        assertFalse(bag.isEmpty());
        //Tests there are exactly 100 tile in the bag after the default constructor
        for (int i = 0; i < 99 ; i++)
            bag.draw();
        assertFalse(bag.isEmpty());
        bag.draw();
        assertTrue(bag.isEmpty());

    }

}
