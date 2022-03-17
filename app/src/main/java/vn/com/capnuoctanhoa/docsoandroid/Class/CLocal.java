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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import vn.com.capnuoctanhoa.docsoandroid.MainActivity;
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
    public static JSONArray jsonDocSo, jsonMessage, jsonTo, jsonNhanVien, jsonNam, jsonCode, jsonViTriDHN, jsonHoaDonTon;
    public static String MaNV, HoTen, May, MaTo, DienThoai, ThermalPrinter, MethodPrinter, IDMobile;
    public static boolean Admin, Doi, ToTruong, SyncTrucTiep;
    public static ArrayList<CEntityParent> listDocSo, listDocSoView;
    public static ServiceThermalPrinter serviceThermalPrinter;
    public static int indexPosition = 0;

    public static void initialCLocal() {
        SharedPreferences.Editor editor = CLocal.sharedPreferencesre.edit();
        editor.putString("Username", "");
        editor.putString("Password", "");
        editor.putString("MaNV", "");
        editor.putString("HoTen", "");
        editor.putString("May", "");
        editor.putString("MaTo", "");
        editor.putString("DienThoai", "");
        editor.putString("jsonDocSo", "");
        editor.putString("jsonCode", "");
        editor.putString("jsonViTriDHN", "");
        editor.putString("jsonNam", "");
        editor.putString("jsonTo", "");
        editor.putString("jsonNhanVien", "");
        editor.putBoolean("Admin", false);
        editor.putBoolean("Doi", false);
        editor.putBoolean("ToTruong", false);
        editor.putBoolean("Login", false);
        editor.putLong("LoginDate", 0L);
        editor.putString("ThermalPrinter", "");
        editor.putString("MethodPrinter", "Intermec");
        editor.putBoolean("SyncTrucTiep", true);
        editor.commit();
        ThermalPrinter = "";
        MaNV = HoTen = May = MaTo = DienThoai = IDMobile = "";
        Admin = Doi = ToTruong = false;
        SyncTrucTiep = true;
        jsonDocSo = jsonMessage = jsonTo = jsonNhanVien = jsonNam = jsonCode = jsonViTriDHN = jsonHoaDonTon = null;
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

    public static String getTime() {
        Date dateCapNhat = new Date();
        return CLocal.DateFormat.format(dateCapNhat);
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

    public static Bitmap imageOreintationValidator(Bitmap bitmap, String path) {
        ExifInterface ei;
        try {
            ei = new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    bitmap = rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    bitmap = rotateImage(bitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    bitmap = rotateImage(bitmap, 270);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {

        Bitmap bitmap = null;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            bitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                    matrix, true);
        } catch (OutOfMemoryError err) {
            err.printStackTrace();
        }
        return bitmap;
    }

    public static String convertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        String str = Base64.encodeToString(stream.toByteArray(), Base64.NO_WRAP);
        return str;
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
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(1000, 1000));
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

    //region Thermal Printer

    // Created by imrankst1221@gmail.com
    // UNICODE 0x23 = #
    public static final byte[] UNICODE_TEXT = new byte[]{0x23, 0x23, 0x23,
            0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23,
            0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23,
            0x23, 0x23, 0x23};

    private static String hexStr = "0123456789ABCDEF";
    private static String[] binaryArray = {"0000", "0001", "0010", "0011",
            "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011",
            "1100", "1101", "1110", "1111"};

    public static byte[] decodeBitmap(Bitmap bmp) {
        int bmpWidth = bmp.getWidth();
        int bmpHeight = bmp.getHeight();

        List<String> list = new ArrayList<String>(); //binaryString list
        StringBuffer sb;


        int bitLen = bmpWidth / 8;
        int zeroCount = bmpWidth % 8;

        String zeroStr = "";
        if (zeroCount > 0) {
            bitLen = bmpWidth / 8 + 1;
            for (int i = 0; i < (8 - zeroCount); i++) {
                zeroStr = zeroStr + "0";
            }
        }

        for (int i = 0; i < bmpHeight; i++) {
            sb = new StringBuffer();
            for (int j = 0; j < bmpWidth; j++) {
                int color = bmp.getPixel(j, i);

                int r = (color >> 16) & 0xff;
                int g = (color >> 8) & 0xff;
                int b = color & 0xff;

                // if color close to white，bit='0', else bit='1'
                if (r > 160 && g > 160 && b > 160)
                    sb.append("0");
                else
                    sb.append("1");
            }
            if (zeroCount > 0) {
                sb.append(zeroStr);
            }
            list.add(sb.toString());
        }

        List<String> bmpHexList = binaryListToHexStringList(list);
        String commandHexString = "1D763000";
        String widthHexString = Integer
                .toHexString(bmpWidth % 8 == 0 ? bmpWidth / 8
                        : (bmpWidth / 8 + 1));
        if (widthHexString.length() > 2) {
//            Log.e("decodeBitmap error", " width is too large");
            return null;
        } else if (widthHexString.length() == 1) {
            widthHexString = "0" + widthHexString;
        }
        widthHexString = widthHexString + "00";

        String heightHexString = Integer.toHexString(bmpHeight);
        if (heightHexString.length() > 2) {
//            Log.e("decodeBitmap error", " height is too large");
            return null;
        } else if (heightHexString.length() == 1) {
            heightHexString = "0" + heightHexString;
        }
        heightHexString = heightHexString + "00";

        List<String> commandList = new ArrayList<String>();
        commandList.add(commandHexString + widthHexString + heightHexString);
        commandList.addAll(bmpHexList);

        return hexList2Byte(commandList);
    }

    public static List<String> binaryListToHexStringList(List<String> list) {
        List<String> hexList = new ArrayList<String>();
        for (String binaryStr : list) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < binaryStr.length(); i += 8) {
                String str = binaryStr.substring(i, i + 8);

                String hexString = myBinaryStrToHexString(str);
                sb.append(hexString);
            }
            hexList.add(sb.toString());
        }
        return hexList;

    }

    public static String myBinaryStrToHexString(String binaryStr) {
        String hex = "";
        String f4 = binaryStr.substring(0, 4);
        String b4 = binaryStr.substring(4, 8);
        for (int i = 0; i < binaryArray.length; i++) {
            if (f4.equals(binaryArray[i]))
                hex += hexStr.substring(i, i + 1);
        }
        for (int i = 0; i < binaryArray.length; i++) {
            if (b4.equals(binaryArray[i]))
                hex += hexStr.substring(i, i + 1);
        }

        return hex;
    }

    public static byte[] hexList2Byte(List<String> list) {
        List<byte[]> commandList = new ArrayList<byte[]>();

        for (String hexStr : list) {
            commandList.add(hexStringToBytes(hexStr));
        }
        byte[] bytes = sysCopy(commandList);
        return bytes;
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    public static byte[] sysCopy(List<byte[]> srcArrays) {
        int len = 0;
        for (byte[] srcArray : srcArrays) {
            len += srcArray.length;
        }
        byte[] destArray = new byte[len];
        int destLen = 0;
        for (byte[] srcArray : srcArrays) {
            System.arraycopy(srcArray, 0, destArray, destLen, srcArray.length);
            destLen += srcArray.length;
        }
        return destArray;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    //endregion

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

    //endregion

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
        return data_json;
    }

    public static void vibrate(Activity activity) {
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);

        // Vibrate for 400 milliseconds
        v.vibrate(400);
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

    public static AlertDialog showDialog(Activity activity, String title, String msg, String positiveLabel, DialogInterface.OnClickListener positiveOnClick, String negativeLabel, DialogInterface.OnClickListener negativeOnClick, boolean isCancelAble) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setCancelable(isCancelAble);
        builder.setMessage(msg);
        builder.setPositiveButton(positiveLabel, positiveOnClick);
        builder.setNegativeButton(negativeLabel, negativeOnClick);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }

}
