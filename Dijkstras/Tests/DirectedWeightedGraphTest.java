    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;

    import static org.junit.jupiter.api.Assertions.*;

    import java.util.LinkedList;
    public class DirectedWeightedGraphTest {
        private DirectedGraph graph;


        @BeforeEach
        void setUp() {
            graph = new DirectedGraph(5);
        }


        // Test adding an edge with a negative weight
        //Note: should this be allowed or no????
        @Test
        void testNegativeWeightEdge() {
            WeightedEdge edge = new WeightedEdge(0, 1, -5);
            graph.addEdge(edge);

            LinkedList<Edge> edgesFrom0 = graph.getIncidentEdges(0);
            assertEquals(1, edgesFrom0.size(), "Node 0 should have 1 edge.");
            assertTrue(edgesFrom0.contains(edge), "Edge with negative weight should be in the list.");
        }

        // Stress test
        @Test
        void testStressAddEdgeWeights() {
            int nodeCount = 1000;
            DirectedGraph largeGraph = new DirectedGraph(nodeCount);

            int weightTotal=0;

            // Add a large number of edges
            for (int i = 0; i < nodeCount - 1; i++) {
                largeGraph.addEdge(new WeightedEdge(i, i + 1, i));
                weightTotal+=i;
            }

            for (int i = 0; i < nodeCount - 1; i++) {
                LinkedList<Edge> edgesFromI = largeGraph.getIncidentEdges(i);
                assertEquals(1, edgesFromI.size(), "Node " + i + " should have 1 edge.");
            }

            // Check last node
            LinkedList<Edge> edgesFromLastNode = largeGraph.getIncidentEdges(nodeCount - 1);
            //check weight total
            assertEquals(498501, weightTotal, "Total weight should be 500500.");
            assertEquals(0, edgesFromLastNode.size(), "Last node should have no edges.");
        }


        // Test adding an edge with zero weight
        @Test
        void testZeroWeightEdge() {
            WeightedEdge edge = new WeightedEdge(0, 1, 0);
            graph.addEdge(edge);

            LinkedList<Edge> edgesFrom0 = graph.getIncidentEdges(0);
            assertEquals(1, edgesFrom0.size(), "Node 0 should have 1 edge");
            assertTrue(edgesFrom0.contains(edge), "Edge with no weight should be in the list");
        }

        @Test
        void testDijkstraDirectedWeighted() {
            Graph directedGraph = new DirectedGraph(6);

            directedGraph.addEdge(new WeightedEdge(0, 1, 6));
        directedGraph.addEdge(new WeightedEdge(1, 5, 2));
        directedGraph.addEdge(new WeightedEdge(0, 2, 1));
        directedGraph.addEdge(new WeightedEdge(2, 3, 2));
        directedGraph.addEdge(new WeightedEdge(3, 5, 3));
        directedGraph.addEdge(new WeightedEdge(0, 4, 4));
        directedGraph.addEdge(new WeightedEdge(4, 5, 2));

        LinkedList<Integer> path = directedGraph.dijkstra(0, 5);

        assertNotNull(path);
        assertEquals(4, path.size());
    }

    @Test
    void testEdgeExistsDirectedWeightedEdge() {
        Graph graph = new DirectedGraph(5);

        WeightedEdge edge1 = new WeightedEdge(0, 1, 10);
        WeightedEdge edge2 = new WeightedEdge(1, 2, 5);

        graph.addEdge(edge1);
        graph.addEdge(edge2);

        assertTrue(graph.edgeExists(edge1));
        assertTrue(graph.edgeExists(edge2));
    }

}

