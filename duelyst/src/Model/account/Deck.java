package Model.account;

import Model.card.Card;
import Model.card.hermione.Hero;
import Model.item.Item;
import Model.item.Usable;
import View.Listeners.OnDeckPresentedListener;
import exeption.*;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private Collection collection;
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Card> graveYard = new ArrayList<>();
    private static ArrayList<OnDeckPresentedListener> deckPresenters = new ArrayList<OnDeckPresentedListener>();
    private Hero hero;
    private String name;
    private int ID;
    final static int CARD_SIZE = 20;
    final static int ITEM_SIZE = 1;

    public Deck(String name,Collection collection) {
        this.name = name;
        this.collection=collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public Collection getCollection() {
        return collection;
    }

    public boolean hasCard(int cardID) {
        for (Card card :
                cards) {
            if (card.getCardID() == cardID)
                return true;
        }
        return false;
    }

    public boolean hasCard(Card wantedCard) {
        for (Card card :
                cards) {
            if (card.equals(wantedCard))
                return true;
        }
        return false;
    }

    public Item getItem(int id) throws InvalidItemException {
        for (Item item : this.items) {
            if (item.getID() == id) return item;
        }
        throw new InvalidItemException();
    }

    public Card getCard(int cardID) throws InvalidCardException {
        for (Card card : this.getCards()) {
            if (card.getCardID() == cardID) return card;
        }
        throw new InvalidCardException();
    }

    public boolean hasItem(int itemID) {
        for (Item item :
                items) {
            if (item.getID() == itemID) {
                return true;
            }
        }
        return false;
    }

    public boolean validateDeck() {
        // TODO: 5/13/19 fatteme return false ha ro exception kon
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

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public void removeFromDeck(int ID) throws InvalidCardException, InvalidItemException {
        if (Card.hasCard(ID))
            removeCardFromDeck(ID);
        if (Item.hasItem(ID))
            removeItemFromDeck(ID);
    }

    private boolean removeCardFromDeck(int cardID) throws InvalidCardException {
        Card willBeRemoved = null;
        if (!this.hasCard(cardID))
            throw new InvalidCardException();
        for (Card card : cards) {
            if (card.getCardID() == cardID) {
                willBeRemoved = card;
                break;
            }
        }
        if (willBeRemoved instanceof Hero) hero = null;
        cards.remove(willBeRemoved);
        return true;
    }

    public void moveToGraveYard(Card card) throws InvalidCardException {
        if (this.hasCard(card)) {
            this.graveYard.add(card);
        } else {
            throw new InvalidCardException();
        }
    }

    private boolean removeItemFromDeck(int itemID) throws InvalidItemException {
        Item willBeRemoved = null;
        if (!this.hasItem(itemID)) throw new InvalidItemException();
        for (Item item : items) {
            if (item.getID() == itemID) {
                willBeRemoved = item;
            }
        }
        items.remove(willBeRemoved);
        return true;
    }


    public boolean addCardToDeck(Card card) throws DeckAlreadyHasThisCardException, FullDeckException, DeckAlreadyHasAHeroException {
        System.err.println("residam");
        if (this.hasCard(card.getCardID())) throw new DeckAlreadyHasThisCardException();
        System.err.println("residam");
        if (this.cards.size() >= CARD_SIZE) throw new FullDeckException();
        System.err.println("residam");
        if (hero != null && card instanceof Hero) throw new DeckAlreadyHasAHeroException();
        System.err.println("residam");
        cards.add(card);
        if (hero !=null && card instanceof Hero) hero = (Hero) card;
        return true;
    }

    public boolean addItemToDeck(Item item) throws DeckAlreadyHasThisItemException, FullDeckException {
        System.err.println("im innnn");
        if (this.hasItem(item.getID())) throw new DeckAlreadyHasThisItemException();
        System.err.println("im innnn");
        if (this.items.size() >= ITEM_SIZE) throw new FullDeckException();
        System.err.println("im innnn");


        items.add(item);
        return true;
    }

    public void addToDeck(int ID) throws DeckAlreadyHasAHeroException, DeckAlreadyHasThisCardException,
            FullDeckException, InvalidCardException, DeckAlreadyHasThisItemException, InvalidItemException {
//        System.err.println("im in addToDeck of Deck.java");
//        System.err.println("and cardName: "+Card.getCard(ID));

        if (Card.hasCard(ID))
            addCardToDeck(collection.getCard(ID));
        else if (Item.hasItem(ID))
            addItemToDeck(collection.getItem(ID));
    }

    public static void addNewOnDeckPresentedListener(OnDeckPresentedListener presenter) {
        Deck.deckPresenters.add(presenter);
    }

    public static ArrayList<OnDeckPresentedListener> getDeckPresenters() {
        return (deckPresenters);
    }

    public Hero getHero() {
        return hero;
    }

    public ArrayList<Card> getGraveYard() {
        return graveYard;
    }

    public void killHero() {
        this.hero = null;
    }

    public ArrayList<Usable> getUsables() {
        ArrayList<Usable>usables=new ArrayList<>();
        for (Item item : this.getItems()) {
            usables.add((Usable) item);
        }
        return usables;
    }
}

