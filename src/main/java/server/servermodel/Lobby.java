package server.servermodel;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

    public static final int MAX_PLAYERS = 4;


    private final int lobbyID;
    public Game currentGame;
    public Player leader;
    private boolean isPublic;

    List<Player> players = new ArrayList<>();

    public Lobby(int lobbyID, Player player) {
        this.lobbyID = lobbyID;
        this.leader = player;
        this.isPublic = true;
        players.add(player);
    }

    public int getLobbyID() {
        return lobbyID;
    }

    public boolean isPublic() {
        return isPublic;
    }
}
