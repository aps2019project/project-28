package Controller.menu.Graphics.FXMLController;

import Model.card.Card;
import Model.card.hermione.*;
import Model.card.spell.Spell;
import exeption.CardExistException;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class CraftingMinionFXMLC extends CraftingHermioneFXMLC {

    @FXML
    private ChoiceBox<SPAType> spa ;
    @FXML
    private TextField manapoint ;

    @Override
    public void buildScene() {
        super.buildScene();
        spa.getItems().addAll(SPAType.None , SPAType.Attack , SPAType.Defend , SPAType.OnTurn , SPAType.Spawn , SPAType.Death , SPAType.Combo , SPAType.Passive);
        spa.setValue(SPAType.None);
    }


    protected void craft(){
        super.craft();
        try {
            Spell sp = (Spell) Card.getCard(specialPower.getValue());
            Minion minion = new Minion(name.getText(), Integer.parseInt(cost.getText()),Integer.parseInt(manapoint.getText()) ,Integer.parseInt(hp.getText()) , Integer.parseInt(ap.getText()),
                    new Melee() , Integer.parseInt(range.getText()) , sp , SPATime.DEATH , "Custom Minion");
            Card.addCardToCards(minion);
        } catch (CardExistException e) {
            Popup.popup("sorry but a card with this name already exists in your Collection");
        }catch (Exception e){
            Popup.popup("Invalid Information !!!");
        }
    }


}


enum SPAType {
    None(SPATime.NULL),
    Attack(SPATime.ATTACK),
    Passive(SPATime.PASSIVE),
    Death(SPATime.DEATH),
    Defend(SPATime.DEFEND),
    Spawn(SPATime.SPAWN) ,
    Combo(SPATime.COMBO),
    OnTurn(SPATime.ON_TURN);

    private SPATime spaTime ;

    SPAType(SPATime spaTime) {
        this.spaTime = spaTime;
    }
}