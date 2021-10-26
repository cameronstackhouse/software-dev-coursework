import java.util.ArrayList;

/**
 * Class representing a Bag
 */
public class Bag {
    ArrayList<Pebble> pebbles;
    int bagIndex;
    String bagName;

    /**
     * Constructor for a Bag object
     * @param pebbles Array List of pebbles in a bag
     * @param index Index of the bag in its array (WhiteBags, BlackBags)
     * @param bagName Name of the bag
     */
    public Bag(ArrayList<Pebble> pebbles, int index, String bagName){
        this.pebbles = pebbles;
        this.bagIndex = index;
        this.bagName = bagName;
    }

    /**
     * Method to get the name of the bag
     * @return name of the bag
     */
    public String getBagName() {
        return bagName;
    }

    /**
     * Method to get the pebbles of a bag
     * Synchronized to remove data inconsistency if a bags pebbles are being accessed and modified by multiple threads
     * @return pebbles in the bag
     */
    public synchronized ArrayList<Pebble> getPebbles() {
        return pebbles;
    }

    /**
     * Method to get the index of the bag in its array
     * @return index of the bag in the array (Black bag array or White bag array)
     */
    public int getBagIndex() {
        return bagIndex;
    }

    /**
     * Method to set the index of the bag in the array
     * @param bagIndex index of the bag in the array
     */
    public void setBagIndex(int bagIndex) {
        this.bagIndex = bagIndex;
    }

    /**
     * Method to set the name of the bag
     * @param bagName name of the bag ("X", "A")
     */
    public void setBagName(String bagName) {
        this.bagName = bagName;
    }

    /**
     * Method to check if the bag is empty
     * @return boolean indicating if the bag is empty or not
     */
    public boolean isEmpty(){
        return pebbles.isEmpty();
    }

    /**
     * Method to set the bags pebbles
     * @param pebbles array list of pebbles
     */
    public void setPebbles(ArrayList<Pebble> pebbles) {
        this.pebbles = pebbles;
    }
}

