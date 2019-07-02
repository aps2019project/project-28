package network;

import java.util.ArrayList;

public class Message {
    private String text;
    private ArrayList<Object> carry=new ArrayList<>();

    public Message(String text) {
        this.text = text;
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
}
