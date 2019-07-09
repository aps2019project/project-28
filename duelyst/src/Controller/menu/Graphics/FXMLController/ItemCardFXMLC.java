package Controller.menu.Graphics.FXMLController;

import Controller.menu.Graphics.GraphicsControls;
import Controller.menu.ShopMenu;
import Model.account.Account;
import Model.item.Item;
import Model.item.Usable;
import exeption.*;
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
        builditemCard(item , account);
        buy.setOnAction(e -> buy(item , fxmlc));
    }

    public void builditemCard(Usable item , Account account){
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
            buy.setCursor(new ImageCursor(new Image(Resources.mouse_card.getPath())));
        }else{
            buy.setCursor(new ImageCursor(new Image(Resources.mouse_disabled.getPath())));
            if (exists) exist.setTextFill(Color.RED);
            else price.setTextFill(Color.RED);
            buy.setDisable(true);
        }
    }

    private void existanceCheck() {
        if (!exists) exist.setText("You don't have this thing!");
        else {
            exist.setText("You own this thing!");
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
        }catch(FullCollectionException e){
            Popup.popup("You can't have any more items in your collection ! try selling some!");
        } catch (ItemExistExeption itemExistExeption) {
            itemExistExeption.printStackTrace();
        } catch (InvalidItemException e) {
            e.printStackTrace();
        } catch (InvalidCardException e) {
            e.printStackTrace();
        } catch (NotEnoughMoneyException e) {
           Popup.popup("get the F outta here you peasant !!!");
        } catch (CardExistException e) {
            e.printStackTrace();
        }
    }

    public Button getButton() {
        return buy;
    }
}
