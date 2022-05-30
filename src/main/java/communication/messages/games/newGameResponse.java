package communication.messages.games;

import server.servermodel.Game;

import java.io.Serializable;

public class newGameResponse implements Serializable {

    public Game game;

    private final ResponseCodes responseCode;

    public newGameResponse(ResponseCodes responseCode, Game game) {
        this.responseCode = responseCode;
        this.game = game;
    }

    public enum ResponseCodes{
        SUCCESSFUL, NO_GAMESLOTS_AVAILABEL, NO_MESSAGE
    }


}
