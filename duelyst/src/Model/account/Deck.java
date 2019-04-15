package Model.account;

import Model.card.Card;
import Model.item.Item;

import java.util.ArrayList;

public class Deck{

    private ArrayList<Card> cards = new ArrayList<>();
    private Item item;
    private Hero hero;
    private String name;
    private int ID;
    final static int CARD_SIZE = 20;

    public Deck(String name){
        this.name = name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public String getName() {
        return name;
    }

    public boolean validateDeck() {
        if (cards.size() != CARD_SIZE) {
            return false;
        }
        if (item == null) {
            return false;
        }
        if (hero == null) {
            return false;
        }
        return true;
    }

    public void shuffle(){
    }
    public void removeFromDeck(int ID){
    }

    public void removeCardFromDeck(int cardID){

    }

    public void removeItemFromDeck(int itemID){

    }

    public void addCardToDeck(Card card){
        //validate
        cards.add(card);
    }

    public void setItem(Item item){
        this.item = item;
    }

    public void addToDeck(int ID){

    }

}

