package Controller.menu.Graphics.FXMLController;

import Controller.menu.Graphics.GraphicsControls;
import Model.account.Account;
import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.hermione.Hero;
import Model.card.spell.Spell;
import Model.item.Item;
import Model.item.Usable;
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


public class ItemCardFXMLC {

    @FXML
    private ImageView imageView , heroStamp;
    @FXML
    private Label name , price , count;
    private int counter = 0 ;
    @FXML
    private Button buy;
    @FXML
    private AnchorPane pane ;



    public void builditemCard(Usable item , Account account){
        Image itemBackground = new Image("Resources/card_backgrounds/card_back_agenor.png");

        name.setText(item.getName());
        price.setText("Price : " + item.getPrice()+"$");
        GraphicsControls.setButtonStyle("shopping-button" , buy);
        if (account.getMoney() >= item.getPrice()){
            buy.setOnAction(e -> buy());
            buy.setCursor(new ImageCursor(new Image(Resources.mouse_card.getPath())));
        }else{
            buy.setCursor(new ImageCursor(new Image(Resources.mouse_disabled.getPath())));
            price.setTextFill(Color.RED);
        }
        for (Usable c : account.getCollection().getUsables()){
            if (c.equals(item)) counter++ ;
        }
        if (counter == 0) count.setText("You don't have this Item");
        else {
            count.setText("You own " + counter + " unit");
            if (counter > 1) count.setText(count.getText() + "s");
        }
        //TODO item graphics !!!
//        try {
//            imageView.setImage(item.getGraphics());
//        }catch(NullPointerException e){
            imageView.setImage(itemBackground);
//        }
    }

    private void buy() {
    }

}
