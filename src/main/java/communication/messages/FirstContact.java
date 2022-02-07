package communication.messages;

import java.io.Serializable;

public class FirstContact implements Serializable {

    private final String playerName;
    public FirstContact(String playerName) {

        this.playerName = playerName;

    }

    public String getPlayerName() {
        return playerName;
    }
}
