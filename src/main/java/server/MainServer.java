package server;

public class MainServer {

    private static final int PORT = 4999;
    public static final int THREADS = 200;


    public static void main(final String[] args) throws Exception {
        ServerConnectionManager server = new ServerConnectionManager(PORT,THREADS);
        server.start();
        server.close();
    }
}
