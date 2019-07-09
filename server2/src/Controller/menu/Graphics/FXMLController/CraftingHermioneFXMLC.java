package Controller.menu.Graphics.FXMLController;

import Controller.menu.Graphics.GraphicsControls;
import Model.card.Card;
import Model.card.hermione.*;
import Model.card.spell.Spell;
import exeption.CardExistException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CraftingHermioneFXMLC extends FXMLController {

    @FXML
    protected Button backButton , craft , reset ;
    @FXML
    protected VBox vbox ;
    @FXML
    protected TextField name , ap , hp , range , cost ;
    @FXML
    protected ChoiceBox<String> specialPower ;
    @FXML
    protected ChoiceBox<Attype> attackType ;


    @Override
    public void buildScene() {
        super.buildScene();
        Scene scene = menu.getGraphic().getScene();

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/CraftingMenu.css");

        GraphicsControls.setBackButtonOnPress(backButton);
        GraphicsControls.setButtonStyle("shopping-button" , craft , reset);

        setUpSpecialPowers();
        setUpAttackTypes();

        reset.setOnAction(e -> resetButtonClicked(vbox));

        craft.setOnAction(e -> craft());
    }

    protected void resetButtonClicked(VBox vbox) {
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
    }

    protected void craft() {
        for (Node node : vbox.getChildren()){
            if (node instanceof TextField){
                if (((TextField)node).getText().isEmpty()){
                    node.getStyleClass().add("text-box-wrong");
                    return;
                }
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
    }


    protected void setUpSpecialPowers() {
        for (Card card : Card.getCards()){//todo
            if (card instanceof Spell){
                specialPower.getItems().add(card.getName());
            }
        }
        specialPower.setValue(specialPower.getItems().get(0));
    }

    protected void setUpAttackTypes(){
        Attype[] k = {Attype.Melee , Attype.Ranged , Attype.Hybrid};
        attackType.getItems().addAll(k);
        attackType.setValue(Attype.Melee);
    }

}



enum Attype {
    Melee(new Melee()) ,
    Ranged(new Range()) ,
    Hybrid(new Hybrid()) ;

    private AttackType attackType ;

    Attype(AttackType attackType) {
        this.attackType = attackType;
    }

    public AttackType getAttackType() {
        return attackType;
    }
}
