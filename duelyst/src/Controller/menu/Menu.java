package Controller.menu;

import View.Listeners.OnMenuClickedListener;
import Model.account.Account;

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
//        this.parentMenu = parentMenu;
        this.subMenus = new ArrayList<>();
        this.patterns = new ArrayList<>();
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

    public abstract void help();

    public void showMenu() {
        for (OnMenuClickedListener presenter : this.menuPresenters) {
            presenter.show();
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

    public Account getAccount() {
        return this.account;
    }
}
