package org.example.dto.usertm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class AllBookTm {
    private String bookId;
    private String title;
    private String author;
    private String genre;
    private Button status;
    private Button borrowing;
}
