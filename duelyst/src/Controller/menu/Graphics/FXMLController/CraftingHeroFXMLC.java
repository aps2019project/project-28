package Controller.menu.Graphics.FXMLController;

import Model.Primary;
import Model.account.Shop;
import Model.card.Card;
import Model.card.hermione.*;
import Model.card.spell.Spell;
import exeption.CardExistException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CraftingHeroFXMLC extends CraftingHermioneFXMLC {

    @FXML
    private TextField cooldown ;


    protected void craft() {
        super.craft();
        try {
            Spell sp = (Spell) Card.getCard(specialPower.getValue());
            Hero hero = new Hero(name.getText(), Integer.parseInt(cost.getText()), Integer.parseInt(hp.getText()), Integer.parseInt(ap.getText()),
                    new Melee(), Integer.parseInt(range.getText()), sp, 0, Integer.parseInt(cooldown.getText()), "Custom Hero");
            Primary.saveCustomHermione(hero);
            menu.exit();
        } catch (CardExistException e) {
            Popup.popup("sorry but a card with this name already exists in the Shop");
        } catch (Exception e) {
            Popup.popup("Invalid Information !!!");
            e.printStackTrace();
        }
    }

}