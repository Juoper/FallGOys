package client.gui;


import client.MainClient;
import client.connection.ClientConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class MainViewController {
    public void onMultiplayerButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MultiplayerLobby.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 640, 480);

        MainView.stage.setScene(scene);

        MainClient.connectionManager = new ClientConnectionManager("localhost", "Jouper");

    }

}
