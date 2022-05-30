package communication.messages;

import java.io.Serializable;

public class JoinLobbyRequest implements Serializable {
    int LobbyID;

    public JoinLobbyRequest(int lobbyID) {
        LobbyID = lobbyID;
    }

    public int getLobbyID() {
        return LobbyID;
    }
}
