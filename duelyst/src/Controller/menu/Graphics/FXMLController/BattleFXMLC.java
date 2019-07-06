package Controller.menu.Graphics.FXMLController;

import Controller.menu.Battle;
import Controller.menu.Graphics.GraphicsControls;
import Controller.menu.MainMenu;
import Model.Graphics.SpriteAnimation;
import Model.Map.Cell;
import Model.account.Hand;
import Model.account.player.GGI;
import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.hermione.Minion;
import Model.card.spell.Spell;
import View.MenuHandler;
import exeption.*;
import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;

public class BattleFXMLC extends FXMLController {

    @FXML
    public Button endTurn;
    public Button graveYard;
    public Button menuButton;
    public Button showCollectableButton;
    public GridPane playerMana;
    public GridPane enemyMana;
    public GridPane handInfo;
    public GridPane showCollectable;
    public ImageView firstPlayer;
    public ImageView secondPlayer;
    public ImageView nextCardOnHand;
    public ImageView ownSP;
    public ImageView opponentSP;
    public Label ownPlayerInfo;
    public Label opponentPlayerInfo;
    public Label errorLable;
    public TextField nextCardOnHandInfo;
    public Label turn;
    public ImageView firstPlayerTurn;
    public ImageView secondPlayerTurn;
    public HBox mapBox;
    public AnchorPane frame;
    public GridPane map;
    public GridPane handFrame;
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
        GraphicsControls.setButtonStyle("showCollectableButton", showCollectableButton);
    }

    @Override
    public void buildScene() {
        super.buildScene();
       endTurn.setOnMousePressed(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
               if(Battle.getMenu().getPlayer().getGI() instanceof GGI) {
                   try {
                       Battle.getMenu().endTurn();
                       updateScene();
                   } catch (HandFullException | DeckIsEmptyException e) {
                       e.printStackTrace();
                   }
               }
           }
       });
        menuButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(Battle.getMenu().getPlayer().getGI() instanceof GGI) {
                    MenuHandler.setCurrentMenu(MainMenu.getMenu());
                    //todo: end game bezan
                }
            }
        });

        graveYard.setOnMousePressed(e ->{
            if(Battle.getMenu().getPlayer().getGI() instanceof GGI) {
                GraveYardFXMLC.makeNewScene(menu.getAccount());
            }
        });

        showCollectableButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(Battle.getMenu().getPlayer().getGI() instanceof GGI) {
                    showCollectable.getStyleClass().add("showCollectableEntered");
                    Battle.getMenu().showCollectable();
                }
            }
        });
        showCollectable.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (Node child : showCollectable.getChildren()) {
                    Label itemLable = (Label)child;
                    itemLable.setOnDragDetected(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            Dragboard db = itemLable.startDragAndDrop(TransferMode.ANY);
                            ClipboardContent content = new ClipboardContent();
                            content.putString(itemLable.getText());
                            db.setContent(content);
                            event.consume();
                        }
                    });
                }
            }
        });
        showCollectable.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showCollectable.getStyleClass().remove("showCollectableEntered");
                showCollectable.getChildren().clear();
            }
        });
    }

    @Override
    public void enterScene() {
        super.enterScene();
        try {
            firstPlayer.setImage(new Image(Battle.getMenu().getPlayer().getUser().getAvatar()));
            secondPlayer.setImage(new Image(Battle.getMenu().getEnemyPlayer().getUser().getAvatar()));
        }catch (Exception e){
            System.err.println("couldn't load the images for players !");
        }
        specialPowerSource();
        updateScene();
    }

    @Override
    public void updateScene() {
        super.updateScene();
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                updateTurn();
                updateHand();
                moveStart();
                attackDrag();
                attackDrop();
                updateMana();
                updateMap();
                updateInfo();
            }
        });
    }

    private void updateTurn() {
        if(Battle.getMenu().getTurn() % 2 == 0){
            firstPlayerTurn.setImage(new Image("resources/profile_icons/borders/gold@2x.png"));
            secondPlayerTurn.setImage(new Image("resources/profile_icons/borders/silver@2x.png"));
        }
        else {
            secondPlayerTurn.setImage(new Image("resources/profile_icons/borders/gold@2x.png"));
            firstPlayerTurn.setImage(new Image("resources/profile_icons/borders/silver@2x.png"));
        }
    }

    private void handDrag() {
        Card[] handCards = Battle.getMenu().getPlayer().getHand().getCards();
        for (int i = 0 ; i < handCards.length ; i++){
            Card card = handCards[i];
            ImageView cardView = (ImageView) handFrame.getChildren().get(i);
            System.err.println(cardView.getId());
            cardView.setOnDragDetected(e -> {
                Dragboard db = cardView.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                if(card instanceof Hermione)
                    content.putImage(new Image(((Hermione)card).getGraphics().getUnitGifs()));
                else if(card instanceof Spell)
                    content.putImage(new Image(((Spell)card).getSpellGraphics().getIconGif()));
                db.setContent(content);
                e.consume();
            });
        }
    }
    private void drop() {
        for (int i = 0 ; i < 9 ; i++) {
            for (int j = 0 ; j < 5 ; j++) {
                try {
                    Cell cell = Battle.getMenu().getMap().getCell(i, j);
                    ImageView cellView = getCell(i, j);
                    cellView.setOnDragOver(new EventHandler<DragEvent>() {
                        @Override
                        public void handle(DragEvent event) {
                            ImageView source = (ImageView) event.getGestureSource();
                            if(source.getId()!= null && (source.getId().equals("ownSP") || source.getId().equals("opponentSP"))){
                                event.acceptTransferModes(TransferMode.ANY);
                            }
                            else if (handFrame.getChildren().contains(source)) {
                                if (!cell.hasItem() && !cell.hasFlag()) {
                                    event.acceptTransferModes(TransferMode.ANY);
                                }
                            }
                            event.consume();
                        }
                    });
                    int finalI = i;
                    int finalJ = j;
                    cellView.setOnDragDropped(new EventHandler<DragEvent>() {
                        @Override
                        public void handle(DragEvent event) {
                            ImageView source = (ImageView) event.getGestureSource();
                            if (handFrame.getChildren().contains(source)) {
                                Card card = getCardOnHand(GridPane.getColumnIndex(source) - 1);
                                if (card != null) {
                                    try {
                                        Battle.getMenu().insert(card.getCardID(), finalI, finalJ);
                                        updateScene();
                                    } catch (DestinationIsFullException e) {
                                        errorLable.setText("there's already a card there!");
                                    } catch (NotEnoughManaException e) {
                                        errorLable.setText("Lets collect some mana first!");
                                    } catch (InvalidCardException | InvalidCellException e) {
                                        e.printStackTrace();
                                    } catch (HandFullException e) {
                                        e.printStackTrace();
                                    } catch (DeckIsEmptyException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            else if(source.getId()!= null && source.getId().equals("ownSP")){
                                if(Battle.getMenu().getPlayer().equals(Battle.getMenu().getOwnPLayer())) {
                                    try {
                                        Battle.getMenu().useSpecialPower(finalI, finalJ);
                                        updateScene();
                                    } catch (CantSpecialPowerCooldownException e) {
                                        errorLable.setText("coolDown exeption");
                                    } catch (InvalidCardException e) {
                                       errorLable.setText("wrong card");
                                    } catch (InvalidCellException e) {
                                        errorLable.setText("wrong cell");
                                    }
                                }
                            }
                            else if(source.getId()!= null && source.getId().equals("opponentSP")){
                                if(Battle.getMenu().getPlayer().equals(Battle.getMenu().getOpponentPlayer())) {
                                    updateScene();
                                }
                            }
                            event.consume();
                            updateScene();
                        }
                    });
                } catch (InvalidCellException e) { e.printStackTrace(); }
            }
        }
    }
    private void updateHand(){
        Card[] handCards = Battle.getMenu().getPlayer().getHand().getCards();
        for (int i = 0 ; i < handCards.length ;  i++) {
            ImageView card = (ImageView) handFrame.getChildren().get(i);
            Label mana = (Label) handManaFrame.getChildren().get(i + 5);
            if(handCards[i] instanceof Minion) {
                card.setImage(new Image(((Minion)handCards[i]).getGraphics().getIcon()));
            }
            else if(handCards[i] instanceof Spell) {
                card.setImage(new Image(((Spell)handCards[i]).getSpellGraphics().getIcon()));
            }
            final Animation animation = new SpriteAnimation(
                    card,
                    Duration.millis(2000),
                    5, 1,
                    100, 0,
                    50, 50
            );
            animation.setCycleCount(Animation.INDEFINITE);
            animation.play();
            //mana
            mana.setText(Integer.toString(handCards[i].getManaPoint()));
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
        else {
            nextCardOnHand.setImage(null);
        }
        handOnHover();
        handDrag();
        drop();
    }
    private void handOnHover(){
        Card[] handCards = Battle.getMenu().getPlayer().getHand().getCards();
        for (int i = 0 ; i <handCards.length ; i++) {
            ImageView card = (ImageView) handFrame.getChildren().get(i);
            int finalI = i;
            card.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    TextField info = (TextField) handInfo.getChildren().get(finalI);
                    Card card = getCardOnHand(finalI);
                    if(card != null) {
                        info.getStyleClass().add("infoEntered");
                        info.setText(card.getName());
                    }
                    else {
                        info.getStyleClass().remove("infoEntered");
                        info.setText(null);
                    }
                }
            });
            card.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    TextField info = (TextField) handInfo.getChildren().get(finalI);
                    info.getStyleClass().remove("infoEntered");
                    info.setText(null);
                }
            });
        }
        nextCardOnHandInfo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TextField info = nextCardOnHandInfo;
                Card nextCard = Battle.getMenu().getPlayer().getHand().getNextCard();
                if(nextCard != null){
                    info.getStyleClass().add("infoEntered");
                    info.setText(nextCard.getName());
                }
                else {
                    info.getStyleClass().remove("infoEntered");
                    info.setText(null);
                }
            }
        });
        nextCardOnHandInfo.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                nextCardOnHandInfo.getStyleClass().remove("infoEntered");
                nextCardOnHandInfo.setText(null);
            }
        });


    }
    private Card getCardOnHand(int index){
        return Battle.getMenu().getPlayer().getHand().getCards()[index];
    }


    private void moveStart(){
        for (int i = 0 ; i < 9 ; i++){
            for (int j = 0 ; j < 5 ; j++){
                try {
                    Cell cell = Battle.getMenu().getMap().getCell(i, j);
                    ImageView cellView = getCell(i, j);
                    if (cell.getCardOnCell() != null) {
                    int finalI = i;
                    int finalJ = j;
                    cellView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                try {
                                    Battle.getMenu().select(cell.getCardOnCell().getCardID());
                                    getRectangle(finalI, finalJ).getStyleClass().add("cellSelected");
                                    moveEnd(finalI, finalJ);
                                    removeEvent(this);
                                } catch (InvalidCardException | InvalidItemException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch(InvalidCellException e){ e.printStackTrace(); }
            }
        }
    }
    private void moveEnd(int iStart, int jStart){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                ImageView cell = getCell(i, j);
                int finalI = i;
                int finalJ = j;
                EventHandler<MouseEvent> click = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            Battle.getMenu().move(finalI, finalJ);
                            getRectangle(iStart, jStart).getStyleClass().remove("cellSelected");
                            removeEvent(this);
                            moveStart();
                            updateScene();
                        } catch (Exception e) {
                        }
                    }
                };
                cell.setOnMouseClicked(click);
            }
        }
    }
    private void removeEvent(EventHandler click) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                ImageView cell = getCell(i , j);
                cell.removeEventHandler(MouseEvent.MOUSE_CLICKED, click);
            }
        }
    }

    private void attackDrag(){
        for (int i = 0 ; i < 9 ; i++){
            for ( int j = 0 ; j < 5 ; j++){
                try {
                    if(Battle.getMenu().getMap().getCell(i, j).getCardOnCell() != null){
                        ImageView cell = getCell(i, j);
                        int finalI = i;
                        int finalJ = j;
                        cell.setOnDragDetected(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent e) {
                                try {
                                Dragboard db = cell.startDragAndDrop(TransferMode.ANY);
                                ClipboardContent content = new ClipboardContent();
                                content.putImage(new Image(Battle.getMenu().getMap().getCell(finalI, finalJ).getCardOnCell().getGraphics().getUnitGifs()));
                                db.setContent(content);
                                } catch (InvalidCellException e1) {}
                            }
                        });
                    }
                } catch (InvalidCellException e) { e.printStackTrace(); }
            }
        }
    }
    private void attackDrop(){
        for (int i = 0 ; i < 9 ; i++){
            for( int j = 0 ; j < 5 ; j++) {
                try {
                    if(Battle.getMenu().getMap().getCell(i, j).getCardOnCell() != null){
                        ImageView cell = getCell(i ,j);
                        cell.setOnDragOver(new EventHandler<DragEvent>() {
                            @Override
                            public void handle(DragEvent event) {
                                ImageView source = (ImageView) event.getGestureSource();
                                if(map.getChildren().contains(source)) {
                                    event.acceptTransferModes(TransferMode.ANY);
                                }
                                event.consume();
                            }
                        });
                        int finalI = i;
                        int finalJ = j;
                        cell.setOnDragDropped(new EventHandler<DragEvent>() {
                            @Override
                            public void handle(DragEvent event) {
                                try {
                                    ImageView source = (ImageView) event.getGestureSource();
                                    Hermione attacker = Battle.getMenu().getMap().getCell(GridPane.getColumnIndex(source), GridPane.getRowIndex(source))
                                        .getCardOnCell();
                                    Battle.getMenu().select(attacker.getCardID());
                                    Battle.getMenu().attack(Battle.getMenu().getMap().getCell(finalI, finalJ).getCardOnCell().getCardID());
                                    event.consume();
                                    updateScene();
                                } catch (InvalidCellException | InvalidCardException | InvalidItemException | NoCardHasBeenSelectedException e) {e.printStackTrace(); }
                                catch (DestinationOutOfreachException e) {
                                    errorLable.setText("destination Out of Reach");
                                    e.printStackTrace();
                                } catch (CantAttackException e) {
                                    errorLable.setText("cant attack");
                                }
                            }
                        });
                    }
                } catch (InvalidCellException ignored) {}
            }
        }
    }

    private void updateInfo(){
        ownPlayerInfo.setText(Battle.getMenu().getOwnPLayer().getDeck().getHero().getName() + "\n"
                + "HealthPoint : "+ Battle.getMenu().getOwnPLayer().getDeck().getHero().getHealthPoint());
        opponentPlayerInfo.setText(Battle.getMenu().getOpponentPlayer().getDeck().getHero().getName() + "\n"
                + "HealthPoint : "+ Battle.getMenu().getOpponentPlayer().getDeck().getHero().getHealthPoint());
    }
    private void updateMana(){
        String manaURL = "resources/ui/icon_mana.png";
        String inactiveManaURL = "resources/ui/icon_mana_inactive.png";
        int mana = Battle.getMenu().getOwnPLayer().getMana();
        for (int i = 0 ; i < mana ; i++){
            ((ImageView)playerMana.getChildren().get(i)).setImage(new Image(manaURL));
        }
        for (int i = mana ; i < 9 ; i++){
            ((ImageView)playerMana.getChildren().get(i)).setImage(new Image(inactiveManaURL));
        }
        int enemyManaNumber = Battle.getMenu().getOpponentPlayer().getMana();
        for (int i = 0 ; i < enemyManaNumber ; i++){
            ((ImageView)enemyMana.getChildren().get(i)).setImage(new Image(manaURL));
        }
        for (int i = enemyManaNumber ; i < 9 ; i++){
            ((ImageView)enemyMana.getChildren().get(i)).setImage(new Image(inactiveManaURL));
        }
    }
    private void updateMap(){
        try {
            for (int i = 0 ; i < 9 ; i++){
                for (int j = 0 ; j < 5 ; j++){
                    Cell cell = Battle.getMenu().getMap().getCell(i, j);
                    if(cell.getCardOnCell() == null){
                        getCell(i, j).setImage(null);
                    }
                    if(cell.hasItem()){
                        getCell(i , j).setImage(new Image(Battle.getMenu().getMap().getCell(i, j).getCollectable().getItemGraphics().getAvatar()));
                    }
                    if(cell.hasFlag()){
                        getCell(i, j).setImage(new Image("resources/ui/collection_card_rarity_rare@2x.png"));
                    }
                }
            }
        } catch (InvalidCellException e) { e.printStackTrace();}
    }

    private void specialPowerSource(){
        ownSP.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = ownSP.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(ownSP.getImage());
                db.setContent(content);
                event.consume();
            }
        });
        opponentSP.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = opponentSP.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(opponentSP.getImage());
                db.setContent(content);
                event.consume();
            }
        });
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

    private boolean isEmpty(ImageView imageView){
        try {
            Cell cell = Battle.getMenu().getMap().getCell(GridPane.getColumnIndex(imageView), GridPane.getRowIndex(imageView));
            if(cell.getCardOnCell() == null) return true;
        } catch (InvalidCellException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

    public void removeFromScene(Node... nodes) {
        this.frame.getChildren().removeAll(nodes);
    }

    public double getMapX(){
        return mapBox.getLayoutX() + 70;
    }
    public double getMapY(){
        return mapBox.getLayoutY();
    }
    public double getMapWidth() {
        return map.getWidth();
    }
    public double getCellWidth(){
        return getMapWidth()/9;
    }
    public double getMapHeight(){
        return map.getHeight();
    }
    public double getCellHeight(){
        return getMapHeight()/5;
    }
    public double getX(int x){
        return getMapX() + ( x + .5 ) * getCellWidth();
    }
    public double getY(int y){
        return getMapY() + (y + .5) * getCellHeight();
    }
}
