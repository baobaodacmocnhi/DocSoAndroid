package vn.com.capnuoctanhoa.docsoandroid.Service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.Vector;

import androidx.annotation.Nullable;

import androidx.annotation.RequiresApi;

import vn.com.capnuoctanhoa.docsoandroid.Class.CEntityChild;
import vn.com.capnuoctanhoa.docsoandroid.Class.CEntityParent;
import vn.com.capnuoctanhoa.docsoandroid.Class.CLocal;
import vn.com.capnuoctanhoa.docsoandroid.MainActivity;

public class ServiceThermalPrinter extends Service {
    public ServiceThermalPrinter() {
    }

    private BluetoothAdapter mBluetoothAdapter;
    //    public static String B_DEVICE = "MY_DEVICE";
    public static final String B_UUID = "00001101-0000-1000-8000-00805f9b34fb";

    public static final int STATE_NONE = 0;
    public static final int STATE_LISTEN = 1;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_CONNECTED = 3;

    private ConnectBtThread mConnectThread;
    private ConnectedBtThread mConnectedThread;

    private static Handler mHandler = null;
    public static int mState = STATE_NONE;
    public static String deviceName;
    public static BluetoothSocket mSocket = null;
    public static BluetoothDevice mDevice = null;
    public Vector<Byte> packData = new Vector<>(2048);
    private final IBinder mBinder = new LocalBinder();
    public InputStream inS;
    public static OutputStream outputStream;


//IBinder mIBinder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //mHandler = getApplication().getHandler();
        return mBinder;
    }

    public void toast(String mess) {
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
    }

    public void toastThread(final String mess) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), mess, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class LocalBinder extends Binder {
        public ServiceThermalPrinter getService() {
            // Return this instance of LocalService so clients can call public methods
            return ServiceThermalPrinter.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        B_DEVICE = intent.getStringExtra("ThermalPrinter");
//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        connectToDevice(CLocal.ThermalPrinter);
        return START_STICKY;
    }

    private synchronized void connectToDevice(String macAddress) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mDevice = mBluetoothAdapter.getRemoteDevice(macAddress);
        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {
                mConnectThread.cancel();
                mConnectThread = null;
            }
        }
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
        mConnectThread = new ConnectBtThread(mDevice);
        toast("Đang Kết Nối Máy In");
        mConnectThread.start();
        setState(STATE_CONNECTING);
    }

    private void setState(int state) {
        mState = state;
        if (mHandler != null) {
            // mHandler.obtainMessage();
        }
    }

    public int getState() {
        return mState;
    }

    public synchronized void stop() {
        setState(STATE_NONE);
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }

        stopSelf();
    }

    public void sendData(String message) {
        if (mConnectedThread != null) {
            mConnectedThread.write(message.getBytes());
            toast("sent data");
        } else {
            Toast.makeText(ServiceThermalPrinter.this, "Failed to send data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean stopService(Intent name) {
        setState(STATE_NONE);

        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        mBluetoothAdapter.cancelDiscovery();
        return super.stopService(name);
    }

    private synchronized void connected(BluetoothSocket mmSocket) {

        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        mConnectedThread = new ConnectedBtThread(mmSocket);
        mConnectedThread.start();

        setState(STATE_CONNECTED);
    }

    private class ConnectBtThread extends Thread {
//        private final BluetoothSocket mSocket;
//        private final BluetoothDevice mDevice;

        public ConnectBtThread(BluetoothDevice device) {
//            mDevice = device;
            BluetoothSocket socket = null;
            try {
                socket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString(B_UUID));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mSocket = socket;

        }

        @Override
        public void run() {
            mBluetoothAdapter.cancelDiscovery();

            try {
                mSocket.connect();
//                Log.d("service","connect thread run method (connected)");
                toastThread("Đã Kết Nối Máy In");
                setState(STATE_CONNECTED);
//                SharedPreferences pre = getSharedPreferences("BT_NAME",0);
//                pre.edit().putString("bluetooth_connected",mDevice.getName()).apply();

            } catch (IOException e) {

                try {
                    mSocket.close();
//                    Log.d("service","connect thread run method ( close function)");
                    toastThread("Lỗi Kết Nối Máy In");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
            //connected(mSocket);
            mConnectedThread = new ConnectedBtThread(mSocket);
            mConnectedThread.start();
        }

        public void cancel() {

            try {
                mSocket.close();
//                Log.d("service","connect thread cancel method");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class ConnectedBtThread extends Thread {
        private BluetoothSocket cSocket;
        //        private  InputStream inS;
//        private  OutputStream outS;
        private byte[] buffer;

        public ConnectedBtThread() {

        }

        public ConnectedBtThread(BluetoothSocket socket) {
            cSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            inS = tmpIn;
            outputStream = tmpOut;
        }

        @Override
        public void run() {
            buffer = new byte[1024];
            int mByte;
            try {
                mByte = inS.read(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            Log.d("service","connected thread run method");
//            toastThread("connected thread run method");
//            setState(STATE_LISTEN);
        }


        public void write(byte[] buff) {
            try {
                outputStream.write(buff);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void cancel() {
            try {
                cSocket.close();
//                Log.d("service","connected thread cancel method");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        this.stop();
        super.onDestroy();
    }

    /////////////////////////////////////////////////////////////

    private final byte[] mmChuoi = {0x1b};
    private static final byte[] ESC = {0x1B};
    private static int toadoY = 0;
    private static StringBuilder stringBuilder;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void printGhiChiSo(CEntityParent entityParent) throws IOException {
        try {
            if (CLocal.serviceThermalPrinter != null)
                switch (CLocal.MethodPrinter) {
                    case "Honeywell45":
                        printGhiChiSo_escpPrint(entityParent, 45);
                        break;
                    case "Intermec":
                        printGhiChiSo_escpEasyPrint(entityParent);
                        break;
                    case "Honeywell31":
                        printGhiChiSo_escpPrint(entityParent, 31);
                        break;
                }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void printGhiChiSo_escpPrint(CEntityParent entityParent, int charWidth) throws IOException {
        try {
            if (entityParent != null) {
                char ESC = 0x1B,
                        RESET = '@',
                        STYLE = '!',
                        padChar = ' ';
                String CMD_RESET = "" + ESC + RESET,
                        CMD_RESET_STYLE = "" + ESC + STYLE + (char) 0,
                        CMD_ALIGN_LEFT = "" + ESC + "a0",
                        CMD_ALIGN_CENTER = "" + ESC + "a1",
                        CMD_ALIGN_RIGHT = "" + ESC + "a2";
                String line;
                {
                    char[] lineChar = new char[charWidth];
                    Arrays.fill(lineChar, '-');
                    line = (new String(lineChar)).concat("\n");
                }
//                cal.setTime(formatter_old.parse(mHoaDon.getDenNgay()));
                String codeMoi = entityParent.getCodeMoi() == null ? "" : entityParent.getCodeMoi().trim();
                String lyDo = codeMoi.equals("F1") ? "Nhà đóng cửa" : codeMoi.equals("F2") ? "Kẹt khóa" : codeMoi.equals("F3") ? "Chất đồ" : "";

//                int mNam = Integer.parseInt(mHoaDon.getDocSoID().substring(0,4));
//                int mKy = Integer.parseInt(mHoaDon.getDocSoID().substring(4,6));
//                boolean namKy = mNam * 12 + mKy - 1 >= 2020 * 12 + 11;
//                boolean namKy2 = mNam * 12 + mKy - 1 >= 2021 * 12;
//                boolean namKy3 = mNam * 12 + mKy - 1 <= 2021 * 12 + 2;

                stringBuilder = new StringBuilder();
                stringBuilder.append(CMD_RESET)
                        .append(CMD_RESET_STYLE)
                        .append(CMD_ALIGN_CENTER)
                        .append("CTY CP CẤP NƯỚC TÂN HÒA\n")
                        .append("95 PHẠM HỮU CHÍ, P12, Q5\n")
                        .append(escpStyle("CSKH: 19006489\n", 0b111000));
                if (codeMoi.startsWith("F")) {
                    stringBuilder.append(escpStyle("PHIẾU BÁO TRỞ NGẠI ĐỌC SỐ\n", 0b11000))
                            .append(CMD_ALIGN_LEFT)
                            .append("Kỳ: ")
                            .append(CMD_ALIGN_RIGHT)
                            .append(entityParent.getKy() + "/" + entityParent.getNam()).append('\n')
                            .append(CMD_ALIGN_LEFT)
                            .append("Từ ngày: ").append(entityParent.getTuNgay()).append('\n')
                            .append(" Đến ngày: ").append(entityParent.getDenNgay()).append('\n')
                            .append("Danh bộ (Mã KH): ").append(escpStyle(entityParent.getDanhBo(), 0b11000)).append('\n')
                            .append("MLT: ").append(entityParent.getMLT()).append('\n')
                            .append("Khách hàng: ").append(breakLine(entityParent.getHoTen(), charWidth)).append('\n')
                            .append("Địa chỉ :").append(breakLine(entityParent.getDiaChi(), charWidth)).append('\n')
                            .append("Giá biểu: ").append(entityParent.getGiaBieu()).append("      ")
                            .append("Định mức: ").append(entityParent.getDinhMuc()).append('\n')
                            .append("Lý do trở ngại: ").append(lyDo).append('\n')
                            .append(line)
                            .append(breakLine("Quý khách vui lòng báo chỉ số nước cho nhân viên đọc số: " + CLocal.HoTen, charWidth)).append('\n')
                            .append(escpStyle("SĐT " + CLocal.DienThoai + "\n", 0b11000))
                            .append("Hoặc tổng đài 19006489.\n")
                            .append(breakLine("Trong vòng 2 ngày kể từ ngày nhận giấy báo để tính đúng lượng nước tiêu thụ trong kỳ.", charWidth)).append('\n')
                            .append(breakLine("Nếu quý khách hàng không thông báo công ty sẽ tạm tính tiêu thụ bình quân 3 kỳ gần nhất.", charWidth)).append('\n');
                } else {
                    String lb = charWidth == 31 ? "\n" : " ";
                    stringBuilder.append(escpStyle("PHIẾU BÁO CHỈ SỐ" + lb + "VÀ TIỀN NƯỚC DỰ KIẾN\n", 0b11000))
                            .append(CMD_ALIGN_LEFT)
                            .append("NV: ").append(CLocal.HoTen).append('\n')
                            .append("SĐT: ").append(CLocal.DienThoai).append('\n')
                            .append("Kỳ: ").append(entityParent.getKy() + "/" + entityParent.getNam()).append('\n')
                            .append("Từ ngày: ").append(entityParent.getTuNgay()).append(" Đến ngày: ").append(entityParent.getDenNgay()).append('\n')
                            .append("Danh bộ (Mã KH): ").append(escpStyle(entityParent.getDanhBo(), 0b11000)).append('\n')
                            .append("MLT: ").append(entityParent.getMLT()).append('\n')
                            .append("Khách hàng: ").append(breakLine(entityParent.getHoTen(), charWidth)).append('\n')
                            .append("Địa chỉ :").append(breakLine(entityParent.getDiaChi(), charWidth)).append('\n')
                            .append("Giá biểu: ").append(entityParent.getGiaBieu()).append("      ")
                            .append("Định mức: ").append(entityParent.getDinhMuc()).append('\n')
                            .append(pad("Cs cũ:", entityParent.getChiSo0(), padChar, charWidth)).append('\n')
                            .append(pad("Cs mới:", entityParent.getChiSoMoi(), padChar, charWidth)).append('\n')
                            .append(pad("Tiêu thụ:", entityParent.getTieuThuMoi(), padChar, charWidth)).append('\n')
                            .append(pad("Tiền nước:", numberVN(Double.parseDouble(entityParent.getTienNuoc())), padChar, charWidth)).append('\n')
                            .append(pad("Thuế VAT:", numberVN(Double.parseDouble(entityParent.getThueGTGT())), padChar, charWidth)).append('\n');
                    if (Double.parseDouble(entityParent.getPhiBVMT()) > 0)
                        stringBuilder.append(pad("Tiền DV thoát nước:", numberVN(Double.parseDouble(entityParent.getPhiBVMT())), padChar, charWidth)).append('\n');
                    if (Double.parseDouble(entityParent.getPhiBVMT_Thue()) > 0)
                        stringBuilder.append(pad("VAT DV thoát nước:", numberVN(Double.parseDouble(entityParent.getPhiBVMT_Thue())), padChar, charWidth)).append('\n');
                    stringBuilder.append(escpStyle(pad("Tổng cộng:", numberVN(Double.parseDouble(entityParent.getTongCong())), padChar, charWidth) + "\n", 0b10000));
                    int TongCong = Integer.parseInt(entityParent.getTongCong());
                    if (entityParent.getLstHoaDon().size() > 0) {
                        stringBuilder.append("Nợ cũ:").append('\n');
                        stringBuilder.append("(bỏ qua nếu đã thanh toán)").append('\n');
                        for (int i = 0; i < entityParent.getLstHoaDon().size(); i++) {
                            TongCong += Integer.parseInt(entityParent.getLstHoaDon().get(i).getTongCong());
                            stringBuilder.append("   Kỳ " + entityParent.getLstHoaDon().get(i).getKy() + ": " + numberVN(Double.parseDouble(entityParent.getLstHoaDon().get(i).getTongCong()))).append('\n');
                        }
                    }
                    stringBuilder.append(escpStyle(pad("Số tiền cần thanh toán:", numberVN(Double.parseDouble(String.valueOf(TongCong))), padChar, charWidth) + "\n", 0b10000));
                    //doc tien
                    String docTien = ReadMoney(String.valueOf(TongCong));
                    String docTienLines = "";
                    if (!docTien.trim().isEmpty())
                        docTienLines = breakLine("BẰNG CHỮ: " + docTien, charWidth);
                    stringBuilder.append(docTienLines).append('\n')
                            .append(line);
//                    stringBuilder.append(breakLine("Ngày thu tiền dự kiến từ " + entityParent.getNgayThuTien(), charWidth)).append('\n');
//                    stringBuilder.append("Phiếu này không có giá trị thanh toán\n");
                }
                stringBuilder.append(breakLine("KHÁCH HÀNG VUI LÒNG THANH TOÁN TIỀN NƯỚC TRONG 7 NGÀY KỂ TỪ NGÀY " +
                                escpStyle(entityParent.getNgayThuTien(), 0b11000) +
                                ".\n",
                        charWidth));
                stringBuilder.append(breakLine("Để biết thêm thông tin chi tiết tiền nước quý khách hàng vui lòng liên hệ tổng đài " +
                                escpStyle("19006489", 0b11000) +
                                " hoặc website: " +
                                escpStyle("https://www.cskhtanhoa.com.vn", 0b11000),
                        charWidth)).append('\n');
                //stringBuilder.append(breakLine("Quét QR để cung cấp chỉ số nước qua Zalo:\n", charWidth));
                //String link = "https://cskhtanhoa.com.vn/bai-viet/cung-cap-chi-so-nuoc-4022.html";
                stringBuilder.append(breakLine("Quét QR để xem chi tiết lịch sử sử dụng nước:\n", charWidth));
                String link = "https://service.cskhtanhoa.com.vn/khachhang/thongtin?danhbo=" + entityParent.getDanhBo().replace(" ", "");
                String qrData;
                if (charWidth == 31)
                    qrData = woosimQrPrint(link, 5);
                else
                    qrData = mdp31dQrPrint(link, 5);
                stringBuilder.append(CMD_ALIGN_CENTER)
                        .append(qrData)
                        .append(CMD_ALIGN_LEFT)
                        .append('\n');
                stringBuilder.append(CMD_ALIGN_CENTER)
                        .append("TRÂN TRỌNG\n")
                        .append(line)
                        .append("Được in vào: ").append(CLocal.getTime()).append("\n")
                        .append("Từ kỳ 04/2022 không thu tiền nước tại nhà")
                        .append("\n\n");
                if (charWidth == 31)
                    stringBuilder.append("\n\n");
                outputStream.write(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private int escpLength(String str) {
        if (str == null)
            return 0;
        return str.length() - (int) str.chars().filter(ch -> ch == 0x1B).count() * 3;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String pad(String left, int rightInt, char pad, int space) {
        String right = String.valueOf(rightInt);
        return pad(left, right, pad, space);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String pad(String left, String right, char pad, int space) {
        if (left == null)
            left = "";
        if (right == null)
            right = "";
        int leftLength = escpLength(left),
                rightLength = escpLength(right);
        int padLength = space - leftLength - rightLength;
        if (padLength > 0) {
            char[] padChar = new char[padLength];
            Arrays.fill(padChar, pad);
            return left + (new String(padChar)) + right;
        } else if (padLength < 0) {
            return breakLine(left + right, space);
        } else
            return left + right;
    }

    private String numberVN(double number) {
        return NumberFormat.getNumberInstance(Locale.GERMANY).format(number);
    }

    private String ReadGroup3(String G3) {
        String[] ReadDigit = {" Không", " Một", " Hai", " Ba", " Bốn", " Năm", " Sáu", " Bảy", " Tám", " Chín"};
        String temp;
        if (G3.equals("000")) return "";
        String c0 = G3.charAt(0) + "";
        String c1 = G3.charAt(1) + "";
        String c2 = G3.charAt(2) + "";
        temp = ReadDigit[Integer.parseInt(c0)] + " Trăm";
        if (c1.equals("0"))
            if (c2.equals("0")) return temp;
            else {
                temp += " Lẻ" + ReadDigit[Integer.parseInt(c2)];
                return temp;
            }
        else
            temp += ReadDigit[Integer.parseInt(c1)] + " Mươi";

        if (c2.equals("5")) temp += " Lăm";
        else if (!c2.equals("0")) temp += ReadDigit[Integer.parseInt(c2)];
        return temp;
    }

    private String ReadMoney(String Money) {
        String temp = "";
        try {
            Money = "000000000000".substring(Money.length()) + Money;
            String g1 = Money.substring(0, 3);
            String g2 = Money.substring(3, 6);
            String g3 = Money.substring(6, 9);
            String g4 = Money.substring(9, 12);
            if (!g1.equals("000")) {
                temp = ReadGroup3(g1);
                temp += " Tỷ";
            }
            if (!g2.equals("000")) {
                temp += ReadGroup3(g2);
                temp += " Triệu";
            }
            if (!g3.equals("000")) {
                temp += ReadGroup3(g3);
                temp += " Ngàn";
            }
            temp = temp + ReadGroup3(g4);
            temp = temp.replace("Một Mươi", "Mười");
            temp = temp.trim();
            if (temp.indexOf("Không Trăm") == 0)
                temp = temp.substring(10);
            temp = temp.trim();
            if (temp.indexOf("Lẻ") == 0)
                temp = temp.substring(2);
            temp = temp.trim();
            temp = temp.replace("Mươi Một", "Mươi Mốt");
            temp = temp.trim();
            return temp.substring(0, 1).toUpperCase() + temp.substring(1).toLowerCase() + " đồng ";
        } catch (Exception ignored) {
            return "";
        }
    }

    private String breakLine(String text, int lineLimit) {
        try {
            char ESC = 0x1B;
            if (text == null || text.trim().isEmpty())
                return "";
            StringBuilder sb = new StringBuilder(text);
            for (int i = 0, lineLenth = 0, breakIdx = 0; i < sb.length(); i++) {
                if (sb.charAt(i) == ESC) {
                    i += 2;
                    continue;
                } else if (sb.charAt(i) == ' ')
                    breakIdx = i;
                else if (sb.charAt(i) == '\n')
                    lineLenth = 0;
                if (lineLenth > lineLimit) {
                    lineLenth = i - breakIdx;
                    sb.replace(breakIdx, breakIdx + 1, "\n");
                }
                lineLenth++;
            }
            return sb.toString();
        } catch (Exception ignored) {
            return "";
        }
    }

    private String escpStyle(String text, int style) {
        //00000000: thường
        //00000001: nhỏ
        //00000010: nền đen
        //00000100: đảo ngược
        //00001000: đậm
        //00010000: 2 x cao
        //00100000: 2 x rộng
        //01000000: gạch ngang chữ
        //10000000: nền đen gạch ngang chữ
        char ESC = 0x1B, CMD_STYLE = '!';
        return "" + ESC + CMD_STYLE + (char) style + text + ESC + CMD_STYLE + (char) 0;
    }

    private String mdp31dQrPrint(String data, int dotSize) {
        StringBuilder sb = new StringBuilder();
        sb.append(new char[]{29, 40, 107, 3, 0, 49, 67, (char) dotSize});
        sb.append(new char[]{29, 40, 107, 3, 0, 49, 69, 48});
        byte[] dataBytes = data.getBytes();
        char b1 = (char) ((dataBytes.length + 3) % 256);
        char b2 = (char) ((dataBytes.length + 3) / 256);
        sb.append(new char[]{29, 40, 107, b1, b2, 49, 80, 48});
        sb.append(data);
        sb.append(new char[]{29, 40, 107, 3, 0, 49, 81, 48});
        return sb.toString();
    }

    private String woosimQrPrint(String data, int dotSize) {
        StringBuilder sb = new StringBuilder();
        sb.append(new char[]{29, 90, 2});
        byte[] dataBytes = data.getBytes();
        char b1 = (char) (dataBytes.length % 256);
        char b2 = (char) (dataBytes.length / 256);
        sb.append(new char[]{27, 90, 0, 'M', (char) dotSize, b1, b2});
        sb.append(data);
        return sb.toString();
    }

    ///////////////////////////////////

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void printGhiChiSo_escpEasyPrint(CEntityParent entityParent) throws IOException {
        try {
            if (entityParent != null) {
//                cal.setTime(formatter_old.parse(mHoaDon.getDenNgay()));
                String codeMoi = entityParent.getCodeMoi() == null ? "" : entityParent.getCodeMoi().trim();
                String lyDo = codeMoi.equals("F1") ? "Nhà đóng cửa" : codeMoi.equals("F2") ? "Kẹt khóa" : codeMoi.equals("F3") ? "Chất đồ" : "";
//                String thuTien;
//                if (mHoaDon.getThuTien() == null || mHoaDon.getThuTien().trim().isEmpty())
//                    thuTien = calcThuTien(cal);
//                else
//                    thuTien = mHoaDon.getThuTien();
                //ten kh
                String[] tenKhLines = null;
                String tenKh = entityParent.getHoTen();
                if (tenKh != null && !tenKh.trim().isEmpty()) {
                    tenKhLines = breakLine("KH:" + tenKh, 33).split("\n");
                }
                //dia chi
                String[] diaChiLines = null;
                String diaChi = entityParent.getDiaChi();
                if (diaChi != null && !diaChi.trim().isEmpty()) {
                    diaChiLines = breakLine("Đ/Chỉ:" + diaChi, 33).split("\n");
                }

                outputStream.write(mmChuoi);
                int y = 5;
                stringBuilder = new StringBuilder();
                stringBuilder.append("EZ\n");
                stringBuilder.append("{PRINT:\n");

                stringBuilder.append(printLine("CTY CP CẤP NƯỚC TÂN HÒA", 3, y, 25, 1, 1));
                y = handlingYMoreThan450(y, 25);
                stringBuilder.append(printLine("95 PHẠM HỮU CHÍ, P12, Q5", 1, y, 40, 1, 1));
                y = handlingYMoreThan450(y, 50);
                stringBuilder.append(printLine("CSKH: 19006489", 4, y, 10, 2, 2));
                y = handlingYMoreThan450(y, 75);
                if (codeMoi.startsWith("F")) {
                    //region in code F
                    stringBuilder.append(printLine("PHIẾU BÁO TRỞ NGẠI ĐỌC SỐ", 4, y, 10, 2, 1));
                    y = handlingYMoreThan450(y, 75);
                    stringBuilder.append(printLine("Kỳ:", 1, y, 0, 1, 1));
                    stringBuilder.append(printLine("%s", 3, y, 305, 1, 1, entityParent.getKy() + "/" + entityParent.getNam()));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("Từ ngày: " + entityParent.getTuNgay(), 1, y, 0, 1, 1));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("Đến ngày: " + entityParent.getDenNgay(), 1, y, 0, 1, 1));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("Danh bạ (Mã KH):", 1, y, 0, 1, 1))
                            .append(printLine("%s", 4, y, 210, 1, 1, entityParent.getDanhBo()));
                    y = handlingYMoreThan450(y, 25);
                    if (tenKhLines != null) {
                        for (String line : tenKhLines) {
                            stringBuilder.append(printLine(line, 1, y, 0, 1, 1));
                            y = handlingYMoreThan450(y, 25);
                        }
                    }
                    if (diaChiLines != null) {
                        for (String line : diaChiLines) {
                            stringBuilder.append(printLine(line, 1, y, 0, 1, 1));
                            y = handlingYMoreThan450(y, 25);
                        }
                    }
                    stringBuilder.append(printLine("GB:", 1, y, 0, 1, 1))
                            .append(printLine("%s", 3, y, 44, 1, 1, entityParent.getGiaBieu()));
                    stringBuilder.append(printLine("ĐM:", 1, y, 100, 1, 1))
                            .append(printLine("%s", 3, y, 160, 1, 1, entityParent.getDinhMuc()));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("MLT:", 1, y, 0, 1, 1))
                            .append(printLine("%s", 3, y, 255, 1, 1, entityParent.getMLT()));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("Lý do trở ngại:", 1, y, 0, 1, 1))
                            .append(printLine("%s", 3, y, 180, 1, 1, lyDo));
                    y = handlingYMoreThan450(y, 50);
                    stringBuilder.append(String.format(Locale.US, "@%d,80:HLINE,Length200,Thick3|", y));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("Quý khách vui lòng báo chỉ số nước", 1, y, 0, 1, 1));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("cho nhân viên đọc số:", 1, y, 0, 1, 1));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("%s", 1, y, 0, 1, 1, CLocal.HoTen));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("SĐT: %s", 3, y, 0, 2, 1, CLocal.DienThoai));
                    y = handlingYMoreThan450(y, 50);
                    stringBuilder.append(printLine("Hoặc tổng đài 19006489", 1, y, 0, 1, 1));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("Trong vòng 2 ngày kể từ ngày nhận", 1, y, 0, 1, 1));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("giấy báo để tính đúng lượng nước", 1, y, 0, 1, 1));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("tiêu thụ trong kỳ.", 1, y, 0, 1, 1));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("Nếu quý khách hàng không thông báo", 1, y, 0, 1, 1));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("công ty sẽ tạm tính tiêu thụ bình", 1, y, 0, 1, 1));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("quân 3 kỳ gần nhất.\n", 1, y, 0, 1, 1));
                    y = handlingYMoreThan450(y, 25);
                    //endregion
                } else {
                    //region in binh thuong
                    stringBuilder.append(printLine("PHIẾU BÁO CHỈ SỐ", 4, y, 80, 2, 1));
                    y = handlingYMoreThan450(y, 50);
                    stringBuilder.append(printLine("VÀ TIỀN NƯỚC DỰ KIẾN", 4, y, 40, 2, 1));
                    y = handlingYMoreThan450(y, 75);
                    stringBuilder.append(String.format(Locale.US, "@%d,0:PD417,YDIM 6,XDIM 2,COLUMNS 2,SECURITY 3|%s|\n", y, entityParent.getDanhBo()));
                    y = handlingYMoreThan450(y, 75);
                    stringBuilder.append(printLine("NV:%s", 1, y, 0, 1, 1, CLocal.HoTen));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("SĐT:%s", 1, y, 0, 1, 1, CLocal.DienThoai));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("Kỳ:", 1, y, 0, 1, 1));
                    stringBuilder.append(printLine("%s", 3, y, 305, 1, 1, entityParent.getKy() + "/" + entityParent.getNam()));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("Từ ngày: " + entityParent.getTuNgay(), 1, y, 0, 1, 1));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("Đến ngày: " + entityParent.getDenNgay(), 1, y, 0, 1, 1));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("Danh bạ (Mã KH):", 1, y, 0, 1, 1));
                    stringBuilder.append(printLine("%s", 4, y, 210, 1, 1, entityParent.getDanhBo()));
                    y = handlingYMoreThan450(y, 25);
                    if (tenKhLines != null) {
                        for (String line : tenKhLines) {
                            stringBuilder.append(printLine(line, 1, y, 0, 1, 1));
                            y = handlingYMoreThan450(y, 25);
                        }
                    }
                    if (diaChiLines != null) {
                        for (String line : diaChiLines) {
                            stringBuilder.append(printLine(line, 1, y, 0, 1, 1));
                            y = handlingYMoreThan450(y, 25);
                        }
                    }
                    stringBuilder.append(printLine("GB:", 1, y, 0, 1, 1));
                    stringBuilder.append(printLine("%s", 3, y, 44, 1, 1, entityParent.getGiaBieu()));
                    stringBuilder.append(printLine("ĐM:", 1, y, 100, 1, 1));
                    stringBuilder.append(printLine("%s", 3, y, 160, 1, 1, entityParent.getDinhMuc()));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("MLT:", 1, y, 0, 1, 1));
                    stringBuilder.append(printLine("%s", 3, y, 255, 1, 1, entityParent.getMLT()));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("Cs cũ:", 1, y, 0, 1, 1));
                    stringBuilder.append(printLine("%s", 3, y, 0, 1, 1,
                            padLeft(entityParent.getChiSo0(), 31)));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("Cs mới:", 1, y, 0, 1, 1));
                    stringBuilder.append(printLine("%s", 3, y, 0, 1, 1,
                            padLeft(entityParent.getChiSoMoi(), 31)));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("Tiêu thụ:", 1, y, 0, 1, 1));
                    stringBuilder.append(printLine("%s m3", 3, y, 0, 1, 1,
                            padLeft(entityParent.getTieuThuMoi(), 28)));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("Tiền nước:", 1, y, 0, 1, 1));
                    stringBuilder.append(printLine("%s đ", 3, y, 0, 1, 1,
                            padLeft(numberVN(Double.parseDouble(entityParent.getTienNuoc())), 30)));
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(printLine("Thuế VAT:", 1, y, 0, 1, 1));
                    stringBuilder.append(printLine("%s đ", 3, y, 0, 1, 1,
                            padLeft(numberVN(Double.parseDouble(entityParent.getThueGTGT())), 30)));
                    y = handlingYMoreThan450(y, 25);
                    if (Double.parseDouble(entityParent.getPhiBVMT()) > 0) {
                        stringBuilder.append(printLine("Tiền DV thoát nước:", 1, y, 0, 1, 1));
                        stringBuilder.append(printLine("%s đ", 3, y, 0, 1, 1,
                                padLeft(numberVN(Double.parseDouble(entityParent.getPhiBVMT())), 30)));
                        y = handlingYMoreThan450(y, 25);
                    }
                    if (Double.parseDouble(entityParent.getPhiBVMT_Thue()) > 0) {
                        stringBuilder.append(printLine("VAT DV thoát nước:", 1, y, 0, 1, 1));
                        stringBuilder.append(printLine("%s đ", 3, y, 0, 1, 1,
                                padLeft(numberVN(Double.parseDouble(entityParent.getPhiBVMT_Thue())), 30)));
                        y = handlingYMoreThan450(y, 25);
                    }
                    stringBuilder.append(printLine("Tổng cộng:", 1, y, 0, 1, 1));
                    stringBuilder.append(printLine("%s đ", 3, y, 0, 1, 1,
                            padLeft(numberVN(Double.parseDouble(entityParent.getTongCong())), 30)));
                    y = handlingYMoreThan450(y, 25);
//                    if (mHoaDon.getTienGiam() > 0) {
//                        stringBuilder.append(printLine("Tiền giảm:", 1, y, 0, 1, 1));
//                        stringBuilder.append(printLine("%s đ", 3, y, 0, 1, 1,
//                                padLeft(numberVN(mHoaDon.getTienGiam()), 30)));
//                        y = handlingYMoreThan450(y, 25);
//                        stringBuilder.append(printLine("Tiền thanh toán:", 1, y, 0, 1, 1));
//                        stringBuilder.append(printLine("%s đ", 3, y, 0, 1, 1,
//                                padLeft(numberVN(mHoaDon.getTienThanhToan()), 30)));
//                        y = handlingYMoreThan450(y, 25);
//                    }
                    int TongCong = Integer.parseInt(entityParent.getTongCong());
                    if (entityParent.getLstHoaDon().size() > 0) {
                        stringBuilder.append(printLine("Nợ cũ:", 1, y, 0, 1, 1));
                        y = handlingYMoreThan450(y, 25);
                        stringBuilder.append(printLine("(bỏ qua nếu đã thanh toán)", 1, y, 0, 1, 1));
                        y = handlingYMoreThan450(y, 25);
                        for (int i = 0; i < entityParent.getLstHoaDon().size(); i++) {
                            TongCong += Integer.parseInt(entityParent.getLstHoaDon().get(i).getTongCong());
                            stringBuilder.append(printLine("   Kỳ " + entityParent.getLstHoaDon().get(i).getKy() + ": " + numberVN(Double.parseDouble(entityParent.getLstHoaDon().get(i).getTongCong())), 1, y, 0, 1, 1));
                            y = handlingYMoreThan450(y, 25);
                        }
                    }
                    stringBuilder.append(printLine("Số tiền cần thanh toán:", 1, y, 0, 1, 1));
                    stringBuilder.append(printLine("%s đ", 3, y, 0, 1, 1,
                            padLeft(numberVN(Double.parseDouble(String.valueOf(TongCong))), 30)));
                    y = handlingYMoreThan450(y, 25);
                    //doc tien
                    String docTien = ReadMoney(String.valueOf(TongCong));
                    String[] docTienLines = null;
                    if (!docTien.trim().isEmpty()) {
                        String docTienBreak = breakLine("BẰNG CHỮ: " + docTien, 33);
                        if (!docTienBreak.isEmpty())
                            docTienLines = docTienBreak.split("\n");
                    }
                    if (docTienLines != null) {
                        for (String line : docTienLines) {
                            stringBuilder.append(printLine(line, 1, y, 0, 1, 1));
                            y = handlingYMoreThan450(y, 25);
                        }
                    }
                    y = handlingYMoreThan450(y, 25);
                    stringBuilder.append(String.format(Locale.US, "@%d,80:HLINE,Length200,Thick3|", y));
                    y = handlingYMoreThan450(y, 25);

//                    stringBuilder.append(printLine("Ngày thu tiền dự kiến từ %s", 1, y, 0, 1, 1, entityParent.getNgayThuTien()));
//                    y = handlingYMoreThan450(y, 25);

//                    stringBuilder.append(printLine("Phiếu này không có giá trị thanh toán", 1, y, 0, 1, 1));
//                    y = handlingYMoreThan450(y, 25);

                    //endregion
                }
                stringBuilder.append(printLine("KHÁCH HÀNG VUI LÒNG THANH", 1, y, 0, 1, 1));
                y = handlingYMoreThan450(y, 25);
                stringBuilder.append(printLine("TOÁN TIỀN NƯỚC TRONG 7 NGÀY", 1, y, 0, 1, 1));
                y = handlingYMoreThan450(y, 25);
                stringBuilder.append(printLine("KỂ TỪ NGÀY %s.", 3, y, 0, 1, 1, entityParent.getNgayThuTien()));
                y = handlingYMoreThan450(y, 25);
                stringBuilder.append(printLine("Để biết thêm thông tin chi tiết tiền", 1, y, 0, 1, 1));
                y = handlingYMoreThan450(y, 25);
                stringBuilder.append(printLine("nước quý khách hàng vui lòng liên hệ", 1, y, 0, 1, 1));
                y = handlingYMoreThan450(y, 25);
                stringBuilder.append(printLine("tổng đài 19006489 hoặc website:", 1, y, 0, 1, 1));
                stringBuilder.append(printLine("              19006489", 3, y, 0, 1, 1));
                y = handlingYMoreThan450(y, 25);
                stringBuilder.append(printLine("https://www.cskhtanhoa.com.vn", 3, y, 0, 1, 1));
                y = handlingYMoreThan450(y, 25);
                stringBuilder.append(printLine("Quét QR để xem chi tiết lịch sử sử", 1, y, 0, 1, 1));
                y = handlingYMoreThan450(y, 25);
                stringBuilder.append(printLine("dụng nước:", 1, y, 0, 1, 1));
                y = handlingYMoreThan450(y, 50);
                stringBuilder.append(easyPrintQr("https://service.cskhtanhoa.com.vn/khachhang/thongtin?danhbo=" + entityParent.getDanhBo().replace(" ", ""), y, 0));
                y = handlingYMoreThan450(y, 225);
                stringBuilder.append(printLine("TRÂN TRỌNG", 3, y, 130, 1, 1));
                y = handlingYMoreThan450(y, 50);
                stringBuilder.append(String.format(Locale.US, "@%d,80:HLINE,Length200,Thick3|", y));
                y = handlingYMoreThan450(y, 15);
                stringBuilder.append(printLine("Được in vào: %s", 1, y, 0, 1, 1, CLocal.getTime()));
                y = handlingYMoreThan450(y, 25);
                stringBuilder.append(printLine("Từ kỳ 04/2022 không thu tiền nước", 1, y, 0, 1, 1));
                y = handlingYMoreThan450(y, 25);
                stringBuilder.append(printLine("tại nhà", 1, y, 0, 1, 1));
                y = handlingYMoreThan450(y, 100);
                stringBuilder.append(printLine(".", 1, y, 0, 1, 1));
                stringBuilder.append("}\n");
                outputStream.write(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static String padLeft(String s, int n) {
        return String.format("%1$" + (n + n - s.length()) + "s", s);
    }

    public static String padLeft(int sInt, int n) {
        String s = String.valueOf(sInt);
        return padLeft(s, n);
    }

    private String printLine(String data, int boldNumber, Object... args) {
        String base = "@%d,%d:TIMNR,HMULT%d,VMULT%d|" + data + "|\n";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < boldNumber; i++) {
            builder.append(String.format(Locale.US, base, args));
            args[1] = (int) args[1] + 1;
        }
        return builder.toString();
    }

    private int handlingYMoreThan450(int y, int delta) {
        if (y + delta > 450) {
            stringBuilder.append("}\n");
            try {
                outputStream.write(stringBuilder.toString().getBytes());
                outputStream.write(mmChuoi);

                stringBuilder = new StringBuilder();
                stringBuilder.append("EZ\n");
                stringBuilder.append("{PRINT:\n");
            } catch (IOException ignored) {
            }
            return 0;
        } else
            return y + delta;
    }

    private String easyPrintQr(String data, int y, int x) {
        //@620,120:QRCOD,ARG16,ARG22|HONEYWELL|
        //@620,120:QRCOD,HIGH6,WIDE6,ARG22|HONEYWELL|
        return String.format(Locale.US, "@%d,%d:QRCOD,HIGH6,WIDE6,ARG22|%s|\n", y, x, data);
    }


}
