package Model.account;

import Model.card.Card;
import Model.item.Item;
import Model.item.Usable;

import java.util.ArrayList;

public class Collection{
    private ArrayList<Deck> decks = new ArrayList<>();
    private ArrayList<Deck> tempDecks = new ArrayList<>(decks);
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Card> tempCards = new ArrayList<>(cards);
    private ArrayList<Usable> usables;
    private Account owner;
    private Deck mainDeck;
    private Deck tempMainDeck;
    private final int MAX_USABLES = 3;

    private boolean hasDeck(String name){
        for (Deck deck:
             decks) {
            if(deck.getName().compareTo(name) == 0){
                return true;
            }
        }
        return false;
    }

    public Deck getDeckByName(String name){
        for (Deck deck:
             decks) {
            if(deck.getName().compareTo(name) == 0){
                return deck;
            }
        }
        return null;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public Account getOwner() {
        return owner;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    public boolean hasCard(int cardID){
        for (Card card:
             cards) {
            if(card.getCardID() == cardID){
                return true;
            }
        }
        return false;
    }

    public boolean hasCard(Card card){
        for (Card collectionCard:
             cards) {
            if(collectionCard.equals(card)){
                return true;
            }
        }
        return false;
    }

    public Card getCard(int cardID){
        for (Card card:
             cards) {
            if(card.getCardID() == cardID){
                return card;
            }
        }
        return null;
    }

    public Card getCard(Card card){
        for (Card collectionCard:
             cards) {
            if(collectionCard.equals(card)){
                return card;
            }
        }
        return null;
    }

    public boolean hasItem(int itemID){
        for (Usable item:
             usables) {
            if(item.getItemID() == itemID){
                return true;
            }
        }
        return false;
    }

    public Item getItem(int itemID){
        for (Usable item:
             usables) {
            if(item.getItemID() == itemID){
                return item;
            }
        }
        return null;
    }

    private Deck getDeck(String deckName){
        for (Deck deck:
             decks) {
            if(deck.getName().compareTo(deckName) == 0){
                return deck;
            }
        }
        return null;
    }

    private boolean addNewDeck(String name){
        if(!this.hasDeck(name)) {
            Deck newDeck = new Deck(name);
            this.tempDecks.add(newDeck);
            return true;
        }
        else {
            return false;
        }
    }

    public void deleteDeck(String name){
        if(this.hasDeck(name)){
            Deck delete = getDeckByName(name);
            tempDecks.remove(delete);
        }
    }

    public void setMainDeck(String deckName){
        Deck mainDeck = getDeck(deckName);
        this.tempMainDeck = mainDeck;
    }

    public boolean addCardToCollection(Card card){
        if(!hasCard(card)){
            tempCards.add(card);
            return true;
        }
        return false;
    }

    public boolean has(int ID){
        return hasCard(ID) || hasItem(ID);
    }

    public void save(){
        decks = tempDecks;
        tempMainDeck = null;
        cards = tempCards;
        tempCards = null;
        mainDeck = tempMainDeck;
        tempMainDeck = null;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }
}