package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
public class Transaction {
    @Id
    private String transactionId;

    @Column(nullable = false)
    private Date borrowingDate;

    @Column(nullable = false)
    private Date returnDate;

    @ManyToOne
    @JoinColumn(name="userId" ,nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="bookId" , nullable = false)
    private Book book;
}
