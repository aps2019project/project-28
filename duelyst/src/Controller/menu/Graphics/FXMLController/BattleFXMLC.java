package Controller.menu.Graphics.FXMLController;

import Controller.menu.Battle;
import Controller.menu.Graphics.GraphicsControls;
import Controller.menu.SignInMenu;
import Model.Graphics.SpriteAnimation;
import Model.Map.Cell;
import Model.account.Account;
import Model.account.player.GGI;
import Model.card.Card;
import Model.card.hermione.Hermione;
import Model.card.hermione.Minion;
import Model.card.spell.Spell;
import Model.item.Collectable;
import Model.item.Item;
import View.MenuHandler;
import exeption.*;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.nio.file.Paths;

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
    public ImageView firstPlayerTurn;
    public ImageView secondPlayerTurn;
    public HBox mapBox;
    public AnchorPane frame;
    public GridPane map;
    public GridPane handFrame;
    public GridPane handManaFrame;
    private MediaPlayer mediaPlayer ;

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
       endTurn.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
           System.out.println("Battle.getMenu().getPlayer().getGI() = " + Battle.getMenu().getPlayer().getGI());
           if(Battle.getMenu().getPlayer().getGI() instanceof GGI) {
               System.err.println("end turn 1");
               try {
                   Battle.getMenu().endTurn();
                   System.err.println("end turn 2");
                   updateScene();
               } catch (HandFullException | DeckIsEmptyException ex) {
                   ex.printStackTrace();
               }
           }
           }});
        menuButton.setOnAction(e -> {
            if(Battle.getMenu().getPlayer().getGI() instanceof GGI) {
//                    MenuHandler.setCurrentMenu(MainMenu.getMenu());
//                    //todo: end game bezan
                ((SignInMenuFXMLC)SignInMenu.getMenu().getGraphic().getController()).playMusic(true);
                mediaPlayer.pause();
            }
        });

        graveYard.setOnAction(e ->{
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
                    updateScene();
                }
            }
        });

        showCollectable.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showCollectable.getStyleClass().remove("showCollectableEntered");
                showCollectable.getChildren().clear();
                updateScene();
            }
        });

        try {
            Media music = new Media(Paths.get("src/resources/music/music_battlemap_risensun2.m4a").toUri().toString());
            mediaPlayer = new MediaPlayer(music);
            mediaPlayer.setCycleCount(-1);
            MediaView mediaView = new MediaView(mediaPlayer);
            frame.getChildren().add(mediaView);
        }catch (Exception ignored){
            System.err.println("couldn't load the music file");
        }

    }

    @Override
    public void enterScene() {
        super.enterScene();
        try {
            mediaPlayer.play();
            ((SignInMenuFXMLC)SignInMenu.getMenu().getGraphic().getController()).playMusic(false);
            firstPlayer.setImage(new Image(Battle.getMenu().getPlayer().getUser().getAvatar()));
            secondPlayer.setImage(new Image(Battle.getMenu().getEnemyPlayer().getUser().getAvatar()));
        }catch (Exception e){
            System.err.println("couldn't load the images for players !");
        }
        specialPowerDrag();
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
                updateInfo();
                updateMana();
                updateMap();
                if(Battle.getMenu().getTurn() % 2 == 0) {
                    handDrag();
                    collectableDrag();
                    attackDrag();
                    moveStart();
                    drop();
                }
            }
        });
    }

    public void finish(){
        ImageView endGameBack = new ImageView("resources/maps/battlemap1_middleground.png");
        Label endGame = new Label();
        endGame.setFont(Font.font("Didot", 40.0));
        endGame.setTextAlignment(TextAlignment.CENTER);
//        endGame.getStyleClass().add("endGame");
        endGameBack.setFitWidth(frame.getMinWidth());
        endGameBack.setFitHeight(frame.getMinHeight());
        endGame.setMinWidth(frame.getMinWidth());
        endGame.setMinHeight(frame.getMinHeight());
        endGame.setLayoutX(frame.getMinWidth()/3);
        endGame.setLayoutY(frame.getMinHeight()/1.5);
        if(Battle.getMenu().getAccount().equals(Battle.getMenu().winner)){
            endGame.setTextFill(Color.rgb(0, 255, 200));
            endGame.setText("Congrats! YOU WON!");
        }
        else {
            endGame.setTextFill(Color.rgb(255, 100, 61 ));
            endGame.setText("Ooops! YOU LOST!");
        }
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
        pauseTransition.play();
        pauseTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //todo exit
            }
        });
    }

    private void updateTurn() {
        if (Battle.getMenu().getTurn() % 2 == 0) {
                firstPlayerTurn.setImage(new Image("resources/profile_icons/borders/gold@2x.png"));
                secondPlayerTurn.setImage(new Image("resources/profile_icons/borders/silver@2x.png"));
            } else {
                secondPlayerTurn.setImage(new Image("resources/profile_icons/borders/gold@2x.png"));
                firstPlayerTurn.setImage(new Image("resources/profile_icons/borders/silver@2x.png"));
            }
    }


    private void updateHand(){
        Card[] handCards = Battle.getMenu().getPlayer().getHand().getCards();
        for (int i = 0 ; i < handCards.length ;  i++) {
            ImageView card = (ImageView) handFrame.getChildren().get(i);
            Label mana = (Label) handManaFrame.getChildren().get(i + 5);
            if(handCards[i] != null) {
                if (handCards[i] instanceof Minion) {
                    card.setImage(new Image(((Minion) handCards[i]).getGraphics().getIcon()));
                } else if (handCards[i] instanceof Spell) {
                    card.setImage(new Image(((Spell) handCards[i]).getSpellGraphics().getIcon()));
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
            else {
                card.setImage(null);
                mana.setText(null);
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
        else {
            nextCardOnHand.setImage(null);
        }
        handOnHover();
    }
    private void handOnHover(){
        Card[] handCards = Battle.getMenu().getPlayer().getHand().getCards();
        for (int i = 0 ; i <handCards.length ; i++) {
            if(handCards[i] != null) {
                ImageView card = (ImageView) handFrame.getChildren().get(i);
                int finalI = i;
                card.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        TextField info = (TextField) handInfo.getChildren().get(finalI);
                        Card card = handCards[finalI];
                        if (card != null) {
                            info.getStyleClass().add("infoEntered");
                            info.setText(card.getName());
                        } else {
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
        }
        if(Battle.getMenu().getPlayer().getHand().getNextCard() != null) {
            nextCardOnHandInfo.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    TextField info = nextCardOnHandInfo;
                    Card nextCard = Battle.getMenu().getPlayer().getHand().getNextCard();
                    if (nextCard != null) {
                        info.getStyleClass().add("infoEntered");
                        info.setText(nextCard.getName());
                    } else {
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
                                    Battle.getMenu().select(cell.getCardOnCell().getID());
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
                            error("cant move!");
                            updateScene();
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
                    ImageView cellView = getCell(i, j);
                    Rectangle cellRect = getRectangle(i, j);
                    if(cell.getCardOnCell() == null){
                        cellView.setImage(null);
                    }
                    if(cell.hasItem()){
                        cellView.setImage(new Image(Battle.getMenu().getMap().getCell(i, j).getCollectable().getItemGraphics().getAvatar()));
                    }
                    if(cell.hasFlag()){
                        cellView.setImage(new Image("resources/ui/collection_card_rarity_rare@2x.png"));
                    }
                    if(cell.getCellAffect().size() > 0){
                        cellView.setImage(new Image("resources/ui/icon_heal.png"));
                    }
                    cellRect.getStyleClass().remove("cellSelected");
                    cellRect.getStyleClass().remove("specialPowerInserted");
                }
            }
        } catch (InvalidCellException e) { e.printStackTrace();}
    }


    private void handDrag() {
        Card[] handCards = Battle.getMenu().getPlayer().getHand().getCards();
        for (int i = 0 ; i < handCards.length ; i++){
            Card card = handCards[i];
            ImageView cardView = (ImageView) handFrame.getChildren().get(i);
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
    private void collectableDrag(){
        if(showCollectable.getStyleClass().contains("showCollectableEntered")) {
            for (Node item : showCollectable.getChildren()) {
                item.setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Dragboard db = item.startDragAndDrop(TransferMode.ANY);
                        ClipboardContent content = new ClipboardContent();
                        content.putImage(new Image("resources/icons/artifact_f1_skywindglaives_single.png"));
                        db.setContent(content);
                        event.consume();
                    }
                });
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
    private void specialPowerDrag(){
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
                            Node source = (Node) event.getGestureSource();
                            if (source.getId() != null && source.getId().equals("ownSP")) {
                                event.acceptTransferModes(TransferMode.ANY);
                            } else if (handFrame.getChildren().contains(source)) {
                                if (!cell.hasItem() && !cell.hasFlag()) {
                                    event.acceptTransferModes(TransferMode.ANY);
                                }
                            } else if (map.getChildren().contains(source) || showCollectable.getChildren().contains(source)) {
                                event.acceptTransferModes(TransferMode.ANY);
                            }
                            event.consume();
                        }
                    });
                    int finalI = i;
                    int finalJ = j;
                    cellView.setOnDragDropped(new EventHandler<DragEvent>() {
                        @Override
                        public void handle(DragEvent event) {
                            Node source = (Node) event.getGestureSource();
                            if (handFrame.getChildren().contains(source)) {
                                Card card = getCardOnHand(handFrame.getChildren().indexOf(source));
                                if (card != null) {
                                    try {
                                        Battle.getMenu().insert(card.getID(), finalI, finalJ);
                                        updateScene();
                                    } catch (DestinationIsFullException e) {
                                        error("there's already a card there!");
                                    } catch (NotEnoughManaException e) {
                                        error("Lets collect some mana first!");
                                    } catch (InvalidCardException | InvalidCellException e) {
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
                                        error("coolDown exeption");
                                    } catch (InvalidCardException e) {
                                        error("wrong card");
                                    } catch (InvalidCellException e) {
                                        error("wrong cell");
                                    }
                                }
                            }
                            else if(map.getChildren().contains(source)){
                                try {
                                    Hermione attacker = Battle.getMenu().getMap().getCell(GridPane.getColumnIndex(source), GridPane.getRowIndex(source))
                                            .getCardOnCell();
                                    Battle.getMenu().select(attacker.getID());
                                    Battle.getMenu().attack(Battle.getMenu().getMap().getCell(finalI, finalJ).getCardOnCell().getID());
                                } catch (CantAttackException e) {
                                    error("cant attack");
                                } catch (InvalidItemException | InvalidCellException | InvalidCardException | NoCardHasBeenSelectedException e) {
                                    e.printStackTrace();
                                } catch (DestinationOutOfreachException e) {
                                    error("destination out of reach!");
                                }
                            }
                            else if(showCollectable.getChildren().contains(source)){
                                try {
                                    Label itemLable = (Label) source;
                                    Item item = Collectable.getItem(itemLable.getText());
                                    Battle.getMenu().select(item.getID());
                                    Battle.getMenu().useItem(finalI, finalJ);
                                } catch (InvalidItemException | NoItemHasBeenSelectedException | InvalidCardException e) {
                                    e.printStackTrace();
                                }catch (InvalidCellException e){
                                    error("the cell is not valid");
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

    private void error(String error){
        errorLable.setText(error);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), errorLable);
        fadeTransition.play();
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                errorLable.setText(null);
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
    public void addToScene(Node... nodes) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            frame.getChildren().addAll(nodes);
            }
        });
    }
    public void removeFromScene(Node... nodes) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                frame.getChildren().removeAll(nodes);
            }
        });
    }
    private double getMapX(){
        return mapBox.getLayoutX() + 70;
    }
    private double getMapY(){
        return mapBox.getLayoutY();
    }
    private double getMapWidth() {
        return map.getWidth();
    }
    public double getCellWidth(){
        return getMapWidth()/9;
    }
    private double getMapHeight(){
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
