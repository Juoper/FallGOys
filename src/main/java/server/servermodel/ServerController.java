package server.servermodel;

import java.io.IOException;
import java.net.Socket;

public class ServerController {
    public synchronized void registerNewConnection(final Socket connectionSocket) throws IOException{

        PlayerConnection connection = new PlayerConnection(connectionSocket, this);
        new Thread(
                () -> {
                    try {
                        connection.handleFirstContact();
                    } catch (Exception e) {
                        System.out.println("First contact failed.");
                    }
                })
                .start();
    }
}
