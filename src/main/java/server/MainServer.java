package server;

import java.net.InetAddress;

public class MainServer {

    private static final int PORT = 4999;
    public static final int THREADS = 200;


    public static void main(final String[] args) throws Exception {
        System.out.println("Current Ip Adress: " + InetAddress.getLocalHost());

        ServerConnectionManager server = new ServerConnectionManager(PORT,THREADS);
        server.start();


        server.close();
    }
}
