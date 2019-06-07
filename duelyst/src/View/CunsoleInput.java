package View;


import java.util.Scanner;

public class CunsoleInput {
    private CunsoleOutput cunsoleOutput=new CunsoleOutput();

    public void play(){
        Scanner commands= MenuHandler.getGameScanner();
        MenuHandler.showMenu();
        String command;
        while(commands.hasNext()){
            System.err.println("for now insert the coordinations 0 base");
            command = commands.nextLine().toLowerCase().trim();

            cunsoleOutput.handleCommand(command);

            MenuHandler.showMenu();
            MenuHandler.nextMove();


            commands=MenuHandler.getGameScanner();

//            System.err.println(Game.accounts[Battle.getMenu().getTurn()].getName());
//            System.err.println(Game.accounts[0].getName()+" , "+Game.accounts[1].getName());
        }
    }

}
