package Model.card;

import Model.Graphics.CardGraphics;
import Model.Primary;
import Model.account.Collection;
import exeption.CardExistException;
import exeption.InvalidCardException;

import java.util.ArrayList;

public abstract class Card {
    private static ArrayList<Card> cards = Primary.cards;

    public static int uniqueID =0;

    protected Collection superCollection;
    private int cardID;
    private String name;
    private int price;
    private int manaPoint;
    private static ArrayList<OnCardDetailsPresentedListener> cardDetailsPresenters=new ArrayList<>();
    private String info;
    private CardGraphics cardGraphics;

    public Card( String name, int price, int manaPoint, String info) {
        this.cardID = uniqueID++;
        this.name = name;
        this.price = price;
        this.manaPoint = manaPoint;
        this.info = info;
        this.cardGraphics = new CardGraphics();
//        this.cardID = uniqueID++;
    }


    public static String gererateID(Card card){
        return card.getClass()+":";
    }


    public static String gererateID(Card card){
        return card.getClass()+":";
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
            if(card.getID()==cardID)return card;
        }
        throw new InvalidCardException();
    }
    public static Card getCard(String name) throws InvalidCardException {
        for (Card card : Card.cards) {
            if(card.getName().toLowerCase().equals(name.toLowerCase()))return card;
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


    public int getID() {
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

    public static void addOnCardDetailPresented(OnCardDetailsPresentedListener presenter){
        Card.cardDetailsPresenters.add(presenter);
    }

    public static ArrayList<OnCardDetailsPresentedListener> getCardDetailsPresenters() {
        return (cardDetailsPresenters);
    }

    public String getInfo() {
        return this.info;
    }
    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Card)) return false ;
        Card card = (Card)obj ;
        if (card.getName().equals(this.name)) return true ;
        return false ;
    }

    public CardGraphics getCardGraphics() {
        return cardGraphics;
    }

    public void setCardGraphics(CardGraphics cardGraphics) {
        this.cardGraphics = cardGraphics;
    }

    public static void addCardToCards(Card card) throws CardExistException {
        if (!cards.contains(card)) cards.add(card);
        else throw new CardExistException();
    }
}

