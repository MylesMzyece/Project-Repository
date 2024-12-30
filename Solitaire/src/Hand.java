import java.util.ArrayList;

public class Hand {

    public ArrayList<Card> handSet;

    public Hand(){
        handSet = new ArrayList<Card>();
    }

    public void reset(){
        for (int i = handSet.size()-1; i > 0; i--){
            handSet.remove(i);
        }
    }

    public void add (Card card){
        handSet.add(card);
    }

    public void addAt(Card card, int i){
        handSet.add(i, card);
    }

    public int size (){
        return handSet.size();
    }

    public Card getCard (int i){
        return handSet.get(i);
    }

    public Card deal(){
        Card tempCard = handSet.get(0);
        handSet.remove(0);
        return tempCard;
    }

    public Card dealTop(){
        Card tempCard = handSet.get(handSet.size()-1);
        handSet.remove(handSet.size()-1);
        return tempCard;
    }

    public Card dealAt(int i){
        Card tempCard = handSet.get(i-1);
        handSet.remove(handSet.get(i-1));
        return tempCard;
    }

    public String toString(){
        String handString = "";
        for (int i = 0; i< handSet.size(); i++){
            handString += handSet.get(i).getValue() + " of " + handSet.get(i).getSuit() + ". ";
        }
        return handString;
    }

    public boolean handIsEmpty() {
        if (handSet.isEmpty() == true) {
            return true;
        } else {
            return false;
        }
    }
}

