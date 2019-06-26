package Controller.menu.Graphics.FXMLController;

import Controller.menu.Battle;
import Controller.menu.Graphics.GraphicsControls;
import Controller.menu.GraveYardMenu;
import Controller.menu.MainMenu;
import Model.Graphics.SpriteAnimation;
import Model.account.Hand;
import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.hermione.Minion;
import Model.card.spell.Spell;
import View.MenuHandler;
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
import javafx.util.Duration;

public class BattleFXMLC extends FXMLController {

    @FXML
    public Button endTurn;
    public Button graveYard;
    public Button menuButton;
    public GridPane playerMana;
    public GridPane enemyMana;
    public ImageView firstPlayer;
    public ImageView secondPlayer;
    public ImageView nextCardOnHand;

    @FXML
    private AnchorPane frame;
    @FXML
    private GridPane map;
    @FXML
    public GridPane handFrame;
    @FXML
    public GridPane handManaFrame;

    @Override
    public void init() {//setStyles
        super.init();
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
    public void buildScene() {
        super.buildScene();
        endTurn.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                try {
                    Battle.getMenu().endTurn();
                    updateScene();
                } catch (HandFullException | DeckIsEmptyException e) {
                    e.printStackTrace();
                }
            }
        });
        menuButton.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                MenuHandler.setCurrentMenu(MainMenu.getMenu());
            }
        });
        graveYard.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                MenuHandler.setCurrentMenu(GraveYardMenu.getMenu());
            }
        });
        dragAndDropTarget();
    }

    @Override
    public void enterScene() {
        super.enterScene();
        firstPlayer.setImage(new Image(Battle.getMenu().getPlayer().getUser().getAvatar()));
        secondPlayer.setImage(new Image(Battle.getMenu().getEnemyPlayer().getUser().getAvatar()));
//        updateScene();
    }

    @Override
    public void updateScene() {
        super.updateScene();
        dragAndDropSource();
        updateMana();
        updateHand();
        //card on hover
        //attack enemy
        //specialpower
//        Battle.getMenu().useSpecialPower();
        //use items
//        Battle.getMenu().useItem();

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
                                    Battle.getMenu().select(hermione.getCardID());
                                    Battle.getMenu().move(GridPane.getColumnIndex(cell), GridPane.getRowIndex(cell));
                                    source.setImage(null);
                                    updateScene();
                                } catch (MoveTrunIsOverException | DestinationOutOfreachException |
                                        CardCantBeMovedException | DestinationIsFullException |
                                        InvalidCellException e) {
                                    //todo: sorry we cant move
                                    System.err.println("we cant move");
                                } catch (InvalidItemException | NoCardHasBeenSelectedException | InvalidCardException e) {
                                    e.printStackTrace();
                                }
                            }
                            else if(handFrame.getChildren().contains(source)){
                                Card card = getCardOnHand(GridPane.getColumnIndex(source));
                                if(card != null){
                                    try {
                                        Battle.getMenu().insert(card.getCardID(), GridPane.getColumnIndex(cell), GridPane.getRowIndex(cell));
                                        if(card instanceof Hermione){
                                            Hermione hermione = (Hermione) card;
                                            Battle.getMenu().insert(hermione.getCardID(), GridPane.getColumnIndex(cell), GridPane.getRowIndex(cell));
                                        }
                                        else if(card instanceof Spell){
                                            Spell spell = (Spell)card;
                                            Battle.getMenu().insert(spell.getCardID(), GridPane.getColumnIndex(cell), GridPane.getRowIndex(cell));
//                                            getRectangle(GridPane.getColumnIndex(cell), GridPane.getRowIndex(cell)).getStyleClass().add("cellEntered");
                                        }
                                        source.setImage(null);
                                    } catch (InvalidCardException | NotEnoughManaException |
                                            DestinationIsFullException | InvalidCellException e) {
                                        //todo ops u cant do anything
                                        System.err.println("we cant insert");
                                    }
                                }
                            }
                            updateScene();
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
       Card nextCard = Battle.getMenu().getPlayer().getHand().getNextCard();
        if(nextCard != null){
            if(nextCard instanceof Minion) nextCardOnHand.setImage(new Image(((Minion)nextCard).getGraphics().getIcon()));
            else if(nextCard instanceof Spell) nextCardOnHand.setImage(new Image(((Spell)nextCard).getSpellGraphics().getIcon()));
            final Animation animation = new SpriteAnimation(
                    nextCardOnHand,
                    Duration.millis(2000),
                    5, 1,
                    100, 0,
                    50, 50
            );
            animation.setCycleCount(Animation.INDEFINITE);
            animation.play();
        }
    }

    private void updateMana(){
        int mana = Battle.getMenu().getOwnPLayer().getMana();
        System.err.println(mana);
        for (int i = 0 ; i < mana ; i++){
            if(playerMana.getChildren().get(i).getStyleClass().contains("emptyMana"))
                playerMana.getChildren().get(i).getStyleClass().remove("emptyMana");
            ((ImageView)playerMana.getChildren().get(i)).setImage(new Image("resources/ui/icon_mana.png"));
        }
        for (int i = mana ; i < 9 ; i++){
//            playerMana.getChildren().get(i).getStyleClass().add("emptyMana");
            ((ImageView)playerMana.getChildren().get(i)).setImage(new Image("resources/ui/icon_mana_inactive.png"));
        }
        int enemyManaNumber = Battle.getMenu().getOpponentPlayer().getMana();
        System.err.println(enemyManaNumber);
        for (int i = 0 ; i < enemyManaNumber ; i++){
            if(enemyMana.getChildren().get(i).getStyleClass().contains("emptyMana"))
                enemyMana.getChildren().get(i).getStyleClass().remove("emptyMana");
//            enemyMana.getChildren().get(i).getStyleClass().add("mana");
            ((ImageView)enemyMana.getChildren().get(i)).setImage(new Image("resources/ui/icon_mana.png"));
        }
        for (int i = enemyManaNumber ; i < 9 ; i++){
//            enemyMana.getChildren().get(i).getStyleClass().add("emptyMana");
            ((ImageView)enemyMana.getChildren().get(i)).setImage(new Image("resources/ui/icon_mana_inactive.png"));
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
