
import DTO.BookDTO;
import DTO.UserDTO;
import service.BookService;
import service.ReportService;
import service.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    String partition = "=============================================";
    String selectMenu = "메뉴를 선택해주세요";
    Scanner scanner = new Scanner(System.in);

    UserDTO user = null;

    UserService userService = new UserService();


    public static void main(String[] args) throws SQLException {

        Main main = new Main();
        main.mainNotLogined();
    }


    public void mainNotLogined() throws SQLException { // 첫 화면
        System.out.println(partition);
        System.out.println("환영합니다 도서 관리 시스템에 오신것을 환영합니다!!");
        System.out.println(selectMenu);
        System.out.println("1. 도서 조회");
        System.out.println("2. 로그인");
//        System.out.println(partition);
        System.out.printf(">>");
        String input = scanner.nextLine();

        if (input.equals("1")) {
//            this.checkBookView();
        } else if (input.equals("2")) {
            this.loginView();
        } else {
            this.mainNotLogined();
        }
    }

    public void loginView() throws SQLException {
        System.out.println(partition);
        System.out.println("아이디를 입력해주세요");
        String id = scanner.nextLine();
        System.out.println("비밀번호를 입력해주세요");
        String pw = scanner.nextLine();

        // 로그인 정보 맞는지 확인
        user = userService.login(id, pw);

        if (user != null) {
            // 맞으면 로그인된 메인화면으로 이동
            System.out.println("로그인 성공하였습니다!!");
            this.mainLogined();
        } else {
            System.out.println("로그인에 실패하였습니다.\n다시 로그인 해주세요");
                this.loginView();
        }


    }

    public void mainLogined() throws SQLException { // 로그인 했을때 메인 화면
        // 1. 도서 조회 => checkBook()
        // 2. 커뮤니티
        // 3. 로그아웃??

        System.out.println(partition);
        System.out.println(selectMenu);
        System.out.println("1. 도서 조회");
        System.out.println("2. 커뮤니티");
        System.out.println("3. 로그아웃");
        System.out.printf(">>");
        String input = scanner.nextLine();
        if (input.equals("1")) {
//            this.checkBookView();
        } else if (input.equals("2")) {
            this.communityView();
        } else if (input.equals("3")) {
            user = null;
            System.out.println("로그아웃 하였습니다!!");
            this.mainNotLogined();
        } else {
            // 로그인 정보 없애고
            // mainNotLogined()으로 이동
            this.mainLogined();
        }

    }

    public void reportInsertView() {
        System.out.println(partition);
        System.out.println("독후감 작성");
        System.out.println(partition);

        ReportService reportService = new ReportService();
        reportService.insertReport();
    }

//    public void checkBookView() { // 도서 조회
//    	BookService bookService = new BookService();
//
//        System.out.println(partition);
//        System.out.println("조회할 항목을 선택해주세요");
//        System.out.println("1. 도서 명");
//        System.out.println("2. 저자 명");
//        System.out.println("3. 출판사 명");
//        System.out.printf(">>");
//        String input = scanner.nextLine();
//
//        if(input.equals("1")){
//            // 도서명으로 검색
//        	System.out.print("제목을 입력해주세요 ");
//        	String bname = scanner.nextLine();
////        	List<BookDTO> bookList = BookService.bookSelectTitle(bname);
//        	for (BookDTO book : bookList) {
//            System.out.println(partition);
//            System.out.println("책이름 : " + book.getBname() );
//            System.out.println("저자 " + book.getBwriter());
//            System.out.println("출판사 " + book.getBpublisher());
//        	}
//        } else if (input.equals("2")) {
//            // 저자 명으로 검색
//        	System.out.print("저자를 입력해주세요 ");
//        	String bwriter = scanner.nextLine();
//        	List<BookDTO> bookList = BookService.bookSelectTitle(bwriter);
//        	for (BookDTO book : bookList) {
//            System.out.println(partition);
//            System.out.println("책이름 : " + book.getBname() );
//            System.out.println("저자 " + book.getBwriter());
//            System.out.println("출판사 " + book.getBpublisher());
//        	}
//        } else if(input.equals("3")) {
//            // 출판사 명으로 검색
//        	System.out.print("출판사를 입력해주세요 ");
//        	String bpublisher = scanner.nextLine();
//
//        	List<BookDTO> bookList = BookService.bookSelectTitle(bpublisher);
//        	for (BookDTO book : bookList) {
//            System.out.println(partition);
//            System.out.println("책이름 : " + book.getBname() );
//            System.out.println("저자 " + book.getBwriter());
//            System.out.println("출판사 " + book.getBpublisher());
//        	}
//        } else {
//            this.checkBookView();
//        }
//        System.out.println("검색어를 입력해주세요");
//        System.out.printf(">>");
//        input = scanner.nextLine();
//
//        // 검색 목록 로직
//
//        System.out.println("조회할 도서를 선택 해주세요");
//        System.out.printf(">>");
//        input = scanner.nextLine();
//
//        this.goToHome();
//
//    }

//    public void bookInfo() {
//    	List<BookDTO> bookList = BookService.bookSelectTitle(bname);
//    	for (BookDTO book : bookList) {
//        System.out.println(partition);
//        System.out.println("책이름 : " + book.getBname() );
//        System.out.println("저자 " + book.getBwriter());
//        System.out.println("출판사 " + book.getBpublisher());
//    	}
//    }

    public void communityView() { // 커뮤니티 눌렀을때

    }

    public void goToHome() throws SQLException {
        System.out.println("1. 예약하기");
        System.out.println("2. 처음으로");
        System.out.printf(">>");
        String input = scanner.nextLine();

        if (input.equals("1")) {
            System.out.println("정말 예약 하시겠습니까?");
            System.out.printf(">>");
            input = scanner.nextLine();

        } else {
            this.mainLogined();

        }
    }


}
