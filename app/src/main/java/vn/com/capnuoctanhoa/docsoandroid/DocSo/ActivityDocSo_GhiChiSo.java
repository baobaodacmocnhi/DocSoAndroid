package vn.com.capnuoctanhoa.docsoandroid.DocSo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vn.com.capnuoctanhoa.docsoandroid.Class.CBitmap;
import vn.com.capnuoctanhoa.docsoandroid.Class.CCode;
import vn.com.capnuoctanhoa.docsoandroid.Class.CEntityParent;
import vn.com.capnuoctanhoa.docsoandroid.Class.CLocal;
import vn.com.capnuoctanhoa.docsoandroid.Class.CMarshMallowPermission;
import vn.com.capnuoctanhoa.docsoandroid.Class.CWebservice;
import vn.com.capnuoctanhoa.docsoandroid.Class.CustomAdapterSpinner;
import vn.com.capnuoctanhoa.docsoandroid.R;

public class ActivityDocSo_GhiChiSo extends AppCompatActivity {
    private CheckBox chkLocDaDoc;
    private TextView txtMLT, txtDanhBo, txtHoTen, txtDiaChi, txtDiaChiDHN, txtViTri, txtHieu, txtCo, txtSoThan, txtGhiChu, txtTinhTrang, txtGiaBieu, txtDinhMuc, txtDinhMucHN, txtDienThoai, txtTBTT, txtChiSo2, txtCode2, txtTieuThu2, txtChiSo1, txtCode1, txtTieuThu1, txtChiSo0, txtCode0, txtTieuThu0, txtChiSoMoi, txtCodeMoi, txtTieuThuMoi;
    private EditText edtChiSo;
    private Spinner spnCode;
    private ArrayList<CCode> spnName_Code;
    private ImageView ivSau;
    private ImageView ivIn;
    private ImageView imgThumb;
    private LinearLayout layoutMoi;
    //    private CCode selectedCode = null;
    private String imgPath;
    private Bitmap imgCapture;
    private CMarshMallowPermission cMarshMallowPermission;
    private CWebservice ws;
    private String _alert;
    //    private ThermalPrinter thermalPrinter;
    private boolean playTinhTieuThu = false;
    private boolean flagQuanLy = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_so_ghi_chi_so);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chkLocDaDoc = (CheckBox) findViewById(R.id.chkLocDaDoc);
        txtMLT = (TextView) findViewById(R.id.txtMLT);
        txtDanhBo = (TextView) findViewById(R.id.txtDanhBo);
        txtHoTen = (TextView) findViewById(R.id.txtHoTen);
        txtDiaChi = (TextView) findViewById(R.id.txtDiaChi);
        txtDiaChiDHN = (TextView) findViewById(R.id.txtDiaChiDHN);
        txtViTri = (TextView) findViewById(R.id.txtViTri);
        txtHieu = (TextView) findViewById(R.id.txtHieu);
        txtCo = (TextView) findViewById(R.id.txtCo);
        txtSoThan = (TextView) findViewById(R.id.txtSoThan);
        txtGhiChu = (TextView) findViewById(R.id.txtGhiChu);
        txtTinhTrang = (TextView) findViewById(R.id.txtTinhTrang);
        txtGiaBieu = (TextView) findViewById(R.id.txtGiaBieu);
        txtDinhMuc = (TextView) findViewById(R.id.txtDinhMuc);
        txtDinhMucHN = (TextView) findViewById(R.id.txtDinhMucHN);
        txtDienThoai = (TextView) findViewById(R.id.txtDienThoai);
        txtTBTT = (TextView) findViewById(R.id.txtTBTT);
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
        LinearLayout layoutLS = (LinearLayout) findViewById(R.id.layoutLS);
        LinearLayout layout2 = (LinearLayout) findViewById(R.id.layout2);
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.layout1);
        LinearLayout layout0 = (LinearLayout) findViewById(R.id.layout0);
        layoutMoi = (LinearLayout) findViewById(R.id.layoutMoi);
        edtChiSo = (EditText) findViewById(R.id.edtChiSo);
        spnCode = (Spinner) findViewById(R.id.spnCode);
        ImageView ivTruoc = (ImageView) findViewById(R.id.ivTruoc);
        ivSau = (ImageView) findViewById(R.id.ivSau);
        ImageView ivGhiChu = (ImageView) findViewById(R.id.ivGhiChu);
        ivIn = (ImageView) findViewById(R.id.ivIn);
        ImageView ivLuu = (ImageView) findViewById(R.id.ivLuu);
        ImageView ivPhieuChuyen = (ImageView) findViewById(R.id.ivPhieuChuyen);
        ImageButton ibtnChupHinh = (ImageButton) findViewById(R.id.ibtnChupHinh);
        Button btnChonHinh = (Button) findViewById(R.id.btnChonHinh);
        imgThumb = (ImageView) findViewById(R.id.imgThumb);

        cMarshMallowPermission = new CMarshMallowPermission(this);
        ws = new CWebservice();
        chkLocDaDoc.setChecked(CLocal.LocDaDoc);
        try {
            if (getIntent().hasExtra("QuanLy"))
                flagQuanLy = Boolean.parseBoolean(getIntent().getStringExtra("QuanLy"));
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
            CustomAdapterSpinner customAdapterSpinner = new CustomAdapterSpinner(this, spnName_Code);
            spnCode.setAdapter(customAdapterSpinner);
            spnCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    selectedCode = (CCode) parent.getItemAtPosition(position);
                    if (!txtChiSoMoi.getText().toString().equals(""))
                        tinhTieuThu();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            layoutLS.setOnLongClickListener(v -> {
                Intent intent = new Intent(ActivityDocSo_GhiChiSo.this, ActivityDocSo_LichSu.class);
                startActivity(intent);
                return false;
            });

            layout2.setOnClickListener(v -> {
                Intent intent = new Intent(ActivityDocSo_GhiChiSo.this, ActivityDocSo_View.class);
                intent.putExtra("DanhBo", CLocal.listDocSoView.get(CLocal.STT).getDanhBo().replace(" ", ""));
                int Nam = Integer.parseInt(CLocal.listDocSoView.get(CLocal.STT).getNam());
                int Ky = Integer.parseInt(CLocal.listDocSoView.get(CLocal.STT).getKy());
                switch (Ky) {
                    case 1:
                        Nam--;
                        Ky = 10;
                        break;
                    case 2:
                        Nam--;
                        Ky = 11;
                        break;
                    case 3:
                        Nam--;
                        Ky = 12;
                        break;
                    default:
                        Ky -= 3;
                        break;
                }
                intent.putExtra("Nam", String.valueOf(Nam));
                intent.putExtra("Ky", (Ky < 10 ? "0" : "") + Ky);
                startActivity(intent);
            });

            layout1.setOnClickListener(v -> {
                Intent intent = new Intent(ActivityDocSo_GhiChiSo.this, ActivityDocSo_View.class);
                intent.putExtra("DanhBo", CLocal.listDocSoView.get(CLocal.STT).getDanhBo().replace(" ", ""));
                int Nam = Integer.parseInt(CLocal.listDocSoView.get(CLocal.STT).getNam());
                int Ky = Integer.parseInt(CLocal.listDocSoView.get(CLocal.STT).getKy());
                switch (Ky) {
                    case 1:
                        Nam--;
                        Ky = 11;
                        break;
                    case 2:
                        Nam--;
                        Ky = 12;
                        break;
                    case 3:
                        Nam--;
                        Ky = 1;
                        break;
                    default:
                        Ky -= 2;
                        break;
                }
                intent.putExtra("Nam", String.valueOf(Nam));
                intent.putExtra("Ky", (Ky < 10 ? "0" : "") + Ky);
                startActivity(intent);
            });

            layout0.setOnClickListener(v -> {
                Intent intent = new Intent(ActivityDocSo_GhiChiSo.this, ActivityDocSo_View.class);
                intent.putExtra("DanhBo", CLocal.listDocSoView.get(CLocal.STT).getDanhBo().replace(" ", ""));
                int Nam = Integer.parseInt(CLocal.listDocSoView.get(CLocal.STT).getNam());
                int Ky = Integer.parseInt(CLocal.listDocSoView.get(CLocal.STT).getKy());
                switch (Ky) {
                    case 1:
                        Nam--;
                        Ky = 12;
                        break;
                    case 2:
                        Nam--;
                        Ky = 1;
                        break;
                    case 3:
                        Nam--;
                        Ky = 2;
                        break;
                    default:
                        Ky--;
                        break;
                }
                intent.putExtra("Nam", String.valueOf(Nam));
                intent.putExtra("Ky", (Ky < 10 ? "0" : "") + Ky);
                startActivity(intent);
            });

            layoutMoi.setOnClickListener(v -> {
                Intent intent = new Intent(ActivityDocSo_GhiChiSo.this, ActivityDocSo_View.class);
                intent.putExtra("DanhBo", CLocal.listDocSoView.get(CLocal.STT).getDanhBo().replace(" ", ""));
                intent.putExtra("Nam", CLocal.listDocSoView.get(CLocal.STT).getNam());
                intent.putExtra("Ky", CLocal.listDocSoView.get(CLocal.STT).getKy());
                startActivity(intent);
            });

            chkLocDaDoc.setOnCheckedChangeListener((buttonView, isChecked) -> CLocal.LocDaDoc = isChecked);

            ivTruoc.setOnClickListener(v -> {
                if (CLocal.STT > 0) {
                    CLocal.STT--;
                    if (chkLocDaDoc.isChecked())
                        while (CLocal.STT > 0 && !CLocal.listDocSoView.get(CLocal.STT).getCodeMoi().equals("") && CLocal.listDocSoView.get(CLocal.STT).getCodeMoi().charAt(0) != 'F')
                            CLocal.STT--;
                    initial();
                    fillLayout(CLocal.listDocSoView.get(CLocal.STT));
                } else
                    CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, "Đầu Danh Sách");
            });

            ivSau.setOnClickListener(v -> {
                if (CLocal.STT < CLocal.listDocSoView.size() - 1) {
                    CLocal.STT++;
                    if (chkLocDaDoc.isChecked())
                        while (CLocal.STT < CLocal.listDocSoView.size() - 1 && !CLocal.listDocSoView.get(CLocal.STT).getCodeMoi().equals("") && CLocal.listDocSoView.get(CLocal.STT).getCodeMoi().charAt(0) != 'F')
                            CLocal.STT++;
                    initial();
                    fillLayout(CLocal.listDocSoView.get(CLocal.STT));
                } else
                    CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, "Cuối Danh Sách");
            });

            ibtnChupHinh.setOnClickListener(view -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!cMarshMallowPermission.checkPermissionForExternalStorage()) {
                        cMarshMallowPermission.requestPermissionForExternalStorage();
                    }
                    if (!cMarshMallowPermission.checkPermissionForExternalStorage())
                        return;
                }
                imgCapture = null;
                Uri imgUri = createImageUri();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(ActivityDocSo_GhiChiSo.this.getPackageManager()) != null) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri); // put uri file khi mà mình muốn lưu ảnh sau khi chụp như thế nào  ?
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    activityResultLauncher_ChupHinh.launch(intent);
                }
            });

            btnChonHinh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (!cMarshMallowPermission.checkPermissionForExternalStorage()) {
                            cMarshMallowPermission.requestPermissionForExternalStorage();
                        }
                        if (!cMarshMallowPermission.checkPermissionForExternalStorage())
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
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        activityResultLauncher_ChonHinh.launch(intent);
                    }
                }
            });

            imgThumb.setOnClickListener(v -> CLocal.showImgThumb(ActivityDocSo_GhiChiSo.this, imgCapture));

            edtChiSo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    tinhTieuThu();
                }
            });

            ivLuu.setOnClickListener(view -> {
                try {
                    if (!CLocal.checkNetworkAvailable(ActivityDocSo_GhiChiSo.this)) {
                        CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, "Không có Internet");
                        return;
                    }
                    if (CLocal.listDocSoView.get(CLocal.STT).isChuBao()) {
                        CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, "Chủ Báo - Không cập nhật");
                        return;
                    }
                    CCode selectedCode = (CCode) spnCode.getSelectedItem();
                    if (imgCapture != null && selectedCode != null
                            && ((selectedCode.getCode().equals("F1")
                            || selectedCode.getCode().equals("F2")
                            || selectedCode.getCode().equals("F3")
                            || selectedCode.getCode().equals("F4")
                            || selectedCode.getCode().equals("61")
                            || selectedCode.getCode().equals("63")
                            || selectedCode.getCode().equals("64")
                            || selectedCode.getCode().equals("66")
                            || selectedCode.getCode().equals("68")
                            || selectedCode.getCode().equals("K"))
                            || (!selectedCode.getCode().equals("F1")
                            && !selectedCode.getCode().equals("F2")
                            && !selectedCode.getCode().equals("F3")
                            && !selectedCode.getCode().equals("F4")
                            && !selectedCode.getCode().equals("61")
                            && !selectedCode.getCode().equals("63")
                            && !selectedCode.getCode().equals("64")
                            && !selectedCode.getCode().equals("66")
                            && !selectedCode.getCode().equals("68")
                            && !selectedCode.getCode().equals("K")
                            && !edtChiSo.getText().toString().equals("")))) {
                        if ((CLocal.listDocSoView.get(CLocal.STT).getCode0().charAt(0) == 'K' && CLocal.listDocSoView.get(CLocal.STT).getCode0().charAt(0) == '5' && !selectedCode.getCode().equals("5K"))
                                || (CLocal.listDocSoView.get(CLocal.STT).getCode0().charAt(0) == 'F' && CLocal.listDocSoView.get(CLocal.STT).getCode0().charAt(0) == '5' && !selectedCode.getCode().equals("5F"))
                                || (CLocal.listDocSoView.get(CLocal.STT).getCode0().charAt(0) == 'N' && CLocal.listDocSoView.get(CLocal.STT).getCode0().charAt(0) == '5' && !selectedCode.getCode().equals("5N"))
                                || (CLocal.listDocSoView.get(CLocal.STT).getCode0().charAt(0) == '4' && (selectedCode.getCode().equals("5F") || selectedCode.getCode().equals("5K") || selectedCode.getCode().equals("5N")))
                                || (CLocal.listDocSoView.get(CLocal.STT).getCode0() == "M0" && selectedCode.getCode().charAt(0) == '4')) {
                            CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, "Vào Code Sai");
                            return;
                        }
                        if (CLocal.getDaysDifference(CLocal.convertStringToDate(CLocal.listDocSoView.get(CLocal.STT).getDenNgay()), new Date()) < 2
                                && (CLocal.lstTT0.contains(selectedCode.getCode())
                                || CLocal.lstTBTT.contains(selectedCode.getCode())
                                || CLocal.lstBinhThuong.contains(selectedCode.getCode()))) {
                            CLocal.listDocSoView.get(CLocal.STT).setSync(false);
                            if (edtChiSo.getText().toString().equals(""))
                                tinhTieuThu();
                            CLocal.listDocSoView.get(CLocal.STT).setModifyDate(CLocal.DateFormat.format(new Date()));
                            CLocal.listDocSoView.get(CLocal.STT).setCodeMoi(txtCodeMoi.getText().toString());
                            CLocal.listDocSoView.get(CLocal.STT).setChiSoMoi(txtChiSoMoi.getText().toString());
                            CLocal.listDocSoView.get(CLocal.STT).setTieuThuMoi(txtTieuThuMoi.getText().toString());
                            ArrayList<Integer> lstTienNuoc = CLocal.TinhTienNuoc(CLocal.listDocSoView.get(CLocal.STT).getDanhBo().replace(" ", ""), Integer.parseInt(CLocal.listDocSoView.get(CLocal.STT).getKy())
                                    , Integer.parseInt(CLocal.listDocSoView.get(CLocal.STT).getNam()), CLocal.listDocSoView.get(CLocal.STT).getTuNgay(), CLocal.listDocSoView.get(CLocal.STT).getDenNgay(), Integer.parseInt(CLocal.listDocSoView.get(CLocal.STT).getGiaBieu())
                                    , CLocal.listDocSoView.get(CLocal.STT).getSH(), CLocal.listDocSoView.get(CLocal.STT).getSX(), CLocal.listDocSoView.get(CLocal.STT).getDV(), CLocal.listDocSoView.get(CLocal.STT).getHCSN()
                                    , Integer.parseInt(CLocal.listDocSoView.get(CLocal.STT).getDinhMuc()), Integer.parseInt(CLocal.listDocSoView.get(CLocal.STT).getDinhMucHN()), Integer.parseInt(CLocal.listDocSoView.get(CLocal.STT).getTieuThuMoi()));
                            CLocal.listDocSoView.get(CLocal.STT).setTienNuoc(lstTienNuoc.get(0).toString());
                            CLocal.listDocSoView.get(CLocal.STT).setThueGTGT(lstTienNuoc.get(1).toString());
                            CLocal.listDocSoView.get(CLocal.STT).setPhiBVMT(lstTienNuoc.get(2).toString());
                            CLocal.listDocSoView.get(CLocal.STT).setPhiBVMT_Thue(lstTienNuoc.get(3).toString());
                            CLocal.listDocSoView.get(CLocal.STT).setTongCong(lstTienNuoc.get(4).toString());
                            CLocal.updateTinhTrangParent(CLocal.listDocSo, CLocal.listDocSoView.get(CLocal.STT));
                            MyAsyncTaskGhiDocSo_GianTiep myAsyncTaskGhiDocSo_gianTiep = new MyAsyncTaskGhiDocSo_GianTiep();
                            myAsyncTaskGhiDocSo_gianTiep.execute(String.valueOf(CLocal.STT));
                            //thành công có cảnh báo
                            if (!_alert.equals("")) {
                                CLocal.vibrate(ActivityDocSo_GhiChiSo.this);
                                if (_alert.contains("= 0"))
                                    CLocal.showDialog(ActivityDocSo_GhiChiSo.this, "Thông Báo", "THÀNH CÔNG\r\n" + _alert, "Đọc Tiếp", (dialog, which) -> {
                                        dialog.dismiss();
                                        ivSau.performClick();
                                    }, "In", (dialog, which) -> {
                                        dialog.dismiss();
                                        ivIn.performClick();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(() -> ivSau.performClick(), 1000);
                                    }, false);
                                else
                                    CLocal.showDialog(ActivityDocSo_GhiChiSo.this, "Thông Báo", "THÀNH CÔNG\r\n\r\n" + _alert, "Xem Lại", (dialog, which) -> dialog.dismiss(), "In", (dialog, which) -> {
                                        dialog.dismiss();
                                        ivIn.performClick();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(() -> ivSau.performClick(), 1000);
                                    }, false);
                            } else {//thành công không có cảnh báo
//                                    CLocal.showPopupMessage(ActivityDocSo_GhiChiSo.this, "THÀNH CÔNG\r\n\r\n", "center");
                                ivIn.performClick();
                                final Handler handler = new Handler();
                                handler.postDelayed(() -> ivSau.performClick(), 1000);
                            }
                        } else {
                            MyAsyncTask_TrucTiep myAsyncTaskTrucTiep = new MyAsyncTask_TrucTiep();
                            myAsyncTaskTrucTiep.execute();
                        }
                        CLocal.hideKeyboard(ActivityDocSo_GhiChiSo.this);
                    } else {
                        CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, "Thiếu dữ liệu Code-CSM-Hình ảnh");
                    }
                } catch (Exception e) {
                    CLocal.showPopupMessage(ActivityDocSo_GhiChiSo.this, "THẤT BẠI\r\n\r\n" + e.getMessage(), "center");
                    CLocal.vibrate(ActivityDocSo_GhiChiSo.this);
                    CLocal.showPopupMessage(ActivityDocSo_GhiChiSo.this, e.getMessage(), "center");
                }
            });

            ivIn.setOnClickListener(view -> {
                try {
                    if (!CLocal.listDocSoView.get(CLocal.STT).getCodeMoi().equals("")) {
//                            if (thermalPrinter != null) {
//                                thermalPrinter.printGhiChiSo(CLocal.listDocSoView.get(CLocal.STT));
//                            }
                        if (CLocal.serviceThermalPrinter != null) {
                            CLocal.serviceThermalPrinter.printGhiChiSo(CLocal.listDocSoView.get(CLocal.STT));
                        }
                    } else {
                        CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, "Chưa có dữ liệu In");
                    }
                } catch (Exception ex) {
                    CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, ex.getMessage());
                }
            });

            ivGhiChu.setOnClickListener(view -> {
                if (!CLocal.checkNetworkAvailable(ActivityDocSo_GhiChiSo.this)) {
                    CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, "Không có Internet");
                    return;
                }
                Intent intent = new Intent(ActivityDocSo_GhiChiSo.this, ActivityDocSo_GhiChu.class);
                startActivity(intent);
            });

            ivPhieuChuyen.setOnClickListener(view -> {
                if (!CLocal.checkNetworkAvailable(ActivityDocSo_GhiChiSo.this)) {
                    CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, "Không có Internet");
                    return;
                }
                Intent intent = new Intent(ActivityDocSo_GhiChiSo.this, ActivityDocSo_PhieuChuyen.class);
                startActivity(intent);
            });

            if (CLocal.STT > -1) {
                initial();
                fillLayout(CLocal.listDocSoView.get(CLocal.STT));
            }

            if (savedInstanceState != null) {
                imgCapture = CLocal.imgCapture;
                imgThumb.setImageBitmap(imgCapture);
            }

        } catch (Exception ex) {
            CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, ex.getMessage());
        }

//        try {
//            if (CLocal.ThermalPrinter == null || CLocal.ThermalPrinter.equals("") == true) {
//                CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, "Chưa Cấu Hình Máy In");
//            } else {
////                thermalPrinter = new ThermalPrinter(ActivityDocSo_GhiChiSo.this);
////                if (thermalPrinter != null) {
////                    thermalPrinter.findBluetoothDevice();
////                    thermalPrinter.openBluetoothPrinter();
////                }
//                MyAsyncTask_ConnectPrinter myAsyncTask_connectPrinter = new MyAsyncTask_ConnectPrinter();
//                myAsyncTask_connectPrinter.execute();
//            }
//        } catch (Exception ex) {
//            CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, ex.getMessage());
//        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        CLocal.imgCapture = imgCapture;
    }

    private void initial() {
        imgCapture = null;
        spnCode.setSelection(0);
//        selectedCode = (CCode) spnCode.getItemAtPosition(0);
        edtChiSo.setText("");
        imgThumb.setImageBitmap(imgCapture);
        //
        txtMLT.setText("");
        txtDanhBo.setText("");
        txtHoTen.setText("");
        txtDiaChi.setText("");
        txtDiaChiDHN.setText("");
        txtViTri.setText("");
        txtHieu.setText("");
        txtCo.setText("");
        txtSoThan.setText("");
        txtDienThoai.setText("");
        txtGiaBieu.setText("");
        txtDinhMuc.setText("");
        txtDinhMucHN.setText("");
        txtTBTT.setText("");
        txtChiSo2.setText("");
        txtCode2.setText("");
        txtTieuThu2.setText("");
        txtChiSo1.setText("");
        txtCode1.setText("");
        txtTieuThu1.setText("");
        txtChiSo0.setText("");
        txtCode0.setText("");
        txtTieuThu0.setText("");
        txtChiSoMoi.setText("");
        txtCodeMoi.setText("");
        txtTieuThuMoi.setText("");
        _alert = "";
    }

    private void selectValue(String value) {
        for (int i = 0; i < spnCode.getCount(); i++) {
            CCode code = (CCode) spnCode.getItemAtPosition(i);
            if (code.getCode().equals(value)) {
                spnCode.setSelection(i);
//                selectedCode = (CCode) spnCode.getItemAtPosition(i);
                break;
            }
        }
    }

    private void fillLayout(CEntityParent entityParent) {
        try {
            if (entityParent != null) {
                layoutMoi.setBackgroundColor(getResources().getColor(R.color.colorCSC_SL_0_1));
                txtMLT.setText(entityParent.getMLT());
                txtDanhBo.setText(entityParent.getDanhBo());
                txtHoTen.setText(entityParent.getHoTen());
                txtDiaChi.setText(entityParent.getDiaChi());
                txtDiaChiDHN.setText(entityParent.getSoNha() + " " + entityParent.getTenDuong());
                String str = "";
                if (entityParent.isViTriNgoai())
                    str += "Ngoài";
                if (entityParent.isViTriHop())
                    if (str.equals(""))
                        str += "Hộp";
                    else
                        str += "-Hộp";
                if (!entityParent.getViTri().equals(""))
                    if (str.equals(""))
                        str += entityParent.getViTri();
                    else
                        str += "-" + entityParent.getViTri();
                txtViTri.setText(str);
                txtHieu.setText(entityParent.getHieu());
                txtCo.setText(entityParent.getCo());
                txtSoThan.setText(entityParent.getSoThan());
                txtGhiChu.setText(entityParent.getGhiChu());
                txtTinhTrang.setText(entityParent.getTinhTrang());
                txtDienThoai.setText(entityParent.getDienThoai());
                txtGiaBieu.setText(entityParent.getGiaBieu());
                txtDinhMuc.setText(entityParent.getDinhMuc());
                txtDinhMucHN.setText(entityParent.getDinhMucHN());
                txtTBTT.setText(entityParent.getTBTT());
                txtChiSo2.setText(entityParent.getChiSo2());
                txtCode2.setText(entityParent.getCode2());
                txtTieuThu2.setText(entityParent.getTieuThu2());
                txtChiSo1.setText(entityParent.getChiSo1());
                txtCode1.setText(entityParent.getCode1());
                txtTieuThu1.setText(entityParent.getTieuThu1());
                txtChiSo0.setText(entityParent.getChiSo0());
                txtCode0.setText(entityParent.getCode0());
                txtTieuThu0.setText(entityParent.getTieuThu0());
                txtChiSoMoi.setText(entityParent.getChiSoMoi());
                txtCodeMoi.setText(entityParent.getCodeMoi());
                txtTieuThuMoi.setText(entityParent.getTieuThuMoi());
                tinhTieuThu_CanhBaoMau();
                if (!entityParent.getCodeMoi().equals("") && !entityParent.getChiSoMoi().equals("")) {
                    selectValue(entityParent.getCodeMoi());
                    edtChiSo.setText(entityParent.getChiSoMoi());
                }
                Bitmap bitmap = BitmapFactory.decodeFile(CLocal.pathAppPicture + "/" + entityParent.getNam() + "_" + entityParent.getKy() + "_" + entityParent.getDot() + "/" + entityParent.getDanhBo().replace(" ", "") + ".jpg");
                if (bitmap != null) {
                    bitmap = CBitmap.imageOreintationValidator(bitmap, CLocal.pathAppPicture + "/" + entityParent.getNam() + "_" + entityParent.getKy() + "_" + entityParent.getDot() + "/" + entityParent.getDanhBo().replace(" ", "") + ".jpg");
                    imgCapture = bitmap;
                    imgThumb.setImageBitmap(imgCapture);
                }
                playTinhTieuThu = true;
            }
        } catch (
                Exception ex) {
            CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, ex.getMessage());
        }
    }

    private void fillLayoutReLoad(CEntityParent entityParent) {
        try {
            if (entityParent != null) {
                txtDiaChiDHN.setText(entityParent.getSoNha() + " " + entityParent.getTenDuong());
                String str = "";
                if (entityParent.isViTriNgoai())
                    str += "Ngoài";
                if (entityParent.isViTriHop())
                    if (str.equals(""))
                        str += "Hộp";
                    else
                        str += "-Hộp";
                if (!entityParent.getViTri().equals(""))
                    if (str.equals(""))
                        str += entityParent.getViTri();
                    else
                        str += "-" + entityParent.getViTri();
                txtViTri.setText(str);
                txtDienThoai.setText(entityParent.getDienThoai());
                txtGhiChu.setText(entityParent.getGhiChu());
            }
        } catch (
                Exception ex) {
            CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, ex.getMessage());
        }
    }

    public void tinhTieuThu() {
        if (playTinhTieuThu) {
            String strChiSo = "";
            if (edtChiSo.getText().toString().equals(""))
                strChiSo = "0";
            else
                strChiSo = edtChiSo.getText().toString();
            txtCodeMoi.setText(((CCode) spnCode.getSelectedItem()).getCode());
            txtChiSoMoi.setText(strChiSo);
            if (CLocal.lstTT0.contains(txtCodeMoi.getText().toString()))
                txtTieuThuMoi.setText("0");
            else if (CLocal.lstTBTT.contains(txtCodeMoi.getText().toString()))
                txtTieuThuMoi.setText(CLocal.listDocSoView.get(CLocal.STT).getTBTT());
            else if (CLocal.lstBinhThuong.contains(txtCodeMoi.getText().toString()))
                if (txtCodeMoi.getText().toString().equals("X41"))
                    txtTieuThuMoi.setText(String.valueOf(10000 + Integer.parseInt(txtChiSoMoi.getText().toString()) - Integer.parseInt(CLocal.listDocSoView.get(CLocal.STT).getChiSo0())));
                else if (txtCodeMoi.getText().toString().equals("X51"))
                    txtTieuThuMoi.setText(String.valueOf(100000 + Integer.parseInt(txtChiSoMoi.getText().toString()) - Integer.parseInt(CLocal.listDocSoView.get(CLocal.STT).getChiSo0())));
                else
                    txtTieuThuMoi.setText(String.valueOf(Integer.parseInt(txtChiSoMoi.getText().toString()) - Integer.parseInt(CLocal.listDocSoView.get(CLocal.STT).getChiSo0())));
            if (txtCodeMoi.getText().toString().charAt(0) == '4' == true
                    && (CLocal.listDocSoView.get(CLocal.STT).getCode0().charAt(0) == 'F'
                    || CLocal.listDocSoView.get(CLocal.STT).getCode0().charAt(0) == 'K'
                    || CLocal.listDocSoView.get(CLocal.STT).getCode0().charAt(0) == 'N'
                    || (CLocal.listDocSoView.get(CLocal.STT).getCode0().charAt(0) == '6' && !CLocal.listDocSoView.get(CLocal.STT).getCode0().equals("68"))))
                txtCodeMoi.setText("5" + CLocal.listDocSoView.get(CLocal.STT).getCode0().charAt(0));
            if (txtCodeMoi.getText().toString().charAt(0) == 'F' || txtCodeMoi.getText().toString().equals("61"))
                txtChiSoMoi.setText(String.valueOf((Integer.parseInt(CLocal.listDocSoView.get(CLocal.STT).getChiSo0()) + Integer.parseInt(CLocal.listDocSoView.get(CLocal.STT).getTBTT()))));
            else if (txtCodeMoi.getText().toString().charAt(0) == 'K')
                txtChiSoMoi.setText(CLocal.listDocSoView.get(CLocal.STT).getChiSo0());
            else if (txtCodeMoi.getText().toString().equals("63"))
                txtChiSoMoi.setText("0");
            if (txtCode0.getText().equals("M0"))
                txtCodeMoi.setText("M1");
            tinhTieuThu_CanhBaoMau();
        }
    }

    public void tinhTieuThu_CanhBaoMau() {
        if (!txtTieuThuMoi.getText().toString().equals("")) {
            if (Integer.parseInt(txtTieuThuMoi.getText().toString()) < 0) {
                _alert = "Tiêu Thụ âm = " + txtTieuThuMoi.getText().toString();
                layoutMoi.setBackgroundColor(getResources().getColor(R.color.colorDanhBo));
            } else if (Integer.parseInt(txtTieuThuMoi.getText().toString()) == 0) {
                _alert = "Tiêu Thụ = " + txtTieuThuMoi.getText().toString();
                layoutMoi.setBackgroundColor(getResources().getColor(R.color.colorDanhBo));
            } else if (Integer.parseInt(txtTieuThuMoi.getText().toString()) <= Integer.parseInt(CLocal.listDocSoView.get(CLocal.STT).getTBTT()) * (1 - 0.3)
                    || Integer.parseInt(txtTieuThuMoi.getText().toString()) >= Integer.parseInt(CLocal.listDocSoView.get(CLocal.STT).getTBTT()) * 1.3) {
                _alert = "Tiêu Thụ bất thường = " + txtTieuThuMoi.getText().toString();
                layoutMoi.setBackgroundColor(getResources().getColor(R.color.colorDanhBo));
            } else {
                _alert = "";
                layoutMoi.setBackgroundColor(getResources().getColor(R.color.colorCSC_SL_0_1));
            }
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
                    try {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            if (imgPath != null && !imgPath.equals("")) {
                                Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
                                bitmap = CBitmap.imageOreintationValidator(bitmap, imgPath);
                                imgCapture = CBitmap.scale(bitmap, 1024);
                                imgThumb.setImageBitmap(imgCapture);
                                CLocal.writeFile(CLocal.pathAppPicture + "/" + CLocal.listDocSoView.get(CLocal.STT).getNam() + "_" + CLocal.listDocSoView.get(CLocal.STT).getKy() + "_" + CLocal.listDocSoView.get(CLocal.STT).getDot()
                                        , CLocal.listDocSoView.get(CLocal.STT).getDanhBo().replace(" ", "") + ".jpg", imgCapture);
                            }
                        }
                    } catch (Exception ex) {
                        CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, ex.getMessage());
                    }
                }
            });

    ActivityResultLauncher<Intent> activityResultLauncher_ChonHinh = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    try {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null && result.getData().getData() != null) {
                            Uri uri = result.getData().getData();
                            String strPath = CLocal.getPathFromUri(ActivityDocSo_GhiChiSo.this, uri);
                            Bitmap bitmap = BitmapFactory.decodeFile(strPath);
                            bitmap = CBitmap.imageOreintationValidator(bitmap, strPath);
                            imgCapture = CBitmap.scale(bitmap, 1024);
                            imgThumb.setImageBitmap(imgCapture);
                            CLocal.writeFile(CLocal.pathAppPicture + "/" + CLocal.listDocSoView.get(CLocal.STT).getNam() + "_" + CLocal.listDocSoView.get(CLocal.STT).getKy() + "_" + CLocal.listDocSoView.get(CLocal.STT).getDot()
                                    , CLocal.listDocSoView.get(CLocal.STT).getDanhBo().replace(" ", "") + ".jpg", imgCapture);
                        }
                    } catch (Exception ex) {
                        CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, ex.getMessage());
                    }
                }
            });

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (CLocal.STT > -1) {
                fillLayoutReLoad(CLocal.listDocSoView.get(CLocal.STT));
            }
        } catch (Exception ex) {
            CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, ex.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!flagQuanLy) {
            MyAsyncTaskGhiHinhAll myAsyncTaskGhiHinhAll = new MyAsyncTaskGhiHinhAll();
            myAsyncTaskGhiHinhAll.execute();
            MyAsyncTaskGhiDocSo_GianTiepALL myAsyncTaskGhiDocSo_gianTiepALL = new MyAsyncTaskGhiDocSo_GianTiepALL();
            myAsyncTaskGhiDocSo_gianTiepALL.execute();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (!flagQuanLy) {
                CLocal.ghiListToFileDocSo();
            }
        } catch (Exception ex) {
            CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, ex.getMessage());
        }
//        if (thermalPrinter != null)
//            thermalPrinter.disconnectBluetoothDevice();
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
                uri = FileProvider.getUriForFile(this, "docso_file_provider", photoFile);
            }
            imgPath = photoFile.getAbsolutePath();
            return uri;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public class MyAsyncTask_TrucTiep extends AsyncTask<String, String, String> {
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
                if (imgCapture != null && imgString.equals("")) {
                    imgString = CBitmap.convertBitmapToString(imgCapture);
                }
                CCode selectedCode = (CCode) spnCode.getSelectedItem();
                CLocal.listDocSoView.get(CLocal.STT).setModifyDate(CLocal.DateFormat.format(new Date()));
                String result = ws.ghiChiSo(CLocal.listDocSoView.get(CLocal.STT).getID(), selectedCode.getCode(), edtChiSo.getText().toString(), "", CLocal.listDocSoView.get(CLocal.STT).getDot(), CLocal.May, CLocal.listDocSoView.get(CLocal.STT).getTBTT());
                if (!result.equals(""))
                    jsonObject = new JSONObject(result);
                if (jsonObject != null && Boolean.parseBoolean(jsonObject.getString("success").replace("null", ""))) {
                    CLocal.listDocSoView.get(CLocal.STT).setSync(true);
                    CLocal.listDocSoView.get(CLocal.STT).setCodeMoi(selectedCode.getCode());
                    CLocal.listDocSoView.get(CLocal.STT).setChiSoMoi(edtChiSo.getText().toString());
                    JSONObject jsonObjectC = new JSONObject(jsonObject.getString("message").replace("null", ""));
                    if (!jsonObjectC.getString("CSC").replace("null", "").equals(""))
                        CLocal.listDocSoView.get(CLocal.STT).setChiSo0(jsonObjectC.getString("CSC").replace("null", ""));
                    CLocal.listDocSoView.get(CLocal.STT).setTieuThuMoi(jsonObjectC.getString("TieuThu").replace("null", ""));
                    CLocal.listDocSoView.get(CLocal.STT).setTienNuoc(jsonObjectC.getString("TienNuoc").replace("null", ""));
                    CLocal.listDocSoView.get(CLocal.STT).setThueGTGT(jsonObjectC.getString("ThueGTGT").replace("null", ""));
                    CLocal.listDocSoView.get(CLocal.STT).setPhiBVMT(jsonObjectC.getString("PhiBVMT").replace("null", ""));
                    CLocal.listDocSoView.get(CLocal.STT).setPhiBVMT_Thue(jsonObjectC.getString("PhiBVMT_Thue").replace("null", ""));
                    CLocal.listDocSoView.get(CLocal.STT).setTongCong(jsonObjectC.getString("TongCong").replace("null", ""));
                    CLocal.updateTinhTrangParent(CLocal.listDocSo, CLocal.listDocSoView.get(CLocal.STT));
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
                if (jsonObject != null) {
                    String error = "", alert = "";
                    if (!jsonObject.getString("error").replace("null", "").equals(""))
                        error = "\r\n" + jsonObject.getString("error").replace("null", "");
                    if (!jsonObject.getString("alert").replace("null", "").equals(""))
                        alert = "\r\n" + jsonObject.getString("alert").replace("null", "");
                    //báo thành công
                    if (Boolean.parseBoolean(jsonObject.getString("success").replace("null", ""))) {
                        if (!imgString.equals("")) {
                            MyAsyncTaskGhiHinh myAsyncTaskGhiHinh = new MyAsyncTaskGhiHinh();
                            myAsyncTaskGhiHinh.execute(CLocal.listDocSoView.get(CLocal.STT).getID(), imgString);
                        }
                        //thành công có cảnh báo
                        if (!alert.equals("")) {
                            CLocal.vibrate(ActivityDocSo_GhiChiSo.this);
                            if (alert.contains("= 0"))
                                CLocal.showDialog(ActivityDocSo_GhiChiSo.this, "Thông Báo", s + alert, "Đọc Tiếp", (dialog, which) -> {
                                    dialog.dismiss();
                                    ivSau.performClick();
                                }, "In", (dialog, which) -> {
                                    dialog.dismiss();
                                    ivIn.performClick();
                                    final Handler handler = new Handler();
                                    handler.postDelayed(() -> ivSau.performClick(), 1000);
                                }, false);
                            else
                                CLocal.showDialog(ActivityDocSo_GhiChiSo.this, "Thông Báo", s + alert, "Xem Lại", (dialog, which) -> {
                                    dialog.dismiss();
//                                    ivSau.performClick();
                                }, "In", (dialog, which) -> {
                                    dialog.dismiss();
                                    ivIn.performClick();
                                    final Handler handler = new Handler();
                                    handler.postDelayed(() -> ivSau.performClick(), 1000);
                                }, false);
                        } else {//thành công không có cảnh báo
//                            CLocal.showPopupMessage(ActivityDocSo_GhiChiSo.this, s, "center");
                            ivIn.performClick();
                            final Handler handler = new Handler();
                            handler.postDelayed(() -> ivSau.performClick(), 1000);
                        }
                    } else {//thất bại
                        CLocal.showPopupMessage(ActivityDocSo_GhiChiSo.this, s + error, "center");
                        CLocal.vibrate(ActivityDocSo_GhiChiSo.this);
                    }
                } else
                    CLocal.showPopupMessage(ActivityDocSo_GhiChiSo.this, s, "center");
            } catch (Exception e) {
                CLocal.showPopupMessage(ActivityDocSo_GhiChiSo.this, e.getMessage(), "center");
            }
        }
    }

    public class MyAsyncTaskGhiHinh extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String error = "";
            try {
                String result = ws.ghi_Hinh(strings[0], strings[1]);
                if (Boolean.parseBoolean(result)) {
                    for (int i = 0; i < CLocal.listDocSoView.size(); i++)
                        if (CLocal.listDocSoView.get(i).getID().equals(strings[0])) {
                            CLocal.listDocSoView.get(i).setGhiHinh(true);
                            CLocal.updateTinhTrangParent(CLocal.listDocSo, CLocal.listDocSoView.get(i));
                        }
                }
            } catch (Exception ex) {
                error = ex.getMessage();
            }
            return error;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.equals(""))
                CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, s);
        }
    }

    public class MyAsyncTaskGhiHinhAll extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String error = "";
            try {
                for (int i = 0; i < CLocal.listDocSoView.size(); i++)
                    if (!CLocal.listDocSoView.get(i).getCodeMoi().equals("")
                            && CLocal.listDocSoView.get(i).isSync()
                            && !CLocal.listDocSoView.get(i).isGhiHinh()) {
                        Bitmap bitmap = BitmapFactory.decodeFile(CLocal.pathAppPicture + "/" + CLocal.listDocSoView.get(i).getNam() + "_" + CLocal.listDocSoView.get(i).getKy() + "_" + CLocal.listDocSoView.get(i).getDot() + "/" + CLocal.listDocSoView.get(i).getDanhBo().replace(" ", "") + ".jpg");
                        if (bitmap != null) {
                            String result = ws.ghi_Hinh(CLocal.listDocSoView.get(i).getID(), CBitmap.convertBitmapToString(bitmap));
                            if (Boolean.parseBoolean(result)) {
                                CLocal.listDocSoView.get(i).setGhiHinh(true);
                                CLocal.updateTinhTrangParent(CLocal.listDocSo, CLocal.listDocSoView.get(i));
                            }
                        }
                    }
            } catch (Exception ex) {
                error = ex.getMessage();
            }
            return error;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.equals(""))
                CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, s);
        }
    }

    public class MyAsyncTaskGhiDocSo_GianTiep extends AsyncTask<String, Void, String> {
        Integer index;

        @Override
        protected String doInBackground(String... strings) {
            String error = "";
            try {
                JSONObject jsonObject = null;
                index = Integer.parseInt(strings[0]);
                if (!CLocal.listDocSoView.get(index).getCodeMoi().equals("")
                        && !CLocal.listDocSoView.get(index).isSync()) {
                    String result = ws.ghiChiSo_GianTiep(CLocal.listDocSoView.get(index).getID(), CLocal.listDocSoView.get(index).getCodeMoi(), CLocal.listDocSoView.get(index).getChiSoMoi(), CLocal.listDocSoView.get(index).getTieuThuMoi()
                            , CLocal.listDocSoView.get(index).getTienNuoc(), CLocal.listDocSoView.get(index).getThueGTGT(), CLocal.listDocSoView.get(index).getPhiBVMT(), CLocal.listDocSoView.get(index).getPhiBVMT_Thue(), CLocal.listDocSoView.get(index).getTongCong(),
                            "", CLocal.listDocSoView.get(index).getDot(), CLocal.May, CLocal.listDocSoView.get(index).getModifyDate());
                    if (!result.equals(""))
                        jsonObject = new JSONObject(result);
                    if (jsonObject != null && Boolean.parseBoolean(jsonObject.getString("success").replace("null", ""))) {
                        CLocal.listDocSoView.get(index).setSync(true);
                        Bitmap bitmap = BitmapFactory.decodeFile(CLocal.pathAppPicture + "/" + CLocal.listDocSoView.get(index).getNam() + "_" + CLocal.listDocSoView.get(index).getKy() + "_" + CLocal.listDocSoView.get(index).getDot() + "/" + CLocal.listDocSoView.get(index).getDanhBo().replace(" ", "") + ".jpg");
                        if (bitmap != null) {
                            String result2 = ws.ghi_Hinh(CLocal.listDocSoView.get(index).getID(), CBitmap.convertBitmapToString(bitmap));
                            if (Boolean.parseBoolean(result2)) {
                                CLocal.listDocSoView.get(index).setGhiHinh(true);
                            }
                        }
                    }
                    CLocal.updateTinhTrangParent(CLocal.listDocSo, CLocal.listDocSoView.get(index));
                }
            } catch (Exception ex) {
                error = ex.getMessage();
            }
            return error;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.equals(""))
                CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, s);
        }
    }

    public class MyAsyncTaskGhiDocSo_GianTiepALL extends AsyncTask<String, Void, String> {
        Integer index;

        @Override
        protected String doInBackground(String... strings) {
            String error = "";
            try {
                JSONObject jsonObject = null;
                for (int i = 0; i < CLocal.listDocSoView.size(); i++)
                    if (!CLocal.listDocSoView.get(i).getCodeMoi().equals(""))
                        if (!CLocal.listDocSoView.get(i).isSync()) {
                            index = i;
                            String result = ws.ghiChiSo_GianTiep(CLocal.listDocSoView.get(i).getID(), CLocal.listDocSoView.get(i).getCodeMoi(), CLocal.listDocSoView.get(i).getChiSoMoi(), CLocal.listDocSoView.get(i).getTieuThuMoi()
                                    , CLocal.listDocSoView.get(i).getTienNuoc(), CLocal.listDocSoView.get(i).getThueGTGT(), CLocal.listDocSoView.get(i).getPhiBVMT(), CLocal.listDocSoView.get(i).getPhiBVMT_Thue(), CLocal.listDocSoView.get(i).getTongCong(),
                                    "", CLocal.listDocSoView.get(i).getDot(), CLocal.May, CLocal.listDocSoView.get(i).getModifyDate());
                            if (!result.equals(""))
                                jsonObject = new JSONObject(result);
                            if (jsonObject != null && Boolean.parseBoolean(jsonObject.getString("success").replace("null", ""))) {
                                CLocal.listDocSoView.get(i).setSync(true);
                                Bitmap bitmap = BitmapFactory.decodeFile(CLocal.pathAppPicture + "/" + CLocal.listDocSoView.get(i).getNam() + "_" + CLocal.listDocSoView.get(i).getKy() + "_" + CLocal.listDocSoView.get(i).getDot() + "/" + CLocal.listDocSoView.get(i).getDanhBo().replace(" ", "") + ".jpg");
                                if (bitmap != null) {
                                    String result2 = ws.ghi_Hinh(CLocal.listDocSoView.get(i).getID(), CBitmap.convertBitmapToString(bitmap));
                                    if (Boolean.parseBoolean(result2)) {
                                        CLocal.listDocSoView.get(i).setGhiHinh(true);
                                    }
                                }
                            }
                            CLocal.updateTinhTrangParent(CLocal.listDocSo, CLocal.listDocSoView.get(i));
                        }
            } catch (Exception ex) {
                error = ex.getMessage();
            }
            return error;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.equals(""))
                CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, s);
        }
    }

//    public class MyAsyncTask_ConnectPrinter extends AsyncTask<String, String, String> {
//        String error = "";
//
//        @Override
//        protected String doInBackground(String... strings) {
//            try {
//                thermalPrinter = new ThermalPrinter(ActivityDocSo_GhiChiSo.this);
//                if (thermalPrinter != null) {
//                    thermalPrinter.findBluetoothDevice();
//                    thermalPrinter.openBluetoothPrinter();
//                }
//            } catch (Exception e) {
//                error = e.getMessage();
//            }
//            return error;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            if (s.equals("") == false)
//                CLocal.showToastMessage(ActivityDocSo_GhiChiSo.this, s);
//        }
//    }

}