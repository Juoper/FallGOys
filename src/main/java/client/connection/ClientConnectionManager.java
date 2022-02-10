package client.connection;

import communication.Connection;
import communication.messages.FirstContact;
import communication.messages.FirstContactResponse;

import java.io.IOException;
import java.net.Socket;

public class ClientConnectionManager {



    private final ClientConnection clientConnection;

    public ClientConnectionManager(String serverIp) throws IOException {
        Socket socket = new Socket(serverIp, 4999);

        clientConnection = new ClientConnection(socket);


        new Thread(
                () -> {
                    try {
                        clientConnection.handleFirstContact();
                    } catch (Exception e) {
                        System.out.println("First contact failed.");
                        e.printStackTrace();
                    }
                })
                .start();



    }

    public ClientConnection getClientConnection() {
        return clientConnection;
    }


}
