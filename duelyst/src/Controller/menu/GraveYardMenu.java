package Controller.menu;

import Model.card.Card;
import Model.card.OnCardDetailsPresentedListener;
import exeption.InvalidCardException;

public class GraveYardMenu extends Menu {

    public GraveYardMenu(Menu parentMenu) {
        super(parentMenu);
        this.account = parentMenu.getAccount();
        this.account = parentMenu.getAccount();
    }

    void showCardInfo(int cardID) throws InvalidCardException {
        Card card=this.account.getPlayer().getDeck().getCard(cardID);
        for (OnCardDetailsPresentedListener presenter : card.getCardDetailsPresenters()) {
            presenter.showCardDetail(card);
        }
    }

    void showCards(){
        for (Card card : this.account.getPlayer().getDeck().getGraveYard()) {
            for (OnCardDetailsPresentedListener presenter : card.getCardDetailsPresenters()) {
                presenter.showCardDetail(card);
            }
        }
    }

    @Override
    public void help() {

    }
}
