package DAO;

import DB.ConnectionManager;
import DTO.BookDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

	 // 책 제목으로 조회
	Connection conn = ConnectionManager.getConnection();
	
		public List<BookDTO> bookSelectTitle(String bname) {

			String sql = "SELECT * FROM book "
				     + "WHERE bname = ? ";

			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<BookDTO> listBook = new ArrayList<>();

			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, bname );

				rs = pstmt.executeQuery();
				while(rs.next()) {
					BookDTO b = new BookDTO();

					b.setBname(rs.getString("bname"));
					b.setBwriter(rs.getString("bwriter"));
					b.setBpublisher(rs.getString("bpublisher"));
					
					listBook.add(b);

				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				 if (rs != null | pstmt != null | conn != null) {
				        try {
				            rs.close();
				            pstmt.close();
				            conn.close();
				        } catch (SQLException e) {
				            e.printStackTrace();
				        }
				    }
			}

			return listBook;
		}


	// 책 저자로 조회
		public List<BookDTO> bookSelectWriter(String bwriter) {
			Connection conn = ConnectionManager.getConnection();

			String sql = "SELECT * FROM book "
					     + "WHERE bwriter = ? ";

			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<BookDTO> listBook = new ArrayList<>();

			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, bwriter);

				rs = pstmt.executeQuery();
				while(rs.next()) {
					BookDTO b = new BookDTO();

					b.setBname(rs.getString("bname"));
					b.setBwriter(rs.getString("bwriter"));
					b.setBpublisher(rs.getString("bpublisher"));

					listBook.add(b);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				 if (rs != null | pstmt != null | conn != null) {
				        try {
				            rs.close();
				            pstmt.close();
				            conn.close();
				        } catch (SQLException e) {
				            e.printStackTrace();
				        }
				    }
			}

			return listBook;
		}

	// 출판사로 조회
		public List<BookDTO> bookSelectPublisher(String bpublisher) {
			Connection conn = ConnectionManager.getConnection();

			String sql = "SELECT * FROM book "
					     + "WHERE bpublisher = ?";

			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<BookDTO> listBook = new ArrayList<>();

			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, bpublisher);

				rs = pstmt.executeQuery();
				while(rs.next()) {
					BookDTO b = new BookDTO();

					b.setBname(rs.getString("bname"));
					b.setBwriter(rs.getString("bwriter"));
					b.setBpublisher(rs.getString("bpublisher"));

					listBook.add(b);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				 if (rs != null | pstmt != null | conn != null) {
				        try {
				            rs.close();
				            pstmt.close();
				            conn.close();
				        } catch (SQLException e) {
				            e.printStackTrace();
				        }
				    }
			}

			return listBook;
		}

}
