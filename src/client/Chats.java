package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;


public class Chats {
        public AnchorPane MainPane;
        public Text LogoText;
        public Button ModeButton;
        public Line DesignLine;
        public Text RequestsHeading;
        public Button AddFriend;
        public Button RefreshButton;

        public Text FriendName;

        public GridPane MainCharGrid;
        public ScrollPane ChatScrollPane;
        public GridPane ChatGrid;
        public Button SendMessageButton;
        public TextArea ChatTextArea;

        @FXML
        GridPane RequestsArea;

        public Text ChatsHeading;
        List<Button> AcceptRequestsButtons= new ArrayList<>();
        List <Button> FriendsList= new ArrayList<>();
        int ColorMode = 0;
        int ScheduleState=0;
        @FXML
        void ChangeColorMode(ActionEvent event) {
                if(ColorMode == 0){
                        LogoText.setStyle("-fx-fill: white;");
                        RequestsHeading.setStyle("-fx-fill: white;");
                        RequestsArea.setStyle("-fx-background-color: #444444;");
                        MainCharGrid.setStyle("-fx-background-color: #444444;");
                        ChatGrid.setStyle("-fx-background-color: #444444;");
                        ChatScrollPane.setStyle("-fx-background-color: #444444;");

                        RequestsHeading.setStyle("-fx-fill: white;");
                        ChatsHeading.setStyle("-fx-fill: white;");
                        FriendName.setStyle("-fx-fill: white;");
                        MainPane.setStyle("-fx-background-color: #444444;");
                        ModeButton.setText("Light Theme");
                        ModeButton.setStyle("-fx-background-color: white; -fx-text-fill: #1b1b1b;");
                        RefreshButton.setStyle("-fx-background-color: white; -fx-text-fill: #1b1b1b;");
                        ColorMode = 1;
                }else{
                        MainPane.setStyle("-fx-background-color: white");
                        ChatsHeading.setStyle("-fx-fill: #444;");
                        ChatGrid.setStyle("-fx-background-color: #fff;");
                        ChatScrollPane.setStyle("-fx-background-color: #fff;");
                        LogoText.setStyle("-fx-fill: black");

                        FriendName.setStyle("-fx-fill: #444;");
                        MainCharGrid.setStyle("-fx-background-color: white;");
                        RequestsHeading.setStyle("-fx-fill: black");
                        RequestsArea.setStyle("-fx-background-color: white");
                        ModeButton.setText("Dark Theme");
                        ModeButton.setStyle("-fx-background-color: #1b1b1b; -fx-text-fill: white;");
                        RefreshButton.setStyle("-fx-background-color: #444; -fx-text-fill: white;");
                        ColorMode = 0;
                }
        }
        private static String Username ;
        @FXML
        public void AddFriendPressed(ActionEvent actionEvent) {
                Stage alert = new Stage();
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("Send Friend Request");
                alert.setMinWidth(500);
                Label label = new Label();
                label.setText("Enter Username to send friend request to : ");
                TextField UsernameField = new TextField();
                Button CloseButton = new Button();
                CloseButton.setOnAction(e-> {
                        Username = UsernameField.getText();
                        client.sendRequest(Username);
                        alert.close();});
                UsernameField.setPromptText("Username");
                CloseButton.setText("Send Request");
                CloseButton.minWidth(10);
                VBox OuterLayout = new VBox(25);
                HBox layout = new HBox(25);
                OuterLayout.getChildren().addAll(layout, CloseButton);
                layout.getChildren().addAll(label,UsernameField);
                OuterLayout.setAlignment(Pos.CENTER);
                Scene scene=new Scene(OuterLayout, 100,100);
                alert.setScene(scene);
                alert.showAndWait();
        }
        public void Refresh(ActionEvent actionEvent) {
                ArrayList<String> requests = client.getRequestNames(client.Username);
                ArrayList<String> friends = client.getFriendNames();
                ArrayList<String> chats;
                if (!FriendName.getText().equals("Username"))
                {
                        chats = client.getChat(FriendName.getText());
                        MainCharGrid.getChildren().clear();
                        for (int i=0;i<chats.size();++i)
                        {
                                Text t = new Text();
                                t.setText(chats.get(i));
                                System.out.println(t.getText());
                                MainCharGrid.add(t, i%2, i);
                        }
                }
                RequestsArea.getChildren().clear();
                ChatGrid.getChildren().clear();

                for (int i=0;i<friends.size();++i)
                {
                        Button t=new Button();
                        t.setText(friends.get(i));
                        FriendsList.add(t);
                        FriendsList.get(i).setOnAction(e->{
                                FriendName.setText(t.getText());
                                this.Refresh(new ActionEvent());
                        });
                        ChatGrid.add(FriendsList.get(i),0,i);
                }
                for (int i=0; i<requests.size();++i)
                {
                        Text t = new Text();
                        t.setText(requests.get(i));
                        RequestsArea.add(t, 0, i);
                        Button Temp = new Button("Add Friend");
                        int finalI = i;
                        AcceptRequestsButtons.add(Temp);
                        AcceptRequestsButtons.get(i).setOnAction(e ->
                        {
                                client.AcceptRequest(requests.get(finalI));
                                this.Refresh(new ActionEvent());

                        });
                        RequestsArea.add(Temp, 1, i);
                }
        }
        public void SendMessage(ActionEvent actionEvent) {
                client.sendMessage(ChatTextArea.getText(), FriendName.getText());
                ChatTextArea.clear();
        }
}