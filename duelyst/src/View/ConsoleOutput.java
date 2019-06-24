package View;

import Controller.GameMode.ClassicMode;
import Controller.GameMode.Domination;
import Controller.GameMode.FlagMode;
import Controller.menu.*;
//import SignInMenu;
import Controller.menu.Graphics.FXMLController.LeaderBoardHavingFXMLC;
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
import View.Listeners.OnLeaderBoardClickedListener;
import View.Listeners.OnMenuClickedListener;
import exeption.*;

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

public class ConsoleOutput {

    public ConsoleOutput() {
        setListener();
        setPatterns();
    }

    public static void allCardPresenter(ArrayList<Card> cards, ArrayList<Usable> items, boolean showID){
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
        System.out.println("Cards : ");
        i=0;
        for (Card card : cards) {
            if(!(card instanceof Hero)){
                i++;
                System.out.print(i+") ");
                for (OnCardDetailsPresentedListener presenter : Card.getCardDetailsPresenters()) {
                    presenter.showCardDetail(card);
//                    if(showID) System.out.println("\tID: "+card.getCardID());
                }
            }
        }
        System.out.println("Items : ");
        i=0;
        for (Usable item : items) {
            i++;
            System.out.println(i+") ");
            for (OnItemDetailPresentedListener presenter : Item.getItemDetailPresenters()) {
                presenter.showItemDetail(item);
                if(showID) System.out.println("\tID: "+item.getID());
            }
        }
    }
    private static void setListener(){
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
                                System.out.print("H ");
                            }else if(cell.getCardOnCell() instanceof Minion){
                                if(battle.getPlayer().getMinionsInGame().contains(cell.getCardOnCell())){
                                    System.out.print("M ");
                                }else{
                                    System.out.print("W ");
                                }
                            }else if(cell.hasItem()) System.out.print("C ");
                            else if(cell.hasFlag()) System.out.print("F ");
                            else if(cell.getCardOnCell()==null) System.out.print(". ");
                        } catch (InvalidCellException e) {
                            e.printStackTrace(); }
                    }
                    System.out.println();
                }
            });
            ChooseBattleModeMenu.getMenu().addMenuClickListener(menu -> {
                System.out.println("Modes:");
                System.out.println("1)Classic      2)flag mode     3)Domination");
            });
            CollectableMenu.getMenu().addMenuClickListener(new ShowMenu());
            CollectionMenu.getMenu().addMenuClickListener(new ShowMenu());
            CostumeModeMenu.getMenu().addMenuClickListener(new ShowMenu());
            GameModeMenu.getMenu().addMenuClickListener(new ShowMenu());
            GraveYardMenu.getMenu().addMenuClickListener(new ShowMenu());
            MainMenu.getMenu().addMenuClickListener(new ShowMenu());
            MultiPlayerModeMenu.getMenu().addMenuClickListener(menu -> {
                System.out.println("Accounts: ");
                int i=0;
                for (Account account : Account.getAccounts()) {
                    i++;
                    System.out.println("\t"+i+") UserName:"+account.getUsername()+"\tName: "+account.getName());
                }
            });
            ShopMenu.getMenu().addMenuClickListener(new ShowMenu());
            SinglePlayerModeMenu.getMenu().addMenuClickListener(new ShowMenu());
            StoryModeMenu.getMenu().addMenuClickListener(menu -> {
                System.out.println("Levels:");
                System.out.println("1) welcome to our world     2) wrath of the Mighty     3) the last fiction");
            });
        }
        //signIn menu
        SignInMenu.getMenu().addLeaderBoardClickedListener((accounts, fxmlc) -> {
            System.out.println("LeaderBoard:");
            int i = 0;
            for (Account account : accounts) {
                i++;
                System.out.println(i + ") " + account.getName() + " - Wins: " + account.getWins());
            }
        });

        //Collection
        Collection.addCollectionPresentedListener((collection, name) -> {
            System.out.println(name + " : ");
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
            System.out.println("\tName : " + hero.getName());
            System.out.println("\tAttackPoint : " + hero.getBuffEffects().getOriginalAttackPoint() +
                    "\tHealth point : " + hero.getOriginalHealthPoint() + "\tManaPoint : ");
            System.out.println("\tClass : " + hero.getAttackType().getClass().toString());
            System.out.println("\tSell cost : " + hero.getPrice());
        });

        //deck
        Deck.addNewOnDeckPresentedListener(deck -> allCardPresenter(deck.getCards(),deck.getUsables(),true));

        //GameInfo
        Battle.getMenu().addGameInfoPresentedListener(() -> {
            if(Battle.getMenu().getGameMode() instanceof ClassicMode){
                System.out.println(Battle.getMenu().getAccount().getName()+" : ");
                System.out.println("\tMANA: "+Battle.getMenu().getAccount().getPlayer().getMana());
                System.out.println("\t"+Battle.getMenu().getAccount().getPlayer().getDeck().getHero().getName()+" : "+Battle.getMenu().getAccount().getPlayer().getDeck().getHero().getHealthPoint());
                System.out.println(Battle.getMenu().getEnemy(Battle.getMenu().getAccount()).getUser().getName()+" : ");
                System.out.println("\tMANA: "+Battle.getMenu().getEnemy(Battle.getMenu().getAccount()).getMana());
                System.out.println("\t"+Battle.getMenu().getEnemy(Battle.getMenu().getAccount()).getDeck().getHero().getName()+" : "+Battle.getMenu().getEnemy(Battle.getMenu().getAccount()).getDeck().getHero().getHealthPoint());
//          }else if(Battle.getMenu().getGameMode() instanceof FlagMode){

            }
            else if(Battle.getMenu().getGameMode() instanceof FlagMode){
                System.out.println("Game info");
                for (Cell cell:
                        Battle.getMenu().getMap().getCells()) {
                    if(cell.hasFlag()){
                        System.out.println("Flag Location :" + cell.getX() + "," + cell.getY());
                    }
                }
                boolean hasflag = false;
                for (Card card:
                        Battle.getMenu().getPlayer().getDeck().getCards()) {
                    if(card instanceof Hermione){
                        if(((Hermione)card).hasFlag()){
                            System.out.println("Flag owner:"+ card.getName());
                            hasflag = true;
                        }
                    }
                }
                for (Card card:
                        Battle.getMenu().getEnemyPlayer().getDeck().getCards()) {
                    if(card instanceof Hermione){
                        if(((Hermione)card).hasFlag()){
                            System.out.println("Flag owner :"+ card.getName());
                            hasflag = true;
                        }
                    }
                }
                if(!hasflag){
                    System.out.println("Nobody got the flag !");
                }
            }
            else if(Battle.getMenu().getGameMode() instanceof Domination){
                System.out.println("Game info");
                boolean hasflag = false;
                for (Card card:
                        Battle.getMenu().getPlayer().getDeck().getCards()) {
                    if(card instanceof Hermione){
                        if(((Hermione)card).hasFlag()){
                            System.out.println("From my team "+ card.getName());
                            hasflag = true;
                        }
                    }
                }
                for (Card card:
                        Battle.getMenu().getEnemyPlayer().getDeck().getCards()) {
                    if(card instanceof Hermione){
                        if(((Hermione)card).hasFlag()){
                            System.out.println("From opponent's team "+ card.getName());
                            hasflag = true;
                        }
                    }
                }
                if(!hasflag){
                    System.out.println("Nobody got any flag!");
                }
            }
        });

        //Hand
        Hand.addOnHandPresentedListener(hand -> {
            int i=0;
            for (Card card : hand.getCards()) {
                i++;
                System.out.println();
                System.out.println(i+ ") "+ card.getName());
                System.out.println("\tid: "+card.getCardID());
            }
        });

        //Item
        Item.addNewOnItemDeatilPresentedListener(item -> {
            System.out.println("\tName : " + item.getName());
            System.out.println("\tDesc : " + item.getComment());
            if(item instanceof Usable)
                System.out.println("\tSell cost" + ((Usable) item).getPrice());
            else if(item instanceof Collectable)
                System.out.println("\tID" + (item).getID());
        });
        //game card presentedListener
        Battle.getMenu().addCardPresentedListener(card -> {
            Hermione h= (Hermione) card;
            System.out.println("\t" + h.getName() + " : ");
            System.out.println("\t\tHealth Point: " + h.getHealthPoint() + ",location: " + h.getLocation().getX() + "," + h.getLocation().getY());
            System.out.println("\t\tAction: "+h.getInfo());
            System.out.println("\t\tID: "+h.getCardID());
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
        setCostumeModeMenuPattern();
    }
    private static void setCostumeModeMenuPattern() {
        CostumeModeMenu.getMenu().addPattern("enter [\\w]+");
        CostumeModeMenu.getMenu().addPattern("[\\d]+");
        CostumeModeMenu.getMenu().addPattern("help");
        CostumeModeMenu.getMenu().addPattern("show");
        CostumeModeMenu.getMenu().addPattern("exit");
        CostumeModeMenu.getMenu().addPattern("select [\\w]+");
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
    public  void handleCommand(String command) {
//        String command;
        try {
//            command = commands.nextLine().toLowerCase().trim();
            String[] word = command.split(" ");
            if (!MenuHandler.currentMenu.allowsCommand(command)) {
                System.out.println("Invalid Command");
            }else if (commonCommandHandler(word)) {

            } else if (MenuHandler.currentMenu instanceof SignInMenu) {
                SignInMenuCommandHandler(word);
            } else if (MenuHandler.currentMenu instanceof CollectionMenu) {
                CollectionMenuCommandHandler(word);
            } else if (MenuHandler.currentMenu instanceof ShopMenu){
                ShopMenuCommandHandler(word);
            }else if(MenuHandler.currentMenu instanceof Battle){
                BattleCommandHandler(word,command);
            }else if(MenuHandler.currentMenu instanceof ChooseBattleModeMenu){
                ChooseBattleModeMenuCommandHandler(word);
            }else if(MenuHandler.currentMenu instanceof StoryModeMenu){
                storyModeMenuCommandHandler(word);
            }else if(MenuHandler.currentMenu instanceof MultiPlayerModeMenu){
                MultiPlayerMenuCommandHandler(word);
            }else if(MenuHandler.currentMenu instanceof CostumeModeMenu){
                CostumeModeMenuCommandHandler(word);
            }else if(MenuHandler.currentMenu instanceof GraveYardMenu){
                GraveYardMenuCommandHandler(word);
            }
        }
        catch (Exception e){
            System.err.println("exceptions accrued while running");
            e.printStackTrace();
        }
    }

    private static boolean commonCommandHandler(String[] word) {
        /*common commands*/
        if(word[0].equals("help")){
            MenuHandler.currentMenu.help();
            return true;
        }
        else if(word.length >= 2 && word[0].equals("show") && word[1].equals("menu")){
            MenuHandler.currentMenu.showMenu();
            return true;
        }else if(word[0].equals("enter")){
            try {

                try {
                    MenuHandler.currentMenu = MenuHandler.currentMenu.enter(MenuHandler.currentMenu.getMenuFromSubMenus(word[1]));
                } catch (InvalidSubMenuException e) {
                    System.out.println("the requested menu doesnt exist");
                }
            }catch (IndexOutOfBoundsException e){
                System.out.println("please choose a number between 1 and " + MenuHandler.currentMenu.getSubMenus().size());
            }
            return true;
        }else if(word[0].equals("exit")){
            if(MenuHandler.currentMenu.getParentMenu()==null) System.out.println("This is the root menu!");
            else {
                MenuHandler.currentMenu=MenuHandler.currentMenu.exit();
                return true;
            }
        }
        return false;
    }

    private static void GraveYardMenuCommandHandler(String[] word) {
        GraveYardMenu menu= (GraveYardMenu) MenuHandler.currentMenu;
        if(word[0].equals("show")&& word[1].equals("cards")){
            menu.showCards();
        }else if(word[0].equals("show") && word[1].equals("card") && word[2].equals("info")){
            try {
                menu.showCardInfo(Integer.parseInt(word[3]));
            } catch (InvalidCardException e) {
                System.out.println("there is not such card in your grave yard");
            }
        }
    }
    private static void CostumeModeMenuCommandHandler(String[] word) {
        if(word[0].equals("select")){
            CostumeModeMenu menu= (CostumeModeMenu) MenuHandler.currentMenu;
            try {
                MenuHandler.currentMenu=menu.selectDeck(word[2]);
            } catch (InvalidDeckException e) {
                System.err.println(word[2]);
                System.out.println("Couldnt find the deck");
            }
        }
    }
    private static void MultiPlayerMenuCommandHandler(String[] word) {
        MultiPlayerModeMenu menu= (MultiPlayerModeMenu) MenuHandler.currentMenu;
        if(word[0].equals("select") && word[1].equals("user")){
            try {
                menu.selectUser(word[2],word[3]);
                MenuHandler.currentMenu=menu.enter(Battle.getMenu());
            } catch (InvalidAccountException e) {
                System.out.println("this account doesnt exist");
            } catch (WrongPassException e) {
                System.out.println("username and pass word dont match try again");
            }
        }
    }
    private static void storyModeMenuCommandHandler(String[] word) {
        StoryModeMenu menu= (StoryModeMenu) MenuHandler.currentMenu;
        if(word[0].equals("level")) {
//            MenuHandler.currentMenu=menu.setAI(Integer.parseInt(word[1]));
        }
    }
    private static void CollectionMenuCommandHandler(String[] word) {
        CollectionMenu menu= (CollectionMenu) MenuHandler.currentMenu;
        if(word[0].equals("show")){
            if(word.length>= 3 && word[1].equals("all") && word[2].equals("decks")){
                menu.showAllDecks();
            }else if(word.length>= 2 && word[1].equals("deck")){
                String name = getName(word , 2) ;
                try {
                    menu.showDeck(name);
                } catch (InvalidDeckException e) {
                    System.out.println("Deck with the name "+name+" doesnt exists ");
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
                System.out.println("Deck with the exact same name already exist please try again");
            }
        }else if(word[0].equals("delete") && word[1].equals("deck")){
            try {
                String name = getName(word , 2) ;
                menu.deleteDeck(name);
            } catch (InvalidDeckException e) {
                System.out.println("Deck with the given name doesnt exist");
            }
        }else if(word[0].equals("add") && word[2].equals("to")&& word[3].equals("deck")){
            try {
                String name = getName(word , 4) ;
                menu.addToDeck(Integer.parseInt(word[1]),name);
            } catch (DeckAlreadyHasAHeroException e) {
                System.out.println("Cant add another Hero to the deck");
            } catch (DeckAlreadyHasThisCardException e) {
                System.out.println("Deck already has this Card");
            } catch (FullDeckException e) {
                System.out.println("No More!!! deck is full");
            } catch (InvalidCardException e) {
                System.out.println("you dont have this Card in your collection");
            } catch (DeckAlreadyHasThisItemException e) {
                System.out.println("Deck already has this Item");
            } catch (InvalidDeckException e) {
                System.out.println("Couldn't find the Deck!");
            } catch (InvalidItemException e) {
                System.out.println("you dont have this Item in your collection");
            }
        }else if(word[0].equals("remove") && word[2].equals("from")&& word[3].equals("deck")){
            try {
                String name = getName(word , 4) ;
                menu.removeFromDeck(Integer.parseInt(word[1]),name);
            } catch (InvalidCardException e) {
                System.out.println("you dont have this Card in your Deck");
            } catch (InvalidItemException e) {
                System.out.println("you dont have this Item in your Deck");
            } catch (InvalidDeckException e) {
                System.out.println("Couldn't find the Deck!");
            }
        }else if(word[0].equals("validate") && word[1].equals("deck")){
            System.err.println("debug");
            String name = getName(word , 2) ;
            try {
                if(menu.validateDeck(name)){
                    System.out.println("your deck is valid");
                }else{
                    System.out.println("your deck is not valid");
                }
            } catch (InvalidDeckException e) {
                System.out.println("Couldn't find the Deck!");
            }
        }else if(word[0].equals("select") && word[1].equals("deck")){
            String name = getName(word , 2) ;
            try {
                System.err.println(name);
                menu.selectDeck(name);
            } catch (InvalidDeckException e) {
                System.out.println("Couldn't find the Deck!");
            }
        }
    }
    private static void ShopMenuCommandHandler(String[] word) {
        ShopMenu menu = (ShopMenu) MenuHandler.currentMenu;
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
                    System.out.println("Card with Given name doesnt exist");
                } catch (InvalidItemException e) {
                    System.out.println("Item with Given name doesnt exist");
                }
            }else {
                String name = getName(word , 1);
                try {
                    menu.search(name);
                } catch (InvalidCardException e) {
                    System.out.println("Card with Given name doesnt exist");
                } catch (InvalidItemException e) {
                    System.out.println("Item with Given name doesnt exist");
                }
            }
        }else if(word[0].equals("buy")){
            String name = getName(word , 1);
            try {
                menu.buy(name);
            } catch (CardExistException e) {
                System.out.println("You already have this Card. it is not wise to buy a same card twice");
            } catch (InvalidCardException e) {
                System.err.println("hhhhhhhhhhhhhhhhhhhhh");
                System.out.println("Me lord! we just ran out of " + name + ". im sorry!");
            } catch (ItemExistExeption itemExistExeption) {
                System.out.println("You already have this Item. it is not wise to buy a same item twice");
            } catch (FullCollectionException e) {
                System.out.println("Sorry but you dont have enough Space in your collection");
                System.out.println("Empty your collection a little bit by selling some cards and try again");
            } catch (NotEnoughMoneyException e) {
                System.out.println("Oops you are not as reach as you thought!");
            } catch (InvalidItemException e) {
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
            }
        }else if(word[0].equals("sell")){
            String name = getName(word , 1);
            try {
                menu.sell(name);
            } catch (InvalidCardException e) {
                System.out.println("Smart Move But you cant sell a Card/Item that you dont have");
            }
        }
    }
    private static void SignInMenuCommandHandler(String[] word) {
        SignInMenu menu= (SignInMenu) MenuHandler.currentMenu;
        if(word[0].equals("create") && word[1].equals("account")){
            try {
                menu.creatAccount(word[2],word[3],word[4]);
            } catch (AccountAlreadyExistsException e) {
                System.out.println("this userName is already taken");
            } catch(ArrayIndexOutOfBoundsException e){
                System.out.println("please enter in the fallowing order");
                System.out.println("1)name     2)username      3)password");
            }
        }else if(word[0].equals("login")){
            try {
                menu.logIn(word[1],word[2]);
                MenuHandler.currentMenu=menu.enter(MainMenu.getMenu());
            } catch (InvalidAccountException e) {
                System.out.println("Account doesnt exist");
            } catch (WrongPassException e) {
                System.out.println("username and password doesnt match try again");
            } catch(ArrayIndexOutOfBoundsException e){
                System.out.println("please enter in the fallowing order");
                System.out.println("1)username      2)password");
            }
        }else if(word[0].equals("show") && word[1].equals("leaderboard")){
            menu.showLeaderBoard();

        }else if(word[0].equals("save")){
            menu.save();
        }else if(word[0].equals("logout")){
            menu.logOut();
        }
    }
    private static void ChooseBattleModeMenuCommandHandler(String[] word) {
        ChooseBattleModeMenu menu= (ChooseBattleModeMenu) ChooseBattleModeMenu.getMenu();
        if(word[0].equals("mode")){
            menu.setMode(Integer.parseInt(word[1]));
        }
    }



    private static void BattleCommandHandler(String[] word,String command) {
        Battle menu = (Battle) MenuHandler.currentMenu;

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
                    System.out.println("Couldn't find the card!");
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
                    System.out.println("please Select an item first");
                }
            }
        } else if (word[0].equals("select")) {
            try {
                menu.select(Integer.parseInt(word[1]));
            } catch (InvalidCardException e) {
                System.out.println("im afraid that you dont acquire this card");
            } catch (InvalidItemException e) {
                System.out.println("im afraid that you dont acquire this item");
            }
        } else if (word[0].equals("move") && word[1].equals("to")) {
            System.err.println();
            try {
                menu.move(Integer.parseInt(word[2]), Integer.parseInt(word[3]));
            } catch (NoCardHasBeenSelectedException e) {
                System.out.println("please select a card first");
            } catch (CardCantBeMovedException e) {
                System.out.println("this card cant be moved due the spell unleashed upon it");
                System.out.println("Or Maybe he/she is just a little bit tired :D");
            } catch (MoveTrunIsOverException e) {
                System.out.println("there is no time to move !!!");
                System.out.println("Attack attack attack");
            } catch (DestinationOutOfreachException e) {
                System.out.println("Ooooo to far!");
                System.out.println("please set the destination some were close");
            } catch (InvalidCellException e) {
                System.out.println("Im afraid our little word doesnt have enough space for your ambitions");
            } catch (DestinationIsFullException e) {
                System.out.println("SomeBodys already there ");
                System.out.println("another location maybe?");
            }
        } else if (word[0].equals("attack")) {
            try {
                menu.attack(Integer.parseInt(word[1]));
            } catch (NoCardHasBeenSelectedException e) {
                System.out.println("please select a card first");
            } catch (InvalidCardException e) {
                System.out.println("are you sure?");
                System.out.println("cause it seems like our enemy doesnt have such card on the ground");
            } catch (DestinationOutOfreachException e) {
                System.out.println("marchin on this destinations may not result in our benefit");
                System.out.println("my lord.... please reconsider");
            } catch (CantAttackException e) {
                System.out.println("this card cant attack due the spell unleashed upon it");
            } catch (InvalidCellException e) {
                System.out.println("Im afraid our little word doesnt have enough space for your ambitions");
            }
        } else if (word[0].equals("use") && word[1].equals("special") && word[2].equals("power")) {
            try {
                menu.useSpecialPower(Integer.parseInt(word[3]), Integer.parseInt(word[4]));
            }catch(InvalidCellException e){
//                System.out.println("sorry but you have to pick a different cell");
//                System.out.println(e.getMessage());
                e.printStackTrace();
            }catch (InvalidCardException e){
                System.out.println("sorry ! invalid card");
            }catch (CantSpecialPowerCooldownException e){
                System.out.println("sorry but you have to cool down first man !");
            }
        } else if (word[0].equals("insert")) {
            try {
                menu.insert(Integer.parseInt(word[1]), Integer.parseInt(word[3]), Integer.parseInt(word[4]));
            } catch (InvalidCardException e) {
                System.out.println("thre is no such card in your hand");
            } catch (NotEnoughManaException e) {
                System.out.println("lets collect some mana first!");
            } catch (DestinationIsFullException e) {
                System.out.println("cant spwan/deploy card on the selected destination");
            } catch (InvalidCellException e) {
                System.out.println("Im afraid our little word doesnt have enough space for your ambitions");
            }
        } else if (word[0].equals("use")) {
            try {
                menu.useItem(Integer.parseInt(word[1]), Integer.parseInt(word[2]));
            } catch (InvalidCellException e) {
                System.out.println("cell " + Integer.parseInt(word[1]) + " , " + Integer.parseInt(word[2]) + "says: ");
                System.out.println("cant touch this!");
            } catch (NoItemHasBeenSelectedException e) {
                System.out.println("please select an item first");
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
