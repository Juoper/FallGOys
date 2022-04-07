package server.servermodel.games;

import server.servermodel.Game;
import server.servermodel.GamesManager;
import server.servermodel.Player;

public class TicTacToe extends Game {
    public TicTacToe(Player gameHost, int maxPlayers, GamesManager manager) {
        super(gameHost, maxPlayers, manager);
    }
}
