package server;

import server.servermodel.PlayerConnection;
import server.servermodel.ServerController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerConnectionManager implements Closeable {

    private final ServerSocket serverSocket;
    private final ExecutorService executorService;
    private final ServerController serverController;

    public ServerConnectionManager(int port, final int maxThreads) throws IOException {

        this.serverSocket = new ServerSocket(port);
        this.serverController = new ServerController();

        executorService = Executors.newFixedThreadPool(maxThreads);
    }

    public void start() {
        try {
            awaitNewConnections();
        } catch (IOException e) {
            System.out.println("couldn't set Socket");
            throw new AssertionError(e);
        }
    }

    private void awaitNewConnections() throws IOException {
        while (!serverSocket.isClosed()) {
            Socket clientConnectionSocket = serverSocket.accept();

            executorService.execute(
                    () -> {
                        try {
                            serverController.registerNewConnection(clientConnectionSocket);
                        } catch (IOException e) {
                            System.out.println("The last client couldn't be connected properly.");
                        }
                    });

        }
        System.out.println("stopped accepting");
        close();
    }


    @Override
    public void close() throws IOException {

    }

}
