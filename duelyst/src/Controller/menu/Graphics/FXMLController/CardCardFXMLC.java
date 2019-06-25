package Controller.menu.Graphics.FXMLController;

import Controller.menu.Graphics.GraphicsControls;
import Controller.menu.ShopMenu;
import Model.account.Account;
import Model.account.Shop;
import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.hermione.Hero;
import Model.card.spell.Spell;
import View.MenuHandler;
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
    private Label name , price , ap , hp , count;
    private int counter = 0 ;
    @FXML
    private Button buy;
    @FXML
    private HBox firstHbox ;
    @FXML
    private AnchorPane pane ;


    public void buildCardCard(Card card , Account account , ShopMenuFXMLC fxmlc){

        Image cardBackground = new Image("Resources/card_backgrounds/card_back_shimzar.png") ;
        Image hero_stamp = new Image ("Resources/images/hero_stamp.png");
        Image spell_stamp = new Image ("Resources/images/spell_stamp.png");

        name.setText(card.getName());
        price.setText("Price : " + card.getPrice()+"$");
        GraphicsControls.setButtonStyle("shopping-button" , buy);
        if (account.getMoney() >= card.getPrice()){
            buy.setOnAction(e -> buy(card , fxmlc));
            buy.setCursor(new ImageCursor(new Image(Resources.mouse_card.getPath())));
        }else{
            buy.setCursor(new ImageCursor(new Image(Resources.mouse_disabled.getPath())));
            price.setTextFill(Color.RED);
        }
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
            if (c.equals(card)) counter++ ;
        }
        setCountText();
        try {
            imageView.setImage(card.getCardGraphics().getAvatar());
        }catch(NullPointerException e){
            imageView.setImage(cardBackground);
        }
    }

    private void setCountText() {
        if (counter == 0) count.setText("You don't have this Card");
        else {
            count.setText("You own " + counter + " unit");
            if (counter > 1) count.setText(count.getText() + "s");
        }
    }

    private void buy(Card card , ShopMenuFXMLC fxmlc) {
        try {
            ShopMenu.getMenu().buy(card.getName());
            counter++;
            setCountText();
            fxmlc.updateBalance();
        }catch(Exception e){
            System.err.println("error has been occurred while buying card in CardCardFXMLC");
            e.printStackTrace();
        }
    }

}
