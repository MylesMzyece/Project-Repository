public class WeightedEdge extends Edge{
    int weight;
    //weighted edge initializer with inputted start end and weights
    public WeightedEdge(int source, int destination, int weight) {
        super(source, destination);
        this.weight=weight;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    @Override
    public String getEdgeRepresentation() {
        return source + " - " + destination + " (weight: " + weight + ")";
    }

}
