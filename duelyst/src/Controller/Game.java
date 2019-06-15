package Controller;

import Controller.menu.Battle;
import Model.account.Account;
import Controller.menu.Menu;

import java.util.Scanner;

public class Game {
    public static Account[] accounts ={Account.getDefaultAccount(),Account.getDefaultAccount()};
    public static Menu menu;
    public static Battle battle= Battle.getMenu();
    public static boolean hasLoggedIn = false;
    public static Scanner scanner=new Scanner(System.in);

}

