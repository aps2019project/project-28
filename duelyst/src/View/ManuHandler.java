package View;

import Controller.Game;
import Controller.GameMode.ClassicMode;
import Controller.GameMode.FlagMode;
import Controller.GameMode.GameMode;
import Controller.menu.Battle;
import Controller.menu.*;
import Model.Primary;
import Model.account.*;
import Model.card.Card;
import Model.card.OnCardDetailsPresentedListener;
import Model.card.hermione.Hermione;
import Model.card.hermione.Hero;
import Model.card.hermione.Minion;
import Model.card.spell.Spell;
import Model.item.Item;
import Model.item.OnItemDetailPresentedListener;
import Model.item.Usable;
import View.Listeners.*;
import exeption.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


// TODO: 5/5/19 command select collectable(page 20) change menu from battle to collectable menu
// TODO: 5/5/19 command entergraveyard changes menu from battle to graveYard

class ShowMenu implements OnMenuClickedListener{
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


public class ManuHandler {

    private static Menu currentMenu;

    //preprocess
    public static void allCardPresenter(ArrayList<Card> cards,ArrayList<Usable> items){
        System.out.println("Heroes : ");
        int i=0;
        for (Card card : cards) {
            if((card instanceof Hero)) {
                i++;
                System.out.print(i+") ");
                for (OnHeroDetailsPresentedListener presenter : Hero.getHeroDetailsPresenters()) {
                    presenter.show((Hero) card);
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
            }
        }
    }
    static{
        try {
            Primary.preprocess();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initMenus();
        setListener();
        setPatterns();
    }

    private static void setListener(){
        //show menu
        {
            SignInMenu.getMenu().addMenuClickListener(new ShowMenu());
            Battle.getMenu().addMenuClickListener(new ShowMenu());
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
        SignInMenu.getMenu().addLeaderBoardClickedListener(accounts -> {
            System.out.println("LeaderBoard:");
            int i=0;
            for (Account account : accounts) {
                i++;
                System.out.println(i+") "+account.getName() + " - Wins: " +account.getWins());
            }
        });

        //Collection
        Collection.addCollectionPresentedListener((collection, name) -> {
            System.out.println(name + " : ");
            allCardPresenter(collection.getCards(), collection.getItems());
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
                System.out.print("\tAttackPoint : " + h.getOriginalAttackPoint());
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
            System.out.println("\tAttackPoint : " + hero.getOriginalAttackPoint() +
                    "\tHealth point : " + hero.getOriginalHealthPoint() + "\tManaPoint : ");
            System.out.println("\tClass : " + hero.getAttackType().getClass().toString());
                System.out.println("\tSell cost : " + hero.getPrice());
        });

        //deck
        Deck.addNewOnDeckPresentedListener(deck -> allCardPresenter(deck.getCards(),deck.getUsables()));

        //GameInfo
        Battle.getMenu().addGameInfoPresentedListener(() -> {
            if(Battle.getMenu().getGameMode() instanceof ClassicMode){
                System.out.println(Battle.getMenu().getAccount().getName()+" : " + Battle.getMenu().getAccount().getPlayer().getDeck().getHero().getHealthPoint());
                System.out.println(Battle.getMenu().getEnemy(Battle.getMenu().getAccount()).getUser().getName()+" : " + Battle.getMenu().getEnemy(Battle.getMenu().getAccount()).getDeck().getHero().getHealthPoint());
//                System.out.println("\tSpecialPower : " + hero.getSpecialPower().getInfo());
            }else if(Battle.getMenu().getGameMode() instanceof FlagMode){

            }else{

            }
        });

        //Hand
        Hand.addOnHandPresentedListener(hand -> {
            for (Card card : hand.getCards()) {
                for (OnCardDetailsPresentedListener presenter : Card.getCardDetailsPresenters()) {
                    presenter.showCardInfo(card);
                }
            }
        });

        //Item
        Item.addNewOnItemDeatilPresentedListener(item -> {
            System.out.println("\tName : " + item.getName());
            System.out.println("\tDesc : " + item.getComment());
            System.out.println("\tSell cost" + ((Usable) item).getPrice());
        });
    }
    private static void initMenus() {
        //az SignIn Menu mirim tuye MainMenu

        SignInMenu.getMenu().addSubMenu(MainMenu.getMenu());

        MainMenu.getMenu().addSubMenu(CollectionMenu.getMenu());
        MainMenu.getMenu().addSubMenu(ShopMenu.getMenu());
        MainMenu.getMenu().addSubMenu(ChooseBattleModeMenu.getMenu());

        GameModeMenu.getMenu().addSubMenu(SinglePlayerModeMenu.getMenu());
        GameModeMenu.getMenu().addSubMenu(MultiPlayerModeMenu.getMenu());

        SinglePlayerModeMenu.getMenu().addSubMenu(StoryModeMenu.getMenu());
        SinglePlayerModeMenu.getMenu().addSubMenu(CostumeModeMenu.getMenu());

        //az Single o Multi mirim gameMode

        Battle.getMenu().addSubMenu(GraveYardMenu.getMenu());
        Battle.getMenu().addSubMenu(CollectionMenu.getMenu());
        Battle.getMenu().addSubMenu(CollectableMenu.getMenu());

        currentMenu = SignInMenu.getMenu();
    }


    public static void setPatterns(){
        setChooseBattleModePattern();
        setSignInPatterns();
        setCollectionPatterns();
        setShopPatterns();
        setBattlePatterns();
        setGraveyardPatterns();
        setCollectablePattern();
        setMainMenuPattern();
        setGameModeMenuPatterns();
        setStoryModePattern();
        setSinglePlayerModePattern();
        setMultiPlayerModeMenuPattern();
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

    public static void setSignInPatterns(){
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
        CollectionMenu.getMenu().addPattern("search [\\w]+");
        CollectionMenu.getMenu().addPattern("save");
        CollectionMenu.getMenu().addPattern("create deck[\\w+]");
        CollectionMenu.getMenu().addPattern("delete deck [\\w]+");
        CollectionMenu.getMenu().addPattern("add [\\d]+ to deck [\\w]+");
        CollectionMenu.getMenu().addPattern("remove [\\d]+ from deck [\\w]+");
        CollectionMenu.getMenu().addPattern("validate deck [\\w]+");
        CollectionMenu.getMenu().addPattern("select deck [\\w]+");
        CollectionMenu.getMenu().addPattern("show all decks");
        CollectionMenu.getMenu().addPattern("show deck [\\w]+");
    }
    public static void setShopPatterns(){
        ShopMenu.getMenu().addPattern("enter [\\w]+");
        ShopMenu.getMenu().addPattern("[\\d]+");
        ShopMenu.getMenu().addPattern("help");
        ShopMenu.getMenu().addPattern("show");
        ShopMenu.getMenu().addPattern("exit");
        ShopMenu.getMenu().addPattern("show collection");
        ShopMenu.getMenu().addPattern("search [\\w]+");
        ShopMenu.getMenu().addPattern("search collection [\\w]+");
        ShopMenu.getMenu().addPattern("buy [\\w]+");
        ShopMenu.getMenu().addPattern("sell [\\d]+");
        ShopMenu.getMenu().addPattern("show");
    }
    public static void setBattlePatterns(){
        Battle.getMenu().addPattern("enter [\\w]+");
        Battle.getMenu().addPattern("[\\d]+");
        Battle.getMenu().addPattern("help");
        Battle.getMenu().addPattern("show");
        Battle.getMenu().addPattern("exit");
        Battle.getMenu().addPattern("Game info");
        Battle.getMenu().addPattern("Show my minions");
        Battle.getMenu().addPattern("Show opponent minions");
        Battle.getMenu().addPattern("Show card info [\\d]+");
        Battle.getMenu().addPattern("Select [\\d]+");
        Battle.getMenu().addPattern("Move to \\([\\d]+ [\\d]+\\)");
        Battle.getMenu().addPattern("Attack [\\d]+");
        Battle.getMenu().addPattern("Attack combo [\\d]+ [\\d]+[ \\d+]+");
        Battle.getMenu().addPattern("Use special power \\([\\d]+ [\\d]+\\)");
        Battle.getMenu().addPattern("Show hand");
        Battle.getMenu().addPattern("Insert [\\w]+ in \\([\\d]+ [\\d]+\\)");
        Battle.getMenu().addPattern("End turn");
        Battle.getMenu().addPattern("Show collectables");
        Battle.getMenu().addPattern("Select [\\d]+");
        Battle.getMenu().addPattern("Show Next Card");
        Battle.getMenu().addPattern("Enter graveyard");
        Battle.getMenu().addPattern("Help");
        Battle.getMenu().addPattern("End Game");
    }
    public static void setGraveyardPatterns(){
        GraveYardMenu.getMenu().addPattern("enter [\\w]+");
        GraveYardMenu.getMenu().addPattern("[\\d]+");
        GraveYardMenu.getMenu().addPattern("help");
        GraveYardMenu.getMenu().addPattern("show");
        GraveYardMenu.getMenu().addPattern("exit");
        GraveYardMenu.getMenu().addPattern("Show info [\\d]+");
        GraveYardMenu.getMenu().addPattern("Show cards");
    }
    public static void setCollectablePattern(){
        CollectableMenu.getMenu().addPattern("enter [\\w]+");
        CollectableMenu.getMenu().addPattern("[\\d]+");
        CollectableMenu.getMenu().addPattern("help");
        CollectableMenu.getMenu().addPattern("show");
        CollectableMenu.getMenu().addPattern("exit");
        CollectableMenu.getMenu().addPattern("Show info");
        CollectableMenu.getMenu().addPattern("Use \\[[\\d+] [\\d]+\\]");
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

    public static void main(String[] args) {
        Scanner commands=Game.accounts[Game.battle.getTurn()].getOutputStream();
        currentMenu.showMenu();

        while(commands.hasNext()){
            try {
                String command = commands.nextLine().toLowerCase().trim();
                String[] word = command.split(" ");
                if (!currentMenu.allowsCommand(command)) {
                    System.out.println("Invalid Command");
                    continue;
                }
                if (commonCommandHandler(word)) {
                } else if (currentMenu instanceof SignInMenu) {
                    SignInMenuCommandHandler(word);
                } else if (currentMenu instanceof CollectionMenu) {
                    CollectionMenuCommandHandler(word);
                } else if (currentMenu instanceof ShopMenu){
                    ShopMenuCommandHandler(word);
                }else if(currentMenu instanceof Battle){
                    BattleCommandHandler(word);
                }else if(currentMenu instanceof ChooseBattleModeMenu){
                    ChooseBattleModeMenuCommandHandler(word);
                }else if(currentMenu instanceof StoryModeMenu){
                    storyModeMenuCommandHandler(word);
                }else if(currentMenu instanceof MultiPlayerModeMenu){
                    MultiPlayerMenuCommandHandler(word);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            currentMenu.showMenu();
            commands=Game.accounts[Game.battle.getTurn()].getOutputStream();
        }
    }

    private static void MultiPlayerMenuCommandHandler(String[] word) {
        MultiPlayerModeMenu menu= (MultiPlayerModeMenu) currentMenu;
        if(word[0].equals("select") && word[1].equals("user")){
            try {
                menu.selectUser(word[2],word[3]);
                currentMenu=menu.enter(Battle.getMenu());
            } catch (InvalidAccountException e) {
                System.out.println("this account doesnt exist");
            } catch (WrongPassException e) {
                System.out.println("username and pass word dont match try again");
            }
        }
    }

    private static void storyModeMenuCommandHandler(String[] word) {
        StoryModeMenu menu= (StoryModeMenu) currentMenu;
        if(word[0].equals("level")) {
            currentMenu=menu.setAI(Integer.parseInt(word[1]));
        }
    }

    private static void ShopMenuCommandHandler(String[] word) {
        ShopMenu menu = (ShopMenu) currentMenu;
        if(word[0].equals("show")){
            if(word.length>= 2 && word[1].equals("collection")){
                menu.showCollection();
            }else{
                System.err.println("asdddddddddddddd");
                menu.show();
                System.err.println("asdddddddddddddd");
            }
        }else if(word[0].equals("search")){
            if(word.length>= 2 && word[1].equals("collection")){
                try {
                    menu.searchCollection(word[2]);
                } catch (InvalidCardException e) {
                    System.out.println("Card with Given name doesnt exist");
                } catch (InvalidItemException e) {
                    System.out.println("Item with Given name doesnt exist");
                }
            }else {
                try {
                    menu.search(word[1]);
                } catch (InvalidCardException e) {
                    System.out.println("Card with Given name doesnt exist");
                } catch (InvalidItemException e) {
                    System.out.println("Item with Given name doesnt exist");
                }
            }
        }else if(word[0].equals("buy")){
            try {
                menu.buy(word[1]);
            } catch (CardExistException e) {
                System.out.println("You already have this Card. it is not wise to buy a same card twice");
            } catch (InvalidCardException | InvalidItemException e) {
                System.out.println("my lord! we just ran out of " + word[1] + ". im sorry!");
            } catch (ItemExistExeption itemExistExeption) {
                System.out.println("You already have this Item. it is not wise to buy a same item twice");
            } catch (FullCollectionException e) {
                System.out.println("sorry but you dont have enough Space in your collection");
                System.out.println("empty your collection a little bit by selling some card and try later");
            } catch (NotEnoughMoneyException e) {
                System.out.println("Oops you are not as reach as you thought!");
            }
        }else if(word[0].equals("sell")){
            try {
                menu.sell(word[1]);
            } catch (InvalidCardException e) {
                System.out.println("Smart Move But you cant sell a Card/Item that you dont have");
            }
        }
    }
    private static void CollectionMenuCommandHandler(String[] word) {
        CollectionMenu menu= (CollectionMenu) currentMenu;
        if(word[0].equals("show")){
            if(word.length>= 3 && word[1].equals("all") && word[2].equals("decks")){
                menu.showAllDecks();
            }else if(word.length>= 2 && word[1].equals("deck")){
                try {
                    menu.showDeck(word[2]);
                } catch (InvalidDeckException e) {
                    System.out.println("Deck with the name "+word[2]+" doesnt exists ");
                }
            }else{
                menu.showCollection();
            }
        }else if(word[0].equals("search")){
            menu.search(word[1]);
        }else if(word[0].equals("save")){
            menu.save();
        }else if(word[0].equals("create") && word[1].equals("deck")){
            try {
                menu.createNewDeck(word[2]);
            } catch (DeckAlreadyExistException e) {
                System.out.println("Deck with the exact same name already exist please try again");
            }
        }else if(word[0].equals("delete") && word[1].equals("deck")){
            try {
                menu.deleteDeck(word[2]);
            } catch (InvalidDeckException e) {
                System.out.println("Deck with the given name doesnt exist");
            }
        }else if(word[0].equals("add") && word[2].equals("to")&& word[3].equals("deck")){
            try {
                menu.addToDeck(Integer.parseInt(word[1]),word[4]);
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
                menu.removeFromDeck(Integer.parseInt(word[1]),word[4]);
            } catch (InvalidCardException e) {
                System.out.println("you dont have this Card in your Deck");
            } catch (InvalidItemException e) {
                System.out.println("you dont have this Item in your Deck");
            } catch (InvalidDeckException e) {
                System.out.println("Couldn't find the Deck!");
            }
        }else if(word[0].equals("validate") && word[1].equals("deck")){
            try {
                menu.validateDeck(word[2]);
            } catch (InvalidDeckException e) {
                System.out.println("Couldn't find the Deck!");
            }
        }else if(word[0].equals("select") && word[1].equals("deck")){
            try {
                menu.selectDeck(word[2]);
            } catch (InvalidDeckException e) {
                System.out.println("Couldn't find the Deck!");
            }
        }
    }
    private static void SignInMenuCommandHandler(String[] word) {
        SignInMenu menu= (SignInMenu) currentMenu;
        System.err.println("sign in bitch");
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
                currentMenu=menu.enter(MainMenu.getMenu());
            } catch (InvalidAccountException e) {
                System.out.println("Account doesnt exist");
            } catch (WrongPassException e) {
                System.out.println("username and password doesnt match try again");
            } catch(ArrayIndexOutOfBoundsException e){
                System.out.println("please enter in the fallowing order");
                System.out.println("1)username      2)password");
            }
        }else if(word[0].equals("show") && word[1].equals("leaderboard")){
            System.err.println("hey sexy lady");
            menu.showLeaderBoard();

        }else if(word[0].equals("save")){
            menu.save();
        }else if(word[0].equals("logout")){
            menu.logOut();
        }
    }
    private static boolean commonCommandHandler(String[] word) {
        /*common commands*/
        if(word[0].equals("help")){
            currentMenu.help();
            return true;
        }
        else if(word.length >= 2 && word[0].equals("show") && word[1].equals("menu")){
            currentMenu.showMenu();
            return true;
        }else if(word[0].equals("enter")){
            try {

                try {
                    currentMenu = currentMenu.enter(currentMenu.getMenuFromSubMenus(word[1]));
                } catch (InvalidSubMenuException e) {
                    System.out.println("the requested menu doesnt exist");
                }
            }catch (IndexOutOfBoundsException e){
                System.out.println("please choose a number between 1 and " + currentMenu.getSubMenus().size());
            }
            return true;
        }else if(word[0].equals("exit")){
            if(currentMenu.getParentMenu()==null) System.out.println("This is the root menu!");
            else {
                currentMenu=currentMenu.exit();
                return true;
            }
        }
        return false;
    }
    private static void ChooseBattleModeMenuCommandHandler(String[] word) {
        ChooseBattleModeMenu menu= (ChooseBattleModeMenu) ChooseBattleModeMenu.getMenu();
        if(word[0].equals("mode")){
            switch (Integer.parseInt(word[1])){
                case 1:
                case 2:
                case 3:
                    Game.battle.setGameMode(new ClassicMode());
                    currentMenu=menu.enter(GameModeMenu.getMenu());
                    break;
                default:
                    System.out.println("please Enter a Number between 1 and 3");
            }
        }
    }
    private static void BattleCommandHandler(String[] word) {
        Battle menu= (Battle) currentMenu;
        if(word[0].equals("game") && word[1].equals("info")){
            menu.gameInfo();
        }
        else if(word[0].equals("show")){
            if(word[1].equals("my") && word[2].equals("minions")){
                menu.showMyMinions();
            }else if(word[1].equals("opponent") && word[2].equals("minions")){
                menu.showMyOpponentMinion();
            }else if(word[1].equals("card") && word[2].equals("info")){
                try {
                    menu.showCardInfo(Integer.parseInt(word[3]));
                } catch (InvalidCardException e) {
                    System.out.println("Couldn't find the card!");
                }
            }else if(word[1].equals("hand")){
                menu.showHand();
            }else if(word[1].equals("collectable")){
                menu.showCollectable();
            }else if(word[1].equals("next") && word[2].equals("card")){
                menu.showNextCard();
            }
        }else if(word[0].equals("select")){
            try {
                menu.select(Integer.parseInt(word[1]));
            } catch (InvalidCardException e) {
                System.out.println("im afraid that you dont acquire this card");
            } catch (InvalidItemException e) {
                System.out.println("im afraid that you dont acquire this item");
            }
        }else if(word[0].equals("move") && word[1].equals("to") ){
            try {
                menu.move(Integer.parseInt(word[2]),Integer.parseInt(word[3]));
            } catch (NoCardHasBeenSelectedException e) {
                System.out.println("please select a card first");
            } catch (CardCantBeMovedException e) {
                System.out.println("this card cant be moved due the spell unleashed upon it");
            } catch (MoveTrunIsOverException e) {
                System.out.println("there is no time to move !!!");
                System.out.println("Attack attack attack");
            } catch (DestinationOutOfreachException e) {
                System.out.println("Ooooo to far!");
                System.out.println("please set the destination some were close");
            } catch (InvalidCellException e) {
                System.out.println("Im afraid our little word doesnt have enough space for your ambitions");
            }
        }else if(word[0].equals("attack")){
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
        }else if(word[0].equals("use") && word[1].equals("special") && word[2].equals("power")){
            menu.useSpecialPower(Integer.parseInt(word[3]),Integer.parseInt(word[4]));
        }else if(word[0].equals("insert")){
            try {
                menu.insert(Integer.parseInt(word[1]),Integer.parseInt(word[3]),Integer.parseInt(word[4]));
            } catch (InvalidCardException e) {
                System.out.println("thre is no such card in your hand");
            } catch (NotEnoughManaException e) {
                System.out.println("lets collect some mana first!");
            } catch (DestinationIsFullException e) {
                System.out.println("cant spwan/deploy card on the selected destination");
            } catch (InvalidCellException e) {
                System.out.println("Im afraid our little word doesnt have enough space for your ambitions");
            }
        }else if(word[0].equals("end") && word[1].equals("turn")){
            try {
                menu.endTurn();
            } catch (HandFullException | DeckIsEmptyException | InvalidCardException e) {
                // :D
            }
        }
    }
}
