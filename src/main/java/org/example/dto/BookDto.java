package org.example.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class BookDto {
    private String bookId;
    private String title;
    private String author;
    private String genre;
    private boolean availability;
//    private List<Branch> branch;
}
