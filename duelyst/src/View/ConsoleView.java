package View;



import java.util.Scanner;

public class ConsoleView implements View{
    protected CommandHandler commandHandler;

    public ConsoleView(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
        commandHandler.setOutputStream(System.out);
    }

    @Override
    public void play(String... args) {
        Scanner commands= MenuHandler.getGameScanner();
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
}
