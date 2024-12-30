import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {

    private Tree<String> tree;
    private Node<String> root;
    private Node<String> leftChild;
    private Node<String> rightChild;

    void initTestTree(){
        //make root and child nodes
        root = new Node<>("Root");
        leftChild = new Node<>("Left");
        rightChild = new Node<>("Right");
        //set child nodes
        root.setLeft(leftChild);
        root.setRight(rightChild);

        //create new tree
        tree = new Tree<>(root);
    }

    @org.junit.jupiter.api.Test
    void getRootTest() {
        initTestTree();
        String rootData = tree.getRootData();
        //make sure root data is not null
        assertNotNull(rootData);
        //make sure root holds string root
        assertEquals("Root", rootData);
    }
//move to node, make it return data
    @org.junit.jupiter.api.Test
    void getLeftDataTest() {
        initTestTree();
        String leftData = tree.getLeftData();
        //make sure left data is not null
        assertNotNull(leftData);
        //make sure left holds string left
        assertEquals("Left", leftData);
    }

//move to node, make it return data
    @org.junit.jupiter.api.Test
    void getRightDataTest() {
        initTestTree();
        String rightData = tree.getRightData();
        //make sure right data is not null
        assertNotNull(rightData);
        //make sure right holds string right
        assertEquals("Right", rightData);
    }
    @org.junit.jupiter.api.Test
    void insertRightTest() {
        initTestTree();
        tree.insertRight("New Right");
        String rightData = tree.getRightData();
        assertNotNull(rightData);
        assertEquals("New Right", rightData);
    }
    @org.junit.jupiter.api.Test
    void insertLeftTest() {
        initTestTree();
        tree.insertLeft("New Left");
        String leftData = tree.getLeftData();
        assertNotNull(leftData);
        assertEquals("New Left", leftData);
    }

    //recursive method to iterate through tree and compare values w/ .txt file
    public boolean recurse (Scanner scan, Node node){
        String txtData = scan.nextLine();
        System.out.println("Txt: " + txtData);
        if(node == null && txtData.equals("null")){
            return true;
        }
        String treeData = (String) node.getData();
        System.out.println("tree: " + treeData);
        if (treeData.equals(txtData)) {
            assertEquals(true, recurse(scan, node.getLeft()));
            assertEquals(true,recurse(scan, node.getRight()));
            return true;
        }
        else{
            return false;
        }
    }
    //Simple read Tree test
    @org.junit.jupiter.api.Test
    void testReadTree() throws FileNotFoundException {
        Tree test = new Tree <>(new File("TreeTest.txt"));
        String num = new String ((String) test.getLeftData());
        String num2 = new String ((String) test.getRightData());
        assertEquals("8", num2);
        assertEquals("3", num);
    }
    //Simple read Tree test
    @org.junit.jupiter.api.Test
    void testReadTree2()throws FileNotFoundException{
        Tree test = new Tree <>(new File("TreeTest.txt"));
        Scanner scan = new Scanner (new File("TreeTest.txt"));
        Boolean check = recurse(scan, test.getRootNode());
        assertEquals(true, check);
    }
    //basic recursive test to read a more complicated Tree
    @org.junit.jupiter.api.Test
    void testReadTree3()throws FileNotFoundException{
        Tree test = new Tree <>(new File("TreeTest3.txt"));
        Scanner scan = new Scanner (new File("TreeTest3.txt"));
        Boolean check = recurse(scan, test.getRootNode());
        assertEquals(true, check);
    }
    //basic recursive test to read a Left-heavy file
    @org.junit.jupiter.api.Test
    void testReadTreeLeft()throws FileNotFoundException{
        Tree test = new Tree <>(new File("TreeTestLeft.txt"));
        Scanner scan = new Scanner (new File("TreeTestLeft.txt"));
        Boolean check = recurse(scan, test.getRootNode());
        assertEquals(true, check);
    }
    //basic recursive test to read a right-heavy file
    @org.junit.jupiter.api.Test
    void testReadTreeRight()throws FileNotFoundException{
        Tree test = new Tree <>(new File("TreeTestRight.txt"));
        Scanner scan = new Scanner (new File("TreeTestRight.txt"));
        Boolean check = recurse(scan, test.getRootNode());
        assertEquals(true, check);
    }

    //Basic test that adds a node and overwrites original .txt file and checks file in end
    // manipulates original tree and saves it
    @org.junit.jupiter.api.Test
    void testWriteTree() throws FileNotFoundException {
        Tree test = new Tree <>(new File("TreeTest2.txt"));
        Node testNum = new Node<>("3");
        test.getRootNode().setLeft(testNum);
        test.saveTree(test.getRootNode(), new File("TreeTest2.txt"));
        Tree test2 = new Tree <>(new File("TreeTest2.txt"));
        String num = new String ((String) test2.getLeftData());
        assertEquals("3", num);
    }

    //Basic test that reads in a .txt tree and overwrites it with same tree and checks roots, no manipulation
    @org.junit.jupiter.api.Test
    void testWriteTree2() throws FileNotFoundException {
        initTestTree();
        tree.saveTree(tree.getRootNode(), new File("TreeTest2.txt"));
        Tree test2 = new Tree <>(new File("TreeTest2.txt"));
        String leftData = test2.getLeftData().toString();
        String rightData = test2.getRightData().toString();
        assertNotNull(leftData);
        assertEquals("Left", leftData);
        assertNotNull(leftData);
        assertEquals("Right", rightData);
    }

    //reads in tree from text file, writes tree to same file and creates a new tree and compares the new tree
    //to .txt and the old file to .txt, no manipulation
    @org.junit.jupiter.api.Test
    void testWriteTree3() throws FileNotFoundException {
        Tree test = new Tree <>(new File("TreeTest3.txt"));
        test.saveTree(test.getRootNode(), new File("TreeTest3.txt"));
        Tree test2 = new Tree <>(new File("TreeTest3.txt"));
        String leftData = test2.getLeftData().toString();

        Scanner scan = new Scanner (new File("TreeTest3.txt"));
        Boolean check = recurse(scan, test2.getRootNode());
        assertEquals(true, check);

        Scanner scan2 = new Scanner (new File("TreeTest3.txt"));
        Boolean check2 = recurse(scan2, test.getRootNode());
        assertEquals(true, check2);

    }

    //reads in tree from text file, adds to tree, writes tree to same file and creates a new tree and compares the new tree
    //to .txt and the old file to .txt, uses manipulation
    @org.junit.jupiter.api.Test
    void testWriteTree4() throws FileNotFoundException {
        Tree test = new Tree <>(new File("TreeTest4.txt"));
        Node testNum = new Node<>("3");
        test.getRootNode().setRight(testNum);
        test.saveTree(test.getRootNode(), new File("TreeTest4.txt"));

        Tree test2 = new Tree <>(new File("TreeTest4.txt"));

        Scanner scan = new Scanner (new File("TreeTest4.txt"));
        Boolean check = recurse(scan, test2.getRootNode());

        assertEquals(true, check);
    }
    @org.junit.jupiter.api.Test
    void containsTest() {
        initTestTree();
        //checks that tree contains data "left"
        assertTrue(tree.contains("Left"));
    }
    //test that the height of test tree is 2
    @org.junit.jupiter.api.Test
    void getHeightTest() {
        initTestTree();
        int height = tree.getHeight();
        assertEquals(2, height);
    }
    //test that getParent returns the parent of the left node, or root in this case
    @org.junit.jupiter.api.Test
    void getParentTest() {
        initTestTree();
        Node <String> current = root.getLeft();
        assertEquals(root, tree.getParent(current));
    }

}

