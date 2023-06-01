package DAO;


import DB.ConnectionManager;
import DTO.ReportDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            pstmt.setInt(3, reportDTO.getUser_no());
            pstmt.setInt(4, reportDTO.getBook_no());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //유저가 대출한 도서 조회
    public List<ReportDTO> findByUserNo(String sql, int no){
        int i = 1;
        List<ReportDTO> list = new ArrayList<>();
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, no);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                ReportDTO reportDTO = new ReportDTO();
                reportDTO.setBname(rs.getString("bname"));
                reportDTO.setUser_no(rs.getInt("user_no"));
                reportDTO.setBook_no(rs.getInt("book_no"));
                System.out.println("=============================================");
                System.out.println("도서번호: " + i);
                System.out.println("제목: " + reportDTO.getBname());
                i++;
                list.add(reportDTO);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}