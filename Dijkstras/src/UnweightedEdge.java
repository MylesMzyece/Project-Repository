public class UnweightedEdge extends Edge {
    public UnweightedEdge(int source, int destination) {
        super(source, destination);
    }

    @Override
    public String getEdgeRepresentation() {
        return source + " - " + destination;
    }

    public int getWeight(){
        return 1;
    }
}
