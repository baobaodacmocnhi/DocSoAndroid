package vn.com.capnuoctanhoa.docsoandroid.Class;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import androidx.annotation.Nullable;

/**
 * Created by user on 22/03/2018.
 */

public class CWebservice {
    private final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private final String SOAP_ADDRESS = "http://113.161.88.180:81/wsDHN.asmx";
//  private final String SOAP_ADDRESS = "http://192.168.90.11:81/wsDHN_test.asmx";

    @Nullable
    private String excute(SoapObject request, String SOAP_ACTION) {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS, 1000 * 60 * 5);
        Object response = null;
        try {
            httpTransport.call(SOAP_ACTION, envelope);
            response = envelope.getResponse();
        } catch (Exception exception) {
            response = exception.toString();
        }
        return response.toString();
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

    public String dangNhaps(String Username, String Password, String IDMobile, String UID) {
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

    public String dangXuats(String Username, String UID) {
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

    public String dangXuats_Person(String Username, String UID) {
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

    public String updateUID(String MaNV, String UID) {
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


    public String getVersion() {
        String SOAP_ACTION = "http://tempuri.org/GetVersion";
        String OPERATION_NAME = "GetVersion";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        return excute(request, SOAP_ACTION);
    }

    public String getDSTo() {
        String SOAP_ACTION = "http://tempuri.org/GetDSTo";
        String OPERATION_NAME = "GetDSTo";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        return excute(request, SOAP_ACTION);
    }

    public String getDSNhanVienTo(String MaTo) {
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

    public String getDS_NhanVien() {
        String SOAP_ACTION = "http://tempuri.org/getDS_NhanVien";
        String OPERATION_NAME = "getDS_NhanVien";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        return excute(request, SOAP_ACTION);
    }

    public String getDS_Nam() {
        String SOAP_ACTION = "http://tempuri.org/getDS_Nam";
        String OPERATION_NAME = "getDS_Nam";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        return excute(request, SOAP_ACTION);
    }

    //đọc số
    public String getDS_DocSo(String Nam, String Ky, String Dot, String May) {
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


    //admin
    public String truyvan(String sql) {
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

    public String capnhat(String sql) {
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
