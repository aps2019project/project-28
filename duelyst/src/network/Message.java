package network;

import Controller.menu.Menu;
import Controller.menu.SignInMenu;

import java.io.Serializable;
import java.util.ArrayList;

public class Message   {
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
        try {
            this.menu = menu.getName();
        }catch (NullPointerException e){
            this.menu = SignInMenu.getMenu().getName();
        }
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
}
