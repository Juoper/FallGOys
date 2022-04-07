package server.servermodel;

import java.util.List;

public class Game {
    private Player gameHost;
    private int maxPlayers;
    private List<Player> players;

    private final GamesManager manager;


    public Game(Player gameHost, int maxPlayers, GamesManager manager) {
        players.add(gameHost);

        this.gameHost = gameHost;
        this.maxPlayers = maxPlayers;
        this.manager = manager;
    }
}
