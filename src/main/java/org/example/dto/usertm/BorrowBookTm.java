package org.example.dto.usertm;

import javafx.scene.control.Button;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class BorrowBookTm {
    private String bookId;
    private String title;
    private String author;
    private Date borrowingDate;
    private Date returnDate;
    private Button btnreturn;
}
