package Model.account;

import Controller.Game;
import Model.Map.Cell;
import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.hermione.Hero;
import Model.card.hermione.Minion;
import Model.card.spell.Spell;
import Model.item.Collectable;
import Model.item.Item;
import com.gilecode.yagson.com.google.gson.Gson;
import exeption.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private Account user;
    private Hand hand;
    private int maxMana = 2;
    private int mana;
    private ArrayList<Minion> minionsInGame;//this players minions
    private ArrayList<Collectable> collectables;
    private Deck deck;
    private Card selectedCard;
    private Item selectedItem;
    private int manaTheriac = 0;
    private int maxManaTheriac = 0;
    private boolean hasAssasinationDagger = false;


    public Player(Account user, int maxMana, int mana) {
        this.user = user;
        this.maxMana = maxMana;
        this.mana = mana;
        this.minionsInGame = new ArrayList<>();
        this.collectables = new ArrayList<>();
        this.selectedCard = null;
        this.selectedItem = null;
        Gson gson = new Gson();
        //In Order To Secure Objects In Account We Made A HardCopy Of MainDeck
        this.deck = gson.fromJson(gson.toJson(user.getCollection().getMainDeck()), Deck.class);
//        this.deck.setCollection(user.getCollection());
        // TODO: 5/9/19 check whether or not the line above needs to be
        this.hand = new Hand(this.deck);
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMana() {
        return mana;
    }

    public void handleWin() {
        // TODO: 5/5/19 ArshiA bezan
    }

    public void spawn(Card card, Cell cell) throws NotEnoughManaException, DestinationIsFullException, InvalidCellException, InvalidCardException {
        if (this.mana < card.getManaPoint()) throw new NotEnoughManaException();
        if (cell.isFull()) throw new DestinationIsFullException();
        if(card instanceof Hero){
            Hermione hermione = (Hermione) card;
            hermione.spawn(cell);
            if (hasAssasinationDagger) {
                Game.battle.getEnemyPlayer().getDeck().getHero().changeHealthPoint(-1);
            }
        }else{
            Spell spell = (Spell) card;
            spell.deploy(this, Game.battle.getEnemyPlayer(), cell);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return player.user.equals(this.user);
    }

    public Account getUser() {
        return user;
    }


    public Hand getHand() {
        return hand;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public ArrayList<Collectable> getCollectables() {
        return collectables;
    }

    public Deck getDeck() {
        return deck;
    }

    public List<Minion> getMinionsInGame() {
        return Collections.unmodifiableList(minionsInGame);
    }

    public List<Collectable> getUsables() {
        return Collections.unmodifiableList(collectables);
    }

    public Card getSelectedCard() throws NoCardHasBeenSelectedException {
        if (selectedCard == null) throw new NoCardHasBeenSelectedException();
        return selectedCard;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public void reFillMana() {
        this.mana = maxMana + maxManaTheriac;
        mana += this.manaTheriac;
        manaTheriac = 0;
    }

    public Item getItem(int ID) throws InvalidItemException {
        for (Collectable collectable : this.collectables) {
            if (collectable.getID() == ID) return collectable;
        }
        throw new InvalidItemException();
    }

    public boolean hasItem(int id) {
        for (Collectable collectable : this.getCollectables()) {
            if (collectable.getID() == id) return true;
        }
        return false;
    }

    public void setManaTheriac(int hasManaTheriac) {
        this.manaTheriac = hasManaTheriac;
    }

    public void setMaxManaTheriac(int maxManaTheriac) {
        this.maxManaTheriac += maxManaTheriac;
    }

    public void setHasAssasinationDagger(boolean hasAssasinationDagger) {
        this.hasAssasinationDagger = hasAssasinationDagger;
    }
}
