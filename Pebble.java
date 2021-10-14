public class Pebble {
    private int weight;
    private int bagIndex;

    public Pebble(int weight, int bagIndex){
        this.weight = weight;
        this.bagIndex = bagIndex;
    }

    public int getBagIndex() {
        return bagIndex;
    }

    public int getWeight() {
        return weight;
    }
}
