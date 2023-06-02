import DTO.BookDTO;
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
    Rent_BookService rentBookService = new Rent_BookService();


    public static void main(String[] args) {
        Main main = new Main();
        main.mainNotLogined();
    }

    public UserDTO getUser() {
        return user;
    }


    public void mainNotLogined() { // 첫 화면
        System.out.println(partition);
        System.out.println("환영합니다 도서 관리 시스템에 오신것을 환영합니다!!");
        System.out.println(selectMenu);
        System.out.println("1. 도서 조회");
        System.out.println("2. 로그인");
        System.out.println("3. 종료");
        System.out.println(partition);
        System.out.printf(">>");
        String input = scanner.nextLine();

        try {
            if ("1".equals(input)) {
                this.checkBookView();
            } else if ("2".equals(input)) {
                this.loginView();
            } else if ("3".equals(input)) {
                this.close();
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
            //System.out.println(user.getNo());
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
        System.out.println("5. 종료");
        System.out.printf(">>");
        String input = scanner.nextLine();
        if ("1".equals(input)) {
            this.checkBookView();
        } else if ("2".equals(input)) {
            this.communityView();
        } else if ("3".equals(input)) {
            this.ReturnBookView();
        } else if ("4".equals(input)) {
            user = null;
            System.out.println("로그아웃 하였습니다!!");
            this.mainNotLogined();
        } else if ("5".equals(input)) {
            this.close();
        } else{
            this.mainLogined();
        }

    }

//    public void reportInsertView() {
//        System.out.println(partition);
//        System.out.println("독후감 작성");
//        System.out.println(partition);
//
//        ReportService reportService = new ReportService();
//        reportService.findByUserNo(user.getNo());
//    }

    public void ReturnBookView() {
        System.out.println(partition);
        List<BookDTO> returnbookList = rentBookService.RentBookUser(user.getNo());
        int i = 0;
        for (BookDTO book : returnbookList) {
            System.out.println(partition);
            System.out.println("책번호: " + ++i);
            System.out.println("책이름: " + book.getBname());
            System.out.println("저자: " + book.getBwriter());
            System.out.println("출판사: " + book.getBpublisher());
            System.out.println(partition);
        }
        if (returnbookList.size() > 0) {
            System.out.println("반납할 책 번호 입력");
            System.out.print(">> ");
            String input = scanner.nextLine();
            try{
                BookDTO book = (BookDTO) returnbookList.get(Integer.parseInt(input) - 1);
                String bname = book.getBname();
                System.out.println(bname + " 책을 반납하시겠습니까?");
                System.out.println("1. 반납하기");
                System.out.println("2. 나가기");
                System.out.print(">> ");

                input = scanner.nextLine();

                if ("1".equals(input)) {
                    Rent_BookService rent_bookService = new Rent_BookService();
                    if (rent_bookService.Return_Book(book.getNo())) {
                        System.out.println(bname + "정상 데이터 베이스 반영");
                        this.mainLogined();
                    }
                } else if ("2".equals(input)) {
                    this.mainLogined();
                } else {
                    this.ReturnBookView();
                }
            }catch(IndexOutOfBoundsException e){
                System.out.println("반납할 책 번호를 다시 입력해주세요");
                this.ReturnBookView();
            }
        } else {
            System.out.println("반납할 책이 없습니다.");
            this.mainLogined();
        }

    }

    public void checkBookView() { // 도서 조회

        System.out.println(partition);
        System.out.println("조회할 항목을 선택해주세요");
        System.out.println("1. 도서 명");
        System.out.println("2. 저자 명");
        System.out.println("3. 출판사 명");
        System.out.println("4. 최다 대출 도서 확인");
        System.out.println("5. 최소 대출 도서 확인");
        System.out.println("6. 메인화면으로 이동");
        System.out.println(partition);

        System.out.print(">>");

        String input = scanner.nextLine();

        BookService bookService = new BookService();

        if (input.equals("1")) {
            // 도서명으로 검색
            System.out.println("제목을 입력해주세요 ");
            System.out.printf(" >> ");
            String bname = scanner.nextLine();
            List<BookDTO> bookList = bookService.bookSelectTitle(bname);
            if(bookList.size() == 0) {
                System.out.println("찾으시는 도서가 없습니다.");
                this.checkBookView();
            }
            int i = 0;
            for (BookDTO book : bookList) {
                System.out.println(partition);
                System.out.println("번호: " + ++i);
                System.out.println("책이름: " + book.getBname());
                System.out.println("저자: " + book.getBwriter());
                System.out.println("출판사: " + book.getBpublisher());
                System.out.println("상태 : " + book.getRentMsg());
                System.out.println(partition);
            }
            this.rentbookview(bookList);

        } else if (input.equals("2")) {
            // 저자 명으로 검색
            System.out.print("저자를 입력해주세요 ");
            System.out.printf(" >> ");
            String bwriter = scanner.nextLine();

            List<BookDTO> bookList = bookService.bookSelectWriter(bwriter);
            if(bookList.size() == 0) {
                System.out.println("찾으시는 저자가 없습니다.");
                this.checkBookView();
            }
            int i = 0;
            for (BookDTO book : bookList) {
                System.out.println(partition);
                System.out.println("번호: " + ++i);
                System.out.println("책이름: " + book.getBname());
                System.out.println("저자: " + book.getBwriter());
                System.out.println("출판사: " + book.getBpublisher());
                System.out.println("상태 : " + book.getRentMsg());
                System.out.println(partition);
            }
            this.rentbookview(bookList);

        } else if (input.equals("3")) {
            // 출판사 명으로 검색
            System.out.print("출판사를 입력해주세요 ");
            System.out.printf(" >> ");
            String bpublisher = scanner.nextLine();

            List<BookDTO> bookList = bookService.bookSelectPublisher(bpublisher);
            if(bookList.size() == 0) {
                System.out.println("찾으시는 출판사가 없습니다.");
                this.checkBookView();
            }
            int i = 0;
            for (BookDTO book : bookList) {
                System.out.println(partition);
                System.out.println("번호: " + ++i);
                System.out.println("책이름: " + book.getBname());
                System.out.println("저자: " + book.getBwriter());
                System.out.println("출판사: " + book.getBpublisher());
                System.out.println("상태 : " + book.getRentMsg());
                System.out.println(partition);
            }
            this.rentbookview(bookList);

        } else if (input.equals("4")) {

            System.out.println(partition);

            String mostBorrowedBook = bookService.getMostBorrowedBookName();

            System.out.println("최다 대출 도서는 " + mostBorrowedBook + " 입니다.");

        } else if (input.equals("5")) {

            System.out.println(partition);

            String leastBorrowBook = bookService.getLeastBorrowBook();

            System.out.println("최소 대출 도서는 " + leastBorrowBook + " 입니다.");
        } else if ("6".equals(input)) {
            if(user == null) {
                this.mainNotLogined();
            } else {
                this.mainLogined();
            }
        } else {
            this.checkBookView();
        }

//        if (user != null) {
//            this.goToHome();
//        } else {
//            this.mainNotLogined();
//        }

    }


    public void returnBook() {

    }

    public void rentbookview(List bookList) {
        System.out.println("대출할 책 번호를 입력해주세요");
        System.out.print(">>");
        String input = scanner.nextLine();
        BookDTO book = (BookDTO) bookList.get(Integer.parseInt(input) - 1);
        String bname = book.getBname();
        System.out.println(bname + " 책을 대출하시겠습니까?");
        ;
        System.out.println("1. 대출하기");
        System.out.println("2. 나가기");
        System.out.print(">>");

        input = scanner.nextLine();
        if ("1".equals(input)) {
            Rent_BookService rent_bookService = new Rent_BookService();
            if (rent_bookService.IsPass_DeadLine(user.getNo())) {
                this.mainLogined();

            }else{
                int rentBookUser_num = rent_bookService.RentBookUser(user.getNo()).size();
                //System.out.println(rentBookUser_num);
                if(rent_bookService.fullMaxRent(user.getNo())<=rentBookUser_num) {
                    System.out.println("대출 가능 갯수를 넘었습니다");
                    this.mainLogined();
                }
                else if (rent_bookService.bookState(book.getNo())&&rent_bookService.Rent_Book(book.getNo(), user.getNo())) {
                    System.out.println(bname + "정상 데이터 베이스 삽입");
                    // 서비스 삽입
                    userService.upgradeAuth(user.getAUTH_code());
                    this.mainLogined();
                } else {
                    System.out.println("이미 대출중인 책입니다.");
                    this.mainLogined();
                }
            }

        } else if ("2".equals(input)) {
            this.mainLogined();
        } else {
            this.rentbookview(bookList);
        }
    }


    public void communityView() { // 커뮤니티 눌렀을때
        ReportService reportService = new ReportService();
        System.out.println(partition);
        System.out.println(selectMenu);
        int i = 1;
        String[] menu = {"커뮤니티 가입", "커뮤니티 탈퇴", "내가 쓴 독후감 보기", "독후감 작성"};

        if("1".equals(user.getComunity_YN())) {
            System.out.println(i++ +". 독후감 작성");
        }
        System.out.println(i++ +". 내가 쓴 독후감 보기");
        if (user.getComunity_YN().equals("1")) {
            System.out.println(i++ +". 커뮤니티 탈퇴");
        } else {
            System.out.println(i++ +". 커뮤니티 가입");
        }
        System.out.println(i +". 처음으로");
        System.out.printf(">>");
        String input = scanner.nextLine(); //
        if (menu[3].equals(menu[i - Integer.parseInt(input)])) {
            //독후감 작성
            reportService.findByUserNo(user.getNo(), user.getAUTH_code());
            System.out.println(selectMenu);
            System.out.println("1. 처음으로");
            System.out.printf(">>");
            String menuNum = scanner.nextLine();
            if(menuNum != null){
                this.communityView();
            }
        } else if (menu[2].equals(menu[i - Integer.parseInt(input)])) {
            //내가쓴독후감보기
            reportService.reportByUserNo(user.getNo());
            System.out.println(selectMenu);
            System.out.println("1. 처음으로");
            System.out.printf(">>");
            String menuNum = scanner.nextLine();
            if(menuNum != null){
                this.communityView();
            }
        } else if (menu[1].equals(menu[i - Integer.parseInt(input)])) {
            if (user.getComunity_YN().equals("1")) {
                System.out.println(partition);
                System.out.println("정말 탈퇴 하시겠습니까?");
                System.out.println("1. 예");
                System.out.println("2. 아니요");
                System.out.println(partition);
                input = scanner.nextLine();

                if ("1".equals(input)) {
                    boolean result =
                            userService.communityN(user.getName());
                    if (result) {
                        user.setComunity_YN("0");
                        this.communityView();
                    } else {
                        System.out.println("가입 후 한달뒤에 탈퇴가 가능합니다.");
                        System.out.println("탈퇴에 실패하였습니다.");
                        this.communityView();
                    }
                } else {
                    // 커뮤니티 페이지로 이동
                    this.communityView();
                }

            } else {
                System.out.println(partition);
                System.out.println("정말 가입 하시겠습니까?");
                System.out.println("1. 예");
                System.out.println("2. 아니요");
                System.out.println(partition);
                input = scanner.nextLine();

                if ("1".equals(input)) {
                    userService.communityY(user.getName());
                    user.setComunity_YN("1");
                    this.communityView();
                } else {
                    // 커뮤니티 페이지로 이동
                    this.communityView();
                }
            }
        } else {
            if (user == null) {
                this.mainNotLogined();
            } else {
                this.mainLogined();
            }
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
                if (user == null) {
                    this.mainNotLogined();
                } else {
                    this.mainLogined();
                }

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
                if (user == null) {
                    this.mainNotLogined();
                } else {
                    this.mainLogined();
                }

            } else {
                this.goToHome();
            }
        } else {
            if (user == null) {
                this.mainNotLogined();
            } else {
                this.mainLogined();
            }
        }
    }

    public void close() {
        System.out.println(partition);
        System.out.println("정말로 종료하시겠습니까??");
        System.out.println("1. 예");
        System.out.println("2. 아니요");
        System.out.println(partition);
        System.out.print(">>");
        String input = scanner.nextLine();

        if ("1".equals(input)) {
            System.exit(0);
        } else if ("2".equals(input)) {
            if (user != null) {
                this.mainLogined();
            } else {
                this.mainNotLogined();
            }
        } else {
            this.close();
        }
    }
}

