package vn.com.capnuoctanhoa.docsoandroid.Class;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import androidx.annotation.Nullable;

import java.io.IOException;

/**
 * Created by user on 22/03/2018.
 */

public class CWebservice {
    private final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private final String SOAP_ADDRESS = "http://113.161.88.180:81/wsDHN.asmx";

    @Nullable
    private String excute(SoapObject request, String SOAP_ACTION) throws XmlPullParserException, IOException {
        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS, 1000 * 60 * 1);
            httpTransport.call(SOAP_ACTION, envelope);
            return envelope.getResponse().toString();
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Nullable
    private SoapObject excuteReturnTable(SoapObject request, String SOAP_ACTION) {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
        SoapObject response = null;
        try {
            httpTransport.call(SOAP_ACTION, envelope);
            response = (SoapObject) envelope.bodyIn;
        } catch (Exception exception) {
            return null;
        }
        response = (SoapObject) response.getProperty(0);
        response = (SoapObject) response.getProperty(1);
        try {
            response = (SoapObject) response.getProperty(0);
        } catch (Exception ex) {
            return null;
        }
        return response;
    }

    public String dangNhaps(String Username, String Password, String IDMobile, String UID) throws XmlPullParserException, IOException {
        String SOAP_ACTION = "http://tempuri.org/DangNhaps";
        String OPERATION_NAME = "DangNhaps";
//        String SOAP_ACTION = "http://tempuri.org/DangNhaps_Admin";
//        String OPERATION_NAME = "DangNhaps_Admin";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("Username");
        pi.setValue(Username);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("Password");
        pi.setValue(Password);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("IDMobile");
        pi.setValue(IDMobile);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("UID");
        pi.setValue(UID);
        pi.setType(String.class);
        request.addProperty(pi);

        return excute(request, SOAP_ACTION);
    }

    public String dangXuats(String Username, String UID) throws XmlPullParserException, IOException {
        String SOAP_ACTION = "http://tempuri.org/DangXuats";
        String OPERATION_NAME = "DangXuats";
//        String SOAP_ACTION = "http://tempuri.org/DangXuats_Admin";
//        String OPERATION_NAME = "DangXuats_Admin";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("Username");
        pi.setValue(Username);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("UID");
        pi.setValue(UID);
        pi.setType(String.class);
        request.addProperty(pi);

        return excute(request, SOAP_ACTION);
    }

    public String dangXuats_Person(String Username, String UID) throws XmlPullParserException, IOException {
        String SOAP_ACTION = "http://tempuri.org/DangXuats_Person";
        String OPERATION_NAME = "DangXuats_Person";
//        String SOAP_ACTION = "http://tempuri.org/DangXuats_Admin";
//        String OPERATION_NAME = "DangXuats_Admin";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("Username");
        pi.setValue(Username);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("UID");
        pi.setValue(UID);
        pi.setType(String.class);
        request.addProperty(pi);

        return excute(request, SOAP_ACTION);
    }

    public String updateUID(String MaNV, String UID) throws XmlPullParserException, IOException {
        String SOAP_ACTION = "http://tempuri.org/UpdateUID";
        String OPERATION_NAME = "UpdateUID";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("MaNV");
        pi.setValue(MaNV);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("UID");
        pi.setValue(UID);
        pi.setType(String.class);
        request.addProperty(pi);

        return excute(request, SOAP_ACTION);
    }

    public String getVersion() throws XmlPullParserException, IOException {
        String SOAP_ACTION = "http://tempuri.org/GetVersion";
        String OPERATION_NAME = "GetVersion";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        return excute(request, SOAP_ACTION);
    }

    public String getDSTo() throws XmlPullParserException, IOException {
        String SOAP_ACTION = "http://tempuri.org/GetDSTo";
        String OPERATION_NAME = "GetDSTo";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        return excute(request, SOAP_ACTION);
    }

    public String getDSNhanVienTo(String MaTo) throws XmlPullParserException, IOException {
        String SOAP_ACTION = "http://tempuri.org/GetDSNhanVienTo";
        String OPERATION_NAME = "GetDSNhanVienTo";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("MaTo");
        pi.setValue(MaTo);
        pi.setType(String.class);
        request.addProperty(pi);

        return excute(request, SOAP_ACTION);
    }

    public String getDS_NhanVien()throws XmlPullParserException, IOException  {
        String SOAP_ACTION = "http://tempuri.org/getDS_NhanVien";
        String OPERATION_NAME = "getDS_NhanVien";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        return excute(request, SOAP_ACTION);
    }

    public String getDS_Nam()throws XmlPullParserException, IOException  {
        String SOAP_ACTION = "http://tempuri.org/getDS_Nam";
        String OPERATION_NAME = "getDS_Nam";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        return excute(request, SOAP_ACTION);
    }

    public String getDS_Code()throws XmlPullParserException, IOException  {
        String SOAP_ACTION = "http://tempuri.org/getDS_Code";
        String OPERATION_NAME = "getDS_Code";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        return excute(request, SOAP_ACTION);
    }

    public String getDS_ViTriDHN() throws XmlPullParserException, IOException {
        String SOAP_ACTION = "http://tempuri.org/getDS_ViTriDHN";
        String OPERATION_NAME = "getDS_ViTriDHN";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        return excute(request, SOAP_ACTION);
    }

    //ghi chú
    public String update_GhiChu(String DanhBo, String SoNha, String TenDuong, String ViTri1, String ViTri2, String Gieng, String GhiChu, String MaNV) throws XmlPullParserException, IOException {
        String SOAP_ACTION = "http://tempuri.org/update_DienThoai";
        String OPERATION_NAME = "update_DienThoai";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("DanhBo");
        pi.setValue(DanhBo);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("SoNha");
        pi.setValue(SoNha);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("TenDuong");
        pi.setValue(TenDuong);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("ViTri1");
        pi.setValue(ViTri1);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("ViTri2");
        pi.setValue(ViTri2);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("Gieng");
        pi.setValue(Gieng);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("GhiChu");
        pi.setValue(GhiChu);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("MaNV");
        pi.setValue(MaNV);
        pi.setType(String.class);
        request.addProperty(pi);

        return excute(request, SOAP_ACTION);
    }

    public String getDS_DienThoai(String DanhBo)throws XmlPullParserException, IOException  {
        String SOAP_ACTION = "http://tempuri.org/getDS_DienThoai";
        String OPERATION_NAME = "getDS_DienThoai";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("DanhBo");
        pi.setValue(DanhBo);
        pi.setType(String.class);
        request.addProperty(pi);

        return excute(request, SOAP_ACTION);
    }

    public String update_DienThoai(String DanhBo, String DienThoai, String HoTen, String SoChinh, String MaNV)throws XmlPullParserException, IOException  {
        String SOAP_ACTION = "http://tempuri.org/update_DienThoai";
        String OPERATION_NAME = "update_DienThoai";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("DanhBo");
        pi.setValue(DanhBo);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("DienThoai");
        pi.setValue(DienThoai);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("HoTen");
        pi.setValue(HoTen);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("SoChinh");
        pi.setValue(SoChinh);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("MaNV");
        pi.setValue(MaNV);
        pi.setType(String.class);
        request.addProperty(pi);

        return excute(request, SOAP_ACTION);
    }

    public String delete_DienThoai(String DanhBo, String DienThoai) throws XmlPullParserException, IOException {
        String SOAP_ACTION = "http://tempuri.org/delete_DienThoai";
        String OPERATION_NAME = "delete_DienThoai";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("DanhBo");
        pi.setValue(DanhBo);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("DienThoai");
        pi.setValue(DienThoai);
        pi.setType(String.class);
        request.addProperty(pi);

        return excute(request, SOAP_ACTION);
    }

    //phiếu chuyển
    public String getDS_PhieuChuyen() throws XmlPullParserException, IOException {
        String SOAP_ACTION = "http://tempuri.org/getDS_PhieuChuyen";
        String OPERATION_NAME = "getDS_PhieuChuyen";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        return excute(request, SOAP_ACTION);
    }

    public String getDS_DonTu(String DanhBo) throws XmlPullParserException, IOException {
        String SOAP_ACTION = "http://tempuri.org/getDS_DonTu";
        String OPERATION_NAME = "getDS_DonTu";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("DanhBo");
        pi.setValue(DanhBo);
        pi.setType(String.class);
        request.addProperty(pi);

        return excute(request, SOAP_ACTION);
    }

    public String ghi_DonTu(String DanhBo,String NoiDung,String GhiChu,String MaNV ) throws XmlPullParserException, IOException {
        String SOAP_ACTION = "http://tempuri.org/ghi_DonTu";
        String OPERATION_NAME = "ghi_DonTu";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("DanhBo");
        pi.setValue(DanhBo);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("NoiDung");
        pi.setValue(NoiDung);
        pi.setType(String.class);
        request.addProperty(pi);

        pi.setName("GhiChu");
        pi.setValue(GhiChu);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("MaNV");
        pi.setValue(MaNV);
        pi.setType(String.class);
        request.addProperty(pi);

        return excute(request, SOAP_ACTION);
    }

    //đọc số
    public String getDS_DocSo(String Nam, String Ky, String Dot, String May)throws XmlPullParserException, IOException  {
        String SOAP_ACTION = "http://tempuri.org/getDS_DocSo";
        String OPERATION_NAME = "getDS_DocSo";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("Nam");
        pi.setValue(Nam);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("Ky");
        pi.setValue(Ky);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("Dot");
        pi.setValue(Dot);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("May");
        pi.setValue(May);
        pi.setType(String.class);
        request.addProperty(pi);

        return excute(request, SOAP_ACTION);
    }

    public String getDS_HoaDonTon(String Nam, String Ky, String Dot, String May) throws XmlPullParserException, IOException {
        String SOAP_ACTION = "http://tempuri.org/getDS_HoaDonTon";
        String OPERATION_NAME = "getDS_HoaDonTon";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("Nam");
        pi.setValue(Nam);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("Ky");
        pi.setValue(Ky);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("Dot");
        pi.setValue(Dot);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("May");
        pi.setValue(May);
        pi.setType(String.class);
        request.addProperty(pi);

        return excute(request, SOAP_ACTION);
    }

    public String ghiChiSo(String ID, String Code, String ChiSo, String HinhDHN, String Dot, String MaNV, String TBTT)throws XmlPullParserException, IOException  {
        String SOAP_ACTION = "http://tempuri.org/ghiChiSo";
        String OPERATION_NAME = "ghiChiSo";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("ID");
        pi.setValue(ID);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("Code");
        pi.setValue(Code);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("ChiSo");
        pi.setValue(ChiSo);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("HinhDHN");
        pi.setValue(HinhDHN);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("Dot");
        pi.setValue(Dot);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("MaNV");
        pi.setValue(MaNV);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("TBTT");
        pi.setValue(TBTT);
        pi.setType(String.class);
        request.addProperty(pi);

        return excute(request, SOAP_ACTION);
    }

    public String ghi_Hinh(String ID, String HinhDHN)throws XmlPullParserException, IOException  {
        String SOAP_ACTION = "http://tempuri.org/ghi_Hinh";
        String OPERATION_NAME = "ghi_Hinh";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("ID");
        pi.setValue(ID);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("HinhDHN");
        pi.setValue(HinhDHN);
        pi.setType(String.class);
        request.addProperty(pi);

        return excute(request, SOAP_ACTION);
    }

    //admin
    public String truyvan(String sql) throws XmlPullParserException, IOException {
        String SOAP_ACTION = "http://tempuri.org/truyvan";
        String OPERATION_NAME = "truyvan";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("sql");
        pi.setValue(sql);
        pi.setType(String.class);
        request.addProperty(pi);

        return excute(request, SOAP_ACTION);
    }

    public String capnhat(String sql)throws XmlPullParserException, IOException  {
        String SOAP_ACTION = "http://tempuri.org/capnhat";
        String OPERATION_NAME = "capnhat";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("sql");
        pi.setValue(sql);
        pi.setType(String.class);
        request.addProperty(pi);

        return excute(request, SOAP_ACTION);
    }


}
