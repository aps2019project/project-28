package Model.account;

import Model.PreProcess;
import Model.card.hermione.Hero;
import Model.card.hermione.Minion;
import Model.card.spell.Spell;
import Model.item.Usable;
import exeption.CardExistException;
import exeption.ItemExistExeption;

public class Shop {
    private static Shop ourInstance = new Shop();

    private Collection collection = new Collection();
    {
        for (Hero hero:
                PreProcess.heroes) {
            try {
                collection.addCardToCollection(hero);
            } catch (CardExistException e) {}
        }

        for (Minion minion:
             PreProcess.minions) {
            try {
                collection.addCardToCollection(minion);
            } catch (CardExistException e) {}
        }

        for (Usable item:
             PreProcess.usables) {
            try {
                collection.addItemToCollection(item);
            } catch (ItemExistExeption itemExistExeption) {
                itemExistExeption.printStackTrace();
            }
        }

        for (Spell spell:
             PreProcess.spells) {
            try {
                collection.addCardToCollection(spell);
            } catch (CardExistException e) {}
        }
        collection.save();
    }

    public Collection getCollection() {
        return collection;
    }

    public static Shop getInstance() {

        return ourInstance;
    }

    private Shop() {
    }
}
