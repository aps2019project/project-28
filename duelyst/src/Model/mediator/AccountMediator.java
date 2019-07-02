package Model.mediator;

import Model.account.Account;
import exeption.AccountAlreadyExistsException;
import exeption.InvalidAccountException;

import java.util.ArrayList;

public interface AccountMediator {
     boolean addNewAccount(Account account) throws Exception;
     void save() throws Exception;
     Account getAccount(String username) throws Exception;
     Account getAccount(int ID) throws Exception;
     ArrayList<Account> getAccounts() throws Exception;
}
