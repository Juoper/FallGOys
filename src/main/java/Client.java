import communication.Connection;
import communication.messages.FirstContact;
import communication.messages.FirstContactResponse;
import communication.messages.Ping;

import java.io.*;
import java.net.Socket;

public class Client {
  private static Connection connection;

  public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
    System.out.println("new client");
    Socket socket = new Socket("localhost", 4999);

    System.out.println("connection established");

    connection = new Connection(socket);

    connection.writeObject(new FirstContact("Jouper"));

    System.out.println("created connection");

    Object msg = connection.readObject();

    if (msg instanceof FirstContactResponse){
      FirstContactResponse response = (FirstContactResponse) msg;
      System.out.println("Response Code: " + response.getResponseCode());
    }

    connection.close();



  }
}
