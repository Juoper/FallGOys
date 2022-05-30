package communication;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Contains a socket and streams on it. The class serves as a container for 'interfaces' to an
 * established connection.
 */
public class Connection implements Closeable{
    public final Socket socket;
    private final ObjectInputStream inStream;
    private final ObjectOutputStream outStream;


    /**
     * Creates a new Connection object holding the given socket and streams.
     *
     * @param socket The socket of the connection.
     */
    public Connection(Socket socket) throws IOException {
        this.socket = socket;

        outStream = new ObjectOutputStream(socket.getOutputStream());
        inStream = new ObjectInputStream(socket.getInputStream());
    }

    public Object readObject() throws IOException {
        try {
            return inStream.readObject();
        } catch (ClassNotFoundException e) {
            close();
            throw new AssertionError(e);
        }

    }

    public void writeObject(Object object) throws IOException {
        outStream.reset();
        outStream.writeObject(object);
        outStream.flush();
    }

    @Override
    public void close() throws IOException {
        if (!socket.isClosed()) {
            socket.close();
        }
    }

    public boolean isSocketOpen() {
        return !socket.isClosed();
    }


}