import DB.ConnectionManager;
import DTO.BookDTO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class AddBooks {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        ArrayList<BookDTO> data = new ArrayList<>();
        //파일 접속
        File file = new File("./data/book_data.csv");
        //파일 한 라인 읽기
        FileReader fr = new FileReader(file);
        //라인 분리하기
        BufferedReader br = new BufferedReader(fr);

        String line = null;
        BookDTO dto = null;
        int no = 0;
        // 현재 날짜 구하기
        LocalDate now = LocalDate.now();

        // 포맷 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 포맷 적용
        String formatedNow = now.format(formatter);

        while ((line = br.readLine()) != null) {
            //System.out.println(line);
            //하나의 라인에서 11개의 데이터 분리
            String[] temp = line.split(",");
            String bname = temp[0].trim();
            String bwriter = temp[1].trim();
            String bpublisher = temp[2].trim();
            //DTO객체 만들기
            //리스트에 저장하기
            data.add(
                    new BookDTO(no, bname, bwriter, bpublisher, null, formatedNow, null)
            );
            no++;
        }


        //위의 작업 1000번 반복
        br.close();
        fr.close();
        Connection con = null;

        try{
            con = ConnectionManager.getConnection();
            con.setAutoCommit(false);
            int count = 0;
            // sql 작성
            String sql = "insert into book(bname, bwriter, bpublisher, cdt) values (?,?,?,?)";

            // 쿼리 (전용) 전송 통로 생성
            PreparedStatement pstmt = con.prepareStatement(sql);

            for (BookDTO dto1 : data) {
                // 통로를 통해서 쿼리 실행
                pstmt.setString(1, dto1.getBname());
                pstmt.setString(2, dto1.getBwriter());
                pstmt.setString(3, dto1.getBpublisher());
                pstmt.setString(4, dto1.getCDT());
                pstmt.addBatch();
                count++;
                if(count % 100 == 0) {
                    pstmt.executeBatch();
                    count = 0;
                    System.out.println("100개 입력");
                }
            }

            if(count > 0) {
                pstmt.executeBatch();
                System.out.println("final" + count + "개 입력");
            }
            con.commit();
            con.setAutoCommit(true);
            pstmt.close();
            con.close();
            System.out.println(data.size() + "개 자료 입력완료");
        } catch(SQLException e) {
            try{
                con.rollback();
                System.out.println(e.getMessage() + "\n위의 이유로 작업이 취소되었습니다.");
                con.setAutoCommit(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}
