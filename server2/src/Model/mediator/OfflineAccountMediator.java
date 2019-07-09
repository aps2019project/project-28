package Model.mediator;

import Model.Primary;
import Model.account.Account;
import com.gilecode.yagson.YaGson;
import exeption.AccountAlreadyExistsException;
import exeption.InvalidAccountException;
import exeption.WrongPassException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class OfflineAccountMediator implements AccountMediator{


    @Override
    public boolean addNewAccount(Account account) throws AccountAlreadyExistsException {
        if (account == null) return false;

        if (Account.hasAccount(account.getUsername()))
            throw new AccountAlreadyExistsException();
        Account.getAccounts().add(account);
        YaGson gson = new YaGson();
        try {
            FileWriter fileWriter = new FileWriter("Account.json", true);
            gson.toJson(account, fileWriter);
            fileWriter.write("\n");
            fileWriter.close();
        } catch (IOException e) {
        }
        return true;
    }

    @Override
    public void save() {
        YaGson gson = new YaGson();
        File file = new File("Account.json");
        file.delete();
        for (Account account:
                Primary.accounts) {
            try{
                FileWriter fileWriter = new FileWriter("Account.json", true);
                account.setPlayer(null);
                gson.toJson(account, fileWriter);
                fileWriter.write("\n");
                fileWriter.close();
            } catch (IOException ignored) {}
        }
    }

    @Override
    public Account getAccount(String username) throws InvalidAccountException {
        for (Account account : Account.getAccounts()) {
            if (account.getUsername().equals(username)) return account;
        }
        throw new InvalidAccountException();
    }

    @Override
    public Account getAccount(int ID) throws InvalidAccountException {
        for (Account account : Account.getAccounts()) {
            if (account.getID() == ID) return account;
        }
        throw new InvalidAccountException();
    }

    @Override
    public ArrayList<Account> getAccounts()  {
        return Primary.accounts;
    }

}
