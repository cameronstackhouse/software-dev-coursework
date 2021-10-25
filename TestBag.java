import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestBag {
    private Bag bag;

    @Before
    public void setUp(){
        ArrayList<Pebble> pebbles = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            pebbles.add(new Pebble(1, 0));
        }

        bag = new Bag(pebbles, 0, "Test");
    }

    @After
    public void tearDown(){
        bag = null;
    }

    @Test
    public void testGetBagName(){
        assertEquals("Test", bag.getBagName());
    }

    @Test
    public void testGetPebbles(){
        for(int i = 0; i < bag.pebbles.size(); i++){
            assertEquals(1, bag.pebbles.get(i).getWeight());
            assertEquals(0, bag.pebbles.get(i).getBagIndex());
        }
    }

    @Test
    public void testGetBagIndex(){
        assertEquals(0, bag.getBagIndex());
    }

    @Test
    public void testSetBagIndex(){
        bag.setBagIndex(3);
        assertEquals(3, bag.getBagIndex());
    }

    @Test
    public void setBagName(){
        bag.setBagName("New Name");
        assertEquals("New Name", bag.getBagName());
    }

    @Test
    public void testIsEmpty(){
        assertFalse(bag.isEmpty());
    }

    @Test
    public void testSetPebbles(){
        ArrayList<Pebble> newPebbles = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            newPebbles.add(new Pebble(3, 0));
        }

        bag.setPebbles(newPebbles);

        for(int i = 0; i < 5; i++){
            assertEquals(3, bag.getPebbles().get(i).getWeight());
            assertEquals(0, bag.getPebbles().get(i).getBagIndex());
        }
    }
}
