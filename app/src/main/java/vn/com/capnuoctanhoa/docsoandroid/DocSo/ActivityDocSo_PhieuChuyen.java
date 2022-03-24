package vn.com.capnuoctanhoa.docsoandroid.DocSo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vn.com.capnuoctanhoa.docsoandroid.Class.CEntityParent;
import vn.com.capnuoctanhoa.docsoandroid.Class.CLocal;
import vn.com.capnuoctanhoa.docsoandroid.Class.CMarshMallowPermission;
import vn.com.capnuoctanhoa.docsoandroid.Class.CWebservice;
import vn.com.capnuoctanhoa.docsoandroid.Class.CustomAdapterRecyclerViewDienThoai;
import vn.com.capnuoctanhoa.docsoandroid.R;

public class ActivityDocSo_PhieuChuyen extends AppCompatActivity {
    private Spinner spnPhieuChuyen;
    private EditText edtGhiChu;
    private Button btnCapNhat;
    private ImageView imgThumb;
    private ImageButton ibtnChupHinh;
    private RecyclerView recyclerView;
    private CWebservice ws;
    private ArrayList<String> spnName_PhieuChuyen;
    private CEntityParent entityParent;
    private JSONArray jsonDSDonTu;
    private CustomAdapterRecyclerViewDienThoai customAdapterRecyclerViewDienThoai;
    private String imgPath;
    private Bitmap imgCapture;
    private CMarshMallowPermission cMarshMallowPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_so_phieu_chuyen);

        spnPhieuChuyen = (Spinner) findViewById(R.id.spnPhieuChuyen);
        edtGhiChu = (EditText) findViewById(R.id.edtGhiChu);
        btnCapNhat = (Button) findViewById(R.id.btnCapNhat);
        imgThumb = (ImageView) findViewById(R.id.imgThumb);
        ibtnChupHinh = (ImageButton) findViewById(R.id.ibtnChupHinh);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        ws = new CWebservice();
        cMarshMallowPermission = new CMarshMallowPermission(this);
        try {
            if (CLocal.jsonPhieuChuyen != null && CLocal.jsonPhieuChuyen.length() > 0) {
                spnName_PhieuChuyen = new ArrayList<>();
                for (int i = 0; i < CLocal.jsonPhieuChuyen.length(); i++) {
                    JSONObject jsonObject = CLocal.jsonPhieuChuyen.getJSONObject(i);
                    spnName_PhieuChuyen.add(jsonObject.getString("Name").replace("null", ""));
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, spnName_PhieuChuyen);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnPhieuChuyen.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (CLocal.STT > -1) {
                if (CLocal.listDocSoView != null && CLocal.listDocSoView.size() > 0) {
                    ArrayList<String> arrayList = new ArrayList<String>();
                    if (CLocal.STT >= 0 && CLocal.STT < CLocal.listDocSoView.size()) {
                        entityParent = CLocal.listDocSoView.get(CLocal.STT);
                    }
                }
                MyAsyncTaskDisapper myAsyncTaskDisapper = new MyAsyncTaskDisapper();
                myAsyncTaskDisapper.execute(new String[]{"getDSDonTu"});
            }
        } catch (Exception ex) {
            CLocal.showToastMessage(ActivityDocSo_PhieuChuyen.this, ex.getMessage());
        }

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imgCapture!=null) {
                    MyAsyncTask myAsyncTask = new MyAsyncTask();
                    myAsyncTask.execute();
                }
                else
                    CLocal.showToastMessage(ActivityDocSo_PhieuChuyen.this, "Thiếu dữ liệu Hình ảnh");
            }
        });

        ibtnChupHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (cMarshMallowPermission.checkPermissionForExternalStorage() == false) {
//                        cMarshMallowPermission.requestPermissionForExternalStorage();
//                    }
//                    if (cMarshMallowPermission.checkPermissionForExternalStorage() == false)
//                        return;
//                }
//                imgCapture = null;
//                Uri imgUri = createImageUri();
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if (intent.resolveActivity(ActivityDocSo_PhieuChuyen.this.getPackageManager()) != null) {
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri); // put uri file khi mà mình muốn lưu ảnh sau khi chụp như thế nào  ?
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                    activityResultLauncher_ChupHinh.launch(intent);
//                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (cMarshMallowPermission.checkPermissionForExternalStorage() == false) {
                        cMarshMallowPermission.requestPermissionForExternalStorage();
                    }
                    if (cMarshMallowPermission.checkPermissionForExternalStorage() == false)
                        return;
                }
                imgCapture = null;
                if (Build.VERSION.SDK_INT <= 19) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    activityResultLauncher_ChonHinh.launch(intent);
                } else if (Build.VERSION.SDK_INT > 19) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher_ChonHinh.launch(intent);
                }
            }
        });


    }

    private void fillDSDonTu() {
        if (jsonDSDonTu != null)
            try {
                ArrayList<CEntityParent> lstDienThoai = new ArrayList<>();
                for (int k = 0; k < jsonDSDonTu.length(); k++) {
                    JSONObject jsonObject = jsonDSDonTu.getJSONObject(k);
                    CEntityParent entityParent = new CEntityParent();
                    entityParent.setAnXoa(true);
                    entityParent.setDienThoai(jsonObject.getString("CreateDate").replace("null", "") + "-" + jsonObject.getString("NoiDung").replace("null", "") + "-" + jsonObject.getString("TinhTrang").replace("null", ""));
                    lstDienThoai.add(entityParent);
                }
                customAdapterRecyclerViewDienThoai = new CustomAdapterRecyclerViewDienThoai(ActivityDocSo_PhieuChuyen.this, lstDienThoai);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(customAdapterRecyclerViewDienThoai);
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
            }
    }

    ActivityResultLauncher<Intent> activityResultLauncher_ChupHinh = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (imgPath != null && imgPath != "") {
                            Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
                            bitmap = CLocal.imageOreintationValidator(bitmap, imgPath);
                            imgCapture = bitmap;
                        }
                    }
                    if (imgCapture != null) {
                        imgCapture = Bitmap.createScaledBitmap(imgCapture, 1024, 1024, false);
                        imgThumb.setImageBitmap(imgCapture);
                    }
                }
            });

    ActivityResultLauncher<Intent> activityResultLauncher_ChonHinh = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null && result.getData().getData() != null) {
                        Uri uri = result.getData().getData();
                        String strPath = CLocal.getPathFromUri(ActivityDocSo_PhieuChuyen.this, uri);
                        Bitmap bitmap = BitmapFactory.decodeFile(strPath);
                        bitmap = CLocal.imageOreintationValidator(bitmap, strPath);
                        imgCapture = bitmap;
                    }
                    if (imgCapture != null) {
                        imgCapture = Bitmap.createScaledBitmap(imgCapture, 1024, 1024, false);
                        imgThumb.setImageBitmap(imgCapture);
                    }
                }
            });

    public Uri createImageUri() {
        try {
            File filesDir = this.getExternalFilesDir(CLocal.pathPicture);
            File photoFile = null;
            try {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                photoFile = File.createTempFile(timeStamp, ".jpg", filesDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Uri uri;

            if (Build.VERSION.SDK_INT < 21) {
                // Từ android 5.0 trở xuống. khi ta sử dụng FileProvider.getUriForFile() sẽ trả về ngoại lệ FileUriExposedException
                // Vì vậy mình sử dụng Uri.fromFile đề lấy ra uri cho file ảnh
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                photoFile = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "" + timeStamp + ".jpg");
                uri = Uri.fromFile(photoFile);
            } else {
                // từ android 5.0 trở lên ta có thể sử dụng Uri.fromFile() và FileProvider.getUriForFile() để trả về uri file sau khi chụp.
                // Nhưng bắt buộc từ Android 7.0 trở lên ta phải sử dụng FileProvider.getUriForFile() để trả về uri cho file đó.
                Uri photoURI = FileProvider.getUriForFile(this, "docso_file_provider", photoFile);
                uri = photoURI;
            }
            imgPath = photoFile.getAbsolutePath();
            return uri;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public class MyAsyncTask extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        JSONObject jsonObject = null;
        String imgString;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ActivityDocSo_PhieuChuyen.this);
            progressDialog.setTitle("Thông Báo");
            progressDialog.setMessage("Đang xử lý...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String result = "";
                imgString = CLocal.convertBitmapToString(imgCapture);
                result = ws.ghi_DonTu(entityParent.getDanhBo().replace(" ", ""), spnPhieuChuyen.getSelectedItem().toString(), edtGhiChu.getText().toString(), CLocal.MaNV);
                if (result.equals("") == false)
                    jsonObject = new JSONObject(result);
                if (jsonObject != null && Boolean.parseBoolean(jsonObject.getString("success").replace("null", "")) == true) {
                    return "THÀNH CÔNG";
                } else
                    return "THẤT BẠI";
            } catch (Exception e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            try {
                if (jsonObject != null && Boolean.parseBoolean(jsonObject.getString("success").replace("null", "")) == true) {
                    if (jsonObject.getString("message").replace("null", "").equals("") == false) {
                        JSONObject jsonObjectC = new JSONObject(jsonObject.getString("message").replace("null", ""));
                        MyAsyncTaskDisapper myAsyncTaskDisapper = new MyAsyncTaskDisapper();
                        myAsyncTaskDisapper.execute(new String[]{"ghiHinhDonTu", jsonObjectC.getString("TieuThu").replace("null", ""), imgString});
                    }
                    CLocal.showPopupMessage(ActivityDocSo_PhieuChuyen.this, s + "\r\n" + jsonObject.getString("error").replace("null", ""), "center");
                } else
                    CLocal.showPopupMessage(ActivityDocSo_PhieuChuyen.this, s, "center");
            } catch (Exception e) {
                CLocal.showPopupMessage(ActivityDocSo_PhieuChuyen.this, e.getMessage(), "center");
            }
        }
    }

    public class MyAsyncTaskDisapper extends AsyncTask<String, String, String> {
        //        ProgressDialog progressDialog;
        JSONObject jsonObject = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(ActivityDocSo_PhieuChuyen.this);
//            progressDialog.setTitle("Thông Báo");
//            progressDialog.setMessage("Đang xử lý...");
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String result = "";
                switch (strings[0]) {
                    case "getDSDonTu":
                        result = ws.getDS_DonTu(entityParent.getDanhBo().replace(" ", ""));
                        if (result.equals("") == false)
                            jsonObject = new JSONObject(result);
                        if (jsonObject != null && Boolean.parseBoolean(jsonObject.getString("success").replace("null", "")) == true) {
                            publishProgress(new String[]{jsonObject.getString("message").replace("null", "")});
                            return "THÀNH CÔNG";
                        } else
                            return "THẤT BẠI";
                    case "ghiHinhDonTu":
                        result = ws.ghi_Hinh_DonTu(strings[1], strings[2], CLocal.MaNV);
                        if (result.equals("") == false)
                            jsonObject = new JSONObject(result);
                        if (jsonObject != null && Boolean.parseBoolean(jsonObject.getString("success").replace("null", "")) == true) {
                            publishProgress(new String[]{jsonObject.getString("message").replace("null", "")});
                            return "THÀNH CÔNG";
                        } else
                            return "THẤT BẠI";
                }
                return null;
            } catch (Exception e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            if (values != null) {
                try {
                    jsonDSDonTu = new JSONArray(values[0]);
                    fillDSDonTu();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            if (progressDialog != null) {
//                progressDialog.dismiss();
//            }
            try {
//                if (jsonObject != null)
//                    CLocal.showPopupMessage(ActivityDocSo_GhiChu.this, s + "\r\n" + jsonObject.getString("error").replace("null", ""), "center");
//                else
//                    CLocal.showPopupMessage(ActivityDocSo_GhiChu.this, s, "center");
            } catch (Exception e) {
                CLocal.showPopupMessage(ActivityDocSo_PhieuChuyen.this, e.getMessage(), "center");
            }
        }

    }
}