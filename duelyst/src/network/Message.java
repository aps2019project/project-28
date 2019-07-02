package network;

public class Message {
    private String text;
    private Object[] carry;

    public Message(String text) {
        this.text = text;
    }

    public Message(String text, Object... carry) {
        this.text = text;
        this.carry = carry;
    }

    public String getText() {
        return text;
    }

    public Object[] getCarry() {
        return carry;
    }
}
