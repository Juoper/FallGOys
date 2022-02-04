import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Closeable {

    private final int port;
    private ServerSocket serverSocket;


    public static void main(final String[] args) throws IOException {
        Server server = new Server(4999);
        server.start();
        server.close();
    }

    Server(int port) {
        this.port = port;
    }

    private void start() throws IOException {

        serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();

        InputStreamReader in = new InputStreamReader(socket.getInputStream());
        BufferedReader bf = new BufferedReader(in);


        String str = bf.readLine();
        System.out.println(str);


    }

    @Override
    public void close() throws IOException {

    }
}
