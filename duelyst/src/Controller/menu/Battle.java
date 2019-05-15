package Controller.menu;

import Controller.Game;
import Controller.GameMode.GameMode;
import Model.Map.Cell;
import Model.card.hermione.Hero;
import Model.card.spell.Buff.Buff;
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
import View.ManuHandler;
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
    private ArrayList<OnGameCardsPresentedListenr> cardsPresenters = new ArrayList<>();

    public Battle(String name) {
        super(name);
    }

    public static Battle getMenu() {
        if (Battle.menu == null) {
            Battle.menu = new Battle("Battle");
        }
        return menu;
    }

    @Override
    public boolean init(Menu parentMenu) {
        super.init(parentMenu);
        if (Game.accounts[0].getCollection().getMainDeck() == null || Game.accounts[1].getCollection().getMainDeck() == null) {
            System.out.println("Please Select your Main Deck");
            return false;
        }

        setPlayer(Game.accounts[0].getPlayer(), Game.accounts[1].getPlayer());
        this.map = this.gameMode.generateMap();

        try {
            this.insert(this.player[0].getDeck().getHero(),this.map.getCell(3, 1));
            this.insert(this.player[1].getDeck().getHero(),this.map.getCell(3, 9));
//            this.map.getCell(3, 1).setCardOnCell(this.player[0].getDeck().getHero());
//            this.player[0].getDeck().getHero().setLocation(this.map.getCell(3, 1));
//            System.err.println();
//            this.map.getCell(3, 9).setCardOnCell(this.player[1].getDeck().getHero());
//            this.player[1].getDeck().getHero().setLocation(this.map.getCell(3, 9));
        } catch (InvalidCellException e) {
            e.printStackTrace();
        }
        return true;
    }
    private void insert(Hermione hermione, Cell cell){
        hermione.spawn(cell);
        this.map.getCell(cell).setCardOnCell(hermione);
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
        if (this.account.getPlayer().hasItem(ID)) {
            this.account.getPlayer().setSelectedItem(this.account.getPlayer().getItem(ID));
            return;
        } else if (deck.hasCard(ID)) {
            for (Minion minion : this.account.getPlayer().getMinionsInGame()) {
                if (minion.getCardID() == ID) {
                    this.account.getPlayer().setSelectedCard(minion);
                    return;
                }
            }
            if(this.account.getPlayer().getDeck().getCard(ID) instanceof Hero){
                this.account.getPlayer().setSelectedCard(this.account.getPlayer().getDeck().getCard(ID));
                return;
            }
        }
        throw new InvalidCardException();
    }


    public void move(int x, int y) throws NoCardHasBeenSelectedException, CardCantBeMovedException, MoveTrunIsOverException, DestinationOutOfreachException, InvalidCellException {
        try {
            Hermione hermione = (Hermione) this.account.getPlayer().getSelectedCard();
//            hermione.move(x, y);
            System.err.println(hermione.move(x, y));
            if (map.getCell(x, y).hasItem()) {
                this.getPlayer().getCollectables().add(map.getCell(x, y).getCollectable());
                map.getCell(x, y).clearCollectable();
            }
        } catch (ClassCastException e) {
            throw new CardCantBeMovedException();//because its Spell
        }
    }

    public void attack(int cardID) throws NoCardHasBeenSelectedException, InvalidCardException, DestinationOutOfreachException, CantAttackException, InvalidCellException {
        Hermione myHermione = (Hermione) this.account.getPlayer().getSelectedCard();
        Hermione enemyCard = (Hermione) this.getEnemy(this.account).getDeck().getCard(cardID);
        myHermione.attack(enemyCard);
        handleDeaths();
    }

    public void attackCombo() {
        // TODO: 5/5/19 COMMMBOOOOOOO
        handleDeaths();
    }

    public void useSpecialPower(int x, int y) {
        this.account.getPlayer().getDeck().getHero().applySpecialPower(x, y);
        handleDeaths();
    }

    public void showHand() {
        Hand hand = this.account.getPlayer().getHand();
        for (OnHandPresentedListener presenter : Hand.getHandPresenters()) {
            presenter.showHand(hand);
        }
    }

    public void insert(int cardID, int x, int y) throws InvalidCardException, NotEnoughManaException, DestinationIsFullException, InvalidCellException {
        Card card = this.account.getPlayer().getHand().getCard(cardID);
        if (card instanceof Hermione) {
            this.account.getPlayer().spawn(card, this.map.getCell(x, y));
            this.account.getPlayer().changeMana((-1) * card.getManaPoint());
            try {
                this.account.getPlayer().getHand().handleHand(this.account.getPlayer().getHand().getCard(cardID));
            } catch (DeckIsEmptyException | HandFullException e) {
                e.printStackTrace();
            }
        } else if (card instanceof Spell) {
            try {
                ((Spell) card).deploy(this.account.getPlayer(), Battle.getMenu().getEnemy(this.account), Battle.getMenu().getMap().getCell(x, y));
                this.account.getPlayer().changeMana((-1) * card.getManaPoint());
            } catch (InvalidCellException e) {
                e.printStackTrace();
            }
        }
        // TODO: 5/5/19 one more exception  (read the doc)
        handleDeaths();
    }


    private void handleDeaths() {
        for (int i = 0; i < 2; i++) {
            ArrayList<Minion> deadMinions = new ArrayList<>();
            for (Minion minion : this.player[i].getMinionsInGame()) {
                if (minion.getHealthPoint() <= 0)
                    deadMinions.add(minion);
            }
            this.player[i].getMinionsInGame().removeAll(deadMinions);
            this.player[i].getDeck().moveAllToGraveYard(deadMinions);
        }
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
        swapPlayers();
        for (Minion minion : this.getEnemy(this.account).getMinionsInGame()) {
            minion.itIsTime(SPATime.PASSIVE);
        }
        swapPlayers();

        //----------start-----------
        handleBuffs("end");
        //-------------end----------

        this.account = this.getEnemy(this.account).getUser();
        swapPlayers();
        System.err.println(this.account.getName() + " , " + this.getEnemy(this.account).getUser().getName());
        /*handling Mana*/
        this.account.getPlayer().setMaxMana(MAX_MANA_PER_TURN[Integer.min(turn, MAX_MANA_PER_TURN.length - 1)]);
        this.account.getPlayer().reFillMana();

        // TODO: 5/5/19 @SaE beBn bayad kari vasse Spell ha ya  SpecialPower ha beshe ya na

        /*handling coolDown*/
        try {
            this.player[0].getDeck().getHero().increaseRemainCoolDown();
            this.player[1].getDeck().getHero().increaseRemainCoolDown();
        } catch (NullPointerException ignored) {
        }
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
        if (this.gameMode.checkState()) {
            this.gameMode.handleWin();
            ManuHandler.currentMenu = this.exit();
        } else {
            nextTurn();
        }

    }

    private void swapPlayers() {
        Player temp = this.player[0];
        this.player[0] = this.player[1];
        this.player[1] = temp;
    }


    private void handleBuffs(String endOrBeginning) {
        for (int i = 0; i < 2; i++) {
            turn += i == 1 ? 1 : 0;
            Player plyr = player[getTurn()];
            try {
                for (Buff buff : plyr.getDeck().getHero().getAppliedBuffs()) {
                    if (endOrBeginning.equals("end"))
                        deployBuffEndTurn(buff);
                    else deployBuffBeginningOfTurn(buff);
                }
            } catch (NullPointerException ignored) {
            }
            try {
                for (Minion minion : plyr.getMinionsInGame()) {
                    for (Buff buff : minion.getAppliedBuffs()) {
                        if (endOrBeginning.equals("end"))
                            deployBuffEndTurn(buff);
                        else deployBuffBeginningOfTurn(buff);
                    }
                }
                turn += i == 1 ? -1 : 0;
            } catch (NullPointerException ignored) {
            }
        }
    }

    private void nextTurn() {
        turn++;


        //TODO arshia karaye marbut be turn e jadid o inja bokon (mana o updateHand o ina)
        for (Minion minion : this.account.getPlayer().getMinionsInGame()) {
            minion.setActionTurn(0);
        }
        this.account.getPlayer().getDeck().getHero().setActionTurn(0);
        handleBuffs("beginning");

    }

    private void deployBuffEndTurn(Buff buff) {
        try {
            buff.handleBuffEndOfTurn();
        } catch (InvalidCellException ignored) {
            System.err.println("buff handle in battle menu end of turn");
        }
    }

    private void deployBuffBeginningOfTurn(Buff buff) {
        try {
            buff.handleBuffBeginningOfTurn();
        } catch (InvalidCellException | BuffHasntBeenDeployedYetException ignored) {
            System.err.println("buff handle in battle menu beginning of turn");
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
            System.err.println(collectable.getName());
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
        return player[0];
    }

    public Player getEnemyPlayer() {
        return player[1];
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
        System.out.println("13)Show hand     14)Insert [card name] in (x, y)     15)Show Next Card");
        System.out.println("16)End turn     17)Show collectables     18)Select [collectable id]");
        System.out.println("19)End Game   20)Use [location x, y]     21)Help");
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void addCardPresentedListener(OnGameCardsPresentedListenr listener) {
        this.cardsPresenters.add(listener);
    }

    public ArrayList<OnGameCardsPresentedListenr> getCardsPresenters() {
        return cardsPresenters;
    }

    public void useItem(int x, int y) throws InvalidCellException {
        this.account.getPlayer().getSelectedItem().deploy(Game.battle.getMap().getCell(x, y));
        // TODO: 5/5/19 saE doroste dg?
    }

    public void showInfo() throws NoItemHasBeenSelectedException {
        Item item = this.account.getPlayer().getSelectedItem();
        if (item == null) throw new NoItemHasBeenSelectedException();
        for (OnItemDetailPresentedListener presenter : item.getItemDetailPresenters()) {
            presenter.showItemDetail(item);
        }
    }

}
