import communication.Connection;
import communication.messages.FirstConnect;
import communication.messages.Ping;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
  private static Connection connection;

  public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
    System.out.println("new client");
    Socket socket = new Socket("localhost", 4999);

    System.out.println("connection established");

    connection = new Connection(socket);

    connection.writeObject(new FirstConnect("Jouper"));

    System.out.println("created connection");

    Ping msg = (Ping) connection.readObject();

    System.out.println(msg.getLastPing());

    connection.close();



  }
}
