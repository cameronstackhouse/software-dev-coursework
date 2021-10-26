import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestPlayer {
    private PebbleGame.Player p;
    private static Bag[] whiteBags = new Bag[3]; //Array containing white bags
    private static Bag[] blackBags = new Bag[3]; //Array containing white bags

    @Before
    public void setUp(){
        String name = "Test Name";
        p = new PebbleGame.Player(name);

        ArrayList<Pebble> hand = new ArrayList<>();

        for(int i = 0; i< 5; i++){
            hand.add(new Pebble(1, 0));
        }

        p.setHand(hand);

    }

    @After
    public void tearDown(){
        p = null;
    }

    @Test
    public void testPlayerCreate(){
        String name = "Test Name";
        PebbleGame.Player p = new PebbleGame.Player(name);
    }

    @Test
    public void testGetName(){
        String name = "Test Name";
        PebbleGame.Player p = new PebbleGame.Player(name);

        assertEquals(name, p.getName());
    }

    @Test
    public void testSetName(){
        String name = "New Name";
        p.setName(name);

        assertEquals(name, p.getName());
    }

    @Test
    public void testGetHandValue(){
        int value = p.getHandValue();

        assertEquals(5, value);
    }

    @Test
    public void testSetHand(){

    }

    @Test
    public void testHandToString(){
        String hand = p.handToString();

        assertEquals("1, 1, 1, 1, 1.", hand);
    }

    @Test
    public void testDiscard(){
        int discardIndex = 0;
        p.discard(0);
        p.discard(0);

        assertEquals(3, p.getHand().size());
    }

    @Test
    public void testDrawFullBag(){
        ArrayList<Pebble> pebbles = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            pebbles.add(new Pebble(2, 0));
        }

        Bag blackBag = new Bag(pebbles, 0, "A");

        p.draw(blackBag);

        assertEquals(6, p.getHand().size()); //Asserts that the number of pebbles in the users hand has increased by one
        assertEquals(7, p.getHandValue()); //Asserts that the total value of the users hand has increased
        assertEquals(4, blackBag.getPebbles().size()); //Asserts that the number of pebbles in the bag drawn from has been decreased
    }

    @Test
    public void testRefill(){

    }

    @Test
    public void testDrawOneInBag(){
        ArrayList<Pebble> pebbles = new ArrayList<>();

        pebbles.add(new Pebble(10, 0));

        Bag blackBag = new Bag(pebbles, 0, "A");

        p.draw(blackBag);

        assertEquals(6, p.getHand().size()); //Asserts that the number of pebbles in the users hand has increased by one
        assertEquals(15, p.getHandValue()); //Asserts that the total value of the users hand has increased
        assertEquals(0, blackBag.getPebbles().size()); //Asserts that the number of pebbles in the bag drawn from has been decreased
    }

    @Test
    public void testDrawEmptyBag(){
        boolean hasDrawn;

    }

    @Test
    public void writeDraw(){

    }

    @Test
    public void writeDiscard(){

    }

}
