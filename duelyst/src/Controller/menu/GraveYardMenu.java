package Controller.menu;

public class GraveYardMenu extends Menu {
    public GraveYardMenu(Menu parentMenu) {
        super(parentMenu);
        this.account=parentMenu.getAccount();
    }

    @Override
    public void help() {

    }
}
