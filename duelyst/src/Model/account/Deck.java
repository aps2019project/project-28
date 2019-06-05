package Model.account;

import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.hermione.Hero;
import Model.card.hermione.Minion;
import Model.item.Item;
import Model.item.Usable;
import View.Listeners.OnDeckPresentedListener;
import exeption.*;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private Collection collection;
    private ArrayList<Integer> cardIDs = new ArrayList<>();
    private ArrayList<Integer> itemIDs = new ArrayList<>();
    private int heroID;
    private ArrayList<Card> cards;
    private ArrayList<Item> items;
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

    public void loadDeck() throws InvalidItemException, InvalidCardException {
        loadItems();
        loadCards();
    }

    public void loadCards() throws InvalidCardException {
        cards = new ArrayList<>();
        for (Integer card : cardIDs) {
            Card wantedCard = this.collection.getCard(card);
            cards.add(wantedCard);
        }
        Card hero = this.collection.getCard(heroID);
        this.hero = (Hero)hero;
    }

    public void loadItems() throws InvalidItemException {
        items = new ArrayList<>();
        for (Integer itemID : itemIDs) {
            Item item = this.collection.getItem(itemID);
            items.add(item);
        }
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
        for (Integer card :
                cardIDs) {
            if (card == cardID)
                return true;
        }
        return false;
    }

    public boolean hasCard(Card wantedCard) {
        for (Integer card :
                cardIDs) {
            if (card == wantedCard.getCardID())
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
            if (card.getCardID() == cardID)
                return card;
        }
        throw new InvalidCardException();
    }

    public boolean hasItem(int itemID) {
        for (Integer item :
                itemIDs) {
            if (item == itemID) {
                return true;
            }
        }
        return false;
    }

    public boolean validateDeck() throws InvalidDeckException {
//        System.err.println(this.getHero().getName());
        if (cardIDs.size() != CARD_SIZE) {
            System.err.println("card size problem");
            throw new InvalidDeckException();
        }
        if (itemIDs.size() != ITEM_SIZE) {
            System.err.println("item size problems");
            throw new InvalidDeckException();
        }
        if (heroID == -1) {
            System.err.println("hero problems");
            throw new InvalidDeckException();
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

    private void removeCardFromDeck(int cardID) throws InvalidCardException {
        Integer willBeRemoved = -1;
        if (!this.hasCard(cardID))
            throw new InvalidCardException();
        for (Integer card : cardIDs) {
            if (card == cardID) {
                willBeRemoved = card;
                break;
            }
        }
        if (willBeRemoved == heroID){
            heroID = -1;
        }
        cardIDs.remove(willBeRemoved);
    }

    public void moveAllToGraveYard(ArrayList<? extends Card> deads){
        for (Card dead : deads) {
            try {
                this.moveToGraveYard(dead);
            } catch (InvalidCardException ignored) {
                ignored.printStackTrace();
            }
        }
    }

    public void moveToGraveYard(Card card) throws InvalidCardException {
        if (this.hasCard(card)) {
            this.graveYard.add(card);
        } else {
            throw new InvalidCardException();
        }
    }

    private boolean removeItemFromDeck(int itemID) throws InvalidItemException {
        int willBeRemoved = -1;
        if (!this.hasItem(itemID)) throw new InvalidItemException();
        for (Integer item : itemIDs) {
            if (item == itemID) {
                willBeRemoved = item;
            }
        }
        itemIDs.remove(willBeRemoved);
        return true;
    }


    public boolean addCardToDeck(Card card) throws DeckAlreadyHasThisCardException, FullDeckException, DeckAlreadyHasAHeroException {
        if (this.hasCard(card.getCardID())) throw new DeckAlreadyHasThisCardException();
        if (this.cardIDs.size() >= CARD_SIZE) throw new FullDeckException();
        if (this.heroID != -1 && card instanceof Hero) throw new DeckAlreadyHasAHeroException();
        cardIDs.add(card.getCardID());
        if (this.heroID == -1 && card instanceof Hero) {
            this.heroID = card.getCardID();
        }
        return true;
    }

    public boolean addItemToDeck(Item item) throws DeckAlreadyHasThisItemException, FullDeckException {
        if (this.hasItem(item.getID())) throw new DeckAlreadyHasThisItemException();
        if (this.itemIDs.size() >= ITEM_SIZE) throw new FullDeckException();
        itemIDs.add(item.getID());
        return true;
    }

    public void addToDeck(int ID) throws DeckAlreadyHasAHeroException, DeckAlreadyHasThisCardException,
            FullDeckException, InvalidCardException, DeckAlreadyHasThisItemException, InvalidItemException {
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
        this.graveYard.add(this.hero);
        this.hero.die();
        this.hero = null;
        this.heroID = -1;
    }

    public ArrayList<Usable> getUsables() {
        ArrayList<Usable>usables=new ArrayList<>();
        for (Item item : this.getItems()) {
            usables.add((Usable) item);
        }
        return usables;
    }
}

