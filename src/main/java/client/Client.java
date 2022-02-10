package client;

import client.discordIntegration.sdkMain;
import communication.Connection;

import java.io.*;

public class Client {
  private static Connection connection;

  public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

    sdkMain sdkMain = new sdkMain();
/*
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

 */



  }
}
