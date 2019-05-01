package Model.card;

import Model.account.Collection;

import java.util.ArrayList;

public abstract class Card {
    private static ArrayList<Card> cards=new ArrayList<>();
    private static long uniqueID =0;

    protected Collection superCollection;
    private long cardID;
    private String name;
    private int price;
    private int manaPoint;


    public Card( String name, int price, int manaPoint) {
        this.cardID = uniqueID++;
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




    public long getCardID() {
        return cardID;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getManaPoint() {
        return manaPoint;
    }

}

