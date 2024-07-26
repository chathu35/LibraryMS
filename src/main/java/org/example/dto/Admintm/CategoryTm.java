package org.example.dto.Admintm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CategoryTm {
    private String categoryId;
    private String categoryName;
    private Button remove;
}
