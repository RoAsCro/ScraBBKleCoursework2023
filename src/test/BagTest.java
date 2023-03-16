package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.Bag;
import pij.main.tiles.LetterTile;
import pij.main.tiles.WildTile;

public class BagTest {

    @Test
    public void testBag() {
        Bag bag;

        bag = new Bag(new int[]
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        //Tests draw() on an empty bag
        Assertions.assertNull(bag.draw());
        //Tests isEmpty() on an empty bag
        Assertions.assertTrue(bag.isEmpty());

        bag = new Bag(new int[]
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        //Tests isEmpty() on a bag with tiles
        Assertions.assertFalse(bag.isEmpty());
        //Tests the above constructor worked and the draw function
        Assertions.assertEquals(new LetterTile("A", 1).toString(), bag.draw().toString());
        Assertions.assertNull(bag.draw());
        Assertions.assertTrue(bag.isEmpty());


        bag = new Bag(new int[]{1});
        //Tests the above constructor
        Assertions.assertEquals(new LetterTile("A", 1).toString(), bag.draw().toString());
        Assertions.assertNull(bag.draw());

        bag = new Bag(new int[]
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1});
        //Tests the above constructor
        Assertions.assertEquals(new LetterTile("Z", 10).toString(), bag.draw().toString());
        Assertions.assertNull(bag.draw());

        bag = new Bag(new int[]
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1});
        //Tests the above constructor for WildTiles
        Assertions.assertEquals(new WildTile().toString(), bag.draw().toString());
        Assertions.assertNull(bag.draw());

        bag = new Bag();
        //Test the default constructor has tiles
        Assertions.assertFalse(bag.isEmpty());
        //Tests there are exactly 100 tile in the bag after the default constructor
        for (int i = 0; i < 99; i++)
            bag.draw();
        Assertions.assertFalse(bag.isEmpty());
        bag.draw();
        Assertions.assertTrue(bag.isEmpty());

    }

}
