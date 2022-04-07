package client.connection;

import communication.Connection;
import communication.messages.FirstContact;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerConnection {

    private final Connection connection;
    private final ClientConnectionManager connectionManager;
    private final ExecutorService readingPool;
    private final ExecutorService writingPool;
    private boolean connected;


    public ServerConnection(Socket socket, ClientConnectionManager connectionManager) throws IOException {

        connection = new Connection(socket);
        this.connectionManager = connectionManager;
        readingPool = Executors.newSingleThreadExecutor();
        writingPool = Executors.newSingleThreadExecutor();
        connected = true;


    }

    public void handleFirstContact(String playerName) throws IOException {
        connection.writeObject(new FirstContact("Jouper"));

        startListeningForMessages();
    }



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
        Object msg = connection.readObject();

        connectionManager.handleContact(msg);

    }

    public Connection getConnection() {
        return connection;
    }

    public void writeMessage(final Object msg) {
        writingPool.execute(() -> {
            try {
                connection.writeObject(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
