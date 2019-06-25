package Controller.menu.Graphics.FXMLController;

import Controller.menu.Battle;
import Controller.menu.Graphics.GraphicsControls;
import Model.Graphics.Listeners.OnSpawnListener;
import Model.Map.Cell;
import Model.account.Hand;
import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.hermione.Hero;
import exeption.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class BattleFXMLC extends FXMLController {

    @FXML
    public Button endTurn;
    public Button graveYard;
    public Button menuButton;

    @FXML
    private AnchorPane frame;

    @FXML
    private GridPane map;
    private Rectangle[][] rectangles = new Rectangle[9][5];
    private Double mapX;
    private Double mapY;
    private Double cellWidth;
    private Double cellHeight;


    @FXML
    public GridPane handFrame;
    private Double handWidth;
    private Hand playerHand;
    private ImageView[] hand = new ImageView[Hand.SIZE];

    @FXML
    public GridPane handManaFrame;

    private Hero hero;
    private Hero enemyHero;

    @Override
    public void buildScene() {
        super.buildScene();
        for(int i = 0 ; i < 9 ; i++){
            for (int j = 0 ; j < 5 ; j++){
                Rectangle rectangle = getRectangle(i, j);
                ImageView imageView = getCell(i , j);
                GraphicsControls.setCellStyle("cell", rectangle, imageView);
            }
        }

        GraphicsControls.setButtonStyle("endTurnButton", endTurn);
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
        dragAndDropTarget();
        dragAndDropSource();
    }

    private void dragAndDropTarget() {
        //target
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
                        ImageView source = (ImageView)event.getGestureSource();
                        Hermione hermione = null;
                        try {
                            hermione = Battle.getMenu().getMap().getCell(GridPane.getColumnIndex(source), GridPane.getRowIndex(source)).getCardOnCell();
                        } catch (InvalidCellException e) { e.printStackTrace(); }
                       try {
                           hermione.move(Battle.getMenu().getMap().getCell(GridPane.getColumnIndex(cell), GridPane.getRowIndex(cell)).getX()
                                   , Battle.getMenu().getMap().getCell(GridPane.getColumnIndex(cell), GridPane.getRowIndex(cell)).getY());
                           source.setImage(null);
                           hermione.getGraphics().onSpawn(Battle.getMenu().getMap().getCell(GridPane.getColumnIndex(cell), GridPane.getRowIndex(cell)));
                           dragAndDropSource();
                       }catch (Exception e){ e.printStackTrace(); /*todo popup u cant move and stuff */}
                        event.consume();
                    }
                });
            }
        }
    }

    private void dragAndDropSource() {
        for (int i = 0 ; i < 9 ; i++) {
            for (int j = 0 ; j < 5 ; j++) {
                Hermione hermione = null;
                try {
                    hermione = Battle.getMenu().getMap().getCell(i, j).getCardOnCell();
                } catch (InvalidCellException e) { e.printStackTrace(); }
                if (hermione != null) {
                    ImageView hermioneView = getCell(hermione.getLocation().getX(), hermione.getLocation().getY());
                    Hermione finalHermione = hermione;
                    hermioneView.setOnDragDetected(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            Dragboard db = hermioneView.startDragAndDrop(TransferMode.ANY);
                            ClipboardContent content = new ClipboardContent();
                            content.putImage(new Image(finalHermione.getGraphics().getUnitGifs()));
                            db.setContent(content);
                            event.consume();
                        }
                    });
                }
            }
        }
    }



    private void updateAfterMove(){
        dragAndDropSource();


    }

    private void turnUpdate(){
        playerHand = Battle.getMenu().getPlayer().getHand();
        Card[] playerHandCards = playerHand.getCards();
        handWidth = handFrame.getPrefWidth() / Hand.SIZE;
        hero = Battle.getMenu().getPlayer().getDeck().getHero();
        enemyHero = Battle.getMenu().getEnemyPlayer().getDeck().getHero();

        for (int i = 0 ; i < Hand.SIZE ;  i++) {
            if(playerHandCards[i] != null) {
                hand[i] = new ImageView(new Image("resources/ui/ranked_chevron_empty.png"));
                hand[i].setX(handFrame.getLayoutX() + i * handWidth);
                hand[i].setY(handFrame.getLayoutY());
                this.addToScene(hand[i]);
            }
        }

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

    public Rectangle getRectangle(int x, int y){
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

    public Double getMapX() {
        mapX = map.getLayoutX();
        return mapX;
    }

    public Double getMapY() {
        mapY = map.getLayoutY();
        return mapY;
    }

    public Double getCellWidth() {
        cellWidth = map.getMaxWidth()/9;
        return cellWidth;
    }

    public Double getCellHeight() {
        cellHeight = map.getHeight()/5;
        return cellHeight;
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
