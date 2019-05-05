package Model.account;

import Model.card.Card;
import Model.card.hermione.Hero;
import Model.item.Item;
import exeption.*;

import java.util.ArrayList;
import java.util.Collections;

public class Deck{
    private Collection collection ;
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<OnDeckPresentedListener>deckPresenters=new ArrayList<OnDeckPresentedListener>();
    private Hero hero;
    private String name;
    private int ID;
    final static int CARD_SIZE = 20;
    final static int ITEM_SIZE = 1;

    public Deck(String name){
        this.name = name;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public String getName() {
        return name;
    }

    public boolean hasCard(int cardID){
        for (Card card:
             cards) {
            if(card.getCardID() == cardID)
                return true;
        }
        return false;
    }

    public Item getItem(int id) throws InvalidItemException {
        for (Item item : this.items) {
            if(item.getID()==id)return item;
        }
        throw new InvalidItemException();
    }

    public Card getCard(int cardID) throws InvalidCardException {
        for (Card card : this.getCards()) {
            if(card.getCardID()==cardID)return card;
        }
        throw new InvalidCardException();
    }

    public boolean hasItem(int itemID){
        for (Item item:
             items) {
            if(item.getID() == itemID) {
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

    public void removeFromDeck(int ID) throws InvalidCardException, InvalidItemException {
        if(Card.hasCard(ID))
            removeCardFromDeck(ID);
        if(Item.hasItem(ID))
            removeItemFromDeck(ID);
    }

    private boolean removeCardFromDeck(int cardID) throws InvalidCardException {
        Card willBeRemoved = null;

        if(!this.hasCard(cardID))throw new InvalidCardException();
        for (Card card : cards) {
            if (card.getCardID() == cardID) {
                willBeRemoved = card;
                break;
            }
        }
        cards.remove(willBeRemoved);
        if(willBeRemoved.getClass().equals(hero.getClass()))hero=null;
        return true;
    }

    private boolean removeItemFromDeck(int itemID) throws InvalidItemException {
        Item willBeRemoved = null;
        if(!this.hasItem(itemID))throw new InvalidItemException();
        for (Item item : items) {
            if(item.getID() == itemID){
                willBeRemoved = item;
            }
        }
        items.remove(willBeRemoved);
        return true;
    }


    public boolean addCardToDeck(Card card) throws DeckAlreadyHasThisCardException, FullDeckException, DeckAlreadyHasAHeroException {
        if(this.hasCard(card.getCardID()))throw new DeckAlreadyHasThisCardException();
        if(this.cards.size()>=CARD_SIZE)throw new FullDeckException();
        if(card.getClass().equals(hero.getClass()) && hero!=null)throw new DeckAlreadyHasAHeroException();

        cards.add(card);
        if(card.getClass().equals(hero.getClass()))hero=(Hero)card;
        return true;
    }

    public boolean addItemToDeck(Item item) throws DeckAlreadyHasThisItemException, FullDeckException {
        if(this.hasItem(item.getID()))throw new DeckAlreadyHasThisItemException();
        if(this.items.size()>=ITEM_SIZE)throw new FullDeckException();


        items.add(item);
        return true;
    }

    public void addToDeck(int ID) throws DeckAlreadyHasAHeroException, DeckAlreadyHasThisCardException,
            FullDeckException, InvalidCardException, DeckAlreadyHasThisItemException, InvalidItemException {
            if(Card.hasCard(ID))
                addCardToDeck(collection.getCard(ID));
            else if(Item.hasItem(ID))
                addItemToDeck(collection.getItem(ID));
    }

    public void addNewOnDeckPresentedListener(OnDeckPresentedListener presenter){
        this.deckPresenters.add(presenter);
    }

    public ArrayList<OnDeckPresentedListener> getDeckPresenters() {
        return (ArrayList<OnDeckPresentedListener>) Collections.unmodifiableList(deckPresenters);
    }

    public Hero getHero() {
        return hero ;
    }
}

