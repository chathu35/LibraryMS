package org.example.dao.custom;

import org.example.dao.SuperDAO;

public interface UserLoginDAO extends SuperDAO {
    boolean login(String username, String password);

    String getUserId(String username);
}
