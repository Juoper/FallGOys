package client.connection;

import communication.messages.FirstContactResponse;
import communication.messages.Ping;

import java.io.IOException;
import java.net.Socket;

public class ClientConnectionManager {



    private final ServerConnection serverConnection;

    public ClientConnectionManager(String serverIp, String playerName) throws IOException {
        Socket socket = new Socket(serverIp, 4999);

        serverConnection = new ServerConnection(socket, this);


        new Thread(
                () -> {
                    try {
                        serverConnection.handleFirstContact(playerName);
                    } catch (Exception e) {
                        System.out.println("First contact failed.");
                        e.printStackTrace();
                    }
                })
                .start();
    }



    public void handleContact(Object msg) throws IOException {

        if (msg instanceof FirstContactResponse response){

            System.out.println("Response Code: " + response.getResponseCode());
        }else if (msg instanceof Ping){

            //System.out.println("received ping");
            serverConnection.writeMessage(new Ping(100));
        }
    }


    public void writeMessage(Object object){
        serverConnection.writeMessage(object);

    }

}
