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

    public static ArrayList<Pebble> readBag(String filename, int bagIndex) throws NumberFormatException, FileNotFoundException {
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

        Scanner reader = new Scanner(System.in);

        String bag0FilePath = null;
        String bag1FilePath = null;
        String bag2FilePath = null;

        final int numberOfBags = 3; //Number of black bags, set at 3 as this is how many are needed for the given program
        Bag[] blackBags = new Bag[numberOfBags]; //Array containing black bags

        Bag X = null;
        Bag Y = null;
        Bag Z = null;

        //Gets the user input for the number of players and parses it as an int
        //TO DO: CHECK IF USER ENTERS "E", IF SO THEN EXIT
        while (true){
            try {
                System.out.println("Enter number of players:");
                int numPlayers = reader.nextInt();
                break;
            } catch (InputMismatchException e){
                PebbleGame.playerNumError();
                reader.next();
            }
        }

        //Calls the function createBag to get the users input and create a new bag from a given csv file
        for(int i = 0; i < numberOfBags; i++){
            Bag current = createBlackBag(i);
            blackBags[i] = current;
        }
    }

    public static Bag createBlackBag(int bagIndex){
        //TO DO: CHECK IF ANY NEGATIVE INTEGERS
        Scanner reader = new Scanner(System.in);
        Bag newBag;
        while (true){
            try {
                System.out.println("Enter the location of bag number " + bagIndex + " to load:");
                String fPath = reader.next();
                newBag = new Bag(readBag(fPath, bagIndex));
                break;

            } catch (NumberFormatException e){
                PebbleGame.csvFormatError();
            } catch (FileNotFoundException e){
                PebbleGame.csvNotFoundError();
            }
        }
        return newBag;
    }

    private static void playerNumError(){
        System.out.println("PebbleGame must be run with the 1st argument as a positive integer representing the number" +
                " of players playing the game.");
    }

    private static void csvFormatError(){
        System.out.println("CSV file in an incorrect format, ensure the file consists of integers separated by commas.");
    }

    private static void csvNotFoundError(){
        System.out.println("No such file exists.");
    }

}
