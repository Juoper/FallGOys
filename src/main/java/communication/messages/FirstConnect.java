package communication.messages;

import java.io.Serializable;

public class FirstConnect implements Serializable {

    private final String playerName;
    public FirstConnect(String playerName) {

        this.playerName = playerName;

    }

    public String getPlayerName() {
        return playerName;
    }
}
