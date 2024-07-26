package org.example.controller.user.booksPages;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;
import org.example.bo.BOFactory;
import org.example.bo.custom.TransactionBO;
import org.example.dto.TransactionDto;
import org.example.dto.usertm.BorrowBookTm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BorrowBookFormController {
    public TableView borrowbookTable;
    public TableColumn colBookId;
    public TableColumn colTitle;
    public TableColumn colAuthor;
    public TableColumn colBorrowDate;
    public TableColumn colReturnDate;
    public TableColumn colReturnBtn;
    public JFXTextField txtSearchBar;


    private ObservableList<BorrowBookTm> obList;

    private TransactionBO transactionBO= (TransactionBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.TRANSACTION);

    public void initialize(){
        setCellValue();
        getAllTransaction();
        //searchTable();
    }

    private void setCellValue() {
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowingDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colReturnBtn.setCellValueFactory(new PropertyValueFactory<>("btnreturn"));
    }

    private void getAllTransaction(){
        obList= FXCollections.observableArrayList();
        List<TransactionDto> allTransaction = transactionBO.getTransactions();
        List<String> suggestionList = new ArrayList<>();

        for (TransactionDto dto: allTransaction){
            suggestionList.add(String.valueOf(dto.getTransactionId()));

            Button buttonReturn=createReturnButton();

            obList.add(new BorrowBookTm(
                    dto.getBook().getBookId(),
                    dto.getBook().getTitle(),
                    dto.getBook().getAuthor(),
                    dto.getBorrowingDate(),
                    dto.getReturnDate(),
                    buttonReturn
            ));
        }
        String[] suggestionArray = suggestionList.toArray(new String[0]);
        TextFields.bindAutoCompletion(txtSearchBar, suggestionArray);

        borrowbookTable.setItems(obList);
    }

    public Button createReturnButton(){
        Button btn=new Button("Return");
        btn.getStyleClass().add("removeBtn");
        btn.setCursor(Cursor.cursor("Hand"));
        setReturnBtnAction(btn);
        return btn;
    }

    private void setReturnBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to return?", yes, no).showAndWait();


            if (type.orElse(no) == yes) {
                BorrowBookTm allBookTm = (BorrowBookTm) borrowbookTable.getSelectionModel().getSelectedItem();

                if (allBookTm != null) {
                    String bookId = allBookTm.getBookId();
                    boolean b = transactionBO.returnBook(bookId);
                    if (b){
                        getAllTransaction();

                        Image image=new Image("/assest/icon/iconsOk.png");
                        try {
                            Notifications notifications=Notifications.create();
                            notifications.graphic(new ImageView(image));
                            notifications.text("Book return successfully");
                            notifications.title("Successfully");
                            notifications.hideAfter(Duration.seconds(5));
                            notifications.position(Pos.TOP_RIGHT);
                            notifications.show();
                        }catch (Exception a){
                            a.printStackTrace();
                        }
                    }else {
                        System.out.println("book not return");
                    }
                }
            }
        });
    }
}
