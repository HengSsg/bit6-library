package service;

import DAO.UserDAO;
import DTO.UserDTO;

import java.sql.SQLException;

public class UserService {

    UserDAO userDAO = new UserDAO();


    public UserDTO login(String id, String pw) throws SQLException {
        UserDTO user = null;

        user = userDAO.selectUser(id);

        if(user.getPW().equals(pw)) {
            return user;

        } else {
            return null;
        }
    }

    public void upgradeAuth() {
        userDAO.updateAuth();
    }


}
