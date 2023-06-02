package DAO;



import DB.ConnectionManager;
import DTO.BookDTO;
import DTO.Rent_BookDTO;
import service.Rent_BookService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Rent_BookDAO {

    public boolean Insert_Rent_Book(int book_pk, int user_pk){//이게 true가 되면 book테이블
        boolean flag = false;
        Connection con = ConnectionManager.getConnection();
        String sql = "insert into rent_book(book_no, user_no, cdt, rentYN) values(?,?,now(),1)";
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, book_pk);
            pstmt.setInt(2, user_pk);
            int affected = pstmt.executeUpdate();

            if(affected > 0){
                flag = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionManager.closeConnection(con, pstmt);
        }
        return flag;
    }
    public boolean Update_Rent_Book(int book_pk){//반납 시 rent_book 테이블 데이터를 업데이트 rentYN = 0, UDT = now()
        boolean flag = false;
        Connection con = ConnectionManager.getConnection();
        String sql = "update rent_book set rentYN = 0, UDT = now() where book_no =?";
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, book_pk);
            int affected = pstmt.executeUpdate();

            if(affected > 0){
                flag = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionManager.closeConnection(con, pstmt);
        }
        return flag;
    }
    public ArrayList<String> Get_CDT(int user_num){
        ArrayList<String> result = null;

        Connection con = ConnectionManager.getConnection();
        String sql = "select * from rent_book where user_no =?";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, user_num);
            ResultSet rs = pstmt.executeQuery();
            result = new ArrayList<String>();

            while(rs.next()){
                result.add(rs.getString("CDT"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    public Rent_BookDTO Get_RBook_UDTCDT(int book_no){
        Connection con = ConnectionManager.getConnection();
        String sql = new StringBuilder().append("select udt, cdt")
                .append(" from rent_book")
                .append("where book_no =?").toString();
        Rent_BookDTO result = new Rent_BookDTO();
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, book_no);
            ResultSet rs = pstmt.executeQuery();
            result.setUDT(rs.getString("udt"));
            result.setCDT(rs.getString("cdt"));
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    public ArrayList<Rent_BookDTO> Get_CDT2(int user_num){
        ArrayList<Rent_BookDTO> result = null;

        Connection con = ConnectionManager.getConnection();
        String sql = "select * from rent_book where user_no =?";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, user_num);
            ResultSet rs = pstmt.executeQuery();
            result = new ArrayList<Rent_BookDTO>();
            Rent_BookDTO rent_bookDTO = null;
            while(rs.next()){
                rent_bookDTO = new Rent_BookDTO(rs.getInt("book_no"),
                        rs.getInt("user_no"),
                        rs.getString("cdt"));

                result.add(rent_bookDTO);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    public List<BookDTO> RentBookUser(int user_num) {
        Connection con = ConnectionManager.getConnection();
        List<BookDTO> list = null;
        String sql = new StringBuilder()
                .append("select * from book a ")
                .append("join rent_book b ")
                .append("on a.no = b.book_no ")
                .append("where b.user_no = ? and b.rentYN = 1").toString();

        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, user_num);
            ResultSet rs = pstmt.executeQuery();
            list = new ArrayList<BookDTO>();
            BookDTO item = new BookDTO();


            while(rs.next()){
                item.setNo(rs.getInt("no"));
                item.setBname(rs.getString("bname"));
                item.setBwriter(rs.getString("bwriter"));
                item.setBpublisher(rs.getString("bpublisher"));
                list.add(item);
            }
            pstmt.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public boolean bookState(int book_pk) {
        Connection conn = ConnectionManager.getConnection();

        boolean result = false;
        String sql = "SELECT rentYN from rent_book where book_no = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, book_pk);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                if(rs.getInt("rentYN")==0) {
                    result = true;
                }
            }else{
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null || pstmt != null || conn != null) {
                try {
                    rs.close();
                    pstmt.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }
     /*public void ex_pass_Deadline(Rent_BookDTO rent_book){
        boolean flag = false;
        Connection con = ConnectionManager.getConnection();
        String sql = new StringBuilder()
                .append("select CDT from rent_book ")
                .append("where book_no = ?, user_no =?").toString();
        PreparedStatement pstmt = null;
        try{
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, rent_book.getBook_no());
            pstmt.setInt(2, rent_book.getUser_no());
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                Rent_BookDTO rent_bookDTO = new Rent_BookDTO();
                rent_bookDTO.setCDT(rs.getString("CDT"));

                Rent_BookService rent_bookService = new Rent_BookService();
                rent_bookService.Is_Pass_DeadLine(rent_bookDTO);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionManager.closeConnection(con, pstmt);
        }
    }*/

}
