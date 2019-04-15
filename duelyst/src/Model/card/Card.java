package Model.card;

import Model.account.Collection;

import java.util.ArrayList;

public class Card {
    private static ArrayList<Card> cards=new ArrayList<>();

    protected Collection superCollection;
    private int cardID;
    private String name;
    private int price;
    private int manaPoint;

    public Card(int cardID, String name, int price, int manaPoint) {
        this.cardID = cardID;
        this.name = name;
        this.price = price;
        this.manaPoint = manaPoint;
    }

    public static Card getCard(int cardID) {
        for (Card card : Card.cards) {
            if(card.getCardID()==cardID)return card;
        }
        return null;
    }
    public static Card getCard(String name) {
        for (Card card : Card.cards) {
            if(card.getName().equals(name))return card;
        }
        return null;
    }

    public static boolean hasCard(int cardID){
        return Card.getCard(cardID)!=null;
    }
    public static boolean hasCard(String name){
        return Card.getCard(name)!=null;
    }


    public int getCardID() {
        return cardID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getManaPoint() {
        return manaPoint;
    }

    public void setManaPoint(int manaPoint) {
        this.manaPoint = manaPoint;
    }
}

