package Controller.menu;

import Model.account.Collection;
import View.Listeners.OnMenuClickedListener;
import Model.account.Account;

import java.util.ArrayList;
import java.util.Collections;

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
        subMenu.init(this);
        return subMenu;
    }

    public void init(Menu parentMenu) {
        this.setParentMenu(parentMenu);
        this.setAccount(parentMenu.account);
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
//        return false;
        return true;
        // TODO: 5/6/19 in bayad return false bashe
    }

//    public abstract void help();
// TODO: 5/6/19 comment bala bayad solve she
    public void help(){
        System.out.println("these are the commands you can use");
        System.out.println("1)exit     2)show menu     3)enter[number]");
    }
    public void showMenu() {
        for (OnMenuClickedListener presenter : this.menuPresenters) {
            presenter.show(this);
        }
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public Menu getMenuFromSubMenus(String name) {
        for (Menu subMenu : this.subMenus) {
            if (subMenu.name.equals(name)) return subMenu;
        }
        return null;
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
