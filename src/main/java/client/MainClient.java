package client;

import client.connection.ClientConnectionManager;
import client.gui.MainView;

import java.util.Arrays;


public class MainClient {
    public static ClientConnectionManager connectionManager;

    public static void main(String[] args) {
        //ClientConnectionManager connectionManager = new ClientConnectionManager("localhost", "Jouper");
        //discordIntegrationManager dcIntegration = new discordIntegrationManager();

        MainView.main();

    }
}
