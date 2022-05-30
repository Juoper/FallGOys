package communication.messages;

import java.io.Serializable;

public class PlayerMessage implements Serializable {
    String name;


    public PlayerMessage(String name) {
        this.name = name;
    }
}
