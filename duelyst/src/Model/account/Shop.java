package Model.account;

public class Shop {
    private static Shop ourInstance = new Shop();

    private Collection collection=new Collection();

    public Collection getCollection() {
        return collection;
    }

    public static Shop getInstance() {

        return ourInstance;
    }

    private Shop() {
    }
}
