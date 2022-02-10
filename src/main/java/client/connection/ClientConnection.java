package client.connection;

import communication.Connection;
import communication.messages.FirstContact;
import communication.messages.FirstContactResponse;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientConnection {

    private final Connection connection;
    private final ExecutorService readingPool;
    private final ExecutorService writingPool;
    private boolean connected;


    public ClientConnection(Socket socket) throws IOException {

        connection = new Connection(socket);
        readingPool = Executors.newSingleThreadExecutor();
        writingPool = Executors.newSingleThreadExecutor();
        connected = true;


    }

    public void handleFirstContact() throws IOException {
        connection.writeObject(new FirstContact("Jouper"));
        System.out.println("listening");

        startListeningForMessages();
    }


    public void startListeningForMessages() {
        System.out.println("inner list");

        System.out.println("looooop");
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

        System.out.println("recieved");
        if (msg instanceof FirstContactResponse){
            FirstContactResponse response = (FirstContactResponse) msg;
            System.out.println("Response Code: " + response.getResponseCode());
        }


    }

    public Connection getConnection() {
        return connection;
    }
}
