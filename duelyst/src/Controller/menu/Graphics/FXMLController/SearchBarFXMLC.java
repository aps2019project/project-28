package Controller.menu.Graphics.FXMLController;

import Controller.menu.Graphics.GraphicsControls;
import Controller.menu.ShopMenu;
import Model.account.Account;
import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.hermione.Hero;
import Model.card.spell.Spell;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import stuff.Resources;


public class SearchBarFXMLC {

    @FXML
    private Button x , find ;
    @FXML
    private TextField bar ;

    public Button getFindButton() {
        return find;
    }
    public String getSearchText(){
        return bar.getText() ;
    }

    private void xButtonClicked(){
        bar.setText("");
    }

}
