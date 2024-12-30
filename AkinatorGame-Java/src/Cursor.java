public class Cursor<T> {
    protected Node<T> current;// Keeps track of the current position in the tree

    // Constructor: Start the cursor at the root of the tree
    public Cursor(Node<T> root) {
        this.current = root;
    }
    // Move the cursor to the left child
    public void goLeft() {
        if (current.getLeft() != null) {
            current = current.getLeft();
        } else {
            throw new IllegalStateException("No left child to move to.");
        }
    }
    // Move the cursor to the right child
    public void goRight() {
        if (current.getRight() != null) {
            current = current.getRight();
        } else {
            throw new IllegalStateException("No right child to move to.");
        }
    }
    // Move the cursor to the parent
    public void goToParent(Tree<T> tree) {
        if (tree.getParent(current) != null) {
            current = tree.getParent(current);
        } else {
            throw new IllegalStateException("No parent to move to.");
        }
    }
    // Set the right child of the current node
    public void setRightChild(T data) {
        if (current.getRight() == null) {
            current.setRight(new Node<>(data));
        } else {
            current.getRight().setData(current.getRight(), data);
        }
    }
    // Set the left child of the current node
    public void setLeftChild(T data) {
        if (current.getLeft() == null) {
            current.setLeft(new Node<>(data));
        } else {
            current.getLeft().setData(current.getLeft(), data);
        }
    }
    public void setPointer(Node<T> root) {
        this.current = root;
    }

}
