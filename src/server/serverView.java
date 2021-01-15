package server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class serverView {
        public Button ModeButton;
        public AnchorPane MainPane;
        public Text LogoText;
        public Line DesignLine;
        public Button StartServerButton;
        public Button Refresh;
        public GridPane UsersList;
        public Text UsersConnectedText;
        @FXML
        Text nConnections;
        @FXML
        int ColorMode=0;
        @FXML
        void ChangeColorMode(ActionEvent event) {

                if(ColorMode == 0){
                        LogoText.setStyle("-fx-fill: white;");
                        MainPane.setStyle("-fx-background-color: #444444;");
                        DesignLine.setStyle("-fx-stroke: white;");
                        ModeButton.setText("Light Theme");
                        nConnections.setStyle("-fx-fill: white;");
                        UsersConnectedText.setStyle("-fx-fill: white;");
                        ModeButton.setStyle("-fx-background-color: white; -fx-text-fill: #1b1b1b;");
                        ColorMode = 1;
                }else{
                        MainPane.setStyle("-fx-background-color: white");
                        UsersConnectedText.setStyle("-fx-fill: black;");
                        LogoText.setStyle("-fx-fill: black");
                        DesignLine.setStyle("-fx-stroke: #444444;");
                        nConnections.setStyle("-fx-fill: black");
                        ModeButton.setText("Dark Theme");
                        ModeButton.setStyle("-fx-background-color: #1b1b1b; -fx-text-fill: white;");
                        ColorMode = 0;
                }
        }

        public void StartServer(ActionEvent actionEvent) {
                Thread myThread = new WelcomeThread();
                myThread.start();
                StartServerButton.setDisable(true);
        }

        public void Refresh(ActionEvent actionEvent) {
                nConnections.setText("Number of Connections : " + WelcomeThread.nConnections);
                int size=WelcomeThread.Users.size();
                UsersList.getChildren().clear();
                for (int i=0;i<size;++i)
                {
                        Text t= new Text();
                        t.setText(WelcomeThread.Users.get(i));
                         UsersList.add(t, 1, i);
                }
        }
}
