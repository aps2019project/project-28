package Model.account.player;

import View.CommandHandler;

public class CGI implements GameInterFace {
    private Player player;
    private CommandHandler commandHandler=new CommandHandler();



    @Override
    public void intervene() {
        System.err.println("debug");
        this.player.doYourMove();
        while(!this.player.getOutputStream().hasNextLine()){}

        commandHandler.handleCommand(this.player.getOutputStream().nextLine());
    }

    @Override
    public void setPlayer(Player player) {
        this.player= player;
    }
}
