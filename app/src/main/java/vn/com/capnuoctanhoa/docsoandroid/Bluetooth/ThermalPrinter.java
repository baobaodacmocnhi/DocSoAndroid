package vn.com.capnuoctanhoa.docsoandroid.Bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

import vn.com.capnuoctanhoa.docsoandroid.Class.CEntityChild;
import vn.com.capnuoctanhoa.docsoandroid.Class.CEntityParent;
import vn.com.capnuoctanhoa.docsoandroid.Class.CLocal;

public class ThermalPrinter {
    private Activity activity;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private static BluetoothDevice bluetoothDevice = null;
    private ArrayList<BluetoothDevice> lstBluetoothDevice;
    private ArrayList<String> arrayList;
    private static OutputStream outputStream;
    private InputStream inputStream;
    private Thread thread;
    private byte[] readBuffer;
    private int readBufferPosition;
    private volatile boolean stopWorker;
    private static final byte[] ESC = {0x1B};
    private static StringBuilder stringBuilder;
    //    private CEntityParent entityParent;
    private int toadoX = 10;
    private static int toadoY = 0;
    private int widthFont = 1;
    private int heightFont = 1;
    private int lengthPaper = 33;

    public static BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public ArrayList<BluetoothDevice> getLstBluetoothDevice() {
        return lstBluetoothDevice;
    }

    public void setLstBluetoothDevice(ArrayList<BluetoothDevice> lstBluetoothDevice) {
        this.lstBluetoothDevice = lstBluetoothDevice;
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    public ThermalPrinter(Activity activity) {
        this.activity = activity;
        findBluetoothDevice();
        for (int i = 0; i < lstBluetoothDevice.size(); i++)
            if (lstBluetoothDevice.get(i).getAddress().equals(CLocal.ThermalPrinter)) {
                bluetoothDevice = lstBluetoothDevice.get(i);
                if (bluetoothDevice != null) {
                    openBluetoothPrinter();
                    beginListenData();
                }
            }
//        bluetoothDevice = bluetoothAdapter.getRemoteDevice(CLocal.ThermalPrinter);
//        if (bluetoothDevice != null)
//            openBluetoothPrinter();
    }

    private void findBluetoothDevice() {
        try {
            lstBluetoothDevice = new ArrayList<BluetoothDevice>();
            arrayList = new ArrayList<String>();
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter == null) {
                arrayList.add("Chưa có kết nối nào");
            } else if (bluetoothAdapter.isEnabled() == false) {
                CLocal.setOnBluetooth(activity);
            } else {
                Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();
                if (pairedDevice.size() > 0) {
                    for (BluetoothDevice pairedDev : pairedDevice) {
                        // My Bluetoth printer name is BTP_F09F1A
//                    if (pairedDev.getName().equals("BTP_F09F1A"))
                        {
                            lstBluetoothDevice.add(pairedDev);
                            arrayList.add(pairedDev.getName() + "\n" + pairedDev.getAddress());
//                        break;
                        }
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void openBluetoothPrinter() {
        try {
            if (bluetoothSocket == null) {
                //Standard uuid from string //
                UUID uuidSting = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
                bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuidSting);
                bluetoothSocket.connect();
                outputStream = bluetoothSocket.getOutputStream();
                inputStream = bluetoothSocket.getInputStream();
//                beginListenData();
            } else if (bluetoothSocket.isConnected() == false)
                bluetoothSocket.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void beginListenData() {
        try {
            final Handler handler = new Handler();
            final byte delimiter = 10;
            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                        try {
                            int byteAvailable = inputStream.available();
                            if (byteAvailable > 0) {
                                byte[] packetByte = new byte[byteAvailable];
                                inputStream.read(packetByte);

                                for (int i = 0; i < byteAvailable; i++) {
                                    byte b = packetByte[i];
                                    if (b == delimiter) {
                                        byte[] encodedByte = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer, 0,
                                                encodedByte, 0,
                                                encodedByte.length
                                        );
                                        final String data = new String(encodedByte, "US-ASCII");
                                        readBufferPosition = 0;
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
//                                                lblPrinterName.setText(data);
                                            }
                                        });
                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            stopWorker = true;
                        }
                    }
                }
            });
            thread.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void disconnectBluetoothDevice() {
        try {
            stopWorker = true;
            outputStream.close();
            inputStream.close();
            bluetoothSocket.close();
        } catch (Exception ex) {
            Log.e("disconnectBluetooth", ex.getMessage());
        }
    }

    public static byte[] initPrinter() {
        return new byte[]{27, 64};
    }

    public static byte[] printNewLine() {
        return new byte[]{10};
    }

    public static byte[] printDotFeed_ESC() {
        return "------------------------------\n".getBytes();
    }

    public static byte[] printDotFeed_ESC(int n) {
        return new byte[]{27, 74, (byte) n};
    }

    public static byte[] printLineFeed(int n) {
        return new byte[]{27, 100, (byte) n};
    }

    public static byte[] setTextStyle(boolean bold, int extWidth, int extHeight) {
        int n1 = extWidth - 1;
        int n2 = extHeight - 1;
        if (n1 < 0) {
            n1 = 0;
        }

        if (n1 > 7) {
            n1 = 7;
        }

        if (n2 < 0) {
            n2 = 0;
        }

        if (n2 > 7) {
            n2 = 7;
        }

        byte extension = (byte) (n1 & 15 | n2 << 4 & 240);
        return new byte[]{27, 69, (byte) (bold ? 1 : 0), 29, 33, extension};
    }

    public static byte[] setTextAlign(int align) {
        return new byte[]{27, 97, (byte) align};
    }

    //initial printer
    private void initialPrinter() {
        resetPrinter();
        setLineSpacing();
        setTimeNewRoman();
    }

    //reset printer
    private static void resetPrinter() {
        try {
            outputStream.write(new byte[]{0x1B, 0x40});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //set font Times New Roman
    private void setTimeNewRoman() {
        try {
            outputStream.write(new byte[]{0x1B, 0x77, 0x35});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //set line spacing
    private static void setLineSpacing() {
        try {
            //set line spacing using minimun units
            outputStream.write(new byte[]{0x1B, '0'});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printHangNgang() {
        try {
            outputStream.write(".............................\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //print new line
    private void printNewLine(int numberLine) {
        try {
            for (int i = 0; i < numberLine; i++) {
                outputStream.write(new byte[]{0x0A});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printText(String content, int align) {
        try {
            switch (align) {
                case 0:
                    //left align
                    outputStream.write(new byte[]{0x1B, 'a', 0x00});
                    break;
                case 1:
                    //center align
                    outputStream.write(new byte[]{0x1B, 'a', 0x01});
                    break;
                case 2:
                    //right align
                    outputStream.write(new byte[]{0x1B, 'a', 0x02});
                    break;
            }
            outputStream.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s).replace(" ", "  ");
    }

    private int handlingYMoreThan450(int y, int delta) {
        if (y + delta > 450) {
            stringBuilder.append("}\n");
            try {
                outputStream.write(stringBuilder.toString().getBytes());
                outputStream.write(ESC);

                stringBuilder = new StringBuilder();
                stringBuilder.append("EZ\n");
                stringBuilder.append("{PRINT:\n");
            } catch (IOException e) {

            } finally {
                return 0;
            }
        } else
            return y + delta;
    }

    private String[] getDateTime() {
        final Calendar c = Calendar.getInstance();
        String dateTime[] = new String[2];
        dateTime[0] = c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR);
        dateTime[1] = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
        return dateTime;
    }


}
