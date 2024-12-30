import acm.program.ConsoleProgram;
import acm.util.RandomGenerator;

import java.sql.Array;
import java.util.ArrayList;
import java.util.logging.Handler;

public class Game extends ConsoleProgram {
    Deck gameDeck = new Deck();


    public ArrayList<Hand> piles;
    public ArrayList<Hand> foundationPiles;

    // Initialize Hand objects

    Hand pile1 = new Hand();
    Hand pile2 = new Hand();
    Hand pile3 = new Hand();
    Hand pile4 = new Hand();
    Hand pile5 = new Hand();
    Hand pile6 = new Hand();
    Hand pile7 = new Hand();
    Hand mainPile = new Hand();
    Hand threeStack = new Hand();
    Hand heartPile = new Hand();
    Hand diamondPile= new Hand();
    Hand clubPile = new Hand();
    Hand spadePile = new Hand();
    Hand selectedCards = new Hand();

    int moves = 0;

    Card tempCard;
    boolean pilesEmpty;
    private RandomGenerator rgen = new RandomGenerator();


    public void run(){
        piles = new ArrayList<Hand>();
        foundationPiles = new ArrayList<Hand>();

        setUpGame();
        listInstructions();

        while (heartPile.size() != 13 && clubPile.size() != 13 && spadePile.size() != 13 && diamondPile.size() != 13) {
            playGame();
        }

        println("Congrats, you beat Solitaire.");
        println("It took you "+ moves + " moves.");

    }

    public void printPiles(){
        for (int i = 0; i < 7; i ++){
            println("Pile " + (i+1) + ": " + piles.get(i));
        }
    }

    public void printWastePile(){
        if (threeStack.handIsEmpty() == false) {
            println("Waste Pile: " + threeStack.getCard(threeStack.size() - 1).toString());
        }
    }

    public void printFoundationPile(){
        println("Hearts (Pile 9): " + heartPile.toString());
        println("Diamonds (Pile 10): " + diamondPile.toString());
        println("Spades (Pile 11): " + spadePile.toString());
        println("Clubs (Pile 12): " + clubPile.toString());

    }

    public void setUpGame(){

        gameDeck.shuffle();
        makePiles();


    }

    public void makePiles(){

        pile1.add(gameDeck.deal());

        for (int i = 0; i < 2; i++){
            pile2.add(gameDeck.deal());
        }

        for (int i = 0; i < 3; i++){
            pile3.add(gameDeck.deal());
        }

        for (int i = 0; i < 4; i++){
            pile4.add(gameDeck.deal());
        }

        for (int i = 0; i < 5; i++){
            pile5.add(gameDeck.deal());
        }

        for (int i = 0; i < 6; i++){
            pile6.add(gameDeck.deal());
        }

        for (int i = 0; i < 7; i++){
            pile7.add(gameDeck.deal());
        }

        while (gameDeck.size()> 0){
            mainPile.add(gameDeck.deal());
        }

        piles.add(pile1);
        piles.add(pile2);
        piles.add(pile3);
        piles.add(pile4);
        piles.add(pile5);
        piles.add(pile6);
        piles.add(pile7);
        piles.add(threeStack);
        piles.add(heartPile);
        piles.add(diamondPile);
        piles.add(spadePile);
        piles.add(clubPile);
    }

    public void listInstructions() {
        println("Are you ready for some Solitaire?!");
        println("If you would like to shuffle through the stock pile, enter the number 14.");
        println("If you would like to pick from the stock pile, enter the number 8.");
        printPiles();
    }

    private boolean tableauPlayed(int userInput){
        if (userInput <=7){
            return true;
        } return false;
    }

    private boolean wastePilePlayed(int userInput){
        if (userInput == 8){
            return true;
        } return false;
    }

    private boolean shuffleWastePilePlayed(int userInput){
        if (userInput == 14){
            return true;
        } return false;
    }

    private boolean foundationPilePlayed(int userInput){
        if (userInput >= 9 && userInput <13){
            return true;
        } return false;
    }

    private boolean tableauPlayedSpecial(int userInput){
        if (userInput >= 13){
            return true;
        }
        return false;
    }

    public void playGame(){
        while (heartPile.size() != 13 && clubPile.size() != 13 && spadePile.size() != 13 && diamondPile.size() != 13) {
            int userInput;
            Card tempCard;

            userInput = readInt("Enter the number of the card pile which you would like to pick from: ");
            println(tableauPlayed(userInput));
            println(wastePilePlayed(userInput));
            println(foundationPilePlayed(userInput));
            println(tableauPlayedSpecial(userInput));

            if (tableauPlayed(userInput) || wastePilePlayed(userInput) || foundationPilePlayed(userInput)) {
                while (piles.get(userInput - 1).handIsEmpty() == true) {
                    userInput = readInt("Enter the number of a card pile with cards in it: ");
                }
            } else if (shuffleWastePilePlayed(userInput)){
                while (shuffleWastePilePlayed(userInput)) {
                    if (threeStack.handIsEmpty() == true && mainPile.handIsEmpty() == false) {
                        threeStack.add(mainPile.dealTop());
                    } else if (mainPile.handIsEmpty() == false) {
                        tempCard = threeStack.dealTop();
                        mainPile.addAt(tempCard, 0);
                        threeStack.add(mainPile.dealTop());
                        println("Main Pile: "+mainPile.toString());
                    } else {
                        println("The main pile is empty.");
                    }
                    printWastePile();
                    userInput = readInt("Enter the number of the card pile which you would like to pick from: ");
                }
            }

            println("");
            println("Foundation Piles: ");
            printFoundationPile();

            int firstInput = userInput;

            if (tableauPlayed(userInput) || foundationPilePlayed(userInput) || wastePilePlayed(userInput) || shuffleWastePilePlayed(userInput)) {
                tempCard = piles.get(userInput - 1).dealTop();
            } else {
                int numCards;
                userInput = readInt("This option allows you to select how many cards to move around. Pick a pile first: ");

                while (piles.get(userInput - 1).handIsEmpty() == true) {
                    userInput = readInt("Enter the number of a card pile with cards in it (Pick from pile 1-7: ");
                }
                numCards = readInt("Now select how many cards you would like to pick: ");
                for (int i = 0; i<numCards; i++){
                    selectedCards.add(piles.get(userInput-1).dealTop());
                }
                userInput = readInt("Finally, enter where you would like to move the cards: ");

                if (piles.get(userInput-1).handIsEmpty() == false &&
                        tableauPlayed(userInput) &&
                        !piles.get(userInput-1).getCard(piles.get(userInput-1).size()-1).getColour().equals(selectedCards.getCard(selectedCards.size()-1).getColour()) &&
                        piles.get(userInput-1).getCard(piles.get(userInput-1).size()-1).getValue()-1 ==  selectedCards.getCard(selectedCards.size()-1).getValue()){
                    while (selectedCards.handIsEmpty() == false){
                        piles.get(userInput-1).add(selectedCards.dealTop());
                    }
                } else if (piles.get(userInput-1).handIsEmpty()==true &&
                        tableauPlayed(userInput) &&
                        selectedCards.getCard(selectedCards.size()-1).getValue() ==13) {
                    while (selectedCards.handIsEmpty() == false) {
                        piles.get(userInput - 1).add(selectedCards.dealTop());
                    }
                }
                printPiles();
                break;
            }

            userInput = readInt("Now select which pile you would like to move your card to: ");

            if (tableauPlayed(userInput)
                    && piles.get(userInput - 1).handIsEmpty() == false
                    && !piles.get(userInput - 1).getCard(piles.get(userInput - 1).size() - 1).getColour().equals(tempCard.getColour())
                    && piles.get(userInput - 1).getCard(piles.get(userInput - 1).size() - 1).getValue() == tempCard.getValue() + 1)
            {
                piles.get(userInput - 1).add(tempCard);
                moves++;
            } else if (foundationPilePlayed(userInput) && piles.get(userInput - 1).handIsEmpty()) {
                if (tempCard.getSuit().equals("Hearts") && tempCard.getValue() == 1) {
                    piles.get(8).add(tempCard);
                } else if (tempCard.getSuit().equals("Diamonds") && tempCard.getValue() == 1) {
                    piles.get(9).add(tempCard);
                } else if (tempCard.getSuit().equals("Spades") && tempCard.getValue() == 1) {
                    piles.get(10).add(tempCard);
                } else if (tempCard.getSuit().equals("Clubs") && tempCard.getValue() == 1) {
                    piles.get(userInput - 1).add(tempCard);
                } else {
                    piles.get(firstInput - 1).add(tempCard);
                    println("Illegal card maneuver.");
                    println("Please select different cards.");
                }
                moves++;
            } else if (foundationPilePlayed(userInput) && !piles.get(userInput - 1).handIsEmpty()) {
                if (tempCard.getSuit().equals("Hearts") && tempCard.getValue() - 1 == heartPile.getCard(heartPile.size() - 1).getValue()) {
                    piles.get(8).add(tempCard);
                } else if (tempCard.getSuit().equals("Diamonds") && tempCard.getValue() - 1 == diamondPile.getCard(diamondPile.size() - 1).getValue()) {
                    piles.get(9).add(tempCard);
                } else if (tempCard.getSuit().equals("Spades") && tempCard.getValue() - 1 == spadePile.getCard(spadePile.size() - 1).getValue()) {
                    piles.get(10).add(tempCard);
                } else if (tempCard.getSuit().equals("Clubs") && tempCard.getValue() - 1 == clubPile.getCard(clubPile.size() - 1).getValue()) {
                    piles.get(11).add(tempCard);
                } else {
                    piles.get(firstInput - 1).add(tempCard);
                    println("Illegal card maneuver.");
                    println("Please select different cards.");
                }
                moves++;
            }
            else if (piles.get(userInput-1).handIsEmpty() && tempCard.getValue() ==13) {
                piles.get(userInput - 1).add(tempCard);
                moves++;
            } else {
                piles.get(firstInput-1).add(tempCard);
                println("Illegal card maneuver.");
                println("Please select different cards.");
                moves++;
            }

            printPiles();
            println("");
            printFoundationPile();
            println("");
            printWastePile();
        }
    }
}
