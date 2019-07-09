package Controller.menu.Graphics.FXMLController;

import Controller.menu.Graphics.GraphicsControls;
import Model.Primary;
import Model.card.hermione.AttackType;
import Model.card.hermione.SPATime;
import Model.card.spell.Buff.BuffActions.BuffActionAP;
import Model.card.spell.Spell;
import Model.card.spell.SpellAction.*;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CraftingSpellFXMLC extends FXMLController {

    @FXML
    private Button backButton , craft , reset ;
    @FXML
    private VBox vbox ;
    @FXML
    private TextField name , perk , cost , manapoint , duration ;
    @FXML
    private ChoiceBox<String> target , action ;

    private Action[] actions ;
    private Target[] targets ;
    private String[] actionNames , targetNames ;
    private List<String> actionList = new ArrayList<>() , targetList = new ArrayList<>();

    @Override
    public void buildScene() {
        super.buildScene();
        Scene scene = menu.getGraphic().getScene();

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/CraftingMenu.css");

        GraphicsControls.setBackButtonOnPress(backButton);
        GraphicsControls.setButtonStyle("shopping-button" , craft , reset);

        setUpChoiceboxes();

        reset.setOnAction(e -> resetbuttonClicekd());
        craft.setOnAction(e -> craft());

    }

    private void craft() {
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

        Action act = actions[actionList.indexOf(action.getValue())]  ;
        Target targ = targets[targetList.indexOf(target.getValue())] ;
        System.err.println("new Spell : " + act + " , " + targ);
        System.err.println(action.getValue() + " , " + target.getValue());
        try {
            Spell spell = new Spell(name.getText(),  Integer.parseInt(cost.getText()) , Integer.parseInt(manapoint.getText()) ,
                    Integer.parseInt(duration.getText()) ,Integer.parseInt(perk.getText()) , "Custom Spell" ,
                    targ , act) ;
            Primary.saveCustomSpell(spell);
            menu.exit();
        } catch (IOException e) {
            System.err.println("SaveCustomSpell has IOException some problems !");
            e.printStackTrace();
            Popup.popup("Technical difficulties !");
        }
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
        String[] targetNamess = {
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
        Target[] targetss = {
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
        String[] actionNamess = {
                "Dispel" ,
                "PowerBuff AP" ,
                "PowerBuff HP" ,
                "Change AP" ,
                "Change HP" ,
                "Holly BUff" ,
                "Make Poison Cell" ,
                "Make Fire Cell" ,
                "Make Holly Cell" ,
                "Disarm" ,
                "Stun"
        };
        Action[] actionss = {
                ActionDispel.getAction(),
                ActionChangeAPBuff.getAction(),
                ActionChangeHPBuff.getAction(),
                ActionChangeAP.getAction(),
                ActionChangeHP.getAction(),
                ActionDeployHollyBuff.getAction(),
                ActionDeployPoison.getAction() ,
                ActionApplyFirecell.getAction() ,
                ActionHollyCell.getAction() ,
                ActionDisarm.getAction() ,
                ActionDisarm.getAction() ,
                ActionStun.getAction()
        };

        actions = actionss ;
        targets = targetss ;
        targetNames = targetNamess ;
        actionNames = actionNamess ;

        Collections.addAll(actionList , actionNames);
        Collections.addAll(targetList , targetNames);

        target.getItems().addAll(targetNames);
        action.getItems().addAll(actionNames);
        target.setValue(target.getItems().get(0));
        action.setValue(action.getItems().get(0));

    }



}
