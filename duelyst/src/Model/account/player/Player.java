package Model.account.player;

import Controller.Game;
import Controller.menu.Battle;
import Model.Map.Cell;
import Model.account.Account;
import Model.account.Deck;
import Model.account.Hand;
import Model.account.StuffEffectsOnPlayer;
import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.hermione.Minion;
import Model.card.spell.Spell;
import Model.item.Collectable;
import Model.item.Item;
import com.gilecode.yagson.YaGson;
import exeption.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


class ScannerWrapper{
    Scanner scanner;

}

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
    private StuffEffectsOnPlayer stuffEffectsOnPlayer = new StuffEffectsOnPlayer() ;
    private boolean hasFlag=false;
    private int flagInteger=0;

    private GameInterFace GI;
    protected ScannerWrapper inputStream =new ScannerWrapper();



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
        if(this.deck!=null){
            this.deck.setCollection(user.getCollection());
            this.deck.shuffle();
            this.hand = new Hand(this.deck);
        }
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

    public void deploy(Card card, Cell cell,Player enemy) throws NotEnoughManaException, DestinationIsFullException, InvalidCellException, InvalidCardException {

        if(!canDeploy(card, cell))return;
        this.setMana(this.getMana()-card.getManaPoint());

        if(card instanceof Minion){
            Minion minion = (Minion) card;
            minion.spawn(cell);
            stuffEffectsOnPlayer.handleMinionDeploy(minion) ;

            Battle.getMenu().getMap().getCell(cell).setCardOnCell((Hermione) card);
        }else if(card instanceof Spell){
            Spell spell = (Spell) card;
            spell.deploy(this, enemy, cell);
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

    public void reFillMana() {
        this.mana = maxMana + stuffEffectsOnPlayer.getManaTheriac();
    }

    public boolean hasItem(int id) {
        for (Collectable collectable : this.getCollectables()) {
            if (collectable.getID() == id) return true;
        }
        return false;
    }
    public boolean hasFlag() {
        return hasFlag;
    }


    public void changeMana(int manaPoint) {
        this.mana+=manaPoint;
    }

    public StuffEffectsOnPlayer getStuffEffectsOnPlayer() {
        return stuffEffectsOnPlayer;
    }


    public GameInterFace getGI() {
        return GI;
    }

    public void setGI(GameInterFace GI) {
        this.GI = GI;
    }

    public void doYourMove(){
    }





    public Scanner getInputStream() {
        if(this.inputStream ==null || this.inputStream.scanner==null){
            this.inputStream = new ScannerWrapper();
            this.inputStream.scanner = Game.scanner;
        }
        return inputStream.scanner;

    }
}
