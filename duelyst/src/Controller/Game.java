package Controller;

import Controller.menu.Battle;
import Model.account.Account;
import Controller.menu.Menu;
import Model.account.player.Bot;
import Model.account.player.CGI;
import Model.account.player.GameInterFace;
import Model.account.player.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class Game {
    public static Account[] accounts = {Account.getDefaultAccount(), Account.getDefaultAccount()};
    public static Menu menu;
    public static Battle battle = Battle.getMenu();
    public static boolean hasLoggedIn = false;
    public static Scanner scanner = new Scanner(System.in);

    public static Class userGI;
    public static Class botGI;
    public static Class defaultGI;

    public static Class getDefaultGI() {
        return defaultGI;
    }

    public static void setDefaultGI(Class defaultGI) {
        Game.defaultGI = defaultGI;
    }

    public static Class getUserGI() {
        return userGI;
    }

    public static void setUserGI(Class GI) {
        Game.userGI = GI;
    }

    public static Class getBotGI() {
        return botGI;
    }

    public static void setBotGI(Class botGI) {
        Game.botGI = botGI;
    }

    public static void setGI(Player player) {
        try {
            if (player instanceof Bot){
                GameInterFace GI=(GameInterFace) botGI.getConstructor().newInstance();
                GI.setPlayer(player);
                player.setGI(GI);
            }
            else {
                GameInterFace GI=(GameInterFace) userGI.getConstructor().newInstance();
                GI.setPlayer(player);
                player.setGI(GI);
                player.setGI(GI);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    public static void setDefaultGI(Player player){
        try {
            GameInterFace GI=(GameInterFace) userGI.getConstructor().newInstance();
            GI.setPlayer(player);
            player.setGI(GI);
            player.setGI(GI);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}

