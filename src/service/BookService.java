package service;


import DAO.BookDAO;

public class BookService {

	private BookDAO dao;

	public BookService() {
		dao = new BookDAO();
	}

	// 책 제목으로 조회
//    	public List<BookDTO> bookSelectTitle(String bname) {
//    		List<BookDTO> bookList = dao.bookSelectTitle(bname);
//
//    		return bookList;
//    	}
//
//    	// 저자 명으로 조회
//	public List<BookDTO> bookSelectWriter(String bwriter) {
//		List<BookDTO> bookList = dao.bookSelectWriter(bwriter);
//
//		return bookList;
//	}
//
//	//출판사 명으로 조회
//	public List<BookDTO> bookSelectPublisher(String bpublisher) {
//		List<BookDTO> bookList = dao.bookSelectPublisher(bpublisher);
//
//		return bookList;
//	}
    	
}
