package Controller.menu;

import Model.card.Card;
import Model.card.OnCardDetailsPresentedListener;
import exeption.InvalidCardException;

public class GraveYardMenu extends Menu {

    private static GraveYardMenu menu;

    private GraveYardMenu(String name) {
        super(name);
    }

    public static GraveYardMenu getMenu(){
        if(GraveYardMenu.menu==null){
            GraveYardMenu.menu=new GraveYardMenu("GraveYardMenu");
        }
        return menu;
    }

    public void showCardInfo(int cardID) throws InvalidCardException {
        Card card = this.account.getPlayer().getDeck().getCard(cardID);
        for (OnCardDetailsPresentedListener presenter : card.getCardDetailsPresenters()) {
            presenter.showCardDetail(card);
        }
    }

    public void showCards() {
        for (Card card : this.account.getPlayer().getDeck().getGraveYard()) {
            for (OnCardDetailsPresentedListener presenter : card.getCardDetailsPresenters()) {
                presenter.showCardDetail(card);
            }
        }
    }

    @Override
    public void help() {
        super.help();
        System.out.println("4)Show info [card id]     5)Show cards     ");

    }
}
