package Controller.menu.Graphics.FXMLController;

import Controller.menu.Graphics.GraphicsControls;
import Controller.menu.ShopMenu;
import Model.account.Account;
import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.hermione.Hero;
import Model.card.spell.Spell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import network.ChatMSG;
import stuff.Resources;

import java.util.Objects;


public class MessageFXMLC {

    @FXML
    private Text textBox ;
    @FXML
    private Label nameLabel , dateLabel ;

    public static Node MakeMessage(ChatMSG msg){
        try {
            String rootPath = "Controller/menu/Graphics/FXMLs/Message.fxml";
            FXMLLoader rootLoader = new FXMLLoader(Objects.requireNonNull(Popup.class.getClassLoader().getResource(rootPath)));
            Parent root = rootLoader.load();
            MessageFXMLC controller = rootLoader.getController();
            controller.buildMessage(msg);
            return root ;

        }catch (Exception e){
            System.err.println("couldn't load the message !");
            e.printStackTrace();
        }
        return null ;
    }

    private void buildMessage(ChatMSG msg) {
        dateLabel.setText(msg.getDate());
        nameLabel.setText(msg.getAccount().getName());
        textBox.setText(msg.getText());
    }


}