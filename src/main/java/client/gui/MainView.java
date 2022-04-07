package client.gui;

import client.MainClient;
import client.connection.ClientConnectionManager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class MainView extends Application {

    public static Stage stage;


    @Override
    public void start(Stage stage) throws IOException {

        MainView.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 640, 480);


        stage.setScene(scene);
        stage.show();
    }

    public static void main() {
        launch();
    }
}





