package server.servermodel;

import java.util.ArrayList;

public class Game {
    private static final int MAX_PLAYERS = 5;
    private final int gameId;
    //private final ArrayList<Player> players;
    private final GamesManager manager;


    public Game(final int gameId, final GamesManager gamesManager) {
        this.manager = gamesManager;
        //players = new ArrayList<>();
        this.gameId = gameId;
    }
}
