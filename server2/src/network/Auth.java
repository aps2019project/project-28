package network;

import Model.account.Account;

public class Auth {
    private String username;
    private int key;

    public Auth(String username, int key) {
        this.username = username;
        this.key = key;
    }


    public static Auth generateAuth(String username){
        int i=0;
        for (Account acc : Account.getAccounts()) {
            if(acc.getUsername().equals(username))break;
            i++;
        }
        StringBuilder toBeHashed=new StringBuilder();
        int key= toBeHashed.append(i).append(username).toString().hashCode();
        System.out.println("key = " + key);
        return new Auth(username,key);
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
