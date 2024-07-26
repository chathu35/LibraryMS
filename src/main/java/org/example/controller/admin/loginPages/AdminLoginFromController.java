package org.example.controller.admin.loginPages;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.example.regex.RegexPattern;

import java.io.IOException;

public class AdminLoginFromController {
    public AnchorPane adminLoginRoot;
    public MFXTextField txtAdminUsername;
    public MFXPasswordField txtAdminPassword;


    private String username="admin";
    private String password="1234";

    public void btnAdminLoginOnAction(ActionEvent actionEvent) throws IOException {


        if (checkValidate()){
            if (username.equals(txtAdminUsername.getText()) & password.equals(txtAdminPassword.getText())){
                Parent parent= FXMLLoader.load(getClass().getResource("/view/Admin/AdminDashboard.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();

                Image image=new Image("/assest/icon/iconsOk.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Login successful");
                    notifications.title("Successfully login");
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.position(Pos.TOP_RIGHT);
                    notifications.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                Image image=new Image("/assest/icon/icons8-cancel-50.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("UserName or Password wrong");
                    notifications.title("Warning");
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.position(Pos.TOP_RIGHT);
                    notifications.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean checkValidate(){
        if (!(RegexPattern.getNamePattern().matcher(txtAdminUsername.getText()).matches())) {
            txtAdminUsername.requestFocus();
            txtAdminUsername.setStyle("-fx-border-color: red");

            Image image=new Image("/assest/icon/icons8-cancel-50.png");
            try {
                Notifications notifications=Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Please enter valid data");
                notifications.title("Warning");
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.TOP_RIGHT);
                notifications.show();
            }catch (Exception e){
                e.printStackTrace();
            }

            return false;
        }

        if (!(RegexPattern.getIntPattern().matcher(txtAdminPassword.getText()).matches())){
            txtAdminPassword.requestFocus();
            txtAdminPassword.setStyle("-fx-border-color: red");

            Image image=new Image("/assest/icon/icons8-cancel-50.png");
            try {
                Notifications notifications=Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Please enter only numbers");
                notifications.title("Warning");
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.TOP_RIGHT);
                notifications.show();
            }catch (Exception e){
                e.printStackTrace();
            }

            return false;
        }

        return true;
    }


}
