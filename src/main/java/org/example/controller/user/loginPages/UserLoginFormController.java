package org.example.controller.user.loginPages;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.example.bo.custom.UserLoginBO;
import org.example.bo.custom.impl.UserLoginBOImpl;

import java.io.IOException;

public class UserLoginFormController {
    public AnchorPane userLoginRoot;
    public MFXTextField txtUserName;

    public static String logUserName;
    public MFXPasswordField txtPassword;

    private UserLoginBO userLoginBO1=new UserLoginBOImpl();

    public void userRegisterOnAction(MouseEvent mouseEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/user/loginPages/UserRegisterForm.fxml"));
        userLoginRoot.getChildren().clear();
        userLoginRoot.getChildren().add(parent);
    }

    public void btnUserLoginOnAction(ActionEvent actionEvent) throws IOException {

        String userName = txtUserName.getText();
        String password = txtPassword.getText();


        boolean login = userLoginBO1.login(userName, password);

        if (login){
            Parent parent= FXMLLoader.load(getClass().getResource("/view/user/UserDashboard.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

            logUserName = userLoginBO1.getUserId(userName);


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
