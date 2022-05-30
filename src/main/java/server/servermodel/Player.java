package server.servermodel;

public class Player {
    private final String playerName;
    private final PlayerConnection myConnection;

    public Lobby lobby;

    public Player(String playerName, PlayerConnection connection) {
        this.playerName = playerName;
        this.myConnection = connection;
    }

    public String getPlayerName() {
        return playerName;
    }

    public PlayerConnection getMyConnection() {
        return myConnection;
    }
}
