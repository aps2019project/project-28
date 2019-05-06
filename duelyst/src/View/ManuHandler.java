package View;

import Controller.menu.Battle;
import Controller.menu.*;
import Model.account.Account;
import Model.account.Collection;
import View.Listeners.OnMenuClickedListener;
import exeption.AccountAlreadyExistsException;
import exeption.InvalidAccountException;
import exeption.WrongPassException;

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
    static{
        initMenus();
        setListener();
    }

    private static void setListener(){
        //show menu
        SignInMenu.getMenu().addMenuClickListener(menu -> System.out.println("SignInMenu:"));
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

        //signIn menu
        SignInMenu.getMenu().addLeaderBoardClickedListener(accounts -> {
            System.out.println("LeaderBoard:");
            int i=0;
            for (Account account : accounts) {
                i++;
                System.out.println(i+") "+account.getName() + " - Wins: " +account.getWins());
            }
        });
    }
    private static void initMenus() {
        //az SignIn Menu mirim tuye MainMenu

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

    public static void main(String[] args) {
        Scanner commands=new Scanner(System.in);
        currentMenu.showMenu();
        while(commands.hasNext()){
            currentMenu.showMenu();
            String command = commands.nextLine().toLowerCase();
            String[] word=command.split(" ");
            if(!currentMenu.allowsCommand(command)) {
                System.out.println("Invalid Command");
                continue;
            }
            if(commonCommandHandler(word))continue;
            if(currentMenu instanceof SignInMenu) {
                SignInMenuCommandHandler(word);
            }else if(currentMenu instanceof CollectionMenu){
                CollectionMenu menu= (CollectionMenu) currentMenu;
                if(word[0].equals("show")){
                    if(word[1].equals("all") && word[2].equals("decks")){

                    }else if(word[1].equals("deck")){

                    }else{
                        menu.showCollection();
                    }
                }
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
                System.out.println("1)username     2)name      3)password");
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
            currentMenu=currentMenu.enter(currentMenu.getSubMenus().get(Integer.parseInt(word[0])));
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
}
