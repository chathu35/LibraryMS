package org.example.bo.custom.impl;

import org.example.bo.custom.UserBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.UserDAO;
import org.example.dto.UserDto;
import org.example.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {

    private UserDAO userDAO= (UserDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOType.USER);
    @Override
    public boolean addUser(UserDto dto) {
        return userDAO.add(new User(dto.getUserId(),dto.getUserName(),dto.getEmail(),dto.getPassword()));
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> all = userDAO.getAll();
        List<UserDto> allDto = new ArrayList<UserDto>();

        for (User user:all) {
            allDto.add(new UserDto(user.getUserId(),user.getUserName(),user.getEmail(),user.getPassword()));
        }

        return allDto;
    }

    @Override
    public boolean updateUser(UserDto dto) {
        return userDAO.update(new User(dto.getUserId(),dto.getUserName(),dto.getEmail(),dto.getPassword()));
    }

    @Override
    public boolean isExistUser(String id) {
        return userDAO.isExists(id);
    }

    @Override
    public UserDto searchUser(String id) {
        User search = userDAO.search(id);
        UserDto userDto = new UserDto(search.getUserId(),search.getUserName(),search.getEmail(),search.getPassword());
        return userDto;
    }

    @Override
    public boolean deleteUser(String id) {
        return userDAO.delete(id);
    }

    @Override
    public int generateNextUserId() {
        return userDAO.generateNextUserId();
    }
}
