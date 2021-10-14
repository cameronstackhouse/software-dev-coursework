import java.util.InputMismatchException;
import java.util.Scanner;

public class PebbleGame {

    class Player implements Runnable{

        @Override
        public void run() {}
    }

    public static void main(String[] args) {

        System.out.println("Welcome to the Pebble Game!");
        System.out.println("You will be asked to enter the number of players \nand then the location of the three files" +
                " in turn containing comma separated integer values for the pebble weights. \nThe integer values must be" +
                " strictly positive. The game will then be simulated, and output written to files in this directory.");

        boolean valid = false;
        while (!valid) { //Keeps repeating until the user provides valid input for both number of players and filepaths
            Scanner reader = new Scanner(System.in);
            try {

                //Gets the user input for the number of players and parses it as an int
                System.out.println("Enter the number of players:");
                int numPlayers = reader.nextInt();

                //Gets the file path names for the
                System.out.println("Enter the location of bag number 0 to load:");
                String bag0FilePath = reader.nextLine();

                System.out.println("Enter the location of bag number 1 to load:");
                String bag1FilePath = reader.nextLine();

                System.out.println("Enter the location of bag number 2 to load:");
                String bag2FilePath = reader.nextLine();

                valid = true;

            } catch (InputMismatchException e) {
                PebbleGame.playerNumError();
            }

            System.out.println();

        }

    }

    private static void playerNumError(){
        System.out.println("PebbleGame must be run with the 1st argument as a positive integer representing the number" +
                " of players playing the game.");
    }

    private static void csvFormatError(){

    }

    private static void csvNotFoundError(){

    }

}
