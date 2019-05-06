package Controller;

import Controller.menu.Menu;
import View.Listeners.OnGameInfoPresentedListener;
import Model.Map.Map;
import Model.account.*;
import Model.card.Card;
import Model.card.OnCardDetailsPresentedListener;
import Model.card.hermione.Hermione;
import Model.card.hermione.Minion;
import Model.card.hermione.SPATime;
import Model.card.spell.Spell;
import Model.item.Collectable;
import Model.item.KingSlayerCounter;
import Model.item.OnItemDetailPresentedListener;
import exeption.*;

import java.util.ArrayList;
import java.util.Collections;

public class Battle extends Menu {
    private Map map;
    private Player[] player =new Player[2];
    private int turn = 0 ;
    private ArrayList<Spell> ongoingSpells = new ArrayList<>();
    private static final int[] MAX_MANA_PER_TURN={2,3,3,4,4,5,5,6,6,7,7,8,8,9};
    private KingSlayerCounter[] kingSlayerCountDown =
            {new KingSlayerCounter(player[0]) , new KingSlayerCounter(player[1]) } ;

    private ArrayList<OnGameInfoPresentedListener>gameInfoPresenters=new ArrayList<>();

    public Battle(Menu parentMenu) {
        super(parentMenu);
        this.map=Map.generate();
    }

//    void init(Player player1,Player player2){
//        this.setPlayer();
//    }

    public void gameInfo() {
        for (OnGameInfoPresentedListener presenter : this.gameInfoPresenters) {
            presenter.showGameInfo();
        }
    }

    public void showMyMinions(){

        for (Card card : this.account.getPlayer().getDeck().getCards()) {
            for (OnCardDetailsPresentedListener presenter : card.getCardDetailsPresenters()) {
                presenter.showCardInfo(card);
            }
        }

    }

    public void showMyOpponentMinion(){
        for (Card card : this.getEnemy(this.account).getDeck().getCards()) {
            for (OnCardDetailsPresentedListener presenter : card.getCardDetailsPresenters()) {
                presenter.showCardInfo(card);
            }
        }
    }

    public void showCardInfo(int cardID) throws InvalidCardException {
        Card card=this.account.getPlayer().getDeck().getCard(cardID);
        for (OnCardDetailsPresentedListener presenter : Card.getCard(cardID).getCardDetailsPresenters()) {
            presenter.showCardInfo(card);
        }
    }

    public void select(int ID) throws InvalidCardException, InvalidItemException {
        Deck deck=this.account.getPlayer().getDeck();
        if(deck.hasCard(ID)) {
            Card card = deck.getCard(ID);
            this.account.getPlayer().setSelectedCard(card);
        }else if(this.account.getPlayer().hasItem(ID)){
            this.account.getPlayer().setSelectedItem(this.account.getPlayer().getItem(ID));
        }
    }


    public void move(int x,int y) throws NoCardHasBeenSelectedException, CardCantBeMovedException, MoveTrunIsOverException, DestinationOutOfreachException, InvalidCellException {
        try {
            Hermione hermione = (Hermione) this.account.getPlayer().getSelectedCard();
            hermione.move(x,y);
        }catch (ClassCastException e){
            throw new CardCantBeMovedException();//because its Spell
        }
    }

    public void attack(int cardID) throws NoCardHasBeenSelectedException, InvalidCardException, DestinationOutOfreachException, CantAttackException {
        Hermione myHermione= (Hermione) this.account.getPlayer().getSelectedCard();
        Hermione enemyCard= (Hermione) this.getEnemy(this.account).getDeck().getCard(cardID);

        myHermione.attack(enemyCard);
    }

    public void attackCombo(){
        // TODO: 5/5/19 COMMMBOOOOOOO
    }

    public void useSpecialPower(int x,int y){
        this.account.getPlayer().getDeck().getHero().applySpecialPower(x,y);
    }

    public void showHand(){
        Hand hand=this.account.getPlayer().getHand();
        for (OnHandPresentedListener presenter : hand.getHandPresenters()) {
            presenter.showHand(hand);
        }
    }

    public void insert(int cardID,int x,int y) throws InvalidCardException, NotEnoughManaException, DestinationIsFullException, InvalidCellException {
        this.account.getPlayer().spawn(this.account.getPlayer().getHand().getCard(cardID),this.map.getCell(x,y));
        // TODO: 5/5/19 one more exception  (read the doc)
    }

    public void endTurn() throws HandFullException, DeckIsEmptyException, InvalidCardException {

        /*updating hand*/
        this.account.getPlayer().getHand().updateHand();

        /*current player minions onTurn SP handling*/
        for (Minion minion : this.account.getPlayer().getMinionsInGame()) {
            minion.itIsTime(SPATime.ON_TURN);
        }
        for (Minion minion : this.account.getPlayer().getMinionsInGame()) {
            minion.itIsTime(SPATime.PASSIVE);
        }

        /*enemy player passive Handling*/
        turn++;
        for (Minion minion : this.getEnemy(this.account).getMinionsInGame()) {
            minion.itIsTime(SPATime.PASSIVE);
        }
        //----------start-----------
            // TODO: 5/6/19 SaE passive buff
        //-------------end----------
        turn--;



        /*changing turn*/
        turn++;
        this.account = this.getEnemy(this.account).getUser();

        /*handling Mana*/
        this.account.getPlayer().setMaxMana(MAX_MANA_PER_TURN[Integer.min(turn, MAX_MANA_PER_TURN.length - 1)]);
        this.account.getPlayer().reFillMana();

        // TODO: 5/5/19 @SaE beBn bayad kari vasse Spell ha ya  SpecialPower ha beshe ya na

        /*handling coolDown*/
        this.player[0].getDeck().getHero().increaseRemainCoolDown();
        this.player[1].getDeck().getHero().increaseRemainCoolDown();
        // TODO: 5/5/19 other stuff maybe?

        for (int i = 0 ; i < 2 ; i++){
            KingSlayerCounter ksc = kingSlayerCountDown[i] ;
            if (ksc.isActive()){
                ksc.increaseCounter() ;
            }
            if (ksc.getCounter() == 15){
                player[i].getDeck().getHero().die() ;
                //TODO @arshia game over mishe inja !
            }
        }

    }

    public void showNextCard(){
        Card card=this.account.getPlayer().getHand().getNextCard();
        for (OnCardDetailsPresentedListener presenter :card.getCardDetailsPresenters()) {
            presenter.showCardDetail(card);
        }
    }

    public void showCollectable(){
        for (Collectable collectable : this.account.getPlayer().getCollectables()) {
            for (OnItemDetailPresentedListener presenter : collectable.getItemDetailPresenters()) {
                presenter.showItemDetail(collectable);
            }
        }
    }

    public void setPlayer(Player firstPlayer, Player secondPlayer){
        this.player[0]=firstPlayer;
        this.player[1]=secondPlayer;
    }
    public void addGameInfoPresentedListener(OnGameInfoPresentedListener presenter){
        this.gameInfoPresenters.add(presenter);
    }

    public ArrayList<OnGameInfoPresentedListener> getGameInfoPresenters() {
        return (ArrayList<OnGameInfoPresentedListener>) Collections.unmodifiableList(gameInfoPresenters);
    }

    public Player getEnemy(Account me) {
        if (player[0].getUser().equals(me)) return player[1];
        return player[0];
    }
    public Player getMe(Account me){
        if(player[0].getUser().equals(me))return player[0];
        return player[1];

    }

    public Map getMap() {
        return map;
    }
    public Player getPlayer(){
        return player[getTurn()] ;
    }
    public Player getEnemyPlayer(){
        return player[1-getTurn()] ;
    }
    public int getTurn() {return turn%2 ; }
    public int getOriginalTurn(){ return this.turn; }
    @Override
    public void help() {

    }

    public ArrayList<Spell> getOngoingSpells() {
        return ongoingSpells;
    }

    public static int[] getMaxManaPerTurn() {
        return MAX_MANA_PER_TURN;
    }

    public KingSlayerCounter getKingSlayerCountDown(Player player) {
        if (player.equals(this.player[0])) return kingSlayerCountDown[0];
        else if(player.equals(this.player[1])) return kingSlayerCountDown[1] ;
        return null ;
    }


    public void setTurn(int turn) {
        this.turn = turn;
    }


    public void setGameInfoPresenters(ArrayList<OnGameInfoPresentedListener> gameInfoPresenters) {
        this.gameInfoPresenters = gameInfoPresenters;
    }
}