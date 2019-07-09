package Controller.menu.Graphics.FXMLController;

import Controller.menu.*;
import Controller.menu.Graphics.GraphicsControls;
import View.MenuHandler;
import exeption.InvalidAccountException;
import exeption.WrongPassException;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import network.ChatMSG;

import java.util.ArrayList;

public class MultiPlayerModeMenuFXMLC extends FXMLController {

    @FXML
    private Button play, setDeck , backButton ;
    @FXML
    private ScrollPane scrollPane ;
    @FXML
    private TextField textBox ;
    private VBox content = new VBox();


    @Override
    public void buildScene() {
        super.buildScene();
        Scene scene = menu.getGraphic().getScene();

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/MultiPlayerMenu.css");
        scrollPane.setContent(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.vvalueProperty().bind(content.heightProperty());
        GraphicsControls.setButtonStyle("menu-button" , play , setDeck);
        GraphicsControls.setBackButtonOnPress(backButton);

        play.setOnAction(e -> play());
        setDeck.setOnAction(e -> ((MultiPlayerModeMenu)menu).showDeckSelector(menu.getAccount()));
        buildContent();
        new AnimationTimer() {
            private long lastUpade = 0;
            @Override
            public void handle(long now) {
                if (now - lastUpade >= 2000_000_000)
                    buildContent();
            }
        }.start();

        textBox.setOnKeyPressed(e -> {
            String text = textBox.getText();
            if (e.getCode() == KeyCode.ENTER && !text.isEmpty()){
                ((MultiPlayerModeMenu)menu).sendMessage(text);
            }
        });
    }

    private void buildContent() {
        ArrayList<ChatMSG> msgs = ((MultiPlayerModeMenu)menu).getChats() ;
        for (ChatMSG msg : msgs){
            Node msgNode =  MessageFXMLC.MakeMessage(msg);
            if (!content.getChildren().contains(msgNode))
                content.getChildren().add(msgNode);
        }
    }


    private void play() {
        try {
            ((MultiPlayerModeMenu)menu).selectUser("" , "");
            play.setText("Cancel");
            play.setTextFill(Color.RED);
            play.setOnAction(e2 -> cancel());
        } catch (InvalidAccountException | WrongPassException ex) {
            ex.printStackTrace();
        }
    }

    private void cancel() {
        //TODO Arschia !
        play.setText("Cancel");
        play.setTextFill(Color.RED);
        play.setOnAction(e2 -> play());
    }
}
