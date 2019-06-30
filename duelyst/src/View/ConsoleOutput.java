package View;

import Controller.GameMode.ClassicMode;
import Controller.GameMode.Domination;
import Controller.GameMode.FlagMode;
import Controller.menu.*;
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
import exeption.InvalidCellException;
import exeption.InvalidSubMenuException;

import java.util.ArrayList;
import java.util.stream.Stream;


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

    private static ConsoleOutput instance;

    public static ConsoleOutput getInstance(){
        if(instance==null)instance=new ConsoleOutput();
        return instance;
    }

    private ConsoleOutput() {
        setListener();
    }


//    public void getOutput(Object outcome){
//            if (commonCommandOutput(outcome)) {
//
//            } else if (MenuHandler.getCurrentMenu() instanceof SignInMenu) {
//                SignInMenuCommandoutput(outcome);
//            } else if (MenuHandler.getCurrentMenu() instanceof CollectionMenu) {
//                CollectionMenuCommandoutput(outcome);
//            } else if (MenuHandler.getCurrentMenu() instanceof ShopMenu){
//                ShopMenuCommandoutput(outcome);
//            }else if(MenuHandler.getCurrentMenu() instanceof Battle){
//                BattleCommandoutput(outcome);
//            }else if(MenuHandler.getCurrentMenu() instanceof ChooseBattleModeMenu){
//                ChooseBattleModeMenuCommandoutput(outcome);
//            }else if(MenuHandler.getCurrentMenu() instanceof StoryModeMenu){
//                storyModeMenuCommandoutput(outcome);
//            }else if(MenuHandler.getCurrentMenu() instanceof MultiPlayerModeMenu){
//                MultiPlayerMenuCommandoutput(outcome);
//            }else if(MenuHandler.getCurrentMenu() instanceof CustomModeMenu){
//                CustomModeMenuCommandoutput(outcome);
//            }else if(MenuHandler.getCurrentMenu() instanceof GraveYardMenu){
//                GraveYardMenuCommandoutput(outcome);
//            }
//    }
//
//    private boolean commonCommandOutput(Object outcome) {
//        if(outcome==null)
//            return true;
//        else if(outcome instanceof String){
//            System.out.println(outcome);
//            return true;
//        }else if(outcome instanceof InvalidSubMenuException) {
//            System.out.println("the requested menu doesnt exist");
//            return true;
//        } else if(outcome instanceof IndexOutOfBoundsException ){
//                System.out.println("please choose a number between 1 and " + MenuHandler.getCurrentMenu().getSubMenus().size());
//            return true;
//        }
//        return false;
//    }


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
            CustomModeMenu.getMenu().addMenuClickListener(new ShowMenu());
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

}
