package Controller.menu.Graphics.FXMLController;

import Controller.menu.Graphics.GraphicsControls;
import Model.account.Account;
import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.spell.Spell;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import stuff.Resources;


public class CardCardFXMLC {

    @FXML
    private ImageView imageView;
    @FXML
    private Label name , price , ap , hp , count;
    private int counter = 0 ;
    @FXML
    private Button buy;
    @FXML
    private HBox firstHbox ;
    @FXML
    private AnchorPane pane ;

    public void buildCardCard(Card card , Account account){
        name.setText(card.getName());
        price.setText(card.getPrice()+"");
        GraphicsControls.setButtonStyle("shopping-button" , buy);
        if (account.getMoney() >= card.getPrice()){
            buy.setOnAction(e -> buy());
            buy.setCursor(new ImageCursor(new Image(Resources.mouse_card.getPath())));
        }else{
            buy.setCursor(new ImageCursor(new Image(Resources.mouse_disabled.getPath())));
            price.setTextFill(Color.RED);
        }
        if (card instanceof Spell){
            pane.getChildren().remove(firstHbox);
        }else{
            Hermione h = (Hermione) card ;
            ap.setText(ap.getText() + h.getAttackType());
            hp.setText(hp.getText() + h.getHealthPoint());
        }
        for (Card c : account.getCollection().getCards()){
            if (c.equals(card)) counter++ ;
        }
        if (counter == 0) count.setText("You don't have this Card");
        count.setText("You own " + counter + " unit");
        if (counter > 1 ) count.setText(count.getText() + "s");
        imageView.setImage(card.getCardGraphics().getAvatar());
    }

    private void buy() {
    }

}
