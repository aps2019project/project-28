package Controller.menu;

import View.Listeners.OnCollectionPresentedListener;
import Model.account.*;
import Model.card.Card;
import Model.card.OnCardDetailsPresentedListener;
import Model.item.Item;
import Model.item.OnItemDetailPresentedListener;
import View.Listeners.OnDeckPresentedListener;
import com.gilecode.yagson.YaGson;
import exeption.*;

public class CollectionMenu extends Menu {

    private static CollectionMenu menu;
    private Collection tempCollection;

    private CollectionMenu(String name) {
        super(name);
        tempCollection = new Collection();
    }

    public static CollectionMenu getMenu(){
        if(CollectionMenu.menu == null){
            CollectionMenu.menu = new CollectionMenu("CollectionMenu");
        }
        return menu;
    }

    @Override
    public void init(Menu parentMenu) {
        super.init(parentMenu);
        YaGson gson = new YaGson();
        this.tempCollection = gson.fromJson(gson.toJson(this.getAccount().getCollection()), Collection.class);
    }

    public void save() {
        this.account.setCollection(this.tempCollection);
        account.save();
    }

    public void showCollection() {
        for (OnCollectionPresentedListener presenter : Collection.getCollectionPresentedListeners()) {
            presenter.show(this.tempCollection,this.account.getName()+"'s Collection");
        }
    }

    public void search(String name) {
            for (Card card : this.tempCollection.getAllCardsByName(name)) {
                for (OnCardDetailsPresentedListener presenter : Card.getCardDetailsPresenters()) {
                    presenter.showCardDetail(card);
                }
            }

        for (Item item : this.tempCollection.getAllItemsByName(name)) {
            for (OnItemDetailPresentedListener presenter : Item.getItemDetailPresenters()) {
                presenter.showItemDetail(item);
            }
        }
    }

    public void createNewDeck(String deckName) throws DeckAlreadyExistException {
        if (this.tempCollection.hasDeck(deckName)) throw new DeckAlreadyExistException();
        this.tempCollection.addNewDeck(deckName);
    }

    public void deleteDeck(String deckName) throws InvalidDeckException {
        if (!this.tempCollection.hasDeck(deckName)) throw new InvalidDeckException();
        this.tempCollection.deleteDeck(deckName);
    }

    public void addToDeck(int ID, String deckName) throws DeckAlreadyHasAHeroException, DeckAlreadyHasThisCardException,
            FullDeckException, InvalidCardException, DeckAlreadyHasThisItemException, InvalidDeckException, InvalidItemException {
        System.err.println("im in addToDeck of CollectionMenu");
        this.tempCollection.getDeckByName(deckName).addToDeck(ID);
        System.err.println("im in addToDeck of CollectionMenu");
    }

    public void removeFromDeck(int ID, String deckName) throws InvalidCardException, InvalidItemException, InvalidDeckException {
        this.tempCollection.getDeckByName(deckName).removeFromDeck(ID);
    }

    public boolean validateDeck(String deckName) throws InvalidDeckException {
        return this.tempCollection.getDeckByName(deckName).validateDeck();
    }

    public void showDeck(String deckName) throws InvalidDeckException {
        Deck deck = this.tempCollection.getDeckByName(deckName);
        for (OnDeckPresentedListener presenter : Deck.getDeckPresenters()) {
            presenter.showDeck(deck);
        }
    }

    public void showAllDecks() {
        for (Deck deck : this.tempCollection.getDecks()) {
            try {
                System.out.println(deck.getName()+ " : ");
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
        this.tempCollection.setMainDeck(deckName);
    }

    @Override
    public Menu exit() {
        this.save();
        return super.exit();
    }
}
