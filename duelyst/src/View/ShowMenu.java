package View;

import Controller.menu.Menu;
import View.Listeners.OnMenuClickedListener;

public class ShowMenu implements OnMenuClickedListener {

    @Override

    public void show(Menu menu) {
        System.out.println();
        System.out.println(menu.getName()+" :");
        int i=0;
        for (Menu subMenu : menu.getSubMenus()) {
            i++;
            System.out.println(i+") "+subMenu.getName());
        }
    }
}
