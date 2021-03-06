package vn.com.capnuoctanhoa.docsoandroid.Class;

public class CViewChild {
    private String ID;
    private String Row1a;
    private String Row1b;
    private String Row2a;
    private String Row2b;
    private String Row3a;
    private String Row3b;
    private String Row4a;
    private String Row4b;
    private Boolean GiaiTrach;
    private Boolean TamThu;
    private Boolean ThuHo;
    private Boolean TBDongNuoc;
    private Boolean LenhHuy;

    public CViewChild() {
        this.ID = "";
        Row1a = "";
        Row1b = "";
        Row2a = "";
        Row2b = "";
        Row3a = "";
        Row3b = "";
        Row4a = "";
        Row4b = "";
        GiaiTrach = false;
        TamThu = false;
        ThuHo = false;
         TBDongNuoc=false;
        LenhHuy=false;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getRow1a() {
        return Row1a;
    }

    public void setRow1a(String row1a) {
        Row1a = row1a;
    }

    public String getRow1b() {
        return Row1b;
    }

    public void setRow1b(String row1b) {
        Row1b = row1b;
    }

    public String getRow2a() {
        return Row2a;
    }

    public void setRow2a(String row2a) {
        Row2a = row2a;
    }

    public String getRow2b() {
        return Row2b;
    }

    public void setRow2b(String row2b) {
        Row2b = row2b;
    }

    public String getRow3a() {
        return Row3a;
    }

    public void setRow3a(String row3a) {
        Row3a = row3a;
    }

    public String getRow3b() {
        return Row3b;
    }

    public void setRow3b(String row3b) {
        Row3b = row3b;
    }

    public String getRow4a() {
        return Row4a;
    }

    public void setRow4a(String row4a) {
        Row4a = row4a;
    }

    public String getRow4b() {
        return Row4b;
    }

    public void setRow4b(String row4b) {
        Row4b = row4b;
    }

    public Boolean getGiaiTrach() {
        return GiaiTrach;
    }

    public void setGiaiTrach(Boolean giaiTrach) {
        GiaiTrach = giaiTrach;
    }

    public Boolean getTamThu() {
        return TamThu;
    }

    public void setTamThu(Boolean tamThu) {
        TamThu = tamThu;
    }

    public Boolean getThuHo() {
        return ThuHo;
    }

    public void setThuHo(Boolean thuHo) {
        ThuHo = thuHo;
    }

    public Boolean getTBDongNuoc() {
        return TBDongNuoc;
    }

    public void setTBDongNuoc(Boolean TBDongNuoc) {
        this.TBDongNuoc = TBDongNuoc;
    }

    public Boolean getLenhHuy() {
        return LenhHuy;
    }

    public void setLenhHuy(Boolean lenhHuy) {
        LenhHuy = lenhHuy;
    }


}
