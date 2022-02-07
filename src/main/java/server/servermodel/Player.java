package server.servermodel;

public class Player {
    private String playerName;
    private boolean gameLeader;

    public Player(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
}
