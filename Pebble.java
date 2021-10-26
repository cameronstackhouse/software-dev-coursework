/**
 * Class representing a Pebble
 */
public class Pebble {
    private int weight;
    private int bagIndex;

    /**
     * Constructor for Pebble object
     * @param weight weight of the pebble
     * @param bagIndex index of the black bag in the array that contains black bags that the pebble has been drawn from
     */
    public Pebble(int weight, int bagIndex){
        this.weight = weight;
        this.bagIndex = bagIndex;
    }

    /**
     * Method to get the corresponding array index of the black bag that the pebble has been drawn from
     * @return index of black bag in the array that the pebble was drawn from
     */
    public int getBagIndex() {
        return bagIndex;
    }

    /**
     * Method to get the weight of a pebble
     * @return weight of the pebble
     */
    public int getWeight() {
        return weight;
    }
}
