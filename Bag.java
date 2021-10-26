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
     * @param pebbles
     * @param index
     * @param bagName
     */
    public Bag(ArrayList<Pebble> pebbles, int index, String bagName){
        this.pebbles = pebbles;
        this.bagIndex = index;
        this.bagName = bagName;
    }

    /**
     *
     * @return
     */
    public String getBagName() {
        return bagName;
    }

    /**
     *
     * @return
     */
    public synchronized ArrayList<Pebble> getPebbles() {
        return pebbles;
    }

    /**
     *
     * @return
     */
    public int getBagIndex() {
        return bagIndex;
    }

    /**
     *
     * @param bagIndex
     */
    public void setBagIndex(int bagIndex) {
        this.bagIndex = bagIndex;
    }

    /**
     *
     * @param bagName
     */
    public void setBagName(String bagName) {
        this.bagName = bagName;
    }

    /**
     *
     * @return
     */
    public boolean isEmpty(){
        return pebbles.isEmpty();
    }

    /**
     *
     * @param pebbles
     */
    public void setPebbles(ArrayList<Pebble> pebbles) {
        this.pebbles = pebbles;
    }
}

