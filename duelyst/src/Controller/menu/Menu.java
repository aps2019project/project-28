package Controller.menu;

import View.GraphicInput;
import View.Listeners.OnMenuClickedListener;
import Model.account.Account;
import exeption.InvalidSubMenuException;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import stuff.Resources;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public abstract class Menu {

    protected Account account;
    private String name;
    private Menu parentMenu=null;
    private ArrayList<Menu> subMenus;
    private ArrayList<OnMenuClickedListener> menuPresenters;
    private ArrayList<String> patterns;
    protected Stage stage ;
    protected Scene scene ;
    protected Parent root ;
    protected String rootPath ;
    protected Rectangle2D bounds ;
    protected String mousePath = Resources.mouse_auto.getPath();


    private void goToScene(Stage stage , Rectangle2D bounds){
        if (this.stage == null) this.stage = stage ;
        if (this.bounds == null) this.bounds = bounds ;
        if (scene == null) {
            try {
                System.out.println(rootPath);
                root = FXMLLoader.load(getClass().getResource(rootPath));
            }catch (IOException ignored) {
                System.err.println("couldn't load the fxml file");
            }
            scene = new Scene(root, bounds.getWidth(), bounds.getHeight());
        }
        buildScene();
        stage.setScene(scene);
    }

    protected void buildScene(){
        //TODO music ! seriously i have busted my ass trying to make it happen but it just doesn't want to happen ! -_-
        scene.setOnMouseEntered(e -> scene.setCursor(new ImageCursor(new Image(mousePath))));
    }


    public Menu(String name) {
        this.name = name;
        this.menuPresenters=new ArrayList<>();
        this.subMenus = new ArrayList<>();
        this.patterns = new ArrayList<>();
    }

    public Menu enter(Menu subMenu){
        if(!subMenu.init(this))return this;
        stage = GraphicInput.getGraphicInput().getStage() ;
        bounds = GraphicInput.getGraphicInput().getPrimaryScreenBounds() ;
        subMenu.goToScene(stage , bounds);
        return subMenu;
    }

    public Menu enter(){
//        if(!subMenu.init(this))return this;
        stage = GraphicInput.getGraphicInput().getStage() ;
        bounds = GraphicInput.getGraphicInput().getPrimaryScreenBounds() ;
        goToScene(stage , bounds);
        return this;
    }

    public boolean init(Menu parentMenu) {
        this.setParentMenu(parentMenu);
        this.setAccount(parentMenu.account);
        return true;
    }
    public void addPattern(String pattern) {
        this.patterns.add(pattern);
    }

    public void addSubMenu(Menu subMenu) {
        this.subMenus.add(subMenu);
    }

    public void addMenuClickListener(OnMenuClickedListener presenter) {
        this.menuPresenters.add(presenter);
    }

    public boolean allowsCommand(String command) {
        for (String pattern : this.patterns) {
            if (command.matches(pattern)) return true;
        }
        return false;
    }

    public void help(){
        System.out.println("HELP:");
        System.out.println("these are the commands you can use");
        System.out.println("1)exit     2)show menu     3)enter[MenuName]");
    }

    public void showMenu() {
        for (OnMenuClickedListener presenter : this.menuPresenters) {
            presenter.show(this);
        }
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public Menu getMenuFromSubMenus(String name) throws InvalidSubMenuException {
        for (Menu subMenu : this.subMenus) {
            if (subMenu.name.toLowerCase().trim().equals(name)) return subMenu;
        }
        throw new InvalidSubMenuException();

    }
    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ArrayList<Menu> getSubMenus() {
        return subMenus;
    }

    public String getName() {
        return name;
    }

    public Menu exit() {
        return parentMenu;
    }
}
