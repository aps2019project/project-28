package Controller.menu.Graphics.FXMLController;

import Controller.menu.CraftingMenu;
import Controller.menu.Graphics.GraphicsControls;
import Model.account.Shop;
import Model.card.hermione.*;
import Model.card.spell.Spell;
import Model.card.spell.SpellAction.Action;
import Model.card.spell.Target;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.concurrent.atomic.AtomicReference;

public class CraftingMenuFXML extends FXMLController {

    @FXML
    private Button backButton , craft , reset ;
    @FXML
    private VBox vbox ;
    @FXML
    private Button heroT , minionT , spellT ;
    @Override
    public void buildScene() {
        super.buildScene();
        Scene scene = menu.getGraphic().getScene();

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/CraftingMenu.css");

        GraphicsControls.setBackButtonOnPress(backButton);
        GraphicsControls.setButtonStyle("shopping-button" , craft , reset);
        GraphicsControls.setButtonStyle("radio-button" , heroT , minionT , spellT);

        heroT.setOnAction(e -> hero());
        minionT.setOnAction(e -> minion());
        spellT.setOnAction(e -> spell());

        reset.setOnAction(e -> {
            for (Node node : vbox.getChildren()){
                if (node instanceof TextField){
                    ((TextField)node).setText("");
                }
            }
        });

    }

    private void minion() {
        vbox.getChildren().clear() ;
        TextField name = new TextField() , ap  = new TextField() , hp  = new TextField() , attackType  = new TextField() ,
                range  = new TextField() , specialPower  = new TextField() , specialPowerActivation  = new TextField() ,
                cost  = new TextField(), manapoint = new TextField();
        vbox.getChildren().addAll(name , ap , hp , attackType , range , specialPower , specialPowerActivation , cost , manapoint) ;
        for (Node node : vbox.getChildren()){
            if (node instanceof TextField){
                ((TextField)node).setPromptText(node.getId());
            }
        }
        craft.setOnAction(e -> {
            String at = attackType.getText();
            AttackType att ;
            if (at.equals("melee")) att = new Melee() ;
            else if (at.equals("ranged")) att = new Range();
            else if (at.equals("hybrid")) att = new Hybrid();
            else return ;

            at = specialPower.getText() ;
            Spell specialP ;
            try {
                specialP = (Spell) Shop.getInstance().getCollection().getCard(at);
            }catch(Exception exc){
                System.err.println("no such spell for special power !");
                return ;}

            at = specialPowerActivation.getText() ;
            SPATime spatime = null;
            for (SPATime s : SPATime.values()){
                if (s.name().equals(at)) spatime = s ;
            }
            if (spatime == null){
                System.err.println("invalid spatime");
                return ;
            }

            try{
                Minion minion = new Minion(name.getText() , Integer.parseInt(cost.getText()) , Integer.parseInt(manapoint.getText()) ,
                        Integer.parseInt(hp.getText()) , Integer.parseInt(ap.getText()) , att , Integer.parseInt(range.getText()) ,
                        specialP , spatime , "custom" );
                CraftingMenu.getMenu().addToShop(minion);
            }catch (Exception exc){
                System.err.println("invalid info to make a minion !");
            }
        });
    }

    private void spell() {
        vbox.getChildren().clear() ;
        TextField name = new TextField() , target  = new TextField() , action  = new TextField() ,
                cost  = new TextField(), manapoint = new TextField() , duration = new TextField() ,
                perk = new TextField() ;
        vbox.getChildren().addAll(name , target , action , cost , manapoint) ;
        for (Node node : vbox.getChildren()){
            if (node instanceof TextField){
                ((TextField)node).setPromptText(node.getId());
            }
        }

        System.out.println("\n\n\n");
        try {
            Spell.getSpellActions().forEach(x -> System.out.println(x.getClass()));
        }catch (NullPointerException ignored) {
        }
        AtomicReference<Action> actionnn = new AtomicReference<>();
        Spell.getSpellActions().forEach(x -> {
            if (x.getClass().toString().equals(action.getText())) actionnn.set(x);
        });

        AtomicReference<Target> targett = new AtomicReference<>();
        Spell.getTargets().forEach(x -> {
            if (x.getClass().toString().equals(action.getText())) targett.set(x);
        });

        /*try {
            Spell spell = new Spell(name.getText(), Integer.parseInt(cost.getText()), Integer.parseInt(manapoint.getText()),
                    Integer.parseInt(duration.getText()), Integer.parseInt(perk.getText()), "", targett.getOpaque(),
                    actionnn.getOpaque());
            CraftingMenu.getMenu().addToShop(spell);
        }catch (Exception exc){
            System.err.println("couldn't make the spell sry !");
        }*/

    }

    private void hero() {
        vbox.getChildren().clear() ;
        TextField name = new TextField() , ap  = new TextField() , hp  = new TextField() , attackType  = new TextField() ,
                range  = new TextField() , specialPower  = new TextField() , cooldown  = new TextField() ,
                cost  = new TextField(), manapoint = new TextField();
        vbox.getChildren().addAll(name , ap , hp , attackType , range , specialPower , cooldown , cost , manapoint) ;
        for (Node node : vbox.getChildren()){
            if (node instanceof TextField){
                ((TextField)node).setPromptText(node.getId());
            }
        }
        craft.setOnAction(e -> {
            String at = attackType.getText();
            AttackType att ;
            if (at.equals("melee")) att = new Melee() ;
            else if (at.equals("ranged")) att = new Range();
            else if (at.equals("hybrid")) att = new Hybrid();
            else return ;

            at = specialPower.getText() ;
            Spell specialP ;
            try {
                specialP = (Spell) Shop.getInstance().getCollection().getCard(at);
            }catch(Exception exc){
                System.err.println("no such spell for special power !");
                return ;}

            try{
                Hero hero = new Hero(name.getText() , Integer.parseInt(cost.getText()) , Integer.parseInt(hp.getText()) ,
                        Integer.parseInt(ap.getText()) , att , Integer.parseInt(range.getText()) , specialP ,
                        Integer.parseInt(manapoint.getText()) , Integer.parseInt(cooldown.getText()), "custom" );
                CraftingMenu.getMenu().addToShop(hero);
            }catch (Exception exc){
                System.err.println("invalid info to make a hero !");
            }
        });
    }
}
