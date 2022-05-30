package server.servermodel;

import communication.Connection;
import communication.messages.*;
import communication.messages.games.newGameRequest;


import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayerConnection implements Closeable {

    private final ServerController serverController;
    private final Connection connection;
    private final ExecutorService readingPool;
    private final ExecutorService writingPool;
    private boolean connected;
    private boolean receivedPong;
    public Player player;

    public Socket socket;


    public PlayerConnection(Socket socket, ServerController serverController) throws IOException {
        this.serverController = serverController;
        this.connection = new Connection(socket);
        readingPool = Executors.newSingleThreadExecutor();
        writingPool = Executors.newSingleThreadExecutor();
        connected = true;
        this.socket = socket;

    }

    //move this to the serverController? because it handles everything?


    private void handleContact(final Object message) throws IOException {

        if (message instanceof Ping){
            receivedPong = true;
        }else if (message instanceof newGameRequest){
            serverController.handleNewGameRequest(message, player);
        }else if (message instanceof JoinLobbyRequest msg){
            serverController.lobbyManager.joinLobbyRequest(player, msg.getLobbyID());
        }else if (message instanceof CreateLobbyRequest){
            serverController.lobbyManager.createLobbyRequest(player);
        }
    }

    public void afterFirstContact(){

        new Thread(
                () -> {
                    try {
                        ping(50);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
                .start();

        startListeningForMessages();


    }


        /** Starts a Thread to listen for incoming messages from the client. */
    public void startListeningForMessages() {
        System.out.println("test");
        readingPool.execute(
                () -> {
                    while (connected) {
                        try {
                            awaitMessages();

                        } catch (IOException | InterruptedException e) {
                            connected = false;
                        }
                    }
                });
    }

    private void awaitMessages() throws IOException, InterruptedException {
        Object msg = connection.readObject();
        handleContact(msg);
    }


    public void writeMessage(final Object message) {
        writingPool.execute(() -> {
            try {
                connection.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void ping(int time) throws IOException {
        while (connected) {
            receivedPong  = false;
            writeMessage(new Ping(123));
            try {
                Thread.sleep(time);
                if (connected && !receivedPong) {
                    System.out.println("closing");

                    close();
                }else if(receivedPong){
                    //System.out.println("recievedPong from: " + player.getPlayerName());
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }


        }
    }

    //maybe move to another class
    //preferably ServerController


    @Override
    public void close() throws IOException {
        if (connection.isSocketOpen()) {
            serverController.players.remove(player);
            connected = false;
            connection.close();
            readingPool.shutdown();
            writingPool.shutdown();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}