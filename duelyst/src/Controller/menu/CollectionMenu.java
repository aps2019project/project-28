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

    private Collection tempCollection;
    private ArrayList<OnCollectionPresentedListener> collectionPresenters;

    public CollectionMenu(Menu parentMenu, String name) {
        super(name);
        this.account = parentMenu.getAccount();
        this.tempCollection = new Collection();
        this.collectionPresenters = new ArrayList<>();
    }

    public void save(Account account) {
        account.setCollection(this.tempCollection);
    }

    public void showCollection() {
        for (OnCollectionPresentedListener presenter : this.collectionPresenters) {
            presenter.show(this.account.getCollection());
        }
    }

    public void search(String name) {
        Collection collection = this.account.getCollection();
        for (Card card : collection.getAllCardsByName(name)) {
            for (OnCardDetailsPresentedListener presenter : card.getCardDetailsPresenters()) {
                presenter.showCardDetail(card);
            }
        }
        for (Item item : collection.getAllItemsByName(name)) {
            for (OnItemDetailPresentedListener presenter : item.getItemDetailPresenters()) {
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

    public void addCollectionPresenterListener(OnCollectionPresentedListener presenter) {
        this.collectionPresenters.add(presenter);
    }

    public boolean validateDeck(String deckName) throws InvalidDeckException {
        return this.account.getCollection().getDeckByName(deckName).validateDeck();
    }

    public void showDeck(String deckName) throws InvalidDeckException {
        Deck deck = this.account.getCollection().getDeckByName(deckName);
        for (OnDeckPresentedListener presenter : deck.getDeckPresenters()) {
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

    }
}
