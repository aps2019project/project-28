package Model.account;

import Controller.Match;
import exeption.InvalidAccountException;

import java.util.ArrayList;
public class Account {

    private static ArrayList<Account> accounts=new ArrayList<>();
    private static int unique =0;
    private static final int INITIAL_MONEY = 1500;
    private Player player;
    private String name;
    private String username;
    private String password;
    private int ID;
    private int money;//unit :derik
    private Collection collection;
    private ArrayList<Match> matchHistory;
    private int wins;
    private int storyModeSPX;

    public static void sort(){
    }
    public void saveMatchHistory(Match match){
    }

    public static void addNewAccount(Account account){
        if(Account.hasAccount(account))return;
        Account.getAccounts().add(account);
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
        for (Account account : Account.getAccounts()) {
            if(account.getUsername().equals(username))return account;
        }
        throw new InvalidAccountException();
    }
    public static Account getAccount(int ID) throws InvalidAccountException {
        for (Account account : Account.getAccounts()) {
            if(account.getID()==ID)return account;
        }
        throw new InvalidAccountException();
    }

    public static boolean hasAccount(String username){
        try {
            Account.getAccount(username);
            return true;
        }
        catch (InvalidAccountException e){
            return false;
        }
    }
    public static boolean hasAccount(int ID){
        try {
            Account.getAccount(ID);
            return true;
        }
        catch (InvalidAccountException e){
            return false;
        }
    }
    public static boolean hasAccount(Account account){
        try {
            Account.getAccount(account.getID());
            return true;
        }
        catch (InvalidAccountException e){
            return false;
        }
    }




    public static ArrayList<Account> getAccounts() {
        return accounts;
    }
    public static void setAccounts(ArrayList<Account> accounts) {
        Account.accounts = accounts;
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
        Account account=(Account)obj;

        return this.ID==account.ID;
    }


    public Player getPlayer() {
        if(this.player==null)
            this.player=new Player(this,2,2);
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
