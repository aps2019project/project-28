package Model.account;

import Model.Primary;
import Model.card.hermione.Hero;
import Model.card.hermione.Minion;
import Model.card.spell.Spell;
import Model.item.Usable;
import exeption.*;

import java.util.ArrayList;

public class Shop {

    private static Shop ourInstance = new Shop();
    private Collection collection = new Collection();


    public static Shop getInstance() {

        return ourInstance;
    }
    private Shop() {
        fillCollection();
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

    public void buy(String name,Account account) throws InvalidCardException, NotEnoughMoneyException {
        System.err.println(name);
        if (!this.getCollection().hasCard(name) && !this.getCollection().hasItem(name)) {
            throw new InvalidCardException();
        }
        if (this.getCollection().hasCard(name)) {
            if (this.getCollection().getCard(name).getPrice() > account.getMoney()) {
                System.err.println(this.getCollection().getCard(name).getPrice());
                System.err.println(account.getMoney());
                throw new NotEnoughMoneyException();
            }
            else {
                tempCollection.addCardToCollection(this.shop.getCollection().getCard(name));
                this.account.setMoney(this.account.getMoney() - this.shop.getCollection().getCard(name).getPrice());
            }

        }
        else if (this.shop.getCollection().hasItem(name)) {
            if (this.shop.getCollection().getItem(name).getPrice() > this.account.getMoney()) {
                throw new NotEnoughMoneyException();
            } else if (account.getCollection().getUsables().size() >= Collection.MAX_USABLES) {
                throw new FullCollectionException();
            } else {
                tempCollection.addItemToCollection((this.shop.getCollection().getItem(name)));
            }
        }
    }



















    public Collection getCollection() {
        return collection;
    }
    public void setCollection(Collection save) {
        this.collection = save;
    }
}
