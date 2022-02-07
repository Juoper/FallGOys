package server.servermodel;

import communication.Connection;
import communication.messages.FirstConnect;
import communication.messages.Ping;

import javax.management.ObjectName;
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
    private boolean firstConnection;
    private Player player;

    public Socket socket;


    public PlayerConnection(Socket socket, ServerController serverController) throws IOException {
        this.serverController = serverController;
        this.connection = new Connection(socket);
        readingPool = Executors.newSingleThreadExecutor();
        writingPool = Executors.newSingleThreadExecutor();
        connected = true;
        firstConnection = true;
        this.socket = socket;

    }

    public void handleFirstContact() throws IOException {
        ping(100);
        Object firstContact = connection.readObject();

        if (firstContact != null) {
            if (firstContact instanceof FirstConnect){
                player = new Player(((FirstConnect) firstContact).getPlayerName());

                System.out.println("New Player Connected with the name: " + player.getPlayerName());
            }else{
                System.out.println("no First Connect message was sent");
            }
        }
        firstConnection = false;


        startListeningForMessages();
    }

    /** Starts a Thread to listen for incoming messages from the client. */
    public void startListeningForMessages() {
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
        connection.readObject();
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

    @Override
    public void close() throws IOException {
        if (connection.isSocketOpen()) {
            connected = false;
            connection.close();
            readingPool.shutdown();
            writingPool.shutdown();
        }
    }

    public void ping(int time) throws IOException {

        new Thread(
                () -> {
                    while (connected) {
                        receivedPong  = false;

                        writeMessage(new Ping(123));
                        try {
                            Thread.sleep(time);
                            if (connected && !receivedPong) {
                                close();
                            }else if(receivedPong){
                                System.out.println("recievedPong from: " + player.getPlayerName());
                            }
                        } catch (InterruptedException | IOException e) {
                            e.printStackTrace();
                        }


                    }
                })
                .start();

    }
}