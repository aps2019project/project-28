package Model.account;

import Model.card.Card;

import java.util.ArrayList;
import java.util.Iterator;

public class Collection {

    private ArrayList<Collection> collections = new ArrayList<>();
    private ArrayList<Card> cards;
    //    private ArrayList<Usables> usables;
    private ArrayList<Deck> decks;
    private Account owner;
    private Deck mainDeck;
    private final int MAX_USABLES = 3;

    public ArrayList<Collection> getCollections() {
        return collections;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    //    public ArrayList<Usables> getUsables() {
//        return usables;
//    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public Account getOwner() {
        return owner;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    private void addNewDeck(Deck deck){
        //todo check e deck
        decks.add(deck);
    }

    public boolean hasCard(int cardID){

    }

    public boolean hasCard(Card card){


    }

    public Card getCard(int cardID){

    }

    public Card getCard(Card card){

    }

    public boolean hasItem(int itemID){

    }

//
//    public Item getItem(int itemID){
//
//    }
//
//    public Item getItem(Item item){
//
//    }
    public Deck getDeck(int deckID){

    }

    public void setMainDeck(String deckName) {

    }

    public void addCardToCollection(Card card){

    }

    public boolean has(String name){

    }

    public void save(){

    }

    public void createDeck(String name){

    }

    public void deleteDeck(String name){

    }
}