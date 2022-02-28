package vn.com.capnuoctanhoa.docsoandroid.DocSo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vn.com.capnuoctanhoa.docsoandroid.Class.CCode;
import vn.com.capnuoctanhoa.docsoandroid.Class.CEntityChild;
import vn.com.capnuoctanhoa.docsoandroid.Class.CEntityParent;
import vn.com.capnuoctanhoa.docsoandroid.Class.CLocal;
import vn.com.capnuoctanhoa.docsoandroid.Class.CMarshMallowPermission;
import vn.com.capnuoctanhoa.docsoandroid.Class.CWebservice;
import vn.com.capnuoctanhoa.docsoandroid.Class.CustomAdapterRecyclerViewImage;
import vn.com.capnuoctanhoa.docsoandroid.Class.CustomAdapterSpinner;
import vn.com.capnuoctanhoa.docsoandroid.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActivityDocSo_GhiChiSo extends AppCompatActivity {
    private Integer STT = -1;
    private EditText edtMLT, edtDanhBo, edtHoTen, edtDiaChi, edtDiaChiDHN, edtViTri, edtHieu, edtCo, edtSoThan, edtGiaBieu, edtDinhMuc, edtDinhMucHN, edtDienThoai, edtChiSo, edtTBTT;
    private Spinner spnCode;
    private ArrayList<CCode> spnName_Code;
    private ImageView ivTruoc, ivSau, ivGhiChu, ivIn, ivLuu;
    private TextView txtChiSo2, txtCode2, txtTieuThu2, txtChiSo1, txtCode1, txtTieuThu1, txtChiSo0, txtCode0, txtTieuThu0, txtChiSoMoi, txtCodeMoi, txtTieuThuMoi;
    private CustomAdapterSpinner customAdapterSpinner;
    private CCode selectedCode = null;
    private String imgPath;
    private Bitmap imgCapture;
    private ArrayList<Bitmap> lstCapture;
    private RecyclerView recyclerView;
    private ImageButton ibtnChupHinh;
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
        txtChiSo2 = (TextView) findViewById(R.id.txtChiSo2);
        txtCode2 = (TextView) findViewById(R.id.txtCode2);
        txtTieuThu2 = (TextView) findViewById(R.id.txtTieuThu2);
        txtChiSo1 = (TextView) findViewById(R.id.txtChiSo1);
        txtCode1 = (TextView) findViewById(R.id.txtCode1);
        txtTieuThu1 = (TextView) findViewById(R.id.txtTieuThu1);
        txtChiSo0 = (TextView) findViewById(R.id.txtChiSo0);
        txtCode0 = (TextView) findViewById(R.id.txtCode0);
        txtTieuThu0 = (TextView) findViewById(R.id.txtTieuThu0);
        txtChiSoMoi = (TextView) findViewById(R.id.txtChiSo);
        txtCodeMoi = (TextView) findViewById(R.id.txtCode);
        txtTieuThuMoi = (TextView) findViewById(R.id.txtTieuThu);
        edtChiSo = (EditText) findViewById(R.id.edtChiSo);
        spnCode = (Spinner) findViewById(R.id.spnCode);
        ivTruoc = (ImageView) findViewById(R.id.ivTruoc);
        ivSau = (ImageView) findViewById(R.id.ivSau);
        ivGhiChu = (ImageView) findViewById(R.id.ivGhiChu);
        ivIn = (ImageView) findViewById(R.id.ivIn);
        ivLuu = (ImageView) findViewById(R.id.ivLuu);
        ibtnChupHinh = (ImageButton) findViewById(R.id.ibtnChupHinh);
        lstCapture = new ArrayList<Bitmap>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        cMarshMallowPermission = new CMarshMallowPermission(this);
        ws = new CWebservice();
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
                if (STT > 0) {
                    STT--;
                    fillLayout(STT);
                } else
                    CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, "Đầu Danh Sách");
            }
        });

        ivSau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (STT < CLocal.listDocSoView.size() - 1) {
                    STT++;
                    fillLayout(STT);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDocSo_GhiChiSo.this);
                builder.setTitle("Thông Báo");
                builder.setMessage("Chọn lựa hành động");
                builder.setCancelable(false);
                builder.setPositiveButton("Chụp từ camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri imgUri = createImageUri();
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (intent.resolveActivity(ActivityDocSo_GhiChiSo.this.getPackageManager()) != null) {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri); // put uri file khi mà mình muốn lưu ảnh sau khi chụp như thế nào  ?
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                            startActivityForResult(intent, 1);
                        }
                    }
                });
                builder.setNegativeButton("Chọn từ thư viện", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (Build.VERSION.SDK_INT <= 19) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            startActivityForResult(intent, 2);
                        } else if (Build.VERSION.SDK_INT > 19) {
                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 2);
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        ivLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CLocal.checkNetworkAvailable(ActivityDocSo_GhiChiSo.this) == false) {
                    CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, "Không có Internet");
                    return;
                }
                if (selectedCode != null && edtChiSo.getText().toString().equals("") == false && lstCapture.size() > 0) {
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
                if (CLocal.listDocSoView.get(STT).getCodeMoi().equals("") == false) {
                    if (CLocal.serviceThermalPrinter != null) {
                        try {
                            CLocal.serviceThermalPrinter.printGhiChiSo(CLocal.listDocSoView.get(STT));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, "Chưa có dữ liệu In");
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
                Intent intent = new Intent(getApplicationContext(), ActivityDocSo_GhiChu.class);
                intent.putExtra("STT", String.valueOf(STT));
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            STT = Integer.parseInt(getIntent().getStringExtra("STT"));
            if (STT > -1) {
                fillLayout(STT);
            }
        } catch (Exception ex) {
            CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, ex.getMessage());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
                if (imgPath != null && imgPath != "") {
                    Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
                    bitmap = CLocal.imageOreintationValidator(bitmap, imgPath);
                    imgCapture = bitmap;
                }

            } else if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
                Uri uri = data.getData();
                String strPath = CLocal.getPathFromUri(this, uri);
                Bitmap bitmap = BitmapFactory.decodeFile(strPath);
                bitmap = CLocal.imageOreintationValidator(bitmap, strPath);
                imgCapture = bitmap;
            }
            if (imgCapture != null) {
                lstCapture.add(imgCapture);
                customAdapterRecyclerViewImage = new CustomAdapterRecyclerViewImage(this, lstCapture);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(customAdapterRecyclerViewImage);
            }
        } catch (Exception ex) {
            CLocal.showToastMessage(getApplicationContext(), ex.getMessage());
        }
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

    private void fillLayout(final Integer STT) {
        try {
            if (CLocal.listDocSoView != null && CLocal.listDocSoView.size() > 0) {
                ArrayList<String> arrayList = new ArrayList<String>();
                if (STT >= 0 && STT < CLocal.listDocSoView.size()) {
                    CEntityParent item = CLocal.listDocSoView.get(STT);
                    edtMLT.setText(item.getMLT());
                    edtDanhBo.setText(item.getDanhBo());
                    edtHoTen.setText(item.getHoTen());
                    edtDiaChi.setText(item.getDiaChi());
                    edtDiaChiDHN.setText(item.getSoNha() + " " + item.getTenDuong());
                    edtViTri.setText(item.getViTri1() + " - " + item.getViTri2());
                    edtHieu.setText(item.getHieu());
                    edtCo.setText(item.getCo());
                    edtSoThan.setText(item.getSoThan());
                    edtDienThoai.setText(item.getDienThoai());
                    edtGiaBieu.setText(item.getGiaBieu());
                    edtDinhMuc.setText(item.getDinhMuc());
                    edtDinhMucHN.setText(item.getDinhMucHN());
                    edtTBTT.setText(item.getTBTT());
                    txtChiSo2.setText(item.getChiSo2());
                    txtCode2.setText(item.getCode2());
                    txtTieuThu2.setText(item.getTieuThu2());
                    txtChiSo1.setText(item.getChiSo1());
                    txtCode1.setText(item.getCode1());
                    txtTieuThu1.setText(item.getTieuThu1());
                    txtChiSo0.setText(item.getChiSo0());
                    txtCode0.setText(item.getCode0());
                    txtTieuThu0.setText(item.getTieuThu0());
                    txtChiSoMoi.setText(item.getChiSoMoi());
                    txtCodeMoi.setText(item.getCodeMoi());
                    txtTieuThuMoi.setText(item.getTieuThuMoi());
                    if (item.getLstCapture().size() > 0) {
                        for (int i = 0; i < item.getLstCapture().size(); i++) {
                            lstCapture.add(item.getLstCapture().get(i));
                        }
                        customAdapterRecyclerViewImage = new CustomAdapterRecyclerViewImage(this, lstCapture);
                        recyclerView.setHasFixedSize(true);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(customAdapterRecyclerViewImage);
                    }
                }
            }
        } catch (Exception ex) {

        }
    }

    public class MyAsyncTask extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        JSONObject jsonObject = null;

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
                String imgString = "";
                if (lstCapture.size() > 0) {
                    for (int i = 0; i < lstCapture.size(); i++) {
                        Bitmap reizeImage = Bitmap.createScaledBitmap(lstCapture.get(i), 1024, 1024, false);
                        if (imgString.equals("") == true)
                            imgString += CLocal.convertBitmapToString(reizeImage);
//                        else
//                            imgString += ";" + CLocal.convertBitmapToString(reizeImage);
                    }
                }
                String result = ws.ghiChiSo(CLocal.listDocSoView.get(STT).getID(), selectedCode.getCode(), edtChiSo.getText().toString(), imgString, CLocal.listDocSoView.get(STT).getDot(), CLocal.May);
                if (result.equals("") == false)
                    jsonObject = new JSONObject(result);
                if (jsonObject != null && Boolean.parseBoolean(jsonObject.getString("success").replace("null", "")) == true) {
                    CLocal.listDocSoView.get(STT).setCodeMoi(selectedCode.getCode());
                    CLocal.listDocSoView.get(STT).setChiSoMoi(edtChiSo.getText().toString());
                    JSONObject jsonObjectC = new JSONObject(jsonObject.getString("message").replace("null", ""));
                    CLocal.listDocSoView.get(STT).setTieuThuMoi(jsonObjectC.getString("TieuThu").replace("null", ""));
                    CLocal.listDocSoView.get(STT).setTienNuoc(jsonObjectC.getString("TienNuoc").replace("null", ""));
                    CLocal.listDocSoView.get(STT).setTienNuoc(jsonObjectC.getString("TienNuoc").replace("null", ""));
                    CLocal.listDocSoView.get(STT).setThueGTGT(jsonObjectC.getString("ThueGTGT").replace("null", ""));
                    CLocal.listDocSoView.get(STT).setPhiBVMT(jsonObjectC.getString("PhiBVMT").replace("null", ""));
                    CLocal.listDocSoView.get(STT).setPhiBVMT_Thue(jsonObjectC.getString("PhiBVMT_Thue").replace("null", ""));
                    CLocal.listDocSoView.get(STT).setTongCong(jsonObjectC.getString("TongCong").replace("null", ""));
                    if (lstCapture.size() > 0) {
                        CLocal.listDocSoView.get(STT).getLstCapture().clear();
                        for (int i = 0; i < lstCapture.size(); i++) {
                            CLocal.listDocSoView.get(STT).getLstCapture().add(lstCapture.get(i));
                        }
                    }
                    if(jsonObject.getString("hoadonton").replace("null", "").equals("")==false) {
                        JSONArray jsonHoaDonTon = new JSONArray(jsonObject.getString("hoadonton").replace("null", ""));
                        for (int i = 0; i < jsonHoaDonTon.length(); i++) {
                            JSONObject jsonhd = jsonHoaDonTon.getJSONObject(i);
                            CEntityChild entityChild = new CEntityChild();
                            entityChild.setKy(jsonhd.getString("KyHD"));
                            entityChild.setTongCong(jsonhd.getString("TongCong"));
                            CLocal.listDocSoView.get(STT).getLstHoaDon().add(entityChild);
                        }
                    }
                    CLocal.updateTinhTrangParent(CLocal.listDocSo, CLocal.listDocSoView.get(STT));
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
                if (jsonObject != null)
                    CLocal.showPopupMessage(ActivityDocSo_GhiChiSo.this, s + "\r\n" + jsonObject.getString("error").replace("null", ""), "center");
                else
                    CLocal.showPopupMessage(ActivityDocSo_GhiChiSo.this, s , "center");
                ivIn.performClick();
            } catch (JSONException e) {
                CLocal.showPopupMessage(ActivityDocSo_GhiChiSo.this, e.getMessage(), "center");
            }
        }

    }

}