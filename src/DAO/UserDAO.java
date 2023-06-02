package DAO;

import DB.ConnectionManager;
import DTO.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public UserDTO selectUser(String id) {
        UserDTO user = null;
        Connection con = ConnectionManager.getConnection();
        String sql = "select * from user where id = ?;";

        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // 로직이 들어오지 않는다.
                // DB의 내용을 로컬데이터셋으로 저장하는 것이 주 목적


                user = new UserDTO(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
            }

            System.out.println(user.getID() + user.getPW());
            pstmt.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return user;
    }

    public void updateAuth(String grade, String username) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        String sql = "update user set auth_code=? where name=?;";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, grade);
        pstmt.setString(2, username);
        pstmt.executeQuery();

        con.close();
        pstmt.close();
    }

    public boolean quitCommunity(String username) { // 커뮤니티 탈퇴하기
        boolean flag = false;
        Connection con = ConnectionManager.getConnection();
        String sql = "update user\n" +
                "set comuntYn = 0\n" +
                "where cdt <= date_sub(now(), interval 1 month) and name=?;";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, username);
            int result = pstmt.executeUpdate();
            if (result != 0) {
                flag = true;
            }
            con.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return flag;
    }

    public boolean joinCommunity(String username) { // 커뮤니티 탈퇴하기
        boolean flag = false;
        Connection con = ConnectionManager.getConnection();
        String sql = "update user\n" +
                "set comuntYn = 1\n" +
                "where name=?;";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, username);
            int result = pstmt.executeUpdate();
            if (result != 0) {
                flag = true;
            }
            con.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return flag;
    }
}
