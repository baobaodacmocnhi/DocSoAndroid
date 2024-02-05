package vn.com.capnuoctanhoa.docsoandroid.Class;

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
    private ArrayList<CEntityChild> lstCuaHangThuHo;
    private String CreateDate;
    private String GiaBieu;
    private String DinhMuc;
    private String DinhMucHN;
    private String ViTri;
    private String KinhDoanh;
    private boolean ViTriNgoai;
    private boolean ViTriHop;
    private boolean Gieng;
    private boolean KhoaTu;
    private boolean AmSau;
    private boolean XayDung;
    private boolean DutChi_Goc;
    private boolean DutChi_Than;
    private boolean NgapNuoc;
    private boolean KetTuong;
    private boolean LapKhoaGoc;
    private boolean BeHBV;
    private boolean BeNapMatNapHBV;
    private boolean GayTayVan;
    private boolean TroNgaiThay;
    private boolean DauChungMayBom;
    private String MauSacChiGoc;
    private String DienThoai;
    private String ChiSoMoi;
    private String CodeMoi;
    private String TieuThuMoi;
    private String ChiSo0;
    private String ChiSo0In;
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
    private String CuaHangThuHo;
    private String TuNgay;
    private String DenNgay;
    private boolean GhiHinh;
    private String GhiChu;
    private boolean AnXoa;
    private boolean Sync;
    private int SH;
    private int SX;
    private int DV;
    private int HCSN;
    private String PhanMay;
    private boolean ChuBao;
    private String TinhTrang;
    private String Latitude;
    private String Longitude;
    private boolean ChuaGuiThongBao;
    private boolean ThayDK;

    public CEntityParent() {
        this.ID = "";
        this.MLT = "";
        this.DanhBo = "";
        this.HoTen = "";
        this.SoNha = "";
        this.TenDuong = "";
        this.DiaChi = "";
        this.ModifyDate = "";
        this.Hieu = "";
        this.Co = "";
        this.SoThan = "";
        this.lstHoaDon = new ArrayList<CEntityChild>();
        this.lstCuaHangThuHo = new ArrayList<CEntityChild>();
        this.CreateDate = "";
        this.GiaBieu = "";
        this.DinhMuc = "";
        this.DinhMucHN = "";
        this.ViTri = "";
        this.KinhDoanh = "";
        this.ViTriNgoai = false;
        this.ViTriHop = false;
        this.Gieng = false;
        this.KhoaTu = false;
        this.AmSau = false;
        this.XayDung = false;
        this.DutChi_Goc = false;
        this.DutChi_Than = false;
        this.NgapNuoc = false;
        this.KetTuong = false;
        this.LapKhoaGoc = false;
        this.BeHBV = false;
        this.BeNapMatNapHBV = false;
        this.GayTayVan = false;
        this.TroNgaiThay = false;
        this.DauChungMayBom = false;
        this.MauSacChiGoc = "";
        this.DienThoai = "";
        this.ChiSoMoi = "";
        this.CodeMoi = "";
        this.TieuThuMoi = "";
        this.ChiSo0 = "";
        this.ChiSo0In = "";
        this.Code0 = "";
        this.TieuThu0 = "";
        this.ChiSo1 = "";
        this.Code1 = "";
        this.TieuThu1 = "";
        this.ChiSo2 = "";
        this.Code2 = "";
        this.TieuThu2 = "";
        this.TBTT = "";
        this.TienNuoc = "";
        this.ThueGTGT = "";
        this.PhiBVMT = "";
        this.PhiBVMT_Thue = "";
        this.TongCong = "";
        this.Nam = "";
        this.Ky = "";
        this.Dot = "";
        this.NgayThuTien = "";
        this.SoChinh = false;
        this.CuaHangThuHo = "";
        this.TuNgay = DenNgay = "";
        this.GhiHinh = false;
        this.GhiChu = "";
        this.AnXoa = false;
        this.Sync = false;
        this.SH = 0;
        this.SX = 0;
        this.DV = 0;
        this.HCSN = 0;
        this.PhanMay = "";
        this.ChuBao = false;
        this.TinhTrang = "";
        this.Latitude = "";
        this.Longitude = "";
        this.ChuaGuiThongBao = false;
        this.ThayDK = false;
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

    public ArrayList<CEntityChild> getLstCuaHangThuHo() {
        return lstCuaHangThuHo;
    }

    public void setLstCuaHangThuHo(ArrayList<CEntityChild> lstCuaHangThuHo) {
        this.lstCuaHangThuHo = lstCuaHangThuHo;
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

    public String getViTri() {
        return ViTri;
    }

    public void setViTri(String viTri) {
        ViTri = viTri;
    }

    public String getKinhDoanh() {
        return KinhDoanh;
    }

    public void setKinhDoanh(String kinhDoanh) {
        KinhDoanh = kinhDoanh;
    }

    public boolean isViTriNgoai() {
        return ViTriNgoai;
    }

    public void setViTriNgoai(boolean viTriNgoai) {
        ViTriNgoai = viTriNgoai;
    }

    public boolean isViTriHop() {
        return ViTriHop;
    }

    public void setViTriHop(boolean viTriHop) {
        ViTriHop = viTriHop;
    }

    public boolean isGieng() {
        return Gieng;
    }

    public void setGieng(boolean gieng) {
        Gieng = gieng;
    }

    public boolean isKhoaTu() {
        return KhoaTu;
    }

    public void setKhoaTu(boolean khoaTu) {
        KhoaTu = khoaTu;
    }

    public boolean isAmSau() {
        return AmSau;
    }

    public void setAmSau(boolean amSau) {
        AmSau = amSau;
    }

    public boolean isXayDung() {
        return XayDung;
    }

    public void setXayDung(boolean xayDung) {
        XayDung = xayDung;
    }

    public boolean isDutChi_Goc() {
        return DutChi_Goc;
    }

    public void setDutChi_Goc(boolean dutChi_Goc) {
        DutChi_Goc = dutChi_Goc;
    }

    public boolean isDutChi_Than() {
        return DutChi_Than;
    }

    public void setDutChi_Than(boolean dutChi_Than) {
        DutChi_Than = dutChi_Than;
    }

    public boolean isNgapNuoc() {
        return NgapNuoc;
    }

    public void setNgapNuoc(boolean ngapNuoc) {
        NgapNuoc = ngapNuoc;
    }

    public boolean isKetTuong() {
        return KetTuong;
    }

    public void setKetTuong(boolean ketTuong) {
        KetTuong = ketTuong;
    }

    public boolean isLapKhoaGoc() {
        return LapKhoaGoc;
    }

    public void setLapKhoaGoc(boolean lapKhoaGoc) {
        LapKhoaGoc = lapKhoaGoc;
    }

    public boolean isBeHBV() {
        return BeHBV;
    }

    public void setBeHBV(boolean beHBV) {
        BeHBV = beHBV;
    }

    public boolean isBeNapMatNapHBV() {
        return BeNapMatNapHBV;
    }

    public void setBeNapMatNapHBV(boolean beNapMatNapHBV) {
        BeNapMatNapHBV = beNapMatNapHBV;
    }

    public boolean isGayTayVan() {
        return GayTayVan;
    }

    public void setGayTayVan(boolean gayTayVan) {
        GayTayVan = gayTayVan;
    }

    public boolean isTroNgaiThay() {
        return TroNgaiThay;
    }

    public void setTroNgaiThay(boolean troNgaiThay) {
        TroNgaiThay = troNgaiThay;
    }

    public boolean isDauChungMayBom() {
        return DauChungMayBom;
    }

    public void setDauChungMayBom(boolean dauChungMayBom) {
        DauChungMayBom = dauChungMayBom;
    }

    public String getMauSacChiGoc() {
        return MauSacChiGoc;
    }

    public void setMauSacChiGoc(String mauSacChiGoc) {
        MauSacChiGoc = mauSacChiGoc;
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

    public String getChiSo0In() {
        return ChiSo0In;
    }

    public void setChiSo0In(String chiSo0In) {
        ChiSo0In = chiSo0In;
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

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public boolean isAnXoa() {
        return AnXoa;
    }

    public void setAnXoa(boolean anXoa) {
        AnXoa = anXoa;
    }

    public boolean isSync() {
        return Sync;
    }

    public void setSync(boolean sync) {
        Sync = sync;
    }

    public int getSH() {
        return SH;
    }

    public void setSH(int SH) {
        this.SH = SH;
    }

    public int getSX() {
        return SX;
    }

    public void setSX(int SX) {
        this.SX = SX;
    }

    public int getDV() {
        return DV;
    }

    public void setDV(int DV) {
        this.DV = DV;
    }

    public int getHCSN() {
        return HCSN;
    }

    public void setHCSN(int HCSN) {
        this.HCSN = HCSN;
    }

    public String getPhanMay() {
        return PhanMay;
    }

    public void setPhanMay(String phanMay) {
        PhanMay = phanMay;
    }

    public boolean isChuBao() {
        return ChuBao;
    }

    public void setChuBao(boolean chuBao) {
        ChuBao = chuBao;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        TinhTrang = tinhTrang;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public boolean isChuaGuiThongBao() {
        return ChuaGuiThongBao;
    }

    public void setChuaGuiThongBao(boolean chuaGuiThongBao) {
        ChuaGuiThongBao = chuaGuiThongBao;
    }

    public boolean isThayDK() {
        return ThayDK;
    }

    public void setThayDK(boolean thayDK) {
        ThayDK = thayDK;
    }
    public void setCEntityParent(CEntityParent entityParent) {
        try {
            this.ID = entityParent.getID();
            this.MLT = entityParent.getMLT();
            this.DanhBo = entityParent.getDanhBo();
            this.HoTen = entityParent.getHoTen();
            this.DiaChi = entityParent.getDiaChi();
            this.SoNha = entityParent.getSoNha();
            this.TenDuong = entityParent.getTenDuong();
            this.ModifyDate = entityParent.getModifyDate();
            this.Hieu = entityParent.getHieu();
            this.Co = entityParent.getCo();
            this.SoThan = entityParent.getSoThan();
            this.lstHoaDon = entityParent.getLstHoaDon();
            this.lstCuaHangThuHo = entityParent.getLstCuaHangThuHo();
            this.CreateDate = entityParent.getCreateDate();
            this.GiaBieu = entityParent.getGiaBieu();
            this.DinhMuc = entityParent.getDinhMuc();
            this.DinhMucHN = entityParent.getDinhMucHN();
            this.ViTri = entityParent.getViTri();
            this.KinhDoanh = entityParent.getKinhDoanh();
            this.ViTriNgoai = entityParent.isViTriNgoai();
            this.ViTriHop = entityParent.isViTriHop();
            this.Gieng = entityParent.isGieng();
            this.KhoaTu = entityParent.isKhoaTu();
            this.AmSau = entityParent.isAmSau();
            this.XayDung = entityParent.isXayDung();
            this.DutChi_Goc = entityParent.isDutChi_Goc();
            this.DutChi_Than = entityParent.isDutChi_Than();
            this.NgapNuoc = entityParent.isNgapNuoc();
            this.KetTuong = entityParent.isKetTuong();
            this.LapKhoaGoc = entityParent.isLapKhoaGoc();
            this.BeHBV = entityParent.isBeHBV();
            this.BeNapMatNapHBV = entityParent.isBeNapMatNapHBV();
            this.GayTayVan = entityParent.isGayTayVan();
            this.TroNgaiThay = entityParent.isTroNgaiThay();
            this.DauChungMayBom = entityParent.isDauChungMayBom();
            this.MauSacChiGoc = entityParent.getMauSacChiGoc();
            this.DienThoai = entityParent.getDienThoai();
            this.ChiSoMoi = entityParent.getChiSoMoi();
            this.CodeMoi = entityParent.getCodeMoi();
            this.TieuThuMoi = entityParent.getTieuThuMoi();
            this.ChiSo0 = entityParent.getChiSo0();
            this.ChiSo0In = entityParent.getChiSo0In();
            this.Code0 = entityParent.getCode0();
            this.TieuThu0 = entityParent.getTieuThu0();
            this.ChiSo1 = entityParent.getChiSo1();
            this.Code1 = entityParent.getCode1();
            this.TieuThu1 = entityParent.getTieuThu1();
            this.ChiSo2 = entityParent.getChiSo2();
            this.Code2 = entityParent.getCode2();
            this.TieuThu2 = entityParent.getTieuThu2();
            this.TBTT = entityParent.getTBTT();
            this.TienNuoc = entityParent.getTienNuoc();
            this.ThueGTGT = entityParent.getThueGTGT();
            this.PhiBVMT = entityParent.getPhiBVMT();
            this.PhiBVMT_Thue = entityParent.getPhiBVMT_Thue();
            this.TongCong = entityParent.getTongCong();
            this.Nam = entityParent.getNam();
            this.Ky = entityParent.getKy();
            this.Dot = entityParent.getDot();
            this.NgayThuTien = entityParent.getNgayThuTien();
            this.SoChinh = entityParent.isSoChinh();
            this.CuaHangThuHo = entityParent.getCuaHangThuHo();
            this.TuNgay = entityParent.getTuNgay();
            this.DenNgay = entityParent.getDenNgay();
            this.GhiHinh = entityParent.isGhiHinh();
            this.GhiChu = entityParent.getGhiChu();
            this.AnXoa = entityParent.isAnXoa();
            this.Sync = entityParent.isSync();
            this.SH = entityParent.getSH();
            this.SX = entityParent.getSX();
            this.DV = entityParent.getDV();
            this.HCSN = entityParent.getHCSN();
            this.PhanMay = entityParent.getPhanMay();
            this.ChuBao = entityParent.isChuBao();
            this.TinhTrang = entityParent.getTinhTrang();
            this.Latitude = entityParent.getLatitude();
            this.Longitude = entityParent.getLongitude();
            this.ChuaGuiThongBao = entityParent.isChuaGuiThongBao();
            this.ThayDK = entityParent.isThayDK();
        } catch (Exception ex) {
            throw ex;
        }
    }


}
