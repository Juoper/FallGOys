package client.connection;

import communication.messages.CreateLobbyRequest;
import communication.messages.FirstContactResponse;
import communication.messages.LobbyUpdate;
import communication.messages.Ping;
import server.servermodel.Lobby;
import server.servermodel.LobbyManager;

import java.io.IOException;
import java.net.Socket;

public class ClientConnectionManager {

    public Lobby lobby;

    private final ServerConnection serverConnection;

    public ClientConnectionManager(String serverIp, String playerName) throws IOException {
        Socket socket = new Socket(serverIp, 4999);

        serverConnection = new ServerConnection(socket, this);

        try {
            serverConnection.handleFirstContact(playerName);
        } catch (Exception e) {
            System.out.println("First contact failed.");
            e.printStackTrace();
        }
    }

    public void handleContact(Object message) throws IOException {

        if (message instanceof FirstContactResponse response){

            System.out.println("Response Code: " + response.getResponseCode());

            serverConnection.connected = true;


        }else if (message instanceof Ping){

            //System.out.println("received ping");
            serverConnection.writeMessage(new Ping(100));
        }else if (message instanceof LobbyUpdate msg){
            System.out.println("lobby update: " + msg.getLobbyID());
        }
    }


    public void writeMessage(Object object){
        serverConnection.writeMessage(object);

    }

}
