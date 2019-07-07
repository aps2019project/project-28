package Model.account;

import Model.Primary;
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
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Card> graveYard = new ArrayList<>();
    private static ArrayList<OnDeckPresentedListener> deckPresenters = new ArrayList<>();
    private Hero hero;
    private String name;
    private int ID;
    final static int CARD_SIZE = 20;
    final static int ITEM_SIZE = 1;

    public Deck(String name,Collection collection) {
        this.name = name;
        this.collection=collection;
    }

    public void updateDeck() {
        ArrayList<Card>newCards=new ArrayList<>();
        this.getCards().forEach(c-> {
            try {
                newCards.add(this.collection.getCard(c.getID()));
            } catch (InvalidCardException ignored) {
                ignored.printStackTrace();
            }
        });
        ArrayList<Item>newItems=new ArrayList<>();
        this.getItems().forEach(i-> {
            try {
                newItems.add(this.collection.getItem(i.getID()));
            } catch (InvalidItemException e) {
                e.printStackTrace();
            }
        });


        try {
            this.hero= (Hero) this.collection.getCard(this.hero.getID());
        } catch (InvalidCardException ignored) {
            ignored.printStackTrace();
        }

        this.setItems(newItems);
        this.setCards(newCards);
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
            if (card.getID() == cardID)
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
            if (card.getID() == cardID)
                return card;
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

    public boolean validateDeck() throws InvalidDeckException {
//        System.err.println(this.getHero().getName());
        if (cards.size() != CARD_SIZE) {
            System.err.println("card size problem");
            throw new InvalidDeckException();
        }
        if (items.size() != ITEM_SIZE) {
            System.err.println("item size problems");
            throw new InvalidDeckException();
        }
        if (hero == null) {
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
        Card willBeRemoved = null;
        if (!this.hasCard(cardID))
            throw new InvalidCardException();
        for (Card card : cards) {
            if (card.getID() == cardID) {
                willBeRemoved = card;
                break;
            }
        }
        if (willBeRemoved instanceof Hero){
            hero = null;
        }
        cards.remove(willBeRemoved);
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
        if (this.hasCard(card.getID())) throw new DeckAlreadyHasThisCardException();
        if (this.cards.size() >= CARD_SIZE) throw new FullDeckException();
        if (this.hero != null && card instanceof Hero) throw new DeckAlreadyHasAHeroException();
        cards.add(card);
        if (this.hero == null && card instanceof Hero) {
            this.hero = (Hero) card;
        }
        return true;
    }

    public boolean addItemToDeck(Item item) throws DeckAlreadyHasThisItemException, FullDeckException {
        if (this.hasItem(item.getID())) throw new DeckAlreadyHasThisItemException();
        if (this.items.size() >= ITEM_SIZE) throw new FullDeckException();
        items.add(item);
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
        this.hero = null;
    }

    public ArrayList<Usable> getUsables() {
        ArrayList<Usable>usables=new ArrayList<>();
        for (Item item : this.getItems()) {
            usables.add((Usable) item);
        }
        return usables;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public boolean has(Object object) {
        for (Item item : this.items) {
            if(item==object)return true;
        }
        for (Card card : this.cards) {
            if(card== object)return true;
        }
        if(this.hero==object)return true;
        return false;
    }
}