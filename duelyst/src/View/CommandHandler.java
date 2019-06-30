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
import java.util.ArrayList;

public class CommandHandler {

    protected OutputStream output;

    public CommandHandler() {

    }


    public void setOutputStream(OutputStream output) {
        this.output = output;
    }


    //commandHandler
    public  void handleCommand(String command) {
        command=command.trim().toLowerCase();
        try {
//            command = commands.nextLine().toLowerCase().trim();
            System.err.println("debug");
            String[] word = command.toLowerCase().split(" ");
            if (!MenuHandler.getCurrentMenu().allowsCommand(command)) {
                System.out.println("Invalid Command");
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

    private static boolean commonCommandHandler(String[] word) {
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
                    System.out.println("the requested menu doesnt exist");
                }
            }catch (IndexOutOfBoundsException e){
                System.out.println("please choose a number between 1 and " + MenuHandler.getCurrentMenu().getSubMenus().size());
            }
            return true;
        }else if(word[0].equals("exit")){
            if(MenuHandler.getCurrentMenu().getParentMenu()==null) System.out.println("This is the root menu!");
            else {
                MenuHandler.setCurrentMenu(MenuHandler.getCurrentMenu().exit());
                return true;
            }
        }
        return false;
    }

    private static void GraveYardMenuCommandHandler(String[] word) {
        GraveYardMenu menu= (GraveYardMenu) MenuHandler.getCurrentMenu();
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
    private static void CustomModeMenuCommandHandler(String[] word) {
        if(word[0].equals("select")){
            CustomModeMenu menu= (CustomModeMenu) MenuHandler.getCurrentMenu();
            try {
                MenuHandler.setCurrentMenu(menu.selectDeck(word[2]));
            } catch (InvalidDeckException e) {
                System.err.println(word[2]);
                System.out.println("Couldnt find the deck");
            }
        }
    }
    private static void MultiPlayerMenuCommandHandler(String[] word) {
        MultiPlayerModeMenu menu= (MultiPlayerModeMenu) MenuHandler.getCurrentMenu();
        if(word[0].equals("select") && word[1].equals("user")){
            try {
                menu.selectUser(word[2],word[3]);
                MenuHandler.setCurrentMenu(menu.enter(Battle.getMenu()));
            } catch (InvalidAccountException e) {
                System.out.println("this account doesnt exist");
            } catch (WrongPassException e) {
                System.out.println("username and pass word dont match try again");
            }
        }
    }
    private static void storyModeMenuCommandHandler(String[] word) {
        System.err.println("debug");
        StoryModeMenu menu= (StoryModeMenu) MenuHandler.getCurrentMenu();
        if(word[0].equals("level")) {
            MenuHandler.setCurrentMenu(menu.setAI(Integer.parseInt(word[1])));
        }
    }
    private static void CollectionMenuCommandHandler(String[] word) {
        CollectionMenu menu= (CollectionMenu) MenuHandler.getCurrentMenu();
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
        SignInMenu menu= (SignInMenu) MenuHandler.getCurrentMenu();
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
                MenuHandler.setCurrentMenu(menu.enter(MainMenu.getMenu()));
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
        ChooseBattleModeMenu menu= ChooseBattleModeMenu.getMenu();
        System.err.println("debug");
        if(word[0].equals("mode")){
            menu.setMode(Integer.parseInt(word[1]));
        }
    }
    private static void BattleCommandHandler(String[] word,String command) {
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