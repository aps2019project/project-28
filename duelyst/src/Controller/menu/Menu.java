package Controller.menu;

import View.Listeners.OnMenuClickedListener;
import Model.account.Account;
import exeption.InvalidSubMenuException;

import java.util.ArrayList;

public abstract class Menu {

    protected Account account;
    private String name;
    private Menu parentMenu=null;
    private ArrayList<Menu> subMenus;
    private ArrayList<OnMenuClickedListener> menuPresenters;
    private ArrayList<String> patterns;


    public Menu(String name) {
        this.name = name;
        this.menuPresenters=new ArrayList<>();
        this.subMenus = new ArrayList<>();
        this.patterns = new ArrayList<>();
    }

    public Menu enter(Menu subMenu){
        if(subMenu.init(this)==false)return this;
        return subMenu;
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
