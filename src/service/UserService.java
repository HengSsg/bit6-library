package service;

import DAO.UserDAO;
import DTO.UserDTO;

public class UserService {

    UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }


    public UserDTO login(String id, String pw) { // 사용자 로그인
        UserDTO user = null;

        user = userDAO.selectUser(id);

        if (user.getPW().equals(pw)) {
            return user;

        } else {
            return null;
        }
    }

    public boolean upgradeAuth(String grade) { // 사용자 등급 조정
        boolean flag = false;
        String UR01 = "UPDATE user\n" +
                "SET auth_code = 'UR02'\n" +
                "WHERE no=? IN (\n" +
                "  SELECT user_no\n" +
                "  FROM rent_book\n" +
                "  GROUP BY user_no=?\n" +
                "  HAVING COUNT(*) >= 5\n" +
                ");";

        String UR02 = "UPDATE user\n" +
                "SET auth_code = 'UR03'\n" +
                "WHERE no = ?\n" +
                "AND (\n" +
                "  (SELECT COUNT(*) FROM rent_book WHERE user_no = ?) >= 10\n" +
                "  and (SELECT COUNT(*) FROM report WHERE user_no = ?) >= 5\n" +
                ");";

//        String[] gradeArr = grade.split("0");
//        gradeArr[1] = String.valueOf(Integer.parseInt(gradeArr[1]) + 1);
//        gradeArr[1] = "0" + gradeArr[1];
//        grade = String.join("", gradeArr);

        try {
            if ("UR01".equals(grade)) {
                userDAO.updateRegular(UR01);
            } else if ("UR02".equals(grade)) {
                userDAO.updateSpecial(UR02);
            }

            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

//    public boolean downgradeAuth(String grade, String username) { // 사용자 등급 조정
//        boolean flag = false;
//
//        String[] gradeArr = grade.split("0");
//        gradeArr[1] = String.valueOf(Integer.parseInt(gradeArr[1]) - 1);
//        gradeArr[1] = "0" + gradeArr[1];
//        grade = String.join("", gradeArr);
//
//        try {
//            userDAO.updateAuth(sql);
//            flag = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return flag;
//    }

    public boolean communityN(String username) { // 커뮤니티 취소
        boolean status = false; // true는 커뮤니티 등록된 상태, flase는 커뮤니티 취소된 상태

        status = userDAO.quitCommunity(username);


        return status;
    }

    public boolean communityY(String username) { // 커뮤니티 취소
        boolean status = false; // true는 커뮤니티 등록된 상태, flase는 커뮤니티 취소된 상태

        status = userDAO.joinCommunity(username);


        return status;
    }

}
