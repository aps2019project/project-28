package View;

import Controller.GameMode.ClassicMode;
import Controller.GameMode.FlagMode;
import Controller.menu.Battle;
import Controller.menu.*;
import Model.PreProcess;
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
            PreProcess.preprocess();
        } catch (FileNotFoundException e) {
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
            ChooseBattleModeMenu.getMenu().addMenuClickListener(new ShowMenu());
            CollectableMenu.getMenu().addMenuClickListener(new ShowMenu());
            CollectionMenu.getMenu().addMenuClickListener(new ShowMenu());
            CostumeModeMenu.getMenu().addMenuClickListener(new ShowMenu());
            GameModeMenu.getMenu().addMenuClickListener(new ShowMenu());
            GraveYardMenu.getMenu().addMenuClickListener(new ShowMenu());
            MainMenu.getMenu().addMenuClickListener(new ShowMenu());
            MultiPlayerModeMenu.getMenu().addMenuClickListener(new ShowMenu());
            ShopMenu.getMenu().addMenuClickListener(new ShowMenu());
            SinglePlayerModeMenu.getMenu().addMenuClickListener(new ShowMenu());
            StoryModeMenu.getMenu().addMenuClickListener(new ShowMenu());
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
                System.out.println("\tAttackPoint : " + h.getOriginalAttackPoint() +
                        "\tHealth point : " + h.getOriginalHealthPoint() + "\tManaPoint : " + h.getManaPoint());
                System.out.println("\tSpecialPower : " + h.getSpecialPower().getInfo());
                System.out.println("\tSell cost : " + h.getPrice());
                System.out.println("\tID : "+h.getCardID());
            }
            private void showHermioneInfo(Hermione h){
                System.out.println("\tName : " + h.getName());
                System.out.println("\tClass : " + h.getAttackType().getClass().toString());
                System.out.println("\tAttackPoint : " + h.getAttackPoint() +
                        "\tHealth point : " + h.getHealthPoint() + "\tManaPoint : " + h.getManaPoint());
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
                System.out.println("\tSpecialPower : " + hero.getSpecialPower().getInfo());
                System.out.println("\tSell cost : " + hero.getPrice());
        });

        //deck
        Deck.addNewOnDeckPresentedListener(deck -> allCardPresenter(deck.getCards(),deck.getUsables()));

        //GameInfo
        Battle.getMenu().addGameInfoPresentedListener(() -> {
            if(Battle.getMenu().getGameMode() instanceof ClassicMode){
                System.out.println(Battle.getMenu().getAccount().getName()+" : " + Battle.getMenu().getAccount().getPlayer().getDeck().getHero().getHealthPoint());
                System.out.println(Battle.getMenu().getEnemy(Battle.getMenu().getAccount()).getUser().getName()+" : " + Battle.getMenu().getEnemy(Battle.getMenu().getAccount()).getDeck().getHero().getHealthPoint());
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
            System.out.println("Name : " + item.getName());
            System.out.println("Desc : " + item.getComment());
            System.out.println("Sell cost" + ((Usable) item).getPrice());
        });
    }
    private static void initMenus() {
        //az SignIn Menu mirim tuye MainMenu

        SignInMenu.getMenu().addSubMenu(MainMenu.getMenu());

        MainMenu.getMenu().addSubMenu(CollectionMenu.getMenu());
        MainMenu.getMenu().addSubMenu(ShopMenu.getMenu());
        MainMenu.getMenu().addSubMenu(ChooseBattleModeMenu.getMenu());

        ChooseBattleModeMenu.getMenu().addSubMenu(SinglePlayerModeMenu.getMenu());
        ChooseBattleModeMenu.getMenu().addSubMenu(MultiPlayerModeMenu.getMenu());

        SinglePlayerModeMenu.getMenu().addSubMenu(StoryModeMenu.getMenu());
        SinglePlayerModeMenu.getMenu().addSubMenu(CostumeModeMenu.getMenu());

        //az Single o Multi mirim gameMode

        Battle.getMenu().addSubMenu(GraveYardMenu.getMenu());
        Battle.getMenu().addSubMenu(CollectionMenu.getMenu());
        Battle.getMenu().addSubMenu(CollectableMenu.getMenu());

        currentMenu = SignInMenu.getMenu();
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
                menu.creatNewDeck(word[2]);
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
        }else if(word[0].equals("show") && word[1].equals("‫‪leaderboard")){
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
        }else if(word[0].matches("[\\d]")){
            currentMenu=currentMenu.enter(currentMenu.getSubMenus().get(Integer.parseInt(word[0])-1));
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

    public static void setPatterns(){
        setSignInPatterns();
        setCollectionPatterns();
        setShopPatterns();
        setBattlePatterns();
        setGraveyardPatterns();
        setCollectablePattern();
        setMainMenuPattern();
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


    public static void main(String[] args) {
        for (Card card:
        Shop.getInstance().getCollection().getCards()) {
            System.out.println(card.getName());
        }

        Scanner commands = new Scanner(System.in);
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
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            currentMenu.showMenu();


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
