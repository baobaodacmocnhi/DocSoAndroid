package vn.com.capnuoctanhoa.docsoandroid.Class;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class CEntityParent {
    private String ID;
    private String MLT;
    private String DanhBo;
    private String HoTen;
    private String DiaChi;
    private String SoNha;
    private String TenDuong;
    private String ModifyDate;
    private String Hieu;
    private String Co;
    private String SoThan;
    private ArrayList<CEntityChild> lstHoaDon;
    private String CreateDate;
    private String GiaBieu;
    private String DinhMuc;
    private String DinhMucHN;
    private String ViTri1;
    private String ViTri2;
    private boolean Gieng;
    private String DienThoai;
    private String ChiSoMoi;
    private String CodeMoi;
    private String TieuThuMoi;
    private String ChiSo0;
    private String Code0;
    private String TieuThu0;
    private String ChiSo1;
    private String Code1;
    private String TieuThu1;
    private String ChiSo2;
    private String Code2;
    private String TieuThu2;
    private String TBTT;
    private String TienNuoc;
    private String ThueGTGT;
    private String PhiBVMT;
    private String PhiBVMT_Thue;
    private String TongCong;
    private String Nam;
    private String Ky;
    private String Dot;
    private String NgayThuTien;
    private boolean SoChinh;
    private ArrayList<String> lstCaptureString;
    private String CuaHangThuHo;
    private String TuNgay;
    private String DenNgay;
    private boolean GhiHinh;

    public CEntityParent() {
        ID = "";
        MLT = "";
        DanhBo = "";
        HoTen = "";
        SoNha = "";
        TenDuong = "";
        DiaChi = "";
        ModifyDate = "";
        Hieu = "";
        Co = "";
        SoThan = "";
        lstHoaDon = new ArrayList<CEntityChild>();
        CreateDate = "";
        GiaBieu = "";
        DinhMuc = "";
        DinhMucHN = "";
        ViTri1 = "";
        ViTri2 = "";
        Gieng = false;
        DienThoai = "";
        ChiSoMoi = "";
        CodeMoi = "";
        TieuThuMoi = "";
        ChiSo0 = "";
        Code0 = "";
        TieuThu0 = "";
        ChiSo1 = "";
        Code1 = "";
        TieuThu1 = "";
        ChiSo2 = "";
        Code2 = "";
        TieuThu2 = "";
        TBTT = "";
        TienNuoc = "";
        ThueGTGT = "";
        PhiBVMT = "";
        PhiBVMT_Thue = "";
        TongCong = "";
        Nam = "";
        Ky = "";
        Dot = "";
        NgayThuTien = "";
        SoChinh = false;
        lstCaptureString = new ArrayList<>();
        CuaHangThuHo = "";
        TuNgay = DenNgay = "";
        GhiHinh = false;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMLT() {
        return MLT;
    }

    public void setMLT(String MLT) {
        this.MLT = MLT;
    }

    public String getDanhBo() {
        return DanhBo;
    }

    public void setDanhBo(String danhBo) {
        DanhBo = danhBo;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getSoNha() {
        return SoNha;
    }

    public void setSoNha(String soNha) {
        SoNha = soNha;
    }

    public String getTenDuong() {
        return TenDuong;
    }

    public void setTenDuong(String tenDuong) {
        TenDuong = tenDuong;
    }

    public String getModifyDate() {
        return ModifyDate;
    }

    public void setModifyDate(String modifyDate) {
        ModifyDate = modifyDate;
    }

    public String getHieu() {
        return Hieu;
    }

    public void setHieu(String hieu) {
        Hieu = hieu;
    }

    public String getCo() {
        return Co;
    }

    public void setCo(String co) {
        Co = co;
    }

    public String getSoThan() {
        return SoThan;
    }

    public void setSoThan(String soThan) {
        SoThan = soThan;
    }

    public ArrayList<CEntityChild> getLstHoaDon() {
        return lstHoaDon;
    }

    public void setLstHoaDon(ArrayList<CEntityChild> lstHoaDon) {
        this.lstHoaDon = lstHoaDon;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getGiaBieu() {
        return GiaBieu;
    }

    public void setGiaBieu(String giaBieu) {
        GiaBieu = giaBieu;
    }

    public String getDinhMuc() {
        return DinhMuc;
    }

    public void setDinhMuc(String dinhMuc) {
        DinhMuc = dinhMuc;
    }

    public String getDinhMucHN() {
        return DinhMucHN;
    }

    public void setDinhMucHN(String dinhMucHN) {
        DinhMucHN = dinhMucHN;
    }

    public String getViTri1() {
        return ViTri1;
    }

    public void setViTri1(String viTri1) {
        ViTri1 = viTri1;
    }

    public String getViTri2() {
        return ViTri2;
    }

    public void setViTri2(String viTri2) {
        ViTri2 = viTri2;
    }

    public boolean isGieng() {
        return Gieng;
    }

    public void setGieng(boolean gieng) {
        Gieng = gieng;
    }

    public String getDienThoai() {
        return DienThoai;
    }

    public void setDienThoai(String dienThoai) {
        DienThoai = dienThoai;
    }

    public String getChiSoMoi() {
        return ChiSoMoi;
    }

    public void setChiSoMoi(String chiSoMoi) {
        this.ChiSoMoi = chiSoMoi;
    }

    public String getCodeMoi() {
        return CodeMoi;
    }

    public void setCodeMoi(String codeMoi) {
        CodeMoi = codeMoi;
    }

    public String getChiSo0() {
        return ChiSo0;
    }

    public void setChiSo0(String chiSo0) {
        ChiSo0 = chiSo0;
    }

    public String getCode0() {
        return Code0;
    }

    public void setCode0(String code0) {
        Code0 = code0;
    }

    public String getChiSo1() {
        return ChiSo1;
    }

    public void setChiSo1(String chiSo1) {
        ChiSo1 = chiSo1;
    }

    public String getCode1() {
        return Code1;
    }

    public void setCode1(String code1) {
        Code1 = code1;
    }

    public String getChiSo2() {
        return ChiSo2;
    }

    public void setChiSo2(String chiSo2) {
        ChiSo2 = chiSo2;
    }

    public String getCode2() {
        return Code2;
    }

    public void setCode2(String code2) {
        Code2 = code2;
    }

    public String getTBTT() {
        return TBTT;
    }

    public void setTBTT(String TBTT) {
        this.TBTT = TBTT;
    }

    public String getTieuThuMoi() {
        return TieuThuMoi;
    }

    public void setTieuThuMoi(String tieuThuMoi) {
        TieuThuMoi = tieuThuMoi;
    }

    public String getTieuThu0() {
        return TieuThu0;
    }

    public void setTieuThu0(String tieuThu0) {
        TieuThu0 = tieuThu0;
    }

    public String getTieuThu1() {
        return TieuThu1;
    }

    public void setTieuThu1(String tieuThu1) {
        TieuThu1 = tieuThu1;
    }

    public String getTieuThu2() {
        return TieuThu2;
    }

    public void setTieuThu2(String tieuThu2) {
        TieuThu2 = tieuThu2;
    }

    public String getTienNuoc() {
        return TienNuoc;
    }

    public void setTienNuoc(String tienNuoc) {
        TienNuoc = tienNuoc;
    }

    public String getThueGTGT() {
        return ThueGTGT;
    }

    public void setThueGTGT(String thueGTGT) {
        ThueGTGT = thueGTGT;
    }

    public String getPhiBVMT() {
        return PhiBVMT;
    }

    public void setPhiBVMT(String phiBVMT) {
        PhiBVMT = phiBVMT;
    }

    public String getPhiBVMT_Thue() {
        return PhiBVMT_Thue;
    }

    public void setPhiBVMT_Thue(String phiBVMT_Thue) {
        PhiBVMT_Thue = phiBVMT_Thue;
    }

    public String getTongCong() {
        return TongCong;
    }

    public void setTongCong(String tongCong) {
        TongCong = tongCong;
    }

    public String getNam() {
        return Nam;
    }

    public void setNam(String nam) {
        Nam = nam;
    }

    public String getKy() {
        return Ky;
    }

    public void setKy(String ky) {
        Ky = ky;
    }

    public String getDot() {
        return Dot;
    }

    public void setDot(String dot) {
        Dot = dot;
    }

    public ArrayList<String> getLstCaptureString() {
        return lstCaptureString;
    }

    public void setLstCaptureString(ArrayList<String> lstCaptureString) {
        this.lstCaptureString = lstCaptureString;
    }

    public String getNgayThuTien() {
        return NgayThuTien;
    }

    public void setNgayThuTien(String ngayThuTien) {
        NgayThuTien = ngayThuTien;
    }

    public boolean isSoChinh() {
        return SoChinh;
    }

    public void setSoChinh(boolean soChinh) {
        SoChinh = soChinh;
    }

    public String getTuNgay() {
        return TuNgay;
    }

    public void setTuNgay(String tuNgay) {
        TuNgay = tuNgay;
    }

    public String getDenNgay() {
        return DenNgay;
    }

    public void setDenNgay(String denNgay) {
        DenNgay = denNgay;
    }

    public String getCuaHangThuHo() {
        return CuaHangThuHo;
    }

    public void setCuaHangThuHo(String cuaHangThuHo) {
        CuaHangThuHo = cuaHangThuHo;
    }

    public boolean isGhiHinh() {
        return GhiHinh;
    }

    public void setGhiHinh(boolean ghiHinh) {
        GhiHinh = ghiHinh;
    }

    public void setCEntityParent(CEntityParent entityParent) {
        ID = entityParent.getID();
        MLT = entityParent.getMLT();
        DanhBo = entityParent.getDanhBo();
        HoTen = entityParent.getHoTen();
        DiaChi = entityParent.getDiaChi();
        SoNha = entityParent.getSoNha();
        TenDuong = entityParent.getTenDuong();
        ModifyDate = entityParent.getModifyDate();
        Hieu = entityParent.getHieu();
        Co = entityParent.getCo();
        SoThan = entityParent.getSoThan();
        lstHoaDon = entityParent.getLstHoaDon();
        CreateDate = entityParent.getCreateDate();
        GiaBieu = entityParent.getGiaBieu();
        DinhMuc = entityParent.getDinhMuc();
        DinhMucHN = entityParent.getDinhMucHN();
        ViTri1 = entityParent.getViTri1();
        ViTri2 = entityParent.getViTri2();
        Gieng = entityParent.isGieng();
        DienThoai = entityParent.getDienThoai();
        ChiSoMoi = entityParent.getChiSoMoi();
        CodeMoi = entityParent.getCodeMoi();
        TieuThuMoi = entityParent.getTieuThuMoi();
        ChiSo0 = entityParent.getChiSo0();
        Code0 = entityParent.getCode0();
        TieuThu0 = entityParent.getTieuThu0();
        ChiSo1 = entityParent.getChiSo1();
        Code1 = entityParent.getCode1();
        TieuThu1 = entityParent.getTieuThu1();
        ChiSo2 = entityParent.getChiSo2();
        Code2 = entityParent.getCode2();
        TieuThu2 = entityParent.getTieuThu2();
        TBTT = entityParent.getTBTT();
        TienNuoc = entityParent.getTienNuoc();
        ThueGTGT = entityParent.getThueGTGT();
        PhiBVMT = entityParent.getPhiBVMT();
        PhiBVMT_Thue = entityParent.getPhiBVMT_Thue();
        TongCong = entityParent.getTongCong();
        Nam = entityParent.getNam();
        Ky = entityParent.getKy();
        Dot = entityParent.getDot();
        NgayThuTien = entityParent.getNgayThuTien();
        SoChinh = entityParent.isSoChinh();
        lstCaptureString = entityParent.getLstCaptureString();
        CuaHangThuHo = entityParent.getCuaHangThuHo();
        TuNgay = entityParent.getTuNgay();
        DenNgay = entityParent.getDenNgay();
        GhiHinh = entityParent.isGhiHinh();
    }


}
