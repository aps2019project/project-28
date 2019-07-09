package Controller.menu.Graphics.FXMLController;

import Controller.menu.Graphics.GraphicsControls;
import Controller.menu.ShopMenu;
import Model.Graphics.SpriteAnimation;
import Model.account.Account;
import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.hermione.Hero;
import Model.card.spell.Spell;
import exeption.InvalidCardException;
import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import stuff.Resources;


public class CollectionCardHermioneFXMLC {
    private static Image heroImage = new Image("resources/images/heroIcon.png");
    private static Image minionImage = new Image("resources/images/minionIcon.png");
    @FXML
    private ImageView avatar;
    @FXML
    private Label name , type , ap , hp ;
    @FXML
    private Button sellButton ;
    @FXML
    private AnchorPane pane ;

    public void buildCardCard(Hermione card) {
        try {
//            avatar.setImage(card.getCardGraphics().getAvatar());
            Image image = new Image(card.getGraphics().getUnits());
            avatar.setImage(image);
            final Animation animation = new SpriteAnimation(
                    avatar,
                    Duration.millis(2000),
                    8, 1,
                    0, 0,
                    1024/10, 1024/10
            );
            animation.setCycleCount(Animation.INDEFINITE);
            animation.play();
        }catch(Exception e){
            System.err.println("card avatar not available");
            if (card instanceof Hero) avatar.setImage(heroImage);
            else avatar.setImage(minionImage);
        }
        name.setText(card.getName());
        type.setText((card instanceof Hero)?"Hero":"Minion");
        ap.setText(card.getAttackPoint()+"");
        hp.setText(card.getHealthPoint()+"");
        sellButton.setText("Sell : " + card.getPrice() + "$");
        GraphicsControls.setButtonStyle("shopping-button" ,sellButton);
        sellButton.setOnAction(e -> {
            try {
                sellAction(card);
            }catch(Exception ex){
                System.err.println("error occurred while trying to sell the card !");
                ex.printStackTrace();
            }
        });
    }

    public void buildCardCard(Hermione card , boolean havesellButton) {
         buildCardCard(card);
        if (!havesellButton){
            pane.getChildren().remove(sellButton);
        }
    }

    public Button getButton(){
        return sellButton;
    }

    public void buildCardCard(Hermione card , CollectionMenuFXMLC fxmlc) {
        buildCardCard(card);
        sellButton.setOnAction(e -> {
            try {
                sellAction(card);
                fxmlc.updateBalance();
            }catch(Exception ex){
                System.err.println("error occurred while trying to sell the card !");
                ex.printStackTrace();
            }
        });
    }

    private void sellAction(Hermione card) throws InvalidCardException {
        ShopMenu.getMenu().sell(card.getName());
        pane.getChildren().clear();
        pane.setStyle("-fx-opacity: 0.0");
    }

}
