import java.util.ArrayList;

public class Bag {
    ArrayList<Pebble> pebbles;

    public Bag(ArrayList<Pebble> pebbles){
        this.pebbles = pebbles;
    }

    public ArrayList<Pebble> getPebbles() {
        return pebbles;
    }

    public boolean isEmpty(){
        return this.pebbles.isEmpty();
    }

    public int valueOfPebbles(){
        int total = 0;
        for(int i = 0; i < pebbles.size(); i++){
            total += pebbles.get(i).getWeight();
        }

        return total;
    }
}
