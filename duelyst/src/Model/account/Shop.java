package Model.account;

import Model.Primary;
import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.hermione.Hero;
import Model.card.hermione.Minion;
import Model.card.spell.Spell;
import Model.item.Item;
import Model.item.Usable;
import Model.mediator.OfflineShopMediator;
import Model.mediator.ShopMediator;
import exeption.*;

import java.io.FileNotFoundException;

public class Shop {

    private static Shop ourInstance = new Shop();

    static {
        try {
            ourInstance = Primary.getShop();
        } catch (FileNotFoundException e) { e.printStackTrace(); }
    }
//    private Collection collection = new Collection();

    private ShopMediator shopMediator;

//    public Shop(){
//        this.setShopMediator(new OfflineShopMediator());
//    }

    public static Shop getInstance() {
        if(ourInstance==null)ourInstance=new Shop();
        return ourInstance;
    }



    public boolean hasCard(String name){
        try {
            return this.shopMediator.hasCard(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean hasItem(String name){
        try {
            return this.shopMediator.hasItem(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public Card getCard(String name) throws InvalidCardException {
        try {
            return this.shopMediator.getCard(name);
        }catch (InvalidCardException e){throw e;}
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Usable getItem(String name) throws InvalidItemException {
        try {
            return this.shopMediator.getItem(name);
        } catch (InvalidItemException e) {throw e;}
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean buy(String name){
        System.err.println("debug");
        try {
            return this.shopMediator.buy(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public void sell(String name) throws InvalidCardException, InvalidItemException {
        try {
            this.shopMediator.sell(name);
        }catch (InvalidCardException |InvalidItemException e){throw e;}
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    public Collection getCollection() {
        try {
            return this.shopMediator.getCollection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void search(String name) throws InvalidCardException, InvalidItemException {
        this.shopMediator.search(name);
    }

    public void setShopMediator(ShopMediator shopMediator) {
        this.shopMediator = shopMediator;
        this.shopMediator.init();
    }

    public int getRemain(String name) {
        return this.shopMediator.getRemain(name);
    }

    public void addCard(Card card) {
        this.shopMediator.addCard(card) ;
    }
}