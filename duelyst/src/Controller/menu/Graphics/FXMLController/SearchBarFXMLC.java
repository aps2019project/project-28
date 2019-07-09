package Controller.menu.Graphics.FXMLController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class SearchBarFXMLC {

    @FXML
    private Button x , find ;
    @FXML
    private TextField bar ;

    public Button getFindButton() {
        return find;
    }
    public String getSearchText(){
        return bar.getText() ;
    }

    private void xButtonClicked(){
        bar.setText("");
    }

    public void build(SearchBarHaving menuController) {
        x.setOnAction(e -> xButtonClicked());
        bar.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER){
                menuController.search();
            }
        });
    }
}
