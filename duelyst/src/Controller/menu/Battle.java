package Controller.menu;

import Controller.Game;
import Controller.GameMode.GameMode;
import Controller.menu.Menu;
import Model.item.Item;
import View.Listeners.OnGameCardsPresentedListenr;
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
import View.Listeners.OnHandPresentedListener;
import exeption.*;

import java.util.ArrayList;
import java.util.Collections;

public class Battle extends Menu {

    private static Battle menu;

    private Map map;
    private Player[] player = new Player[2];
    private int turn = 0;
    private ArrayList<Spell> ongoingSpells = new ArrayList<>();
    private static final int[] MAX_MANA_PER_TURN = {2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9};

    private GameMode gameMode;


    private KingSlayerCounter[] kingSlayerCountDown =
            {new KingSlayerCounter(player[0]), new KingSlayerCounter(player[1])};

    private ArrayList<OnGameInfoPresentedListener> gameInfoPresenters = new ArrayList<>();
    private ArrayList<OnGameCardsPresentedListenr> cardsPresenters=new ArrayList<>();
    public Battle( String name) {
        super(name);
    }

    public static Battle getMenu() {
        if(Battle.menu==null){
            Battle.menu=new Battle("Battle");
        }
        return menu;
    }

    @Override
    public boolean init(Menu parentMenu) {
        super.init(parentMenu);
        if(Game.accounts[0].getCollection().getMainDeck()==null || Game.accounts[1].getCollection().getMainDeck()==null){
            System.out.println("Please Select your Main Deck");
            return false;
        }
        setPlayer(Game.accounts[0].getPlayer(),Game.accounts[1].getPlayer());
        this.map = Map.generate();
        try {
            this.player[0].getDeck().getHero().setLocation(this.map.getCell(1,3));
            this.player[1].getDeck().getHero().setLocation(this.map.getCell(9,3));
        } catch (InvalidCellException ignored){}
        return true;
    }

    public void gameInfo() {
        for (OnGameInfoPresentedListener presenter : this.gameInfoPresenters) {
            presenter.showGameInfo();
        }
    }

    public void showMyMinions() {

        System.out.println("HERO:");
        for (OnGameCardsPresentedListenr presenter : Battle.getMenu().getCardsPresenters()) {
            presenter.showCard(this.account.getPlayer().getDeck().getHero());
        }
        System.out.println("MINIONS:");
        for (Minion minion : this.account.getPlayer().getMinionsInGame()) {
            for (OnGameCardsPresentedListenr presenter : Battle.getMenu().getCardsPresenters()) {
                    presenter.showCard(minion);
                }
        }

    }

    public void showMyOpponentMinion() {
        System.out.println("HERO:");
        for (OnGameCardsPresentedListenr presenter : Battle.getMenu().getCardsPresenters()) {
            presenter.showCard(this.getEnemy(this.account).getDeck().getHero());
        }
        System.out.println("MINIONS:");
        for (Minion minion : this.getEnemy(this.account).getMinionsInGame()) {
            for (OnGameCardsPresentedListenr presenter : Battle.getMenu().getCardsPresenters()) {
                presenter.showCard(minion);
            }
        }
    }

    public void showCardInfo(int cardID) throws InvalidCardException {
        Card card = this.account.getPlayer().getDeck().getCard(cardID);
        for (OnCardDetailsPresentedListener presenter : Card.getCardDetailsPresenters()) {
            presenter.showCardInfo(card);
        }
    }

    public void select(int ID) throws InvalidCardException, InvalidItemException {
        Deck deck = this.account.getPlayer().getDeck();
        if (deck.hasCard(ID)) {
            Card card = deck.getCard(ID);
            this.account.getPlayer().setSelectedCard(card);
        } else if (this.account.getPlayer().hasItem(ID)) {
            this.account.getPlayer().setSelectedItem(this.account.getPlayer().getItem(ID));
        }
    }


    public void move(int x, int y) throws NoCardHasBeenSelectedException, CardCantBeMovedException, MoveTrunIsOverException, DestinationOutOfreachException, InvalidCellException {
        try {
            Hermione hermione = (Hermione) this.account.getPlayer().getSelectedCard();
            hermione.move(x, y);
        } catch (ClassCastException e) {
            throw new CardCantBeMovedException();//because its Spell
        }
    }

    public void attack(int cardID) throws NoCardHasBeenSelectedException, InvalidCardException, DestinationOutOfreachException, CantAttackException, InvalidCellException {
        Hermione myHermione = (Hermione) this.account.getPlayer().getSelectedCard();
        Hermione enemyCard = (Hermione) this.getEnemy(this.account).getDeck().getCard(cardID);

        myHermione.attack(enemyCard);
    }

    public void attackCombo() {
        // TODO: 5/5/19 COMMMBOOOOOOO
    }

    public void useSpecialPower(int x, int y) {
        this.account.getPlayer().getDeck().getHero().applySpecialPower(x, y);
    }

    public void showHand() {
        Hand hand = this.account.getPlayer().getHand();
        for (OnHandPresentedListener presenter : Hand.getHandPresenters()) {
            presenter.showHand(hand);
        }
    }

    public void insert(int cardID, int x, int y) throws InvalidCardException, NotEnoughManaException, DestinationIsFullException, InvalidCellException {
        this.account.getPlayer().spawn(this.account.getPlayer().getHand().getCard(cardID), this.map.getCell(x, y));
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

        for (int i = 0; i < 2; i++) {
            KingSlayerCounter ksc = kingSlayerCountDown[i];
            if (ksc.isActive()) {
                ksc.increaseCounter();
            }
            if (ksc.getCounter() == 15) {
                player[i].getDeck().getHero().die();
                //TODO @arshia game over mishe inja !
            }
        }

        /*checkState*/
        if(this.gameMode.checkState()){
            this.gameMode.handleWin();
        }
    }

    public void showNextCard() {
        Card card = this.account.getPlayer().getHand().getNextCard();
        for (OnCardDetailsPresentedListener presenter : Card.getCardDetailsPresenters()) {
            presenter.showCardDetail(card);
        }
    }

    public void showCollectable() {
        for (Collectable collectable : this.account.getPlayer().getCollectables()) {
            for (OnItemDetailPresentedListener presenter : Item.getItemDetailPresenters()) {
                presenter.showItemDetail(collectable);
            }
        }
    }

    public void setPlayer(Player firstPlayer, Player secondPlayer) {
        this.player[0] = firstPlayer;
        this.player[1] = secondPlayer;
    }

    public void addGameInfoPresentedListener(OnGameInfoPresentedListener presenter) {
        this.gameInfoPresenters.add(presenter);
    }

    public ArrayList<OnGameInfoPresentedListener> getGameInfoPresenters() {
        return (ArrayList<OnGameInfoPresentedListener>) Collections.unmodifiableList(gameInfoPresenters);
    }

    public Player getEnemy(Account me) {
        if (player[0].getUser().equals(me)) return player[1];
        return player[0];
    }

    public Player getMe(Account me) {
        if (player[0].getUser().equals(me)) return player[0];
        return player[1];

    }

    public Map getMap() {
        return map;
    }

    public Player getPlayer() {
        return player[getTurn()];
    }

    public Player getEnemyPlayer() {
        return player[1 - getTurn()];
    }

    public int getTurn() {
        return turn % 2;
    }

    public int getOriginalTurn() {
        return this.turn;
    }


    public ArrayList<Spell> getOngoingSpells() {
        return ongoingSpells;
    }

    public static int[] getMaxManaPerTurn() {
        return MAX_MANA_PER_TURN;
    }

    public KingSlayerCounter getKingSlayerCountDown(Player player) {
        if (player.equals(this.player[0])) return kingSlayerCountDown[0];
        else if (player.equals(this.player[1])) return kingSlayerCountDown[1];
        return null;
    }


    public void setTurn(int turn) {
        this.turn = turn;
    }


    public void setGameInfoPresenters(ArrayList<OnGameInfoPresentedListener> gameInfoPresenters) {
        this.gameInfoPresenters = gameInfoPresenters;
    }

    @Override
    public void help() {
        super.help();
        System.out.println("4)Game info     5)Show my minions     6)Show opponent minions");
        System.out.println("7)Show card info [card id]     8)Select [card id]     9)Move to ([x], [y])");
        System.out.println("10)Attack [opponent card id]     11)Attack combo [opponent card id] [my card id] [my card id] [...]     12)Use special power (x, y)");
        System.out.println("13)Show hand     14)Insert [card name] in (x, y)     15)Insert [card name] in (x, y)");
        System.out.println("16)End turn     17)Show collectables     18)Select [collectable id]");
        System.out.println("19)Show info     20)Use [location x, y]     21)Show Next Card");
        System.out.println("22)Help    23)End Game     ");
    }

    public GameMode getGameMode() {
        return gameMode;
    }
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }
    public void addCardPresentedListener(OnGameCardsPresentedListenr listener){
        this.cardsPresenters.add(listener);
    }
    public ArrayList<OnGameCardsPresentedListenr> getCardsPresenters() {
        return cardsPresenters;
    }
}
