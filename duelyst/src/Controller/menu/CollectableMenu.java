package Controller.menu;

public class CollectableMenu extends Menu {

    private static CollectableMenu menu;

//    @Override
//    protected void init() {
//        super.init();
//    }

    private CollectableMenu(String name) {
        super(name);
//        this.account = parentMenu.getAccount();
    }

    public static CollectableMenu getMenu(){
        if(CollectableMenu.menu==null){
            CollectableMenu.menu=new CollectableMenu("CollectableMenu");
        }
        return menu;
    }



}
