package View.Listeners;

import Model.item.Item;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public interface OnItemDetailPresentedListener {
    void showItemDetail(Item item);
}
