package vn.com.capnuoctanhoa.docsoandroid.Class;

public class CCode {
    private String Code;
    private String MoTa;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public CCode() {
        Code = MoTa = "";
    }
}
