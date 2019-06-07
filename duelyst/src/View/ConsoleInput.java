package View;



import java.util.Scanner;

public class ConsoleInput {
    protected ConsoleOutput consoleOutput =new ConsoleOutput();

    public void play(){
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
