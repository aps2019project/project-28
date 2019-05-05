package Controller.menu;

public class CollectableMenu extends Menu{

    public CollectableMenu(Menu parentMenu) {
        super(parentMenu);
        this.account=parentMenu.getAccount();
    }

    @Override
    public void help() {

    }
}
