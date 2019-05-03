package Model.item;

public class Usable extends Item {
    int price;
    public Usable(String name, int price, ItemAction effect) {
        super(name, effect);
        this.price = price;
    }
    //TODO:Item ID

    @Override
    public void deploy() {

    }
}
