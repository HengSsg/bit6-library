package DAO;


import DB.ConnectionManager;
import DTO.ReportDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *  독후감 작성 DAO
 * */

public class ReportDAO {
    Connection con = ConnectionManager.getConnection();

    //독후감 등록
    public void insertReport(String sql, ReportDTO reportDTO){
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, reportDTO.getTitle());
            pstmt.setString(2, reportDTO.getContents());
            //pstmt.setInt(3, reportDTO.getUser_no());
            //pstmt.setInt(4, reportDTO.getBook_no());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}