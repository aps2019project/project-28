package Controller.menu;

public class MainMenu extends Menu {
    public MainMenu(Menu parentMenu) {
        super(parentMenu);
        this.account=parentMenu.getAccount();
    }

    @Override
    public void help() {

    }
}
