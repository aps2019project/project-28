package Controller.menu.Graphics.FXMLController;

import Controller.menu.CustomModeMenu;
import Controller.menu.Graphics.GraphicsControls;
import Controller.menu.Menu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class ShopMenuFXMLC extends FXMLController {

    @FXML
    private Button backButton;
    @FXML
    private ScrollPane scrollPane ;
    @FXML
    private VBox vbox ;

    @Override
    public void buildScene() {
        super.buildScene();
        Scene scene = menu.getGraphic().getScene();

        scene.setUserAgentStylesheet("Controller/menu/Graphics/StyleSheets/CustomModeMenu.css");
        scrollPane.setContent(vbox);
        for (int i = 0 ; i < 5 ; i++) {
            FXMLLoader rootLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(
                    "Controller/menu/Graphics/FXMLs/Hbox.fxml")));
            Parent hbox = new HBox();
            try {
                hbox = (HBox) rootLoader.load();
                Button button =(Button) hbox.lookup("#button");
                button.setText("butbut");

            } catch (IOException e) {
                e.printStackTrace();
            }
            vbox.getChildren().add(hbox);
        }

        GraphicsControls.setBackButtonOnPress(backButton);




    }

    private void enterSubMenu(Menu subMenu){
        menu.enter(subMenu);
    }
}
