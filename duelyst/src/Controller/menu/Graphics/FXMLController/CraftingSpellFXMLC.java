package Controller.menu.Graphics.FXMLController;

import Controller.menu.Graphics.GraphicsControls;
import Model.card.hermione.AttackType;
import Model.card.hermione.SPATime;
import Model.card.spell.Spell;
import Model.card.spell.SpellAction.Action;
import Model.card.spell.Target;
import Model.card.spell.Targets.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CraftingSpellFXMLC extends FXMLController {

    @FXML
    private Button backButton , craft , reset ;
    @FXML
    private VBox vbox ;
    @FXML
    private TextField name , perk , cost , manapoint ;
    @FXML
    private ChoiceBox<String> target , action ;


    @Override
    public void buildScene() {
        super.buildScene();
        Scene scene = menu.getGraphic().getScene();

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/CraftingMenu.css");

        GraphicsControls.setBackButtonOnPress(backButton);
        GraphicsControls.setButtonStyle("shopping-button" , craft , reset);

        setUpChoiceboxes();

        reset.setOnAction(e -> resetbuttonClicekd());

    }

    private void resetbuttonClicekd() {
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

    private void setUpChoiceboxes() {
        String[] targetNames = {
                "All Own cards",
                "All Enemies",
                "Own Hero",
                "Enemy Hero",
                "Own Minion",
                "Enemy Minion",
                "Random Own",
                "Random Enemy",
                "Own Hero Row" ,
                "Enemy Hero Column",
                "3 Random Enemies",
        };
        Target[] targets = {
                TargetAllOwnCards.getTargetInstance(),
                TargetAllEnemyCards.getTargetInstance(),
                TargetOwnHero.getTargetInstance(),
                TargetEnemyHero.getTargetInstance(),
                TargetOwnMinion.getTargetInstance(),
                TargetEnemyMinion.getTargetInstance(),
                TargetRandomOwn.getTargetInstance(),
                TargetRandomEnemy.getTargetInstance(),
                TargetOwnHeroRow.getTargetInstance(),
                TargetEnemyHeroColumn.getTargetInstance(),
                Target3RandomEnemy.getTargetInstance()
        };
        String[] actionNames = {
                "Dispel" ,
                "PowerBuff AP" ,
                "PO"
        };
    }



}
