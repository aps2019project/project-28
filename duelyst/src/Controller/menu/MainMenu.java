package Controller.menu;


import javafx.scene.control.Button;

public class MainMenu extends Menu {
    private static MainMenu menu;

    private Button battleButton ;
    private Button collectionButton ;
    private Button shopButton ;
    private Button exitButton ;

    private  MainMenu(String name) {
        super(name);
        this.rootPath = "Scenes/MainMenu.fxml";
    }

    public static MainMenu getMenu(){
        if(MainMenu.menu==null){
            MainMenu.menu=new MainMenu("MainMenu");
        }
        return menu;
    }

    @Override
    protected void buildScene() {
        super.buildScene();

        scene.setUserAgentStylesheet("Controller/menu/Scenes/StyleSheets/MainMenu.css");
        battleButton = (Button)scene.lookup("#battleMenuButton");
        collectionButton = (Button)scene.lookup("#collectionMenuButton");
        shopButton = (Button)scene.lookup("#shopMenuButton");
        exitButton = (Button)scene.lookup("#exitMenuButton");
        exitButton.setStyle("-fx-text-fill: red;");
        GraphicsControlls.setButtonStyle("menu-button" , battleButton ,
                collectionButton , shopButton , exitButton );

    }


}
