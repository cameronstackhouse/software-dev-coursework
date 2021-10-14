import java.util.ArrayList;

public class Bag {
    private ArrayList<Pebble> pebbles;
    private int numPebbles;

    public Bag(ArrayList<Pebble> pebbles){
        this.pebbles = pebbles;
        setNumPebbles();
    }

    public void setPebbles(ArrayList<Pebble> pebbles) {
        this.pebbles = pebbles;
    }

    public void setNumPebbles() {
        this.numPebbles = pebbles.size();
    }
}
