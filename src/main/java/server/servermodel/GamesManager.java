package server.servermodel;

import server.servermodel.games.TicTacToe;

import java.util.HashMap;
import java.util.Random;

public class GamesManager extends HashMap<Integer, Game> {

    private static final int MAX_GAME_ID = 10000;
    private static final int MAX_PLAYERS = 5;


    public void newTicTacToeRequest(Player player){

        Random gameIdGenerator = new Random();
        int newGameId = gameIdGenerator.nextInt(MAX_GAME_ID);
        int i = 0;
        while (containsKey(newGameId) & i < MAX_GAME_ID) {
            newGameId = gameIdGenerator.nextInt(MAX_GAME_ID);
            i++;
        }
        put(newGameId, new TicTacToe(player, MAX_PLAYERS, this));


    }
}
