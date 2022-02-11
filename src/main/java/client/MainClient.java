package client;

import client.connection.ClientConnectionManager;
import client.discordIntegration.discordIntegrationManager;
import client.discordIntegration.richPresenceStates.InLobby;

import java.io.IOException;

public class MainClient {
    public static ClientConnectionManager connectionManager;
    public static void main(String[] args) throws IOException {
        ClientConnectionManager connectionManager = new ClientConnectionManager("192.168.96.152");

        discordIntegrationManager sdkMain = new discordIntegrationManager();
        sdkMain.updatePresence(new InLobby("Currently In Game", "Hier Könnte ihre Werbung stehen", 5, 10));


    }
}
