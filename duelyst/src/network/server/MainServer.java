package network.server;

import java.io.IOException;

public class MainServer {
    public static void main(String[] args) throws IOException {

        BattleServer BS=new BattleServer();
        BS.run();
    }
}
