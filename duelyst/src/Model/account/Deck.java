package Model.account;

import Model.card.Card;
import Model.item.Item;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;
    private ArrayList<Item> items;
    private String deckname;
    private Hero hero;
    final static int CARD_SIZE = 20;
    private final int ITEM_SIZE;

    public ArrayList<Card> getCards() {
        return cards;
    }

    public static boolean validateDeck(String deckname){

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

    }

    public void addItemToDeck(Item item){

    }

    public void addToDeck(int ID){

    }

}

