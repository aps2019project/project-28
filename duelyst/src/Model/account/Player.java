package Model.account;

import Model.card.Card;
import Model.card.hermione.Minion;
import Model.item.Collectable;
import Model.item.Item;
import exeption.NoCardHasBeenSelectedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Player {
    private Account user;
    private Hand hand;
    private int maxMana =2;
    private int mana;
    private ArrayList<Minion> minionsInGame ;//this players minions
    private ArrayList<Collectable> collectables;
    private Deck deck;

    private Card selectedCard;
    private Item selectedItem;

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMana() {
        return mana;
    }

    public void handleWin(){

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
        return Collections.unmodifiableList(minionsInGame) ;
    }

    public List<Collectable> getUsabless() {
        return Collections.unmodifiableList(collectables);
    }

    public Card getSelectedCard() throws NoCardHasBeenSelectedException {
        if(selectedCard==null)throw new NoCardHasBeenSelectedException();
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

    public Player(Account user, int maxMana, int mana) {
        // TODO: 5/5/19 hardCopy
        this.user = user;
        this.maxMana = maxMana;
        this.mana = mana;
        this.minionsInGame = new ArrayList<>();
        this.collectables = new ArrayList<>();
        this.deck = this.user.getCollection().getMainDeck();
        this.hand = new Hand(this.deck);
        this.selectedCard = null;
        this.selectedItem = null;
    }
}
