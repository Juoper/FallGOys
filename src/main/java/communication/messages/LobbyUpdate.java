package communication.messages;

import java.io.Serializable;
import java.util.List;

public class LobbyUpdate implements Serializable {
    List<PlayerMessage> players;
    private final PlayerMessage leader;
    private final boolean isPublic;
    private final int lobbyID;

    public LobbyUpdate(List<PlayerMessage> players, int lobbyID, PlayerMessage leader, boolean isPublic) {
        this.lobbyID = lobbyID;
        this.leader = leader;
        this.isPublic = isPublic;
        this.players = players;
    }

    public PlayerMessage getLeader() {
        return leader;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public int getLobbyID() {
        return lobbyID;
    }
}
