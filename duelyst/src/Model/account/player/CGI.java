package Model.account.player;

import View.CommandHandler;

public class CGI implements GameInterFace {
    private Player player;
    private CommandHandler commandHandler=new CommandHandler();



    @Override
    public void intervene() {
        System.err.println("Console GI");
        this.player.doYourMove();
        while(!this.player.getOutputStream().hasNextLine()){}

        String command=this.player.getOutputStream().nextLine();
        System.err.println(command);
        commandHandler.handleCommand(command);
    }

    @Override
    public void setPlayer(Player player) {
        this.player= player;
    }
}
