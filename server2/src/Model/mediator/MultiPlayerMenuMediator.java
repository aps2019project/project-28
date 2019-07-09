package Model.mediator;

import exeption.InvalidAccountException;
import exeption.WrongPassException;

import java.io.IOException;

public interface MultiPlayerMenuMediator {
    void selectUser(String username, String password) throws InvalidAccountException, WrongPassException, IOException;
}
