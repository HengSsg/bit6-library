
import DTO.BookDTO;
import DTO.Rent_BookDTO;
import DTO.UserDTO;
import service.BookService;
import service.Rent_BookService;
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


    public void mainNotLogined() { // 첫 화면
        System.out.println(partition);
        System.out.println("환영합니다 도서 관리 시스템에 오신것을 환영합니다!!");
        System.out.println(selectMenu);
        System.out.println("1. 도서 조회");
        System.out.println("2. 로그인");
        System.out.println(partition);
        System.out.printf(">>");
        String input = scanner.nextLine();

        try {
            if ("1".equals(input)) {
                this.checkBookView();
            } else if ("2".equals(input)) {
                this.loginView();
            } else {
                this.mainNotLogined();
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            System.out.println(user.getNo());
            System.out.println("로그인 성공하였습니다!!");
            this.mainLogined();
        } else {
            System.out.println("로그인에 실패하였습니다.\n다시 로그인 해주세요");
            this.loginView();
        }


    }

    public void mainLogined() { // 로그인 했을때 메인 화면
        // 1. 도서 조회 => checkBook()
        // 2. 커뮤니티
        // 3. 로그아웃??

        System.out.println(partition);
        System.out.println(selectMenu);
        System.out.println("1. 도서 조회");
        System.out.println("2. 커뮤니티");
        System.out.println("3. 도서반납");
        System.out.println("4. 로그아웃");
        System.out.printf(">>");
        String input = scanner.nextLine();
        if ("1".equals(input)) {
            this.checkBookView();
        } else if ("2".equals(input)) {
            this.communityView();
        } else if ("3".equals(input)) {

        } else if ("4".equals(input)) {
            user = null;
            System.out.println("로그아웃 하였습니다!!");
            this.mainNotLogined();
        }

    }

    public void reportInsertView() {
        System.out.println(partition);
        System.out.println("독후감 작성");
        System.out.println(partition);

        ReportService reportService = new ReportService();
        reportService.insertReport();
    }

    public void checkBookView() { // 도서 조회

         System.out.println(partition);
        System.out.println("조회할 항목을 선택해주세요");
        System.out.println("1. 도서 명");
        System.out.println("2. 저자 명");
        System.out.println("3. 출판사 명");
        System.out.println(partition);

        System.out.print(">>");
        String input = scanner.nextLine();

        BookService bookService = new BookService();
        if ("1".equals(input)) {
            // 도서명으로 검색
            System.out.println("제목을 입력해주세요 ");
            System.out.print(" >> ");
            String bname = scanner.nextLine();

            List<BookDTO> bookList = bookService.bookSelectTitle(bname);
            int i = 0;
            for (BookDTO book : bookList) {
                String bookState = bookService.bookState();
                System.out.println(partition);
                System.out.println("책번호: "+ ++i);
                System.out.println("책이름: " + book.getBname());
                System.out.println("저자: " + book.getBwriter());
                System.out.println("출판사: " + book.getBpublisher());
                System.out.println("상태 : " + bookState);
                System.out.println(partition);
            }
            if(user != null) {
                this.rentbookview(bookList);
            }
        } else if (input.equals("2")) {
            // 저자 명으로 검색
            System.out.print("저자를 입력해주세요 ");
            System.out.printf(">> ");
            String bwriter = scanner.nextLine();

            List<BookDTO> bookList = bookService.bookSelectWriter(bwriter);

            System.out.println(bookList);
            int i = 0;
            for (BookDTO book : bookList) {
                String bookState = bookService.bookState();
                System.out.println(partition);
                System.out.println("책번호: "+ ++i);
                System.out.println("책이름: " + book.getBname());
                System.out.println("저자: " + book.getBwriter());
                System.out.println("출판사: " + book.getBpublisher());
                System.out.println("상태 : " + bookState);
                System.out.println(partition);
            }
            if(user != null) {
                this.rentbookview(bookList);
            }

        } else if (input.equals("3")) {
            // 출판사 명으로 검색
            System.out.print("출판사를 입력해주세요 ");
            System.out.printf(" >> ");
            String bpublisher = scanner.nextLine();

            List<BookDTO> bookList = bookService.bookSelectPublisher(bpublisher);
            int i = 0;
            for (BookDTO book : bookList) {
                String bookState = bookService.bookState();
                System.out.println(partition);
                System.out.println("책번호: "+ ++i);
                System.out.println("책이름: " + book.getBname());
                System.out.println("저자: " + book.getBwriter());
                System.out.println("출판사: " + book.getBpublisher());
                System.out.println("상태 : " + bookState);
                System.out.println(partition);
            }
            if(user != null) {
                this.rentbookview(bookList);
            }
        } else {
            this.checkBookView();
        }

        this.goToHome();

    }
    public void returnBook(){

    }
    public void rentbookview(List bookList) {
        System.out.println("대출할 책 번호를 입력해주세요");
        System.out.print(">>");
        String input = scanner.nextLine();
        BookDTO book = (BookDTO) bookList.get(Integer.parseInt(input)-1);
        String bname = book.getBname();
        System.out.println(bname+" 책을 대출하시겠습니까?");;
        System.out.println("1. 대출하기");
        System.out.println("2. 나가기");
        System.out.print(">>");
        System.out.println(book.getNo()+"+"+user.getNo());
        input = scanner.nextLine();
        if ("1".equals(input)) {
            Rent_BookService rent_bookService = new Rent_BookService();
            if(rent_bookService.Rent_Book(book.getNo(), user.getNo())){
                System.out.println(bname+"정상 데이터 베이스 삽입");
                this.mainLogined();
            }
        }else if("2".equals(input)) {
            this.mainLogined();
        }else{
            this.rentbookview(bookList);
        }
    }
    public void communityView() { // 커뮤니티 눌렀을때
        System.out.println(partition);
        System.out.println(selectMenu);
        System.out.println("1. 독후감 작성");
        System.out.printf(">>");
        String input = scanner.nextLine();
        if (input.equals("1")) {

        } else {

        }
    }

    public void RentBookView() {
        System.out.println(partition);
        System.out.println("반납할 책 이름을 입력해주세요");
        System.out.println(">>");
    }

    public void goToHome() {
        System.out.println("1. 예약하기");
        System.out.println("2. 대출하기");
        System.out.println("3. 처음으로");
        System.out.printf(">>");
        String input = scanner.nextLine();

        if (input.equals("1")) {
            System.out.println("정말 예약 하시겠습니까?");
            System.out.println("1. 예");
            System.out.println("2. 아니요");
            System.out.printf(">>");
            input = scanner.nextLine();

            if (input.equals("1")) {
                // 책 예약 로직 추가해야 함
                System.out.println("예약이 완료 되었습니다.");
                this.mainLogined();

            } else {
                this.goToHome();
            }

        } else if (input.equals("2")) {
            System.out.println("정말 대출 하시겠습니까?");
            System.out.println("1. 예");
            System.out.println("2. 아니요");
            System.out.printf(">>");
            input = scanner.nextLine();

            if (input.equals("1")) {
                // 책 예약 로직 추가해야 함
                System.out.println("대출이 완료 되었습니다.");
                this.mainLogined();

            } else {
                this.goToHome();
            }
        } else {
            this.mainLogined();

        }

    }
}

