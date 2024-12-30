import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileNotFoundException;

public class CursorTest {

    @Test
    public void testInitialization() {
        Node<String> root = new Node<>("Root");
        Cursor<String> cursor = new Cursor<>(root);

        assertEquals("Root", cursor.current.getData(), "Cursor should start at the root.");
    }

    @Test
    public void testGoLeft() {
        Node<String> root = new Node<>("Root");
        Node<String> left = new Node<>("Left");
        root.setLeft(left);
        Cursor<String> cursor = new Cursor<>(root);

        cursor.goLeft();
        assertEquals("Left", cursor.current.getData(), "Cursor should move to the left child.");
    }

    @Test
    public void testGoRight() {
        Node<String> root = new Node<>("Root");
        Node<String> right = new Node<>("Right");
        root.setRight(right);
        Cursor<String> cursor = new Cursor<>(root);

        cursor.goRight();
        assertEquals("Right", cursor.current.getData(), "Cursor should move to the right child.");
    }

    @Test
    public void testSetLeftChild() {
        Node<String> root = new Node<>("Root");
        Cursor<String> cursor = new Cursor<>(root);

        cursor.setLeftChild("Left");
        assertNotNull(cursor.current.getLeft(), "Left child should be set.");
        assertEquals("Left", cursor.current.getLeft().getData(), "Left child data should match.");
    }

    @Test
    public void testSetRightChild() {
        Node<String> root = new Node<>("Root");
        Cursor<String> cursor = new Cursor<>(root);

        cursor.setRightChild("Right");
        assertNotNull(cursor.current.getRight(), "Right child should be set.");
        assertEquals("Right", cursor.current.getRight().getData(), "Right child data should match.");
    }

    @Test
    public void testReset() {
        Node<String> root = new Node<>("Root");
        Node<String> left = new Node<>("Left");
        root.setLeft(left);

        Cursor<String> cursor = new Cursor<>(root);
        cursor.goLeft(); // Move to the left child
        assertEquals("Left", cursor.current.getData(), "Cursor should be at the left child.");

        cursor.setPointer(root); // Reset to the root
        assertEquals("Root", cursor.current.getData(), "Cursor should reset to the root.");
    }

    @Test
    public void testGoLeftNoChild() {
        Node<String> root = new Node<>("Root");
        Cursor<String> cursor = new Cursor<>(root);

        Exception exception = assertThrows(IllegalStateException.class, cursor::goLeft);
        assertEquals("No left child to move to.", exception.getMessage());
    }

    @Test
    public void testGoRightNoChild() {
        Node<String> root = new Node<>("Root");
        Cursor<String> cursor = new Cursor<>(root);

        Exception exception = assertThrows(IllegalStateException.class, cursor::goRight);
        assertEquals("No right child to move to.", exception.getMessage());
    }

    @Test
    public void testSetLeftChildOverwrite() {
        Node<String> root = new Node<>("Root");
        Node<String> left = new Node<>("OldLeft");
        root.setLeft(left);

        Cursor<String> cursor = new Cursor<>(root);
        cursor.setLeftChild("NewLeft");

        assertEquals("NewLeft", cursor.current.getLeft().getData(), "Left child data should be overwritten.");
    }

    @Test
    public void testSetRightChildOverwrite() {
        Node<String> root = new Node<>("Root");
        Node<String> right = new Node<>("OldRight");
        root.setRight(right);

        Cursor<String> cursor = new Cursor<>(root);
        cursor.setRightChild("NewRight");

        assertEquals("NewRight", cursor.current.getRight().getData(), "Right child data should be overwritten.");
    }
    @Test
    public void testGoToParent() {
        Node<String> root = new Node<>("Root");
        Node<String> left = new Node<>("Left");
        root.setLeft(left);

        Tree<String> tree = new Tree<>(root); // Assuming Tree is implemented to find the parent.
        Cursor<String> cursor = new Cursor<>(root);

        cursor.goLeft();
        assertEquals("Left", cursor.current.getData(), "Cursor should move to the left child.");

        cursor.goToParent(tree);
        assertEquals("Root", cursor.current.getData(), "Cursor should move back to the parent.");
    }

    @Test
    void testGoToParentWithTreeTest3() throws FileNotFoundException {
        // Load the tree from the file
        Tree<String> tree = new Tree<>(new File("TreeTest3.txt"));
        Cursor<String> cursor = new Cursor<>(tree.getRootNode());

        // Start at the root
        assertEquals("50", cursor.current.getData());

        // Move to the left child (30)
        cursor.goLeft();
        assertEquals("30", cursor.current.getData());

        // Move back to the parent (50)
        cursor.goToParent(tree);
        assertEquals("50", cursor.current.getData());

        // Move to the right child (70)
        cursor.goRight();
        assertEquals("70", cursor.current.getData());

        // Move to the left child of 70 (60)
        cursor.goLeft();
        assertEquals("60", cursor.current.getData());

        // Move back to the parent (70)
        cursor.goToParent(tree);
        assertEquals("70", cursor.current.getData());

        // Move to the right child of 70 (80)
        cursor.goRight();
        assertEquals("80", cursor.current.getData());

        // Move to the left child of 80 (75)
        cursor.goLeft();
        assertEquals("75", cursor.current.getData());

        // Move back to the parent (80)
        cursor.goToParent(tree);
        assertEquals("80", cursor.current.getData());

        // Attempt to move to parent of root (should throw exception)
        cursor.setPointer(tree.getRootNode()); // Reset cursor to root
        Exception exception = assertThrows(IllegalStateException.class, () -> cursor.goToParent(tree));
        assertEquals("No parent to move to.", exception.getMessage(), "Exception should be thrown when no parent exists.");
    }


}
