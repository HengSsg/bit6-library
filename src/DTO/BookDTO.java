package DTO;

public class BookDTO {
	private int no;
	private String bname;
	private String bwriter;
	private String bpublisher;
	private String bgrade;
	private String CDT;
	private String display; // 전시 OR 보관 1 true 0 false( 검색 x) 
	
	public BookDTO() {
		
	}
	
	public BookDTO(int no, String bname, String bwriter, String bpublisher, String bgrade, String cDT, String display) {
		super();
		this.no = no;
		this.bname = bname;
		this.bwriter = bwriter;
		this.bpublisher = bpublisher;
		this.bgrade = bgrade;
		CDT = cDT;
		this.display = display;
	}
	
	public BookDTO(String bname, String bwriter, String bpublisher) {
		this.bname = bname;
		this.bwriter = bwriter;
		this.bpublisher = bpublisher;
	
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getBwriter() {
		return bwriter;
	}

	public void setBwriter(String bwriter) {
		this.bwriter = bwriter;
	}

	public String getBpublisher() {
		return bpublisher;
	}

	public void setBpublisher(String bpublisher) {
		this.bpublisher = bpublisher;
	}

	public String getBgrade() {
		return bgrade;
	}

	public void setBgrade(String bgrade) {
		this.bgrade = bgrade;
	}

	public String getCDT() {
		return CDT;
	}

	public void setCDT(String cDT) {
		CDT = cDT;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	@Override
	public String toString() {
		return "BookDTO [no=" + no + ", bname=" + bname + ", bwriter=" + bwriter + ", bpublisher=" + bpublisher
				+ ", bgrade=" + bgrade + ", CDT=" + CDT + ", display=" + display + "]";
	}
	
}
