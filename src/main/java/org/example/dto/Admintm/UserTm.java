package org.example.dto.Admintm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter

public class UserTm {
    private String userId;
    private String userName;
    private String email;
    private Button remove;
}
