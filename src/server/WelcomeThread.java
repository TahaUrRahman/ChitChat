package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WelcomeThread  extends Thread{
        static ServerSocket welcomeSocket;
        static final int port = 9989;
        static Connection connection;
        static int nConnections=0;
        static List<String> Users = new ArrayList<>();
        static void makeDatabaseConnection() {
                String url = "jdbc:mysql://localhost:3306/chitchat";
                String Username = "chitchatserveradmin";
                String Password = "ChitChatServer";
                try {
                        connection = DriverManager.getConnection(url, Username, Password);
                        System.out.println("Database Connection Succeeded");
                } catch (SQLException throwable) {
                        System.out.println("Oops! Database Connection Failed");
                        throwable.printStackTrace();
                }
        }
        static void listenThread() {
                try {
                        while (true) {
                                System.out.println("Listening for clients");
                                Socket workSocket = welcomeSocket.accept();
                                nConnections++;
                                Users.add(String.valueOf(workSocket.getRemoteSocketAddress()));

                                System.out.println("Client Connected : " + workSocket.getRemoteSocketAddress());
                                Thread myThread = new workThread(workSocket);
                                myThread.start();
                        }
                } catch (IOException e) {
                        System.out.println("Oops! Exception Occurred.");
                }
        }
        @Override
        public void run() {
                makeDatabaseConnection();
                try {
                        welcomeSocket = new ServerSocket(port);
                        listenThread();

                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
}
