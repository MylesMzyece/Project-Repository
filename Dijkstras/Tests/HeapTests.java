import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

public class HeapTests {
    private Heap heap;

    // Create a new heap before each test
    @BeforeEach
    void setUp() {
        heap = new Heap(10);
    }

    @Test
    void testInsert() { //issue is w/ percolate now
        heap.insert(5, 5);
        heap.insert(10, 10);
        heap.insert(3, 3);
        // The heap should have 3 elements now, and the min should be 3
        assertEquals(3, heap.getMinWeight(), "Min number should be 3 after inserting 5, 10, and 3.");
        assertEquals(3, heap.getMinData(), "Min number should be 3 after inserting 5, 10, and 3.");

    }

    @Test
    void testInsertData() { //issue is w/ percolate now
        heap.insert("eee", 1);
        heap.insert("6", 2);
        heap.insert("!", 3);
        heap.insert("", 4);
        heap.insert(" ", 5);
        heap.insert(100, 5);

        ArrayList<Integer> testArrayList = new ArrayList<Integer>();
        testArrayList.add(2);
        testArrayList.add(5);
        testArrayList.add(3);

        heap.insert(testArrayList, 5);


        // The heap should have 3 elements now, and the min should be 3
        assertEquals("eee", heap.getData(0), "Data should be equal");
        assertEquals("6", heap.getData(1), "Data should be equal");
        assertEquals("!", heap.getData(2), "Data should be equal");
        assertEquals("", heap.getData(3), "Data should be equal");
        assertEquals(" ", heap.getData(4), "Data should be equal");
        assertEquals(100, heap.getData(5), "Data should be equal");
        assertEquals(testArrayList, heap.getData(6), "Data should be equal");

        assertEquals("eee", heap.getMinData(), "Min data should be eee");

        heap.extractMin();

        assertEquals("6", heap.getMinData(), "Min data should be 6");
    }

    @Test
    void testGetMinData() {
        heap.insert(7, 7);
        heap.insert(2, 2);
        heap.insert(5, 5);

        // The minimum should be 2
        assertEquals(2, heap.getMinData(), "Min number should be 2");
    }

    @Test
    void testGetMinWeight() {
        heap.insert(1, 1);
        heap.insert(2, 2);
        heap.insert(3, 3);

        // Since no weights are set
        // the minimum weight should be 1 (associated with value 1)
        assertEquals(1, heap.getMinWeight(), "Min weight should be 1.");
    }

    @Test
    void testHeapify() {
        // Insert values to trigger heapify process
        heap.insert(10, 10);
        heap.insert(20, 10);
        heap.insert(5, 5);
        heap.insert(30, 30);

        // After heapifying, the min value should be 5
        assertEquals(5, heap.getMinWeight(), "Heap should have 5 as the minimum.");
        assertEquals(5, heap.getMinData(), "Heap should have 5 as the minimum.");
    }

    @Test
    void testIsLeaf() {
        heap.insert(4, 4);
        heap.insert(8, 8);
        heap.insert(3, 3);
        heap.insert(9, 9);

        // Check if the last node is a leaf
        assertTrue(heap.isLeaf(3), "Node at index 3 should be a leaf.");
        assertFalse(heap.isLeaf(0), "Node at index 0 should not be a leaf.");
    }

    @Test
    void testEdgeCaseHeap() {
        // Test a single element in the heap
        heap.insert(42, 42);
        assertEquals(42, heap.getMinWeight(), "Heap with one element should have that element as min.");
    }

    //what happens if you insert too many
    @Test
    void testInsertBeyondCapacity() {
        Heap smallHeap = new Heap(3);

        // Inserting more elements than capacity
        smallHeap.insert(4, 4);
        smallHeap.insert(2, 2);
        smallHeap.insert(3, 3);
        smallHeap.insert(1, 1);

        assertEquals(1, smallHeap.getMinWeight(), "Original Capacity was 3, new min should be 1 after inserting 4th element");
        assertEquals(2, smallHeap.getLeftChild(0), "");
        assertEquals(3, smallHeap.getRightChild(0), "");
    }

    @Test
    void testGetParent() {
        heap.insert(10, 10);
        heap.insert(5, 5);
        heap.insert(7, 7);
        heap.insert(3, 3);

        // The parent of index 3, node with value 7, should be the node with value 10,index 0
        assertEquals(5, heap.getParent(3), "Parent of node at index 3 should be 10.");
    }

    @Test
    void testGetLeftChildAndRightChild() {
        heap.insert(10, 10);
        heap.insert(5, 5);
        heap.insert(7, 7);
        heap.insert(3, 3);


        // The left child of index 0 (node with value 10) should be 5
        assertEquals(5, heap.getLeftChild(0), "Left child of node at index 0 should be 5.");

        // The right child of index 0 (node with value 10) should be 7
        assertEquals(7, heap.getRightChild(0), "Right child of node at index 0 should be 7.");
    }


    //the extract min method utilizes the heapify function, so this test also tests heapify
    @Test
    void testExtractMin() {
        heap.insert(10, 10);
        heap.insert(5, 5);
        heap.insert(7, 7);
        heap.insert(3, 3);
        heap.insert(1, 1);
        heap.insert(25, 25);
        heap.insert(2, 2);
        heap.insert(17, 17);

        heap.extractMin();

        assertEquals(2, heap.getWeight(0), "");
        assertEquals(3, heap.getWeight(1), "");
        assertEquals(7, heap.getWeight(2), "");
        assertEquals(10, heap.getWeight(3), "");
        assertEquals(5, heap.getWeight(4), "");
        assertEquals(25, heap.getWeight(5), "");
        assertEquals(17, heap.getWeight(6), "");


        assertEquals(2, heap.getMinWeight(), "New Root should be 5 after extracting 3");
        assertEquals(3, heap.getLeftChild(0), "");
        assertEquals(7, heap.getRightChild(0), "");
    }

    @Test
    void testExtractMinEdgecase() {
        heap.insert(1, 1);

        heap.extractMin();

        assertEquals(0, heap.getMinWeight(), "New Root should be 5 after extracting 3");
        assertEquals(0, heap.getLeftChild(0), "");
        assertEquals(0, heap.getLeftChild(0), "");

        heap.insert(3, 3);
        assertEquals(3, heap.getMinWeight(), "New Root should be 5 after extracting 3");
    }

    @Test
    void insertBeyondCapacity() {
        heap.insert(10, 10);
        heap.insert(5, 5);
        heap.insert(7, 7);
        heap.insert(3, 3);
        heap.insert(1, 1);
        heap.insert(25, 25);
        heap.insert(2, 2);
        heap.insert(17, 17);
        heap.insert(3, 25);
        heap.insert(3, 26);
        heap.insert(3, 100);
        heap.insert(3, 100);
        heap.insert(3, 100);

        assertEquals(26, heap.getWeight(9), "Final Weight should be 26 because Heap Array has been fully filled");
    }

    @Test
    void addEqualValues() {
        heap.insert(1, 1);
        heap.insert(5, 5);
        heap.insert(10, 10);
        heap.insert(7, 7);
        heap.insert(1, 1);
        heap.insert(1, 1);
        heap.insert(1, 1);

        assertEquals(1, heap.getMinWeight(), "Final Weight should be 26 because Heap Array has been fully filled");
        assertEquals(1, heap.getLeftChild(0), "Final Weight should be 26 because Heap Array has been fully filled");
        assertEquals(1, heap.getRightChild(0), "Final Weight should be 26 because Heap Array has been fully filled");

        assertEquals(1, heap.getWeight(0), "");
        assertEquals(1, heap.getWeight(1), "");
        assertEquals(1, heap.getWeight(2), "");
        assertEquals(7, heap.getWeight(3), "");
        assertEquals(5, heap.getWeight(4), "");
        assertEquals(10, heap.getWeight(5), "");
        assertEquals(1, heap.getWeight(6), "");

        heap.extractMin();
        assertEquals(1, heap.getMinWeight(), "Final Weight should be 26 because Heap Array has been fully filled");
    }

    @Test
    void stress() {
        for (int i = 0; i < 10000; i++) {
            heap.insert(i, (int) (Math.random() * 100000));
        }
        int prev = heap.getMinWeight();
        for (int i = 0; i < 9999; i++) {
            heap.extractMin();
            assertTrue(prev <= heap.getMinWeight());
            prev = heap.getMinWeight();
        }

    }

    @Test
    void extractMinTest() {
        heap.insert(0, 1);
        heap.insert(1, 2);
        heap.insert(2, 3);
        heap.insert(3, 4);

        int prev = heap.getMinWeight();
        for (int i = 0; i < 3; i++) {
            heap.extractMin();
            assertTrue(prev <= heap.getMinWeight());
            prev = heap.getMinWeight();
        }
    }

    @Test
    void insertDecreasingPriority() {
        heap.insert(10, 10);
        heap.insert(8, 8);
        heap.insert(7, 7);
        heap.insert(3, 3);
        heap.insert(1, 1);

        assertEquals(1, heap.getMinWeight());

    }

    @Test
    void testInsertDuplicateWeights() {
        heap.insert(10, 5);
        heap.insert(20, 5);
        heap.insert(30, 5);
        heap.insert(40, 5);

        // The minimum weight is 5, so the min data should be the first inserted element with this weight, which is 10.
        assertEquals(5, heap.getMinWeight(), "The minimum weight should be 5.");
        assertEquals(10, heap.getMinData(), "The minimum data should be 10, as it's the first inserted with weight 5.");

        // Extract the minimum and check the behavior
        heap.extractMin();
        assertEquals(5, heap.getMinWeight(), "The minimum weight should still be 5 after extracting the first min.");
        assertEquals(40, heap.getMinData(), "The minimum data should be 40 after extracting 10.");
    }

    @Test
    void ReverseStress() {
        for (int i = 10000; i > 0; i--) {
            heap.insert(i, i);
        }
        for (int i = 1; i < 10000; i++) {
            assertEquals(i, heap.getMinWeight(), "");
            heap.extractMin();
        }
    }

    @Test
    void StressEqualValue() {
        for (int i = 0; i < 100; i++) {
            heap.insert(5, 5);
        }
        for (int i = 0; i < 100; i++) {
            heap.insert(33, 33);
        }
        for (int i = 0; i < 100; i++) {
            heap.insert(1, 1);
        }
        for (int i = 0; i < 100; i++) {
            heap.insert(20, 20);
        }
        for (int i = 0; i < 100; i++) {
            assertEquals(1, heap.getMinWeight(), "");
            assertEquals(1, heap.getMinData(), "");

            heap.extractMin();
        }
        for (int i = 0; i < 100; i++) {
            assertEquals(5, heap.getMinWeight(), "");
            assertEquals(5, heap.getMinData(), "");
            heap.extractMin();
        }
        for (int i = 0; i < 100; i++) {
            assertEquals(20, heap.getMinWeight(), "");
            assertEquals(20, heap.getMinData(), "");
            heap.extractMin();
        }
        for (int i = 0; i < 100; i++) {
            assertEquals(33, heap.getMinWeight(), "");
            assertEquals(33, heap.getMinData(), "");
            heap.extractMin();
        }
    }
    @Test
    void ForcePercolate() {
        for(int i = 100; i>0;i--){
            heap.insert(i,i);
            assertEquals(i, heap.getMinWeight(), "");
            assertEquals(i, heap.getMinData(), "");
        }
        for(int i = 1;i<100;i++){
            assertEquals(i, heap.getMinWeight(), "");
            assertEquals(i, heap.getMinWeight(), "");
            heap.extractMin();
            assertEquals(i+1, heap.getMinWeight(), "");
            assertEquals(i+1, heap.getMinWeight(), "");
        }
    }
    @Test
    void ExtractMinEmptyHeap() {
        //extractMin should return if the heap is empty
        heap.extractMin();
        assertEquals(0, heap.getMinWeight(), "");
        assertEquals(null, heap.getMinData(), "");
    }
    @Test
    void ForceHeapify() {
        heap.insert(55,55);
        heap.insert(3,3);
        heap.insert(1,1);
        assertEquals(1, heap.getMinData(), "");
        assertEquals(1, heap.getMinWeight(), "");
        heap.extractMin();
        assertEquals(3, heap.getMinData(), "");
        assertEquals(3, heap.getMinWeight(), "");
        heap.extractMin();
        assertEquals(55, heap.getMinData(), "");
        assertEquals(55, heap.getMinWeight(), "");
        heap.extractMin();
        assertEquals(0, heap.getMinWeight(), "");
        assertEquals(null, heap.getMinData(), "");
    }
    @Test
    void InsertZeros() {
        heap.insert(null,0);
        heap.insert(null,0);
        heap.insert(null,0);

        assertEquals(null, heap.getMinData(), "");
        assertEquals(0, heap.getMinWeight(), "");
        heap.extractMin();

        assertEquals(null, heap.getMinData(), "");
        assertEquals(0, heap.getMinWeight(), "");
        heap.extractMin();

        assertEquals(null, heap.getMinData(), "");
        assertEquals(0, heap.getMinWeight(), "");
        heap.extractMin();
    }
    @Test
    void InsertNullData() {
        heap.insert(null,55);
        heap.insert(null,77);

        assertEquals(null, heap.getMinData(), "");
        assertEquals(55, heap.getMinWeight(), "");
        heap.extractMin();

        assertEquals(null, heap.getMinData(), "");
        assertEquals(77, heap.getMinWeight(), "");
        heap.extractMin();

        assertEquals(null, heap.getMinData(), "");
        assertEquals(0, heap.getMinWeight(), "");
        heap.extractMin();
    }

}