package org.example.controller.user.transactionPages;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.textfield.TextFields;
import org.example.bo.BOFactory;
import org.example.bo.custom.TransactionBO;
import org.example.dto.UserTransactionDto;
import org.example.dto.usertm.UserTransactionTm;

import java.util.ArrayList;
import java.util.List;

public class UserTransactionFormController {
    public TableView transactionTable;
    public TableColumn colBookId;
    public TableColumn colTitle;
    public TableColumn colType;
    public TableColumn colBorrowDate;
    public TableColumn colReturnDate;
    public TableColumn colStatus;
    public JFXTextField txtSearchBar;

    private ObservableList<UserTransactionTm> obList;

    private TransactionBO transactionBO= (TransactionBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.TRANSACTION);


    public void initialize(){
        setCellValue();
        geUserTransaction();
        //searchTable();
    }

    private void setCellValue() {
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowingDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void geUserTransaction(){
        obList= FXCollections.observableArrayList();
        List<UserTransactionDto> allTransaction = transactionBO.getUserTransactions();
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

        transactionTable.setItems(obList);
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

}
