package DTO;

public class Rent_BookDTO {
    private int book_no;
    private int user_no;
    private String CDT;
    private boolean rentYN;
    private String UDT;

    public Rent_BookDTO() {}
    public Rent_BookDTO(int book_no, int user_no) {
        this.book_no = book_no;
        this.user_no = user_no;
    }

    public Rent_BookDTO(int book_no, int user_no, String CDT) {
        this.book_no = book_no;
        this.user_no = user_no;
        this.CDT = CDT;
    }

    public int getBook_no() {
        return book_no;
    }

    public void setBook_no(int book_no) {
        this.book_no = book_no;
    }

    public int getUser_no() {
        return user_no;
    }

    public void setUser_no(int user_no) {
        this.user_no = user_no;
    }

    public String getCDT() {
        return CDT;
    }

    public void setCDT(String CDT) {
        this.CDT = CDT;
    }

    public boolean isRentYN() {return rentYN;}

    public void setRentYN(boolean rentYN) {this.rentYN = rentYN;}

    public String getUDT() {return UDT;}

    public void setUDT(String UDT) {this.UDT = UDT;}
}
