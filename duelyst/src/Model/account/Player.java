package Model.account;

import Controller.Game;
import Controller.GameMode.GameMode;
import Controller.menu.Battle;
import Model.Map.Cell;
import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.hermione.Hero;
import Model.card.hermione.Minion;
import Model.card.spell.Spell;
import Model.item.Collectable;
import Model.item.Item;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.Gson;
import exeption.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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
    // TODO: 6/6/19 saE's shit 2.0 :|
                                        //ina ro ham mese hermione ye class bezan bebar untu dg
                                        private int manaTheriac = 0;
                                        private int maxManaTheriac = 0;
                                        private boolean hasAssasinationDagger = false;
    //
    private boolean hasFlag=false;
    private int flagInteger=0;



    public Player(Account user, int maxMana, int mana) {
        this.user = user;
        this.maxMana = maxMana;
        this.mana = mana;
        this.minionsInGame = new ArrayList<>();
        this.collectables = new ArrayList<>();
        this.selectedCard = null;
        this.selectedItem = null;
        YaGson gson = new YaGson();
        //In Order To Secure Objects In Account We Made A HardCopy Of MainDeck
        this.deck = gson.fromJson(gson.toJson(user.getCollection().getMainDeck()), Deck.class);
        this.deck.setCollection(user.getCollection());
        this.deck.shuffle();
        this.hand = new Hand(this.deck);
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMana() {
        return mana;
    }

    private boolean canDeploy(Card card, Cell cell) throws NotEnoughManaException, DestinationIsFullException {
        if (this.mana < card.getManaPoint()) throw new NotEnoughManaException();
        if (cell.isFull()) throw new DestinationIsFullException();
        return true;
    }

    public void deploy(Card card, Cell cell) throws NotEnoughManaException, DestinationIsFullException, InvalidCellException, InvalidCardException {

        if(!canDeploy(card, cell))return;

        if(card instanceof Minion){
            Minion minion = (Minion) card;
            minion.spawn(cell);
            // TODO: 6/6/19 SaE's shit 2.0
                                            //tu un class e ke sakhT ye tabe ye onDeployMinion besaz ina ro bebar un tu
                                            if (hasAssasinationDagger) {
                                                Battle.getMenu().getEnemyPlayer().getDeck().getHero().changeHealthPoint(-1);
                                            }
            //

            Battle.getMenu().getMap().getCell(cell).setCardOnCell((Hermione) card);
        }else if(card instanceof Spell){
            Spell spell = (Spell) card;
            spell.deploy(this, Battle.getMenu().getEnemyPlayer(), cell);
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
        return (minionsInGame);
    }
    public List<Collectable> getUsables() {
        return Collections.unmodifiableList(collectables);
    }
    public Card getSelectedCard() throws NoCardHasBeenSelectedException {
        if (selectedCard == null) throw new NoCardHasBeenSelectedException();
        return selectedCard;
    }
    public Item getSelectedItem() throws NoItemHasBeenSelectedException {
        if(selectedItem==null)throw new NoItemHasBeenSelectedException();
        return selectedItem;
    }
    public Item getItem(int ID) throws InvalidItemException {
        for (Collectable collectable : this.collectables) {
            if (collectable.getID() == ID) return collectable;
        }
        throw new InvalidItemException();
    }
    public int getFlagInteger() {
        return flagInteger;
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
    public void setFlag(boolean hasFlag) {
        this.hasFlag = hasFlag;
    }
    public void setFlagInteger(int flagInteger) {
        this.flagInteger = flagInteger;
    }

    // TODO: 6/6/19 saE's shit 2.0.
                                    public void reFillMana() {
                                        this.mana = maxMana + maxManaTheriac;
                                        mana += this.manaTheriac;
                                        manaTheriac = 0;
                                    }
    //
    public boolean hasItem(int id) {
        for (Collectable collectable : this.getCollectables()) {
            if (collectable.getID() == id) return true;
        }
        return false;
    }
    public boolean hasFlag() {
        return hasFlag;
    }

    // TODO: 6/6/19 saE's shit 2.0.
                                    public void setManaTheriac(int hasManaTheriac) {
                                                                        this.manaTheriac = hasManaTheriac;
                                                                    }
                                    public void setMaxManaTheriac(int maxManaTheriac) {
                                                                        this.maxManaTheriac += maxManaTheriac;
                                                                    }
                                    public void setHasAssasinationDagger(boolean hasAssasinationDagger) {
        this.hasAssasinationDagger = hasAssasinationDagger;
    }
    //

    public void changeMana(int manaPoint) {
        this.mana+=manaPoint;
    }
}
