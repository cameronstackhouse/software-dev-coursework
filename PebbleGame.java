import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PebbleGame {

    class Player implements Runnable{

        @Override
        public void run() {}
    }

    public static ArrayList<Pebble> createBag(String filename, int bagIndex) throws FileNotFoundException, NumberFormatException {
        ArrayList<Pebble> pebbles = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));

        scanner.useDelimiter(",");

        while (scanner.hasNext()){

            int weight = Integer.parseInt(scanner.next().strip());

            Pebble pebble = new Pebble(weight,bagIndex);

            pebbles.add(pebble);

        }

        return pebbles;
    }

    public static void main(String[] args) {

        System.out.println("Welcome to the Pebble Game!");
        System.out.println("You will be asked to enter the number of players \nand then the location of the three files" +
                " in turn containing comma separated integer values for the pebble weights. \nThe integer values must be" +
                " strictly positive. The game will then be simulated, and output written to files in this directory.");

        boolean valid = false;
        while (!valid) { //Keeps repeating until the user provides valid input for both number of players and filepaths
            Scanner reader = new Scanner(System.in);
            String bag0FilePath = null, bag1FilePath = null, bag2FilePath = null;
            Bag X = null;
            Bag Y = null;
            Bag Z = null;
            try {

                //Gets the user input for the number of players and parses it as an int
                System.out.println("Enter the number of players:");
                int numPlayers = reader.nextInt();

                //Gets the file path names for the
                System.out.println("Enter the location of bag number 0 to load:");
                bag0FilePath = reader.next();

                System.out.println("Enter the location of bag number 1 to load:");
                bag1FilePath = reader.next();

                System.out.println("Enter the location of bag number 2 to load:");
                bag2FilePath = reader.next();

                valid = true;

            } catch (InputMismatchException e) {
                PebbleGame.playerNumError();
            }

            try{
                X = new Bag(createBag(bag0FilePath, 0));
                Y = new Bag(createBag(bag1FilePath, 1));
                Z = new Bag(createBag(bag2FilePath, 2));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (NumberFormatException e){
                PebbleGame.csvFormatError();
            }

            System.out.println(X.valueOfPebbles());
        }

    }

    private static void playerNumError(){
        System.out.println("PebbleGame must be run with the 1st argument as a positive integer representing the number" +
                " of players playing the game.");
    }

    private static void csvFormatError(){
        System.out.println("CSV file in an incorrect format, ensure the file consists of integers separated by commas.");
    }

    private static void csvNotFoundError(){

    }

}
