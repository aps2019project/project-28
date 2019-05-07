package Controller;

import Controller.menu.Battle;
import Model.PreProcess;
import Model.account.Account;
import Controller.menu.Menu;

public class Game {
    public static Account[] accounts = new Account[2];
    public static Menu menu;
    public static Battle battle= Battle.getMenu();
    public static boolean hasLoggedIn = false;


}

