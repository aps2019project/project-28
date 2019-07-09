package Model.mediator;

import exeption.InvalidAccountException;
import exeption.WrongPassException;
import network.ChatMSG;

import java.io.IOException;
import java.util.ArrayList;

public interface MultiPlayerMenuMediator {
    void selectUser(String username, String password) throws InvalidAccountException, WrongPassException, IOException;

    void sendMessage(String text);

    ArrayList<ChatMSG> getChats();

    void cancel();
}
