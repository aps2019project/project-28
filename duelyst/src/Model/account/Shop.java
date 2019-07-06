package Model.account;

import Model.Primary;
import Model.card.hermione.Hero;
import Model.card.hermione.Minion;
import Model.card.spell.Spell;
import Model.item.Usable;
import exeption.CardExistException;
import exeption.ItemExistExeption;

public class Shop {

    private static Shop ourInstance = new Shop();
    private Collection collection = new Collection();

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

    public static Shop getInstance() {

        return ourInstance;
    }

    private Shop() {
        fillCollection();
    }

    public void setCollection(Collection save) {
        this.collection = save;
    }
}
