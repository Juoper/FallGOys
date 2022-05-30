package server.servermodel;

import communication.messages.LobbyUpdate;
import communication.messages.LobbyUpdate.*;
import communication.messages.PlayerMessage;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class LobbyManager extends HashMap<Integer, Lobby> {

    private static final int MAX_LOBBY_ID = 10000;

    public void createLobbyRequest(Player player){

        Random gameIdGenerator = new Random();
        int newLobbyID = gameIdGenerator.nextInt(MAX_LOBBY_ID);
        int i = 0;
        while (containsKey(newLobbyID) & i < MAX_LOBBY_ID) {
            newLobbyID = gameIdGenerator.nextInt(MAX_LOBBY_ID);
            i++;
        }

        Lobby newLobby = new Lobby(newLobbyID, player);
        put(newLobbyID, newLobby);
        player.lobby = newLobby;

        sendLobbyUpdate(newLobby);
    }


    public void joinLobbyRequest(Player player, int lobbyID){
        Lobby joinLobby = get(lobbyID);
        Lobby oldLobby = player.lobby;

        if (joinLobby.players.size() < Lobby.MAX_PLAYERS){
            if (joinLobby.isPublic()){
                player.lobby.players.remove(player);
                get(lobbyID).players.add(player);
                player.lobby = get(lobbyID);
            }
        }
        remove(oldLobby.getLobbyID());

    }


    public static void sendLobbyUpdate(Lobby lobby){

        List<Player> players = lobby.players;

        List<PlayerMessage> playersUpdate = new ArrayList<>();
        PlayerMessage leader = new PlayerMessage(lobby.leader.getPlayerName());
        boolean isPublic = lobby.isPublic();
        int lobbyID = lobby.getLobbyID();


        for (Player p:players) {
            playersUpdate.add(new PlayerMessage(p.getPlayerName()));
        }


        for (Player p:players) {
            p.getMyConnection().writeMessage(new LobbyUpdate(playersUpdate, lobbyID, leader, isPublic));
        }

    }
}
