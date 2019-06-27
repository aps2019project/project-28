package Controller.menu.Graphics.FXMLController;

import Controller.menu.Graphics.GraphicsControls;
import Controller.menu.ShopMenu;
import Model.card.hermione.Hermione;
import Model.card.spell.Spell;
import Model.item.Item;
import Model.item.Usable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;


public class CollectionCardItemFXMLC {
    @FXML
    private Label name;
    @FXML
    private Button sellButton ;
    @FXML
    private AnchorPane pane ;

    public void buildCardCard(Usable item) {
        name.setText(item.getName());
        sellButton.setText("Sell : " + item.getPrice() + "$");
        GraphicsControls.setButtonStyle("shopping-button" ,sellButton);
        sellButton.setOnAction(e -> {
            try {
                ShopMenu.getMenu().sell(item.getName());
                pane.getChildren().clear() ;
                pane.setStyle("-fx-opacity: 0.0");
            }catch(Exception ex){
                System.err.println("error occurred while trying to sell the card !");
                ex.printStackTrace();
            }
        });
    }
    public void buildCardCard(Usable item , boolean havesellButton) {
        buildCardCard(item);
        if (!havesellButton){
            pane.getChildren().remove(sellButton);
        }
    }
}
