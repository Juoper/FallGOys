package server.servermodel;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerController {
    public List<Player> players;


    public ServerController() {
        players = new ArrayList<>();
    }

    //Contains all handling stuff like new Games or joining Games
    public synchronized void registerNewConnection(final Socket connectionSocket) throws IOException{

        PlayerConnection connection = new PlayerConnection(connectionSocket, this);
        new Thread(
                () -> {
                    try {
                        connection.handleFirstContact();
                    } catch (Exception e) {
                        System.out.println("First contact failed.");
                        e.printStackTrace();
                    }
                })
                .start();


    }

}
