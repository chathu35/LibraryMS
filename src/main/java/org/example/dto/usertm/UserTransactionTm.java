package org.example.dto.usertm;

import javafx.scene.control.Button;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class UserTransactionTm {

    private String bookId;
    private String title;
    private String type;
    private Date borrowingDate;
    private Date returnDate;
    private Button status;

}
