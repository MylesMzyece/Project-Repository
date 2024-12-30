import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Akinator {

    private final Tree<String> questionTree;
    private final Cursor<String> cursor;
    private final Scanner scanner;


    // Constructor
    public Akinator(Tree<String> tree, Scanner scanner) {
        this.questionTree = tree;
        this.cursor = new Cursor<>(tree.getRootNode());
        this.scanner = scanner;
    }

    // Method to initialize the test tree
    public void initTestTree() {
        Node<String> root = questionTree.getRootNode();
        root.setData(root, ("Is it an animal?"));

        root.setLeft(new Node<>("Is it alive?"));
        root.setRight(new Node<>("Is it furry?"));

        root.getLeft().setLeft(new Node<>("Rock?"));
        root.getLeft().setRight(new Node<>("Tree?"));

        root.getRight().setLeft(new Node<>("Lizard?"));
        root.getRight().setRight(new Node<>("Dog?"));
    }


    // run the Akinator
    public void run() {
        questionTree.saveTree(questionTree.getRootNode(), new File("Akinator.txt"));
        System.out.println("Welcome to Akinator!");
        while (true) {
            System.out.println(cursor.current.getData());
            if (cursor.current.getLeft() == null && cursor.current.getRight() == null) {
                handleLeafNode();
                break;
            }

            moveCursorBasedOnAnswer();
        }
        questionTree.saveTree(questionTree.getRootNode(), new File("Akinator.txt"));
    }

    // Handle behavior when the cursor is at a leaf node
    public void handleLeafNode() {

        System.out.println("Am I correct?");
        System.out.print("Answer (y/n): ");
        String correct = scanner.nextLine().trim().toLowerCase();

        if (correct.equals("y")) {
            System.out.println("Huzzah!");
        } else if (correct.equals("n")) {
            akinatorLoses(cursor.current.getData());
        }
    }

    // Move the cursor based on the user's input
    public void moveCursorBasedOnAnswer() {
        System.out.print("Answer (y/n): ");
        String answer = scanner.nextLine().trim().toLowerCase();

        if (answer.equals("n")) {
            cursor.goLeft();
        } else if (answer.equals("y")) {
            cursor.goRight();
        } else {
            System.out.println("Invalid input. Please answer with 'y' or 'n'.");
        }
    }

    //case if akinator's guess is wrong
    public void akinatorLoses(String originalAnswer) {
        //ask q to differentiate
        System.out.print("Enter a new question to help me differentiate between your object and my guess: ");
        String newQuestion = scanner.nextLine().trim();
        //set new question as the data for the current node
        String data = cursor.current.getData();//original guess
        cursor.current.setData(cursor.current, newQuestion);//set new question at current node

        //ask for answer to question
        System.out.print("For the object I guessed: " + originalAnswer.substring(0, originalAnswer.length()-1 ) + ". Is the answer to the new question: " + newQuestion + " yes or no(y/n): ");
        String newYN = scanner.nextLine().trim().toLowerCase();

        if (newYN.equals("y")) {
            cursor.setRightChild(originalAnswer);
            System.out.print("You said yes, please type an example object for no (all lowercase)");
            String noAnswer = scanner.nextLine().trim().toLowerCase();
            cursor.setLeftChild(noAnswer+"?");
        } else if (newYN.equals("n")) {
            cursor.setLeftChild(originalAnswer);
            System.out.print("You said no, please type an example object for yes: ");
            String yesAnswer = scanner.nextLine().trim().toLowerCase();
            cursor.setRightChild(yesAnswer+"?");
            questionTree.saveTree(questionTree.getRootNode(), new File("Akinator.txt"));
        }
    }
}







