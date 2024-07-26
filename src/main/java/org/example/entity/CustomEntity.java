package org.example.entity;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class CustomEntity {
    private String bookId;
    private String title;
    private String type;
    private Date borrowingDate;
    private Date returnDate;
    private boolean status;
}
