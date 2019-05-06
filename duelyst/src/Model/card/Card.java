package Model.card;

import Model.account.Collection;
import exeption.InvalidCardException;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Card {

    private static ArrayList<Card> cards=new ArrayList<>();
    public static int uniqueID =0;

    protected Collection superCollection;
    private int cardID;
    private String name;
    private int price;
    private int manaPoint;
    private ArrayList<OnCardDetailsPresentedListener> cardDetailsPresenters=new ArrayList<>();

    public Card( String name, int price, int manaPoint) {
        this.cardID = uniqueID++;
        this.name = name;
        this.price = price;
        this.manaPoint = manaPoint;
    }

    public void setSuperCollection(Collection superCollection) {
        this.superCollection = superCollection;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setManaPoint(int manaPoint) {
        this.manaPoint = manaPoint;
    }

    public static ArrayList<Card> getCards() {
        return cards;
    }

    public Collection getSuperCollection() {
        return superCollection;
    }

    public static Card getCard(int cardID) throws InvalidCardException {
        for (Card card : Card.cards) {
            if(card.getCardID()==cardID)return card;
        }
        throw new InvalidCardException();
    }
    public static Card getCard(String name) throws InvalidCardException {
        for (Card card : Card.cards) {
            if(card.getName().equals(name))return card;
        }
        throw new InvalidCardException();
    }

    public static boolean hasCard(int cardID){
        try {
            Card.getCard(cardID);
            return true;
        } catch (InvalidCardException e) {
            return false;
        }

    }
    public static boolean hasCard(String name){
        try {
            Card.getCard(name);
            return true;
        } catch (InvalidCardException e) {
            return false;
        }
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
    
    public void addOnCardDetailPresented(OnCardDetailsPresentedListener presenter){
        this.cardDetailsPresenters.add(presenter);
    }

    public ArrayList<OnCardDetailsPresentedListener> getCardDetailsPresenters() {
        return (ArrayList<OnCardDetailsPresentedListener>) Collections.unmodifiableList(cardDetailsPresenters);
    }
}

