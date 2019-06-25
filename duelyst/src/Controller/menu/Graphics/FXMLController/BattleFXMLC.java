package Controller.menu.Graphics.FXMLController;

import Controller.menu.Battle;
import Controller.menu.Graphics.GraphicsControls;
import Model.Graphics.SpriteAnimation;
import Model.account.Hand;
import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.hermione.Hero;
import Model.card.hermione.Minion;
import Model.card.spell.Spell;
import exeption.*;
import javafx.animation.Animation;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class BattleFXMLC extends FXMLController {

    @FXML
    public Button endTurn;
    public Button graveYard;
    public Button menuButton;
    public GridPane playerMana;
    public GridPane enemyMana;
    @FXML
    private AnchorPane frame;
    @FXML
    private GridPane map;
    @FXML
    public GridPane handFrame;
    @FXML
    public GridPane handManaFrame;

    @Override
    public void buildScene() {//setStyles
        super.buildScene();
        for(int i = 0 ; i < 9 ; i++){
            for (int j = 0 ; j < 5 ; j++){
                Rectangle rectangle = getRectangle(i, j);
                ImageView imageView = getCell(i , j);
                GraphicsControls.setCellStyle("cell", rectangle, imageView);
            }
        }
        GraphicsControls.setButtonStyle("endTurnButton", endTurn);
        GraphicsControls.setButtonStyle(".battleMenuButton", menuButton);
        GraphicsControls.setButtonStyle(".graveYardButton", graveYard);
    }
    @Override
    public void updateScene() {
        super.updateScene();
        turnUpdate();
        endTurn.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                try {
                    Battle.getMenu().endTurn();
                    turnUpdate();
                } catch (HandFullException | DeckIsEmptyException e) {
                    e.printStackTrace();
                }
            }
        });
        menuButton.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //todo: goto mainMenu
            }
        });
        graveYard.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //todo: goto graveyardmenu
            }
        });
        dragAndDropTarget();
        dragAndDropSource();
    }

    private void dragAndDropTarget() {
        for (int i = 0 ; i < 9 ; i++) {
            for (int j = 0 ; j < 5 ; j++) {
                ImageView cell = getCell(i, j);
                cell.setOnDragOver(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        if (event.getDragboard().hasImage()) {
                            event.acceptTransferModes(TransferMode.ANY);
                        }
                        event.consume();
                    }
                });
                cell.setOnDragDropped(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        try{
                            ImageView source = (ImageView)event.getGestureSource();
                            if(map.getChildren().contains(source)) {
                                Hermione hermione = Battle.getMenu().getMap().getCell(GridPane.getColumnIndex(source), GridPane.getRowIndex(source)).getCardOnCell();
                                try {
                                    hermione.move(GridPane.getColumnIndex(cell), GridPane.getRowIndex(cell));
                                    hermione.getGraphics().onSpawn(Battle.getMenu().getMap().getCell(GridPane.getColumnIndex(cell), GridPane.getRowIndex(cell)));
                                    source.setImage(null);
                                    updateAfterMove();
                                } catch (MoveTrunIsOverException | DestinationOutOfreachException |
                                        CardCantBeMovedException | DestinationIsFullException |
                                        InvalidCellException e) {
                                    //todo: sorry we cant move
                                    System.err.println("we cant move");
                                }
                            }
                            else if(handFrame.getChildren().contains(source)){
                                Card card = getCardOnHand(GridPane.getColumnIndex(source));
                                if(card != null){
                                    try {
                                        Battle.getMenu().insert(card.getCardID(), GridPane.getColumnIndex(cell), GridPane.getRowIndex(cell));
                                        if(card instanceof Hermione){
                                            Hermione hermione = (Hermione) card;
                                            hermione.getGraphics().onSpawn(Battle.getMenu().getMap().getCell(
                                                    GridPane.getColumnIndex(cell), GridPane.getRowIndex(cell)));
                                        }
                                        else if(card instanceof Spell){
                                            Spell spell = (Spell)card;
                                            getRectangle(GridPane.getColumnIndex(cell), GridPane.getRowIndex(cell)).getStyleClass().add("cellEntered");
                                            //todo some other ways....?
                                        }
                                        source.setImage(null);
                                    } catch (InvalidCardException | NotEnoughManaException |
                                            DestinationIsFullException | InvalidCellException e) {
                                        //todo ops u cant do anything
                                        System.err.println("we cant insert");
                                    }
                                }
                            }
                            updateAfterMove();
                        } catch (InvalidCellException e) { e.printStackTrace(); }
                        event.consume();
                }});
            }
        }
    }

    private void dragAndDropSource() {
        for (int i = 0 ; i < 9 ; i++) {
            for (int j = 0 ; j < 5 ; j++) {
                try {
                    Hermione hermione = Battle.getMenu().getMap().getCell(i, j).getCardOnCell();
                    if(hermione != null){
                        ImageView hermioneView = getCell(hermione.getLocation().getX(), hermione.getLocation().getY());
                        hermioneView.setOnDragDetected(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                Dragboard db = hermioneView.startDragAndDrop(TransferMode.ANY);
                                ClipboardContent content = new ClipboardContent();
                                content.putImage(new Image(hermione.getGraphics().getUnitGifs()));
                                db.setContent(content);
                                event.consume();
                            }
                        });
                    }
                } catch (InvalidCellException e) { e.printStackTrace(); }
            }
        }
        for (int i = 0 ; i < Hand.SIZE ; i++){
            Card card = getCardOnHand(i);
            if(card != null){
                ImageView cardView = (ImageView) handFrame.getChildren().get(i);
                cardView.setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Dragboard db = cardView.startDragAndDrop(TransferMode.ANY);
                        ClipboardContent content = new ClipboardContent();
                        if(card instanceof Hermione)
                            content.putImage(new Image(((Hermione)card).getGraphics().getUnitGifs()));
                        else if(card instanceof Spell)
                            content.putImage(new Image(((Spell)card).getSpellGraphics().getIconGif()));
                        db.setContent(content);
                        event.consume();
                    }
                });
            }
        }
    }

    private void updateAfterMove(){
        dragAndDropSource();
        updateHand();
    }

    private void turnUpdate(){
        updateHand();
        updateMana();
    }

    private void updateHand(){
        Card[] playerHandCards = Battle.getMenu().getPlayer().getHand().getCards();
        for (int i = 0 ; i < Hand.SIZE ;  i++) {
            if(playerHandCards[i] != null) {
                ImageView card = (ImageView) handFrame.getChildren().get(i);
                if(playerHandCards[i] instanceof Minion)
                    card.setImage(new Image(((Minion)playerHandCards[i]).getGraphics().getIcon()));
                else if(playerHandCards[i] instanceof Spell)
                card.setImage(new Image(((Spell)playerHandCards[i]).getSpellGraphics().getIcon()));
                final Animation animation = new SpriteAnimation(
                        card,
                        Duration.millis(2000),
                        5, 1,
                        100, 0,
                        50, 50
                );
                animation.setCycleCount(Animation.INDEFINITE);
                animation.play();

                Label mana = (Label) handManaFrame.getChildren().get(i + 5);
                mana.setText(Integer.toString(playerHandCards[i].getManaPoint()));
            }
        }
    }

    private void updateMana(){
        int mana = Battle.getMenu().getPlayer().getMana();
        for (int i = 0 ; i < mana ; i++){
            playerMana.getChildren().get(i).getStyleClass().add("mana");
        }
        for (int i = mana ; i < 9 ; i++){
            playerMana.getChildren().get(i).getStyleClass().add("emptyMana");
        }
        int enemyManaNumber = Battle.getMenu().getEnemyPlayer().getMana();
        for (int i = 0 ; i < enemyManaNumber ; i++){
            enemyMana.getChildren().get(i).getStyleClass().add("mana");
        }
        for (int i = enemyManaNumber ; i < 9 ; i++){
            enemyMana.getChildren().get(i).getStyleClass().add("emptyMana");
        }

    }
    private Card getCardOnHand(int index){
        for (int i = 0 ; i < Hand.SIZE ;  i++) {
            if (((ImageView)(handFrame.getChildren().get(i))).getImage() != null){
                return Battle.getMenu().getPlayer().getHand().getCards()[i];
            }
        }
        return null;
    }

    public ImageView getCell(int x , int y){
        for (Node node : map.getChildren()) {
            if (GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y) {
                if(node instanceof ImageView)
                    return (ImageView) node;
            }
        }
        return null;
    }

    private Rectangle getRectangle(int x, int y){
        for(Node node : map.getChildren()){
            if(GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y){
                if(node instanceof Rectangle){
                    return (Rectangle) node;
                }
            }
        }
        return null;
    }

    public void addToScene(Node... nodes){
        this.frame.getChildren().addAll(nodes);
    }

    public void removeFromScene(Node... nodes) {
        this.frame.getChildren().removeAll(nodes);
    }

    public double getMapWidth() {
        return map.getWidth();
    }

    public double getMapHeight(){
        return map.getHeight();
    }
}
