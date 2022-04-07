package client.gui;

import client.MainClient;
import communication.messages.games.tictactoe.FieldSelected;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class TicTacToeController {

    @FXML
    GridPane TicTacToeGridPane;

    public void onFieldButtonClick(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();

        int x = GridPane.getRowIndex(button);
        int y = GridPane.getColumnIndex(button);

        System.out.println("row: " + x + " column: " + y);

        MainClient.connectionManager.writeMessage(new FieldSelected(x,y,"test"));
    }
}
