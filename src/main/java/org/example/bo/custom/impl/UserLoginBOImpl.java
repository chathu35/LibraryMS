package org.example.bo.custom.impl;

import org.example.bo.custom.UserLoginBO;
import org.example.dao.custom.UserLoginDAO;
import org.example.dao.custom.impl.UserLoginDAOImpl;

public class UserLoginBOImpl implements UserLoginBO {


    private UserLoginDAO userLoginDAO1=new UserLoginDAOImpl();
    @Override
    public boolean login(String username, String password) {
        return userLoginDAO1.login(username, password);
    }

    @Override
    public String getUserId(String username) {
        return userLoginDAO1.getUserId(username);
    }
}
