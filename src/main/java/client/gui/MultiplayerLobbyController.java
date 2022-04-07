package client.gui;

import client.MainClient;
import communication.messages.Ping;
import communication.messages.games.tictactoe.newTicTacToeRequest;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;

import java.io.IOException;

public class MultiplayerLobbyController {

    @FXML
    ComboBox availableGamesCollection;
    public void onStartGameButtonClick(ActionEvent event) throws IOException {
        String selection = (String) availableGamesCollection.getValue();

        switch (selection){
            case "Tic Tac Toe":

                MainClient.connectionManager.writeMessage(new newTicTacToeRequest());

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TicTacToe.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 640, 480);

                MainView.stage.setScene(scene);
        }



    }
}
