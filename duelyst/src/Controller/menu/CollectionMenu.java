package Controller.menu;

import View.Listeners.OnCollectionPresentedListener;
import Model.account.*;
import Model.card.Card;
import Model.card.OnCardDetailsPresentedListener;
import Model.item.Item;
import Model.item.OnItemDetailPresentedListener;
import View.Listeners.OnDeckPresentedListener;
import exeption.*;

import java.util.ArrayList;

public class CollectionMenu extends Menu {
    private static CollectionMenu menu;

    private Collection tempCollection;
    private ArrayList<OnCollectionPresentedListener> collectionPresenters;

    private CollectionMenu(String name) {
        super(name);
        this.tempCollection = new Collection();
        this.collectionPresenters = new ArrayList<>();
    }

    public static CollectionMenu getMenu(){
        if(CollectionMenu.menu==null){
            CollectionMenu.menu=new CollectionMenu("CollectionMenu");
        }
        return menu;
    }

    public void save() {
        this.account.setCollection(this.tempCollection);
    }

    public void showCollection() {
        for (OnCollectionPresentedListener presenter : this.collectionPresenters) {
            presenter.show(this.account.getCollection());
        }
    }

    public void search(String name) {
        Collection collection = this.account.getCollection();
        for (Card card : collection.getAllCardsByName(name)) {
            for (OnCardDetailsPresentedListener presenter : Card.getCardDetailsPresenters()) {
                presenter.showCardDetail(card);
            }
        }
        for (Item item : collection.getAllItemsByName(name)) {
            for (OnItemDetailPresentedListener presenter : Item.getItemDetailPresenters()) {
                presenter.showItemDetail(item);
            }
        }
    }

    public void creatNewDeck(String deckName) throws DeckAlreadyExistException {
        if (this.account.getCollection().hasDeck(deckName)) throw new DeckAlreadyExistException();
        this.account.getCollection().addNewDeck(deckName);
    }

    public void deleteDeck(String deckName) throws InvalidDeckException {
        if (!this.account.getCollection().hasDeck(deckName)) throw new InvalidDeckException();
        this.account.getCollection().deleteDeck(deckName);
    }

    public void addToDeck(int ID, String deckName) throws DeckAlreadyHasAHeroException, DeckAlreadyHasThisCardException,
            FullDeckException, InvalidCardException, DeckAlreadyHasThisItemException, InvalidDeckException, InvalidItemException {
        this.account.getCollection().getDeckByName(deckName).addToDeck(ID);
    }

    public void removeFromDeck(int ID, String deckName) throws InvalidCardException, InvalidItemException, InvalidDeckException {
        this.account.getCollection().getDeckByName(deckName).removeFromDeck(ID);
    }

    public void addCollectionPresentedListener(OnCollectionPresentedListener presenter) {
        this.collectionPresenters.add(presenter);
    }

    public boolean validateDeck(String deckName) throws InvalidDeckException {
        return this.account.getCollection().getDeckByName(deckName).validateDeck();
    }

    public void showDeck(String deckName) throws InvalidDeckException {
        Deck deck = this.account.getCollection().getDeckByName(deckName);
        for (OnDeckPresentedListener presenter : Deck.getDeckPresenters()) {
            presenter.showDeck(deck);
        }
    }

    public void showAllDecks() {
        for (Deck deck : this.account.getCollection().getDecks()) {
            try {
                showDeck(deck.getName());
            } catch (InvalidDeckException e) {
            }
        }
    }

    @Override
    public void help() {
        super.help();
        System.out.println("4)show     5)search [card name | item name]    6)save");
        System.out.println("7)create deck[deck name]     8)delete deck [deck name]     9)add [card id | card id | hero id] to deck [deck name]");
        System.out.println("10)remove [card id | card id| hero id] from deck [deck name]     11)validate deck [deck name]     12)select deck [deck name]");
        System.out.println("13)show all decks     14)show deck [deck name]     15)enter[MenuName]");
    }

    public void selectDeck(String deckName) throws InvalidDeckException {
        this.account.getCollection().setMainDeck(deckName);
    }
}
