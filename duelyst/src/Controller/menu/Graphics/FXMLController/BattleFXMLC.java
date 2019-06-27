package Controller.menu.Graphics.FXMLController;

import Controller.menu.Battle;
import Controller.menu.Graphics.GraphicsControls;
import Controller.menu.GraveYardMenu;
import Controller.menu.MainMenu;
import Model.Graphics.SpriteAnimation;
import Model.Map.Cell;
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
import javafx.scene.control.TextField;
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
        GraphicsControls.setButtonStyle("showCollectableButton", showCollectableButton);
    }

    @Override
    public void buildScene() {
        super.buildScene();
       endTurn.setOnMousePressed(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
               try {
                   Battle.getMenu().endTurn();
                   updateScene();
               } catch (HandFullException | DeckIsEmptyException e) { e.printStackTrace(); }
           }
       });
        menuButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MenuHandler.setCurrentMenu(MainMenu.getMenu());
                //todo: end game bezan
            }
        });

        graveYard.setOnMousePressed(e -> GraveYardFXMLC.makeNewScene(menu.getAccount()));

        showCollectableButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showCollectable.getStyleClass().add("showCollectableEntered");
                Battle.getMenu().showCollectable();
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
        updateScene();
    }

    @Override
    public void updateScene() {
        super.updateScene();
        moveTargets();
        moveAndAttackSource();
        attackTargets();
        updateMana();
        updateHand();
        updateMap();
//        specialPowerSource();
//        specialPowerTarget();
        updateInfo();
    }

    private void moveTargets() {
        for (int i = 0 ; i < 9 ; i++) {
            for (int j = 0 ; j < 5 ; j++) {
                try {
                    Cell cell = Battle.getMenu().getMap().getCell(i, j);
                    ImageView cellView = getCell(i, j);
                    cellView.setOnDragOver(new EventHandler<DragEvent>() {
                        @Override
                        public void handle(DragEvent event) {
                            ImageView source = (ImageView) event.getGestureSource();
                            if (handFrame.getChildren().contains(source)) {
                                if (!cell.hasItem() && !cell.hasFlag()) {
                                    event.acceptTransferModes(TransferMode.ANY);
                                }
                            } else if (map.getChildren().contains(source)) {
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
                            ImageView source = (ImageView) event.getGestureSource();
                            if (handFrame.getChildren().contains(source)) {
                                Card card = getCardOnHand(GridPane.getColumnIndex(source));
                                if (card != null) {
                                    try {
                                        Battle.getMenu().insert(card.getCardID(), finalI, finalJ);
                                    } catch (DestinationIsFullException e) {
                                        errorLable.setText("there's already a card there!");
                                    } catch (NotEnoughManaException e) {
                                        errorLable.setText("Lets collect some mana first!");
                                    } catch (InvalidCardException | InvalidCellException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else if (map.getChildren().contains(source)) {
                                try {
                                    Hermione hermione = (Hermione) Battle.getMenu().getMap().getCell(GridPane.getColumnIndex(source), GridPane.getRowIndex(source)).getCardOnCell();
                                    Battle.getMenu().select(hermione);
                                        Battle.getMenu().move(finalI, finalJ);
                                } catch (InvalidCardException | InvalidCellException | NoCardHasBeenSelectedException | InvalidItemException e) {
                                    e.printStackTrace();
                                } catch (MoveTrunIsOverException e) {
                                    errorLable.setText("Your move turn is over");
                                } catch (DestinationIsFullException e) {
                                    errorLable.setText("There's already a card there!");
                                } catch (DestinationOutOfreachException e) {
                                    errorLable.setText("Too far darlin', too far!");
                                } catch (CardCantBeMovedException e) {
                                    errorLable.setText("card cant be moved!");
                                }
                            }
                            updateScene();
                        }
                    });
                } catch (InvalidCellException e) { e.printStackTrace(); }
            }
        }
    }
    private void attackTargets(){
        for (int i = 0 ; i < 9 ; i++){
            for( int j = 0 ; j < 5 ; j++){
                try {
                    Cell cell = Battle.getMenu().getMap().getCell(i, j);
                    if(cell.getCardOnCell() != null) {
                        ImageView attackTarget = getCell(i, j);
                        attackTarget.setOnDragOver(new EventHandler<DragEvent>() {
                            @Override
                            public void handle(DragEvent event) {
                                try {
                                    ImageView attackSource = (ImageView) event.getGestureSource();
                                    Cell attackerCell = Battle.getMenu().getMap().getCell(GridPane.getColumnIndex(attackSource), GridPane.getRowIndex(attackSource));
                                    if (attackerCell.getCardOnCell() != null) {
                                        event.acceptTransferModes(TransferMode.ANY);
                                    }
                                    event.consume();
                                } catch (InvalidCellException e) { e.printStackTrace(); }
                            }
                        });
                        int finalI = i;
                        int finalJ = j;
                        attackTarget.setOnDragDropped(new EventHandler<DragEvent>() {
                            @Override
                            public void handle(DragEvent event) {
                                try {
                                    ImageView attackSource = (ImageView) event.getGestureSource();
                                    Hermione attacker = Battle.getMenu().getMap().getCell(GridPane.getColumnIndex(attackSource), GridPane.getRowIndex(attackSource)).getCardOnCell();
                                    Hermione attacked = Battle.getMenu().getMap().getCell(finalI, finalJ).getCardOnCell();
                                    Battle.getMenu().select(attacker);
                                    Battle.getMenu().attack(attacked.getCardID());
                                } catch (InvalidCellException | CantAttackException |
                                        NoCardHasBeenSelectedException | InvalidCardException |
                                        DestinationOutOfreachException | InvalidItemException e) { e.printStackTrace(); }
                            }
                        });
                    }
                } catch (InvalidCellException e) { e.printStackTrace(); }
            }
        }
    }
    private void moveAndAttackSource() {
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
    }
    private void specialPowerSource(){
        ownSP.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ownSP.getStyleClass().add("specialPowerDragged");
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
                opponentSP.getStyleClass().add("specialPowerDragged");
                Dragboard db = opponentSP.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(opponentSP.getImage());
                db.setContent(content);
                event.consume();
            }
        });

        ownSP.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                ownSP.getStyleClass().remove("specialPowerDragged");
            }
        });

        opponentSP.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                opponentSP.getStyleClass().remove("specialPowerDragged");
                opponentSP.getStyleClass().add("specialPower");
            }
        });
    }
    private void specialPowerTarget(){
        for (int i = 0 ; i < 9 ; i++){
            for(int j = 0 ; j < 5 ; j++){
                ImageView cell = getCell(i, j);
                int finalI = i;
                int finalJ = j;
                cell.setOnDragOver(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        ImageView source = (ImageView) event.getGestureSource();
                        if (source.getId() != null && source.getId().compareTo("ownSP") == 0 || source.getId().compareTo("opponentSP") == 0) {
                            try {
                                Battle.getMenu().useSpecialPower(finalI, finalJ);
                                event.acceptTransferModes(TransferMode.ANY);
                            } catch (CantSpecialPowerCooldownException e) {
                                errorLable.setText("Still cooling down!");
                            } catch (InvalidCardException e) {
                                e.printStackTrace();
                            } catch (InvalidCellException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                cell.setOnDragDropped(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        ImageView source = (ImageView) event.getGestureSource();
                        source.getStyleClass().remove("specialPowerDragged");
                        source.getStyleClass().add("specialPower");
                    }
                });
            }
        }
    }
    private void updateInfo(){
        ownPlayerInfo.setText(Battle.getMenu().getOwnPLayer().getDeck().getHero().getName() + "\n"
                + "HealthPoint : "+ Battle.getMenu().getOwnPLayer().getDeck().getHero().getHealthPoint());
        opponentPlayerInfo.setText(Battle.getMenu().getOpponentPlayer().getDeck().getHero().getName() + "\n"
                + "HealthPoint : "+ Battle.getMenu().getOpponentPlayer().getDeck().getHero().getHealthPoint());
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
        handOnHover();
    }
    private void handOnHover(){
        for (Node child : handFrame.getChildren()) {
            ImageView image = (ImageView) child;
            image.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    int i = handFrame.getChildren().indexOf(child);
                    TextField info = (TextField) handInfo.getChildren().get(i);
                    Card card = getCardOnHand(i);
                    info.getStyleClass().add("infoEntered");
                    info.setText(card.getName());
                }
            });
            image.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    int i = handFrame.getChildren().indexOf(child);
                    TextField info = (TextField) handInfo.getChildren().get(i);
                    info.getStyleClass().remove("infoEntered");
                    info.setText("");
                }
            });
        }
        nextCardOnHand.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Card card = Battle.getMenu().getPlayer().getHand().getNextCard();
                nextCardOnHandInfo.getStyleClass().add("infoEntered");
                nextCardOnHandInfo.setText(card.getName());
            }
        });
        nextCardOnHand.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                nextCardOnHandInfo.getStyleClass().remove("infoEntered");
                nextCardOnHandInfo.setText("");
            }
        });
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
        } catch (InvalidCellException e) { e.printStackTrace(); }
    }

    private Card getCardOnHand(int index){
        return Battle.getMenu().getPlayer().getHand().getCards()[index];
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

    public double getMapWidth() {
        return map.getWidth();
    }

    public double getMapHeight(){
        return map.getHeight();
    }
}
