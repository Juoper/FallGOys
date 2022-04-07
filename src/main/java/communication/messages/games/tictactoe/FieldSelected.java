package communication.messages.games.tictactoe;

import java.io.Serializable;

public class FieldSelected implements Serializable {

    private final int x;
    private final int y;

    public FieldSelected(int x1, int y1, String playerName) {
        this.x = x1;
        this.y = y1;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
