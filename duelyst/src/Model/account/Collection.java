package Model.account;

import Model.item.OnItemDetailPresentedListener;
import View.Listeners.OnCollectionPresentedListener;
import Model.card.Card;
import Model.item.Item;
import Model.item.Usable;
import exeption.*;

import java.util.ArrayList;

public class Collection {

    private ArrayList<Deck> decks = new ArrayList<>();
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Usable> usables = new ArrayList<>();
    private static ArrayList<OnCollectionPresentedListener> collectionPresentedListeners = new ArrayList<>();
    private Account owner;
    private Deck mainDeck;
    public static final int MAX_USABLES = 3;

    public boolean hasDeck(String name) {
        for (Deck deck :
                decks) {
            if (deck.getName().compareTo(name) == 0) {
                return true;
            }
        }
        return false;
    }

    public Deck getDeckByName(String name) throws InvalidDeckException {
        for (Deck deck : decks) {
            if (deck.getName().compareTo(name) == 0) {
                return deck;
            }
        }
        throw new InvalidDeckException();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
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

    public ArrayList<Usable> getUsables() {
        return usables;
    }

    public boolean hasCard(int cardID) {
        for (Card card :
                cards) {
            if (card.getCardID() == cardID) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCard(Card card) {
        for (Card collectionCard :
                cards) {
            if (collectionCard.equals(card)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCard(String name) {
        System.err.println("TO BE FOUND :"+ name);
        for (Card collectionCard : cards) {
            if (collectionCard.getName().toLowerCase().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Card getCard(int cardID) throws InvalidCardException {
        for (Card card :
                cards) {
            if (card.getCardID() == cardID) {
                return card;
            }
        }
        throw new InvalidCardException();
    }

    public Card getCard(String name) throws InvalidCardException {
        for (Card card :
                cards) {
            if (card.getName().toLowerCase().equals(name)) {
                return card;
            }
        }
        throw new InvalidCardException();
    }

    public boolean hasItem(int itemID) {
        for (Usable item :
                usables) {
            if (item.getID() == itemID) {
                return true;
            }
        }
        return false;
    }

    public boolean hasItem(String name) {
        for (Usable item :
                usables) {
            if (item.getName().toLowerCase().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasItem(Item item) {
        for (Usable usable :
                usables) {
            if (usable.getName().toLowerCase().equals(item.getName().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public Usable getItem(int itemID) throws InvalidItemException {
        for (Usable item :
                usables) {
            if (item.getID() == itemID) {
                return item;
            }
        }
        throw new InvalidItemException();
    }

    public Usable getItem(String name) throws InvalidItemException {
        for (Usable item :
                usables) {
            if (item.getName().toLowerCase().equals(name)) {
                return item;
            }
        }
        throw new InvalidItemException();
    }

    private Deck getDeck(String deckName) throws InvalidDeckException {
        for (Deck deck :
                decks) {
            if (deck.getName().compareTo(deckName) == 0) {
                return deck;
            }
        }
        throw new InvalidDeckException();
    }

    public boolean addNewDeck(String name) throws DeckAlreadyExistException {
        if (!this.hasDeck(name)) {
            Deck newDeck = new Deck(name,this);
            this.decks.add(newDeck);
            return true;
        }
        throw new DeckAlreadyExistException();
    }

    public void deleteDeck(String name) throws InvalidDeckException {
        if (this.hasDeck(name)) {
            Deck delete = getDeckByName(name);
            this.decks.remove(delete);
            return;
        }
        throw new InvalidDeckException();
    }

    public void addCardToDeck(int cardID, String deckName) throws InvalidCardException, InvalidDeckException, DeckAlreadyHasAHeroException, DeckAlreadyHasThisCardException, FullDeckException {
        Card card = this.getCard(cardID);
        this.getDeck(deckName).addCardToDeck(card);
    }

    public void setMainDeck(String deckName) throws InvalidDeckException {
        Deck mainDeck = getDeck(deckName);
        if(mainDeck.validateDeck())
            this.setMainDeck(mainDeck);
        else{
            System.out.println("selected deck is not valid");
        }
    }

    public void setMainDeck(Deck mainDeck) {
        this.mainDeck = mainDeck;
    }

    public void addCardToCollection(Card card) throws CardExistException {
        if (!this.hasCard(card)) {
           this.cards.add(card);
            return;
        }
        throw new CardExistException();
    }

    public void forcePushDeck(Deck deck){
        this.decks.add(deck);
        // TODO: 5/14/19 in goh khorit ro jam kon plz be hamrah e saE
    }

    public void removeCardFromCollection(Card card) throws InvalidCardException {
        if (this.hasCard(card)) {
            this.cards.remove(card);
            return;
        }
        throw new InvalidCardException();
    }

    public void addItemToCollection(Usable item) throws ItemExistExeption {
        if (!hasItem(item)) {
            this.usables.add(item);
            return;
        }
        throw new ItemExistExeption();
    }

    public void removeItemFromCollection(Usable item) throws InvalidItemException {
        if (this.hasItem(item)) {
            this.usables.remove(item);
            return;
        }
        throw new InvalidItemException();
    }

    public boolean has(int ID) {
        return hasCard(ID) || hasItem(ID);
    }

    public ArrayList<Card> getAllCardsByID(int ID) {
        ArrayList<Card> returnArray = new ArrayList<>();
        for (Card card : this.cards) {
            if (card.getCardID() == ID) returnArray.add(card);
        }
        return returnArray;
    }

    public ArrayList<Card> getAllCardsByName(String name) {
        ArrayList<Card> returnArray = new ArrayList<>();
        for (Card card : this.cards) {
            if (card.getName().toLowerCase().equals(name)) returnArray.add(card);
        }
        return returnArray;

    }

    public ArrayList<Item> getAllItemsByName(String name) {
        ArrayList<Item> returnArray = new ArrayList<>();
        for (Usable usable : this.usables) {
            if (usable.getName().toLowerCase().equals(name)) returnArray.add(usable);
        }
        return returnArray;
    }

    public ArrayList<Item> getAllItemsByID(int ID) {
        ArrayList<Item> returnArray = new ArrayList<>();
        for (Usable usable : this.usables) {
            if (usable.getID() == ID) returnArray.add(usable);
        }
        return returnArray;
    }

    public void removeFromCollection(String name) throws InvalidCardException {
        try {
            removeItemFromCollection(this.getItem(name));
        } catch (InvalidItemException e) {
            removeCardFromCollection(this.getCard(name));
        }
    }

    public static ArrayList<OnCollectionPresentedListener> getCollectionPresentedListeners() {
        return (Collection.collectionPresentedListeners);
    }

    public static void addCollectionPresentedListener(OnCollectionPresentedListener presenter) {
        Collection.collectionPresentedListeners.add(presenter);
    }

    public ArrayList<Usable> getItems() {
        return usables;
    }
}
