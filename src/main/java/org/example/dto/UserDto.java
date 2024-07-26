package org.example.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter

public class UserDto {
    private String userId;
    private String userName;
    private String email;
    private String password;
}
