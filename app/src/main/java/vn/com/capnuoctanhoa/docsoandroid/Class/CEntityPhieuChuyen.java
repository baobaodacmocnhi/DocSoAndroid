package vn.com.capnuoctanhoa.docsoandroid.Class;

import java.util.ArrayList;

public class CEntityPhieuChuyen {
    private String DanhBo;
    private String NoiDung;
    private String GhiChu;
    private String imgString;
    private String MaNV;

    public String getDanhBo() {
        return DanhBo;
    }

    public void setDanhBo(String danhBo) {
        DanhBo = danhBo;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public String getImgString() {
        return imgString;
    }

    public void setImgString(String imgString) {
        this.imgString = imgString;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public CEntityPhieuChuyen() {
        DanhBo = "";
        NoiDung = "";
        GhiChu = "";
        imgString = "";
        MaNV = "";
    }
}
