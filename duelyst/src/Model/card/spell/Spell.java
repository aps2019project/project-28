package Model.card.spell;
import Model.account.Player;
import Model.card.Card;

public class Spell extends Card {
    public Spell(int cardID, String name, int price, int manaPoint) {
        super(cardID, name, price, manaPoint);
    }
    public void deploy(Player player,Player enemy){

    }
}
