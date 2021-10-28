import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestPlayer {
    private PebbleGame.Player p;
    private static Bag[] whiteBags; //Array containing white bags
    private static Bag[] blackBags;//Array containing white bags

    @Before
    public void setUp(){
        //Create new player and set hand

        String name = "Test Name";
        p = new PebbleGame.Player(name);

        ArrayList<Pebble> hand = new ArrayList<>();

        for(int i = 0; i< 5; i++){
            hand.add(new Pebble(1, 0));
        }

        p.setHand(hand);

        //Create black bags
        ArrayList<Pebble> bag1Pebbles = new ArrayList<>();
        ArrayList<Pebble> bag2Pebbles = new ArrayList<>();
        ArrayList<Pebble> bag3Pebbles = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            bag1Pebbles.add(new Pebble(2, 0));
            bag2Pebbles.add(new Pebble(3, 1));
            bag3Pebbles.add(new Pebble(4, 2));
        }

        Bag b1 = new Bag(bag1Pebbles, 0, "A");
        Bag b2 = new Bag(bag2Pebbles, 1, "B");
        Bag b3 = new Bag(bag3Pebbles, 2, "C");

        blackBags = new Bag[]{b1, b2, b3};

        //Create white bags
        Bag w1 = new Bag(new ArrayList<>(), 0, "X");
        Bag w2 = new Bag(new ArrayList<>(), 1, "Y");
        Bag w3 = new Bag(new ArrayList<>(), 2, "Z");

        whiteBags = new Bag[]{w1,w2,w3};
    }

    @After
    public void tearDown(){
        p = null;
        whiteBags = null;
        blackBags = null;
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
        p.setHand(new ArrayList<>());

        assertEquals(0, p.getHand().size());
    }

    @Test
    public void testHandToString(){
        String hand = p.handToString();

        assertEquals("1, 1, 1, 1, 1.", hand);
    }

    @Test
    public void testDiscard(){
        p.discard(0, whiteBags);
        p.discard(0, whiteBags);

        assertEquals(3, p.getHand().size());
    }

    @Test
    public void testDiscardEmptyHand(){
        p.setHand(new ArrayList<>());

        try {
            p.discard(0, whiteBags);
            fail();

        }catch (IndexOutOfBoundsException | NullPointerException e){}
    }

    @Test
    public void testDrawFullBag(){
        ArrayList<Pebble> pebbles = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            pebbles.add(new Pebble(2, 0));
        }

        Bag blackBag = new Bag(pebbles, 0, "A");

        p.draw(blackBag, whiteBags);

        assertEquals(6, p.getHand().size()); //Asserts that the number of pebbles in the users hand has increased by one
        assertEquals(7, p.getHandValue()); //Asserts that the total value of the users hand has increased
        assertEquals(4, blackBag.getPebbles().size()); //Asserts that the number of pebbles in the bag drawn from has been decreased
    }

    @Test
    public void testRefill(){
        Bag bagToBeRefilled = new Bag(new ArrayList<>(), 0, "TEST");

        whiteBags[0].getPebbles().add(new Pebble(2, 0));

        p.refill(bagToBeRefilled, whiteBags);

        assertEquals(1, bagToBeRefilled.getPebbles().size());
        assertEquals(0, whiteBags[0].getPebbles().size());
    }

    @Test
    public void testDrawOneInBag(){
        ArrayList<Pebble> pebbles = new ArrayList<>();

        pebbles.add(new Pebble(10, 0));

        Bag blackBag = new Bag(pebbles, 0, "A");

        p.draw(blackBag, whiteBags);

        assertEquals(6, p.getHand().size()); //Asserts that the number of pebbles in the users hand has increased by one
        assertEquals(15, p.getHandValue()); //Asserts that the total value of the users hand has increased
        assertEquals(0, blackBag.getPebbles().size()); //Asserts that the number of pebbles in the bag drawn from has been decreased
    }

    @Test
    public void testDrawEmptyBag(){
        boolean hasDrawn;
        blackBags[0].setPebbles(new ArrayList<>());
        whiteBags[0].getPebbles().add(new Pebble(2, 0));

        hasDrawn = p.draw(blackBags[0], whiteBags);

        assertFalse(hasDrawn); //Checks if a pebble has been drawn, shouldn't be drawn as bag is empty

        hasDrawn = p.draw(blackBags[0], whiteBags); //Tries to draw again as the black bag should've been refilled

        //Asserts a pebble has been drawn and added to the players hand
        assertEquals(6, p.getHand().size());
        assertTrue(hasDrawn);
    }

    @Test
    public void writeDraw(){

    }

    @Test
    public void writeDiscard(){

    }

}
