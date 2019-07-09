package network;

import Controller.menu.Menu;

import java.io.Serializable;
import java.util.ArrayList;

public class Message {
    private String text;
    private ArrayList<Object> carry=new ArrayList<>();
    private Auth authToken=null;
    private String menu;

    public Message(String text, Auth authToken) {

        this.text = text;
        this.authToken=authToken;
    }

    public Message(String text) {
        this.text = text;
    }

    public Auth getAuthToken() {
        return authToken;
    }

    public void setAuth(Auth authToken) {
        this.authToken = authToken;
    }

    public void addCarry(Object carry) {
        this.carry.add(carry);
    }

    public String getText() {
        return text;
    }

    public ArrayList<Object> getCarry() {
        return carry;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu.getName();
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }
    public static Message getFailedMessage(){
        return new Message("failed!");
    }
    public static Message getDoneMessage(){
        return new Message("Done!");
    }

//    @Override
//    public String toString() {
//        StringBuilder s=new StringBuilder();
//        s.append("TEXT : "+this.text+
//                "\n KEY: "+this.getAuthToken());
//        this.carry.forEach(s::append);
//        return s.toString();
//
//    }
}
