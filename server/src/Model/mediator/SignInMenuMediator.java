package Model.mediator;

import Model.account.Account;
import exeption.AccountAlreadyExistsException;
import exeption.InvalidAccountException;
import exeption.WrongPassException;

public interface SignInMenuMediator {
    void logIn(String username, String password) throws Exception;
    void logOut(Account account);
}
