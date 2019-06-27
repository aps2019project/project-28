package Controller.menu.Graphics.FXMLController;

import Controller.menu.Graphics.GraphicsControls;
import Controller.menu.ShopMenu;
import Model.account.Account;
import Model.item.Item;
import Model.item.Usable;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import stuff.Resources;


public class ItemCardFXMLC {

    @FXML
    private ImageView imageView ;
    @FXML
    private Label name , price , exist;
    private boolean exists = false ;
    @FXML
    private Button buy;



    public void builditemCard(Usable item , Account account , ShopMenuFXMLC fxmlc){
        Image itemBackground = new Image("resources/card_backgrounds/card_back_agenor.png");

        name.setText(item.getName());
        price.setText("Price : " + item.getPrice()+"$");
        GraphicsControls.setButtonStyle("shopping-button" , buy);
        for (Usable c : account.getCollection().getUsables()){
            if (c.equals(item)) {
                exists = true ;
                break;
            }
        }
        existanceCheck();
        //TODO item graphics !!!
//        try {
//            imageView.setImage(item.getGraphics());
//        }catch(NullPointerException e){
            imageView.setImage(itemBackground);
//        }
        if (account.getMoney() >= item.getPrice() && !exists){
            buy.setOnAction(e -> buy(item , fxmlc));
            buy.setCursor(new ImageCursor(new Image(Resources.mouse_card.getPath())));
        }else{
            buy.setCursor(new ImageCursor(new Image(Resources.mouse_disabled.getPath())));
            if (exists) exist.setTextFill(Color.RED);
            else price.setTextFill(Color.RED);
            buy.setDisable(true);
        }
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

    private void buy(Item item , ShopMenuFXMLC fxmlc) {
        try {
            ShopMenu.getMenu().buy(item.getName());
            exists = true ;
            existanceCheck();
            fxmlc.updateBalance();
        }catch(Exception e){
            System.err.println("error has been occurred while buying Item in ItemCardFXMLC");
            e.printStackTrace();
        }
    }

}
