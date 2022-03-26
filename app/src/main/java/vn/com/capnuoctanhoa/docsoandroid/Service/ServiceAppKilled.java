package vn.com.capnuoctanhoa.docsoandroid.Service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.google.gson.Gson;

import vn.com.capnuoctanhoa.docsoandroid.Class.CLocal;

public class ServiceAppKilled extends Service {
    public ServiceAppKilled() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        try {
            CLocal.ghiListToFileDocSo();
//            if (CLocal.jsonMessage != null)
//                editor.putString("jsonMessage", CLocal.jsonMessage.toString());
//            editor.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //stop ServiceThermalPrinter
        stopService(new Intent(this, ServiceThermalPrinter.class));


        //stop ServiceAppKilled
        this.stopSelf();

    }
}
