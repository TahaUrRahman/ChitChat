package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class SignUpController {

        @FXML
        PasswordField PasswordField;
        @FXML
        TextField UsernameField;
        @FXML
        TextField EmailField;
        @FXML
        Button FileChooserButton;
        @FXML
        Button ModeButton;
        @FXML
        Pane MainPane;
        @FXML
        Text LogoText;
        @FXML
        Line DesignLine;
        @FXML
        Text AccountText;
        @FXML
        Button SignUpButton;
        @FXML
        Button SignInButton;
        int ColorMode = 0;

        @FXML
        void ValidateSignUp(ActionEvent event) {

                boolean ans = client.ReceiveSignUpValidationFromServer(UsernameField.getText(),PasswordField.getText(), EmailField.getText());
                System.out.println(UsernameField.getText());
                System.out.println(PasswordField.getText());
                System.out.println(EmailField.getText());

                if (ans) //change Scene
                {
                        System.out.println("Input Validated");

                        //User Created alert
                        Stage alert = new Stage();
                        alert.initModality(Modality.APPLICATION_MODAL);
                        alert.setTitle("Congratulations");
                        alert.setMinWidth(500);
                        Label label = new Label();
                        label.setText("User has been added");
                        System.out.println("User added ");
                        Button CloseButton = new Button();
                        CloseButton.setOnAction(e-> alert.close());
                        CloseButton.setText("Okay");
                        CloseButton.minWidth(10);
                        VBox layout = new VBox(25);
                        layout.getChildren().addAll(label,CloseButton);
                        layout.setAlignment(Pos.CENTER);
                        Scene scene=new Scene(layout, 100,100);
                        alert.setScene(scene);
                        alert.showAndWait();
                        //Goto login
                        Parent root;
                        try {
                                root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));

                                client.getMainStage().setScene(new Scene(root,1080, 600));

                        } catch (IOException e) {
                                e.printStackTrace();
                        }

                }
                else //create alert
                {
                        Stage alert = new Stage();
                        alert.initModality(Modality.APPLICATION_MODAL);
                        alert.setTitle("Invalid Input");
                        alert.setMinWidth(500);
                        Label label = new Label();
                        label.setText("You have entered Invalid SignUp Information!!!");
                        System.out.println("Wrong Input");
                        Button CloseButton = new Button();
                        CloseButton.setOnAction(e-> alert.close());
                        CloseButton.setText("Okay");
                        CloseButton.minWidth(10);
                        VBox layout = new VBox(25);
                        layout.getChildren().addAll(label,CloseButton);
                        layout.setAlignment(Pos.CENTER);
                        Scene scene=new Scene(layout, 100,100);
                        alert.setScene(scene);
                        alert.showAndWait();
                }
        }
        public void SignInButtonPressed(ActionEvent actionEvent) {
                Parent root;
                try {
                        root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));

                        client.getMainStage().setScene(new Scene(root, 1080, 600));
                        client.getMainStage().show();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
        @FXML
        void ChangeColorMode(ActionEvent event) {

                if(ColorMode == 0){
                        LogoText.setStyle("-fx-fill: white;");
                        MainPane.setStyle("-fx-background-color: #444444;");
                        DesignLine.setStyle("-fx-stroke: white;");
                        AccountText.setStyle("-fx-fill: white");
                        ModeButton.setText("Light Theme");
                        ModeButton.setStyle("-fx-background-color: white; -fx-text-fill: #1b1b1b;");
                        ColorMode = 1;
                }else{
                        MainPane.setStyle("-fx-background-color: white");
                        LogoText.setStyle("-fx-fill: black");
                        DesignLine.setStyle("-fx-stroke: #444444;");
                        AccountText.setStyle("-fx-fill:  #292929");
                        ModeButton.setText("Dark Theme  ");
                        ModeButton.setStyle("-fx-background-color: #1b1b1b; -fx-text-fill: white;");
                        ColorMode = 0;
                }
        }
}
