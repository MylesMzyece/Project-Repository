import java.io.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Tree<T> {
    //initialize root
    private final Node<T> root;

    //make tree
    public Tree(Node<T> root) {
        this.root = root;
    }
    //Tree Constructor that reads from file and uses read method to populate tree
    public Tree(File file) throws FileNotFoundException {
        Scanner txt = new Scanner(file);
        Tree tree = new Tree(read(txt));
        this.root = tree.getRootNode();
    }

    //return the data inside the root of a tree
    public T getRootData() {
        return root.getData();
    }

    public Node<T> getRootNode(){
        return root;
    }

    //gives the height of the tree
    public int getHeight() {
        return heightRecursive(root);
    }

    //recursive method that returns the height max
    private int heightRecursive(Node<T> node) {
        if (node == null) return 0;
        int leftHeight = heightRecursive(node.getLeft());
        int rightHeight = heightRecursive(node.getRight());
        return Math.max(leftHeight, rightHeight) + 1;
    }

    //return the data in left node--child of parent node
    public T getLeftData() {
        if (root == null || root.getLeft() == null) return null;
        return root.getLeft().getData();
    }

    //return the data inside right node--child of parent node
    public T getRightData() {
        if (root == null || root.getRight() == null) return null;
        return root.getRight().getData();
    }

    //add data to the right, if there is no data in root, add data to root
    public void insertRight(T data) {
        if (root.getData() == null) {
            root.setData(root, data);
        } else {
            // If there is no right node, create it
            if (root.getRight() == null) {
                root.setRight(new Node<T>(data));
            } else {
                root.getRight().setData(root.getRight(), data);  // If right node exists, update its data
            }
        }
    }

    //add data to the left, if there is no data in root, add data to root
    public void insertLeft(T data) {
        if (root.getData() == null) {
            root.setData(root, data);
        } else {
            // If there is no right node, create it
            if (root.getLeft() == null) {
                root.setLeft(new Node<T>(data));
            } else {
                root.getLeft().setData(root.getLeft(), data);  // If right node exists, update its data
            }
        }
    }

    public Node<T> getParent(Node<T> target) {
        if (root == null || root == target) return null;
        return getParentRecursive(root, target);
    }

    private Node<T> getParentRecursive(Node<T> current, Node<T> target) {
        if (current == null) return null;
        if (current.getLeft() == target || current.getRight() == target) return current;
        Node<T> parent = getParentRecursive(current.getLeft(), target);
        if (parent != null) return parent;
        return getParentRecursive(current.getRight(), target);
    }

    //tells if a function contains a certain data point
    public boolean contains(T target) {
        return containsRecursive(this.root, target);
    }

    // recursive method to help contains
    private boolean containsRecursive(Node<T> current, T target) {
        if (current == null) return false;
        if (current.getData().equals(target)) {
            return true;
        }
        return containsRecursive(current.getLeft(), target) || containsRecursive(current.getRight(), target);
    }

    //recursive method used by constructor to uses scanner to read the tree from txt file
    private Node read(Scanner scanner) {
        String data = scanner.nextLine();
        if (data.equals("null")) {
            return null;
        }
        Node<String> n = new Node<>(data);
        n.setLeft(read(scanner));
        n.setRight(read(scanner));
        return n;
    }

    //actual method that takes current tree and saves it to txt file
    //kevin helped to teach me about printWriter and bufferedWriter
    //takes in root node
    public void saveTree(Node node, File file) { //have file path be provided and write to it
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            write(writer, node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

   //recursive method used to write Trees to txt file
    private void write(PrintWriter writer, Node node) {
        if (node == null) {
           writer.println("null");
           return;
        }
        String data = (String) node.getData();
        writer.println(data);
        write(writer, node.getLeft());
        write(writer, node.getRight());
    }

    public Cursor<T> getCursor() {
        return new Cursor<>(root);
    }

}
