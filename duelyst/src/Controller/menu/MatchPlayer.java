package Controller.menu;

import Model.account.Command;
import Model.account.Match;
import View.ConsoleView;
import View.MenuHandler;

public class MatchPlayer extends ConsoleView {
    private Match match;
    private Battle battle;

    public MatchPlayer(Match match, Battle battle) {
        this.match = match;
        this.battle = battle;
    }

    @Override
    public void play(String... args) {
        Battle.newGame(match.getGameMode());
        MenuHandler.showMenu();
        Command command;
        long previousTime=0;
        while((command=match.getNextCommand())!=null){
                                                                                                                         try {
            Thread.sleep(command.getTime()-previousTime);
                                                                                                                         } catch (InterruptedException ignored) { }
            this.consoleOutput.handleCommand(command.getCommand());
            previousTime=command.getTime();

            MenuHandler.showMenu();
            MenuHandler.nextMove();

        }
    }
}
