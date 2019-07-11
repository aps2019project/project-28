package network.client;

import Controller.Game;
import Controller.menu.Battle;
import Model.mediator.OnlineBattleMediator;
import Controller.menu.SignInMenu;
import Model.account.Account;
import Model.account.player.OnlinePlayer;
import View.MenuHandler;
import network.Message;

import java.io.IOException;
import java.net.Socket;

public class BattleClient extends Client{

    private final int PORT= MenuHandler.getBattle_port();
    private final String HOST=MenuHandler.getHost();

    public BattleClient(Account account) {
        super(account);
    }

    public void connect() throws IOException {
        this.setSocket(new Socket(HOST,PORT));

        Message message=new Message("Battle!");
        message.addCarry(SignInMenu.getMenu().getAccount());
        message.addCarry(Battle.getMenu().getGameMode());
        this.write(message);


        /*
        * server protocol for checking whether or not im still available
        * */
        message = this.read();
        if(message.getText().equals("are you ready?")){
            message=new Message("yes");
            this.write(message);
        }


        /*
        * this message carry an  account
        * */
        message=this.read();
        Account account= (Account) message.getCarry().get(0);
        int turn= (int) message.getCarry().get(1);
        account.setPlayer(new OnlinePlayer(account,2,2,this.getInput()));
        if(turn==1){
            Game.setSecondAccount(Game.getAccount(0));
            Game.setFirstAccount(account);
        }else{
            Game.setSecondAccount(account);
        }
        Battle.getMenu().setMediator(new OnlineBattleMediator());
    }
    public void close(){
        try {
            this.getSocket().close();
        } catch (Exception e) {
            System.err.println("handeld error--------------------");
            e.printStackTrace();
        }
    }

}
