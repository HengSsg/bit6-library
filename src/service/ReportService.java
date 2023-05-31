package service;

import DAO.ReportDAO;
import DTO.ReportDTO;

import java.util.Scanner;

public class ReportService {
    public ReportDAO dao;

    public ReportService(){
        dao = new ReportDAO();
    }

    Scanner scanner = new Scanner(System.in);

    //독후감 작성
    public void insertReport(){
        ReportDTO reportDTO = new ReportDTO();

        System.out.println("제목: ");
        reportDTO.setTitle(scanner.nextLine());

        System.out.println("내용: ");
        reportDTO.setContents(scanner.nextLine());

        System.out.println("저장하시겠습니까?");
        System.out.println("-----------------------------------");
        System.out.println("1.저장 | 2.취소");
        String menuNum = scanner.nextLine();
        if("1".equals(menuNum)){
            String sql = "INSERT INTO report (title, contents, user_no, book_no, cdt) " +
                    "VALUES (?,?,4,1,now())";
            dao.insertReport(sql, reportDTO);
        }
    }
}