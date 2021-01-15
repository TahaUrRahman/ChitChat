package client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class client extends Application{
        static Stage mainStage;
        static Socket socket;
        static OutputStream stream = null;
        static DataOutputStream stream2;
        static InputStream myStream;
        static String Username;
        static DataInputStream myStream2;
        @Override
        public void start(Stage primaryStage) throws Exception{
                MakeConnection();
                mainStage = primaryStage;
                Parent root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
                mainStage.setTitle("ChitChat");
                mainStage.setScene(new Scene(root,1080, 600));
                mainStage.getIcons().add(new Image("/resources/logo.png" ));
                mainStage.show();
        }
        static boolean ReceiveSignUpValidationFromServer(String UserName, String Password, String Email)
        {
                String ans;
                try {
                        stream2.flush();
                        stream2.writeUTF("2");
                        stream2.writeUTF(UserName);
                        stream2.writeUTF(Password);
                        stream2.writeUTF(Email);
                        System.out.println("In Client");
                        System.out.println(UserName);
                        System.out.println(Password);
                        System.out.println(Email);
                        ans = myStream2.readUTF();
                        System.out.println("SQL answer is " + ans);
                        return ans.equals("1");
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return false;
        }
       static ArrayList<String> getRequestNames(String Username)
        {
                ArrayList<String> ans= new ArrayList<>();
                try {
                        stream2.flush();
                        stream2.writeUTF("3");
                        stream2.writeUTF(Username);
                        int size =Integer.parseInt( myStream2.readUTF());
                        for (int i=0;i<size;++i)
                        {
                                ans.add(myStream2.readUTF());
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return ans;
        }
        static boolean ReceiveLogInValidationFromServer(String UserName, String Password)
        {
                String ans;
                try {
                        Username=UserName;
                        stream2.flush();
                        stream2.writeUTF("1");
                        stream2.writeUTF(UserName);
                        stream2.writeUTF(Password);
                        ans = myStream2.readUTF();
                        return ans.equals("1");
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return false;
        }
        static void sendRequest(String RecieverName)
        {
                try {
                        stream2.flush();
                        stream2.writeUTF("4");
                        stream2.writeUTF(RecieverName);
                } catch (IOException e) {
                        e.printStackTrace();
                }

        }
        static void AcceptRequest(String ReceiverName)
        {
                try {
                        stream2.flush();
                        stream2.writeUTF("5");
                        stream2.writeUTF(ReceiverName);
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
        static ArrayList<String> getFriendNames()
        {
                ArrayList<String > FriendsList= new ArrayList<>();
                try {
                        stream2.flush();
                        stream2.writeUTF("6");
                        int size =Integer.parseInt( myStream2.readUTF());

                        for (int i=0;i<size;++i)
                        {
                                FriendsList.add(myStream2.readUTF());
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return FriendsList;
        }
        static void sendMessage(String message, String SenderName)
        {
                try {
                        stream2.flush();
                        stream2.writeUTF("7");
                        stream2.writeUTF(message);
                        stream2.writeUTF(SenderName);
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
        static ArrayList<String> getChat(String FriendName)
        {
                ArrayList<String> Chat= new ArrayList<>();
                try {
                        stream2.flush();
                        stream2.writeUTF("8");
                        stream2.writeUTF(FriendName);
                        int size =Integer.parseInt( myStream2.readUTF());
                        for (int i=0;i<size;++i)
                        {
                                Chat.add(myStream2.readUTF());
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return Chat;
        }
       public static void MakeConnection()
        {       
                try {
                        socket = new Socket("localhost",9989);
                        System.out.println("Client connected to server");
                        stream = socket.getOutputStream();
                        stream2 = new DataOutputStream(stream);
                        myStream  = socket.getInputStream();
                        myStream2 = new DataInputStream(myStream);
                } catch (IOException e) {
                        System.out.println("Oops! Connection Failed");
                }
        }
        public static Stage getMainStage()
        {
                return mainStage;
        }
        public static void main(String[] args) {
                launch(args);
        }
}
