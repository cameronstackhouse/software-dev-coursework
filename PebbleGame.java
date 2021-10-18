import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class PebbleGame {
    static volatile boolean won = false; //Records if a player has won the game. Volatile to force compiler to check won every time


    /**
     * Class representing a player of the game
     */
    static class Player implements Runnable{
        private ArrayList<Pebble> hand; //Represents users hand containing pebbles

        public Player(){
            this.hand = new ArrayList<Pebble>();
        }

        /**
         * Method to get the weight value of the players hand
         * @return the weight value of the players hand
         */
        public int getHandValue(){
            int total = 0;

            for(int i = 0; i < hand.size(); i++){ //Iterates through hand
                total += hand.get(i).getWeight(); //Adds weight of current pebble to total
            }

            return total;
        }

        @Override
        public void run() {
            Random rand = new Random();
            //initialHand()

            while (!won){ //Repeats until the won condition has been met
                if(getHandValue() == 100){ //Checks if hand value is 100
                    won = true;
                } else {
                    int discardIndex = rand.nextInt(hand.size()); //Gets the index of a random pebble to be discarded

                    //discard(discardIndex)
                    //draw()
                }
            }
        }

        public void discard(int index) {
            Pebble removedPebble = hand.get(index);
            hand.remove(index);
            int bagIndex = removedPebble.getBagIndex();

        }
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

    /**
     * Method to create a black bag. The method internally asks for the user input of file name
     * @param bagIndex black bag number (e.g 0th bag, 1st bag, 2nd bag)
     * @param numberOfPlayers number of players playing the game. Used to calculate if the amount of pebbles in each bag
     *                        is sufficient
     * @param reader scanner to get user input passed in
     * @return Bag object containing pebbles
     */
    public static Bag createBlackBag(int bagIndex, int numberOfPlayers, Scanner reader){
        Bag newBag;
        while (true){ //Repeats infinitely until break statement called
            try {
                System.out.println("Enter the location of bag number " + bagIndex + " to load:");
                String fPath = reader.nextLine(); //Gets user input

                if(fPath.equals("E")){ //Checks if user has entered the exit code
                    System.exit(0);
                }

                newBag = new Bag(readCSVToBag(fPath, bagIndex)); //Creates a new bag by reading the CSV file specified

                if(newBag.getPebbles().size() < 11*numberOfPlayers){ //Checks that the number of pebbles in the bag is valid
                    throw new IllegalArgumentException(); //If not then throw an exception
                }

                break; //Breaks from infinite while loop if the black bag is valid

            } catch (NumberFormatException e){ //Catches if the CSV file is in the wrong format
                PebbleGame.csvFormatError();
            } catch (FileNotFoundException e){ //Catches if the CSV file does not exist
                PebbleGame.csvNotFoundError();
            } catch (IllegalArgumentException e){ //Catches if the number of pebbles is invalid
                PebbleGame.pebbleNumError(numberOfPlayers);
            }
        }

        return newBag;
    }

    /**
     * Gets the user input for total number of players until the input is valid
     * @param reader scanner to get user input passed in
     * @return number of players playing the game
     */
    public static int getNumPlayers(Scanner reader){

        int numPlayers;
        //Gets the user input for the number of players and parses it as an int
        while (true){ //Repeats until break statement called
            try {
                String userInput;
                System.out.println("Enter number of players:");
                userInput = reader.nextLine(); //Gets the user input

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

        return numPlayers;
    }


    public static void main(String[] args) {

        System.out.println("Welcome to the Pebble Game!");
        System.out.println("You will be asked to enter the number of players \nand then the location of the three files" +
                " in turn containing comma separated integer values for the pebble weights. \nThe integer values must be" +
                " strictly positive. The game will then be simulated, and output written to files in this directory.");


        final int numberOfEachBag = 3; //Number of black bags, set at 3 as this is how many are needed for the given program

        //Index of black bag in the black bags array corresponds with the index of its corresponding white bag in the
        //White bag array.

        Bag[] blackBags = new Bag[numberOfEachBag]; //Array containing black bags
        Bag[] whiteBags = new Bag[numberOfEachBag]; //Array containing white bags

        Scanner reader = new Scanner(System.in); //Creates a new scanner for user input

        int numPlayers = getNumPlayers(reader); //Calls the function getNumPlayers to get the user entered number of players

        //Calls the function createBag to get the users input and create a new bag from a given csv file
        for(int i = 0; i < numberOfEachBag; i++){ //Creates each black bag one at a time depending on how many bags needed (In this case 3)
            Bag current = createBlackBag(i, numPlayers, reader); //Creates the black bag using the createBlackBag method
            blackBags[i] = current; //Adds the black bag to the black bag array
        }

        //Fills the white bag array with empty bags
        for(int i = 0; i < numberOfEachBag; i++){
            whiteBags[i] = new Bag(new ArrayList<Pebble>());
        }

        reader.close(); //Closes the reader after all user data has been inputted

        for(int i = 0; i < numPlayers; i++){ //Loops for the total number of players
            (new Thread((new Player()))).start(); //Creates a new thread and a new player and starts it
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
     * Method containing an error message if the format of the CSV file is invalid
     */
    private static void csvFormatError(){
        System.out.println("CSV file in an incorrect format, ensure the file consists of positive integers separated by commas.");
    }

    /**
     * Method containing an error message if the CSV file can't be found with a given filepath
     */
    private static void csvNotFoundError(){
        System.out.println("No such file exists.");
    }

    /**
     * Method containing an error message if the number of pebbles in each bag is too small
     * @param playerNum player number entered by the user
     */
    private static void pebbleNumError(int playerNum){
        System.out.println("Number of pebbles in each bag must be at least 11 times that of the number of players" +
                " which is: " + 11*playerNum);
    }

}