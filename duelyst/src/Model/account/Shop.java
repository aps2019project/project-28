package Model.account;

import Model.Primary;
import Model.card.Card;
import Model.card.hermione.Hero;
import Model.card.hermione.Minion;
import Model.card.spell.Spell;
import Model.item.Item;
import Model.item.Usable;
import exeption.*;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Shop {

    private static int INITIAL_AMOUNT=10;
    private static Shop ourInstance;
    {
        try {
            ourInstance = Primary.getShop();
        } catch (FileNotFoundException e) { e.printStackTrace(); }
    }
    private Collection collection = new Collection();

    Map<Integer,Integer>cards=new HashMap<>();//cardId,amount
    Map<Integer,Integer>items=new HashMap<>();//itemId,amount

    public static Shop getInstance() {
        return ourInstance;
    }

    public Shop() {
        try {
            fillCollection();
            fillCards();
            fillItems();
        }catch (Exception e){}
    }

    private void fillItems() {
        this.collection.getItems().forEach(item -> items.put(item.getID(),INITIAL_AMOUNT));
    }

    private void fillCards() {
        this.collection.getCards().forEach(card -> cards.put(card.getCardID(),INITIAL_AMOUNT));
    }

    public boolean hasCard(String name){
        if(!this.collection.hasCard(name))return false;
        try {
            if (this.cards.get(this.collection.getCard(name).getCardID())==0)return false;
        } catch (InvalidCardException e) {e.printStackTrace();}
        return true;
    }
    public boolean hasItem(String name){
        if(!this.collection.hasItem(name))return false;
        try {
            if(this.items.get(this.collection.getItem(name).getID())==0)return false;
        } catch (InvalidItemException e) {e.printStackTrace();}
        return true;
    }

    public Card getCard(String name) throws InvalidCardException {
        return this.collection.getCard(name);
    }

    public Usable getItem(String name) throws InvalidItemException {
        return this.collection.getItem(name);
    }

    private Card buyCard(String name) throws CardDeoesntExistException {
        if(!this.hasCard(name))throw new CardDeoesntExistException();
        try {
            Card card=this.getCard(name);
            this.cards.put(card.getCardID(),this.cards.get(card.getCardID())-1);
            return card;
        } catch (InvalidCardException e) { e.printStackTrace(); }
        return null;
    }

    private Item buyItem(String name) throws ItemDoesntExistException {
        if(!this.hasItem(name))throw new ItemDoesntExistException();
        try {
            Item item=this.getItem(name);
            this.items.put(item.getID(),this.items.get(item.getID())-1);
            return item;
        }catch (InvalidItemException e) { e.printStackTrace(); }
        return null;
    }

    public boolean buy(String name){
        boolean isSuccessful=false;

        try {
            buyCard(name);
            isSuccessful=true;
        } catch (CardDeoesntExistException e) { e.printStackTrace(); }
        try {
            buyItem(name);
            isSuccessful=true;
        } catch (ItemDoesntExistException e) { e.printStackTrace(); }

        return isSuccessful;
    }


    public void sell(String name) throws InvalidCardException, InvalidItemException {
        sellCard(name);
        sellItem(name);
    }

    private boolean sellCard(String name) throws InvalidCardException {
        Card card=this.collection.getCard(name);
        this.cards.put(card.getCardID(),this.cards.get(card.getCardID())+1);
        return true;
    }
    private boolean sellItem(String name) throws InvalidItemException {
        Item item=this.collection.getItem(name);
        this.items.put(item.getID(),this.items.get(item.getID())+1);
        return true;
    }



    private void fillCollection(){
        for (Hero hero: Primary.heroes) {
            try {
                collection.addCardToCollection(hero);
            } catch (CardExistException e) {
                e.printStackTrace();
            }
        }

        for (Minion minion: Primary.minions) {
            try {
                collection.addCardToCollection(minion);
            } catch (CardExistException e) {}
        }

        for (Usable item: Primary.usables) {
            try {
                collection.addItemToCollection(item);
            } catch (ItemExistExeption itemExistExeption) {
                itemExistExeption.printStackTrace();
            }
        }

        for (Spell spell:
             Primary.spells) {
            try {
                collection.addCardToCollection(spell);
            } catch (CardExistException e) {}
        }
    }

    public Collection getCollection() {
        return collection;
    }
    public void setCollection(Collection save) {
        this.collection = save;
    }

    public void search(String name) throws InvalidCardException, InvalidItemException {
        this.collection.search(name);
    }
}
