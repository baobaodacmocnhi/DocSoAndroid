package vn.com.capnuoctanhoa.docsoandroid.Class;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.Vibrator;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.ls.LSException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import vn.com.capnuoctanhoa.docsoandroid.Service.ServiceThermalPrinter;


public class CLocal {
    //Android 1.5 	Cupcake 	27/4/2009
    //Android 1.6	Donut 	15/9/2009
    //Android 2.0 - 2.1 	Eclair 	26/9/2009 (phát hành lần đầu)
    //Android 2.2 - 2.2.3 	Froyo 	20/5/2010 (phát hành lần đầu)
    //Android 2.3 - 2.3.7 	Gingerbread 	6/12/2010 (phát hành lần đầu)
    //Android 3.0 - 3.2.6 	Honeycomb 	22/2/2011 (phát hành lần đầu)
    //Android 4.0 - 4.0.4 	Ice Cream Sandwich 	18/10/2011 (phát hành lần đầu)
    //Android 4.1 - 4.3.1 	Jelly Bean 	9/7/2012 (phát hành lần đầu)
    //Android 4.4 - 4.4.4 	KitKat 	31/10/2013 (phát hành lần đầu)
    //Android 5.0 - 5.1.1 	Lollipop 	12/11/2014 (phát hành lần đầu)
    //Android 6.0 - 6.0.1 	Marshmallow 	5/10/2015 (phát hành lần đầu)
    //Android 7.0 - 7.1.2 	Nougat 	22/8/2016 (phát hành lần đầu)
    //Android 8.0 - 8.1 	Oreo 	21/8/2017 (phát hành lần đầu)

    public static SharedPreferences sharedPreferencesre;
    public static String pathApp = "/data/data/vn.com.capnuoctanhoa.docsoandroid/files/";
    public static String pathAppDownload = pathApp + "Download";
    public static String pathAppPicture = pathApp + "Picture";
    public static String pathRoot = Environment.getExternalStorageDirectory() + "/TanHoa/";
    public static String pathFile = pathRoot + "File/";
    public static String pathPicture = pathRoot + "Picture/";
    public static String fileName_SharedPreferences = "my_configuration";
    public static SimpleDateFormat DateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static SimpleDateFormat DateFormatShort = new SimpleDateFormat("dd/MM/yyyy");
    public static JSONArray jsonDocSo, jsonMessage, jsonTo, jsonNhanVien, jsonNam, jsonCode, jsonViTriDHN, jsonHoaDonTon, jsonPhieuChuyen, jsonGiaNuoc, jsonKhongTinhPBVMT;
    public static String MaNV, HoTen, May, MaTo, DienThoai, ThermalPrinter, MethodPrinter, IDMobile;
    public static boolean Admin, Doi, ToTruong, SyncTrucTiep, LocDaDoc = false;
    public static ArrayList<CEntityParent> listDocSo, listDocSoView;
    public static ServiceThermalPrinter serviceThermalPrinter;
    public static int indexPosition = 0;
    public static int STT = 0;
    public static Bitmap imgCapture;
    public static final int _GiamTienNuoc = 10;
    public static ArrayList<String> lstTT0 = new ArrayList<String>(Arrays.asList("K", "N", "N1", "N2", "N3", "68", "Q"));
    public static ArrayList<String> lstTBTT = new ArrayList<String>(Arrays.asList("60", "61", "62", "63", "64", "66", "80", "F1", "F2", "F3", "F4"));
    public static ArrayList<String> lstBinhThuong = new ArrayList<String>(Arrays.asList("40", "41", "42", "54", "55", "56", "58", "5Q", "5", "M0", "M1", "M2", "M3", "X41", "X51"));

    public static void initialCLocal() {
        SharedPreferences.Editor editor = CLocal.sharedPreferencesre.edit();
        editor.putString("Username", "");
        editor.putString("Password", "");
        editor.putString("MaNV", "");
        editor.putString("HoTen", "");
        editor.putString("May", "");
        editor.putString("MaTo", "");
        editor.putString("DienThoai", "");
        editor.putString("jsonNam", "");
        editor.putString("jsonDocSo", "");
        editor.putString("jsonCode", "");
        editor.putString("jsonViTriDHN", "");
        editor.putString("jsonPhieuChuyen", "");
        editor.putString("jsonGiaNuoc", "");
        editor.putString("jsonKhongTinhPBVMT", "");
        editor.putString("jsonTo", "");
        editor.putString("jsonNhanVien", "");
        editor.putBoolean("Admin", false);
        editor.putBoolean("Doi", false);
        editor.putBoolean("ToTruong", false);
        editor.putBoolean("Login", false);
        editor.putLong("LoginDate", 0L);
        editor.commit();
        MaNV = HoTen = May = MaTo = DienThoai = IDMobile = ThermalPrinter = MethodPrinter = "";
        Admin = Doi = ToTruong = false;
        SyncTrucTiep = true;
        jsonDocSo = jsonMessage = jsonTo = jsonNhanVien = jsonNam = jsonCode = jsonViTriDHN = jsonHoaDonTon = jsonPhieuChuyen = jsonGiaNuoc = jsonKhongTinhPBVMT = null;
        listDocSo = listDocSoView = null;
    }

    public static String creatPathFile(Activity activity, String path, String filename, String filetype) {
        File filesDir = activity.getExternalFilesDir(path);
        File photoFile = null;
        try {
            photoFile = File.createTempFile(filename, "." + filetype, filesDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT < 21) {
            // Từ android 5.0 trở xuống. khi ta sử dụng FileProvider.getUriForFile() sẽ trả về ngoại lệ FileUriExposedException
            // Vì vậy mình sử dụng Uri.fromFile đề lấy ra uri cho file ảnh
            photoFile = new File(Environment.getExternalStorageDirectory() + "/TanHoa/", filename + "." + filetype);
        } else {
            // từ android 5.0 trở lên ta có thể sử dụng Uri.fromFile() và FileProvider.getUriForFile() để trả về uri file sau khi chụp.
            // Nhưng bắt buộc từ Android 7.0 trở lên ta phải sử dụng FileProvider.getUriForFile() để trả về uri cho file đó.
            FileProvider.getUriForFile(activity, "docso_file_provider", photoFile);
        }
        return photoFile.getAbsolutePath();
    }

    public static boolean checkNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public static boolean checkBluetoothAvaible() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null)
            return bluetoothAdapter.isEnabled();
        else
            return false;
    }

    public static boolean checkGPSAvaible(Activity activity) {
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            boolean flag = false;
            flag = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            return flag;
        } else
            return false;
    }

    public static boolean checkServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void openBluetoothSettings(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Yêu cầu bật Bluetooth")
                .setCancelable(false)
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_BLUETOOTH_SETTINGS);
                        activity.startActivity(intent);
                    }
                })
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
                        activity.finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        Button btnPositive = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btnNegative = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
        layoutParams.weight = 10;
        layoutParams.gravity = Gravity.CENTER;
        btnPositive.setLayoutParams(layoutParams);
        btnNegative.setLayoutParams(layoutParams);

    }

    public static void setOnBluetooth(Activity activity) {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(intent, 1);
    }

    public static void openGPSSettings(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Yêu cầu bật định vị GPS")
                .setCancelable(false)
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        activity.startActivity(intent);
                    }
                })
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
                        activity.finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        Button btnPositive = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btnNegative = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
        layoutParams.weight = 10;
        layoutParams.gravity = Gravity.CENTER;
        btnPositive.setLayoutParams(layoutParams);
        btnNegative.setLayoutParams(layoutParams);
    }

    public static void setOnGPS(Activity activity) {
        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", true);
        activity.sendBroadcast(intent);
    }

    public static void showPopupMessage1(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông Báo");
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
//        alertDialog.getWindow().getAttributes();

        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
    }

    public static void showPopupMessage(Context context, String message, String align) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông Báo");
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
//        alertDialog.getWindow().getAttributes();

        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        switch (align) {
            case "center":
                textView.setGravity(Gravity.CENTER);
                break;
            case "right":
                textView.setGravity(Gravity.RIGHT);
                break;
            default:
                textView.setGravity(Gravity.LEFT);
                break;
        }

        Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        LinearLayout parent = (LinearLayout) btnPositive.getParent();
        parent.setGravity(Gravity.CENTER_HORIZONTAL);
        View leftSpacer = parent.getChildAt(1);
        leftSpacer.setVisibility(View.GONE);
    }

    public static void showToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getIMEI(Activity activity) {
        try {
            CMarshMallowPermission cMarshMallowPermission = new CMarshMallowPermission(activity);
            String imei = null;

            if (cMarshMallowPermission.checkPermissionForPhoneState() == false) {
                cMarshMallowPermission.requestPermissionForPhoneState();
            }
            if (cMarshMallowPermission.checkPermissionForPhoneState() == false)
                return imei;

            TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
            if (cMarshMallowPermission.checkVersionQ()) {
                imei = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    if (telephonyManager != null) {
                        try {
                            imei = telephonyManager.getImei();
                        } catch (Exception e) {
                            e.printStackTrace();
                            imei = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
                        }
                    }
                } else {
                    cMarshMallowPermission.requestPermissionForPhoneState();
                }
            } else {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    if (telephonyManager != null) {
                        imei = telephonyManager.getDeviceId();
                    }
                } else {
                    cMarshMallowPermission.requestPermissionForPhoneState();
                }
            }
            return imei;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getPhoneNumber(Activity activity) {
        try {
            CMarshMallowPermission cMarshMallowPermission = new CMarshMallowPermission(activity);
            String imei = null;

            if (cMarshMallowPermission.checkPermissionForPhoneState() == false) {
                cMarshMallowPermission.requestPermissionForPhoneState();
            }
            if (cMarshMallowPermission.checkPermissionForPhoneState() == false)
                return imei;

            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
                if (telephonyManager != null) {
                    imei = telephonyManager.getLine1Number();
                }
            }
            return imei;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //Android ID thay đổi trên các điện thoại được root và khi điện thoại factory reset
    public static String getAndroidID(Activity activity) {
        try {
            return Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getWifiMac(Activity activity) {
        try {
            WifiManager wifiManager = (WifiManager) activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            return wifiManager.getConnectionInfo().getMacAddress();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getBluetoothMac(Activity activity) {
        try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            return bluetoothAdapter.getAddress();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //update list to Json
    public static void updateArrayListToJson() {
        SharedPreferences.Editor editor = CLocal.sharedPreferencesre.edit();
        if (CLocal.listDocSo != null)
            editor.putString("jsonDocSo", new Gson().toJsonTree(CLocal.listDocSo).getAsJsonArray().toString());
        editor.commit();
    }

    public static void updateJSON(JSONArray jsonArray, String ID, String Key, String Value) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("ID").equals(ID)) {
                    jsonObject.put(Key, Value);
                    //thiết lập ModifyDate để sort
                    jsonObject.put("ModifyDate", new Date().toString());
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //update giá trí child
    public static void updateValueChild(ArrayList<CEntityParent> lst, String NameUpdate, String ValueUpdate, String MaHD) {
        try {
            for (int i = 0; i < lst.size(); i++) {
                for (int j = 0; j < lst.get(i).getLstHoaDon().size(); j++)
                    if (lst.get(i).getLstHoaDon().get(j).getMaHD().equals(MaHD) == true) {
                        switch (NameUpdate) {
                            case "XoaHoaDon":
                                lst.get(i).getLstHoaDon().remove(j);
                                break;
                        }
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateTinhTrangParent(ArrayList<CEntityParent> lst, CEntityParent entityParentUpdate) {
        for (int i = 0; i < lst.size(); i++)
            if (lst.get(i).getID().equals(entityParentUpdate.getID())) {
                lst.get(i).setCEntityParent(entityParentUpdate);
            }
        //goi update lại json hệ thống
//        updateArrayListToJson();
    }

    public File createFile(Activity activity) {
        File filesDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = null;
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            file = File.createTempFile(timeStamp, ".jpg", filesDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static String getPathFromUri(final Context context, final Uri uri) {

//        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }

                    // TODO handle non-primary volumes
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };

                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {

                // Return the remote address
                if (isGooglePhotosUri(uri))
                    return uri.getLastPathSegment();

                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[]
            selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static void showImgThumb(Activity activity, Bitmap bitmap) {
        Dialog builder = new Dialog(activity);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(activity);
        imageView.setImageBitmap(bitmap);
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(1024, 1024));
        builder.show();
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    //region ConvertMoneyToWord

    public static HashMap<String, String> hm_tien = new HashMap<String, String>() {
        {
            put("0", "không");
            put("1", "một");
            put("2", "hai");
            put("3", "ba");
            put("4", "bốn");
            put("5", "năm");
            put("6", "sáu");
            put("7", "bảy");
            put("8", "tám");
            put("9", "chín");
        }
    };

    public static HashMap<String, String> hm_hanh = new HashMap<String, String>() {
        {
            put("1", "đồng");
            put("2", "mươi");
            put("3", "trăm");
            put("4", "nghìn");
            put("5", "mươi");
            put("6", "trăm");
            put("7", "triệu");
            put("8", "mươi");
            put("9", "trăm");
            put("10", "tỷ");
            put("11", "mươi");
            put("12", "trăm");
            put("13", "nghìn");
            put("14", "mươi");
            put("15", "trăm");

        }
    };

    public static String ConvertMoneyToWord(String money) {
        String kq = "";
        money = money.replace(".", "");
        String arr_temp[] = money.split(",");
        if (!TextUtils.isDigitsOnly(arr_temp[0])) {
            return "";
        }
        String m = arr_temp[0];
        int dem = m.length();
        String dau = "";
        int flag10 = 1;
        while (!m.equals("")) {
            if (m.length() <= 3 && m.length() > 1 && Long.parseLong(m) == 0) {

            } else {
                dau = m.substring(0, 1);
                if (dem % 3 == 1 && m.startsWith("1") && flag10 == 0) {
                    kq += "mốt ";
                    flag10 = 0;
                } else if (dem % 3 == 2 && m.startsWith("1")) {
                    kq += "mười ";
                    flag10 = 1;
                } else if (dem % 3 == 2 && m.startsWith("0") && m.length() >= 2 && !m.substring(1, 2).equals("0")) {
                    //System.out.println("a  "+m.substring(1, 2));
                    kq += "lẻ ";
                    flag10 = 1;
                } else {
                    if (!m.startsWith("0")) {
                        kq += hm_tien.get(dau) + " ";
                        flag10 = 0;
                    }
                }
                if (dem % 3 != 1 && m.startsWith("0") && m.length() > 1) {
                } else {
                    if (dem % 3 == 2 && (m.startsWith("1") || m.startsWith("0"))) {//mười
                    } else {
                        kq += hm_hanh.get(dem + "") + " ";
                    }
                }
            }
            m = m.substring(1);
            dem = m.length();
        }
        kq = kq.substring(0, kq.length() - 1);
        return kq;
    }

    //convert tiền thành chữ
    public static String formatMoney(String price, String symbol) {

        NumberFormat format = new DecimalFormat("#,##0.00");// #,##0.00 ¤ (¤:// Currency symbol)
        format.setCurrency(Currency.getInstance(Locale.US));//Or default locale

        price = (!TextUtils.isEmpty(price)) ? price : "0";
        price = price.trim();
        price = format.format(Double.parseDouble(price));
        price = price.replaceAll(",", "\\.");

        if (price.endsWith(".00")) {
            int centsIndex = price.lastIndexOf(".00");
            if (centsIndex != -1) {
                price = price.substring(0, centsIndex);
            }
        }
        price = String.format("%s " + symbol, price);
        return price;
    }

    //endregion

    public static String getTime() {
        Date dateCapNhat = new Date();
        return CLocal.DateFormat.format(dateCapNhat);
    }

    public static String convertTimestampToDate(long time) {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();//get your local time zone.
        DateFormat.setTimeZone(tz);
//        DateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        Date date = new Date(time); // *1000 is to convert seconds to milliseconds
        return DateFormat.format(date);
    }

    public static Calendar GetToDate(Calendar FromDate, int SoNgayCongThem) {
        while (SoNgayCongThem > 0) {
            if (FromDate.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
                FromDate.add(Calendar.DATE, 3);
            else if (FromDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                FromDate.add(Calendar.DATE, 2);
            else
                FromDate.add(Calendar.DATE, 1);
            SoNgayCongThem--;
        }
        return FromDate;
    }

    public static String convertStandardJSONString(String data_json) {
        data_json = data_json.replaceAll("\\\\r\\\\n", "");
        data_json = data_json.replace("\"{", "{");
        data_json = data_json.replace("}\",", "},");
        data_json = data_json.replace("}\"", "}");
        data_json = data_json.replace(":,", ":null,");
        return data_json;
    }

    public static Date convertStringToDate(String strDate) throws ParseException {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(strDate);
        } catch (ParseException e) {
            throw e;
        }
    }

    public static int getDaysDifference(Date fromDate, Date toDate) {
        if (fromDate == null || toDate == null)
            return 0;
        return (int) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
    }

    public static void vibrate(Activity activity) {
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);

        // Vibrate for 400 milliseconds
        v.vibrate(400);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(activity, notification);
        r.play();
    }

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private static ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            ServiceThermalPrinter.LocalBinder binder = (ServiceThermalPrinter.LocalBinder) service;
            CLocal.serviceThermalPrinter = binder.getService();
//            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
//            mBound = false;
        }
    };

    public static void runServiceThermalPrinter(Activity activity) {
        if (CLocal.ThermalPrinter != null && CLocal.ThermalPrinter != "")
            if (CLocal.checkBluetoothAvaible() == false) {
                CLocal.openBluetoothSettings(activity);
            } else if (CLocal.checkServiceRunning(activity, ServiceThermalPrinter.class) == false) {
                Intent intent2 = new Intent(activity, ServiceThermalPrinter.class);
//                intent2.putExtra("ThermalPrinter", CLocal.ThermalPrinter);
                activity.startService(intent2);
                activity.bindService(intent2, mConnection, Context.BIND_AUTO_CREATE);
            }
    }

    public static String readFile(String path, String filename) throws IOException {
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                return "";
            }
            File gpxfile = new File(dir, filename);
            FileReader reader = new FileReader(gpxfile);
            String result = "", strLine = "";
            BufferedReader br = new BufferedReader(reader);
            while ((strLine = br.readLine()) != null) {
                result = result + strLine + "\n";
            }
            br.close();
            reader.close();
            return result;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static boolean writeFile(String path, String filename, String value) throws IOException {
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdir();
            }
            if (value != null && value != "") {
                File gpxfile = new File(dir, filename);
                FileWriter writer = new FileWriter(gpxfile);
                writer.append(value);
                writer.flush();
                writer.close();
            }
            return true;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static boolean writeFile(String path, String filename, Bitmap value) throws IOException {
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File dir2 = new File(path, filename);
            if (dir2.exists())
                dir2.delete();
            if (value != null) {
                File file = new File(dir, filename);
                FileOutputStream fOut = new FileOutputStream(file);
                value.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.flush();
                fOut.close();
            }
            return true;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static boolean deleteFile(String path, String filename) throws IOException {
        try {
            if (filename == "") {
                File[] files = CLocal.getFilesInFolder(path);
                if (files != null && files.length > 0)
                    for (int i = 0; i < files.length; i++) {
                        if (files[i].exists())
                            files[i].delete();
                    }
                File dir = new File(path);
                if (dir.exists())
                    dir.delete();
            } else {
                File dir = new File(path, filename);
                if (dir.exists())
                    dir.delete();
            }
            return true;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static File[] getFilesInFolder(String path) throws IOException {
        try {
            File directory = new File(path);
            File[] files = directory.listFiles();
            return files;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static void ghiListToFileDocSo() throws IOException {
        try {
            SharedPreferences.Editor editor = CLocal.sharedPreferencesre.edit();
            String Nam = "", Ky = "", Dot = "";
            if (CLocal.listDocSo != null && CLocal.listDocSo.size() > 0) {
                Nam = CLocal.listDocSo.get(0).getNam();
                Ky = CLocal.listDocSo.get(0).getKy();
                Dot = CLocal.listDocSo.get(0).getDot();
                editor.putString("jsonDocSo", new Gson().toJsonTree(CLocal.listDocSo).getAsJsonArray().toString());
                writeFile(CLocal.pathAppDownload, Nam + "_" + Ky + "_" + Dot + ".txt", CLocal.sharedPreferencesre.getString("jsonDocSo", ""));
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static AlertDialog showDialog(Activity activity, String title, String msg, String positiveLabel, DialogInterface.OnClickListener positiveOnClick, String negativeLabel, DialogInterface.OnClickListener negativeOnClick, boolean isCancelAble) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setCancelable(isCancelAble);
        builder.setMessage(msg);
        builder.setPositiveButton(positiveLabel, positiveOnClick);
        builder.setNegativeButton(negativeLabel, negativeOnClick);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
        layoutParams.weight = 10;
        layoutParams.gravity = Gravity.CENTER;
        btnPositive.setLayoutParams(layoutParams);
        btnNegative.setLayoutParams(layoutParams);
        return alertDialog;
    }

    public static ArrayList<Integer> TinhTienNuoc(List<Integer> lstGiaNuoc, int GiaBieu, int TyLeSH, int TyLeSX, int TyLeDV, int TyLeHCSN, int TongDinhMuc, int DinhMucHN, int TieuThu) {
        try {
            int DinhMuc = TongDinhMuc - DinhMucHN, _SH = 0, _SX = 0, _DV = 0, _HCSN = 0;
            ///Table GiaNuoc được thiết lập theo bảng giá nước
            ///1. Đến 4m3/người/tháng
            ///2. Trên 4m3 đến 6m3/người/tháng
            ///3. Trên 6m3/người/tháng
            ///4. Đơn vị sản xuất
            ///5. Cơ quan, đoàn thể HCSN
            ///6. Đơn vị kinh doanh, dịch vụ
            ///7. Hộ nghèo, cận nghèo
            ///List bắt đầu từ phần tử thứ 0
            ArrayList<Integer> result = new ArrayList<>();
            int TongTien = 0, TongTienPhiBVMT = 0;
            switch (GiaBieu) {
                ///TƯ GIA
                case 10:
                    DinhMucHN = TongDinhMuc;
                    if (TieuThu <= DinhMucHN) {
                        TongTien = TieuThu * lstGiaNuoc.get(6);
                        TongTienPhiBVMT = TieuThu * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100);
                    } else if (TieuThu - DinhMucHN <= Math.round((double) DinhMucHN / 2)) {
                        TongTien = (DinhMucHN * lstGiaNuoc.get(6))
                                + ((TieuThu - DinhMucHN) * lstGiaNuoc.get(1));
                        TongTienPhiBVMT = TieuThu * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100)
                                + ((TieuThu - DinhMucHN) * (int) Math.round((double) lstGiaNuoc.get(1) * lstGiaNuoc.get(7) / 100));
                    } else {
                        TongTien = (DinhMucHN * lstGiaNuoc.get(6))
                                + ((int) Math.round((double) DinhMucHN / 2) * lstGiaNuoc.get(1))
                                + ((TieuThu - DinhMucHN - (int) Math.round((double) DinhMucHN / 2)) * lstGiaNuoc.get(2));
                        TongTienPhiBVMT = (DinhMucHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                + ((int) Math.round((double) DinhMucHN / 2) * (int) Math.round((double) lstGiaNuoc.get(1) * lstGiaNuoc.get(7) / 100))
                                + ((TieuThu - DinhMucHN - (int) Math.round((double) DinhMucHN / 2)) * (int) Math.round((double) lstGiaNuoc.get(2) * lstGiaNuoc.get(7) / 100));
                    }
                    break;
                case 11:
                case 21:///SH thuần túy
                    if (TieuThu <= DinhMucHN + DinhMuc) {
                        double TyLe = (double) DinhMucHN / (DinhMucHN + DinhMuc);
                        if (Double.isNaN(TyLe) == true)
                            TyLe = 0;
                        int TieuThuHN = 0, TieuThuDC = 0;
                        TieuThuHN = (int) Math.round(TieuThu * TyLe);
                        TieuThuDC = TieuThu - TieuThuHN;
                        TongTien = (TieuThuHN * lstGiaNuoc.get(6))
                                + (TieuThuDC * lstGiaNuoc.get(0));
                        TongTienPhiBVMT = (TieuThuHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                + (TieuThuDC * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100));
                    } else if (TieuThu - DinhMucHN - DinhMuc <= Math.round((double) (DinhMucHN + DinhMuc) / 2)) {
                        TongTien = (DinhMucHN * lstGiaNuoc.get(6))
                                + (DinhMuc * lstGiaNuoc.get(0))
                                + ((TieuThu - DinhMucHN - DinhMuc) * lstGiaNuoc.get(1));
                        TongTienPhiBVMT = (DinhMucHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                + (DinhMuc * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100))
                                + ((TieuThu - DinhMucHN - DinhMuc) * (int) Math.round((double) lstGiaNuoc.get(1) * lstGiaNuoc.get(7) / 100));
                    } else {
                        TongTien = (DinhMucHN * lstGiaNuoc.get(6))
                                + (DinhMuc * lstGiaNuoc.get(0))
                                + ((int) Math.round((double) (DinhMucHN + DinhMuc) / 2) * lstGiaNuoc.get(1))
                                + ((TieuThu - DinhMucHN - DinhMuc - (int) Math.round((double) (DinhMucHN + DinhMuc) / 2)) * lstGiaNuoc.get(2));
                        TongTienPhiBVMT = (DinhMucHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                + (DinhMuc * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100))
                                + ((int) Math.round((double) (DinhMucHN + DinhMuc) / 2) * (int) Math.round((double) lstGiaNuoc.get(1) * lstGiaNuoc.get(7) / 100))
                                + ((TieuThu - DinhMucHN - DinhMuc - (int) Math.round((double) (DinhMucHN + DinhMuc) / 2)) * (int) Math.round((double) lstGiaNuoc.get(2) * lstGiaNuoc.get(7) / 100));
                    }

                    break;
                case 12:
                case 22:
                case 32:
                case 42:///SX thuần túy
                    TongTien = TieuThu * lstGiaNuoc.get(3);
                    TongTienPhiBVMT = TieuThu * (int) Math.round((double) lstGiaNuoc.get(3) * lstGiaNuoc.get(7) / 100);
                    break;
                case 13:
                case 23:
                case 33:
                case 43:///DV thuần túy
                    TongTien = TieuThu * lstGiaNuoc.get(5);
                    TongTienPhiBVMT = TieuThu * (int) Math.round((double) lstGiaNuoc.get(5) * lstGiaNuoc.get(7) / 100);
                    break;
                case 14:
                case 24:///SH + SX
                    ///Nếu không có tỉ lệ
                    if (TyLeSH == 0 && TyLeSX == 0) {
                        if (TieuThu <= DinhMucHN + DinhMuc) {
                            double TyLe = (double) DinhMucHN / (DinhMucHN + DinhMuc);
                            if (Double.isNaN(TyLe) == true)
                                TyLe = 0;
                            int TieuThuHN = 0, TieuThuDC = 0;
                            TieuThuHN = (int) Math.round(TieuThu * TyLe);
                            TieuThuDC = TieuThu - TieuThuHN;
                            TongTien = (TieuThuHN * lstGiaNuoc.get(6))
                                    + (TieuThuDC * lstGiaNuoc.get(0));
                            TongTienPhiBVMT = (TieuThuHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                    + (TieuThuDC * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100));
                        } else {
                            TongTien = (DinhMucHN * lstGiaNuoc.get(6))
                                    + (DinhMuc * lstGiaNuoc.get(0))
                                    + ((TieuThu - DinhMucHN - DinhMuc) * lstGiaNuoc.get(3));
                            TongTienPhiBVMT = (DinhMucHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                    + (DinhMuc * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100))
                                    + ((TieuThu - DinhMucHN - DinhMuc) * (int) Math.round((double) lstGiaNuoc.get(3) * lstGiaNuoc.get(7) / 100));
                        }
                    } else
                    ///Nếu có tỉ lệ SH + SX
                    {
                        //int _SH = 0, _SX = 0;
                        if (TyLeSH != 0)
                            _SH = (int) Math.round((double) TieuThu * TyLeSH / 100);
                        _SX = TieuThu - _SH;
                        if (_SH <= DinhMucHN + DinhMuc) {
                            double TyLe = (double) DinhMucHN / (DinhMucHN + DinhMuc);
                            if (Double.isNaN(TyLe) == true)
                                TyLe = 0;
                            int TieuThuHN = 0, TieuThuDC = 0;
                            TieuThuHN = (int) Math.round(_SH * TyLe);
                            TieuThuDC = _SH - TieuThuHN;
                            TongTien = (TieuThuHN * lstGiaNuoc.get(6))
                                    + (TieuThuDC * lstGiaNuoc.get(0));
                            TongTienPhiBVMT = (TieuThuHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                    + (TieuThuDC * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100));
                        } else if (_SH - DinhMucHN - DinhMuc <= Math.round((double) (DinhMucHN + DinhMuc) / 2)) {
                            TongTien = (DinhMucHN * lstGiaNuoc.get(6))
                                    + (DinhMuc * lstGiaNuoc.get(0))
                                    + ((_SH - DinhMucHN - DinhMuc) * lstGiaNuoc.get(1));
                            TongTienPhiBVMT = (DinhMucHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                    + (DinhMuc * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100))
                                    + ((_SH - DinhMucHN - DinhMuc) * (int) Math.round((double) lstGiaNuoc.get(1) * lstGiaNuoc.get(7) / 100));
                        } else {
                            TongTien = (DinhMucHN * lstGiaNuoc.get(6))
                                    + (DinhMuc * lstGiaNuoc.get(0))
                                    + ((int) Math.round((double) (DinhMucHN + DinhMuc) / 2) * lstGiaNuoc.get(1))
                                    + ((_SH - DinhMucHN - DinhMuc - (int) Math.round((double) (DinhMucHN + DinhMuc) / 2)) * lstGiaNuoc.get(2));
                            TongTienPhiBVMT = (DinhMucHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                    + (DinhMuc * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100))
                                    + ((int) Math.round((double) (DinhMucHN + DinhMuc) / 2) * (int) Math.round((double) lstGiaNuoc.get(1) * lstGiaNuoc.get(7) / 100))
                                    + ((_SH - DinhMucHN - DinhMuc - (int) Math.round((double) (DinhMucHN + DinhMuc) / 2)) * (int) Math.round((double) lstGiaNuoc.get(2) * lstGiaNuoc.get(7) / 100));
                        }
                        TongTien += _SX * lstGiaNuoc.get(3);
                        TongTienPhiBVMT += _SX * (int) Math.round((double) lstGiaNuoc.get(3) * lstGiaNuoc.get(7) / 100);
                    }
                    break;
                case 15:
                case 25:///SH + DV
                    ///Nếu không có tỉ lệ
                    if (TyLeSH == 0 && TyLeDV == 0) {
                        if (TieuThu <= DinhMucHN + DinhMuc) {
                            //double TyLe = Math.Round((double)DinhMucHN / (DinhMucHN + DinhMuc), 2);
                            double TyLe = (double) DinhMucHN / (DinhMucHN + DinhMuc);
                            if (Double.isNaN(TyLe) == true)
                                TyLe = 0;
                            int TieuThuHN = 0, TieuThuDC = 0;
                            TieuThuHN = (int) Math.round(TieuThu * TyLe);
                            TieuThuDC = TieuThu - TieuThuHN;
                            TongTien = (TieuThuHN * lstGiaNuoc.get(6))
                                    + (TieuThuDC * lstGiaNuoc.get(0));
                            TongTienPhiBVMT = (TieuThuHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                    + (TieuThuDC * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100));
                        } else {
                            TongTien = (DinhMucHN * lstGiaNuoc.get(6))
                                    + (DinhMuc * lstGiaNuoc.get(0))
                                    + ((TieuThu - DinhMucHN - DinhMuc) * lstGiaNuoc.get(5));
                            TongTienPhiBVMT = (DinhMucHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                    + (DinhMuc * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100))
                                    + ((TieuThu - DinhMucHN - DinhMuc) * (int) Math.round((double) lstGiaNuoc.get(5) * lstGiaNuoc.get(7) / 100));
                        }
                    } else
                    ///Nếu có tỉ lệ SH + DV
                    {
                        //int _SH = 0, _DV = 0;
                        if (TyLeSH != 0)
                            _SH = (int) Math.round((double) TieuThu * TyLeSH / 100);
                        _DV = TieuThu - _SH;
                        if (_SH <= DinhMucHN + DinhMuc) {
                            double TyLe = (double) DinhMucHN / (DinhMucHN + DinhMuc);
                            if (Double.isNaN(TyLe) == true)
                                TyLe = 0;
                            int TieuThuHN = 0, TieuThuDC = 0;
                            TieuThuHN = (int) Math.round(_SH * TyLe);
                            TieuThuDC = _SH - TieuThuHN;
                            TongTien = (TieuThuHN * lstGiaNuoc.get(6))
                                    + (TieuThuDC * lstGiaNuoc.get(0));
                            TongTienPhiBVMT = (TieuThuHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                    + (TieuThuDC * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100));
                        } else if (_SH - DinhMuc <= Math.round((double) (DinhMucHN + DinhMuc) / 2)) {
                            TongTien = (DinhMucHN * lstGiaNuoc.get(6))
                                    + (DinhMuc * lstGiaNuoc.get(0))
                                    + ((_SH - DinhMucHN - DinhMuc) * lstGiaNuoc.get(1));
                            TongTienPhiBVMT = (DinhMucHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                    + (DinhMuc * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100))
                                    + ((_SH - DinhMucHN - DinhMuc) * (int) Math.round((double) lstGiaNuoc.get(1) * lstGiaNuoc.get(7) / 100));
                        } else {
                            TongTien = (DinhMucHN * lstGiaNuoc.get(6))
                                    + (DinhMuc * lstGiaNuoc.get(0))
                                    + ((int) Math.round((double) (DinhMucHN + DinhMuc) / 2) * lstGiaNuoc.get(1))
                                    + ((_SH - DinhMucHN - DinhMuc - (int) Math.round((double) (DinhMucHN + DinhMuc) / 2)) * lstGiaNuoc.get(2));
                            TongTienPhiBVMT = (DinhMucHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                    + (DinhMuc * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100))
                                    + ((int) Math.round((double) (DinhMucHN + DinhMuc) / 2) * (int) Math.round((double) lstGiaNuoc.get(1) * lstGiaNuoc.get(7) / 100))
                                    + ((_SH - DinhMucHN - DinhMuc - (int) Math.round((double) (DinhMucHN + DinhMuc) / 2)) * (int) Math.round((double) lstGiaNuoc.get(2) * lstGiaNuoc.get(7) / 100));
                        }
                        TongTien += _DV * lstGiaNuoc.get(5);
                        TongTienPhiBVMT += _DV * (int) Math.round((double) lstGiaNuoc.get(5) * lstGiaNuoc.get(7) / 100);
                    }
                    break;
                case 16:
                case 26:///SH + SX + DV
                    ///Nếu chỉ có tỉ lệ SX + DV mà không có tỉ lệ SH, không xét Định Mức
                    if (TyLeSX != 0 && TyLeDV != 0 && TyLeSH == 0) {
                        if (TyLeSX != 0)
                            _SX = (int) Math.round((double) TieuThu * TyLeSX / 100);
                        _DV = TieuThu - _SX;
                        TongTien = (_SX * lstGiaNuoc.get(3))
                                + (_DV * lstGiaNuoc.get(5));
                        TongTienPhiBVMT = (_SX * (int) Math.round((double) lstGiaNuoc.get(3) * lstGiaNuoc.get(7) / 100))
                                + (_DV * (int) Math.round((double) lstGiaNuoc.get(5) * lstGiaNuoc.get(7) / 100));
                    } else
                    ///Nếu có đủ 3 tỉ lệ SH + SX + DV
                    {
                        //int _SH = 0, _SX = 0, _DV = 0;
                        if (TyLeSH != 0)
                            _SH = (int) Math.round((double) TieuThu * TyLeSH / 100);
                        if (TyLeSX != 0)
                            _SX = (int) Math.round((double) TieuThu * TyLeSX / 100);
                        _DV = TieuThu - _SH - _SX;
                        if (_SH <= DinhMucHN + DinhMuc) {
                            double TyLe = (double) DinhMucHN / (DinhMucHN + DinhMuc);
                            if (Double.isNaN(TyLe) == true)
                                TyLe = 0;
                            int TieuThuHN = 0, TieuThuDC = 0;
                            TieuThuHN = (int) Math.round(_SH * TyLe);
                            TieuThuDC = _SH - TieuThuHN;
                            TongTien = (TieuThuHN * lstGiaNuoc.get(6))
                                    + (TieuThuDC * lstGiaNuoc.get(0));
                            TongTienPhiBVMT = (TieuThuHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                    + (TieuThuDC * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100));
                        } else if (_SH - DinhMucHN - DinhMuc <= Math.round((double) (DinhMucHN + DinhMuc) / 2)) {
                            TongTien = (DinhMucHN * lstGiaNuoc.get(6))
                                    + (DinhMuc * lstGiaNuoc.get(0))
                                    + ((_SH - DinhMuc) * lstGiaNuoc.get(1));
                            TongTienPhiBVMT = (DinhMucHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                    + (DinhMuc * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100))
                                    + ((_SH - DinhMuc) * (int) Math.round((double) lstGiaNuoc.get(1) * lstGiaNuoc.get(7) / 100));
                        } else {
                            TongTien = (DinhMucHN * lstGiaNuoc.get(6))
                                    + (DinhMuc * lstGiaNuoc.get(0))
                                    + ((int) Math.round((double) (DinhMucHN + DinhMuc) / 2) * lstGiaNuoc.get(1))
                                    + ((_SH - DinhMucHN - DinhMuc - (int) Math.round((double) (DinhMucHN + DinhMuc) / 2)) * lstGiaNuoc.get(2));
                            TongTienPhiBVMT = (DinhMucHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                    + (DinhMuc * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100))
                                    + ((int) Math.round((double) (DinhMucHN + DinhMuc) / 2) * (int) Math.round((double) lstGiaNuoc.get(1) * lstGiaNuoc.get(7) / 100))
                                    + ((_SH - DinhMucHN - DinhMuc - (int) Math.round((double) (DinhMucHN + DinhMuc) / 2)) * (int) Math.round((double) lstGiaNuoc.get(2) * lstGiaNuoc.get(7) / 100));
                        }
                        TongTien += (_SX * lstGiaNuoc.get(3))
                                + (_DV * lstGiaNuoc.get(5));
                        TongTienPhiBVMT += (_SX * (int) Math.round((double) lstGiaNuoc.get(3) * lstGiaNuoc.get(7) / 100))
                                + (_DV * (int) Math.round((double) lstGiaNuoc.get(5) * lstGiaNuoc.get(7) / 100));
                    }
                    break;
                case 17:
                case 27:///SH ĐB
                    TongTien = TieuThu * lstGiaNuoc.get(0);
                    TongTienPhiBVMT = TieuThu * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100);
                    break;
                case 18:
                case 28:
                case 38:///SH + HCSN
                    ///Nếu không có tỉ lệ
                    if (TyLeSH == 0 && TyLeHCSN == 0) {
                        if (TieuThu <= DinhMucHN + DinhMuc) {
                            double TyLe = (double) DinhMucHN / (DinhMucHN + DinhMuc);
                            if (Double.isNaN(TyLe) == true)
                                TyLe = 0;
                            int TieuThuHN = 0, TieuThuDC = 0;
                            TieuThuHN = (int) Math.round(TieuThu * TyLe);
                            TieuThuDC = TieuThu - TieuThuHN;
                            TongTien = (TieuThuHN * lstGiaNuoc.get(6))
                                    + (TieuThuDC * lstGiaNuoc.get(0));
                            TongTienPhiBVMT = (TieuThuHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                    + (TieuThuDC * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100));
                        } else {
                            TongTien = (DinhMucHN * lstGiaNuoc.get(6))
                                    + (DinhMuc * lstGiaNuoc.get(0))
                                    + ((TieuThu - DinhMuc) * lstGiaNuoc.get(4));
                            TongTienPhiBVMT = (DinhMucHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                    + (DinhMuc * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100))
                                    + ((TieuThu - DinhMuc) * (int) Math.round((double) lstGiaNuoc.get(5) * lstGiaNuoc.get(7) / 100));
                        }
                    } else
                    ///Nếu có tỉ lệ SH + HCSN
                    {
                        //int _SH = 0, _HCSN = 0;
                        if (TyLeSH != 0)
                            _SH = (int) Math.round((double) TieuThu * TyLeSH / 100);
                        _HCSN = TieuThu - _SH;
                        if (_SH <= DinhMucHN + DinhMuc) {
                            double TyLe = (double) DinhMucHN / (DinhMucHN + DinhMuc);
                            if (Double.isNaN(TyLe) == true)
                                TyLe = 0;
                            int TieuThuHN = 0, TieuThuDC = 0;
                            TieuThuHN = (int) Math.round(_SH * TyLe);
                            TieuThuDC = _SH - TieuThuHN;
                            TongTien = (TieuThuHN * lstGiaNuoc.get(6))
                                    + (TieuThuDC * lstGiaNuoc.get(0));
                            TongTienPhiBVMT = (TieuThuHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                    + (TieuThuDC * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100));
                        } else if (_SH - DinhMucHN - DinhMuc <= Math.round((double) (DinhMucHN + DinhMuc) / 2)) {
                            TongTien = (DinhMucHN * lstGiaNuoc.get(6))
                                    + (DinhMuc * lstGiaNuoc.get(0))
                                    + ((_SH - DinhMucHN - DinhMuc) * lstGiaNuoc.get(1));
                            TongTienPhiBVMT = (DinhMucHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                    + (DinhMuc * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100))
                                    + ((_SH - DinhMucHN - DinhMuc) * (int) Math.round((double) lstGiaNuoc.get(1) * lstGiaNuoc.get(7) / 100));
                        } else {
                            TongTien = (DinhMucHN * lstGiaNuoc.get(6))
                                    + (DinhMuc * lstGiaNuoc.get(0))
                                    + ((int) Math.round((double) (DinhMucHN + DinhMuc) / 2) * lstGiaNuoc.get(1))
                                    + ((_SH - DinhMucHN - DinhMuc - (int) Math.round((double) (DinhMucHN + DinhMuc) / 2)) * lstGiaNuoc.get(2));
                            TongTienPhiBVMT = (DinhMucHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                    + (DinhMuc * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100))
                                    + ((int) Math.round((double) (DinhMucHN + DinhMuc) / 2) * (int) Math.round((double) lstGiaNuoc.get(1) * lstGiaNuoc.get(7) / 100))
                                    + ((_SH - DinhMucHN - DinhMuc - (int) Math.round((double) (DinhMucHN + DinhMuc) / 2)) * (int) Math.round((double) lstGiaNuoc.get(2) * lstGiaNuoc.get(7) / 100));
                        }
                        TongTien += _HCSN * lstGiaNuoc.get(4);
                        TongTienPhiBVMT += _HCSN * (int) Math.round((double) lstGiaNuoc.get(4) * lstGiaNuoc.get(7) / 100);
                    }
                    break;
                case 19:
                case 29:
                case 39:///SH + HCSN + SX + DV
                    //int _SH = 0, _HCSN = 0, _SX = 0, _DV = 0;
                    if (TyLeSH != 0)
                        _SH = (int) Math.round((double) TieuThu * TyLeSH / 100);
                    if (TyLeHCSN != 0)
                        _HCSN = (int) Math.round((double) TieuThu * TyLeHCSN / 100);
                    if (TyLeSX != 0)
                        _SX = (int) Math.round((double) TieuThu * TyLeSX / 100);
                    _DV = TieuThu - _SH - _HCSN - _SX;
                    if (_SH <= DinhMucHN + DinhMuc) {
                        double TyLe = (double) DinhMucHN / (DinhMucHN + DinhMuc);
                        if (Double.isNaN(TyLe) == true)
                            TyLe = 0;
                        int TieuThuHN = 0, TieuThuDC = 0;
                        TieuThuHN = (int) Math.round(_SH * TyLe);
                        TieuThuDC = _SH - TieuThuHN;
                        TongTien = (TieuThuHN * lstGiaNuoc.get(6))
                                + (TieuThuDC * lstGiaNuoc.get(0));
                        TongTienPhiBVMT = (TieuThuHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                + (TieuThuDC * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100));
                    } else if (_SH - DinhMucHN - DinhMuc <= Math.round((double) (DinhMucHN + DinhMuc) / 2)) {
                        TongTien = (DinhMucHN * lstGiaNuoc.get(6))
                                + (DinhMuc * lstGiaNuoc.get(0))
                                + ((_SH - DinhMucHN - DinhMuc) * lstGiaNuoc.get(1));
                        TongTienPhiBVMT = (DinhMucHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                + (DinhMuc * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100))
                                + ((_SH - DinhMucHN - DinhMuc) * (int) Math.round((double) lstGiaNuoc.get(1) * lstGiaNuoc.get(7) / 100));
                    } else {
                        TongTien = (DinhMucHN * lstGiaNuoc.get(6))
                                + (DinhMuc * lstGiaNuoc.get(0))
                                + ((int) Math.round((double) (DinhMucHN + DinhMuc) / 2) * lstGiaNuoc.get(1))
                                + ((_SH - DinhMucHN - DinhMuc - (int) Math.round((double) (DinhMucHN + DinhMuc) / 2)) * lstGiaNuoc.get(2));
                        TongTienPhiBVMT = (DinhMucHN * (int) Math.round((double) lstGiaNuoc.get(6) * lstGiaNuoc.get(7) / 100))
                                + (DinhMuc * (int) Math.round((double) lstGiaNuoc.get(0) * lstGiaNuoc.get(7) / 100))
                                + ((int) Math.round((double) (DinhMucHN + DinhMuc) / 2) * (int) Math.round((double) lstGiaNuoc.get(1) * lstGiaNuoc.get(7) / 100))
                                + ((_SH - DinhMucHN - DinhMuc - (int) Math.round((double) (DinhMucHN + DinhMuc) / 2)) * (int) Math.round((double) lstGiaNuoc.get(2) * lstGiaNuoc.get(7) / 100));
                    }
                    TongTien += (_HCSN * lstGiaNuoc.get(4))
                            + (_SX * lstGiaNuoc.get(3))
                            + (_DV * lstGiaNuoc.get(5));
                    TongTienPhiBVMT += (_HCSN * (int) Math.round((double) lstGiaNuoc.get(4) * lstGiaNuoc.get(7) / 100))
                            + (_SX * (int) Math.round((double) lstGiaNuoc.get(3) * lstGiaNuoc.get(7) / 100))
                            + (_DV * (int) Math.round((double) lstGiaNuoc.get(5) * lstGiaNuoc.get(7) / 100));
                    break;
                ///CƠ QUAN
                case 31:///SHVM thuần túy
                    TongTien = TieuThu * lstGiaNuoc.get(4);
                    TongTienPhiBVMT = TieuThu * (int) Math.round((double) lstGiaNuoc.get(4) * lstGiaNuoc.get(7) / 100);
                    break;
                case 34:///HCSN + SX
                    ///Nếu không có tỉ lệ
                    if (TyLeHCSN == 0 && TyLeSX == 0) {
                        TongTien = TieuThu * lstGiaNuoc.get(3);
                        TongTienPhiBVMT = TieuThu * (int) Math.round((double) lstGiaNuoc.get(3) * lstGiaNuoc.get(7) / 100);
                    } else
                    ///Nếu có tỉ lệ
                    {
                        //int _HCSN = 0, _SX = 0;
                        if (TyLeHCSN != 0)
                            _HCSN = (int) Math.round((double) TieuThu * TyLeHCSN / 100);
                        _SX = TieuThu - _HCSN;

                        TongTien = (_HCSN * lstGiaNuoc.get(4))
                                + (_SX * lstGiaNuoc.get(3));
                        TongTienPhiBVMT = (_HCSN * (int) Math.round((double) lstGiaNuoc.get(4) * lstGiaNuoc.get(7) / 100))
                                + (_SX * (int) Math.round((double) lstGiaNuoc.get(3) * lstGiaNuoc.get(7) / 100));
                    }
                    break;
                case 35:///HCSN + DV
                    ///Nếu không có tỉ lệ
                    if (TyLeHCSN == 0 && TyLeDV == 0) {
                        TongTien = TieuThu * lstGiaNuoc.get(5);
                        TongTienPhiBVMT = TieuThu * (int) Math.round((double) lstGiaNuoc.get(5) * lstGiaNuoc.get(7) / 100);
                    } else
                    ///Nếu có tỉ lệ
                    {
                        //int _HCSN = 0, _DV = 0;
                        if (TyLeHCSN != 0)
                            _HCSN = (int) Math.round((double) TieuThu * TyLeHCSN / 100);
                        _DV = TieuThu - _HCSN;
                        TongTien = (_HCSN * lstGiaNuoc.get(4))
                                + (_DV * lstGiaNuoc.get(5));
                        TongTienPhiBVMT = (_HCSN * (int) Math.round((double) lstGiaNuoc.get(4) * lstGiaNuoc.get(7) / 100))
                                + (_DV * (int) Math.round((double) lstGiaNuoc.get(5) * lstGiaNuoc.get(7) / 100));
                    }
                    break;
                case 36:///HCSN + SX + DV
                {
                    if (TyLeHCSN != 0)
                        _HCSN = (int) Math.round((double) TieuThu * TyLeHCSN / 100);
                    if (TyLeSX != 0)
                        _SX = (int) Math.round((double) TieuThu * TyLeSX / 100);
                    _DV = TieuThu - _HCSN - _SX;
                    TongTien = (_HCSN * lstGiaNuoc.get(4))
                            + (_SX * lstGiaNuoc.get(3))
                            + (_DV * lstGiaNuoc.get(5));
                    TongTienPhiBVMT = (_HCSN * (int) Math.round((double) lstGiaNuoc.get(4) * lstGiaNuoc.get(7) / 100))
                            + (_SX * (int) Math.round((double) lstGiaNuoc.get(3) * lstGiaNuoc.get(7) / 100))
                            + (_DV * (int) Math.round((double) lstGiaNuoc.get(5) * lstGiaNuoc.get(7) / 100));
                }
                break;
                ///NƯỚC NGOÀI
                case 41:///SHVM thuần túy
                    TongTien = TieuThu * lstGiaNuoc.get(2);
                    TongTienPhiBVMT = TieuThu * (int) Math.round((double) lstGiaNuoc.get(2) * lstGiaNuoc.get(7) / 100);
                    break;
                case 44:///SH + SX
                {
                    if (TyLeSH != 0)
                        _SH = (int) Math.round((double) TieuThu * TyLeSH / 100);
                    _SX = TieuThu - _SH;
                    TongTien = (_SH * lstGiaNuoc.get(2))
                            + (_SX * lstGiaNuoc.get(3));
                    TongTienPhiBVMT = (_SH * (int) Math.round((double) lstGiaNuoc.get(2) * lstGiaNuoc.get(7) / 100))
                            + (_SX * (int) Math.round((double) lstGiaNuoc.get(3) * lstGiaNuoc.get(7) / 100));
                }
                break;
                case 45:///SH + DV
                    if (TyLeSH != 0)
                        _SH = (int) Math.round((double) TieuThu * TyLeSH / 100);
                    _DV = TieuThu - _SH;
                    TongTien = (_SH * lstGiaNuoc.get(2))
                            + (_DV * lstGiaNuoc.get(5));
                    TongTienPhiBVMT = (_SH * (int) Math.round((double) lstGiaNuoc.get(2) * lstGiaNuoc.get(7) / 100))
                            + (_DV * (int) Math.round((double) lstGiaNuoc.get(5) * lstGiaNuoc.get(7) / 100));
                    break;
                case 46:///SH + SX + DV
                {
                    if (TyLeSH != 0)
                        _SH = (int) Math.round((double) TieuThu * TyLeSH / 100);
                    if (TyLeSX != 0)
                        _SX = (int) Math.round((double) TieuThu * TyLeSX / 100);
                    _DV = TieuThu - _SH - _SX;
                    TongTien = (_SH * lstGiaNuoc.get(2))
                            + (_SX * lstGiaNuoc.get(3))
                            + (_DV * lstGiaNuoc.get(5));
                    TongTienPhiBVMT = (_SH * (int) Math.round((double) lstGiaNuoc.get(2) * lstGiaNuoc.get(7) / 100))
                            + (_SX * (int) Math.round((double) lstGiaNuoc.get(3) * lstGiaNuoc.get(7) / 100))
                            + (_DV * (int) Math.round((double) lstGiaNuoc.get(5) * lstGiaNuoc.get(7) / 100));
                }
                break;
                ///BÁN SỈ
                case 51:///sỉ khu dân cư - Giảm % tiền nước cho ban quản lý chung cư
                    if (TieuThu <= DinhMucHN + DinhMuc) {
                        double TyLe = (double) DinhMucHN / (DinhMucHN + DinhMuc);
                        if (Double.isNaN(TyLe) == true)
                            TyLe = 0;
                        int TieuThuHN = 0, TieuThuDC = 0;
                        TieuThuHN = (int) Math.round(TieuThu * TyLe);
                        TieuThuDC = TieuThu - TieuThuHN;
                        TongTien = (TieuThuHN * (lstGiaNuoc.get(6) - lstGiaNuoc.get(6) * _GiamTienNuoc / 100))
                                + (TieuThuDC * (lstGiaNuoc.get(0) - lstGiaNuoc.get(0) * _GiamTienNuoc / 100));
                        TongTienPhiBVMT = (TieuThuHN * (int) Math.round((double) (lstGiaNuoc.get(6) - lstGiaNuoc.get(6) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100))
                                + (TieuThuDC * (int) Math.round((double) (lstGiaNuoc.get(0) - lstGiaNuoc.get(0) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100));
                    } else if (TieuThu - DinhMucHN - DinhMuc <= Math.round((double) (DinhMucHN + DinhMuc) / 2)) {
                        TongTien = (DinhMucHN * (lstGiaNuoc.get(6) - lstGiaNuoc.get(6) * _GiamTienNuoc / 100))
                                + (DinhMuc * (lstGiaNuoc.get(0) - lstGiaNuoc.get(0) * _GiamTienNuoc / 100))
                                + ((TieuThu - DinhMuc) * (lstGiaNuoc.get(1) - lstGiaNuoc.get(1) * _GiamTienNuoc / 100));
                        TongTienPhiBVMT = (DinhMucHN * (int) Math.round((double) (lstGiaNuoc.get(6) - lstGiaNuoc.get(6) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100))
                                + (DinhMuc * (int) Math.round((double) (lstGiaNuoc.get(0) - lstGiaNuoc.get(0) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100))
                                + ((TieuThu - DinhMuc) * (int) Math.round((double) (lstGiaNuoc.get(1) - lstGiaNuoc.get(1) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100));
                    } else {
                        TongTien = (DinhMucHN * (lstGiaNuoc.get(6) - lstGiaNuoc.get(6) * _GiamTienNuoc / 100))
                                + (DinhMuc * (lstGiaNuoc.get(0) - lstGiaNuoc.get(0) * _GiamTienNuoc / 100))
                                + ((int) Math.round((double) (DinhMucHN + DinhMuc) / 2) * (lstGiaNuoc.get(1) - lstGiaNuoc.get(1) * _GiamTienNuoc / 100))
                                + ((TieuThu - DinhMucHN - DinhMuc - (int) Math.round((double) (DinhMucHN + DinhMuc) / 2)) * (lstGiaNuoc.get(2) - lstGiaNuoc.get(2) * _GiamTienNuoc / 100));
                        TongTienPhiBVMT = (DinhMucHN * (int) Math.round((double) (lstGiaNuoc.get(6) - lstGiaNuoc.get(6) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100))
                                + (DinhMuc * (int) Math.round((double) (lstGiaNuoc.get(0) - lstGiaNuoc.get(0) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100))
                                + ((int) Math.round((double) (DinhMucHN + DinhMuc) / 2) * (int) Math.round((double) (lstGiaNuoc.get(1) - lstGiaNuoc.get(1) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100))
                                + ((TieuThu - DinhMucHN - DinhMuc - (int) Math.round((double) (DinhMucHN + DinhMuc) / 2)) * (int) Math.round((double) (lstGiaNuoc.get(2) - lstGiaNuoc.get(2) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100));
                    }
                    break;
                case 52:///sỉ khu công nghiệp
                    TongTien = TieuThu * (lstGiaNuoc.get(3) - lstGiaNuoc.get(3) * _GiamTienNuoc / 100);
                    TongTienPhiBVMT = TieuThu * (int) Math.round((double) (lstGiaNuoc.get(3) - lstGiaNuoc.get(3) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100);
                    break;
                case 53:///sỉ KD - TM
                    TongTien = TieuThu * (lstGiaNuoc.get(5) - lstGiaNuoc.get(5) * _GiamTienNuoc / 100);
                    TongTienPhiBVMT = TieuThu * (int) Math.round((double) (lstGiaNuoc.get(5) - lstGiaNuoc.get(5) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100);
                    break;
                case 54:///sỉ HCSN
                    TongTien = TieuThu * (lstGiaNuoc.get(4) - lstGiaNuoc.get(4) * _GiamTienNuoc / 100);
                    TongTienPhiBVMT = TieuThu * (int) Math.round((double) (lstGiaNuoc.get(4) - lstGiaNuoc.get(4) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100);
                    break;
                case 58:
                    if (TyLeSH != 0)
                        _SH = (int) Math.round((double) TieuThu * TyLeSH / 100);
                    if (TyLeHCSN != 0)
                        _HCSN = (int) Math.round((double) TieuThu * TyLeHCSN / 100);
                    if (TyLeSX != 0)
                        _SX = (int) Math.round((double) TieuThu * TyLeSX / 100);
                    _DV = TieuThu - _SH - _HCSN - _SX;
                    TongTien = (_SH * (lstGiaNuoc.get(0) - lstGiaNuoc.get(0) * _GiamTienNuoc / 100))
                            + (_HCSN * (lstGiaNuoc.get(4) - lstGiaNuoc.get(4) * _GiamTienNuoc / 100))
                            + (_SX * (lstGiaNuoc.get(3) - lstGiaNuoc.get(3) * _GiamTienNuoc / 100))
                            + (_DV * (lstGiaNuoc.get(5) - lstGiaNuoc.get(5) * _GiamTienNuoc / 100));
                    TongTienPhiBVMT = (_SH * (int) Math.round((double) (lstGiaNuoc.get(0) - lstGiaNuoc.get(0) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100))
                            + (_HCSN * (int) Math.round((double) (lstGiaNuoc.get(4) - lstGiaNuoc.get(4) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100))
                            + (_SX * (int) Math.round((double) (lstGiaNuoc.get(3) - lstGiaNuoc.get(3) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100))
                            + (_DV * (int) Math.round((double) (lstGiaNuoc.get(5) - lstGiaNuoc.get(5) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100));
                    break;
                case 59:///sỉ phức tạp
                    if (TyLeSH != 0)
                        _SH = (int) Math.round((double) TieuThu * TyLeSH / 100);
                    if (TyLeHCSN != 0)
                        _HCSN = (int) Math.round((double) TieuThu * TyLeHCSN / 100);
                    if (TyLeSX != 0)
                        _SX = (int) Math.round((double) TieuThu * TyLeSX / 100);
                    _DV = TieuThu - _SH - _HCSN - _SX;
                    if (_SH <= DinhMucHN + DinhMuc) {
                        double TyLe = (double) DinhMucHN / (DinhMucHN + DinhMuc);
                        if (Double.isNaN(TyLe) == true)
                            TyLe = 0;
                        int TieuThuHN = 0, TieuThuDC = 0;
                        TieuThuHN = (int) Math.round(_SH * TyLe);
                        TieuThuDC = _SH - TieuThuHN;
                        TongTien = (TieuThuHN * (lstGiaNuoc.get(6) - lstGiaNuoc.get(6) * _GiamTienNuoc / 100))
                                + (TieuThuDC * (lstGiaNuoc.get(0) - lstGiaNuoc.get(0) * _GiamTienNuoc / 100));
                        TongTienPhiBVMT = (TieuThuHN * (int) Math.round((double) (lstGiaNuoc.get(6) - lstGiaNuoc.get(6) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100))
                                + (TieuThuDC * (int) Math.round((double) (lstGiaNuoc.get(0) - lstGiaNuoc.get(0) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100));
                    } else if (_SH - DinhMucHN - DinhMuc <= Math.round((double) (DinhMucHN + DinhMuc) / 2)) {
                        TongTien = (DinhMucHN * (lstGiaNuoc.get(6) - lstGiaNuoc.get(6) * _GiamTienNuoc / 100))
                                + (DinhMuc * (lstGiaNuoc.get(0) - lstGiaNuoc.get(0) * _GiamTienNuoc / 100))
                                + ((_SH - DinhMucHN - DinhMuc) * (lstGiaNuoc.get(1) - lstGiaNuoc.get(1) * _GiamTienNuoc / 100));
                        TongTienPhiBVMT = (DinhMucHN * (int) Math.round((double) (lstGiaNuoc.get(6) - lstGiaNuoc.get(6) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100))
                                + (DinhMuc * (int) Math.round((double) (lstGiaNuoc.get(0) - lstGiaNuoc.get(0) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100))
                                + ((_SH - DinhMucHN - DinhMuc) * (int) Math.round((double) (lstGiaNuoc.get(1) - lstGiaNuoc.get(1) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100));
                    } else {
                        TongTien = (DinhMucHN * (lstGiaNuoc.get(6) - lstGiaNuoc.get(6) * _GiamTienNuoc / 100))
                                + (DinhMuc * (lstGiaNuoc.get(0) - lstGiaNuoc.get(0) * _GiamTienNuoc / 100))
                                + ((int) Math.round((double) (DinhMucHN + DinhMuc) / 2) * (lstGiaNuoc.get(1) - lstGiaNuoc.get(1) * _GiamTienNuoc / 100))
                                + ((_SH - DinhMucHN - DinhMuc - (int) Math.round((double) (DinhMucHN + DinhMuc) / 2)) * (lstGiaNuoc.get(2) - lstGiaNuoc.get(2) * _GiamTienNuoc / 100));
                        TongTienPhiBVMT = (DinhMucHN * (int) Math.round((double) (lstGiaNuoc.get(6) - lstGiaNuoc.get(6) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100))
                                + (DinhMuc * (int) Math.round((double) (lstGiaNuoc.get(0) - lstGiaNuoc.get(0) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100))
                                + ((int) Math.round((double) (DinhMucHN + DinhMuc) / 2) * (int) Math.round((double) (lstGiaNuoc.get(1) - lstGiaNuoc.get(1) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100))
                                + ((_SH - DinhMucHN - DinhMuc - (int) Math.round((double) (DinhMucHN + DinhMuc) / 2)) * (int) Math.round((double) (lstGiaNuoc.get(2) - lstGiaNuoc.get(2) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100));
                    }
                    TongTien += (_HCSN * (lstGiaNuoc.get(4) - lstGiaNuoc.get(4) * _GiamTienNuoc / 100))
                            + (_SX * (lstGiaNuoc.get(3) - lstGiaNuoc.get(3) * _GiamTienNuoc / 100))
                            + (_DV * (lstGiaNuoc.get(5) - lstGiaNuoc.get(5) * _GiamTienNuoc / 100));
                    TongTienPhiBVMT += (_HCSN * (int) Math.round((double) (lstGiaNuoc.get(4) - lstGiaNuoc.get(4) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100))
                            + (_SX * (int) Math.round((double) (lstGiaNuoc.get(3) - lstGiaNuoc.get(3) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100))
                            + (_DV * (int) Math.round((double) (lstGiaNuoc.get(5) - lstGiaNuoc.get(5) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100));
                    break;
                case 68:///SH giá sỉ - KD giá lẻ
                    if (TyLeSH != 0)
                        _SH = (int) Math.round((double) TieuThu * TyLeSH / 100);
                    _DV = TieuThu - _SH;
                    if (_SH <= DinhMucHN + DinhMuc) {
                        double TyLe = (double) DinhMucHN / (DinhMucHN + DinhMuc);
                        if (Double.isNaN(TyLe) == true)
                            TyLe = 0;
                        int TieuThuHN = 0, TieuThuDC = 0;
                        TieuThuHN = (int) Math.round(_SH * TyLe);
                        TieuThuDC = _SH - TieuThuHN;
                        TongTien = (TieuThuHN * (lstGiaNuoc.get(6) - lstGiaNuoc.get(6) * _GiamTienNuoc / 100))
                                + (TieuThuDC * (lstGiaNuoc.get(0) - lstGiaNuoc.get(0) * _GiamTienNuoc / 100));
                        TongTienPhiBVMT = (TieuThuHN * (int) Math.round((double) (lstGiaNuoc.get(6) - lstGiaNuoc.get(6) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100))
                                + (TieuThuDC * (int) Math.round((double) (lstGiaNuoc.get(0) - lstGiaNuoc.get(0) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100));
                    } else if (_SH - DinhMucHN - DinhMuc <= Math.round((double) (DinhMucHN + DinhMuc) / 2)) {
                        TongTien = (DinhMucHN * (lstGiaNuoc.get(6) - lstGiaNuoc.get(6) * _GiamTienNuoc / 100))
                                + (DinhMuc * (lstGiaNuoc.get(0) - lstGiaNuoc.get(0) * _GiamTienNuoc / 100))
                                + ((_SH - DinhMucHN - DinhMuc) * (lstGiaNuoc.get(1) - lstGiaNuoc.get(1) * _GiamTienNuoc / 100));
                        TongTienPhiBVMT = (DinhMucHN * (int) Math.round((double) (lstGiaNuoc.get(6) - lstGiaNuoc.get(6) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100))
                                + (DinhMuc * (int) Math.round((double) (lstGiaNuoc.get(0) - lstGiaNuoc.get(0) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100))
                                + ((_SH - DinhMucHN - DinhMuc) * (int) Math.round((double) (lstGiaNuoc.get(1) - lstGiaNuoc.get(1) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100));
                    } else {
                        TongTien = (DinhMucHN * (lstGiaNuoc.get(6) - lstGiaNuoc.get(6) * _GiamTienNuoc / 100))
                                + (DinhMuc * (lstGiaNuoc.get(0) - lstGiaNuoc.get(0) * _GiamTienNuoc / 100))
                                + ((int) Math.round((double) (DinhMucHN + DinhMuc) / 2) * (lstGiaNuoc.get(1) - lstGiaNuoc.get(1) * _GiamTienNuoc / 100))
                                + ((_SH - DinhMucHN - DinhMuc - (int) Math.round((double) (DinhMucHN + DinhMuc) / 2)) * (lstGiaNuoc.get(2) - lstGiaNuoc.get(2) * _GiamTienNuoc / 100));
                        TongTienPhiBVMT = (DinhMucHN * (int) Math.round((double) (lstGiaNuoc.get(6) - lstGiaNuoc.get(6) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100))
                                + (DinhMuc * (int) Math.round((double) (lstGiaNuoc.get(0) - lstGiaNuoc.get(0) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100))
                                + ((int) Math.round((double) (DinhMucHN + DinhMuc) / 2) * (int) Math.round((double) (lstGiaNuoc.get(1) - lstGiaNuoc.get(1) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100))
                                + ((_SH - DinhMucHN - DinhMuc - (int) Math.round((double) (DinhMucHN + DinhMuc) / 2)) * (int) Math.round((double) (lstGiaNuoc.get(2) - lstGiaNuoc.get(2) * _GiamTienNuoc / 100) * lstGiaNuoc.get(7) / 100));
                    }
                    TongTien += _DV * lstGiaNuoc.get(5);
                    TongTienPhiBVMT += _DV * (int) Math.round((double) lstGiaNuoc.get(5) * lstGiaNuoc.get(7) / 100);
                    break;
                default:
                    TongTien = 0;
                    TongTienPhiBVMT = 0;
                    break;
            }
            result.add(TongTien);
            result.add(TongTienPhiBVMT);
            return result;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static ArrayList<Integer> TinhTienNuoc(String DanhBo, int Ky, int Nam, String TuNgay, String DenNgay, int GiaBieu, int TyLeSH, int TyLeSX, int TyLeDV, int TyLeHCSN, int TongDinhMuc, int DinhMucHN, int TieuThu) throws JSONException, ParseException {
//        DataTable dtGiaNuoc = getDS_GiaNuoc();
//        //check giảm giá
//        if (KhongApGiaGiam == false)
//            checkExists_GiamGiaNuoc(Nam, Ky, GiaBieu, ref dtGiaNuoc);
        try {
            ArrayList<Integer> result = new ArrayList<>();
            int index = -1;
            int TienNuocNamCu = 0, TienNuocNamMoi = 0, PhiBVMTNamCu = 0, PhiBVMTNamMoi = 0, TienNuoc = 0, ThueGTGT = 0, TDVTN = 0, ThueTDVTN = 0;
            Date dateTuNgay = convertStringToDate(TuNgay), dateDenNgay = convertStringToDate(DenNgay);
            Calendar calTuNgay = Calendar.getInstance();
            calTuNgay.setTime(dateTuNgay);
            Calendar calDenNgay = Calendar.getInstance();
            calDenNgay.setTime(dateDenNgay);
            Calendar calNgayChot = Calendar.getInstance();
            calNgayChot.set(2019, 11, 15);
            for (int i = 0; i < CLocal.jsonGiaNuoc.length(); i++)
                if (dateTuNgay.compareTo(convertStringToDate(CLocal.jsonGiaNuoc.getJSONObject(i).getString("NgayTangGia"))) < 0 && convertStringToDate(CLocal.jsonGiaNuoc.getJSONObject(i).getString("NgayTangGia")).compareTo(dateDenNgay) < 0) {
                    index = i;
                } else if (dateTuNgay.compareTo(convertStringToDate(CLocal.jsonGiaNuoc.getJSONObject(i).getString("NgayTangGia"))) >= 0) {
                    index = i;
                }
            if (index != -1) {
                if (dateDenNgay.compareTo(calNgayChot.getTime()) < 0) {
                    ArrayList<Integer> lstGiaNuoc = new ArrayList<Integer>();
                    lstGiaNuoc.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index).getString("SHTM")));
                    lstGiaNuoc.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index).getString("SHVM1")));
                    lstGiaNuoc.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index).getString("SHVM2")));
                    lstGiaNuoc.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index).getString("SX")));
                    lstGiaNuoc.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index).getString("HCSN")));
                    lstGiaNuoc.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index).getString("KDDV")));
                    lstGiaNuoc.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index).getString("SHN")));
                    lstGiaNuoc.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index).getString("PhiBVMT")));
                    ArrayList<Integer> TienNuocNamCus = TinhTienNuoc(lstGiaNuoc, GiaBieu, TyLeSH, TyLeSX, TyLeDV, TyLeHCSN, TongDinhMuc, 0, TieuThu);
                    TienNuocNamCu = TienNuocNamCus.get(0);
                    PhiBVMTNamCu = TienNuocNamCus.get(1);
                } else if (dateTuNgay.compareTo(convertStringToDate(CLocal.jsonGiaNuoc.getJSONObject(index).getString("NgayTangGia"))) < 0 && convertStringToDate(CLocal.jsonGiaNuoc.getJSONObject(index).getString("NgayTangGia")).compareTo(dateDenNgay) < 0) {
                    int TongSoNgay = (int) (getDaysDifference(dateTuNgay, dateDenNgay));
                    int SoNgayCu = (int) (getDaysDifference(convertStringToDate(CLocal.jsonGiaNuoc.getJSONObject(index).getString("NgayTangGia")), dateTuNgay));
                    int TieuThuCu = (int) Math.round((double) TieuThu * SoNgayCu / TongSoNgay);
                    int TieuThuMoi = TieuThu - TieuThuCu;
                    int TongDinhMucCu = (int) Math.round((double) TongDinhMuc * SoNgayCu / TongSoNgay);
                    int TongDinhMucMoi = TongDinhMuc - TongDinhMucCu;
                    int DinhMucHN_Cu = 0, DinhMucHN_Moi = 0;
                    if (dateTuNgay.compareTo(calNgayChot.getTime()) > 0)
                        if (TongDinhMucCu != 0 && DinhMucHN != 0 && TongDinhMuc != 0)
                            DinhMucHN_Cu = (int) Math.round((double) TongDinhMucCu * DinhMucHN / TongDinhMuc);
                    if (TongDinhMucMoi != 0 && DinhMucHN != 0 && TongDinhMuc != 0)
                        DinhMucHN_Moi = (int) Math.round((double) TongDinhMucMoi * DinhMucHN / TongDinhMuc);
                    ArrayList<Integer> lstGiaNuocCu = new ArrayList<Integer>();
                    lstGiaNuocCu.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index - 1).getString("SHTM")));
                    lstGiaNuocCu.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index - 1).getString("SHVM1")));
                    lstGiaNuocCu.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index - 1).getString("SHVM2")));
                    lstGiaNuocCu.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index - 1).getString("SX")));
                    lstGiaNuocCu.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index - 1).getString("HCSN")));
                    lstGiaNuocCu.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index - 1).getString("KDDV")));
                    lstGiaNuocCu.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index - 1).getString("SHN")));
                    lstGiaNuocCu.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index - 1).getString("PhiBVMT")));
                    ArrayList<Integer> lstGiaNuocMoi = new ArrayList<Integer>();
                    lstGiaNuocMoi.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index).getString("SHTM")));
                    lstGiaNuocMoi.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index).getString("SHVM1")));
                    lstGiaNuocMoi.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index).getString("SHVM2")));
                    lstGiaNuocMoi.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index).getString("SX")));
                    lstGiaNuocMoi.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index).getString("HCSN")));
                    lstGiaNuocMoi.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index).getString("KDDV")));
                    lstGiaNuocMoi.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index).getString("SHN")));
                    lstGiaNuocMoi.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index).getString("PhiBVMT")));
                    //lần đầu áp dụng giá biểu 10, tổng áp giá mới luôn
                    if (dateTuNgay.compareTo(new Date(2019, 11, 15)) < 0 && new Date(2019, 11, 15).compareTo(dateDenNgay) < 0 && GiaBieu == 10) {
                        ArrayList<Integer> TienNuocNamCus = TinhTienNuoc(lstGiaNuocCu, GiaBieu, TyLeSH, TyLeSX, TyLeDV, TyLeHCSN, TongDinhMucCu, DinhMucHN_Cu, TieuThuCu);
                        TienNuocNamCu = TienNuocNamCus.get(0);
                        PhiBVMTNamCu = TienNuocNamCus.get(1);
                    } else {
                        ArrayList<Integer> TienNuocNamCus = TinhTienNuoc(lstGiaNuocCu, GiaBieu, TyLeSH, TyLeSX, TyLeDV, TyLeHCSN, TongDinhMucCu, DinhMucHN_Cu, TieuThuCu);
                        TienNuocNamCu = TienNuocNamCus.get(0);
                        PhiBVMTNamCu = TienNuocNamCus.get(1);
                    }
                    ArrayList<Integer> TienNuocNamMois = TinhTienNuoc(lstGiaNuocMoi, GiaBieu, TyLeSH, TyLeSX, TyLeDV, TyLeHCSN, TongDinhMucMoi, DinhMucHN_Moi, TieuThuMoi);
                    TienNuocNamMoi = TienNuocNamMois.get(0);
                    PhiBVMTNamMoi = TienNuocNamMois.get(1);
                } else {
                    ArrayList<Integer> lstGiaNuoc = new ArrayList<Integer>();
                    lstGiaNuoc.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index).getString("SHTM")));
                    lstGiaNuoc.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index).getString("SHVM1")));
                    lstGiaNuoc.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index).getString("SHVM2")));
                    lstGiaNuoc.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index).getString("SX")));
                    lstGiaNuoc.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index).getString("HCSN")));
                    lstGiaNuoc.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index).getString("KDDV")));
                    lstGiaNuoc.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index).getString("SHN")));
                    lstGiaNuoc.add(Integer.parseInt(CLocal.jsonGiaNuoc.getJSONObject(index).getString("PhiBVMT")));
                    ArrayList<Integer> TienNuocNamCus = TinhTienNuoc(lstGiaNuoc, GiaBieu, TyLeSH, TyLeSX, TyLeDV, TyLeHCSN, TongDinhMuc, DinhMucHN, TieuThu);
                    TienNuocNamCu = TienNuocNamCus.get(0);
                    PhiBVMTNamCu = TienNuocNamCus.get(1);
                }
                for (int i = 0; i < CLocal.jsonKhongTinhPBVMT.length(); i++)
                    if (CLocal.jsonKhongTinhPBVMT.getJSONObject(i).getString("DanhBo").equals(DanhBo) == true) {
                        PhiBVMTNamCu = PhiBVMTNamMoi = 0;
                    }
                TienNuoc = TienNuocNamCu + TienNuocNamMoi;
                ThueGTGT = (int) Math.round((double) (TienNuocNamCu + TienNuocNamMoi) * 5 / 100);
                TDVTN = PhiBVMTNamCu + PhiBVMTNamMoi;
                //Từ 2022 Phí BVMT -> Tiền Dịch Vụ Thoát Nước
                if ((calTuNgay.get(Calendar.YEAR) < 2021) || (calTuNgay.get(Calendar.YEAR) == 2021 && calDenNgay.get(Calendar.YEAR) == 2021)) {
                    ThueTDVTN = 0;
                } else if (calTuNgay.get(Calendar.YEAR) == 2021 && dateDenNgay.getYear() == 2022) {
                    ThueTDVTN = (int) Math.round((double) (PhiBVMTNamMoi) * 10 / 100);
                } else if (calTuNgay.get(Calendar.YEAR) >= 2022) {
                    ThueTDVTN = (int) Math.round((double) (PhiBVMTNamCu + PhiBVMTNamMoi) * 10 / 100);
                }
                result.add(TienNuoc);
                result.add(ThueGTGT);
                result.add(TDVTN);
                result.add(ThueTDVTN);
                result.add(TienNuoc + ThueGTGT + TDVTN + ThueTDVTN);
            }
            return result;
        } catch (Exception ex) {
            throw ex;
        }
    }


}
