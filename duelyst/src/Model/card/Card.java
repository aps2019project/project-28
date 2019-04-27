package Model.card;

import Model.account.Account;
import Model.account.Collection;

import java.util.ArrayList;

public abstract class Card {
    private static ArrayList<Card> cards=new ArrayList<>();

    protected Collection superCollection;
    private int cardID;
    private String name;
    private int price;
    private int manaPoint;
    private Account owner ;

    public Card(int cardID, String name, int price, int manaPoint , Account owner) {
        this.cardID = cardID;
        this.name = name;
        this.price = price;
        this.manaPoint = manaPoint;
        this.owner = owner ;
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

