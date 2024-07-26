package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dto.UserDto;

import java.util.List;

public interface UserBO extends SuperBO {
    boolean addUser(UserDto dto);
    List<UserDto> getAllUser();
    boolean updateUser(UserDto dto);
    boolean isExistUser(String id);
    UserDto searchUser(String id);
    boolean deleteUser(String id);

    int generateNextUserId();
}
