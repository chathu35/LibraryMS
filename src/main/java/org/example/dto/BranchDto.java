package org.example.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class BranchDto {
    private String branchId;
    private String branchName;
    private String branchLocation;
//    private List<Book> books;
}
