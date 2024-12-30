import acm.program.ConsoleProgram;
import acm.util.RandomGenerator;

import java.sql.Array;
import java.util.ArrayList;
import java.util.logging.Handler;

public class Solitaire extends ConsoleProgram {
    Deck gameDeck = new Deck();


    public ArrayList<Hand> piles;
    public ArrayList<Hand> foundationPiles;

    // Initialize Hand objects

    Hand mainPile = new Hand();
    Hand threeStack = new Hand();
    Hand heartPile = new Hand();
    Hand diamondPile= new Hand();
    Hand clubPile = new Hand();
    Hand spadePile = new Hand();
    Hand selectedCards = new Hand();

    int moves = 0;
    int userInput;

    Card tempCard;
    boolean pilesEmpty;
    private RandomGenerator rgen = new RandomGenerator();


    public void run(){
        piles = new ArrayList<Hand>();
        foundationPiles = new ArrayList<Hand>();

        setUpGame();
        listInstructions();
        playGame();


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

    public void makePiles() {
        // Initialize and populate numbered piles
        for (int i = 1; i <= 7; i++) {
            Hand pile = new Hand();
            for (int j = 0; j < i; j++) {
                pile.add(gameDeck.deal());
            }
            piles.add(pile);
        }

        // Populate mainPile with remaining cards from gameDeck
        while (!gameDeck.deckIsEmpty()) {
            mainPile.add(gameDeck.deal());
        }

        // Add special piles
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
        printFoundationPile();
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
        if (userInput >= 15){
            return true;
        }
        return false;
    }

    public void playGame() {
        while (isPlaying()) {
            userInput = getInput();
            processUserMove(userInput);
            printGameStatus();
        }
    }

    private boolean isPlaying() {
        return heartPile.size() != 13 && clubPile.size() != 13 && spadePile.size() != 13 && diamondPile.size() != 13;
    }

    private int getInput() {
        int userInput = readInt("Enter the number of the card pile which you would like to pick from: ");
        println("");
        return userInput;
    }

    private boolean tableauMoveIsValid(int userInput){
        if (tableauPlayed(userInput)
                && piles.get(userInput - 1).handIsEmpty() == false
                && !piles.get(userInput - 1).getCard(piles.get(userInput - 1).size() - 1).getColour().equals(tempCard.getColour())
                && piles.get(userInput - 1).getCard(piles.get(userInput - 1).size() - 1).getValue() == tempCard.getValue() + 1)
        {
            return true;
        }
        return false;
    }

    private boolean foundationPileIsEmpty(int userInput){
        if (foundationPilePlayed(userInput) && piles.get(userInput - 1).handIsEmpty()){
            return true;
        }
        return false;
    }

    private boolean foundationPileIsntEmpty(int userInput){
        if (foundationPilePlayed(userInput) && !piles.get(userInput - 1).handIsEmpty()){
            return true;
        }
        return false;
    }

    private boolean tableauIsEmpty(int userInput){
        if (piles.get(userInput-1).handIsEmpty() && tempCard.getValue() ==13){
            return true;
        }
        return false;
    }

    private void processUserMove(int userInput) {
        if (tableauPlayed(userInput) || wastePilePlayed(userInput) || foundationPilePlayed(userInput)) {
            preventPullEmpty(userInput);
            moves++;
        } else if (shuffleWastePilePlayed(userInput)) {
            shuffleWastePile(userInput);
        }

        /** First input is necessary for returning a player's card to its original position in the event that
         * the player makes an illegal card maneuver
         */

        int firstInput = userInput;

        if (!tableauPlayedSpecial(userInput)) {
            println(userInput);
            tempCard = piles.get(userInput - 1).dealTop();
        } else {
            handleSpecialMove(userInput);
        }
        userInput = readInt("Now select which pile you would like to move your card to: ");

        if (tableauMoveIsValid(userInput)){
            addToTableau(userInput);
        } else if (foundationPileIsEmpty(userInput)) {
            addToEmptyFoundation(userInput, firstInput);
        } else if (foundationPileIsntEmpty(userInput)) {
            addToFoundation(userInput, firstInput);
        } else if (tableauIsEmpty(userInput)) {
            addToEmptyTableau(userInput);
        } else {
            illegalMove(firstInput);
        }
    }

    public void addToTableau(int userInput){
        piles.get(userInput - 1).add(tempCard);
        moves++;
    }

    public void addToFoundation (int userInput, int firstInput){
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

    public void addToEmptyFoundation(int userInput, int firstInput){
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
    }

    public void addToEmptyTableau(int userInput){
        piles.get(userInput - 1).add(tempCard);
        moves++;
    }

    public void illegalMove (int firstInput){
        piles.get(firstInput-1).add(tempCard);
        println("Illegal card maneuver.");
        println("Please select different cards.");
        moves++;
    }

    private void preventPullEmpty(int userInput) {
        while (piles.get(userInput - 1).handIsEmpty()) {
            userInput = readInt("Enter the number of a card pile with cards in it: ");
        }
    }


    private void shuffleWastePile(int userInput) {
        while (shuffleWastePilePlayed(this.userInput)) {
            if (threeStack.handIsEmpty() == true && mainPile.handIsEmpty() == false) {
                threeStack.add(mainPile.dealTop());
            } else if (mainPile.handIsEmpty() == false) {
                tempCard = threeStack.dealTop();
                mainPile.addAt(tempCard, 0);
                threeStack.add(mainPile.dealTop());
                println("Main Pile: "+mainPile.toString());
                println("");
            } else {
                println("The main pile is empty.");
            }
            printGameStatus();
            this.userInput = readInt("Enter the number of the card pile which you would like to pick from: ");
            println("");
        }
        moves++;
    }

    private void handleSpecialMove(int userInput) {
        int numCards;
        userInput = readInt("This option allows you to select how many cards to move around. Pick a pile first: ");

        while (piles.get(userInput - 1).handIsEmpty() == true) {
            userInput = readInt("Enter the number of a card pile with cards in it (Pick from pile 1-7): ");
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
        moves++;
    }

    private void printGameStatus() {
        printPiles();
        println("");
        printFoundationPile();
        println("");
        printWastePile();
        println("");
    }

}
