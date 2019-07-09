package Model.mediator;

import Controller.Game;
import Controller.menu.SignInMenu;
import exeption.InvalidAccountException;
import exeption.WrongPassException;
import network.ChatMSG;
import network.Message;

import java.io.IOException;
import java.util.ArrayList;

public class OnlineMultiPlayerMenuMediator implements MultiPlayerMenuMediator {

    ArrayList<ChatMSG> chats=new ArrayList<>();

    public OnlineMultiPlayerMenuMediator() {
        new Thread(() -> {
            while (true){
                Message message = Game.getChatRoomClient().read();
                ChatMSG chatMSG = (ChatMSG) message.getCarry().get(0);
                chats.add(chatMSG);
            }
        }).start();
    }

    @Override
    public void selectUser(String username, String password) throws InvalidAccountException, WrongPassException, IOException {
        /*username and password are ignored*/
        Game.getBattleClient().connect();
    }

    @Override
    public void sendMessage(String text) {
        ChatMSG chat=new ChatMSG(text, SignInMenu.getMenu().getAccount());
        Message message=new Message("Chat!");
        message.addCarry(chat);
        Game.getChatRoomClient().write(message);
    }

    @Override
    public ArrayList<ChatMSG> getChats() {
        return chats;
    }
}
