import java.util.ArrayList;

public class Bag {
    ArrayList<Pebble> pebbles;
    int bagIndex;
    String bagName;

    public Bag(ArrayList<Pebble> pebbles, int index, String bagName){
        this.pebbles = pebbles;
        this.bagIndex = index;
        this.bagName = bagName;
    }

    public String getBagName() {
        return bagName;
    }

    public ArrayList<Pebble> getPebbles() {
        return pebbles;
    }

    public int getBagIndex() {
        return bagIndex;
    }

    public void setBagIndex(int bagIndex) {
        this.bagIndex = bagIndex;
    }

    public void setBagName(String bagName) {
        this.bagName = bagName;
    }

    public boolean isEmpty(){
        return pebbles.isEmpty();
    }

    public void setPebbles(ArrayList<Pebble> pebbles) {
        this.pebbles = pebbles;
    }
}
