package server.servermodel;

import communication.Connection;
import communication.messages.FirstContact;
import communication.messages.FirstContactResponse;
import communication.messages.Ping;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    //move this to the serverCXontroller? becuase it handles everything?
    public void handleFirstContact() throws IOException {
        //ping(100); ping needs to be done

        Object firstContact = connection.readObject();
        if (firstContact != null) {
            if (firstContact instanceof FirstContact){

                FirstContact firstConnect = (FirstContact) firstContact;

                if (!validatePlayerName(firstConnect.getPlayerName())){
                    writeMessage(new FirstContactResponse(FirstContactResponse.ResponseCodes.INVALID_NAME));

                }else{
                    player = new Player(firstConnect.getPlayerName());
                    serverController.players.add(player);

                    System.out.println("New Player Connected with the name: " + player.getPlayerName());

                    writeMessage(new FirstContactResponse(FirstContactResponse.ResponseCodes.SUCCESSFULL));

                }


            }else{

                writeMessage(new FirstContactResponse(FirstContactResponse.ResponseCodes.NO_MESSAGE));
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

    //maybe move to another class
    //preferably ServerController
    public boolean validatePlayerName(String validate){
        boolean isValid = false;

        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(validate);

        isValid = !m.find();
        isValid = serverController.players.stream().noneMatch(player -> player.getPlayerName().equalsIgnoreCase(validate));
        return isValid;
    }
}