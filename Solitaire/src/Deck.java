import acm.util.RandomGenerator;

import java.util.ArrayList;

public class Deck {

    public ArrayList<Card> cardDeck;

    private RandomGenerator rgen = new RandomGenerator();

    public Deck(){

        cardDeck = new ArrayList<Card>();

        for (int i = 1; i< 14; i++){
            cardDeck.add(new Card(i, "Diamonds", "Red"));
        }

        for (int i = 1; i < 14 ; i++){
            cardDeck.add( new Card(i, "Hearts", "Red"));
        }

        for (int i = 1; i < 14 ; i++){
            cardDeck.add(new Card (i, "Clubs", "Black"));
        }

        for (int i = 1; i < 14 ; i++){
            cardDeck.add(new Card(i, "Spades", "Black"));
        }
    }

    public int size(){
        return cardDeck.size();
    }

    public Card deal(){
        Card tempCard = cardDeck.get(0);
        cardDeck.remove(0);
        return tempCard;
    }

    public Card pick(int i){
        Card tempCard = cardDeck.get(i-1);
        cardDeck.remove(i-1);
        return tempCard;
    }

    public void shuffle(){
        for (int i = 0; i < rgen.nextInt(1, 100); i++) {
            for (int j = 0; j < cardDeck.size(); j++) {
                Card tempCard = cardDeck.get(j);
                cardDeck.remove(j);
                cardDeck.add(rgen.nextInt(0, cardDeck.size() - 1), tempCard);
            }
        }
    }

    public String toString(){
        String deckString = "";
        for (int i = 0; i< cardDeck.size(); i++){
            deckString += cardDeck.get(i).getValue() + " of " + cardDeck.get(i).getSuit() + ". ";
        }
        return deckString;
    }

    public boolean deckIsEmpty() {
        if (cardDeck.isEmpty() == true) {
            return true;
        } else {
            return false;
        }
    }
}
