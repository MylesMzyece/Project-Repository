import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

public class UndirectedUnweightedGraphTest {
    private UndirectedGraph graph;


    @BeforeEach
    void setUp() {
        graph = new UndirectedGraph(5);  // Initialize with 5 nodes
    }

    @Test
    void testAddEdgeDuplicate() {
        UnweightedEdge edge1 = new UnweightedEdge(0, 1);
        UnweightedEdge edge2 = new UnweightedEdge(0, 1);

        graph.addEdge(edge1);
        graph.addEdge(edge2);

        LinkedList<Edge> edgesFrom0 = graph.getIncidentEdges(0);
        LinkedList<Edge> edgesFrom1 = graph.getIncidentEdges(1);

        assertEquals(1, edgesFrom0.size(), "Node 0 should have only 1 edge.");
        assertEquals(1, edgesFrom1.size(), "Node 1 should have only 1 edge.");
    }

    @Test
    void testAddInvalidEdge() {
        assertThrows(IllegalArgumentException.class, () -> {
            //invalid edge
            UnweightedEdge edge = new UnweightedEdge(0, 5);
            graph.addEdge(edge);
        }, "Should throw an exception for invalid edge.");
    }

    @Test
    void testDijkstraUndirectedUnweighted() {
        Graph undirectedGraph = new UndirectedGraph(6);
        undirectedGraph.addEdge(new UnweightedEdge(0, 1));
        undirectedGraph.addEdge(new UnweightedEdge(1, 5)); // Path 1: (0, 1, 5)

        undirectedGraph.addEdge(new UnweightedEdge(0, 2));
        undirectedGraph.addEdge(new UnweightedEdge(2, 3));
        undirectedGraph.addEdge(new UnweightedEdge(3, 5)); // Path 2: (0, 2, 3, 5)

        undirectedGraph.addEdge(new UnweightedEdge(0, 4));
        undirectedGraph.addEdge(new UnweightedEdge(4, 5)); // Path 3: (0, 4, 5)

        // Expected shortest path: 0, 1, 5
        LinkedList<Integer> path = undirectedGraph.dijkstra(0, 5);

        assertNotNull(path);
        assertEquals(3, path.size()); // Shortest path should have only 3 nodes
    }

    @Test
    void testEdgeExistsUndirectedUnweightedEdge() {
        Graph graph = new UndirectedGraph(5);

        UnweightedEdge edge1 = new UnweightedEdge(0, 1);
        UnweightedEdge edge2 = new UnweightedEdge(2, 3);

        graph.addEdge(edge1);
        graph.addEdge(edge2);

        assertTrue(graph.edgeExists(edge1));
        assertTrue(graph.edgeExists(edge2));
    }

}



