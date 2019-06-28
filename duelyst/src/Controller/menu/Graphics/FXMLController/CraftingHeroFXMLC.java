package Controller.menu.Graphics.FXMLController;

import Controller.menu.CraftingMenu;
import Controller.menu.Graphics.GraphicsControls;
import Model.account.Shop;
import Model.card.hermione.*;
import Model.card.spell.SpecialPower;
import Model.card.spell.Spell;
import Model.card.spell.SpellAction.Action;
import Model.card.spell.Target;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ChoiceBoxListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.concurrent.atomic.AtomicReference;

public class CraftingHeroFXMLC extends FXMLController {

    @FXML
    private Button backButton , craft , reset ;
    @FXML
    private VBox vbox ;
    @FXML
    private TextField name , ap , hp , range , cooldown , cost , manapoint ;
    @FXML
    private ChoiceBox<Spell> specialPower ;
    @FXML
    private ChoiceBox<AttackType> attackType ;


    @Override
    public void buildScene() {
        super.buildScene();
        Scene scene = menu.getGraphic().getScene();

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/CraftingMenu.css");

        GraphicsControls.setBackButtonOnPress(backButton);
        GraphicsControls.setButtonStyle("shopping-button" , craft , reset);

        setUpChoiceboxes();

        reset.setOnAction(e -> {
            for (Node node : vbox.getChildren()){
                if (node instanceof TextField){
                    ((TextField)node).setText("");
                }
                if (node instanceof HBox){
                    for (Node n : ((HBox) node).getChildren()){
                        if (n instanceof TextField){
                            ((TextField)n).setText("");
                        }
                    }
                }
            }
        });

    }

    private void setUpChoiceboxes() {

    }

    private void hero() {

    }

    private void minion() {

    }

    private void spell() {

    }


}
