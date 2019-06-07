package Model.account;

import Model.Primary;
import com.gilecode.yagson.YaGson;
import exeption.InvalidAccountException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Account {

    private static final Account defaultAccount = new Account("Duelyst","SAF","Pass the fucking word");
    public static final Account[]AI={new Account("EL_DUELYSTA","Costum","AI")
            ,new Account("sheraider","level_1","AI")
            ,new Account("mster_yoda","level_2","AI")
            ,new Account("thanos","level_3","AI")};

    protected static int unique = 0;
    protected static final int INITIAL_MONEY = 99999999;

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




    public static void addNewAccount(Account account) {
        if (account == null) return;
        if (Account.hasAccount(account)) return;
        Account.getAccounts().add(account);
        YaGson gson = new YaGson();
        try {
            FileWriter fileWriter = new FileWriter("Account.json", true);
            gson.toJson(account, fileWriter);
            fileWriter.write("\n");
            fileWriter.close();
        } catch (IOException e) {
        }
    }

    public static void save() {
        YaGson gson = new YaGson();
        File file = new File("Account.json");
        file.delete();
        for (Account account:
                Primary.accounts) {
            try{
                FileWriter fileWriter = new FileWriter("Account.json", true);
                account.player=null;
                gson.toJson(account, fileWriter);
                fileWriter.write("\n");
                fileWriter.close();
            } catch (IOException ignored) {}
        }
    }

    public static Account getDefaultAccount(){
        return Account.defaultAccount;
    }
    public Account(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.ID = Account.unique++;
        this.money = Account.INITIAL_MONEY;
        this.collection = new Collection();
        this.matchHistory = new ArrayList<>();
        this.wins = 0;
        this.storyModeSPX = 0;
    }

    public static Account getAccount(String username) throws InvalidAccountException {
        for (Account account : Account.getAccounts()) {
            if (account.getUsername().equals(username)) return account;
        }
        throw new InvalidAccountException();
    }

    public static Account getAccount(int ID) throws InvalidAccountException {
        for (Account account : Account.getAccounts()) {
            if (account.getID() == ID) return account;
        }
        throw new InvalidAccountException();
    }

    public static boolean hasAccount(String username) {
        try {
            Account.getAccount(username);
            return true;
        } catch (InvalidAccountException e) {
            return false;
        }
    }

    public static boolean hasAccount(Account account) {
        try {
            Account.getAccount(account.getUsername());
            return true;
        } catch (InvalidAccountException e) {
            return false;
        }
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
        Collections.reverse(Account.getAccounts());
        return Account.getAccounts();
    }

    public void clearCollection() {
        this.collection=new Collection();
    }

    public void saveMatch(Match match){
        this.matchHistory.add(match);
    }
}
