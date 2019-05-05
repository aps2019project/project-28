package Controller;

import Controller.menu.Menu;
import Model.Map.Map;
import Model.account.*;
import Model.card.Card;
import Model.card.OnCardDetailsPresentedListener;
import Model.card.hermione.Hermione;
import Model.card.spell.Spell;
import exeption.*;

import java.util.ArrayList;
import java.util.Collections;

public class Battle extends Menu {
    private Map map;
    private Player[] player =new Player[2];
    private int turn = 0 ;
    private ArrayList<Spell> ongoingSpells = new ArrayList<>();

    private ArrayList<OnGameInfoPresentedListener>gameInfoPresenters=new ArrayList<>();

    public Battle(Menu parentMenu) {
        super(parentMenu);
        this.map=Map.generate();
    }

    public void gameInfo(){
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

    public void select(int cardID) throws InvalidCardException {
        Card card=this.account.getPlayer().getDeck().getCard(cardID);
        this.account.getPlayer().setSelectedCard(card);
    }

    public void move(int x,int y) throws NoCardHasBeenSelectedException, CardCantBeMovedException, MoveTrunIsOverException, DestinationOutOfreachException {
        try {
            Hermione hermione = (Hermione) this.account.getPlayer().getSelectedCard();
            hermione.move(x,y);
        }catch (ClassCastException e){
            throw new CardCantBeMovedException();
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
    public Player getPlayers(){
        return player[turn] ;
    }
    public void nextTurn(){
        turn++ ;
        this.account=this.getEnemy(this.account).getUser();
    }
    public int getTurn() {return turn%2 ; }
    public int getOriginalTurn(){ return this.turn; }
    @Override
    public void help() {

    }
}
