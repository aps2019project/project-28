package network.client;

import Controller.Game;
import Controller.menu.Battle;
import Controller.menu.SignInMenu;
import Model.account.Account;
import Model.account.player.OnlinePlayer;
import network.Message;

import java.io.IOException;
import java.net.Socket;

public class BattleClient extends Client{

    private final int PORT=8888;
    private final String HOST="127.0.0.1";

    public BattleClient(Account account) {
        super(account);
    }

    public void connect() throws IOException {
        this.setSocket(new Socket(HOST,PORT));

        Message message=new Message("Battle!");
        message.addCarry(SignInMenu.getMenu().getAccount());
        message.addCarry(Battle.getMenu().getGameMode());
        this.write(message);

        message = this.read();
        /*
        * this message carry an  account
        * */
        Account account= (Account) message.getCarry().get(0);
        int turn= (int) message.getCarry().get(1);
        account.setPlayer(new OnlinePlayer(account,2,2,this.getInput()));
        if(turn==1){
            Game.setSecondAccount(Game.getAccount(0));
            Game.setFirstAccount(account);
        }else{
            Game.setSecondAccount(account);
        }
    }
}
