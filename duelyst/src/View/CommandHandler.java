package View;

import Controller.GameMode.ClassicMode;
import Controller.GameMode.Domination;
import Controller.GameMode.FlagMode;
import Controller.menu.*;
//import SignInMenu;
import Controller.menu.SignInMenu;
import Model.Map.Cell;
import Model.Map.Map;
import Model.account.Account;
import Model.account.Collection;
import Model.account.Deck;
import Model.account.Hand;
import Model.card.Card;
import Model.card.OnCardDetailsPresentedListener;
import Model.card.hermione.Hermione;
import Model.card.hermione.Hero;
import Model.card.hermione.Minion;
import Model.card.spell.Spell;
import Model.item.Collectable;
import Model.item.Item;
import Model.item.OnItemDetailPresentedListener;
import Model.item.Usable;
import View.Listeners.OnHeroDetailsPresentedListener;
import View.Listeners.OnMenuClickedListener;
import exeption.*;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

class ShowMenu implements OnMenuClickedListener {
    @Override

    public void show(Menu menu) {
        System.out.println(menu.getName()+" :");
        int i=0;
        for (Menu subMenu : menu.getSubMenus()) {
            i++;
            System.out.println(i+") "+subMenu.getName());
        }
    }
}

public class CommandHandler {

    OutputStream output;
    PrintStream printer;

    public CommandHandler() {
        setListener();
        setPatterns();
    }
    public OutputStream getOutputStream() {
        return output;
    }

    public void setOutputStream(OutputStream output) {
        this.output = output;
        this.printer=new PrintStream(output);
    }

    public  void allCardPresenter(ArrayList<Card> cards, ArrayList<Usable> items, boolean showID){
        System.out.println("Heroes : ");
        int i=0;
        for (Card card : cards) {
            if((card instanceof Hero)) {
                i++;
                System.out.print(i+") ");
                for (OnHeroDetailsPresentedListener presenter : Hero.getHeroDetailsPresenters()) {
                    presenter.show((Hero) card);
                    if(showID) System.out.println("\tID: "+card.getCardID());
                }
            }
        }
        this.printer.println("Cards : ");
        i=0;
        for (Card card : cards) {
            if(!(card instanceof Hero)){
                i++;
                this.printer.print(i+") ");
                for (OnCardDetailsPresentedListener presenter : Card.getCardDetailsPresenters()) {
                    presenter.showCardDetail(card);
//                    if(showID) this.printer.println("\tID: "+card.getCardID());
                }
            }
        }
        this.printer.println("Items : ");
        i=0;
        for (Usable item : items) {
            i++;
            this.printer.println(i+") ");
            for (OnItemDetailPresentedListener presenter : Item.getItemDetailPresenters()) {
                presenter.showItemDetail(item);
                if(showID) this.printer.println("\tID: "+item.getID());
            }
        }
    }
    private  void setListener(){
        //show menu
        {
            SignInMenu.getMenu().addMenuClickListener(new ShowMenu());
            Battle.getMenu().addMenuClickListener(new ShowMenu());
            Battle.getMenu().addMenuClickListener(menu -> {
                Battle battle= (Battle) menu;
                for(int j = 0; j< Map.BALA_PAEEN_Y; j++){
                    for(int i=0;i< Map.CHAP_RAST_X;i++){
                        try {
                            Cell cell=battle.getMap().getCell(i,j);
                            if(cell.getCardOnCell() instanceof Hero){
                                this.printer.print("H ");
                            }else if(cell.getCardOnCell() instanceof Minion){
                                if(battle.getPlayer().getMinionsInGame().contains(cell.getCardOnCell())){
                                    this.printer.print("M ");
                                }else{
                                    this.printer.print("W ");
                                }
                            }else if(cell.hasItem()) this.printer.print("C ");
                            else if(cell.hasFlag()) this.printer.print("F ");
                            else if(cell.getCardOnCell()==null) this.printer.print(". ");
                        } catch (InvalidCellException e) {
                            e.printStackTrace(); }
                    }
                    this.printer.println();
                }
            });
            ChooseBattleModeMenu.getMenu().addMenuClickListener(menu -> {
                this.printer.println("Modes:");
                this.printer.println("1)Classic      2)flag mode     3)Domination");
            });
            CollectableMenu.getMenu().addMenuClickListener(new ShowMenu());
            CollectionMenu.getMenu().addMenuClickListener(new ShowMenu());
            CustomModeMenu.getMenu().addMenuClickListener(new ShowMenu());
            GameModeMenu.getMenu().addMenuClickListener(new ShowMenu());
            GraveYardMenu.getMenu().addMenuClickListener(new ShowMenu());
            MainMenu.getMenu().addMenuClickListener(new ShowMenu());
            MultiPlayerModeMenu.getMenu().addMenuClickListener(menu -> {
                this.printer.println("Accounts: ");
                int i=0;
                for (Account account : Account.getAccounts()) {
                    i++;
                    this.printer.println("\t"+i+") UserName:"+account.getUsername()+"\tName: "+account.getName());
                }
            });
            ShopMenu.getMenu().addMenuClickListener(new ShowMenu());
            SinglePlayerModeMenu.getMenu().addMenuClickListener(new ShowMenu());
            StoryModeMenu.getMenu().addMenuClickListener(menu -> {
                this.printer.println("Levels:");
                this.printer.println("1) welcome to our world     2) wrath of the Mighty     3) the last fiction");
            });
        }
        //signIn menu
        SignInMenu.getMenu().addLeaderBoardClickedListener((accounts, fxmlc) -> {
            this.printer.println("LeaderBoard:");
            int i = 0;
            for (Account account : accounts) {
                i++;
                this.printer.println(i + ") " + account.getName() + " - Wins: " + account.getWins());
            }
        });

        //Collection
        Collection.addCollectionPresentedListener((collection, name) -> {
            this.printer.println(name + " : ");
            allCardPresenter(collection.getCards(), collection.getItems(),true);
        });
        //Card
        Card.addOnCardDetailPresented(new OnCardDetailsPresentedListener() {
            private void showSpell(Spell s){
                System.out.println("Type : Spell");
                System.out.println("\tName : " + s.getName());
                System.out.println("\tManaPoint : " + s.getManaPoint());
                System.out.println("\tAction : " + s.getInfo());
                System.out.println("\tSell cost : " + s.getPrice());
                System.out.println("\tName : " + s.getName());
                System.out.println("\tID : "+s.getCardID());
            }
            private void showHermioneDetail(Hermione h){
                System.out.println("\tName : " + h.getName());
                System.out.println("\tClass : " + h.getAttackType().getClass().toString());
                System.out.print("\tAttackPoint : " + h.getBuffEffects().getOriginalAttackPoint());
                System.out.println("\tHealth point : " + h.getOriginalHealthPoint());
                System.out.println("\tManaPoint : " + h.getManaPoint());
//                System.out.println("\tSpecialPower : " + h.getSpecialPower().getInfo());
                System.out.println("\tSell cost : " + h.getPrice());
                System.out.println("\tID : "+h.getCardID());
            }
            private void showHermioneInfo(Hermione h){
                System.out.println("\tName : " + h.getName());
                System.out.println("\tClass : " + h.getAttackType().getClass().toString());
                System.out.println("\tAttackPoint : " + h.getAttackPoint() +
                        "\tHealth point : " + h.getHealthPoint() + "\tManaPoint : " + h.getSpecialPower().getManaPoint());
                System.out.println("\tSpecialPower : " + h.getSpecialPower().getInfo());
                System.out.println("\tSell cost : " + h.getPrice());
                System.out.println("\tID : "+h.getCardID());
            }

            @Override
            public void showCardDetail(Card card) {
                if (card instanceof Spell) {
                    Spell s = (Spell) card;
                    showSpell(s);
                } else {
                    if (card instanceof Minion)
                        System.out.println("Type : Minion");
                    else
                        System.out.println("Type : Hero");

                    Hermione h = (Hermione) card;
                    showHermioneDetail(h);
                }
            }

            @Override
            public void showCardInfo(Card card) {
                if (card instanceof Spell) {
                    Spell s = (Spell) card;
                    showSpell(s);
                } else {
                    if (card instanceof Minion)
                        System.out.println("Type : Minion");
                    else
                        System.out.println("Type : Hero");
                    Hermione h = (Hermione) card;
                    showHermioneInfo(h);
                }

            }
        });

        //hero
        Hero.addOnHeroDetailPresented(hero -> {
            this.printer.println("\tName : " + hero.getName());
            this.printer.println("\tAttackPoint : " + hero.getBuffEffects().getOriginalAttackPoint() +
                    "\tHealth point : " + hero.getOriginalHealthPoint() + "\tManaPoint : ");
            this.printer.println("\tClass : " + hero.getAttackType().getClass().toString());
            this.printer.println("\tSell cost : " + hero.getPrice());
        });

        //deck
        Deck.addNewOnDeckPresentedListener(deck -> allCardPresenter(deck.getCards(),deck.getUsables(),true));

        //GameInfo
        Battle.getMenu().addGameInfoPresentedListener(() -> {
            if(Battle.getMenu().getGameMode() instanceof ClassicMode){
                this.printer.println(Battle.getMenu().getAccount().getName()+" : ");
                this.printer.println("\tMANA: "+Battle.getMenu().getAccount().getPlayer().getMana());
                this.printer.println("\t"+Battle.getMenu().getAccount().getPlayer().getDeck().getHero().getName()+" : "+Battle.getMenu().getAccount().getPlayer().getDeck().getHero().getHealthPoint());
                this.printer.println(Battle.getMenu().getEnemy(Battle.getMenu().getAccount()).getUser().getName()+" : ");
                this.printer.println("\tMANA: "+Battle.getMenu().getEnemy(Battle.getMenu().getAccount()).getMana());
                this.printer.println("\t"+Battle.getMenu().getEnemy(Battle.getMenu().getAccount()).getDeck().getHero().getName()+" : "+Battle.getMenu().getEnemy(Battle.getMenu().getAccount()).getDeck().getHero().getHealthPoint());
//          }else if(Battle.getMenu().getGameMode() instanceof FlagMode){

            }
            else if(Battle.getMenu().getGameMode() instanceof FlagMode){
                this.printer.println("Game info");
                for (Cell cell:
                        Battle.getMenu().getMap().getCells()) {
                    if(cell.hasFlag()){
                        this.printer.println("Flag Location :" + cell.getX() + "," + cell.getY());
                    }
                }
                boolean hasflag = false;
                for (Card card:
                        Battle.getMenu().getPlayer().getDeck().getCards()) {
                    if(card instanceof Hermione){
                        if(((Hermione)card).hasFlag()){
                            this.printer.println("Flag owner:"+ card.getName());
                            hasflag = true;
                        }
                    }
                }
                for (Card card:
                        Battle.getMenu().getEnemyPlayer().getDeck().getCards()) {
                    if(card instanceof Hermione){
                        if(((Hermione)card).hasFlag()){
                            this.printer.println("Flag owner :"+ card.getName());
                            hasflag = true;
                        }
                    }
                }
                if(!hasflag){
                    this.printer.println("Nobody got the flag !");
                }
            }
            else if(Battle.getMenu().getGameMode() instanceof Domination){
                this.printer.println("Game info");
                boolean hasflag = false;
                for (Card card:
                        Battle.getMenu().getPlayer().getDeck().getCards()) {
                    if(card instanceof Hermione){
                        if(((Hermione)card).hasFlag()){
                            this.printer.println("From my team "+ card.getName());
                            hasflag = true;
                        }
                    }
                }
                for (Card card:
                        Battle.getMenu().getEnemyPlayer().getDeck().getCards()) {
                    if(card instanceof Hermione){
                        if(((Hermione)card).hasFlag()){
                            this.printer.println("From opponent's team "+ card.getName());
                            hasflag = true;
                        }
                    }
                }
                if(!hasflag){
                    this.printer.println("Nobody got any flag!");
                }
            }
        });

        //Hand
        Hand.addOnHandPresentedListener(hand -> {
            int i=0;
            for (Card card : hand.getCards()) {
                i++;
                this.printer.println();
                this.printer.println(i+ ") "+ card.getName());
                this.printer.println("\tid: "+card.getCardID());
            }
        });

        //Item
        Item.addNewOnItemDeatilPresentedListener(item -> {
            this.printer.println("\tName : " + item.getName());
            this.printer.println("\tDesc : " + item.getComment());
            if(item instanceof Usable)
                this.printer.println("\tSell cost" + ((Usable) item).getPrice());
            else if(item instanceof Collectable)
                this.printer.println("\tID" + (item).getID());
        });
        //game card presentedListener
        Battle.getMenu().addCardPresentedListener(card -> {
            Hermione h= (Hermione) card;
            this.printer.println("\t" + h.getName() + " : ");
            this.printer.println("\t\tHealth Point: " + h.getHealthPoint() + ",location: " + h.getLocation().getX() + "," + h.getLocation().getY());
            this.printer.println("\t\tAction: "+h.getInfo());
            this.printer.println("\t\tID: "+h.getCardID());
        });

    }

    //paturns
    public static void setPatterns(){
        setChooseBattleModePattern();
        setSignInPatterns();
        setCollectionPatterns();
        setShopPatterns();
        setBattlePatterns();
        setGraveyardPatterns();
        setMainMenuPattern();
        setGameModeMenuPatterns();
        setStoryModePattern();
        setSinglePlayerModePattern();
        setMultiPlayerModeMenuPattern();
        setCustomModeMenuPattern();
    }
    private static void setCustomModeMenuPattern() {
        CustomModeMenu.getMenu().addPattern("enter [\\w]+");
        CustomModeMenu.getMenu().addPattern("[\\d]+");
        CustomModeMenu.getMenu().addPattern("help");
        CustomModeMenu.getMenu().addPattern("show");
        CustomModeMenu.getMenu().addPattern("exit");
        CustomModeMenu.getMenu().addPattern("select [\\w]+");
    }
    private static void setMultiPlayerModeMenuPattern() {
        MultiPlayerModeMenu.getMenu().addPattern("enter [\\w]+");
        MultiPlayerModeMenu.getMenu().addPattern("[\\d]+");
        MultiPlayerModeMenu.getMenu().addPattern("help");
        MultiPlayerModeMenu.getMenu().addPattern("show");
        MultiPlayerModeMenu.getMenu().addPattern("exit");
        MultiPlayerModeMenu.getMenu().addPattern("select user [\\w]+ [\\w]+");
    }
    private static void setSinglePlayerModePattern() {
        SinglePlayerModeMenu.getMenu().addPattern("enter [\\w]+");
        SinglePlayerModeMenu.getMenu().addPattern("[\\d]+");
        SinglePlayerModeMenu.getMenu().addPattern("help");
        SinglePlayerModeMenu.getMenu().addPattern("show");
        SinglePlayerModeMenu.getMenu().addPattern("exit");
        SinglePlayerModeMenu.getMenu().addPattern("level [\\d]+");
    }
    private static void setStoryModePattern() {
        StoryModeMenu.getMenu().addPattern("enter [\\w]+");
        StoryModeMenu.getMenu().addPattern("[\\d]+");
        StoryModeMenu.getMenu().addPattern("help");
        StoryModeMenu.getMenu().addPattern("show");
        StoryModeMenu.getMenu().addPattern("exit");
        StoryModeMenu.getMenu().addPattern("level [\\d]+");
    }
    public static void setSignInPatterns() {
        SignInMenu.getMenu().addPattern("enter [\\w]+");
        SignInMenu.getMenu().addPattern("[\\d]+");
        SignInMenu.getMenu().addPattern("help");
        SignInMenu.getMenu().addPattern("show");
        SignInMenu.getMenu().addPattern("exit");
        SignInMenu.getMenu().addPattern("create account [\\w]+ [\\w]+ [\\w]+");
        SignInMenu.getMenu().addPattern("login [\\w]+ [\\w]+");
        SignInMenu.getMenu().addPattern("show leaderboard");
        SignInMenu.getMenu().addPattern("save");
        SignInMenu.getMenu().addPattern("logout");
    }
    public static void setCollectionPatterns(){
        CollectableMenu.getMenu().addPattern("enter [\\w]+");
        CollectionMenu.getMenu().addPattern("[\\d]+");
        CollectionMenu.getMenu().addPattern("help");
        CollectionMenu.getMenu().addPattern("show");
        CollectionMenu.getMenu().addPattern("exit");
        CollectionMenu.getMenu().addPattern("show");
        CollectionMenu.getMenu().addPattern("search ([\\w]+\\s?)+");
        CollectionMenu.getMenu().addPattern("save");
        CollectionMenu.getMenu().addPattern("create deck [\\w]+");
        CollectionMenu.getMenu().addPattern("create deck ([\\w]+\\s?)+");
        CollectionMenu.getMenu().addPattern("delete deck [\\w]+");
        CollectionMenu.getMenu().addPattern("add [\\d]+ to deck ([\\w]+\\s?)+");
        CollectionMenu.getMenu().addPattern("remove [\\d]+ from deck ([\\w]+\\s?)+");
        CollectionMenu.getMenu().addPattern("validate deck ([\\w]+\\s?)+");
        CollectionMenu.getMenu().addPattern("select deck ([\\w]+\\s?)+");
        CollectionMenu.getMenu().addPattern("show all decks");
        CollectionMenu.getMenu().addPattern("show deck ([\\w]+\\s?)+");
    }
    public static void setShopPatterns(){
        ShopMenu.getMenu().addPattern("enter [\\w]+");
        ShopMenu.getMenu().addPattern("[\\d]+");
        ShopMenu.getMenu().addPattern("help");
        ShopMenu.getMenu().addPattern("show");
        ShopMenu.getMenu().addPattern("exit");
        ShopMenu.getMenu().addPattern("show collection");
        ShopMenu.getMenu().addPattern("search( [\\w]+)+");
        ShopMenu.getMenu().addPattern("search collection( [\\w]+)+");
        ShopMenu.getMenu().addPattern("buy( [\\w]+)+");
        ShopMenu.getMenu().addPattern("sell( [\\w]+)+");
        ShopMenu.getMenu().addPattern("show");
    }
    public static void setBattlePatterns(){
        Battle.getMenu().addPattern("enter [\\w]+");
        Battle.getMenu().addPattern("[\\d]+");
        Battle.getMenu().addPattern("help");
        Battle.getMenu().addPattern("exit");
        Battle.getMenu().addPattern("show");
        Battle.getMenu().addPattern("game info");
        Battle.getMenu().addPattern("show hand");
        Battle.getMenu().addPattern("show my minions");
        Battle.getMenu().addPattern("show opponent minions");
        Battle.getMenu().addPattern("show card info [\\d]+");
        Battle.getMenu().addPattern("show next card");
        Battle.getMenu().addPattern("show collectables");
        Battle.getMenu().addPattern("show info");
        Battle.getMenu().addPattern("select [\\d]+");
        Battle.getMenu().addPattern("move to [\\d]+ [\\d]+");
        Battle.getMenu().addPattern("attack [\\d]+");
        Battle.getMenu().addPattern("attack combo [\\d]+ [\\d]+[ \\d+]+");
        Battle.getMenu().addPattern("use special power [\\d]+ [\\d]+");
        Battle.getMenu().addPattern("insert [\\w]+ in [\\d]+ [\\d]+");
        Battle.getMenu().addPattern("end turn");
        Battle.getMenu().addPattern("use \\[[\\d+] [\\d]+\\]");
        Battle.getMenu().addPattern("select [\\d]+");
        Battle.getMenu().addPattern("enter graveyard");
        Battle.getMenu().addPattern("help");
        Battle.getMenu().addPattern("end game");
    }
    public static void setGraveyardPatterns(){
        GraveYardMenu.getMenu().addPattern("enter [\\w]+");
        GraveYardMenu.getMenu().addPattern("[\\d]+");
        GraveYardMenu.getMenu().addPattern("help");
        GraveYardMenu.getMenu().addPattern("show");
        GraveYardMenu.getMenu().addPattern("exit");
        GraveYardMenu.getMenu().addPattern("show card info [\\d]+");
        GraveYardMenu.getMenu().addPattern("show cards");
    }
    public static void setMainMenuPattern(){
        MainMenu.getMenu().addPattern("[\\d]+");
        MainMenu.getMenu().addPattern("enter [\\w]+");
        MainMenu.getMenu().addPattern("help");
        MainMenu.getMenu().addPattern("show");
        MainMenu.getMenu().addPattern("exit");
    }
    public static void setChooseBattleModePattern(){
        ChooseBattleModeMenu.getMenu().addPattern("[\\d]+");
        ChooseBattleModeMenu.getMenu().addPattern("enter [\\w]+");
        ChooseBattleModeMenu.getMenu().addPattern("help");
        ChooseBattleModeMenu.getMenu().addPattern("show");
        ChooseBattleModeMenu.getMenu().addPattern("exit");
        ChooseBattleModeMenu.getMenu().addPattern("mode [\\d]+");
    }
    public static void setGameModeMenuPatterns(){
        GameModeMenu.getMenu().addPattern("[\\d]+");
        GameModeMenu.getMenu().addPattern("enter [\\w]+");
        GameModeMenu.getMenu().addPattern("help");
        GameModeMenu.getMenu().addPattern("show");
        GameModeMenu.getMenu().addPattern("exit");

    }

    //commandHandler
    public void handleCommand(String command) {
//        String command;
        try {
//            command = commands.nextLine().toLowerCase().trim();
            String[] word = command.split(" ");
            if (!MenuHandler.getCurrentMenu().allowsCommand(command)) {
                this.printer.println("Invalid Command");
            }else if (commonCommandHandler(word)) {

            } else if (MenuHandler.getCurrentMenu() instanceof SignInMenu) {
                SignInMenuCommandHandler(word);
            } else if (MenuHandler.getCurrentMenu() instanceof CollectionMenu) {
                CollectionMenuCommandHandler(word);
            } else if (MenuHandler.getCurrentMenu() instanceof ShopMenu){
                ShopMenuCommandHandler(word);
            }else if(MenuHandler.getCurrentMenu() instanceof Battle){
                BattleCommandHandler(word,command);
            }else if(MenuHandler.getCurrentMenu() instanceof ChooseBattleModeMenu){
                ChooseBattleModeMenuCommandHandler(word);
            }else if(MenuHandler.getCurrentMenu() instanceof StoryModeMenu){
                storyModeMenuCommandHandler(word);
            }else if(MenuHandler.getCurrentMenu() instanceof MultiPlayerModeMenu){
                MultiPlayerMenuCommandHandler(word);
            }else if(MenuHandler.getCurrentMenu() instanceof CustomModeMenu){
                CustomModeMenuCommandHandler(word);
            }else if(MenuHandler.getCurrentMenu() instanceof GraveYardMenu){
                GraveYardMenuCommandHandler(word);
            }
        }
        catch (Exception e){
            System.err.println("exceptions accrued while running");
            e.printStackTrace();
        }
    }

    private boolean commonCommandHandler(String[] word) {
        /*common commands*/
        if(word[0].equals("help")){
            MenuHandler.getCurrentMenu().help();
            return true;
        }
        else if(word.length >= 2 && word[0].equals("show") && word[1].equals("menu")){
            MenuHandler.getCurrentMenu().showMenu();
            return true;
        }else if(word[0].equals("enter")){
            try {

                try {
                    MenuHandler.setCurrentMenu(MenuHandler.getCurrentMenu().enter(MenuHandler.getCurrentMenu().getMenuFromSubMenus(word[1])));
                } catch (InvalidSubMenuException e) {
                    this.printer.println("the requested menu doesnt exist");
                }
            }catch (IndexOutOfBoundsException e){
                this.printer.println("please choose a number between 1 and " + MenuHandler.getCurrentMenu().getSubMenus().size());
            }
            return true;
        }else if(word[0].equals("exit")){
            if(MenuHandler.getCurrentMenu().getParentMenu()==null) this.printer.println("This is the root menu!");
            else {
                MenuHandler.setCurrentMenu(MenuHandler.getCurrentMenu().exit());
                return true;
            }
        }
        return false;
    }

    private void GraveYardMenuCommandHandler(String[] word) {
        GraveYardMenu menu= (GraveYardMenu) MenuHandler.getCurrentMenu();
        if(word[0].equals("show")&& word[1].equals("cards")){
            menu.showCards();
        }else if(word[0].equals("show") && word[1].equals("card") && word[2].equals("info")){
            try {
                menu.showCardInfo(Integer.parseInt(word[3]));
            } catch (InvalidCardException e) {
                this.printer.println("there is not such card in your grave yard");
            }
        }
    }
    private void CustomModeMenuCommandHandler(String[] word) {
        if(word[0].equals("select")){
            CustomModeMenu menu= (CustomModeMenu) MenuHandler.getCurrentMenu();
            try {
                MenuHandler.setCurrentMenu(menu.selectDeck(word[2]));
            } catch (InvalidDeckException e) {
                System.err.println(word[2]);
                this.printer.println("Couldnt find the deck");
            }
        }
    }
    private void MultiPlayerMenuCommandHandler(String[] word) {
        MultiPlayerModeMenu menu= (MultiPlayerModeMenu) MenuHandler.getCurrentMenu();
        if(word[0].equals("select") && word[1].equals("user")){
            try {
                menu.selectUser(word[2],word[3]);
                MenuHandler.setCurrentMenu(menu.enter(Battle.getMenu()));
            } catch (InvalidAccountException e) {
                this.printer.println("this account doesnt exist");
            } catch (WrongPassException e) {
                this.printer.println("username and pass word dont match try again");
            }
        }
    }
    private void storyModeMenuCommandHandler(String[] word) {
        System.err.println("debug");
        StoryModeMenu menu= (StoryModeMenu) MenuHandler.getCurrentMenu();
        if(word[0].equals("level")) {
            MenuHandler.setCurrentMenu(menu.setAI(Integer.parseInt(word[1])));
        }
    }
    private void CollectionMenuCommandHandler(String[] word) {
        CollectionMenu menu= (CollectionMenu) MenuHandler.getCurrentMenu();
        if(word[0].equals("show")){
            if(word.length>= 3 && word[1].equals("all") && word[2].equals("decks")){
                menu.showAllDecks();
            }else if(word.length>= 2 && word[1].equals("deck")){
                String name = getName(word , 2) ;
                try {
                    menu.showDeck(name);
                } catch (InvalidDeckException e) {
                    this.printer.println("Deck with the name "+name+" doesnt exists ");
                }
            }else{
                menu.showCollection();
            }
        }else if(word[0].equals("search")){
            String name = getName(word , 1) ;
            menu.search(name);
        }else if(word[0].equals("save")){
            menu.save();
        }else if(word[0].equals("create") && word[1].equals("deck")){
            String name = getName(word , 2) ;
            try {
                menu.createNewDeck(name);
            } catch (DeckAlreadyExistException e) {
                this.printer.println("Deck with the exact same name already exist please try again");
            }
        }else if(word[0].equals("delete") && word[1].equals("deck")){
            try {
                String name = getName(word , 2) ;
                menu.deleteDeck(name);
            } catch (InvalidDeckException e) {
                this.printer.println("Deck with the given name doesnt exist");
            }
        }else if(word[0].equals("add") && word[2].equals("to")&& word[3].equals("deck")){
            try {
                String name = getName(word , 4) ;
                menu.addToDeck(Integer.parseInt(word[1]),name);
            } catch (DeckAlreadyHasAHeroException e) {
                this.printer.println("Cant add another Hero to the deck");
            } catch (DeckAlreadyHasThisCardException e) {
                this.printer.println("Deck already has this Card");
            } catch (FullDeckException e) {
                this.printer.println("No More!!! deck is full");
            } catch (InvalidCardException e) {
                this.printer.println("you dont have this Card in your collection");
            } catch (DeckAlreadyHasThisItemException e) {
                this.printer.println("Deck already has this Item");
            } catch (InvalidDeckException e) {
                this.printer.println("Couldn't find the Deck!");
            } catch (InvalidItemException e) {
                this.printer.println("you dont have this Item in your collection");
            }
        }else if(word[0].equals("remove") && word[2].equals("from")&& word[3].equals("deck")){
            try {
                String name = getName(word , 4) ;
                menu.removeFromDeck(Integer.parseInt(word[1]),name);
            } catch (InvalidCardException e) {
                this.printer.println("you dont have this Card in your Deck");
            } catch (InvalidItemException e) {
                this.printer.println("you dont have this Item in your Deck");
            } catch (InvalidDeckException e) {
                this.printer.println("Couldn't find the Deck!");
            }
        }else if(word[0].equals("validate") && word[1].equals("deck")){
            String name = getName(word , 2) ;
            try {
                if(menu.validateDeck(name)){
                    this.printer.println("your deck is valid");
                }else{
                    this.printer.println("your deck is not valid");
                }
            } catch (InvalidDeckException e) {
                this.printer.println("Couldn't find the Deck!");
            }
        }else if(word[0].equals("select") && word[1].equals("deck")){
            String name = getName(word , 2) ;
            try {
                System.err.println(name);
                menu.selectDeck(name);
            } catch (InvalidDeckException e) {
                this.printer.println("Couldn't find the Deck!");
            }
        }
    }
    private void ShopMenuCommandHandler(String[] word) {
        ShopMenu menu = (ShopMenu) MenuHandler.getCurrentMenu();
        if(word[0].equals("show")){
            if(word.length>= 2 && word[1].equals("collection")){
                menu.showCollection();
            }else{
                menu.show();
            }
        }else if(word[0].equals("search")){
            if(word.length>= 2 && word[1].equals("collection")){
                try {
                    String name = getName(word , 2);
                    menu.searchCollection(name);
                } catch (InvalidCardException e) {
                    this.printer.println("Card with Given name doesnt exist");
                } catch (InvalidItemException e) {
                    this.printer.println("Item with Given name doesnt exist");
                }
            }else {
                String name = getName(word , 1);
                try {
                    menu.search(name);
                } catch (InvalidCardException e) {
                    this.printer.println("Card with Given name doesnt exist");
                } catch (InvalidItemException e) {
                    this.printer.println("Item with Given name doesnt exist");
                }
            }
        }else if(word[0].equals("buy")){
            String name = getName(word , 1);
            try {
                menu.buy(name);
            } catch (CardExistException e) {
                this.printer.println("You already have this Card. it is not wise to buy a same card twice");
            } catch (InvalidCardException e) {
                System.err.println("hhhhhhhhhhhhhhhhhhhhh");
                this.printer.println("Me lord! we just ran out of " + name + ". im sorry!");
            } catch (ItemExistExeption itemExistExeption) {
                this.printer.println("You already have this Item. it is not wise to buy a same item twice");
            } catch (FullCollectionException e) {
                this.printer.println("Sorry but you dont have enough Space in your collection");
                this.printer.println("Empty your collection a little bit by selling some cards and try again");
            } catch (NotEnoughMoneyException e) {
                this.printer.println("Oops you are not as reach as you thought!");
            } catch (InvalidItemException e) {
                this.printer.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
            }
        }else if(word[0].equals("sell")){
            String name = getName(word , 1);
            try {
                menu.sell(name);
            } catch (InvalidCardException e) {
                this.printer.println("Smart Move But you cant sell a Card/Item that you dont have");
            }
        }
    }
    private void SignInMenuCommandHandler(String[] word) {
        SignInMenu menu= (SignInMenu) MenuHandler.getCurrentMenu();
        if(word[0].equals("create") && word[1].equals("account")){
            try {
                menu.creatAccount(word[2],word[3],word[4]);
            } catch (AccountAlreadyExistsException e) {
                this.printer.println("this userName is already taken");
            } catch(ArrayIndexOutOfBoundsException e){
                this.printer.println("please enter in the fallowing order");
                this.printer.println("1)name     2)username      3)password");
            }
        }else if(word[0].equals("login")){
            try {
                menu.logIn(word[1],word[2]);
                MenuHandler.setCurrentMenu(menu.enter(MainMenu.getMenu()));
            } catch (InvalidAccountException e) {
                this.printer.println("Account doesnt exist");
            } catch (WrongPassException e) {
                this.printer.println("username and password doesnt match try again");
            } catch(ArrayIndexOutOfBoundsException e){
                this.printer.println("please enter in the fallowing order");
                this.printer.println("1)username      2)password");
            }
        }else if(word[0].equals("show") && word[1].equals("leaderboard")){
            menu.showLeaderBoard();

        }else if(word[0].equals("save")){
            menu.save();
        }else if(word[0].equals("logout")){
            menu.logOut();
        }
    }
    private void ChooseBattleModeMenuCommandHandler(String[] word) {
        ChooseBattleModeMenu menu= ChooseBattleModeMenu.getMenu();
        System.err.println("debug");
        if(word[0].equals("mode")){
            menu.setMode(Integer.parseInt(word[1]));
        }
    }
    private void BattleCommandHandler(String[] word,String command) {
        Battle menu = (Battle) MenuHandler.getCurrentMenu();

        menu.getMatch().addCommand(command,menu.getTurn());

        if (word[0].equals("game") && word[1].equals("info")) {
            menu.gameInfo();
        } else if (word[0].equals("show") && word.length > 1) {
            if (word[1].equals("my") && word[2].equals("minions")) {
                menu.showMyMinions();
            } else if (word[1].equals("opponent") && word[2].equals("minions")) {
                menu.showMyOpponentMinion();
            } else if (word[1].equals("card") && word[2].equals("info")) {
                try {
                    menu.showCardInfo(Integer.parseInt(word[3]));
                } catch (InvalidCardException e) {
                    this.printer.println("Couldn't find the card!");
                }
            } else if (word[1].equals("hand")) {
                menu.showHand();
            } else if (word[1].equals("collectables")) {
                menu.showCollectable();
            } else if (word[1].equals("next") && word[2].equals("card")) {
                menu.showNextCard();
            }else if(word[1].equals("info")){
                try {
                    menu.showInfo();
                } catch (NoItemHasBeenSelectedException e) {
                    this.printer.println("please Select an item first");
                }
            }
        } else if (word[0].equals("select")) {
            try {
                menu.select(Integer.parseInt(word[1]));
            } catch (InvalidCardException e) {
                this.printer.println("im afraid that you dont acquire this card");
            } catch (InvalidItemException e) {
                this.printer.println("im afraid that you dont acquire this item");
            }
        } else if (word[0].equals("move") && word[1].equals("to")) {
            System.err.println();
            try {
                menu.move(Integer.parseInt(word[2]), Integer.parseInt(word[3]));
            } catch (NoCardHasBeenSelectedException e) {
                this.printer.println("please select a card first");
            } catch (CardCantBeMovedException e) {
                this.printer.println("this card cant be moved due the spell unleashed upon it");
                this.printer.println("Or Maybe he/she is just a little bit tired :D");
            } catch (MoveTrunIsOverException e) {
                this.printer.println("there is no time to move !!!");
                this.printer.println("Attack attack attack");
            } catch (DestinationOutOfreachException e) {
                this.printer.println("Ooooo to far!");
                this.printer.println("please set the destination some were close");
            } catch (InvalidCellException e) {
                this.printer.println("Im afraid our little word doesnt have enough space for your ambitions");
            } catch (DestinationIsFullException e) {
                this.printer.println("SomeBodys already there ");
                this.printer.println("another location maybe?");
            }
        } else if (word[0].equals("attack")) {
            try {
                menu.attack(Integer.parseInt(word[1]));
            } catch (NoCardHasBeenSelectedException e) {
                this.printer.println("please select a card first");
            } catch (InvalidCardException e) {
                this.printer.println("are you sure?");
                this.printer.println("cause it seems like our enemy doesnt have such card on the ground");
            } catch (DestinationOutOfreachException e) {
                this.printer.println("marchin on this destinations may not result in our benefit");
                this.printer.println("my lord.... please reconsider");
            } catch (CantAttackException e) {
                this.printer.println("this card cant attack due the spell unleashed upon it");
            } catch (InvalidCellException e) {
                this.printer.println("Im afraid our little word doesnt have enough space for your ambitions");
            }
        } else if (word[0].equals("use") && word[1].equals("special") && word[2].equals("power")) {
            try {
                menu.useSpecialPower(Integer.parseInt(word[3]), Integer.parseInt(word[4]));
            }catch(InvalidCellException e){
//                this.printer.println("sorry but you have to pick a different cell");
//                this.printer.println(e.getMessage());
                e.printStackTrace();
            }catch (InvalidCardException e){
                this.printer.println("sorry ! invalid card");
            }catch (CantSpecialPowerCooldownException e){
                this.printer.println("sorry but you have to cool down first man !");
            }
        } else if (word[0].equals("insert")) {
            try {
                menu.insert(Integer.parseInt(word[1]), Integer.parseInt(word[3]), Integer.parseInt(word[4]));
            } catch (InvalidCardException e) {
                this.printer.println("thre is no such card in your hand");
            } catch (NotEnoughManaException e) {
                this.printer.println("lets collect some mana first!");
            } catch (DestinationIsFullException e) {
                this.printer.println("cant spwan/deploy card on the selected destination");
            } catch (InvalidCellException e) {
                this.printer.println("Im afraid our little word doesnt have enough space for your ambitions");
            }
        } else if (word[0].equals("use")) {
            try {
                menu.useItem(Integer.parseInt(word[1]), Integer.parseInt(word[2]));
            } catch (InvalidCellException e) {
                this.printer.println("cell " + Integer.parseInt(word[1]) + " , " + Integer.parseInt(word[2]) + "says: ");
                this.printer.println("cant touch this!");
            } catch (NoItemHasBeenSelectedException e) {
                this.printer.println("please select an item first");
            }
        }else if(word[0].equals("end") && word[1].equals("turn")){
            try {
                menu.endTurn();
            } catch (HandFullException | DeckIsEmptyException ignored) {
                // :D
            }
        }
    }

    private static String getName(String[] word , int startPoint) {
        StringBuilder name = new StringBuilder();
        for (int i = startPoint ; i < word.length ; i++){
            name.append(word[i]).append(" ");
        }
        return name.substring(0, name.length() - 1);
    }
}
