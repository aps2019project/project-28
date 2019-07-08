package Controller.menu;

import Controller.menu.Graphics.FXMLController.CollectionMenuFXMLC;
import Model.Primary;
import Model.item.Usable;
import View.Listeners.OnCollectionPresentedListener;
import Model.account.*;
import Model.card.Card;
import Model.card.OnCardDetailsPresentedListener;
import Model.item.Item;
import View.Listeners.OnItemDetailPresentedListener;
import View.Listeners.OnDeckPresentedListener;
import View.Listeners.OnDeckSelectorClickedListener;
import com.gilecode.yagson.YaGson;
import exeption.*;

import java.util.List;

public class CollectionMenu extends Menu implements DeckSelectorHavingMenu{

    private static CollectionMenu menu;
    private Collection tempCollection;
    private Deck selectedDeck ;

    private OnDeckSelectorClickedListener onDeckSelectorClickedListener ;


//    @Override
//    protected void init() {
//        super.init();
//    }

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
    public boolean init(Menu parentMenu) {
        super.init(parentMenu);
        YaGson gson = new YaGson();
        this.tempCollection = gson.fromJson(gson.toJson(this.getAccount().getCollection()), Collection.class);
        return true;
    }

    public void save() {
        if (this.account == null) return ;
        this.account.setCollection(this.tempCollection);
        Primary.saveAccounts();
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

    public Deck createNewDeck(String deckName) throws DeckAlreadyExistException {
        if (this.tempCollection.hasDeck(deckName)) throw new DeckAlreadyExistException();
        return this.tempCollection.addNewDeck(deckName);
    }

    public void deleteDeck(String deckName) throws InvalidDeckException {
        if (!this.tempCollection.hasDeck(deckName)) throw new InvalidDeckException();
        this.tempCollection.deleteDeck(deckName);
    }

    public void addToDeck(int ID, String deckName) throws DeckAlreadyHasAHeroException, DeckAlreadyHasThisCardException,
            FullDeckException, InvalidCardException, DeckAlreadyHasThisItemException, InvalidDeckException, InvalidItemException {
        this.tempCollection.getDeckByName(deckName).addToDeck(ID);
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
    public void exit() {
        this.save();
        super.exit();
    }

    public boolean isTheCardInTheDeck(Card card){
        if (selectedDeck == null) {
            System.err.println("selected deck is null");
            return false ;
        }
        try {
            return this.tempCollection.getDeckByName(selectedDeck.getName()).hasCard(card) ;
        } catch (InvalidDeckException e) {
            e.printStackTrace();
        }
        return false ;
    }
    public boolean isTheItemInTheDeck(Item item){
        if (selectedDeck == null) return false ;
        return selectedDeck.hasItem(item.getID()) ;
    }

    public void setSelectedDeck(Deck selectedDeck) {
        this.selectedDeck = selectedDeck;
    }

    public Deck getSelectedDeck() {
        return selectedDeck;
    }

    @Override
    public void selectDeck(Account account, Deck deck) {
        try {
            deleteDeck(deck.getName());
            ((CollectionMenuFXMLC)getGraphic().getController()).buildDecksVbox();
        } catch (InvalidDeckException e) {
            e.printStackTrace();
        }
//        Primary.saveAccounts();
    }

    @Override
    public void setDeckSelectorListener(OnDeckSelectorClickedListener ds) {
        onDeckSelectorClickedListener = ds ;
    }

    @Override
    public void showDeckSelector(Account account) {
        onDeckSelectorClickedListener.show(menu.getAccount(), this , "Which deck do you wish to remove?", false);
    }

    public List<Deck> getDecks(){
        System.out.println(tempCollection.getDecks().size());
        return tempCollection.getDecks();
    }
}
