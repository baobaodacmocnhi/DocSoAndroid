package vn.com.capnuoctanhoa.docsoandroid.DocSo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vn.com.capnuoctanhoa.docsoandroid.Class.CCode;
import vn.com.capnuoctanhoa.docsoandroid.Class.CEntityParent;
import vn.com.capnuoctanhoa.docsoandroid.Class.CLocal;
import vn.com.capnuoctanhoa.docsoandroid.Class.CMarshMallowPermission;
import vn.com.capnuoctanhoa.docsoandroid.Class.CWebservice;
import vn.com.capnuoctanhoa.docsoandroid.Class.CustomAdapterRecyclerViewDienThoai;
import vn.com.capnuoctanhoa.docsoandroid.Class.CustomAdapterRecyclerViewImage;
import vn.com.capnuoctanhoa.docsoandroid.Class.CustomAdapterSpinner;
import vn.com.capnuoctanhoa.docsoandroid.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActivityDocSo_GhiChiSo extends AppCompatActivity {
    //    private Integer STT = -1;
    private CEntityParent entityParent;
    private EditText edtMLT, edtDanhBo, edtHoTen, edtDiaChi, edtDiaChiDHN, edtViTri, edtHieu, edtCo, edtSoThan, edtGiaBieu, edtDinhMuc, edtDinhMucHN, edtDienThoai, edtChiSo, edtTBTT;
    private Spinner spnCode;
    private ArrayList<CCode> spnName_Code;
    private ImageView ivTruoc, ivSau, ivGhiChu, ivIn, ivLuu;
    private TextView txtChiSo2, txtCode2, txtTieuThu2, txtChiSo1, txtCode1, txtTieuThu1, txtChiSo0, txtCode0, txtTieuThu0, txtChiSoMoi, txtCodeMoi, txtTieuThuMoi;
    private EditText edtChiSo2, edtCode2, edtTieuThu2, edtChiSo1, edtCode1, edtTieuThu1, edtChiSo0, edtCode0, edtTieuThu0, edtChiSoMoi, edtCodeMoi, edtTieuThuMoi;
    private CustomAdapterSpinner customAdapterSpinner;
    private CCode selectedCode = null;
    private String imgPath;
    private Bitmap imgCapture;
    private ArrayList<Bitmap> lstCapture;
    private RecyclerView recyclerView;
    private ImageButton ibtnChupHinh;
    private Button btnChonHinh;
    private CustomAdapterRecyclerViewImage customAdapterRecyclerViewImage;
    private CMarshMallowPermission cMarshMallowPermission;
    private CWebservice ws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_so_ghi_chi_so);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtMLT = (EditText) findViewById(R.id.edtMLT);
        edtDanhBo = (EditText) findViewById(R.id.edtDanhBo);
        edtHoTen = (EditText) findViewById(R.id.edtHoTen);
        edtDiaChi = (EditText) findViewById(R.id.edtDiaChi);
        edtDiaChiDHN = (EditText) findViewById(R.id.edtDiaChiDHN);
        edtViTri = (EditText) findViewById(R.id.edtViTri);
        edtHieu = (EditText) findViewById(R.id.edtHieu);
        edtCo = (EditText) findViewById(R.id.edtCo);
        edtSoThan = (EditText) findViewById(R.id.edtSoThan);
        edtGiaBieu = (EditText) findViewById(R.id.edtGiaBieu);
        edtDinhMuc = (EditText) findViewById(R.id.edtDinhMuc);
        edtDinhMucHN = (EditText) findViewById(R.id.edtDinhMucHN);
        edtDienThoai = (EditText) findViewById(R.id.edtDienThoai);
        edtTBTT = (EditText) findViewById(R.id.edtTBTT);
//        txtChiSo2 = (TextView) findViewById(R.id.txtChiSo2);
//        txtCode2 = (TextView) findViewById(R.id.txtCode2);
//        txtTieuThu2 = (TextView) findViewById(R.id.txtTieuThu2);
//        txtChiSo1 = (TextView) findViewById(R.id.txtChiSo1);
//        txtCode1 = (TextView) findViewById(R.id.txtCode1);
//        txtTieuThu1 = (TextView) findViewById(R.id.txtTieuThu1);
//        txtChiSo0 = (TextView) findViewById(R.id.txtChiSo0);
//        txtCode0 = (TextView) findViewById(R.id.txtCode0);
//        txtTieuThu0 = (TextView) findViewById(R.id.txtTieuThu0);
//        txtChiSoMoi = (TextView) findViewById(R.id.txtChiSo);
//        txtCodeMoi = (TextView) findViewById(R.id.txtCode);
//        txtTieuThuMoi = (TextView) findViewById(R.id.txtTieuThu);
        edtChiSo2 = (EditText) findViewById(R.id.edtChiSo2);
        edtCode2 = (EditText) findViewById(R.id.edtCode2);
        edtTieuThu2 = (EditText) findViewById(R.id.edtTieuThu2);
        edtChiSo1 = (EditText) findViewById(R.id.edtChiSo1);
        edtCode1 = (EditText) findViewById(R.id.edtCode1);
        edtTieuThu1 = (EditText) findViewById(R.id.edtTieuThu1);
        edtChiSo0 = (EditText) findViewById(R.id.edtChiSo0);
        edtCode0 = (EditText) findViewById(R.id.edtCode0);
        edtTieuThu0 = (EditText) findViewById(R.id.edtTieuThu0);
        edtChiSoMoi = (EditText) findViewById(R.id.edtChiSoMoi);
        edtCodeMoi = (EditText) findViewById(R.id.edtCodeMoi);
        edtTieuThuMoi = (EditText) findViewById(R.id.edtTieuThuMoi);
        edtChiSo = (EditText) findViewById(R.id.edtChiSo);
        spnCode = (Spinner) findViewById(R.id.spnCode);
        ivTruoc = (ImageView) findViewById(R.id.ivTruoc);
        ivSau = (ImageView) findViewById(R.id.ivSau);
        ivGhiChu = (ImageView) findViewById(R.id.ivGhiChu);
        ivIn = (ImageView) findViewById(R.id.ivIn);
        ivLuu = (ImageView) findViewById(R.id.ivLuu);
        ibtnChupHinh = (ImageButton) findViewById(R.id.ibtnChupHinh);
        btnChonHinh = (Button) findViewById(R.id.btnChonHinh);
        lstCapture = new ArrayList<Bitmap>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        cMarshMallowPermission = new CMarshMallowPermission(this);
        ws = new CWebservice();
        try {
//            STT = Integer.parseInt(getIntent().getStringExtra("STT"));
            if (CLocal.STT > -1) {
                entityParent = CLocal.listDocSoView.get(CLocal.STT);
                fillLayout(entityParent);
            }
        } catch (Exception ex) {
            CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, ex.getMessage());
        }

        try {
            if (CLocal.jsonCode != null && CLocal.jsonCode.length() > 0) {
                spnName_Code = new ArrayList<>();
                for (int i = 0; i < CLocal.jsonCode.length(); i++) {
                    JSONObject jsonObject = CLocal.jsonCode.getJSONObject(i);
                    CCode item = new CCode();
                    item.setCode(jsonObject.getString("Code"));
                    item.setMoTa(jsonObject.getString("MoTa"));
                    spnName_Code.add(item);
                }
            }
            customAdapterSpinner = new CustomAdapterSpinner(this, spnName_Code);
            spnCode.setAdapter(customAdapterSpinner);
            spnCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedCode = (CCode) parent.getItemAtPosition(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        ivTruoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CLocal.STT > 0) {
                    CLocal.STT--;
                    entityParent = CLocal.listDocSoView.get(CLocal.STT);
                    fillLayout(entityParent);
                } else
                    CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, "Đầu Danh Sách");
            }
        });

        ivSau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CLocal.STT < CLocal.listDocSoView.size() - 1) {
                    CLocal.STT++;
                    entityParent = CLocal.listDocSoView.get(CLocal.STT);
                    fillLayout(entityParent);
                } else
                    CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, "Cuối Danh Sách");
            }
        });

        ibtnChupHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (cMarshMallowPermission.checkPermissionForExternalStorage() == false) {
                        cMarshMallowPermission.requestPermissionForExternalStorage();
                    }
                    if (cMarshMallowPermission.checkPermissionForExternalStorage() == false)
                        return;
                }
                imgCapture = null;
                Uri imgUri = createImageUri();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(ActivityDocSo_GhiChiSo.this.getPackageManager()) != null) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri); // put uri file khi mà mình muốn lưu ảnh sau khi chụp như thế nào  ?
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                    startActivityForResult(intent, 1);
                    activityResultLauncher_ChupHinh.launch(intent);
                }
            }
        });

        btnChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
//                    startActivityForResult(intent, 2);
                    activityResultLauncher_ChonHinh.launch(intent);
                } else if (Build.VERSION.SDK_INT > 19) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(intent, 2);
                    activityResultLauncher_ChonHinh.launch(intent);
                }
            }
        });

        ivLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CLocal.checkNetworkAvailable(ActivityDocSo_GhiChiSo.this) == false) {
                    CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, "Không có Internet");
                    return;
                }
                if (lstCapture.size() > 0 && selectedCode != null
                        && ((((CCode) selectedCode).getCode().contains("F1") == true
                        || ((CCode) selectedCode).getCode().contains("F2") == true
                        || ((CCode) selectedCode).getCode().contains("F3") == true
                        || ((CCode) selectedCode).getCode().contains("F4") == true
                        || ((CCode) selectedCode).getCode().contains("61") == true
                        || ((CCode) selectedCode).getCode().contains("63") == true
                        || ((CCode) selectedCode).getCode().contains("64") == true
                        || ((CCode) selectedCode).getCode().contains("66") == true
                        || ((CCode) selectedCode).getCode().contains("68") == true
                        || ((CCode) selectedCode).getCode().contains("K") == true)
                        || (((CCode) selectedCode).getCode().contains("F1") == false
                        && ((CCode) selectedCode).getCode().contains("F2") == false
                        && ((CCode) selectedCode).getCode().contains("F3") == false
                        && ((CCode) selectedCode).getCode().contains("F4") == false
                        && ((CCode) selectedCode).getCode().contains("61") == false
                        && ((CCode) selectedCode).getCode().contains("63") == false
                        && ((CCode) selectedCode).getCode().contains("64") == false
                        && ((CCode) selectedCode).getCode().contains("66") == false
                        && ((CCode) selectedCode).getCode().contains("68") == false
                        && ((CCode) selectedCode).getCode().contains("K") == false
                        && edtChiSo.getText().toString().equals("") == false))) {
                    MyAsyncTask myAsyncTask = new MyAsyncTask();
                    myAsyncTask.execute();
                } else {
                    CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, "Thiếu dữ liệu Code-CSM-Hình ảnh");
                }
            }
        });

        ivIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (entityParent.getCodeMoi().equals("") == false) {
                        if (CLocal.serviceThermalPrinter != null) {
                            CLocal.serviceThermalPrinter.printGhiChiSo(entityParent);
                        }
                    } else {
                        CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, "Chưa có dữ liệu In");
                    }
                } catch (Exception ex) {
                    CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, ex.getMessage());
                }
            }
        });

        ivGhiChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CLocal.checkNetworkAvailable(ActivityDocSo_GhiChiSo.this) == false) {
                    CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, "Không có Internet");
                    return;
                }
                Intent intent = new Intent(ActivityDocSo_GhiChiSo.this, ActivityDocSo_GhiChu.class);
//                intent.putExtra("STT", String.valueOf(STT));
                startActivity(intent);
            }
        });

        if (savedInstanceState != null) {
            imgCapture = CLocal.convertByteArrayToBitmap(savedInstanceState.getByteArray("imgCapture"));
            lstCapture.add(imgCapture);
            loadRecyclerViewImage();
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putByteArray("imgCapture", CLocal.convertBitmapToByteArray(imgCapture));
    }

    private void initial() {
//        entityParent=null;
        lstCapture = new ArrayList<>();
        lstCapture.clear();
        spnCode.setSelection(0);
        selectedCode = (CCode) spnCode.getItemAtPosition(0);
        edtChiSo.setText("");
        loadRecyclerViewImage();
        //
        edtMLT.setText("");
        edtDanhBo.setText("");
        edtHoTen.setText("");
        edtDiaChi.setText("");
        edtDiaChiDHN.setText("");
        edtViTri.setText("");
        edtHieu.setText("");
        edtCo.setText("");
        edtSoThan.setText("");
        edtDienThoai.setText("");
        edtGiaBieu.setText("");
        edtDinhMuc.setText("");
        edtDinhMucHN.setText("");
        edtTBTT.setText("");
        edtChiSo2.setText("");
        edtCode2.setText("");
        edtTieuThu2.setText("");
        edtChiSo1.setText("");
        edtCode1.setText("");
        edtTieuThu1.setText("");
        edtChiSo0.setText("");
        edtCode0.setText("");
        edtTieuThu0.setText("");
        edtChiSoMoi.setText("");
        edtCodeMoi.setText("");
        edtTieuThuMoi.setText("");
    }

    private void loadRecyclerViewImage() {
        customAdapterRecyclerViewImage = new CustomAdapterRecyclerViewImage(this, lstCapture);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(customAdapterRecyclerViewImage);
    }

    private void selectValue(String value) {
        for (int i = 0; i < spnCode.getCount(); i++) {
            CCode code = (CCode) spnCode.getItemAtPosition(i);
            if (code.getCode().equals(value)) {
                spnCode.setSelection(i);
                selectedCode = (CCode) spnCode.getItemAtPosition(i);
                break;
            }
        }
    }

    private void fillLayout(CEntityParent entityParent) {
        try {
            initial();
            if (entityParent != null) {
                edtMLT.setText(entityParent.getMLT());
                edtDanhBo.setText(entityParent.getDanhBo());
                edtHoTen.setText(entityParent.getHoTen());
                edtDiaChi.setText(entityParent.getDiaChi());
                edtDiaChiDHN.setText(entityParent.getSoNha() + " " + entityParent.getTenDuong());
                edtViTri.setText(entityParent.getViTri1() + " - " + entityParent.getViTri2());
                edtHieu.setText(entityParent.getHieu());
                edtCo.setText(entityParent.getCo());
                edtSoThan.setText(entityParent.getSoThan());
                edtDienThoai.setText(entityParent.getDienThoai());
                edtGiaBieu.setText(entityParent.getGiaBieu());
                edtDinhMuc.setText(entityParent.getDinhMuc());
                edtDinhMucHN.setText(entityParent.getDinhMucHN());
                edtTBTT.setText(entityParent.getTBTT());
                edtChiSo2.setText(entityParent.getChiSo2());
                edtCode2.setText(entityParent.getCode2());
                edtTieuThu2.setText(entityParent.getTieuThu2());
                edtChiSo1.setText(entityParent.getChiSo1());
                edtCode1.setText(entityParent.getCode1());
                edtTieuThu1.setText(entityParent.getTieuThu1());
                edtChiSo0.setText(entityParent.getChiSo0());
                edtCode0.setText(entityParent.getCode0());
                edtTieuThu0.setText(entityParent.getTieuThu0());
                edtChiSoMoi.setText(entityParent.getChiSoMoi());
                edtCodeMoi.setText(entityParent.getCodeMoi());
                edtTieuThuMoi.setText(entityParent.getTieuThuMoi());
                // if (entityParent.getChiSoMoi().equals("") == false)
                //        edtChiSo.setText(entityParent.getChiSoMoi());
                //    if (entityParent.getCodeMoi().equals("") == false)
                //       selectValue(entityParent.getCodeMoi());
                try {
                    Bitmap bitmap = BitmapFactory.decodeFile(CLocal.pathAppPicture + "/" + entityParent.getNam() + "_" + entityParent.getKy() + "_" + entityParent.getDot() + "/" + entityParent.getDanhBo().replace(" ", "") + ".jpg");
                    if (bitmap != null) {
                        bitmap = CLocal.imageOreintationValidator(bitmap, CLocal.pathAppPicture + "/" + entityParent.getNam() + "_" + entityParent.getKy() + "_" + entityParent.getDot() + "/" + entityParent.getDanhBo().replace(" ", "") + ".jpg");
                        lstCapture.add(bitmap);
                        loadRecyclerViewImage();
                    }
                } catch (Exception ex) {
                }
            }
        } catch (
                Exception ex) {
            CLocal.showPopupMessage(ActivityDocSo_GhiChiSo.this, ex.getMessage(), "center");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
                        CLocal.imgCapture = Bitmap.createScaledBitmap(imgCapture, 1024, 1024, false);
                        lstCapture = new ArrayList<>();
                        lstCapture.add(CLocal.imgCapture);
                        loadRecyclerViewImage();
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
                        String strPath = CLocal.getPathFromUri(ActivityDocSo_GhiChiSo.this, uri);
                        Bitmap bitmap = BitmapFactory.decodeFile(strPath);
                        bitmap = CLocal.imageOreintationValidator(bitmap, strPath);
                        imgCapture = bitmap;
                    }
                    if (imgCapture != null) {
                        CLocal.imgCapture = Bitmap.createScaledBitmap(imgCapture, 1024, 1024, false);
                        lstCapture = new ArrayList<>();
                        lstCapture.add(CLocal.imgCapture);
                        loadRecyclerViewImage();
                    }
                }
            });

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        try {
//            if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
//                if (imgPath != null && imgPath != "") {
//                    Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
//                    bitmap = CLocal.imageOreintationValidator(bitmap, imgPath);
//                    imgCapture = bitmap;
//                }
//
//            } else if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
//                Uri uri = data.getData();
//                String strPath = CLocal.getPathFromUri(this, uri);
//                Bitmap bitmap = BitmapFactory.decodeFile(strPath);
//                bitmap = CLocal.imageOreintationValidator(bitmap, strPath);
//                imgCapture = bitmap;
//            }
//            if (imgCapture != null) {
//                lstCapture = new ArrayList<>();
//                lstCapture.add(Bitmap.createScaledBitmap(imgCapture, 1024, 1024, false));
//                loadRecyclerViewImage();
//            }
//        } catch (Exception ex) {
//            CLocal.showToastMessage(getApplicationContext(), ex.getMessage());
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyAsyncTaskGhiHinhAll myAsyncTaskGhiHinhAll = new MyAsyncTaskGhiHinhAll();
        myAsyncTaskGhiHinhAll.execute();
        CLocal.updateArrayListToJson();
    }

    @Override
    protected void onResume() {
        super.onResume();
        lstCapture.add(CLocal.imgCapture);
    }

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
        String imgString = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ActivityDocSo_GhiChiSo.this);
            progressDialog.setTitle("Thông Báo");
            progressDialog.setMessage("Đang xử lý...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
//                String imgString = "";
                jsonObject = new JSONObject();
                if (lstCapture.size() > 0) {
                    for (int i = 0; i < lstCapture.size(); i++) {
                        if (imgString.equals("") == true)
                            imgString = CLocal.convertBitmapToString(lstCapture.get(i));
//                        else
//                            imgString += ";" + CLocal.convertBitmapToString(lstCapture.get(i));
                    }
                }
                String result = ws.ghiChiSo(entityParent.getID(), selectedCode.getCode(), edtChiSo.getText().toString(), "", entityParent.getDot(), CLocal.May, entityParent.getTBTT());
                if (result.equals("") == false)
                    jsonObject = new JSONObject(result);
                if (jsonObject != null && Boolean.parseBoolean(jsonObject.getString("success").replace("null", "")) == true) {
                    entityParent.setCodeMoi(selectedCode.getCode());
                    entityParent.setChiSoMoi(edtChiSo.getText().toString());
                    JSONObject jsonObjectC = new JSONObject(jsonObject.getString("message").replace("null", ""));
                    entityParent.setTieuThuMoi(jsonObjectC.getString("TieuThu").replace("null", ""));
                    entityParent.setTienNuoc(jsonObjectC.getString("TienNuoc").replace("null", ""));
                    entityParent.setTienNuoc(jsonObjectC.getString("TienNuoc").replace("null", ""));
                    entityParent.setThueGTGT(jsonObjectC.getString("ThueGTGT").replace("null", ""));
                    entityParent.setPhiBVMT(jsonObjectC.getString("PhiBVMT").replace("null", ""));
                    entityParent.setPhiBVMT_Thue(jsonObjectC.getString("PhiBVMT_Thue").replace("null", ""));
                    entityParent.setTongCong(jsonObjectC.getString("TongCong").replace("null", ""));
                    if (lstCapture.size() > 0) {
                        for (int i = 0; i < lstCapture.size(); i++) {
                            CLocal.writeFile(CLocal.pathAppPicture + "/" + entityParent.getNam() + "_" + entityParent.getKy() + "_" + entityParent.getDot()
                                    , entityParent.getDanhBo().replace(" ", "") + ".jpg", lstCapture.get(i));
                        }
                    }
//                    if (jsonObject.getString("hoadonton").replace("null", "").equals("") == false) {
//                        JSONArray jsonHoaDonTon = new JSONArray(jsonObject.getString("hoadonton").replace("null", ""));
//                        for (int i = 0; i < jsonHoaDonTon.length(); i++) {
//                            JSONObject jsonhd = jsonHoaDonTon.getJSONObject(i);
//                            CEntityChild entityChild = new CEntityChild();
//                            entityChild.setKy(jsonhd.getString("KyHD"));
//                            entityChild.setTongCong(jsonhd.getString("TongCong"));
//                            entityParent.getLstHoaDon().add(entityChild);
//                        }
//                    }
                    CLocal.updateTinhTrangParent(CLocal.listDocSo, entityParent);
                    return "THÀNH CÔNG";
                } else
                    return "THẤT BẠI";
            } catch (Exception e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            try {
                if (jsonObject != null) {
                    String error = "", alert = "";
                    if (jsonObject.getString("error").replace("null", "").equals("") == false)
                        error = "\r\n" + jsonObject.getString("error").replace("null", "");
                    if (jsonObject.getString("alert").replace("null", "").equals("") == false)
                        alert = "\r\n" + jsonObject.getString("alert").replace("null", "");
                    //báo thành công
                    if (Boolean.parseBoolean(jsonObject.getString("success").replace("null", "")) == true) {
                        if (imgString.equals("") == false) {
                            MyAsyncTaskGhiHinh myAsyncTaskGhiHinh = new MyAsyncTaskGhiHinh();
                            myAsyncTaskGhiHinh.execute(new String[]{entityParent.getID(), imgString});
                        }
                        //thành công có cảnh báo
                        if (alert.equals("") == false) {
                            CLocal.vibrate(ActivityDocSo_GhiChiSo.this);
                            CLocal.showDialog(ActivityDocSo_GhiChiSo.this, "Thông Báo", s + alert, "Đọc Tiếp", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    ivSau.performClick();
                                }
                            }, "In", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    ivIn.performClick();
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            ivSau.performClick();
                                        }
                                    }, 2000);
                                }
                            }, false);
                        } else {//thành công không có cảnh báo
                            CLocal.showPopupMessage(ActivityDocSo_GhiChiSo.this, s, "center");
                            ivIn.performClick();
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ivSau.performClick();
                                }
                            }, 2000);
                        }
                    } else {//thất bại
                        CLocal.showPopupMessage(ActivityDocSo_GhiChiSo.this, s + error, "center");
                        CLocal.vibrate(ActivityDocSo_GhiChiSo.this);
                    }
                } else
                    CLocal.showPopupMessage(ActivityDocSo_GhiChiSo.this, s, "center");
//                if(flagGhiHinh==true)
//                {
//                    MyAsyncTaskGhiHinhAll myAsyncTaskGhiHinhAll=new MyAsyncTaskGhiHinhAll();
//                    myAsyncTaskGhiHinhAll.execute();
//                }
            } catch (JSONException e) {
                CLocal.showPopupMessage(ActivityDocSo_GhiChiSo.this, e.getMessage(), "center");
            }
        }
    }


    public class MyAsyncTaskGhiHinh extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                String result = ws.ghi_Hinh(strings[0], strings[1]);
                if (Boolean.parseBoolean(result) == true) {
                    for (int i = 0; i < CLocal.listDocSoView.size(); i++)
                        if (CLocal.listDocSoView.get(i).getID().equals(strings[0]) == true) {
                            CLocal.listDocSoView.get(i).setGhiHinh(true);
                            CLocal.updateTinhTrangParent(CLocal.listDocSo, CLocal.listDocSoView.get(i));
                        }
                }
            } catch (Exception ex) {
                CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, ex.getMessage());
            }
            return null;
        }
    }


    public class MyAsyncTaskGhiHinhAll extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                for (int i = 0; i < CLocal.listDocSoView.size(); i++)
                    if (CLocal.listDocSoView.get(i).getCodeMoi().equals("") == false && CLocal.listDocSoView.get(i).isGhiHinh() == false) {
                        Bitmap bitmap = BitmapFactory.decodeFile(CLocal.pathAppPicture + "/" + CLocal.listDocSoView.get(i).getNam() + "_" + CLocal.listDocSoView.get(i).getKy() + "_" + CLocal.listDocSoView.get(i).getDot() + "/" + CLocal.listDocSoView.get(i).getDanhBo().replace(" ", "") + ".jpg");
                        if (bitmap != null) {
                            bitmap = CLocal.imageOreintationValidator(bitmap, CLocal.pathAppPicture + "/" + CLocal.listDocSoView.get(i).getNam() + "_" + CLocal.listDocSoView.get(i).getKy() + "_" + CLocal.listDocSoView.get(i).getDot() + "/" + CLocal.listDocSoView.get(i).getDanhBo().replace(" ", "") + ".jpg");
                            String result = ws.ghi_Hinh(CLocal.listDocSoView.get(i).getID(), CLocal.convertBitmapToString(bitmap));
                            if (Boolean.parseBoolean(result) == true) {
                                CLocal.listDocSoView.get(i).setGhiHinh(true);
                                CLocal.updateTinhTrangParent(CLocal.listDocSo, CLocal.listDocSoView.get(i));
                            }
                        }
                    }
            } catch (Exception ex) {
                CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, ex.getMessage());
            }
            return null;
        }
    }
}