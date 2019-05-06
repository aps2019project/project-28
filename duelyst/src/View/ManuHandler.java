package View;

import Controller.menu.Battle;
import Controller.menu.*;
import Model.account.Account;
import View.Listeners.OnLeaderBoardClickedListener;
import exeption.AccountAlreadyExistsException;
import exeption.InvalidAccountException;
import exeption.WrongPassException;

import java.util.ArrayList;
import java.util.Scanner;


// TODO: 5/5/19 command select collectable(page 20) change menu from battle to collectable menu
// TODO: 5/5/19 command entergraveyard changes menu from battle to graveYard


public class ManuHandler {

    private static Menu currentMenu;
    static Menu gameMode ;
    static MainMenu mainMenu;
    static SignInMenu signInMenu;
    static CollectionMenu collectionMenu;
    static CollectableMenu collectableMenu;
    static ShopMenu shopMenu;
    static Battle battle;
    static ChooseBattleModeMenu chooseBattleModeMenu;
    static CostumeModeMenu costumeModeMenu;
    static MultiPlayerModeMenu multiPlayerModeMenu;
    static SinglePlayerModeMenu singlePlayerModeMenu;
    static GraveYardMenu graveYardMenu;
    static StoryModeMenu storyModeMenu;

    //preprocess
    static{
        initMenus();
        setListener();
    }

    private static void setListener(){
        signInMenu.addLeaderBoardClickedListener(accounts -> {
            System.out.println("LeaderBoard:");
            int i=0;
            for (Account account : accounts) {
                i++;
                System.out.println(i+") "+account.getName() + " - Wins: " +account.getWins());
            }
        });
    }
    private static void initMenus() {
        gameMode = new Menu("gameMode") {@Override public void help() {}};
        mainMenu = new MainMenu( "MainMenu");
        signInMenu = new SignInMenu(null, "SignInMenu");
        collectionMenu = new CollectionMenu(null, "CollectionMenu");
        collectableMenu  = new CollectableMenu(null, "CollectableMenu");
        shopMenu = new ShopMenu(null, "ShopMenu");
        battle = new Battle(null, "Battle");
        chooseBattleModeMenu = new ChooseBattleModeMenu(null, "ChooseBattleMenu");
        costumeModeMenu = new CostumeModeMenu(null, "CustomeModeMenu");
        multiPlayerModeMenu = new MultiPlayerModeMenu(null, "MultiPlayerModeMenu");
        singlePlayerModeMenu = new SinglePlayerModeMenu(null, "SinglePlayerModeMenu");
        graveYardMenu = new GraveYardMenu(null, "GraveYardMenu");
        storyModeMenu = new StoryModeMenu(null, "StoryModeMenu");

        //az SignIn Menu mirim tuye MainMenu

        mainMenu.addSubMenu(collectionMenu);
        mainMenu.addSubMenu(shopMenu);
        mainMenu.addSubMenu(chooseBattleModeMenu);

        chooseBattleModeMenu.addSubMenu(singlePlayerModeMenu);
        chooseBattleModeMenu.addSubMenu(multiPlayerModeMenu);

        singlePlayerModeMenu.addSubMenu(storyModeMenu);
        singlePlayerModeMenu.addSubMenu(costumeModeMenu);

        //az Single o Multi mirim gameMode

        battle.addSubMenu(graveYardMenu);
        battle.addSubMenu(collectionMenu);
        battle.addSubMenu(collectableMenu);

        currentMenu = signInMenu;
    }

    public static void main(String[] args) {
        Scanner commands=new Scanner(System.in);
        while(commands.hasNext()){
            String command = commands.nextLine();
            String[] word=command.split(" ");
            if(!currentMenu.allowsCommand(command)) System.out.println("Invalid Command");

            if(word[0].equals("create") && word[1].equals("account")){
                SignInMenu menu= (SignInMenu) currentMenu;
                try {
                    menu.creatAccount(word[2],word[3],word[4]);
                } catch (AccountAlreadyExistsException e) {
                    System.out.println("this userName is already taken");
                } catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("please enter in the fallowing order");
                    System.out.println("1)name     2)username      3)password");
                }
            }else if(word[0].equals("login")){
                SignInMenu menu= (SignInMenu) currentMenu;
                try {
                    menu.logIn(word[1],word[2]);
                } catch (InvalidAccountException e) {
                    System.out.println("Account doesnt exist");
                } catch (WrongPassException e) {
                    System.out.println("username and password doesnt match try again");
                } catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("please enter in the fallowing order");
                    System.out.println("1)username      2)password");
                }
            }else if(word[0].equals("show") && word[1].equals("‫‪leaderboard")){
                SignInMenu menu= (SignInMenu) currentMenu;
                menu.showLeaderBoard();
            }
        }
    }
}
