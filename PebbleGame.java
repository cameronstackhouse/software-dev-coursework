import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class PebbleGame {
    private static final Object lock = new Object(); //Lock to be synchronized on
    static final int numberOfEachBag = 3; //Number of each bag (In this case 3)
    static final String[] blackBagNames = {"A", "B", "C"}; //Names of black bags
    static final String[] whiteBagNames = {"X", "Y", "Z"}; //Names of white bags

    //Arrays containing black and white bags. Black bag at [0] has corresponding white bag at whiteBags [0].
    static Bag[] blackBags = new Bag[numberOfEachBag];
    static Bag[] whiteBags = new Bag[numberOfEachBag];

    static volatile boolean won = false; //Records if a player has won the game. Volatile to force compiler to check won every time

    /**
     * Class representing a player of the game
     * Implements runnable as each player is a thread running concurrently playing the game
     */
    static class Player implements Runnable{
        private ArrayList<Pebble> hand; //Represents users hand containing pebbles
        private String name; //Name of the player playing the game

        /**
         * Constructor for creating a player object
         * @param name name of the player
         */
        public Player(String name){
            this.hand = new ArrayList<>();
            this.name = name;
        }

        /**
         * Method to get the name of the player
         * @return name of the player
         */
        public String getName() {
            return name;
        }

        /**
         * Method to get the hand of the player
         * @return hand of the player
         */
        public ArrayList<Pebble> getHand() {
            return hand;
        }

        /**
         * Method to set the hand of the player
         * @param hand hand of the player
         */
        public void setHand(ArrayList<Pebble> hand) {
            this.hand = hand;
        }

        /**
         * Method to set the name of the player
         * @param name name of the player
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Method to get the total weight value of the pebbles in the users hand
         * @return total weight value of hand
         */
        public int getHandValue(){
            int total = 0;

            for(int i = 0; i < hand.size(); i++){ //Iterates through hand
                total += hand.get(i).getWeight(); //Adds weight of current pebble to total
            }

            return total; //Returns the total weight value
        }

        /**
         * Method to discard a pebble at a given index in the users hand to its corresponding bag that it was drawn from
         * @param index index of the pebble in the hand to be discarded
         */
        public void discard(int index, Bag[] whiteBags) {
            Pebble removedPebble = this.hand.get(index); //Gets the pebble object from the hand from the index entered
            this.hand.remove(index); //Removes the pebble from the hand
            int bagIndex = removedPebble.getBagIndex(); //Gets the array index of the bag which the pebble was drawn from

            synchronized (lock) {
                //Synchronised on lock to ensure data consistencies don't occur when modifying white bag contents
                //that is shared between multiple threads.
                Bag whiteBag = whiteBags[bagIndex]; //Gets the corresponding discard bag from the whiteBags array
                whiteBag.getPebbles().add(removedPebble); //Adds the pebble to the white bag
                writeDiscard(removedPebble, whiteBags[bagIndex]); //Writes the discard to a text file
            }
        }

        /**
         * Method to draw a pebble from a given black bag into the users hand
         * @param blackBag bag to draw the pebble from
         * @return boolean value stating if a pebble has successfully been drawn from the bag
         */
        public boolean draw(Bag blackBag, Bag[] whiteBags){
            Random rand = new Random();
            synchronized (lock){
                //Synchronises on lock to ensure data inconsistencies do not occur when taking a pebble from a black
                //bag or refilling the black bag and modifying the corresponding white bag contents
                if (blackBag.isEmpty()) { //Checks if the bag is empty
                    refill(blackBag, whiteBags); //If so then call the refill method on the black bag
                    return false; //Returns false as a pebble has not been drawn
                } else {
                    int randPebbleIndex = rand.nextInt(blackBag.getPebbles().size()); //Gets a random integer from 0 to the number of pebbles in selected black bag
                    Pebble randomPebble = blackBag.getPebbles().get(randPebbleIndex); //Gets the pebble from the bag
                    blackBag.getPebbles().remove(randPebbleIndex); //Removes the randomly selected pebble from the bag
                    hand.add(randomPebble); //Adds the random pebble to the players hand
                    writeDraw(randomPebble, blackBag); //Writes the draw to the users output text file
                }
            }
            return true; //Returns true as a pebble has been drawn
        }

        /**
         * Method to refill a black bag with its corresponding white bag (e.g black bag A is refilled with white bag X)
         * @param blackBag black bag that is being refilled
         */
        public void refill(Bag blackBag, Bag[] whiteBags){
            int bagIndex = blackBag.getBagIndex(); //Gets the index of the black bag in the array (to get the corresponding white bag)
            Bag whiteBagAtIndex = whiteBags[bagIndex]; //Gets the white bag at the corresponding black bag index

            //Code to add the white bag pebbles into the black bag
            for(int i = 0; i < whiteBagAtIndex.getPebbles().size(); i++){
                //Iterates over the white pebbles bag and adds each pebble to the black bag which is being refilled
                blackBag.getPebbles().add(whiteBagAtIndex.getPebbles().get(i));
            }

            whiteBagAtIndex.getPebbles().clear(); //Clears the white bags pebble array list
        }

        /**
         * Method to write a draw to the users corresponding text file
         * @param pebble pebble that the user has drawn
         * @param bag bag that the user has drawn the pebble from
         */
        public void writeDraw(Pebble pebble, Bag bag){
            try {
                //Creates a new FileWriter to the filename of the player name. Append set to true
                FileWriter writer = new FileWriter(this.name + "_output.txt", true);
                //Writes that the user has drawn a pebble from a bag
                writer.write(name + " has drawn a " + pebble.getWeight() + " from bag " + bag.getBagName() + "\n");
                writer.write(name + " hand is " + handToString() + "\n"); //Writes users new hand
                writer.close();
            } catch (IOException e){
                //If file is not found then throws an error, this should never occur as file name is the player name
                //Which is set by the program.
                System.out.println("Error, File not found");
            }

        }

        /**
         * Method to write a discard to the users corresponding text file
         * @param pebble pebble that the user is discarding
         * @param bag bag that the user is discarding the pebble into
         */
        public void writeDiscard(Pebble pebble, Bag bag){
            try {
                //Creates a new FileWriter to the filename of the player name. Append set to true
                FileWriter writer = new FileWriter(this.name + "_output.txt", true);
                //Writes that the user has discarded a pebble to a bag
                writer.write(name + " has discarded a " + pebble.getWeight() + " to bag " + bag.getBagName() + "\n");
                writer.write(name + " hand is " + handToString() + "\n"); //Writes users new hand
                writer.close();
            } catch (IOException e){
                //If file is not found then throws an error, this should never occur as file name is the player name
                //Which is set by the program.
                System.out.println("Error, File not found");
            }
        }

        /**
         * Method to convert the users hand of pebble weights into a string output
         * @return users hand in a string format
         */
        public String handToString(){
            StringBuilder output = new StringBuilder();

            //Iterates over the players hand up to the last pebble
            for(int i= 0; i < hand.size() - 1; i++){
                output.append(hand.get(i).getWeight()).append(", "); //Adds indexed pebble weight to the StringBuilder
            }

            //Adds the last value in the players hand to the StringBuilder but with a full stop
            output.append(hand.get(hand.size() - 1).getWeight()).append(".");

            return output.toString();
        }

        @Override
        public void run() {
            Random rand = new Random();
            //initial hand code:

            Bag blackBagSelection = blackBags[rand.nextInt(numberOfEachBag)]; //Selects a random black bag

            //Draws 10 pebbles into the users hand from selected black bag
            for(int i = 0; i < 10; i++){
                draw(blackBagSelection, whiteBags);
            }

            while (!won){ //Repeats until a thread has won
                if(getHandValue() == 100){ //Checks if hand value is 100
                    won = true; //If so then set won to true
                    System.out.println(Thread.currentThread().getName() + " WON!");
                } else {
                    //Discards a random pebble from the players hand into corresponding white bag from the black bag in which the pebble was drawn from
                    int randomPebbleIndex = rand.nextInt(hand.size());
                    discard(randomPebbleIndex, whiteBags);

                    //Draws a pebble from a random black bag
                    boolean pebbleDrawn = false;
                    while (!pebbleDrawn) { //While the user has not drawn a pebble from a bag
                        //generates a bag index from the number of bags available
                        int randomPebbleBag = rand.nextInt(numberOfEachBag);
                        //selects bag at bag index
                        Bag bagToDrawFrom = blackBags[randomPebbleBag];
                        pebbleDrawn = draw(bagToDrawFrom, whiteBags); //Returns if user has drawn a pebble or not and
                    }
                }
            }
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
    public static ArrayList<Pebble> readCSVToPebbles(String filename, int bagIndex) throws NumberFormatException, FileNotFoundException {
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
    public static Bag createBlackBag(int bagIndex, int numberOfPlayers, Scanner reader, String name){
        Bag newBag;
        while (true){ //Repeats infinitely until break statement called
            try {
                System.out.println("Enter the location of bag number " + bagIndex + " to load:");
                String fPath = reader.nextLine(); //Gets user input

                if(fPath.equals("E")){ //Checks if user has entered the exit code
                    System.exit(0);
                }

                newBag = new Bag(readCSVToPebbles(fPath, bagIndex), bagIndex, name); //Creates a new bag by reading the CSV file specified

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
    public static void csvNotFoundError(){
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

    public static void main(String[] args) {

        System.out.println("Welcome to the Pebble Game!");
        System.out.println("You will be asked to enter the number of players \nand then the location of the three files" +
                " in turn containing comma separated integer values for the pebble weights. \nThe integer values must be" +
                " strictly positive. The game will then be simulated, and output written to files in this directory.");



        //Index of black bag in the black bags array corresponds with the index of its corresponding white bag in the
        //White bag array.

        Scanner reader = new Scanner(System.in); //Creates a new scanner for user input

        int numPlayers = getNumPlayers(reader); //Calls the function getNumPlayers to get the user entered number of players

        //Calls the function createBag to get the users input and create a new bag from a given csv file
        for(int i = 0; i < numberOfEachBag; i++){ //Creates each black bag one at a time depending on how many bags needed (In this case 3)
            Bag current = createBlackBag(i, numPlayers, reader, blackBagNames[i]); //Creates the black bag using the createBlackBag method
            blackBags[i] = current; //Adds the black bag to the black bag array
        }

        //Fills the white bag array with empty bags
        for(int i = 0; i < numberOfEachBag; i++){
            whiteBags[i] = new Bag(new ArrayList<>(), i, whiteBagNames[i]); //Creates a new empty bag
        }

        reader.close(); //Closes the reader after all user data has been inputted

        for(int i = 0; i < numPlayers; i++){ //Loops for the total number of players
            (new Thread((new Player("player" + i)))).start(); //Creates a new thread and a new player and starts it
        }
    }
}

