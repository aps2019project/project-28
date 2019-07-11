package Model.mediator;

import Controller.Game;
import Controller.menu.Battle;
import Controller.menu.SignInMenu;
import View.MenuHandler;
import exeption.InvalidAccountException;
import exeption.WrongPassException;
import javafx.application.Platform;
import network.ChatMSG;
import network.Message;

import java.io.IOException;
import java.util.ArrayList;

public class OnlineMultiPlayerMenuMediator implements MultiPlayerMenuMediator {

    ArrayList<ChatMSG> chats=new ArrayList<>();

    Thread connectionThread;
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
        connectionThread=new Thread(() -> {
            try {
                Game.getBattleClient().connect();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        MenuHandler.enterMenu(Battle.getMenu());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        connectionThread.start();
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

    @Override
    public void cancel() {
        try {
            connectionThread.interrupt();
        }catch (Throwable arshia){}
        System.err.println("hey yo shit i canceled");
            Game.getBattleClient().close();

        Game.setBattleClient(null);
    }
}
