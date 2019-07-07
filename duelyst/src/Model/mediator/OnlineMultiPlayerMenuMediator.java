package Model.mediator;

import Controller.Game;
import exeption.InvalidAccountException;
import exeption.WrongPassException;

import java.io.IOException;

public class OnlineMultiPlayerMenuMediator implements MultiPlayerMenuMediator {
    @Override
    public void selectUser(String username, String password) throws InvalidAccountException, WrongPassException, IOException {
        /*username and password are ignored*/
        Game.getBattleClient().connect();
    }
}
