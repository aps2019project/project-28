package Controller.menu;

public class MainMenu extends Menu {
    public MainMenu(Menu parentMenu, String name) {
        super(parentMenu, name);
        this.account = parentMenu.getAccount();
    }

    @Override
    public void help() {

    }
}
