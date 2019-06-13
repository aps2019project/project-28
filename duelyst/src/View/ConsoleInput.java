package View;



import java.util.Scanner;

public class ConsoleInput implements Input {
    protected ConsoleOutput consoleOutput =new ConsoleOutput();

    @Override
    public void start(String... args) {
        Scanner commands= MenuHandler.getGameScanner();
        MenuHandler.showMenu();
        String command;
        while(commands.hasNext()){
            command = commands.nextLine().toLowerCase().trim();

            consoleOutput.handleCommand(command);

            MenuHandler.showMenu();
            MenuHandler.nextMove();

            commands=MenuHandler.getGameScanner();
        }
    }
}
