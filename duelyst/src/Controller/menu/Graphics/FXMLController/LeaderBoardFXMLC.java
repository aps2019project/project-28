package Controller.menu.Graphics.FXMLController;

import Controller.menu.SignInMenu;
import Model.account.Account;
import View.GraphicView;
import View.Listeners.OnLeaderBoardClickedListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import stuff.Resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class LeaderBoardFXMLC {
//    private static LeaderBoardFXMLC leaderBoard ;
    private Stage stage ;
    @FXML
    private ScrollPane scrollPane ;
    private VBox vbox ;

//    public static LeaderBoardFXMLC getLeaderBoard(){
//        if (leaderBoard == null) leaderBoard = new LeaderBoardFXMLC() ;
//        return leaderBoard;
//    }
//    public  LeaderBoardFXMLC(){
//        leaderBoard=this;
//    }

    public static void makeNewScene(ArrayList<Account>accounts) {
        try {
            Parent root;
            String rootPath = "Controller/menu/Graphics/FXMLs/LeaderBoard.fxml";
            FXMLLoader rootLoader = new FXMLLoader(Objects.requireNonNull(LeaderBoardFXMLC.class.getClassLoader().getResource(rootPath)));
            root = rootLoader.load();
            Scene scene = new Scene(root, 800, 500);
            scene.setOnMouseEntered(e -> scene.setCursor(new ImageCursor(new Image(Resources.mouse_auto.getPath()))));
            scene.setOnMouseMoved(e -> scene.setCursor(new ImageCursor(new Image(Resources.mouse_auto.getPath()))));

            LeaderBoardFXMLC controller = rootLoader.getController();
            controller.show(accounts,scene);
        }catch (Exception e){}
    }

    public void show(ArrayList<Account> accounts,Scene scene) {
        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/LeaderBoard.css");
        stage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (! isNowFocused) {
                stage.hide();
            }
        });

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
