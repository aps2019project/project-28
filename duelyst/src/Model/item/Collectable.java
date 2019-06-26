package Model.item;

import Model.Graphics.ItemGraphics;
import Model.card.spell.Target;
import Model.item.ItemActions.ItemAction;

public class Collectable extends Item {
    private ItemGraphics itemGraphics = new ItemGraphics();
    public Collectable(String name, int duration, int perk, String info, Target target, ItemAction... itemActions){
        super(name, duration, perk, info, target, itemActions);
    }

    public ItemGraphics getItemGraphics() {
        return itemGraphics;
    }
}
