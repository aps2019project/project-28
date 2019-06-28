package View;



import Controller.menu.Menu;

import java.util.Scanner;

public class ConsoleView implements View{
    protected CommandHandler commandHandler;


    @Override
    public void play(String... args) {
        Scanner commands= MenuHandler.getGameScanner();
        MenuHandler.startMenus();
        MenuHandler.showMenu();
        String command;
        while(commands.hasNext()){
            command = commands.nextLine().toLowerCase().trim();

            commandHandler.handleCommand(command);

            MenuHandler.showMenu();
            MenuHandler.nextMove();

            commands=MenuHandler.getGameScanner();
        }

    }

    @Override
    public void setCommandHandler(CommandHandler commandHandler) {
        this.commandHandler=commandHandler;
    }
}
