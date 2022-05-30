package client.gui;

import client.MainClient;
import communication.messages.games.tictactoe.newTicTacToeRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.io.IOException;

public class MultiplayerLobbyController {

    @FXML
    ComboBox availableGamesCollection;
    @FXML
    Button LobbyIDButton;


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

    public void onLobbyIDButtonPressed(ActionEvent event) {
        LobbyIDButton.setText("Lobby ID: " + MainClient.connectionManager);

        System.out.println(MainClient.connectionManager.lobby.getLobbyID());

    }
}
