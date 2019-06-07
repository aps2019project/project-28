package Model.account;

import Controller.menu.Battle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class Command {
    Account owner;
    String command;
    Long time;

    public Command(String command,Account owner) {
        this.owner=owner;
        this.command = command;
        this.time = System.currentTimeMillis();
    }
}

public class Match {

    private Account[] accounts;
    private Date lastDateModified;
    private int state; //0-->first player won    1-->second player won


    private ArrayList<Command>commands=new ArrayList<>();
    private int commandsPointer=0;


    public Match(Account first, Account second) {

        // TODO: 6/7/19 fattme first o second ro inja ba json hard copy bezan plz
        this.accounts =new Account[]{first,second};
        this.lastDateModified=new Date();
    }

    public void addCommand(String command,int turn){
        commands.add(new Command(command,accounts[turn]));
    }

    public Command getNextCommand() {
        return commands.get(commandsPointer++);

    }


    //    {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        Date date = new Date();
//        System.out.println(formatter.format(date));
//    }

}
