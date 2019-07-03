package network;

import Model.account.Account;

public class Auth {
    private String username;
    private int key;

    public Auth(String username, int key) {
        this.username = username;
        this.key = key;
    }

    public static Auth generateAuth(Account account){
        int i=0;
        for (Account acc : Account.getAccounts()) {
            if(acc.getUsername().equals(account.getUsername()))break;
            i++;
        }
        StringBuilder toBeHashed=new StringBuilder();
        int key= toBeHashed.append(i).append(account.getUsername()).toString().hashCode();
        return new Auth(account.getUsername(),key);
    }
    public boolean authenticate(){
        int i=0;
        for (Account account : Account.getAccounts()) {
            if(account.getUsername().equals(this.getUsername()))break;
            i++;
        }
        return (i + this.getUsername()).hashCode()==this.getKey();
    }



    public int getKey() {
        return key;
    }
    public void setKey(int key) {
        this.key = key;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

}
