package network;

import Model.account.Account;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatMSG {
    private String date;
    private String text;
    private Account account;

    public ChatMSG(String text, Account account) {
        this.text = text;
        this.account = account;

        Date date=new Date();
        String strDateFormat = "hh:mm:ss a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        this.date= dateFormat.format(date);
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public Account getAccount() {
        return account;
    }
}
