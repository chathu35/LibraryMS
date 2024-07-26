package org.example.dto.Admintm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class BranchTm {
    private String branchId;
    private String branchLocation;
    private String branchName;
    private Button closeBranch;
}
