package Model.account;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class Command {
    Player owner;
    String command;
    Long time;

    public Command(String command,Player owner) {
        this.owner=owner;
        this.command = command;
        this.time = System.currentTimeMillis();
    }
}

public class Match {

    private Account player;
    private Account enemy;

    private Date lastDateModified;
    private int state; //0-->loose    1-->win


    private ArrayList<Command>commands=new ArrayList<>();

    public Match(Account player, Account enemy) {

        // TODO: 6/7/19 fattme player o enemy ro inja ba json hard copy bezan plz

        this.player = player;
        this.enemy = enemy;
        this.lastDateModified=new Date();
    }

    public void addCommand(String command,String owner){
        commands.add(new Command(command));
    }

    //    {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        Date date = new Date();
//        System.out.println(formatter.format(date));
//    }

}
