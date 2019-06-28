package Controller.menu.Graphics.FXMLController;

import Controller.menu.Graphics.GraphicsControls;
import Model.card.hermione.*;
import Model.card.spell.SpecialPower;
import Model.card.spell.SpecialPowerActions.SPActionAP;
import Model.card.spell.Spell;
import Model.card.spell.Targets.TargetEnemyHero;
import exeption.CardExistException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CraftingMinionFXMLC extends FXMLController {

    @FXML
    private Button backButton , craft , reset ;
    @FXML
    private VBox vbox ;
    @FXML
    private TextField name , ap , hp , range , cost , manapoint ;
    @FXML
    private ChoiceBox<Spell> specialPower ;
    @FXML
    private ChoiceBox<AttackType> attackType ;
    @FXML
    private ChoiceBox<SPATime> spa ;


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

    private void craft(){
        for (Node node : vbox.getChildren()){
            if (node instanceof TextField){
                ((TextField)node).setText("");
            }
            if (node instanceof HBox){
                for (Node n : ((HBox) node).getChildren()){
                    if (n instanceof TextField && ((TextField) n).getText().isEmpty()){
                        n.getStyleClass().add("text-box-wrong");
                        return;
                    }
                }
            }
        }
        try {
            SpecialPower sp = new SpecialPower("sample" , 0 , 2 , 1 , 1 ,  "" , TargetEnemyHero.getTargetInstance(),
                    SPActionAP.getSpecialPower());
            Minion minion = new Minion(name.getText(), Integer.parseInt(cost.getText()),Integer.parseInt(manapoint.getText()) ,Integer.parseInt(hp.getText()) , Integer.parseInt(ap.getText()),
                    new Melee() , Integer.parseInt(range.getText()) , sp , SPATime.DEATH , "Custom Hero");
            menu.getAccount().getCollection().addCardToCollection(minion);
        } catch (CardExistException e) {
            Popup.popup("sorry but a card with this name already exists in your Collection");
        }catch (Exception e){
            Popup.popup("Invalid Information !!!");
        }
    }


}
