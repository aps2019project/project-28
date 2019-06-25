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
    private Label name , price , count;
    private int counter = 0 ;
    @FXML
    private Button buy;



    public void builditemCard(Usable item , Account account , ShopMenuFXMLC fxmlc){
        Image itemBackground = new Image("Resources/card_backgrounds/card_back_agenor.png");

        name.setText(item.getName());
        price.setText("Price : " + item.getPrice()+"$");
        GraphicsControls.setButtonStyle("shopping-button" , buy);
        if (account.getMoney() >= item.getPrice()){
            buy.setOnAction(e -> buy(item , fxmlc));
            buy.setCursor(new ImageCursor(new Image(Resources.mouse_card.getPath())));
        }else{
            buy.setCursor(new ImageCursor(new Image(Resources.mouse_disabled.getPath())));
            price.setTextFill(Color.RED);
        }
        for (Usable c : account.getCollection().getUsables()){
            if (c.equals(item)) counter++ ;
        }
        setCountText();
        //TODO item graphics !!!
//        try {
//            imageView.setImage(item.getGraphics());
//        }catch(NullPointerException e){
            imageView.setImage(itemBackground);
//        }
    }

    private void setCountText() {
        if (counter == 0) count.setText("You don't have this Item");
        else {
            count.setText("You own " + counter + " unit");
            if (counter > 1) count.setText(count.getText() + "s");
        }
    }

    private void buy(Item item , ShopMenuFXMLC fxmlc) {
        try {
            ShopMenu.getMenu().buy(item.getName());
            counter++;
            setCountText();
            fxmlc.updateBalance();
        }catch(Exception e){
            System.err.println("error has been occurred while buying Item in ItemCardFXMLC");
            e.printStackTrace();
        }
    }

}
