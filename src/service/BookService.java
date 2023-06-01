package service;

import java.util.List;
import DAO.BookDAO;
import DTO.BookDTO;

public class BookService {

	private BookDAO dao;

	public BookService() {
		dao = new BookDAO();
	}

	// 책 제목으로 조회
    	public List<BookDTO> bookSelectTitle(String bname) {
    		List<BookDTO> bookList = dao.bookSelectTitle(bname);

    		return bookList;
    	}

    	// 저자 명으로 조회
	public List<BookDTO> bookSelectWriter(String bwriter) {
		List<BookDTO> bookList = dao.bookSelectWriter(bwriter);

		return bookList;
	}

	//출판사 명으로 조회
	public List<BookDTO> bookSelectPublisher(String bpublisher) {
		List<BookDTO> bookList = dao.bookSelectPublisher(bpublisher);

		return bookList;
	}
	
	// 해당 도서의 상태를 조회
	public String bookState() {
		 String bookStatus = dao.bookState();
		    
	    if (bookStatus.equals("대출가능")) {
	        return "대출가능";
	    } else {
	        return "대출중";
	    }
	}
	
	// 최다 도서 대출 조회
	public String getMostBorrowedBookName() {
	    String mostBorrowedBookName = dao.getMostBorrowedBookName();
	    return mostBorrowedBookName;
	}
    	
}
