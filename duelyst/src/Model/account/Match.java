package Model.account;

import com.gilecode.yagson.YaGson;

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
        YaGson gson = new YaGson();
        this.player = gson.fromJson(gson.toJson(player), Account.class);
        this.enemy = gson.fromJson(gson.toJson(enemy), Account.class);
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
