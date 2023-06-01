package service;

import DAO.UserDAO;
import DTO.UserDTO;

import java.sql.SQLException;

public class UserService {

    UserDAO userDAO = new UserDAO();


    public UserDTO login(String id, String pw) { // 사용자 로그인
        UserDTO user = null;

        user = userDAO.selectUser(id);

        if (user.getPW().equals(pw)) {
            return user;

        } else {
            return null;
        }
    }

    public boolean upgradeAuth(String grade, String username) { // 사용자 등급 조정
        boolean flag = false;

        try {
            userDAO.updateAuth(grade, username);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }


}
