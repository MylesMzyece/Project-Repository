import java.util.LinkedList;
import java.util.Scanner;

public class TextBasedRunner {
    public static int pointA;
    public static int pointB;
    public static Graph graph;//text reading has to make the graph

    public static void main(String[] args){

        Scanner scn = new Scanner(System.in);

        System.out.println("welcome to map finder");

        System.out.println("please enter a number for Point A: ");
        pointA=scn.nextInt();

        System.out.println("please enter a four digit X coordinate for Point B: ");
        pointB=scn.nextInt();

        System.out.println("Calculating Path...");
        LinkedList<Integer> path = graph.dijkstra(pointA, pointB);

        printPath(path);
    }
    public static void printPath(LinkedList path){
        for(int i = 0; i<path.size();i++){
            System.out.println(path.get(i));
        }
    }
}
