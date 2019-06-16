package Controller.menu.Graphics.FXMLController;

import Controller.menu.SignInMenu;
import Model.account.Account;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import stuff.Resources;

import java.io.IOException;
import java.util.ArrayList;

public class LeaderBoardFXMLC {
    private static Stage stage ;
    private static Scene scene ;
    @FXML
    private static ScrollPane scrollPane ;
    private static VBox vbox ;

    public static void showLeaderBoard(ArrayList<Account> accounts){
        stage = new Stage();
        try {
            Parent root = FXMLLoader.load(Controller.menu.Graphics.FXMLController.LeaderBoardFXMLC.class.getResource("LeaderBoard.fxml"));
            scene = new Scene(root , 800 , 600);
        }catch(IOException e){
            System.err.println("couldn't load the freaking popup due to IOException");
        }catch (Exception e){
            System.err.println("couldn't load the freaking popup");
        }
        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/LeaderBoard.css");
        scrollPane =(ScrollPane) scene.lookup("#scrollPane");
        vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setFillWidth(true);
        vbox.setMinWidth(400);
        vbox.setSpacing(20);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        int index = 1;
        for (Account account : accounts){
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER_LEFT);
            Label label = new Label(index+"- " + account.getName() + " (" + account.getUsername() + ")"
                    + " Wins : " + account.getWins());
            System.out.println("hellow : " + label.getText());
            label.getStyleClass().add("nameLabel");
            label.setOnMouseClicked(e -> {
                 SignInMenuFXMLC fxmlc = (SignInMenuFXMLC)SignInMenu.getMenu().getGraphic().getController() ;
                 fxmlc.setUsernameInput(account.getUsername());
                stage.close();
            });
            label.setCursor(new ImageCursor(new Image(Resources.mouse_assist.getPath())));
            hbox.getChildren().add(label);
            vbox.getChildren().add(hbox);
            index++ ;
        }
        scrollPane.setContent(vbox);
        stage.setScene(scene);
        stage.show() ;
    }
}
