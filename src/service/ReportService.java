package service;

import DAO.ReportDAO;
import DTO.ReportDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReportService {
    public ReportDAO dao;

    public ReportService(){
        dao = new ReportDAO();
    }

    Scanner scanner = new Scanner(System.in);

    //독후감 작성
    public void insertReport(int bookNo, int userNo){
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
        }
    }

    public List<ReportDTO> findByUserNo(int userNo){
        String sql = "select b.bname, rb.book_no, rb.user_no from rent_book rb " +
                "JOIN book b ON rb.book_no = b.no where user_no = ?";
        List<ReportDTO> list = dao.findByUserNo(sql, userNo);
        int cnt = list.size();
        System.out.println("=============================================");
        System.out.println("독후감 작성할 도서 번호를 입력해주세요.");
        System.out.println(">>");
        int menuNum = Integer.parseInt(scanner.nextLine());
        if(menuNum <= cnt){
            System.out.println("["+list.get(menuNum-1).getBname()+"] 독후감 작성하시겠습니까?");
            System.out.println("1.작성");
            System.out.println("2.취소");
            System.out.println(">>");
            String menuNum2 = scanner.nextLine();
            if("1".equals(menuNum2)){
                int bookNo = list.get(menuNum-1).getBook_no();
                int userNo2 = list.get(menuNum-1).getUser_no();
                this.insertReport(bookNo, userNo2);
            }else {
                this.findByUserNo(userNo);
            }
        }else{
            this.findByUserNo(userNo);
        }

        return list;
    }
}