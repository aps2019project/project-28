package Controller.menu;

public class GraveYardMenu extends Menu {
    public GraveYardMenu(Menu parentMenu) {
        super(parentMenu);
        this.account=parentMenu.getAccount();
    }

    @Override
    public void help() {

    }

    // TODO: 5/5/19 grave Yard
    
    public void showInfo(int cardID){
    }

}
