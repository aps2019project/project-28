package Model.account;

import Controller.Match;
import Model.Primary;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.internal.Primitives;
import exeption.InvalidAccountException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Predicate;

public class Account {

    protected static int unique = 0;
    protected static final int INITIAL_MONEY = 1500;

    protected Player player;
    protected String name;
    protected String username;
    protected String password;
    protected int ID;
    protected int money;//unit :derik
    protected Collection collection = new Collection();
    protected ArrayList<Match> matchHistory;
    protected int wins;
    protected int storyModeSPX;

    public void saveMatchHistory(Match match) {
    }

    public static void addNewAccount(Account account) {
        if (account == null) return;
        System.err.println(Account.hasAccount(account));
        if (Account.hasAccount(account))
            return;
        Primary.accounts.add(account);
        YaGson gson = new YaGson();
        try {
            FileWriter fileWriter = new FileWriter("Account.json");
            gson.toJson(account, fileWriter);
            fileWriter.write("\n");
            fileWriter.close();
        } catch (IOException e) {
        }
    }

    public Account(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.ID = Account.unique++;
        this.money = Account.INITIAL_MONEY;
        this.collection = new Collection();
        this.matchHistory = new ArrayList<Match>();
        this.wins = 0;
        this.storyModeSPX = 0;
    }

    public static Account getAccount(String username) throws InvalidAccountException {
        for (Account account : Primary.accounts) {
            if (account.getUsername().equals(username)) return account;
        }
        throw new InvalidAccountException();
    }

    public static Account getAccount(int ID) throws InvalidAccountException {
        for (Account account : Primary.accounts) {
            if (account.getID() == ID) return account;
        }
        throw new InvalidAccountException();
    }

    public static boolean hasAccount(String username) {
        for (Account account : Primary.accounts) {
            if (account.getUsername().equals(username)) return true;
        }
        return false;
    }

    public static boolean hasAccount(int ID) {
        for (Account account : Primary.accounts) {
            if (account.getID()==ID) return true;
        }
        return false;
    }

    public static boolean hasAccount(Account wantedAccount) {
        for (Account account : Primary.accounts) {
            System.err.println(account.getName());
            if (account.equals(wantedAccount)) return true;
        }
        return false;
    }


    public static ArrayList<Account> getAccounts() {
        return Primary.accounts;
    }

    public static int getUnique() {
        return unique;
    }

    public static void setUnique(int unique) {
        Account.unique = unique;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public ArrayList<Match> getMatchHistory() {
        return matchHistory;
    }

    public void setMatchHistory(ArrayList<Match> matchHistory) {
        this.matchHistory = matchHistory;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getStoryModeSPX() {
        return storyModeSPX;
    }

    public void setStoryModeSPX(int storyModeSPX) {
        this.storyModeSPX = storyModeSPX;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Account account = (Account) obj;

        return this.ID == account.ID;
    }


    public Player getPlayer() {
        if (this.player == null)
            this.player = new Player(this, 2, 2);
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public static ArrayList<Account> sort() {
        Collections.sort(Account.getAccounts(), Comparator.comparingInt(o -> o.wins));
        return Account.getAccounts();
    }
}
