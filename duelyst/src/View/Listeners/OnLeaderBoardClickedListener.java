package View.Listeners;

import Controller.menu.Graphics.FXMLController.LeaderBoardFXMLC;
import Controller.menu.Graphics.FXMLController.LeaderBoardHavingFXMLC;
import Model.account.Account;

import java.util.ArrayList;

public interface OnLeaderBoardClickedListener {
    void show(ArrayList<Account> accounts , LeaderBoardHavingFXMLC fxmlc);

}
