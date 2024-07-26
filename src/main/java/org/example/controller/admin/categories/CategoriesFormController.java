package org.example.controller.admin.categories;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.example.bo.custom.CategoryBO;
import org.example.dto.Admintm.CategoryTm;
import org.example.dto.CategoryDto;
import org.example.entity.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoriesFormController {

    public JFXTextField txtCategoryId;
    public JFXTextField txtCategoryName;
    public TableView categoryTable;
    public TableColumn colCategoryId;
    public TableColumn colCategoryName;
    public TableColumn colRemoveCategory;
    public JFXTextField txtSearchBar;

    private ObservableList<CategoryTm> obList;

    private CategoryBO categoryBO= (CategoryBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.CATEGORY);


    public void initialize(){
        if (categoryBO == null) {
            System.out.println("categoryBO is null!");
        } else {
            System.out.println("categoryBO initialized successfully!");
        }

        categoryTable.getStylesheets().add("/style/style.css");
        setCellValue();
        getAllCategory();
        generateNextId();
    }

    private void getAllCategory() {
        obList= FXCollections.observableArrayList();
        List<CategoryDto> allBranch = categoryBO.getAllCategory();
        List<String> suggestionList = new ArrayList<>();

        for (CategoryDto dto: allBranch){
            suggestionList.add(String.valueOf(dto.getCategoryId()));

            Button buttonRemove=createRemoveButton();

            obList.add(new CategoryTm(
                    dto.getCategoryId(),
                    dto.getCategoryName(),
                    buttonRemove
            ));
        }
        String[] suggestionArray = suggestionList.toArray(new String[0]);
        TextFields.bindAutoCompletion(txtSearchBar, suggestionArray);

        categoryTable.setItems(obList);
    }
    public Button createRemoveButton(){
        Button btn=new Button("Remove");
        btn.getStyleClass().add("removeBtn");
        btn.setCursor(Cursor.cursor("Hand"));
        setDeleteBtnAction(btn);
        return btn;
    }
    private void setDeleteBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to delete?", yes, no).showAndWait();


            if (type.orElse(no) == yes) {
                int focusedIndex = categoryTable.getSelectionModel().getSelectedIndex();
                CategoryTm categoryTm = (CategoryTm) categoryTable.getSelectionModel().getSelectedItem();

                if (categoryTm != null) {
                    String categoryId = categoryTm.getCategoryId();
                    boolean b = categoryBO.deleteCategory(categoryId);
                    if (b) {

                        Image image=new Image("/assest/icon/iconsDelete.png");
                        Notifications notifications=Notifications.create();
                        notifications.graphic(new ImageView(image));
                        notifications.text(" Delete Successfully");
                        notifications.title("Successfully");
                        notifications.hideAfter(Duration.seconds(5));
                        notifications.position(Pos.TOP_RIGHT);
                        notifications.show();
                        System.out.println("delete selected");
                        obList.remove(focusedIndex);
                        getAllCategory();
                    }
                }
            }
        });
    }

    private void setCellValue() {
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        colCategoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        colRemoveCategory.setCellValueFactory(new PropertyValueFactory<>("remove"));
    }
    private boolean isEmptyCheck() {

        if(txtCategoryId.getText().isEmpty()){
            txtCategoryId.requestFocus();
            txtCategoryId.setFocusColor(Color.RED);
            System.out.println("id field is empty");
            return true;
        }
        if(txtCategoryName.getText().isEmpty()){
            txtCategoryName.requestFocus();
            txtCategoryName.setFocusColor(Color.RED);
            System.out.println(" name field is empty");
            return true;
        }
        return false;
    }

    public void btnAddCategoryOnAction(ActionEvent actionEvent) {
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

            if (true){
                List<Category> categories=new ArrayList<>();


                boolean addedCategory = categoryBO.addCategory(new CategoryDto(txtCategoryId.getText(), txtCategoryName.getText()));


                if (addedCategory){
                    Image image=new Image("/assest/icon/iconsOk.png");
                    try {
                        Notifications notifications=Notifications.create();
                        notifications.graphic(new ImageView(image));
                        notifications.text(" add success");
                        notifications.title("success");
                        notifications.hideAfter(Duration.seconds(5));
                        notifications.position(Pos.TOP_RIGHT);
                        notifications.show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    getAllCategory();
                    clearField();
                    generateNextId();
                    System.out.println("add success");
                }
            }
        }
    }
    private void generateNextId() {
        int id = categoryBO.generateNextCategoryId();
        txtCategoryId.setText(String.valueOf("00"+id));
        System.out.println(id);
    }

    public void btnUpdateCategoryOnAction(ActionEvent actionEvent) {
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

            List<Category> categories=new ArrayList<>();

            boolean updateCategory = categoryBO.updateCategory(new CategoryDto(txtCategoryId.getText(), txtCategoryName.getText()));



            if (updateCategory) {
                Image image = new Image("/assest/icon/iconsOk.png");
                try {
                    Notifications notifications = Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text(" update success");
                    notifications.title("Warning");
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.position(Pos.TOP_RIGHT);
                    notifications.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                getAllCategory();
                clearField();
                System.out.println(" update success");
            }
        }
    }

    public void btnSearchBranchOnAction(ActionEvent actionEvent) {
        String id = txtSearchBar.getText();

        if (categoryBO.isExistCategory(id)){
            CategoryDto categoryDto = categoryBO.searchCategory(id);

            if (categoryDto!=null){
                txtCategoryId.setText(categoryDto.getCategoryId());
                txtCategoryName.setText(categoryDto.getCategoryName());

                Image image=new Image("/assest/icon/iconsOk.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text(" Search Successfully");
                    notifications.title("Successfully");
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.position(Pos.TOP_RIGHT);
                    notifications.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else {
            System.out.println("no category found");
        }
    }

    private void clearField(){
        txtCategoryId.clear();
        txtCategoryName.clear();
    }
}
