package server;
import java.io.*;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class workThread extends Thread{
        Socket workSocket;
        InputStream stream;
        DataInputStream TheInputStream;
        OutputStream stream1;
        DataOutputStream TheOutputStream;
        String Username;
        workThread(Socket tempSocket)
        {
                try {
                        workSocket = tempSocket;
                }
                catch (Exception e)
                {
                        System.out.println("Oops! Connection Error");
                }
        }
        String ValidateLogin()
        {
                String UserName;
                String Password;
                try {
                        UserName = TheInputStream.readUTF();
                        Password = TheInputStream.readUTF();

                        String query = "SELECT email_id FROM person WHERE profile_name = ? and password = ?";
                        PreparedStatement statement = WelcomeThread.connection.prepareStatement(query);
                        statement.setString(1,UserName);
                        statement.setString(2,Password);
                        ResultSet rs = statement.executeQuery();
                        int size =0;
                        while (rs.next())
                        {
                                size ++;
                        }
                        if (size>0) {
                               String ans = "1";
                               Username = UserName;
                               return ans;
                       }
                       else
                       {
                               return "0";
                       }
                } catch (IOException e) {
                        e.printStackTrace();
                } catch (SQLException throwables) {
                        System.out.println("Query Failed");
                       throwables.printStackTrace();
                }
                return "-1";
        }
        String ValidateSignUp()
        {
                String UserName;
                String Password;
                String Email;
                try {
                        UserName = TheInputStream.readUTF();
                        Password = TheInputStream.readUTF();
                        Email = TheInputStream.readUTF();
                        String query = "Insert into person(email_id, password, profile_name,online_status) VALUES (?,?,?,1)";
                        PreparedStatement statement = WelcomeThread.connection.prepareStatement(query);
                        statement.setString(1,Email);
                        statement.setString(2,Password);
                        statement.setString(3,UserName);
                        int nrows = statement.executeUpdate();
                        if (nrows>0) {
                                return "1";
                        }
                        else
                        {
                                return "0";
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                } catch (SQLException throwables) {
                        System.out.println("Query Failed");
                        throwables.printStackTrace();
                }
                return "-1";
        }
        ArrayList <String > getRequests(String Username)
        {
                ArrayList<String> ans = new ArrayList<>();

                String query = "select sender_name from friend_request where receiver_name = ?";
                PreparedStatement statement;
                try {
                        statement = WelcomeThread.connection.prepareStatement(query);
                        statement.setString(1,Username);
                        ResultSet rs = statement.executeQuery();
                        while (rs.next())
                        {
                                ans.add(rs.getString("sender_name"));
                        }
                } catch (SQLException throwables) {
                        throwables.printStackTrace();
                }
                return ans;
        }
        ArrayList <String > getFriends()
        {
                ArrayList<String> ans = new ArrayList<>();
                String query = "select profile_name1 from friends_list where profile_name2 = ?";
                String query2 = "select profile_name2 from friends_list where profile_name1 = ?";
                PreparedStatement statement;
                PreparedStatement statement2;
                try {
                        statement = WelcomeThread.connection.prepareStatement(query);
                        statement2 = WelcomeThread.connection.prepareStatement(query2);
                        statement.setString(1,Username);
                        statement2.setString(1,Username);
                        ResultSet rs = statement.executeQuery();
                        while (rs.next())
                        {
                                ans.add(rs.getString("profile_name1"));
                        }
                        ResultSet rs2 = statement2.executeQuery();
                        while (rs2.next())
                        {
                                ans.add(rs2.getString("profile_name2"));
                        }
                } catch (SQLException throwables) {
                        throwables.printStackTrace();
                }
                return ans;
        }
        void storeReceivedRequest(String ReceiverName)
        {
               String query =" insert into friend_request(sender_name, receiver_name, sent_date) values(?,?,now())";
                PreparedStatement statement;
                try {
                        statement = WelcomeThread.connection.prepareStatement(query);
                        statement.setString(1,Username);
                        statement.setString(2, ReceiverName);
                       statement.executeUpdate();

                } catch (SQLException throwables) {
                        throwables.printStackTrace();
                }
        }
        void AddFriend(String Receivername)
        {
                String query = "INSERT INTO friends_list (profile_name1,profile_name2) VALUES (?,?)";
                PreparedStatement statement;
                PreparedStatement statement2;
                PreparedStatement statement3;
                String query2 = "delete from friend_request where (sender_name = ? and receiver_name = ?) or (sender_name = ? and receiver_name = ?)";
                String query3 = "delete from friend_request where (sender_name = ? and receiver_name = ?) or (sender_name = ? and receiver_name = ?)";
                try {
                        statement = WelcomeThread.connection.prepareStatement(query);
                        statement2 = WelcomeThread.connection.prepareStatement(query2);
                        statement3 = WelcomeThread.connection.prepareStatement(query3);
                        statement.setString(1,Receivername);
                        statement.setString(2,Username);
                        statement2.setString(1,Receivername);
                        statement2.setString(2,Username);
                        statement2.setString(3,Username);
                        statement2.setString(4,Receivername);
                        statement3.setString(1,Receivername);
                        statement3.setString(2,Username);
                        statement3.setString(3,Username);
                        statement3.setString(4,Receivername);
                        statement.executeUpdate();
                        statement2.executeUpdate();
                        statement3.executeUpdate();
                } catch (SQLException throwables) {
                        throwables.printStackTrace();
                }
        }
        void StoreMessage(String message, String ReceiverName)
        {
                String query =" insert into message (sender_name,receiver_name,content) values (?,?,?);";
                PreparedStatement statement;
                try {
                        statement = WelcomeThread.connection.prepareStatement(query);
                        statement.setString(1,Username);
                        statement.setString(2, ReceiverName);
                        statement.setString(3, message);
                        statement.executeUpdate();
                } catch (SQLException throwables) {
                        throwables.printStackTrace();
                }
        }
        ArrayList<String> getChats(String friendName)
        {
                ArrayList<String> ans = new ArrayList<>();
                String query = "select content from message where (message.sender_name = ? and message.receiver_name = ?) or (message.sender_name = ? and receiver_name = ?) order by message_id";

                PreparedStatement statement;
                try {
                        statement = WelcomeThread.connection.prepareStatement(query);
                        statement.setString(1,Username);
                        statement.setString(2,friendName);
                        statement.setString(3,friendName);
                        statement.setString(4,Username);
                        ResultSet rs = statement.executeQuery();
                        while (rs.next())
                        {
                                ans.add(rs.getString("content"));
                        }
                } catch (SQLException throwables) {
                        throwables.printStackTrace();
                }
                return ans;
        }
        @Override
        public void run() {
                try{
                        stream = null;
                        stream1 = workSocket.getOutputStream();
                        TheOutputStream = new DataOutputStream(stream1);
                        stream = workSocket.getInputStream();
                        TheInputStream = new DataInputStream(stream);
                        while (true) {
                                String id = TheInputStream.readUTF();
                                if (id.equals("1")) {
                                        String status = ValidateLogin();
                                        TheOutputStream.flush();
                                        TheOutputStream.writeUTF(status);
                                }
                                if (id.equals("2")) {

                                        String status = ValidateSignUp();
                                        TheOutputStream.flush();
                                        TheOutputStream.writeUTF(status);
                                }
                                if (id.equals("3")) {
                                        String Username = TheInputStream.readUTF();
                                        ArrayList<String> requests = getRequests(Username);
                                        TheOutputStream.flush();
                                        TheOutputStream.writeUTF(String.valueOf(requests.size()));
                                        for (String request : requests) {
                                                TheOutputStream.writeUTF(request);
                                        }
                                }
                                if (id.equals("4")) {
                                        String ReceiverName = TheInputStream.readUTF();
                                        storeReceivedRequest(ReceiverName);
                                }
                                if (id.equals("5")) {
                                        String ReceiverName = TheInputStream.readUTF();
                                        AddFriend(ReceiverName);
                                }
                                if (id.equals("6")) {
                                      TheOutputStream.flush();
                                      ArrayList<String> FriendsList = getFriends();
                                      TheOutputStream.writeUTF(String.valueOf(FriendsList.size()));
                                        for (String s : FriendsList) {
                                                TheOutputStream.writeUTF(s);
                                        }
                                }
                                if (id.equals("7")) {
                                        String message = TheInputStream.readUTF();
                                        String ReceiverName = TheInputStream.readUTF();
                                        StoreMessage(message, ReceiverName);
                                }
                                if (id.equals("8")) {
                                        String FriendName = TheInputStream.readUTF();
                                        TheOutputStream.flush();
                                        ArrayList<String> chats = getChats(FriendName);
                                        TheOutputStream.writeUTF(String.valueOf(chats.size()));
                                        for (String chat : chats) {
                                                TheOutputStream.writeUTF(chat);
                                        }

                                }
                        }
                } catch (IOException e) {
                        System.out.println("Connection Ended");
                        WelcomeThread.nConnections--;
                        WelcomeThread.Users.remove(String.valueOf(workSocket.getRemoteSocketAddress()));
                }
        }
}
