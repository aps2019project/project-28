package Controller.menu;

import Controller.menu.Graphics.MenuGraphics;
import View.Listeners.OnMenuClickedListener;
import Model.account.Account;
import View.MenuHandler;
import exeption.InvalidSubMenuException;
import java.util.ArrayList;


public abstract class Menu {

    protected Account account;
    private String name;
    private Menu parentMenu=null;
    private ArrayList<Menu> subMenus;
    private ArrayList<OnMenuClickedListener> menuPresenters;
    private ArrayList<String> patterns;
    private MenuGraphics graphic=new MenuGraphics(this);


    public Menu(String name) {
        this.name = name;
        this.menuPresenters=new ArrayList<>();
        this.subMenus = new ArrayList<>();
        this.patterns = new ArrayList<>();

    }

    public Menu enter(Menu subMenu){
        if(!subMenu.init(this)) return this;
        try {
            subMenu.getGraphic().enter();
        }catch (Exception e){
            System.err.println("graphic error---------------------");
            e.printStackTrace();
        }
        return subMenu;
    }

    public Menu enter(){
        // TODO: 6/15/19 handle graphics
        try {
            this.getGraphic().enter();
        }catch (Exception e){
            System.err.println("graphic error---------------------");
            e.printStackTrace();
        }
        return this;
    }

    public boolean init(Menu parentMenu) {
        if(this.parentMenu==null)this.setParentMenu(parentMenu);
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
            if (subMenu.name.toLowerCase().trim().equals(name.toLowerCase())) return subMenu;
        }
        throw new InvalidSubMenuException();

    }
    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public Account getAccount() {
        if (this.account == null) this.account = MenuHandler.getAccount() ;
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

    public void exit() {
        MenuHandler.enterMenu(this.parentMenu);
    }

    public MenuGraphics getGraphic() {
        return graphic;
    }

    public void setGraphic(MenuGraphics graphic) {
        this.graphic = graphic;
    }

}
