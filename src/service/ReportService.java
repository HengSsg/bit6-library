package service;

import DAO.ReportDAO;
import DTO.ReportDTO;

import java.util.List;
import java.util.Scanner;

public class ReportService {
    public ReportDAO dao;
    public UserService userService;
    public ReportService(){
        dao = new ReportDAO();
        userService = new UserService();
    }

    Scanner scanner = new Scanner(System.in);

    //독후감 작성
    public void insertReport(int bookNo, int userNo, String grade){
        ReportDTO reportDTO = new ReportDTO();

        System.out.println("제목: ");
        reportDTO.setTitle(scanner.nextLine());

        System.out.println("내용: ");
        reportDTO.setContents(scanner.nextLine());

        reportDTO.setBook_no(bookNo);
        reportDTO.setUser_no(userNo);

        System.out.println("=============================================");
        System.out.println("저장하시겠습니까?");
        System.out.println("1.저장");
        System.out.println("2.취소");
        System.out.println(">>");
        String menuNum = scanner.nextLine();
        if("1".equals(menuNum)){
            String sql = "INSERT INTO report (title, contents, user_no, book_no, cdt) " +
                    "VALUES (?,?,?,?,now())";
            dao.insertReport(sql, reportDTO);
            userService.upgradeAuth(grade);

        }
    }

    //유저가 대출한 도서 목록 조회
    public void findByUserNo(int userNo, String grade){
        String sql = "select b.bname, rb.book_no, rb.user_no from rent_book rb " +
                "JOIN book b ON rb.book_no = b.no where user_no = ?";
        List<ReportDTO> list = dao.findByUserNo(sql, userNo);
        int cnt = list.size();
        if(cnt > 0) {
            System.out.println("=============================================");
            System.out.println("독후감 작성할 도서 번호를 입력해주세요.");
            System.out.println(">>");
            int menuNum = Integer.parseInt(scanner.nextLine());
            if (menuNum <= cnt) {
                System.out.println("[" + list.get(menuNum - 1).getBname() + "] 독후감 작성하시겠습니까?");
                System.out.println("1.작성");
                System.out.println("2.취소");
                System.out.println(">>");
            String menuNum2 = scanner.nextLine();
            if("1".equals(menuNum2)){
                int bookNo = list.get(menuNum-1).getBook_no();
                int userNo2 = list.get(menuNum-1).getUser_no();
                this.insertReport(bookNo, userNo2, grade);
            } else if ("2".equals(menuNum2)) {

                } else {
                    this.findByUserNo(userNo, grade);
                }
            } else {
                this.findByUserNo(userNo, grade);
            }
        }else{
            System.out.println("도서 대출내역이 없습니다. 대출한 도서에 한하여 독후감 작성이 가능합니다.");
            System.out.println("=============================================");
        }
    }

    //유저가 작성한 독후감 조회
    public void reportByUserNo(int userNo){
        String sql = "select * from report " +
                "where user_no = ?";
        List<ReportDTO> list = dao.reportByUserNo(sql, userNo);
        int cnt = list.size();
        if(cnt > 0) {
            System.out.println("=============================================");
            System.out.println("조회할 독후감을 선택해주세요.");
            System.out.println(">>");
            int menuNum = Integer.parseInt(scanner.nextLine());
            if (menuNum <= cnt) {
                System.out.println("=============================================");
                System.out.println("독후감 제목: " + list.get(menuNum - 1).getTitle());
                System.out.println("독후감 내용: " + list.get(menuNum - 1).getContents());
                System.out.println("작성일자: " + list.get(menuNum - 1).getCDT());
            } else {
                this.reportByUserNo(userNo);
            }
        } else{
            System.out.println("작성한 독후감이 존재하지 않습니다.");
        }
    }
}