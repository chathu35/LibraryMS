package org.example.controller.user.booksPages;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;
import org.example.bo.BOFactory;
import org.example.bo.custom.BookBO;
import org.example.bo.custom.TransactionBO;
import org.example.bo.custom.UserBO;
import org.example.controller.user.loginPages.UserLoginFormController;
import org.example.dto.BookDto;
import org.example.dto.TransactionDto;
import org.example.dto.UserDto;
import org.example.dto.usertm.AllBookTm;
import org.example.entity.Book;
import org.example.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AllBookFromController {
    public TableView bookTable;
    public TableColumn colBookId;
    public TableColumn colTitle;
    public TableColumn colAuthor;
    public TableColumn colGenre;
    public TableColumn colStatus;
    public TableColumn colBorrowing;
    public JFXTextField txtSearchBar;
    public AnchorPane allBookFormRoot;

    private BookBO bookBO= (BookBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.BOOK);

    private UserBO userBO= (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.USER);

    private TransactionBO transactionBO= (TransactionBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.TRANSACTION);

    private ObservableList<AllBookTm> obList;


    public void initialize(){
        bookTable.getStylesheets().add("/style/style.css");
        setCellValue();
        getAllBooks();
        searchTable();
        generateNextId();
    }

    private void setCellValue() {
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colBorrowing.setCellValueFactory(new PropertyValueFactory<>("borrowing"));
    }

    private void getAllBooks(){
        obList= FXCollections.observableArrayList();
        List<BookDto> allBooks = bookBO.getAllBooks();
        List<String> suggestionList = new ArrayList<>();

        for (BookDto dto: allBooks){
            suggestionList.add(String.valueOf(dto.getBookId()));

            Button buttonBorrow=createBorrowButton();
            Button buttonStatus;

            if (dto.isAvailability()){
                buttonStatus = createAvailableButton("Available");

            } else {
                buttonStatus = createNotAvailableButton("Not Available");
            }

            obList.add(new AllBookTm(
                    dto.getBookId(),
                    dto.getTitle(),
                    dto.getAuthor(),
                    dto.getGenre(),
                    buttonStatus,
                    buttonBorrow
            ));
        }
        String[] suggestionArray = suggestionList.toArray(new String[0]);
        TextFields.bindAutoCompletion(txtSearchBar, suggestionArray);

        bookTable.setItems(obList);
    }

    public Button createBorrowButton(){
        Button btn=new Button("Borrow");
        btn.getStyleClass().add("BorrowBtn");
        btn.setCursor(Cursor.cursor("Hand"));
        setBorrowBtnAction(btn);
        return btn;
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

    private void setBorrowBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            allBookFormRoot.setEffect(new GaussianBlur());
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to borrow?", yes, no).showAndWait();

            allBookFormRoot.setEffect(null);

            if (type.orElse(no) == yes) {
                int focusedIndex = bookTable.getSelectionModel().getSelectedIndex();
                AllBookTm allBookTm = (AllBookTm) bookTable.getSelectionModel().getSelectedItem();

                if (allBookTm != null) {
                    String bookId = allBookTm.getBookId();
                    System.out.println("bookid "+bookId);
                    boolean b = bookBO.borrowBook(bookId);
                    if (b){

                        long millis=System.currentTimeMillis();
                        java.sql.Date borrowingDate=new java.sql.Date(millis);


                        LocalDate currentDate = LocalDate.now();
                        LocalDate futureDate = currentDate.plusDays(7);
                        java.sql.Date returnDate = java.sql.Date.valueOf(futureDate);

                        UserDto userDto = userBO.searchUser(UserLoginFormController.logUserName);
                        User user = new User(userDto.getUserId(),userDto.getUserName(),userDto.getEmail(),userDto.getPassword());

                        BookDto bookDto = bookBO.searchBook(bookId);
                        Book book = new Book(bookDto.getBookId(), bookDto.getTitle(), bookDto.getAuthor(), bookDto.getGenre(), bookDto.isAvailability());

                        String id = generateNextId();

                        boolean b1 = transactionBO.saveTransaction(new TransactionDto(id, borrowingDate, returnDate, user, book));

                        if (b1){
                            System.out.println("transaction save");

                            Image image=new Image("/assest/icon/iconsOk.png");
                            try {
                                Notifications notifications=Notifications.create();
                                notifications.graphic(new ImageView(image));
                                notifications.text("Book borrow successful");
                                notifications.title("Warning");
                                notifications.hideAfter(Duration.seconds(5));
                                notifications.position(Pos.TOP_RIGHT);
                                notifications.show();
                            }catch (Exception a){
                                a.printStackTrace();
                            }

                            getAllBooks();
                        }else {
                            System.out.println("transaction not saved");
                        }
                    }else {
                        System.out.println("book not borrow");
                    }
                }
            }
        });
    }

    public void searchTable() {
        FilteredList<AllBookTm> filteredData = new FilteredList<>(obList, b -> true);

        txtSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(allBookTm -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                String bookId = String.valueOf(allBookTm.getBookId());
                String title = allBookTm.getTitle().toLowerCase();
                String genre = allBookTm.getGenre().toLowerCase();

                return bookId.contains(lowerCaseFilter) || title.contains(lowerCaseFilter) || genre.contains(lowerCaseFilter);
            });
        });

        SortedList<AllBookTm> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(bookTable.comparatorProperty());
        bookTable.setItems(sortedData);
    }

    private String generateNextId() {
        int id = transactionBO.generateNextTransactionId();
        String nextID="00"+id;
        return nextID;
    }

}
