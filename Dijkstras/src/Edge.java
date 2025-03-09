import java.util.Objects;

abstract class Edge {
    int source;
    int destination;


    //creates and edge with inputted start and end points
    public Edge(int source, int destination){
        this.source = source;
        this.destination = destination;
    }

    public int getSource() {
        return source;
    }
    public void setSource(int source) {
        this.source = source;
    }
    public int getDestination() {
        return destination;
    }
    public void setDestination(int destination) {
        this.destination = destination;
    }
    public abstract String getEdgeRepresentation();
    public abstract int getWeight();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge edge)) return false;
        return source == edge.source && destination == edge.destination;
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, destination);
    }
}
