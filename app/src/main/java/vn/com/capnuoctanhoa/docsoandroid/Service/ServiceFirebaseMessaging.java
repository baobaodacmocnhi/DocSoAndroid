package vn.com.capnuoctanhoa.docsoandroid.Service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.PowerManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import androidx.core.app.NotificationCompat;

import vn.com.capnuoctanhoa.docsoandroid.ActivityDangNhap;
import vn.com.capnuoctanhoa.docsoandroid.Class.CBitmap;
import vn.com.capnuoctanhoa.docsoandroid.Class.CEntityParent;
import vn.com.capnuoctanhoa.docsoandroid.Class.CLocal;
import vn.com.capnuoctanhoa.docsoandroid.Class.CWebservice;
import vn.com.capnuoctanhoa.docsoandroid.DocSo.ActivityDocSo_DanhSach;
import vn.com.capnuoctanhoa.docsoandroid.MainActivity;
import vn.com.capnuoctanhoa.docsoandroid.R;

public class ServiceFirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        sendRegistrationToServer(s);
    }

    public void sendRegistrationToServer(String token) {
        SharedPreferences.Editor editor = CLocal.sharedPreferencesre.edit();
        editor.putString("UID", token);
        editor.commit();
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //liên kết hàm [spSendNotificationToClient] sqlserver
        if (remoteMessage.getData().containsKey("Action"))
            if (remoteMessage.getData().get("Action").equals("DangXuat")) {
                CLocal.initialCLocal();
            } else if (remoteMessage.getData().get("Action").equals("DocSo") == true) {
                if (CLocal.listDocSo != null && CLocal.listDocSo.size() > 0) {
                    CLocal.updateValueChild(CLocal.listDocSo, remoteMessage.getData().get("NameUpdate"), "", remoteMessage.getData().get("ID"));
                    CLocal.updateValueChild(CLocal.listDocSoView, remoteMessage.getData().get("NameUpdate"), "", remoteMessage.getData().get("ID"));
                }
            } else if (remoteMessage.getData().get("Action").equals("Ton") == true) {
                if (remoteMessage.getData().get("NameUpdate").equals("CodeTon") == true) {
                    sendCodeTonToServer(remoteMessage.getData().get("ID"), remoteMessage.getData().get("ValueUpdate"));
                } else if (remoteMessage.getData().get("NameUpdate").equals("HinhTon") == true) {
                    sendHinhTonToServer(remoteMessage.getData().get("ID"), remoteMessage.getData().get("ValueUpdate"));
                }
            } else if (remoteMessage.getData().get("Action").equals("ChuBao") == true) {
                try {
                    if (CLocal.listDocSo != null && CLocal.listDocSo.size() > 0)
                        for (int i = 0; i < CLocal.listDocSo.size(); i++)
                            if (CLocal.listDocSo.get(i).getCodeMoi().equals("") == true && CLocal.listDocSo.get(i).getID().equals(remoteMessage.getData().get("ID")) == true) {
                                JSONObject jsonObjectC = new JSONObject(remoteMessage.getData().get("ValueUpdate").replace("null", ""));
                                CLocal.listDocSo.get(i).setSync(true);
                                CLocal.listDocSo.get(i).setChuBao(true);
                                CLocal.listDocSo.get(i).setCodeMoi(jsonObjectC.getString("CodeMoi").replace("null", ""));
                                CLocal.listDocSo.get(i).setChiSoMoi(jsonObjectC.getString("ChiSoMoi").replace("null", ""));
                                if (jsonObjectC.getString("CSC").replace("null", "").equals("") == false)
                                    CLocal.listDocSo.get(i).setChiSo0(jsonObjectC.getString("CSC").replace("null", ""));
                                CLocal.listDocSo.get(i).setTieuThuMoi(jsonObjectC.getString("TieuThu").replace("null", ""));
                                CLocal.listDocSo.get(i).setTienNuoc(jsonObjectC.getString("TienNuoc").replace("null", ""));
                                CLocal.listDocSo.get(i).setThueGTGT(jsonObjectC.getString("ThueGTGT").replace("null", ""));
                                CLocal.listDocSo.get(i).setPhiBVMT(jsonObjectC.getString("PhiBVMT").replace("null", ""));
                                CLocal.listDocSo.get(i).setPhiBVMT_Thue(jsonObjectC.getString("PhiBVMT_Thue").replace("null", ""));
                                CLocal.listDocSo.get(i).setTongCong(jsonObjectC.getString("TongCong").replace("null", ""));
                            }
                    if (CLocal.listDocSoView != null && CLocal.listDocSoView.size() > 0)
                        for (int i = 0; i < CLocal.listDocSoView.size(); i++)
                            if (CLocal.listDocSoView.get(i).getCodeMoi().equals("") == true && CLocal.listDocSoView.get(i).getID().equals(remoteMessage.getData().get("ID")) == true) {
                                JSONObject jsonObjectC = new JSONObject(remoteMessage.getData().get("ValueUpdate").replace("null", ""));
                                CLocal.listDocSoView.get(i).setSync(true);
                                CLocal.listDocSoView.get(i).setChuBao(true);
                                CLocal.listDocSoView.get(i).setCodeMoi(jsonObjectC.getString("CodeMoi").replace("null", ""));
                                CLocal.listDocSoView.get(i).setChiSoMoi(jsonObjectC.getString("ChiSoMoi").replace("null", ""));
                                if (jsonObjectC.getString("CSC").replace("null", "").equals("") == false)
                                    CLocal.listDocSoView.get(i).setChiSo0(jsonObjectC.getString("CSC").replace("null", ""));
                                CLocal.listDocSoView.get(i).setTieuThuMoi(jsonObjectC.getString("TieuThu").replace("null", ""));
                                CLocal.listDocSoView.get(i).setTienNuoc(jsonObjectC.getString("TienNuoc").replace("null", ""));
                                CLocal.listDocSoView.get(i).setThueGTGT(jsonObjectC.getString("ThueGTGT").replace("null", ""));
                                CLocal.listDocSoView.get(i).setPhiBVMT(jsonObjectC.getString("PhiBVMT").replace("null", ""));
                                CLocal.listDocSoView.get(i).setPhiBVMT_Thue(jsonObjectC.getString("PhiBVMT_Thue").replace("null", ""));
                                CLocal.listDocSoView.get(i).setTongCong(jsonObjectC.getString("TongCong").replace("null", ""));
                            }
                } catch (Exception ex) {

                }
                //Calling method to generate notification
//        Random random = new Random();
//        int UNIQUE_INT_VALUE_FOR_EVERY_CALL=random.nextInt(9999 - 1000) + 1000;;
                int UNIQUE_INT_VALUE_FOR_EVERY_CALL = 0;
                Intent intent = new Intent(this, ActivityDocSo_DanhSach.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, UNIQUE_INT_VALUE_FOR_EVERY_CALL, intent, 0);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                pendingIntent = PendingIntent.getActivity(this, UNIQUE_INT_VALUE_FOR_EVERY_CALL, intent, 0);
                try {
                    if (CLocal.jsonMessage == null)
                        CLocal.jsonMessage = new JSONArray();
                    JSONObject jsonObject = new JSONObject();
                    SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    jsonObject.put("NgayNhan", currentDate.format(new Date()));
                    jsonObject.put("Title", remoteMessage.getData().get("Title"));
                    jsonObject.put("Content", remoteMessage.getData().get("Body"));
                    CLocal.jsonMessage.put(jsonObject);
                } catch (Exception ex) {
                }

                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder notificationBuilder = null;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel mChannel = notificationManager.getNotificationChannel("DocSoNotification_ID");
                    if (mChannel == null) {
                        mChannel = new NotificationChannel("DocSoNotification_ID", "DocSoNotification_Name", NotificationManager.IMPORTANCE_HIGH);
                        mChannel.setDescription("DocSoNotification_Des");
                        mChannel.enableVibration(true);
                        mChannel.setVibrationPattern(new long[]{0, 1000});
                        notificationManager.createNotificationChannel(mChannel);
                    }
                    notificationBuilder = new NotificationCompat.Builder(this, "DocSoNotification_ID");

                    notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(remoteMessage.getData().get("Title"))
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getData().get("Body")))
                            .setContentText(remoteMessage.getData().get("Body"))
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setAutoCancel(true)
                            .setSound(defaultSoundUri)
                            .setVibrate(new long[]{0, 1000})
//                    .setGroup("vn.com.capnuoctanhoa.docsoandroid")
//                    .setGroupSummary(true)
                            .setContentIntent(pendingIntent);
                } else {
                    notificationBuilder = new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(remoteMessage.getData().get("Title"))
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getData().get("Body")))
                            .setContentText(remoteMessage.getData().get("Body"))
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setAutoCancel(true)
                            .setSound(defaultSoundUri)
                            .setVibrate(new long[]{0, 1000})
//                    .setGroup("vn.com.capnuoctanhoa.docsoandroid")
//                    .setGroupSummary(true)
                            .setContentIntent(pendingIntent);
                }

                PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
                boolean isScreenOn = false;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
                    isScreenOn = powerManager.isInteractive();
                }
                if (isScreenOn == false) {
                    @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "MyLock");
                    wl.acquire();
                    wl.release();
//                PowerManager.WakeLock wl_cpu = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"MyCpuLock");
//                wl_cpu.acquire(10000);
                }

                Random random = new Random();
                int id = random.nextInt(9999 - 1000) + 1000;
//        int id = 1000;
                notificationManager.notify(id, notificationBuilder.build());
            }
    }
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);
//        //Calling method to generate notification
////        Random random = new Random();
////        int UNIQUE_INT_VALUE_FOR_EVERY_CALL=random.nextInt(9999 - 1000) + 1000;;
//        int UNIQUE_INT_VALUE_FOR_EVERY_CALL = 0;
//        Intent intent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, UNIQUE_INT_VALUE_FOR_EVERY_CALL, intent, 0);
//
//        //liên kết hàm [spSendNotificationToClient] sqlserver
//        if (remoteMessage.getData().containsKey("Action"))
//            if (remoteMessage.getData().get("Action").equals("DangXuat")) {
//                CLocal.initialCLocal();
//                intent = new Intent(this, ActivityDangNhap.class);
//            } else if (CLocal.listDocSo != null && CLocal.listDocSo.size() > 0) {
//                if (remoteMessage.getData().get("Action").equals("DocSo")) {
//                    CLocal.updateValueChild(CLocal.listDocSo, remoteMessage.getData().get("NameUpdate"), "", remoteMessage.getData().get("ID"));
//                    CLocal.updateValueChild(CLocal.listDocSoView, remoteMessage.getData().get("NameUpdate"), "", remoteMessage.getData().get("ID"));
//                } else if (remoteMessage.getData().get("NameUpdate").equals("CodeTon")) {
//                    sendCodeTonToServer(remoteMessage.getData().get("ID"));
//                } else if (remoteMessage.getData().get("NameUpdate").equals("HinhTon")) {
//                    sendHinhTonToServer(remoteMessage.getData().get("ID"));
//                }
//                intent = new Intent(this, ActivityDocSo_DanhSach.class);
//            } else {
//                intent = new Intent(this, MainActivity.class);
//            }
//
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        pendingIntent = PendingIntent.getActivity(this, UNIQUE_INT_VALUE_FOR_EVERY_CALL, intent, 0);
//        try {
//            if (CLocal.jsonMessage == null)
//                CLocal.jsonMessage = new JSONArray();
//            JSONObject jsonObject = new JSONObject();
//            SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//            jsonObject.put("NgayNhan", currentDate.format(new Date()));
//            jsonObject.put("Title", remoteMessage.getData().get("Title"));
//            jsonObject.put("Content", remoteMessage.getData().get("Body"));
//            CLocal.jsonMessage.put(jsonObject);
//        } catch (Exception ex) {
//        }
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        NotificationCompat.Builder notificationBuilder = null;
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationChannel mChannel = notificationManager.getNotificationChannel("DocSoNotification_ID");
//            if (mChannel == null) {
//                mChannel = new NotificationChannel("DocSoNotification_ID", "DocSoNotification_Name", NotificationManager.IMPORTANCE_HIGH);
//                mChannel.setDescription("DocSoNotification_Des");
//                mChannel.enableVibration(true);
//                mChannel.setVibrationPattern(new long[]{0, 1000});
//                notificationManager.createNotificationChannel(mChannel);
//            }
//            notificationBuilder = new NotificationCompat.Builder(this, "DocSoNotification_ID");
//
//            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
//                    .setContentTitle(remoteMessage.getData().get("Title"))
//                    .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getData().get("Body")))
//                    .setContentText(remoteMessage.getData().get("Body"))
//                    .setDefaults(Notification.DEFAULT_ALL)
//                    .setAutoCancel(true)
//                    .setSound(defaultSoundUri)
//                    .setVibrate(new long[]{0, 1000})
////                    .setGroup("vn.com.capnuoctanhoa.docsoandroid")
////                    .setGroupSummary(true)
//                    .setContentIntent(pendingIntent);
//        } else {
//            notificationBuilder = new NotificationCompat.Builder(this)
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setContentTitle(remoteMessage.getData().get("Title"))
//                    .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getData().get("Body")))
//                    .setContentText(remoteMessage.getData().get("Body"))
//                    .setDefaults(Notification.DEFAULT_ALL)
//                    .setAutoCancel(true)
//                    .setSound(defaultSoundUri)
//                    .setVibrate(new long[]{0, 1000})
////                    .setGroup("vn.com.capnuoctanhoa.docsoandroid")
////                    .setGroupSummary(true)
//                    .setContentIntent(pendingIntent);
//        }
//
//        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
//        boolean isScreenOn = false;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
//            isScreenOn = powerManager.isInteractive();
//        }
//        if (isScreenOn == false) {
//            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "MyLock");
//            wl.acquire();
//            wl.release();
////                PowerManager.WakeLock wl_cpu = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"MyCpuLock");
////                wl_cpu.acquire(10000);
//        }
//
//        Random random = new Random();
//        int id = random.nextInt(9999 - 1000) + 1000;
////        int id = 1000;
//        notificationManager.notify(id, notificationBuilder.build());
//    }

    public void sendCodeTonToServer(String ID, String Dot) {
        MyAsyncTaskSyncCodeTon myAsyncTaskSyncCodeTon = new MyAsyncTaskSyncCodeTon();
        myAsyncTaskSyncCodeTon.execute(new String[]{ID, Dot});
    }

    public void sendHinhTonToServer(String ID, String Dot) {
        MyAsyncTaskSyncHinhTon myAsyncTaskSyncHinhTon = new MyAsyncTaskSyncHinhTon();
        myAsyncTaskSyncHinhTon.execute(new String[]{ID, Dot});
    }

    public class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                CWebservice ws = new CWebservice();
                ws.updateUID(CLocal.sharedPreferencesre.getString("MaNV", ""), CLocal.sharedPreferencesre.getString("UID", ""));
            } catch (Exception ex) {
                CLocal.showToastMessage(getApplicationContext(), ex.getMessage());
            }
            return null;
        }
    }

    public class MyAsyncTaskSyncCodeTon extends AsyncTask<String, String, String> {
        CWebservice ws;

        @Override
        protected String doInBackground(String... strings) {
            ws = new CWebservice();
            String error = "";
            try {
                String ID = strings[0];
                if (ID != null && ID.equals("") == false) {
                    String Nam = "", Ky = "", Dot = "";
                    if (CLocal.listDocSo != null && CLocal.listDocSo.size() > 0) {
                        Nam = CLocal.listDocSo.get(0).getNam();
                        Ky = CLocal.listDocSo.get(0).getKy();
                        Dot = CLocal.listDocSo.get(0).getDot();
                    }
                    if (ID.substring(0, 4).equals(Nam) == true && ID.substring(4, 6).equals(Ky) == true && strings[1].equals(Dot) == true)
                        for (int i = 0; i < CLocal.listDocSo.size(); i++) {
                            if (CLocal.listDocSo.get(i).getID().equals(ID) == true && CLocal.listDocSo.get(i).getCodeMoi().equals("") == false) {
                                String HinhDHN = "";
                                Bitmap bitmap = BitmapFactory.decodeFile(CLocal.pathAppPicture + "/" + CLocal.listDocSo.get(i).getNam() + "_" + CLocal.listDocSo.get(i).getKy() + "_" + CLocal.listDocSo.get(i).getDot() + "/" + CLocal.listDocSo.get(i).getDanhBo().replace(" ", "") + ".jpg");
                                if (bitmap != null) {
                                    HinhDHN = CBitmap.convertBitmapToString(bitmap);
                                }
                                String result = ws.ghiChiSo_GianTiep(CLocal.listDocSo.get(i).getID(), CLocal.listDocSo.get(i).getCodeMoi(), CLocal.listDocSo.get(i).getChiSoMoi(), CLocal.listDocSo.get(i).getTieuThuMoi()
                                        , CLocal.listDocSo.get(i).getTienNuoc(), CLocal.listDocSo.get(i).getThueGTGT(), CLocal.listDocSo.get(i).getPhiBVMT(), CLocal.listDocSo.get(i).getPhiBVMT_Thue(), CLocal.listDocSo.get(i).getTongCong(),
                                        HinhDHN, CLocal.listDocSo.get(i).getDot(), CLocal.May, CLocal.listDocSo.get(i).getModifyDate());
                                JSONObject jsonObject = null;
                                if (result.equals("") == false)
                                    jsonObject = new JSONObject(result);
                                if (jsonObject != null && Boolean.parseBoolean(jsonObject.getString("success").replace("null", "")) == true) {
                                    CLocal.listDocSo.get(i).setSync(true);
                                    CLocal.listDocSo.get(i).setGhiHinh(true);
                                }
                            }
                        }
                    else {
                        ArrayList<CEntityParent> listDocSo = new Gson().fromJson(CLocal.readFile(CLocal.pathAppDownload, ID.substring(0, 4) + "_" + ID.substring(4, 6) + "_" + strings[1] + ".txt"), new TypeToken<ArrayList<CEntityParent>>() {
                        }.getType());
                        for (int i = 0; i < listDocSo.size(); i++) {
                            if (listDocSo.get(i).getID().equals(ID) == true && listDocSo.get(i).getCodeMoi().equals("") == false) {
                                String HinhDHN = "";
                                Bitmap bitmap = BitmapFactory.decodeFile(CLocal.pathAppPicture + "/" + listDocSo.get(i).getNam() + "_" + listDocSo.get(i).getKy() + "_" + listDocSo.get(i).getDot() + "/" + listDocSo.get(i).getDanhBo().replace(" ", "") + ".jpg");
                                if (bitmap != null) {
                                    HinhDHN = CBitmap.convertBitmapToString(bitmap);
                                }
                                String result = ws.ghiChiSo_GianTiep(listDocSo.get(i).getID(), listDocSo.get(i).getCodeMoi(), listDocSo.get(i).getChiSoMoi(), listDocSo.get(i).getTieuThuMoi()
                                        , listDocSo.get(i).getTienNuoc(), listDocSo.get(i).getThueGTGT(), listDocSo.get(i).getPhiBVMT(), listDocSo.get(i).getPhiBVMT_Thue(), listDocSo.get(i).getTongCong(),
                                        HinhDHN, listDocSo.get(i).getDot(), CLocal.May, listDocSo.get(i).getModifyDate());
                                JSONObject jsonObject = null;
                                if (result.equals("") == false)
                                    jsonObject = new JSONObject(result);
                                if (jsonObject != null && Boolean.parseBoolean(jsonObject.getString("success").replace("null", "")) == true) {
                                    listDocSo.get(i).setSync(true);
                                    listDocSo.get(i).setGhiHinh(true);
                                }
                            }
                        }
                        CLocal.writeFile(CLocal.pathAppDownload, ID.substring(0, 4) + "_" + ID.substring(4, 6) + "_" + strings[1] + ".txt", new Gson().toJsonTree(listDocSo).getAsJsonArray().toString());
                    }
                }
            } catch (Exception e) {
                error = e.getMessage();
            }
            return error;
        }
    }

    public class MyAsyncTaskSyncHinhTon extends AsyncTask<String, String, String> {
        CWebservice ws;

        @Override
        protected String doInBackground(String... strings) {
            ws = new CWebservice();
            String error = "";
            try {
                String ID = strings[0];
                if (ID != null && ID.equals("") == false) {
                    String Nam = "", Ky = "", Dot = "";
                    if (CLocal.listDocSo != null && CLocal.listDocSo.size() > 0) {
                        Nam = CLocal.listDocSo.get(0).getNam();
                        Ky = CLocal.listDocSo.get(0).getKy();
                        Dot = CLocal.listDocSo.get(0).getDot();
                    }
                    if (ID.substring(0, 4).equals(Nam) == true && ID.substring(4, 6).equals(Ky) == true && strings[1].equals(Dot) == true)
                        for (int i = 0; i < CLocal.listDocSo.size(); i++) {
                            if (CLocal.listDocSo.get(i).getID().equals(ID) == true
                                    && CLocal.listDocSo.get(i).getCodeMoi().equals("") == false) {
                                String HinhDHN = "";
                                Bitmap bitmap = BitmapFactory.decodeFile(CLocal.pathAppPicture + "/" + CLocal.listDocSo.get(i).getNam() + "_" + CLocal.listDocSo.get(i).getKy() + "_" + CLocal.listDocSo.get(i).getDot() + "/" + CLocal.listDocSo.get(i).getDanhBo().replace(" ", "") + ".jpg");
                                if (bitmap != null) {
                                    HinhDHN = CBitmap.convertBitmapToString(bitmap);
                                    String result = ws.ghi_Hinh(CLocal.listDocSo.get(i).getID(), HinhDHN);
                                    if (Boolean.parseBoolean(result) == true)
                                        CLocal.listDocSo.get(i).setGhiHinh(true);
                                }
                            }
                        }
                    else {
                        ArrayList<CEntityParent> listDocSo = new Gson().fromJson(CLocal.readFile(CLocal.pathAppDownload, ID.substring(0, 4) + "_" + ID.substring(4, 6) + "_" + strings[1] + ".txt"), new TypeToken<ArrayList<CEntityParent>>() {
                        }.getType());
                        for (int i = 0; i < listDocSo.size(); i++) {
                            if (listDocSo.get(i).getID().equals(ID) == true
                                    && listDocSo.get(i).getCodeMoi().equals("") == false) {
                                String HinhDHN = "";
                                Bitmap bitmap = BitmapFactory.decodeFile(CLocal.pathAppPicture + "/" + listDocSo.get(i).getNam() + "_" + listDocSo.get(i).getKy() + "_" + listDocSo.get(i).getDot() + "/" + listDocSo.get(i).getDanhBo().replace(" ", "") + ".jpg");
                                if (bitmap != null) {
                                    HinhDHN = CBitmap.convertBitmapToString(bitmap);
                                    String result = ws.ghi_Hinh(listDocSo.get(i).getID(), HinhDHN);
                                    if (Boolean.parseBoolean(result) == true)
                                        listDocSo.get(i).setGhiHinh(true);
                                }
                            }
                        }
                        CLocal.writeFile(CLocal.pathAppDownload, ID.substring(0, 4) + "_" + ID.substring(4, 6) + "_" + strings[1] + ".txt", new Gson().toJsonTree(listDocSo).getAsJsonArray().toString());
                    }
                }
            } catch (Exception e) {
                error = e.getMessage();
            }
            return error;
        }
    }

}


