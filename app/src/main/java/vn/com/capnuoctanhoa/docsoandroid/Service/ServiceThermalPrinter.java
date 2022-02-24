package vn.com.capnuoctanhoa.docsoandroid.Service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.Vector;

import androidx.annotation.Nullable;
import vn.com.capnuoctanhoa.docsoandroid.Class.CEntityChild;
import vn.com.capnuoctanhoa.docsoandroid.Class.CEntityParent;
import vn.com.capnuoctanhoa.docsoandroid.Class.CLocal;

public class ServiceThermalPrinter extends Service {
    public ServiceThermalPrinter() {
    }

    private BluetoothAdapter mBluetoothAdapter;
    public static String B_DEVICE = "MY_DEVICE";
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
        B_DEVICE = intent.getStringExtra("ThermalPrinter");
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        connectToDevice(B_DEVICE);
        return START_STICKY;
    }

    private synchronized void connectToDevice(String macAddress) {
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
    private static final byte[] ESC = {0x1B};
    private static int toadoY = 0;
    private static StringBuilder stringBuilder;

    public byte[] printDotFeed_ESC() {
//        return "------------------------------\n".getBytes();
        return "".getBytes();
    }

    public byte[] printLineFeed(int n) {
        return new byte[]{27, 100, (byte) n};
    }

    public byte[] setTextStyle(boolean bold, int extWidth, int extHeight) {
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

    public byte[] setTextAlign(int align) {
        return new byte[]{27, 97, (byte) align};
    }

    //reset printer
    private void resetPrinter() {
        try {
            outputStream.write(new byte[]{0x1B, 0x40});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
