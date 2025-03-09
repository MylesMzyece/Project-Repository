import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

public class UndirectedWeightedGraphTest {

    private UndirectedGraph graph;

    @BeforeEach
    void setUp() {
        graph = new UndirectedGraph(5);  // Initialize with 5 nodes
    }

    @Test
    void testAddEdgeDuplicate() {
        WeightedEdge edge1 = new WeightedEdge(0, 1, 10);
        WeightedEdge edge2 = new WeightedEdge(0, 1, 10);


        graph.addEdge(edge1);
        // Try adding the same edge again
        graph.addEdge(edge2);

        LinkedList<Edge> edgesFrom0 = graph.getIncidentEdges(0);
        LinkedList<Edge> edgesFrom1 = graph.getIncidentEdges(1);

        assertEquals(1, edgesFrom0.size(), "Node 0 should have only 1 edge.");
        assertEquals(1, edgesFrom1.size(), "Node 1 should have only 1 edge.");
    }

    @Test
    void testAddInvalidEdge() {
        assertThrows(IllegalArgumentException.class, () -> {
            WeightedEdge edge = new WeightedEdge(0, 5, 15); // Invalid edge (node 5 doesn't exist)
            graph.addEdge(edge);
        }, "Should throw an exception for invalid edge.");
    }

    @Test
    void testDijkstraUndirectedWeighted() {
        Graph undirectedGraph = new UndirectedGraph(6);

        undirectedGraph.addEdge(new WeightedEdge(0, 1, 3));
        undirectedGraph.addEdge(new WeightedEdge(1, 5, 7));
        undirectedGraph.addEdge(new WeightedEdge(0, 2, 2));
        undirectedGraph.addEdge(new WeightedEdge(2, 3, 2));
        undirectedGraph.addEdge(new WeightedEdge(3, 5, 4));
        undirectedGraph.addEdge(new WeightedEdge(0, 4, 5));
        undirectedGraph.addEdge(new WeightedEdge(4, 5, 2));

        LinkedList<Integer> path = undirectedGraph.dijkstra(0, 5);
        //0,4,5 weight of 7
        assertNotNull(path);
        assertEquals(3, path.size());
    }

    @Test
    void testEdgeExistsUndirectedWeightedEdge() {
        Graph graph = new UndirectedGraph(5);

        WeightedEdge edge1 = new WeightedEdge(0, 1, 10);
        WeightedEdge edge2 = new WeightedEdge(1, 2, 5);

        graph.addEdge(edge1);
        graph.addEdge(edge2);

        assertTrue(graph.edgeExists(edge1));
        assertTrue(graph.edgeExists(edge2));
    }
}

