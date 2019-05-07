package View;

import Controller.menu.Battle;
import Controller.menu.*;
import Model.PreProcess;
import Model.account.Account;
import Model.account.Collection;
import Model.account.Deck;
import Model.card.Card;
import Model.card.OnCardDetailsPresentedListener;
import Model.card.hermione.Hermione;
import Model.card.hermione.Hero;
import Model.card.hermione.Minion;
import Model.card.spell.Spell;
import Model.item.Item;
import Model.item.OnItemDetailPresentedListener;
import Model.item.Usable;
import View.Listeners.OnHeroDetailsPresentedListener;
import exeption.*;
import java.util.ArrayList;
import java.util.Scanner;


// TODO: 5/5/19 command select collectable(page 20) change menu from battle to collectable menu
// TODO: 5/5/19 command entergraveyard changes menu from battle to graveYard

public class ManuHandler {

    private static Menu currentMenu;

    //preprocess
    public static void allCardPresenter(ArrayList<Card> cards,ArrayList<Usable> items){
        System.out.println("Heroes : ");
        int i=0;
        for (Card card : cards) {
            i++;
            System.out.print(i+") ");
            if((card instanceof Hero)) {
                for (OnHeroDetailsPresentedListener presenter : Hero.getHeroDetailsPresenters()) {
                    presenter.show((Hero) card);
                }
            }
        }
        System.out.println("Cards : ");
        i=0;
        for (Card card : cards) {
            i++;
            System.out.print(i+") ");
            if(!(card instanceof Hero)){
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
        PreProcess.preProcess();
        initMenus();
        setListener();
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
                System.out.println("\tAction : " + s.getComment());
                System.out.println("\tSell cost : " + s.getPrice());
                System.out.println("\tName : " + s.getName());
                System.out.println("\tID : "+s.getCardID());
            }
            private void showHermioneDetail(Hermione h){
                System.out.println("\tName : " + h.getName());
                System.out.println("\tClass : " + h.getAttackType().getClass().toString());
                System.out.println("\tAttackPoint : " + h.getOriginalAttackPoint() +
                        "\tHealth point : " + h.getOriginalHealthPoint() + "\tManaPoint : " + h.getManaPoint());
                System.out.println("\tSpecialPower : " + h.getSpecialPower().getComment());
                System.out.println("\tSell cost : " + h.getPrice());
                System.out.println("\tID : "+h.getCardID());
            }
            private void showHermioneInfo(Hermione h){
                System.out.println("\tName : " + h.getName());
                System.out.println("\tClass : " + h.getAttackType().getClass().toString());
                System.out.println("\tAttackPoint : " + h.getAttackPoint() +
                        "\tHealth point : " + h.getHealthPoint() + "\tManaPoint : " + h.getManaPoint());
                System.out.println("\tSpecialPower : " + h.getSpecialPower().getComment());
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
                System.out.println("\tSpecialPower : " + hero.getSpecialPower().getComment());
                System.out.println("\tSell cost : " + hero.getPrice());
        });

        //deck
        Deck.addNewOnDeckPresentedListener(deck -> allCardPresenter(deck.getCards(),deck.getUsables()));
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
        ShopMenu menu= (ShopMenu) currentMenu;
        if(word[0].equals("show")){
           if(word[1].equals("collection")){
               menu.showCollection();
           }else{
                menu.show();
           }
       }else if(word[0].equals("search")){
            if(word[1].equals("collection")){
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
            if(word[1].equals("all") && word[2].equals("decks")){
                menu.showAllDecks();
            }else if(word[1].equals("deck")){
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
        }else if(word[0].equals("show") && word[1].equals("menu")){
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

    public void setSignInPatterns(){
        SignInMenu.getMenu().addPattern("create account [\\w]+");
        SignInMenu.getMenu().addPattern("login [\\w]+ [\\w]+");
        SignInMenu.getMenu().addPattern("show leaderboard");
        SignInMenu.getMenu().addPattern("save");
        SignInMenu.getMenu().addPattern("logout");
    }

    public void setCollectionPatterns(){
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


    public void setShopPatterns(){
        ShopMenu.getMenu().addPattern("show collection");
        ShopMenu.getMenu().addPattern("search [\\w]+");
        ShopMenu.getMenu().addPattern("search collection [\\w]+");
        ShopMenu.getMenu().addPattern("buy [\\w]+");
        ShopMenu.getMenu().addPattern("sell [\\d]+");
        ShopMenu.getMenu().addPattern("show");
    }

    public void setBattlePatterns(){
        Battle.getMenu().addPattern("Game info");
        Battle.getMenu().addPattern("Show my minions");
        Battle.getMenu().addPattern("Show opponent minions");
        Battle.getMenu().addPattern("Show card info [\\d]+");
        Battle.getMenu().addPattern("Select [\\d]+");
        Battle.getMenu().addPattern("Move to ([\\d]+, [\\d]+)");//Mitune ye rqmi ham bzrim
        Battle.getMenu().addPattern("Attack [\\d]+");
        Battle.getMenu().addPattern("Attack combo [\\d]+ [\\d]+[ \\d+]+");
        Battle.getMenu().addPattern("Use special power ([\\d]+, [\\d]+)");
        Battle.getMenu().addPattern("Show hand");
        Battle.getMenu().addPattern("Insert [\\w]+ in ([\\d]+, [\\d]+)");
        Battle.getMenu().addPattern("End turn");
        Battle.getMenu().addPattern("Show collectables");
        Battle.getMenu().addPattern("Select [\\d]+");
        Battle.getMenu().addPattern("Show info");
        Battle.getMenu().addPattern("Use \\[[\\d+], [\\d]+\\]");
        Battle.getMenu().addPattern("Show Next Card");
        Battle.getMenu().addPattern("Enter graveyard");
        Battle.getMenu().addPattern("Help");
        Battle.getMenu().addPattern("End Game");
    }

    public void setGraveyardPatterns(){
        Battle.getMenu().addPattern("Show info [\\d]+");
        Battle.getMenu().addPattern("Show cards");
    }


    public static void main(String[] args) {

        Scanner commands=new Scanner(System.in);
        currentMenu.showMenu();
        while(commands.hasNext()){
            try {
                String command = commands.nextLine().toLowerCase();
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
                }
            }
            catch (Exception e){};
            currentMenu.showMenu();


        }
    }

}
