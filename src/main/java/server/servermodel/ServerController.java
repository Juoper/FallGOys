package server.servermodel;

import communication.messages.FirstContact;
import communication.messages.FirstContactResponse;
import communication.messages.LobbyUpdate;
import communication.messages.games.tictactoe.newTicTacToeRequest;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerController {
    public List<Player> players;
    public GamesManager gamesManager;
    public LobbyManager lobbyManager;

    public ServerController() {
        players = new ArrayList<>();
        gamesManager = new GamesManager();
        lobbyManager = new LobbyManager();
    }

    //Contains all handling stuff like new Games or joining Games
    public synchronized void registerNewConnection(final Socket connectionSocket) throws IOException{

        PlayerConnection connection = new PlayerConnection(connectionSocket, this);
        new Thread(
                () -> {
                    try {
                        handleFirstContact(connection);
                    } catch (Exception e) {
                        System.out.println("First contact failed.");
                        e.printStackTrace();
                    }
                })
                .start();
    }

    public void handleFirstContact(PlayerConnection playerConnection) throws IOException {

        Object firstContact = playerConnection.getConnection().readObject();
        if (firstContact != null) {
            if (firstContact instanceof FirstContact firstConnect){

                if (!validatePlayerName(firstConnect.getPlayerName())){
                    playerConnection.writeMessage(new FirstContactResponse(FirstContactResponse.ResponseCodes.INVALID_NAME));
                    playerConnection.close();

                }else{


                    playerConnection.player = new Player(firstConnect.getPlayerName(), playerConnection);
                    players.add(playerConnection.player);

                    lobbyManager.createLobbyRequest(playerConnection.player);

                    System.out.println("New Player Connected with the name: " + playerConnection.player.getPlayerName());

                    System.out.println(playerConnection.player.lobby.getLobbyID());

                    playerConnection.writeMessage(new FirstContactResponse(FirstContactResponse.ResponseCodes.SUCCESSFUL));


                    //LobbyManager.sendLobbyUpdate(playerConnection.player.lobby);
                }
            }else{
                playerConnection.writeMessage(new FirstContactResponse(FirstContactResponse.ResponseCodes.NO_MESSAGE));
                System.out.println("no First Contact message was sent");
            }
        }

        playerConnection.afterFirstContact();
    }

    public void handleNewGameRequest(Object message, Player player){
        if (message instanceof newTicTacToeRequest){
            gamesManager.newTicTacToeRequest(player);

        }

    }
    public boolean validatePlayerName(String validate){
        boolean isValid = false;

        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(validate);

        isValid = !m.find();
        isValid = players.stream().noneMatch(player -> player.getPlayerName().equalsIgnoreCase(validate));
        return isValid;
    }

}
