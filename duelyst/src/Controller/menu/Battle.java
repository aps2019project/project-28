package Controller.menu;

import Controller.Game;
import Controller.GameMode.GameMode;
import Model.Map.Cell;
import Model.Primary;
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
import Model.item.OnItemDetailPresentedListener;
import View.Listeners.OnHandPresentedListener;
import View.MenuHandler;
import exeption.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class Battle extends Menu {

    private static Battle menu;

    private Map map;
    private Player[] player = new Player[2];
    private int turn = 0;
    private ArrayList<Spell> ongoingSpells = new ArrayList<>();
    private static final int[] MAX_MANA_PER_TURN = {2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9};

    private Match match;

    private GameMode gameMode;


    private ArrayList<OnGameInfoPresentedListener> gameInfoPresenters = new ArrayList<>();

    private ArrayList<OnGameCardsPresentedListenr> cardsPresenters = new ArrayList<>();

    private Battle(String name) {
        super(name);
    }


    public static Battle getMenu() {
        if (Battle.menu == null) {
            Battle.menu = new Battle("Battle");
        }
        return menu;
    }


//    @Override
//    protected void init() {
//        super.init();
//    }

    @Override
    public boolean init(Menu parentMenu) {
        System.out.println("initiating Battle in " + this.gameMode.getClass());
        super.init(parentMenu);
        try {
            Primary.initGraphics();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.match=new Match(Game.accounts[0],Game.accounts[1],this.gameMode);
        this.ongoingSpells=new ArrayList<>();

        if (Game.accounts[0].getCollection().getMainDeck() == null || Game.accounts[1].getCollection().getMainDeck() == null) {
            System.out.println("Please Select your Main Deck");
            return false;
        }

        setPlayer(Game.accounts[0].getPlayer(), Game.accounts[1].getPlayer());
        this.map = this.gameMode.generateMap();

        try {
            this.insert(this.player[0].getDeck().getHero(), this.map.getCell(Map.FIRST_HERO_X, Map.FIRST_HERO_Y));
            this.insert(this.player[1].getDeck().getHero(), this.map.getCell(Map.SECOND_HERO_X, Map.SECOND_HERO_Y));
        } catch (NullPointerException e){
            e.printStackTrace();
            System.out.println("\n");
            if (this.player[1] == null) System.err.println("player 1 is null");
            else if (this.player[1].getDeck() == null) System.err.println("deck is null !");
            else if (this.player[1].getDeck().getHero() == null) System.err.println("hero is null");
            else if (this.map == null) System.err.println("map is null !");
            else {
                try {
                    if (this.map.getCell(Map.SECOND_HERO_X, Map.SECOND_HERO_Y) == null) System.err.println("getCell is null");
                } catch (InvalidCellException ex) {
                    ex.printStackTrace();
                }
                System.out.println("\n\n\n");
            }
        }catch (InvalidCellException e) {e.printStackTrace();}

        return true;
    }

    private void insert(Hermione hermione, Cell cell) throws InvalidCellException {
        System.out.println("hermione.getGraphics() = " + hermione.getGraphics());
        hermione.spawn(cell);
        this.map.getCell(cell).setCardOnCell(hermione);
    }

    public void insert(int cardID, int x, int y) throws InvalidCardException, NotEnoughManaException, DestinationIsFullException, InvalidCellException {
        Card card = this.account.getPlayer().getHand().getCard(cardID);

        if (card instanceof Hermione) {

            this.account.getPlayer().deploy(card, this.map.getCell(x, y));
            this.account.getPlayer().changeMana((-1) * card.getManaPoint());

            try { this.account.getPlayer().getHand().handleHand(card);
            } catch (DeckIsEmptyException | HandFullException ignored) { ignored.printStackTrace(); }

        } else if (card instanceof Spell) {
            try {
                ((Spell) card).deploy(this.account.getPlayer(), Battle.getMenu().getEnemy(this.account), Battle.getMenu().getMap().getCell(x, y));
                this.account.getPlayer().changeMana((-1) * card.getManaPoint());
                this.account.getPlayer().getHand().handleHand(card);
            } catch (InvalidCellException | HandFullException | DeckIsEmptyException ignored) { ignored.printStackTrace();}
        }
        // TODO: 5/5/19 one more exception  (read the doc)
        handleDeaths();
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
        System.err.println();
        Deck deck = this.account.getPlayer().getDeck();
        if (this.account.getPlayer().hasItem(ID)) {
            this.account.getPlayer().setSelectedItem(this.account.getPlayer().getItem(ID));
            return;
        } else if (deck.hasCard(ID)) {
            //// TODO: 6/8/19 instance of
            for (Minion minion : this.account.getPlayer().getMinionsInGame()) {
                if (minion.getCardID() == ID) {
                    this.account.getPlayer().setSelectedCard(minion);
                    return;
                }
            }
            if (this.account.getPlayer().getDeck().getCard(ID) instanceof Hero) {
                this.account.getPlayer().setSelectedCard(this.account.getPlayer().getDeck().getCard(ID));
                return;
            }
        }
        throw new InvalidCardException();
    }

    public void move(int x, int y) throws NoCardHasBeenSelectedException, CardCantBeMovedException, MoveTrunIsOverException, DestinationOutOfreachException, InvalidCellException, DestinationIsFullException {
        try {
            Hermione hermione = (Hermione) this.account.getPlayer().getSelectedCard();


            if(hermione.canMove(x,y))this.getMap().getCell(hermione.getLocation()).clear();
            hermione.move(x, y);


            if (this.getMap().getCell(x, y).hasFlag()) {
                hermione.setNumberOfFlags(hermione.getNumberOfFlags());
                hermione.setFlag(true);
            }

            this.getMap().getCell(x, y).setCardOnCell(hermione);

            if (map.getCell(x, y).hasItem()) {
                this.getPlayer().getCollectables().add(map.getCell(x, y).getCollectable());
                map.getCell(x, y).clearCollectable();
            }

            this.gameMode.getFlag(this.account.getPlayer(), hermione,
                    map.getCell(x, y));

        } catch (ClassCastException e) {
            throw new CardCantBeMovedException();//because its Spell
        }
    }

    public void attack(int cardID) throws NoCardHasBeenSelectedException, InvalidCardException, DestinationOutOfreachException, CantAttackException, InvalidCellException {
        System.err.println();
        Hermione myHermione = (Hermione) this.account.getPlayer().getSelectedCard();
        Hermione enemyCard = (Hermione) this.getEnemy(this.account).getDeck().getCard(cardID);
        myHermione.attack(enemyCard,false);
        handleDeaths();
    }

    public void kill(Hermione hermione) throws InvalidCardException {


        if (hermione instanceof Hero) {
            this.playerOf(hermione).getDeck().killHero();
        }else{
            try {
                this.map.getCell(hermione.getLocation()).setFlag(hermione.hasFlag());
                this.map.getCell(hermione.getLocation()).clear();
            } catch (InvalidCellException e) {
                e.printStackTrace();
            }

            this.gameMode.handleDeath(this.playerOf(hermione), (Minion) hermione);

            this.playerOf(hermione).getMinionsInGame().remove(hermione);
            this.playerOf(hermione).getDeck().moveToGraveYard(hermione);
        }
        // TODO: 6/5/19 fatteme after making the unit test check when we kill a minion or hero at any condition(direct attack /counter attack/spell affects....)
        // TODO: 6/5/19 do they go to grave yard or not and the maps get clear or not and announce me

        try {
            Battle.getMenu().getMap().getCell(hermione.getLocation()).setFull(false);
        } catch (InvalidCellException e) {
            e.printStackTrace();
        }
        handleDeaths();
    }

    public void attackCombo(int enemyCardId,int[] troopsId) throws CantAttackException, InvalidCardException {

        /*
        * extracting the cards
        * */
        Hermione enemyCard= (Hermione) this.getEnemyPlayer().getDeck().getCard(enemyCardId);
        Hermione[]troops=new Hermione[troopsId.length];
        for(int i=0;i<troops.length;i++)troops[i]= (Hermione) this.account.getPlayer().getDeck().getCard(troopsId[i]);

        /*if the first card can attack combo*/
        if(!((Minion) troops[0]).getSPActivationTime().equals(SPATime.COMBO))return;

        /*
        * checking to see if all of the troops can attack the enemy card
        * */
        for (Hermione troop : troops) {
            try {
                troop.canAttack(enemyCard);
            } catch (DestinationOutOfreachException | CantAttackException e) {
                throw new CantAttackException();
            }
        }
        for(int i=0;i<troops.length;i++){
            try {
                troops[i].attack(enemyCard,(i!=0));
            } catch (DestinationOutOfreachException | InvalidCellException ignored) {
            }
        }


        handleDeaths();
    }

    public void useSpecialPower(int x, int y) throws InvalidCellException, CantSpecialPowerCooldownException, InvalidCardException {
        Cell cell = map.getCell(x, y);
        this.account.getPlayer().getDeck().getHero().applySpecialPower(cell);
        handleDeaths();

    }

    public void showHand() {
        Hand hand = this.account.getPlayer().getHand();
        for (OnHandPresentedListener presenter : Hand.getHandPresenters()) {
            presenter.showHand(hand);
        }
    }


    @Deprecated
    private void handleDeaths() {
        // TODO: 6/6/19 Fatteme marg ha ok budan aya? grave yard o ina
        //
//        for (int i = 0; i < 2; i++) {
//            ArrayList<Minion> deadMinions = new ArrayList<>();
//            for (Minion minion : this.player[i].getMinionsInGame()) {
//                if (minion.getHealthPoint() <= 0) {
//                    deadMinions.add(minion);
//                    this.map.getCell(minion.getLocation()).setFlag(minion.hasFlag());
//                    this.map.getCell(minion.getLocation()).clear();
//                    this.gameMode.handleDeath(this.player[i], minion);
//                }
//            }
//            this.player[i].getMinionsInGame().removeAll(deadMinions);
//            this.player[i].getDeck().moveAllToGraveYard(deadMinions);
//        }
    }

    public void endTurn() throws HandFullException, DeckIsEmptyException {

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
            this.player[0].getDeck().getHero().handleCoolDown();
            this.player[1].getDeck().getHero().handleCoolDown();
        } catch (NullPointerException ignored) {}

        // handle cellAffects
        for (Cell cell : map.getCells()) {
            if (cell != null)  cell.checkCellAffects();
        }

        // TODO: 5/5/19 other stuff maybe?


        /*checkState*/
        if (this.gameMode.checkState()) {
            handleBattleFinish();
        } else {
            nextTurn();
        }

    }

    private void handleBattleFinish() {
        /*
        * giving the prize
        * */
        this.gameMode.handleWin();

        /*
         * saving the match
         * */
        Game.accounts[0].saveMatch(this.match);
        Game.accounts[1].saveMatch(this.match);
        Account.save();


        /*
        * handling the account for getting input and stuff
        * */
        Game.accounts[1] = Account.getDefaultAccount();
        this.account = SignInMenu.getMenu().account;
        this.turn = 0;




        /*
        * getting out of battle
        * */
        MenuHandler.setCurrentMenu(MainMenu.getMenu());
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

        if (this.getPlayer().getDeck().getHero() == null) System.err.println("hero is null");
        else if (this.getPlayer().getDeck().getHero().getBuffEffects() == null) System.err.println("buffeffects are null");
        this.getPlayer().getDeck().getHero().getBuffEffects().handleOnNewTurn();

        //TODO arshia karaye marbut be turn e jadid o inja bokon (mana o updateHand o ina)
        for (Minion minion : this.account.getPlayer().getMinionsInGame()) {
            minion.getBuffEffects().handleOnNewTurn();
            minion.setActionTurn(0);
        }
        this.account.getPlayer().getDeck().getHero().setActionTurn(0);
        handleBuffs("beginning");



    }

    private void deployBuffEndTurn(Buff buff) {
        try {
            buff.handleBuffEndOfTurn();
        } catch (InvalidCellException ignored) {
            System.err.println("buff handleOnAttack in battle menu end of turn");
        }
    }

    private void deployBuffBeginningOfTurn(Buff buff) {
        try {
            buff.handleBuffBeginningOfTurn();
        } catch (InvalidCellException | BuffHasntBeenDeployedYetException ignored) {
            System.err.println("buff handleOnAttack in battle menu beginning of turn");
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

    public void showInfo() throws NoItemHasBeenSelectedException {
        Item item = this.account.getPlayer().getSelectedItem();
        if (item == null) throw new NoItemHasBeenSelectedException();
        for (OnItemDetailPresentedListener presenter : item.getItemDetailPresenters()) {
            presenter.showItemDetail(item);
        }
    }

    public void useItem(int x, int y) throws InvalidCellException, NoItemHasBeenSelectedException {
        this.account.getPlayer().getSelectedItem().deploy(Battle.getMenu().getMap().getCell(x, y));
    }

    public Player playerOf(Hermione hermione) throws InvalidCardException {
        for (int i = 0; i < 2; i++) {

            //checking for hero
            if(hermione instanceof Hermione){
                if(this.player[i].getDeck().getHero().equals(hermione))return this.player[i];
            }

            //checking in minions
            for (Card card : this.player[i].getDeck().getCards()) {
                if (card instanceof Hermione) {
                    if (card.equals(hermione)) return this.player[i];
                }
            }
        }
        throw new InvalidCardException();
    }

    public Player enemyPlayerOf(Hermione hermione) throws InvalidCardException {
        if (playerOf(hermione).equals(this.player[0])) return this.player[1];
        return this.player[0];
    }

    public static Battle newGame(GameMode gameMode){
        Battle.menu.setGameMode(gameMode);
        Battle.menu.init(Battle.menu.getParentMenu());
        return Battle.menu;
    }

    public Match getMatch() {
        return this.match;
    }
}
