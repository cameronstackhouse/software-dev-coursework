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

    /**
     * Method to create a black bag. The method internally asks for the user input of file name
     * @param bagIndex black bag number (e.g 0th bag, 1st bag, 2nd bag)
     * @param numberOfPlayers number of players playing the game. Used to calculate if the amount of pebbles in each bag
     *                        is sufficient
     * @return Bag object containing pebbles
     */
    public static Bag createBlackBag(int bagIndex, int numberOfPlayers){
        Scanner reader = new Scanner(System.in); //Creates a new scanner for user input
        Bag newBag;
        while (true){ //Repeats infinitely until break statement called
            try {
                System.out.println("Enter the location of bag number " + bagIndex + " to load:");
                String fPath = reader.next(); //Gets user input

                if(fPath.equals("E")){ //Checks if user has entered the exit code
                    System.exit(0);
                }

                newBag = new Bag(readCSVToBag(fPath, bagIndex)); //Creates a new bag by reading the CSV file specified

                if(newBag.getPebbles().size() < 11*numberOfPlayers){ //Checks that the number of pebbles in the bag is valid
                    throw new IllegalArgumentException(); //If not then throw an exception
                }
                break;

            } catch (NumberFormatException e){
                PebbleGame.csvFormatError();
            } catch (FileNotFoundException e){
                PebbleGame.csvNotFoundError();
            } catch (IllegalArgumentException e){
                PebbleGame.pebbleNumError(numberOfPlayers);
            }
        }

        reader.close(); //Closes reader
        return newBag;
    }

    /**
     * Method to read a CSV file of pebble weights into an ArrayList of pebbles
     * @param filename name of the CSV file with pebble weights
     * @param bagIndex bag number that is being read (e.g 0th bag, 1st bag, 2nd bag)
     * @return ArrayList of pebbles generated from the data in the CSV file
     * @throws NumberFormatException handles if the file is not in the correct format, e.g not comma separated integers
     * @throws FileNotFoundException handles if the given file name can not be found
     */
    public static ArrayList<Pebble> readCSVToBag(String filename, int bagIndex) throws NumberFormatException, FileNotFoundException {
        ArrayList<Pebble> pebbles = new ArrayList<>(); //Creates a new array list pebbles to store the pebbles

        Scanner scanner = new Scanner(new File(filename));
        scanner.useDelimiter(","); //Sets the delimiter to , as that is what separates values in a CSV file

        while (scanner.hasNext()){ //Iterates through CSV file for each item in the file

            int weight = Integer.parseInt(scanner.next().strip()); //Gets the value and strips it of whitespace and then parses it to an int

            if(weight < 0){ //Checks that the weight is positive. If not then throw an exception
                throw new NumberFormatException();
            }

            Pebble pebble = new Pebble(weight,bagIndex); //Creates a new pebble using the weight from the CSV file and the bag number it will be in
            pebbles.add(pebble); //Adds the newly created pebble to the array list of pebbles

        }

        scanner.close(); //Closes the scanner
        return pebbles; //Returns the ArrayList of pebbles generated from the CSV file
    }

    public static void main(String[] args) {

        System.out.println("Welcome to the Pebble Game!");
        System.out.println("You will be asked to enter the number of players \nand then the location of the three files" +
                " in turn containing comma separated integer values for the pebble weights. \nThe integer values must be" +
                " strictly positive. The game will then be simulated, and output written to files in this directory.");

        Scanner reader = new Scanner(System.in);

        final int numberOfBlackBags = 3; //Number of black bags, set at 3 as this is how many are needed for the given program
        Bag[] blackBags = new Bag[numberOfBlackBags]; //Array containing black bags

        int numPlayers;
        //Gets the user input for the number of players and parses it as an int
        while (true){ //Repeats until break statement called
            try {
                String userInput;
                System.out.println("Enter number of players:");
                userInput = reader.next(); //Gets the user input

                if(userInput.equals("E")){ //Checks if the user has entered exit condition
                    System.exit(0); //Ends the program
                } else {
                    numPlayers = Integer.parseInt(userInput); //Parses the input as an integer
                    if(numPlayers <= 0){ //Checks if the input is a valid number of players
                        throw new IllegalArgumentException(); //If not throw an exception
                    }
                    break; //If all conditions met then break out of the while loop
                }

            } catch (InputMismatchException | IllegalArgumentException e){
                PebbleGame.playerNumError(); //Displays an error message
            }
        }

        //Calls the function createBag to get the users input and create a new bag from a given csv file
        for(int i = 0; i < numberOfBlackBags; i++){
            Bag current = createBlackBag(i, numPlayers);
            blackBags[i] = current;
        }
    }


    /**
     * Method containing an error message if the user enters an invalid number of players
     */
    private static void playerNumError(){
        System.out.println("PebbleGame must be run with the 1st argument as a positive integer representing the number" +
                " of players playing the game.");
    }

    /**
     *
     */
    private static void csvFormatError(){
        System.out.println("CSV file in an incorrect format, ensure the file consists of positive integers separated by commas.");
    }

    /**
     *
     */
    private static void csvNotFoundError(){
        System.out.println("No such file exists.");
    }

    private static void pebbleNumError(int playerNum){
        System.out.println("Number of pebbles in each bag must be at least 11 times that of the number of players" +
                " which is: " + 11*playerNum);
    }

}