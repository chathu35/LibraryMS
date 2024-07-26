package org.example.bo.custom;

import org.example.bo.SuperBO;

public interface UserLoginBO extends SuperBO {
    boolean login(String username, String password);

    String getUserId(String username);
}
