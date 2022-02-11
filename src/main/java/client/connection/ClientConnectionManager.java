package client.connection;

import communication.messages.FirstContactResponse;
import communication.messages.Ping;

import java.io.IOException;
import java.net.Socket;

public class ClientConnectionManager {



    private final ClientConnection clientConnection;

    public ClientConnectionManager(String serverIp, String playerName) throws IOException {
        Socket socket = new Socket(serverIp, 4999);

        clientConnection = new ClientConnection(socket, this);


        new Thread(
                () -> {
                    try {
                        clientConnection.handleFirstContact(playerName);
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


    public void handleContact(Object msg) throws IOException {

        if (msg instanceof FirstContactResponse){
            FirstContactResponse response = (FirstContactResponse) msg;

            System.out.println("Response Code: " + response.getResponseCode());
        }else if (msg instanceof Ping){

            System.out.println("received ping");
            clientConnection.writeMessage(new Ping(100));
        }
    }

}
