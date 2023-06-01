package service;

import DAO.Rent_BookDAO;
import DB.ConnectionManager;
import DTO.BookDTO;
import DTO.Rent_BookDTO;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class Rent_BookService {
    public boolean Rent_Book(int book_pk, int user_pk){//rent_bookDTO에는 지금 반납할 책 Pk가 들어가 있음
        boolean flag = false;
        Connection con = ConnectionManager.getConnection();
        Rent_BookDAO rent_bookDAO = new Rent_BookDAO();
        if(rent_bookDAO.Insert_Rent_Book(book_pk, user_pk)) {
            flag = true;
        }
        return flag;
    }
    public List<BookDTO> RentBookUser(int user_pk) {
        Rent_BookDAO dao = new Rent_BookDAO();
        List<BookDTO> bookList = dao.RentBookUser(user_pk);

        return bookList;
    }
    public boolean Return_Book(int book_pk){
        boolean flag = false;
        Connection con = ConnectionManager.getConnection();
        Rent_BookDAO rent_bookDAO = new Rent_BookDAO();
        if(rent_bookDAO.Update_Rent_Book(book_pk)){//delete하는데 성공했으면 flag를 true로 바꿔서 main에 flag값 반환 값이용할거면 이용하기
            flag = true;
        }
        return flag;
    }
    public boolean IsPass_DeadLine(int user_num){//대출 시 데드라인이 넘었는지 확인함
        boolean flag = false;

        Rent_BookDAO rent_bookDAO = new Rent_BookDAO();
        ArrayList<String> CDT = rent_bookDAO.Get_CDT(user_num);

        for(String get_CDT : CDT){
            String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Date now_date = null;
            Date CDT_date = null;
            try {
                now_date = new SimpleDateFormat("yyyy-MM-dd").parse(now);

                CDT_date = new SimpleDateFormat("yyyy-MM-dd").parse(get_CDT);
                long diffsec = CDT_date.getTime()-now_date.getTime();
                long diffDays = diffsec / (24 * 60 * 60 * 1000);

                if(-diffDays > 7){
                    System.out.println(-diffDays + "일 동안 대출할 수 없습니다.");
                    flag = true;
                }
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        }
        return flag;
    }
    public boolean permit_rent(int book_no){//book_no가 돼야하나 user_no가 돼야하나
        //user_no로 할 경우 user가 빌린 책리스트를 조회 후 반납할 책의 pk를 찾아야함 그럼 반납할 책의 이름도 알아야함
        int penalty = 0;
        boolean permit = false;

        Rent_BookDAO rent_bookDAO = new Rent_BookDAO();
        Rent_BookDTO rent_bookDTO = rent_bookDAO.Get_RBook_UDTCDT(book_no);

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy/MM/dd"));
        Date now_date = null;
        Date CDT_date = null;
        Date UDT_date = null;

        try {
            now_date = new SimpleDateFormat("yy/MM/dd").parse(now);

            CDT_date = new SimpleDateFormat("yy/MM/dd").parse(rent_bookDTO.getCDT());
            UDT_date = new SimpleDateFormat("yy/MM/dd").parse(rent_bookDTO.getUDT());
            long diffUDT_CDT = (UDT_date.getTime()-CDT_date.getTime())/ (24 * 60 * 60 * 1000);
            if(diffUDT_CDT > 7 ){
                if(now_date.getTime()-UDT_date.getTime() > diffUDT_CDT){
                    permit = true;
                }
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        return permit;
    }
    /*public boolean Is_Pass_DeadLine(Rent_BookDTO rent_bookDTO){
        boolean flag = false;

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy/MM/dd"));
        Date now_date = null;
        Date CDT_date = null;
        try {
            now_date = new SimpleDateFormat("yy/MM/dd").parse(now);
            String CDT = rent_bookDTO.getCDT();
            CDT_date = new SimpleDateFormat("yy/MM/dd").parse(CDT);
            long diffsec = CDT_date.getTime()-now_date.getTime();
            long diffDays = diffsec / (24 * 60 * 60 * 1000);

            if(diffDays > 7){
                flag = true;
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return flag;
    }
    public*/
}
