package Controller.menu.Graphics.FXMLController;

import Controller.menu.Graphics.GraphicsControls;
import Controller.menu.ShopMenu;
import Model.Graphics.SpriteAnimation;
import Model.card.hermione.Hermione;
import Model.card.hermione.Hero;
import Model.card.spell.Spell;
import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public class CollectionCardSpellFXMLC {
    private static Image heroImage = new Image("resources/images/heroIcon.png");
    private static Image minionImage = new Image("resources/images/minionIcon.png");
    @FXML
    private Label name;
    @FXML
    private Button sellButton ;
    @FXML
    private AnchorPane pane ;

    public void buildCardCard(Spell card) {
        name.setText(card.getName());
        sellButton.setText("Sell : " + card.getPrice() + "$");
        GraphicsControls.setButtonStyle("shopping-button" ,sellButton);
        sellButton.setOnAction(e -> {
            try {
                ShopMenu.getMenu().sell(card.getName());
                pane.getChildren().clear() ;
                pane.setStyle("-fx-opacity: 0.0");
            }catch(Exception ex){
                System.err.println("error occurred while trying to sell the card !");
                ex.printStackTrace();
            }
        });
    }
    public void buildCardCard(Spell card , boolean hasSpellButton) {
        buildCardCard(card);
        if (!hasSpellButton){
            pane.getChildren().remove(sellButton);
        }
    }

    public Button getButton() {
        return sellButton;
    }
}
