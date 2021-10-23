import java.util.ArrayList;

import java.util.ArrayList;

public class Bag {
    ArrayList<Pebble> pebbles;
    int bagIndex;

    public Bag(ArrayList<Pebble> pebbles, int index){
        this.pebbles = pebbles;
        this.bagIndex = index;
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

    public boolean isEmpty(){
        return pebbles.isEmpty();
    }

    public void setPebbles(ArrayList<Pebble> pebbles) {
        this.pebbles = pebbles;
    }
}
