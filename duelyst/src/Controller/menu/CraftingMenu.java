package Controller.menu;

import Model.account.Shop;
import Model.card.Card;
import Model.card.hermione.*;
import Model.card.spell.SpecialPower;
import exeption.*;

public class CraftingMenu extends Menu {

    private static CraftingMenu menu;
    private static CraftingMenu heroCraftMenu;
    private static CraftingMenu minionCraftMenu;
    private static CraftingMenu spellCraftMenu;


    private CraftingMenu(String name) {
        super(name);
    }

    public static CraftingMenu getMenu() {
        if (CraftingMenu.menu == null) {
            CraftingMenu.menu = new CraftingMenu("CraftingMenu");
        }
        return menu;
    }
    public static CraftingMenu getHeroMenu() {
        if (CraftingMenu.heroCraftMenu == null) {
            CraftingMenu.heroCraftMenu = new CraftingMenu("HeroCraftMenu");
        }
        return heroCraftMenu;
    }
    public static CraftingMenu getMinionMenu() {
        if (CraftingMenu.minionCraftMenu == null) {
            CraftingMenu.minionCraftMenu = new CraftingMenu("MinionCraftMenu");
        }
        return minionCraftMenu;
    }
    public static CraftingMenu getSpellMenu() {
        if (CraftingMenu.spellCraftMenu == null) {
            CraftingMenu.spellCraftMenu = new CraftingMenu("SpellCraftMenu");
        }
        return spellCraftMenu;
    }


    public void craftHero(String name, int HP, int AP, AttackType attackType, int range, SpecialPower SP,int manaPoint,int coolDown,String info){




        int price=getPriceForHero(HP, AP, attackType, range, SP, manaPoint, coolDown);
        Hero hero = new Hero(name,price,HP,AP,attackType,range,SP,manaPoint,coolDown,info);

        Card card=hero;
        if(card.getPrice()<this.account.getMoney()){
            //// TODO: 7/1/19 change the ID system for cards
            // TODO: 7/1/19 add another arraylist : crafted card to primery and stuff
//            this.account.getCollection().addCardToCollection(card);
        }
    }

    private int getPriceForHero(int HP, int AP, AttackType attackType, int range, SpecialPower SP, int manaPoint, int coolDown) {
        int price =AP*100+
                HP*200+
                ((attackType instanceof Hybrid)?2000:((attackType instanceof Range)?1000:500))+
                range*50+
                SP.getActions().size()*100+
                (-1)*manaPoint*50+
                (-1)*coolDown*50;
        return price;
    }

    public void addToShop(Card h) throws CardExistException {
        Shop.getInstance().getCollection().addCardToCollection(h);
    }


}
