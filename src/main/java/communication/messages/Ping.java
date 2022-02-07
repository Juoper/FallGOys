package communication.messages;

import java.io.Serial;
import java.io.Serializable;

/**
 * Ping message between server and client to check, whether the connection works properly,
 * or if the latency is to big to play the game.
 */

public class Ping implements Serializable {

    private final int lastPing;
    public Ping(int lastPing){
        this.lastPing = lastPing;

    }


    public int getLastPing() {
        return lastPing;
    }
}
