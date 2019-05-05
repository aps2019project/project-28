package Controller.menu;

import java.util.ArrayList;

public class GraveYardMenu extends Menu {

    private ArrayList<OnGraveYardInfoClickedListener> graveYardInfoClickedListeners;
    private ArrayList<OnGraveYardCardInfoClickedListener> graveYardCardInfoClickedListeners;

    public GraveYardMenu(Menu parentMenu) {
        super(parentMenu);
        this.account = parentMenu.getAccount();
    }

    void showInfo(int cardID){
        for (OnGraveYardCardInfoClickedListener presenter:
                graveYardCardInfoClickedListeners) {
            presenter.show(account.getCollection(), cardID);
        }
    }

    void showCards(){
        for (OnGraveYardInfoClickedListener presenter:
                graveYardInfoClickedListeners) {
            presenter.Show(account.getCollection());
        }
    }

    @Override
    public void help() {

    }

}
