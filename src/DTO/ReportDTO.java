package DTO;

public class ReportDTO {
    private String title;
    private String contents;
    private int user_no;
    private int book_no;
    private String CDT;

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    //조회
    private String bname;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getUser_no() {
        return user_no;
    }

    public void setUser_no(int user_no) {
        this.user_no = user_no;
    }

    public int getBook_no() {
        return book_no;
    }

    public void setBook_no(int book_no) {
        this.book_no = book_no;
    }

    public String getCDT() {
        return CDT;
    }

    public void setCDT(String CDT) {
        this.CDT = CDT;
    }

    public String getUDT() {
        return UDT;
    }

    public void setUDT(String UDT) {
        this.UDT = UDT;
    }

    private String UDT;

}
