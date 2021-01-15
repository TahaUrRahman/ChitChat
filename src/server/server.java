package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.stage.Stage;


public class server extends Application {
        static Stage mainStage;
        @Override
        public void start(Stage primaryStage) throws Exception{
                mainStage = primaryStage;
                Parent root = FXMLLoader.load(getClass().getResource("serverView.fxml"));
                mainStage.setTitle("ChitChat");
                mainStage.setScene(new Scene(root,1080, 600));
                mainStage.getIcons().add(new Image("/resources/logo.png" ));
                mainStage.show();
        }
        public static void main(String[] args) {
                launch(args);
        }
}
