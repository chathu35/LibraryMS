package org.example.controller.user.loginPages;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.example.bo.BOFactory;
import org.example.bo.custom.UserBO;
import org.example.dto.UserDto;
import org.example.regex.RegexPattern;

import java.io.IOException;

public class UserRegisterController {
    public AnchorPane userRegisterRoot;
    public MFXTextField txtName;
    public MFXTextField txtEmail;
    public MFXPasswordField txtPassword;

    private UserBO userBO= (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.USER);

    public void userLoginOnAction(MouseEvent mouseEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/user/loginPages/UserLoginForm.fxml"));
        userRegisterRoot.getChildren().clear();
        userRegisterRoot.getChildren().add(parent);
    }

    public void btnRegisterOnAction(ActionEvent actionEvent) {

        if (txtName.getText().isEmpty() & txtEmail.getText().isEmpty() & txtPassword.getText().isEmpty()) {
            Image image = new Image("/assest/icon/icons8-cancel-50.png");
            try {
                Notifications notifications = Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Text feild is empty");
                notifications.title("Error");
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.TOP_RIGHT);
                notifications.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            if (checkValidate()){

                int i = userBO.generateNextUserId();
                String id= String.valueOf(i);

                boolean b = userBO.addUser(new UserDto(id, txtName.getText(), txtEmail.getText(), txtPassword.getText()));

                if (b) {
                    if (b) {
                        Image image = new Image("/assest/icon/iconsOk.png");
                        try {
                            Notifications notifications = Notifications.create();
                            notifications.graphic(new ImageView(image));
                            notifications.text("User Register success");
                            notifications.title("success");
                            notifications.hideAfter(Duration.seconds(5));
                            notifications.position(Pos.TOP_RIGHT);
                            notifications.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }

    public boolean checkValidate(){
        if (!(RegexPattern.getNamePattern().matcher(txtName.getText()).matches())) {
            txtName.requestFocus();
            txtName.setStyle("-fx-border-color: red");

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

        if (!(RegexPattern.getEmailPattern().matcher(txtEmail.getText()).matches())){
            txtEmail.requestFocus();
            txtEmail.setStyle("-fx-border-color: red");

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

        if (!(RegexPattern.getIntPattern().matcher(txtPassword.getText()).matches())){
            txtPassword.requestFocus();
            txtPassword.setStyle("-fx-border-color: red");

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

        return true;
    }

}
