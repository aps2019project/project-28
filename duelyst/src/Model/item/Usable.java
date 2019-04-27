package Model.item;

import java.util.ArrayList;

public class Usable extends Item{

    private static ArrayList<Usable> usables = new ArrayList<>();

    private int price;

    public Usable(String name, String effect, int itemID, int price){
        super(name, effect, itemID);
        this.price = price;
        usables.add(this);
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public static ArrayList<Usable> getUsables() {
        return usables;
    }


    public void deploy(){

    }

}
