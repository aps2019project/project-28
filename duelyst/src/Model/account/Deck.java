package Model.account;

import Model.card.Card;
import Model.item.Item;

import java.util.ArrayList;
import java.util.Collections;

public class Deck{

    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private Hero hero;
    private String name;
    private int ID;
    final static int CARD_SIZE = 20;
    final static int ITEM_SIZE = 1;

    public Deck(String name){
        this.name = name;
    }

    private ArrayList<Card> getCards() {
        return cards;
    }

    public String getName() {
        return name;
    }

    private boolean hasCard(int cardID){
        for (Card card:
             cards) {
            if(card.getCardID() == cardID)
                return true;
        }
        return false;
    }

    private boolean hasItem(int itemID){
        for (Item item:
             items) {
            if(item.getItemID() == itemID) {
                return true;
            }
        }
        return false;
    }

    public boolean validateDeck() {
        if (cards.size() != CARD_SIZE) {
            return false;
        }
        if (items.size() != ITEM_SIZE) {
            return false;
        }
        if (hero == null) {
            return false;
        }
        return true;
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public void removeFromDeck(int ID){
        removeCardFromDeck(ID);
        removeItemFromDeck(ID);
    }

    private boolean removeCardFromDeck(int cardID){
        Card willBeRemoved = null;
        if(this.hasCard(cardID)) {
            for (Card card :
                    cards) {
                if (card.getCardID() == cardID) {
                    willBeRemoved = card;
                    break;
                }
            }
            cards.remove(willBeRemoved);
            return true;
        }
        return false;
    }

    private boolean removeItemFromDeck(int itemID) {
        Item willBeRemoved = null;
        if (this.hasItem(itemID)) {
            for (Item item :
                    items) {
                if(item.getItemID() == itemID){
                    willBeRemoved = item;
                }
            }
            items.remove(willBeRemoved);
            return true;
        }
        return false;
    }

    private boolean addCardToDeck(Card card){
        if(!this.hasCard(card.getCardID())) {
            cards.add(card);
            return true;
        }
        return false;
    }

    private boolean addItemToDeck(Item item){
        if(!this.hasItem(item.getItemID())) {
            items.add(item);
            return true;
        }
        return false;
    }

    public void addToDeck(int ID){
        addCardToDeck(Card.getCardByID(ID));
        addItemToDeck(Item.getItemByID(ID));
    }

}

