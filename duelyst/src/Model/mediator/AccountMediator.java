package Model.mediator;

import Model.account.Account;
import exeption.AccountAlreadyExistsException;
import exeption.InvalidAccountException;

import java.util.ArrayList;

public interface AccountMediator {
     void addNewAccount(Account account) throws AccountAlreadyExistsException;
     void save();
     Account getAccount(String username) throws InvalidAccountException;
     Account getAccount(int ID) throws InvalidAccountException;
     ArrayList<Account> getAccounts();
}
