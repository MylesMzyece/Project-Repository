import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @org.junit.jupiter.api.Test
    void getData() {
        Integer data = 10;
        Node<Integer> node = new Node<>(data);
        Integer result = node.getData();
        assertEquals(data, result);
    }

    @org.junit.jupiter.api.Test
    void getLeft() {
        Integer data = 10;
        Node<Integer> node = new Node<>(data);
        Node<Integer> leftChild = new Node<>(5);
        node.setLeft(leftChild);
        Node<Integer> result = node.getLeft();
        assertEquals(leftChild, result);
    }

    @org.junit.jupiter.api.Test
    void getRight() {
        Integer data = 10;
        Node<Integer> node = new Node<>(data);
        Node<Integer> rightChild = new Node<>(15);
        node.setRight(rightChild);
        Node<Integer> result = node.getRight();
        assertEquals(rightChild, result);
    }

    @org.junit.jupiter.api.Test
    void setLeft() {
        Integer data = 10;
        Node<Integer> node = new Node<>(data);
        Node<Integer> leftChild = new Node<>(5);
        node.setLeft(leftChild);
        assertEquals(leftChild, node.getLeft());
    }

    @org.junit.jupiter.api.Test
    void setRight() {
        Integer data = 10;
        Node<Integer> node = new Node<>(data);
        Node<Integer> rightChild = new Node<>(15);
        node.setRight(rightChild);
        assertEquals(rightChild, node.getRight());
    }
    @org.junit.jupiter.api.Test
    void setLeftWithSelf() {
        Node<Integer> selfNode = new Node<>(20);
        // Setting a node's left child to itself should ideally be prevented
        selfNode.setLeft(selfNode);
        assertEquals(selfNode, selfNode.getLeft());  // This is an undesirable state
        assertNotNull(selfNode.getLeft());  // Assert false: the left child is set incorrectly
    }
    @org.junit.jupiter.api.Test
    void largeNodeChain() {
        int size = 1000;
        Node<Integer> root = new Node<>(0);
        Node<Integer> current = root;
        // Build a long chain (linear tree)
        for (int i = 1; i < size; i++) {
            Node<Integer> newNode = new Node<>((i));
            current.setRight(newNode);
            current = newNode;
        }
        // Traverse and check all nodes up to size
        current = root;
        for (int i = 0; i < size; i++) {
            assertNotNull(current);  // Assert the node exists
            assertEquals(i, current.getData());  // Assert data is correct
            if (i < size - 1) {
                assertNotNull(current.getRight());  // Ensure right child exists except at last node
            }
            current = current.getRight();
        }
    }


}