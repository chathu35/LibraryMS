package org.example.controller.admin.dashboard;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.TextFields;
import org.example.bo.BOFactory;
import org.example.bo.custom.BookBO;
import org.example.bo.custom.BranchBO;
import org.example.bo.custom.TransactionBO;
import org.example.bo.custom.UserBO;
import org.example.dto.BookDto;
import org.example.dto.BranchDto;
import org.example.dto.UserDto;
import org.example.dto.UserTransactionDto;
import org.example.dto.usertm.UserTransactionTm;

import java.util.ArrayList;
import java.util.List;

public class DashboardFormController {
    public TableView TransactionTable;
    public TableColumn colUserId;
    public TableColumn colBookName;
    public TableColumn colBookType;
    public TableColumn colDate;
    public TableColumn colReturnDate;
    public TableColumn colStatus;
    public JFXTextField txtSearchBar;
    public Label lblAllUser;
    public Label lblAllBook;
    public Label lblAllBranch;
    public AnchorPane userPane;
    public AnchorPane bookspane;
    public AnchorPane branchpane;


    private ObservableList<UserTransactionTm> obList;

    private TransactionBO transactionBO= (TransactionBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.TRANSACTION);

    private UserBO userBO= (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.USER);

    private BookBO bookBO= (BookBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.BOOK);

    private BranchBO branchBO= (BranchBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.BRANCH);


    public void initialize(){
        setCellValue();
        geUserTransaction();
        //searchTable();
        setLableValue();


    }


    private void setCellValue() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("title"));
        colBookType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("borrowingDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void geUserTransaction(){
        obList= FXCollections.observableArrayList();
        List<UserTransactionDto> allTransaction = transactionBO.getAllUserTransactions();
        List<String> suggestionList = new ArrayList<>();

        for (UserTransactionDto dto: allTransaction){
            suggestionList.add(String.valueOf(dto.getBookId()));


            Button buttonStatus;

            if (dto.isStatus()){
                buttonStatus = createAvailableButton("Returning");

            } else {
                buttonStatus = createNotAvailableButton("Not Returning");
            }


            obList.add(new UserTransactionTm(
                    dto.getBookId(),
                    dto.getTitle(),
                    dto.getType(),
                    dto.getBorrowingDate(),
                    dto.getReturnDate(),
                    buttonStatus
            ));
        }
        String[] suggestionArray = suggestionList.toArray(new String[0]);
        TextFields.bindAutoCompletion(txtSearchBar, suggestionArray);

        TransactionTable.setItems(obList);
    }

    public Button createAvailableButton(String text){
        Button btn=new Button(text);
        btn.getStyleClass().add("Availablebtn");
        return btn;
    }

    public Button createNotAvailableButton(String text){
        Button btn=new Button(text);
        btn.getStyleClass().add("NotAvailablebtn");
        return btn;
    }

    public void setLableValue(){
        List<UserDto> allUser = userBO.getAllUser();
        int size = allUser.size();
        lblAllUser.setText(String.valueOf(size));

        List<BookDto> allBooks = bookBO.getAllBooks();
        int size1 = allBooks.size();
        lblAllBook.setText(String.valueOf(size1));

        List<BranchDto> allBranch = branchBO.getAllBranch();
        int size2 = allBranch.size();
        lblAllBranch.setText(String.valueOf(size2));

    }

    public void btnUserEnter(MouseEvent mouseEvent) {
        userPane.setStyle("-fx-background-color: #afc2e1");
    }

    public void btnUserRelease(MouseEvent mouseEvent) {
        userPane.setStyle("-fx-background-color:  #cad3e3");

    }

    public void btnBookEnter(MouseEvent mouseEvent) {
        bookspane.setStyle("-fx-background-color: #afc2e1");
    }

    public void btnBookExit(MouseEvent mouseEvent) {
        bookspane.setStyle("-fx-background-color:  #cad3e3");
    }

    public void btnBranchEnter(MouseEvent mouseEvent) {
        branchpane.setStyle("-fx-background-color: #afc2e1");
    }

    public void btnBranchExit(MouseEvent mouseEvent) {
        userPane.setStyle("-fx-background-color:  #cad3e3");
    }
}
