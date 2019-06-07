package Model.account;
import Controller.Game;
import Controller.GameMode.GameMode;

import java.util.ArrayList;
import java.util.Date;


public class Match {

    private Account[] accounts;
    private Date lastDateModified;
    private int state; //0-->first player won    1-->second player won
    private long startingTime;

    private ArrayList<Command>commands=new ArrayList<>();
    private int commandsPointer=0;

    private GameMode gamemode;

    public Match(Account first, Account second, GameMode gameMode) {

        // TODO: 6/7/19 fattme first o second ro inja ba json hard copy bezan plz
        this.accounts =new Account[]{first,second};
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
