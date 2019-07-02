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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import stuff.Resources;


public class CardCardFXMLC {

    @FXML
    private ImageView imageView , heroStamp;
    @FXML
    private Label name , price , ap , hp , exist;
    private boolean exists = false ;
    @FXML
    private Button buy;
    @FXML
    private HBox firstHbox ;
    @FXML
    private AnchorPane pane ;

    public void buildCardCard(Card card , Account account){

        Image cardBackground = new Image("resources/card_backgrounds/card_back_shimzar.png") ;
        Image hero_stamp = new Image ("resources/images/hero_stamp.png");
        Image spell_stamp = new Image ("resources/images/spell_stamp.png");

        name.setText(card.getName());
        price.setText("Price : " + card.getPrice()+"$");
        GraphicsControls.setButtonStyle("shopping-button" , buy);
        if (card instanceof Spell){
            pane.getChildren().remove(firstHbox);
            heroStamp.setImage(spell_stamp);
        }else{
            Hermione h = (Hermione) card ;
            ap.setText(ap.getText() + h.getAttackPoint());
            hp.setText(hp.getText() + h.getHealthPoint());
            if (h instanceof Hero) heroStamp.setImage(hero_stamp);
        }
        for (Card c : account.getCollection().getCards()){
            if (c.equals(card)) {
                exists = true ;
                break;
            }
        }
        existanceCheck();
//        try {
//            imageView.setImage(card.getCardGraphics().getAvatar());
//        }catch(NullPointerException e){
        imageView.setImage(cardBackground);
//        }
        if (account.getMoney() >= card.getPrice() && !exists){
            buy.setCursor(new ImageCursor(new Image(Resources.mouse_card.getPath())));
        }else{
            buy.setCursor(new ImageCursor(new Image(Resources.mouse_disabled.getPath())));
            if (exists) exist.setTextFill(Color.RED);
            else price.setTextFill(Color.RED);
            buy.setDisable(true);
        }
    }

    public void buildCardCard(Card card , Account account , ShopMenuFXMLC fxmlc){
        buildCardCard(card , account);
        buy.setOnAction(e -> buy(card , fxmlc));
    }


    private void existanceCheck() {
        if (!exists) exist.setText("You don't have this Item");
        else {
            exist.setText("You own this item");
            buy.setDisable(true);
            buy.getStyleClass().remove("shopping-button");
            buy.getStyleClass().add("shopping-button-disabled");
        }
    }

    private void buy(Card card , ShopMenuFXMLC fxmlc) {
        try {
            ShopMenu.getMenu().buy(card.getName());
            exists = true ;
            existanceCheck();
            fxmlc.updateBalance();
        }catch(Exception e){
            System.err.println("error has been occurred while buying card in CardCardFXMLC");
            e.printStackTrace();
        }
    }

    public Button getButton(){
        return buy ;
    }

}
