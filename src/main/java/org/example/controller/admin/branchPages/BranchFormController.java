package org.example.controller.admin.branchPages;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;
import org.example.bo.BOFactory;
import org.example.bo.custom.BranchBO;
import org.example.dto.Admintm.BranchTm;
import org.example.dto.BranchDto;
import org.example.entity.Book;
import org.example.regex.RegexPattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BranchFormController {
    public JFXTextField txtBranchId;
    public JFXTextField txtBranchName;
    public JFXTextField txtBranchLocation;
    public TableView branchTable;
    public TableColumn colBranchId;
    public TableColumn colBranchName;
    public TableColumn colBranchLocation;
    public TableColumn colCloseBranch;
    public JFXTextField txtSearchBar;

    private BranchBO branchBO= (BranchBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.BRANCH);

    private ObservableList<BranchTm> obList;

    public void initialize(){
        branchTable.getStylesheets().add("/style/style.css");
        setCellValue();
        getAllBranch();
        searchTable();
    }

    private void setCellValue() {
        colBranchId.setCellValueFactory(new PropertyValueFactory<>("branchId"));
        colBranchName.setCellValueFactory(new PropertyValueFactory<>("branchName"));
        colBranchLocation.setCellValueFactory(new PropertyValueFactory<>("branchLocation"));
        colCloseBranch.setCellValueFactory(new PropertyValueFactory<>("closeBranch"));
    }

    private void getAllBranch(){
        obList= FXCollections.observableArrayList();
        List<BranchDto> allBranch = branchBO.getAllBranch();
        List<String> suggestionList = new ArrayList<>();

        for (BranchDto dto: allBranch){
            suggestionList.add(String.valueOf(dto.getBranchId()));

            Button buttonRemove=createRemoveButton();

            obList.add(new BranchTm(
                    dto.getBranchId(),
                    dto.getBranchName(),
                    dto.getBranchLocation(),
                    buttonRemove
            ));
        }
        String[] suggestionArray = suggestionList.toArray(new String[0]);
        TextFields.bindAutoCompletion(txtSearchBar, suggestionArray);

        branchTable.setItems(obList);
    }

    public Button createRemoveButton(){
        Button btn=new Button("Remove");
        btn.getStyleClass().add("removeBtn");
        btn.setCursor(Cursor.cursor("Hand"));
        setDeleteBtnAction(btn);
        return btn;
    }

    public void btnAddBranchOnAction(ActionEvent actionEvent) {
        if(isEmptyCheck()){
            Image image=new Image("/assest/icon/icons8-cancel-50.png");
            try {
                Notifications notifications=Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Value is empty! Please enter all values");
                notifications.title("Warning");
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.TOP_RIGHT);
                notifications.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {

            if (checkValidate()){
                List<Book> books=new ArrayList<>();

                boolean b = branchBO.addBranch(new BranchDto(txtBranchId.getText(), txtBranchName.getText(), txtBranchLocation.getText()));

                if (b){
                    Image image=new Image("/assest/icon/iconsOk.png");
                    try {
                        Notifications notifications=Notifications.create();
                        notifications.graphic(new ImageView(image));
                        notifications.text("branch add success");
                        notifications.title("success");
                        notifications.hideAfter(Duration.seconds(5));
                        notifications.position(Pos.TOP_RIGHT);
                        notifications.show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    getAllBranch();
                    clearField();
                    generateNextId();
                    System.out.println("branch add success");
                }
            }
        }
    }

    public void btnUpdateBranchOnAction(ActionEvent actionEvent) {
        if (isEmptyCheck()){
            Image image=new Image("/assest/icon/icons8-cancel-50.png");
            try {
                Notifications notifications=Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Value is empty! Please enter all values");
                notifications.title("Warning");
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.TOP_RIGHT);
                notifications.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {

            List<Book> books=new ArrayList<>();

            boolean b = branchBO.updateBranch(new BranchDto(txtBranchId.getText(), txtBranchName.getText(), txtBranchLocation.getText()));


            if (b) {
                Image image = new Image("/assest/icon/iconsOk.png");
                try {
                    Notifications notifications = Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Branch update success");
                    notifications.title("Warning");
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.position(Pos.TOP_RIGHT);
                    notifications.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                getAllBranch();
                clearField();
                System.out.println("branch update success");
            }
        }
    }

    public void btnSearchBranchOnAction(ActionEvent actionEvent) {
        String id = txtSearchBar.getText();

        if (branchBO.isExistBranch(id)){
            BranchDto branchDto = branchBO.searchBranch(id);

            if (branchDto!=null){
                txtBranchId.setText(branchDto.getBranchId());
                txtBranchName.setText(branchDto.getBranchName());
                txtBranchLocation.setText(branchDto.getBranchLocation());

                Image image=new Image("/assest/icon/iconsOk.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Branch Search Successfully");
                    notifications.title("Successfully");
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.position(Pos.TOP_RIGHT);
                    notifications.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else {
            System.out.println("no branch found");
        }
    }

    private boolean isEmptyCheck() {

        if(txtBranchId.getText().isEmpty()){
            txtBranchId.requestFocus();
            txtBranchId.setFocusColor(Color.RED);
            System.out.println("Branch id field is empty");
            return true;
        }
        if(txtBranchName.getText().isEmpty()){
            txtBranchName.requestFocus();
            txtBranchName.setFocusColor(Color.RED);
            System.out.println("Branch name field is empty");
            return true;
        }
        if (txtBranchLocation.getText().isEmpty()){
            txtBranchLocation.requestFocus();
            txtBranchLocation.setFocusColor(Color.RED);
            System.out.println("Branch location field is empty");
            return true;
        }
        return false;
    }

    private void setDeleteBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to delete?", yes, no).showAndWait();


            if (type.orElse(no) == yes) {
                int focusedIndex = branchTable.getSelectionModel().getSelectedIndex();
                BranchTm branchTm = (BranchTm) branchTable.getSelectionModel().getSelectedItem();

                if (branchTm != null) {
                    String bookId = branchTm.getBranchId();
                    boolean b = branchBO.deleteBranch(bookId);
                    if (b) {

                        Image image=new Image("/assest/icon/iconsDelete.png");
                        Notifications notifications=Notifications.create();
                        notifications.graphic(new ImageView(image));
                        notifications.text("Branch Delete Successfully");
                        notifications.title("Successfully");
                        notifications.hideAfter(Duration.seconds(5));
                        notifications.position(Pos.TOP_RIGHT);
                        notifications.show();
                        System.out.println("delete selected");
                        obList.remove(focusedIndex);
                        getAllBranch();
                        searchTable();
                    }
                }
            }
        });
    }

    public void searchTable() {
        FilteredList<BranchTm> filteredData = new FilteredList<>(obList, b -> true);

        txtSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(branchTm -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                String branchId = String.valueOf(branchTm.getBranchId());
                String name = branchTm.getBranchName().toLowerCase();

                return branchId.contains(lowerCaseFilter) || name.contains(lowerCaseFilter);
            });
        });

        SortedList<BranchTm> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(branchTable.comparatorProperty());
        branchTable.setItems(sortedData);
    }

    private void clearField(){
        txtBranchId.clear();
        txtBranchName.clear();
        txtBranchLocation.clear();
    }

    private void generateNextId() {
        int id = branchBO.generateNextBranchId();
        txtBranchId.setText(String.valueOf("00"+id));
        System.out.println(id);
    }


    public boolean checkValidate(){
        if (!(RegexPattern.getNamePattern().matcher(txtBranchName.getText()).matches())) {
            txtBranchName.requestFocus();
            txtBranchName.setFocusColor(Color.RED);

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

        if (!(RegexPattern.getAddressPattern().matcher(txtBranchLocation.getText()).matches())){
            txtBranchLocation.requestFocus();
            txtBranchLocation.setFocusColor(Color.RED);

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
