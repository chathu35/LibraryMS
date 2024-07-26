package org.example.controller.admin;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdminDashboardController {


    public AnchorPane changeRoot;
    public Label lblTime;
    public Label lblDate;
    public AnchorPane mainDashboard;

    public void initialize() throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/Admin/dashboard/DashboardForm.fxml"));
        changeRoot.getChildren().clear();
        changeRoot.getChildren().add(parent);

        Timeline time=new Timeline(
                new KeyFrame(Duration.ZERO,e->{
                    DateTimeFormatter formatter=DateTimeFormatter.ofPattern("HH:MM:SS");
                    lblTime.setText(LocalDateTime.now().format(formatter));
                }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();

        lblTime.setText(String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:MM:SS"))));
        lblDate.setText(String.valueOf(LocalDate.now()));
    }
    public void btndashboardOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/Admin/dashboard/DashboardForm.fxml"));
        changeRoot.getChildren().clear();
        changeRoot.getChildren().add(parent);
    }

    public void btnBookOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/Admin/booksPages/BookForm.fxml"));
        changeRoot.getChildren().clear();
        changeRoot.getChildren().add(parent);
    }

    public void btnUserOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/Admin/userPages/UserForm.fxml"));
        changeRoot.getChildren().clear();
        changeRoot.getChildren().add(parent);
    }

    public void btnTransactionOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/Admin/transaction/TransactionForm.fxml"));
        changeRoot.getChildren().clear();
        changeRoot.getChildren().add(parent);
    }

    public void btnBranchesOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/Admin/branchPaages/BranchForm.fxml"));
        changeRoot.getChildren().clear();
        changeRoot.getChildren().add(parent);
    }

    public void btnArrowClickOnAction(ActionEvent actionEvent) {
    }

    public void txtSearchBarOnAction(ActionEvent actionEvent) {
    }

    public void btnLogoutOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(this.getClass().getResource("/view/WelcomeForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage= (Stage) mainDashboard.getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public void btnCategoryOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/Admin/categories/CategoriesForm.fxml"));
        changeRoot.getChildren().clear();
        changeRoot.getChildren().add(parent);
    }
}
