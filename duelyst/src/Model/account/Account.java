package Model.account;

import java.util.ArrayList;
public class Account {
    private static ArrayList<Account> accounts=new ArrayList<>();
    private static int unique =0;
    private static final int INITIAL_MONEY = 1500;
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

    public void addNewAccount(Account account){
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

    public static Account getAccount(String username){
        for (Account account : Account.getAccounts()) {
            if(account.getUsername().equals(username))return account;
        }
        return null;
    }
    public static Account getAccount(int ID){
        for (Account account : Account.getAccounts()) {
            if(account.getID()==ID)return account;
        }
        return null;
    }

    public static boolean hasAccount(String username){
        return Account.getAccount(username)!=null;
    }
    public static boolean hasAccount(int ID){
        return Account.getAccount(ID)!=null;
    }
    public static boolean hasAccount(Account account){
        return Account.getAccount(account.getID())!=null;
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
}
