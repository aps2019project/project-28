package Controller.menu;

import java.util.ArrayList;

public class GraveYardMenu extends Menu {

    private ArrayList<OnGraveYardInfoClickedListener> graveYardInfoClickedListeners;
    private ArrayList<OnGraveYardCardInfoClickedListener> graveYardCardInfoClickedListeners;

    public GraveYardMenu(Menu parentMenu) {
        super(parentMenu);
        this.account = parentMenu.getAccount();
    }

    void showInfo(int cardid){
        for (OnGraveYardCardInfoClickedListener presenter:
             graveYardCardInfoClickedListeners) {
            presenter.show(account.getCollection(), cardid);
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

    // TODO: 5/5/19 grave Yard
    
    public void showInfo(int cardID){
    }

}
