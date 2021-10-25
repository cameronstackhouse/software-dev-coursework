import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.Assert.*;

public class TestPebbleGame {

    @Test
    public void testGetNumPlayersValid(){
        try {
            String userInput = "5";
            Scanner scanner = new Scanner(userInput);

            int players = PebbleGame.getNumPlayers(scanner);
            assertSame(5, players);

        } catch (NoSuchElementException e){
            fail();
        }
    }

    @Test
    public void testGetNumPlayersNegative(){
        try {
            String userInput = "-1";
            Scanner scanner = new Scanner(userInput);

            int players = PebbleGame.getNumPlayers(scanner);
            fail();

        } catch (NoSuchElementException e){
            //Passes if this is called. No Such element exception is thrown if the program asks for another user input
            //as the first input provided is invalid.
        }
    }

    @Test
    public void testGetNumPlayersInvalidType(){
        try{
            String userInput = "Invalid";
            Scanner scanner = new Scanner(userInput);

            PebbleGame.getNumPlayers(scanner);
            fail();
        } catch (NoSuchElementException e){}
        //Passes if this is called. No Such element exception is thrown if the program asks for another user input
        //as the first input provided is invalid.
    }

    @Test
    public void testGetNumPlayersExit(){
        String userInput = "E";
        Scanner scanner = new Scanner(userInput);

        PebbleGame.getNumPlayers(scanner);
        fail();
    }

    @Test
    public void testReadCSVToPebbleValid(){
        int mockBagIndex = 0;

        ArrayList<Pebble> expectedPebbles = new ArrayList<>();
        expectedPebbles.add(new Pebble(1, 0));
        expectedPebbles.add(new Pebble(1, 0));
        expectedPebbles.add(new Pebble(1, 0));
        expectedPebbles.add(new Pebble(1, 0));
        expectedPebbles.add(new Pebble(1, 0));


        try {
            String fileName = "testValid.csv";
            ArrayList<Pebble> actualPebbles = PebbleGame.readCSVToPebbles(fileName, mockBagIndex);
            for(int i = 0; i < 5; i++){
                assertEquals(expectedPebbles.get(i).getWeight(), actualPebbles.get(i).getWeight());
                assertEquals(expectedPebbles.get(i).getBagIndex(), actualPebbles.get(i).getBagIndex());
            }

        } catch (FileNotFoundException e){
            fail();
        }
    }

    @Test
    public void testReadCSVToPebbleInvalidFile(){
        int mockBagIndex = 0;

        try {
            String fileName = "doesNotExist.csv";
            PebbleGame.readCSVToPebbles(fileName, mockBagIndex);
            fail();
        } catch (FileNotFoundException e){}
    }

    @Test
    public void testReadCSVToPebbleInvalidFormat(){
        int mockBagIndex = 0;

        try {
            String filename = "invalidFormat.csv";
            PebbleGame.readCSVToPebbles(filename, mockBagIndex);
            fail();
        } catch (FileNotFoundException | NumberFormatException e){}
    }

    @Test
    public void testReadCSVToPebbleInvalidFileFormat(){
        int mockBagIndex = 0;

        try {
            String filename = "invalid.txt";
            PebbleGame.readCSVToPebbles(filename, mockBagIndex);
            fail();
        } catch (FileNotFoundException | NumberFormatException e){}
    }

    @Test
    public void testReadCSVToPebbleAllNegativeNum(){
        int mockBagIndex = 0;

        try {
            String filename = "negative.csv";
            PebbleGame.readCSVToPebbles(filename, mockBagIndex);
            fail();
        } catch (FileNotFoundException | NumberFormatException e){}
    }

    @Test
    public void testReadCSVToPebbleOneNegativeNum(){
        int mockBagIndex = 0;

        try {
            String filename = "oneNegative.csv";
            PebbleGame.readCSVToPebbles(filename, mockBagIndex);
            fail();
        } catch (FileNotFoundException | NumberFormatException e){}
    }

    @Test
    public void testCreateBlackBagValid(){
        int mockBagIndex = 0;
        int mockNumberOfPlayers = 3;
        String mockName = "A";


    }
}
