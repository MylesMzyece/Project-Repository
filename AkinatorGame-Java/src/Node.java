public class Node <T> {

    //chnage it to protected class node, not public

    private T data;
    private Node<T> left;
    private Node<T> right;

    public Node(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    public T getData() {
        return data;
    }

    public Node<T> getLeft() {
        return left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }
    public void setData(Node<T> node, T data) {
        node.data = data;
    }
    //deletes just the data from a node, sets node data to null
    public void deleteDataFromNode(Node <T> node){
        if(node.data!=null) node.data= null;
    }
    //tells if node is a leaf by checking if its children are null
    public boolean isLeaf(Node<T> node){
        return node.left==null && node.right==null;
    }

    // is leaf method
}
