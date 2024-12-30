//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Runner {

    public Runner() {

    }
    public static boolean isPlaying = true;
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException {
        while(isPlaying) {
            // Create a tree and initialize it
            Tree tree = new Tree<>(new File("Akinator.txt"));
            Akinator akinator = new Akinator(tree, scanner);
            // Run the Akinator game
            akinator.run();
            playAgain();
        }
    }

    public static void playAgain() {
        System.out.println("Do you want to play again?");
        System.out.print("Answer (y/n): ");
        String answer = scanner.nextLine().trim().toLowerCase();
        if(answer.equals("y")) {isPlaying = true;}
        else if(answer.equals("n")) {isPlaying = false;}
    }
}