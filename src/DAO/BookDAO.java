package DAO;


import DB.ConnectionManager;
import DTO.BookDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
	// 도서 목록 전체 조회
		public List<BookDTO> bookSelectAll() {
			String query = "SELECT * FROM book";
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			List<BookDTO> bookList = new ArrayList<>(); // 책 전체정보 담기위한 컬렉션

			try {
				stmt = conn.createStatement();

				rs = stmt.executeQuery(query);
				while(rs.next()) {
					String title = rs.getString("bname");
					String author = rs.getString("bwriter");
					String publisher = rs.getString("bpublisher");

					BookDTO book = new BookDTO(title, author, publisher);

					bookList.add(book);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {

			}

			return bookList;
		}

	 // 책 제목으로 조회
		public List<BookDTO> bookSelectTitle(String bname) {

			Connection conn = ConnectionManager.getConnection();
			String sql = "SELECT * FROM book "
				     + "WHERE btitle LIKE ('%' || ? || '%') "
					 + "AND display = 1";

			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<BookDTO> listBook = new ArrayList<>();

			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, bname);

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
					     + "WHERE bwriter LIKE ('%' || ? || '%')"
					     + "AND display = 1";

			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<BookDTO> listBook = new ArrayList<>();

			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(2, bwriter);

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
					     + "WHERE bpublisher LIKE ('%' || ? || '%')"
					     + "AND display = 1";

			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<BookDTO> listBook = new ArrayList<>();

			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(3, bpublisher);

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
