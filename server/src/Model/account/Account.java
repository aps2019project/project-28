package Model.account;

import Controller.Game;
import Model.account.player.Player;
import Model.mediator.AccountMediator;
import exeption.AccountAlreadyExistsException;
import exeption.InvalidAccountException;
import Model.Primary;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Account {

    private static final Account defaultAccount = new Account("Duelyst","SAF","Pass the fucking word");
    public static final Account[] AI={new Account("EL_DUELYSTA","Costum","AI")
            ,new Account("sheraider","level_1","AI")
            ,new Account("mster_yoda","level_2","AI")
            ,new Account("thanos","level_3","AI")};

    private static int unique = 0;
    private static final int INITIAL_MONEY = 99999999;

    private static AccountMediator mediator;


    protected Player player;
    protected String name;
    protected String username;
    protected String password;
    protected int ID;
    private int money;//unit :derik
    protected Collection collection = new Collection();
    private ArrayList<Match> matchHistory;
    private int wins;
    private int storyModeSPX;
    private String avatar;
    private boolean isOnline = false ;





    public static boolean addNewAccount(Account account) throws AccountAlreadyExistsException {
        try {
            return mediator.addNewAccount(account);
        } catch (AccountAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    public static void addNewAccount(String name, String username, String password) throws AccountAlreadyExistsException {
        addNewAccount(new Account(name, username, password));
    }

    public static void save() {
        try {
            mediator.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void save(Account account){
        int i=0;
        for (Account acc: Account.getAccounts()) {
            if(acc.getUsername().equals(account.getUsername()))break;
            i++;
        }
        Account.getAccounts().set(i,account);
        Account.save();
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
        try {
            return mediator.getAccount(username);
        } catch (InvalidAccountException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Account getAccount(int ID) throws InvalidAccountException {
        try {
            return mediator.getAccount(ID);
        } catch (InvalidAccountException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean hasAccount(String username) {
        try {
            Account.getAccount(username);
            return true;
        } catch (InvalidAccountException e) {
            return false;
        }
    }

    public static ArrayList<Account> getAccounts() {
        try {
            return mediator.getAccounts();
        } catch (Exception e) {
            e.printStackTrace();
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
//            return null;
        }
    }

    public static void updateAccounts() {
        Primary.accounts.forEach(account -> account.getCollection().updateCollection());
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
        if (this.player == null){
            this.player = new Player(this, 2, 2);
            Game.setDefaultGI(player);
        }
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public static ArrayList<Account> sort() {
        Account.getAccounts().sort(Comparator.comparingInt(o -> o.wins));
        Collections.reverse(Account.getAccounts());
        return Account.getAccounts();
    }

    public void clearCollection() {
        this.collection = new Collection();
    }

    public void saveMatch(Match match){
        this.matchHistory.add(match);
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public static void setMediator(AccountMediator mediator) {
        Account.mediator = mediator;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
