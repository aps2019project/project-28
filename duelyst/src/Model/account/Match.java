package Model.account;
import Controller.Game;
import Controller.GameMode.GameMode;

import com.gilecode.yagson.YaGson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Match {

    private Account[] accounts;
    private Date lastDateModified;
    private int state; //0-->first player won    1-->second player won
    private long startingTime;
    private GameMode gamemode;

    private ArrayList<Command>commands=new ArrayList<>();
    private int commandsPointer=0;

    public Match(Account first, Account second, GameMode gameMode) {

        this.accounts =new Account[]{first,second};
        Player firstPlayer = first.player;
        Player secondPlayer = second.player;
        first.player = null;
        second.player = null;
        YaGson gson = new YaGson();
        String firstAccount = gson.toJson(first);
        String secondAccount = gson.toJson(second);
        this.accounts[0] = gson.fromJson(firstAccount, Account.class);
        this.accounts[1] = gson.fromJson(secondAccount, Account.class);
        accounts[0].player = firstPlayer;
        accounts[1].player = secondPlayer;
        this.lastDateModified=new Date();
        this.startingTime=System.currentTimeMillis();
        this.gamemode=gameMode;
    }

    public void addCommand(String command,int turn){
        commands.add(new Command(command,accounts[turn],System.currentTimeMillis()-startingTime));
    }

    public Command getNextCommand() {
        if(commandsPointer<commands.size())
            return commands.get(commandsPointer++);
        return null;
    }

    public GameMode getGameMode() {
        return gamemode;
    }

}
