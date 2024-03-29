package vn.com.capnuoctanhoa.docsoandroid.DocSo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import vn.com.capnuoctanhoa.docsoandroid.Class.CBitmap;
import vn.com.capnuoctanhoa.docsoandroid.Class.CLocal;
import vn.com.capnuoctanhoa.docsoandroid.Class.CWebservice;
import vn.com.capnuoctanhoa.docsoandroid.R;

public class ActivityDocSo_View extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_so_view);

        TextView txtKy, txtTuNgay, txtDenNgay, txtMLT, txtDanhBo, txtHoTen, txtDiaChi, txtGiaBieu, txtDinhMuc, txtCode, txtChiSoCu, txtChiSo, txtTieuThu, txtTienNuoc, txtThueGTGT, txtPhiBVMT, txtPhiBVMT_Thue, txtTongCong;
        ImageView imgThumb;

        txtKy = (TextView) findViewById(R.id.txtKy);
        txtTuNgay = (TextView) findViewById(R.id.txtTuNgay);
        txtDenNgay = (TextView) findViewById(R.id.txtDenNgay);
        txtMLT = (TextView) findViewById(R.id.txtMLT);
        txtDanhBo = (TextView) findViewById(R.id.txtDanhBo);
        txtHoTen = (TextView) findViewById(R.id.txtHoTen);
        txtDiaChi = (TextView) findViewById(R.id.txtDiaChi);
        txtGiaBieu = (TextView) findViewById(R.id.txtGiaBieu);
        txtDinhMuc = (TextView) findViewById(R.id.txtDinhMuc);
        txtCode = (TextView) findViewById(R.id.txtCode);
        txtChiSoCu = (TextView) findViewById(R.id.txtChiSoCu);
        txtChiSo = (TextView) findViewById(R.id.txtChiSo);
        txtTieuThu = (TextView) findViewById(R.id.txtTieuThu);
        txtTienNuoc = (TextView) findViewById(R.id.txtTienNuoc);
        txtThueGTGT = (TextView) findViewById(R.id.txtThueGTGT);
        txtPhiBVMT = (TextView) findViewById(R.id.txtPhiBVMT);
        txtPhiBVMT_Thue = (TextView) findViewById(R.id.txtPhiBVMT_Thue);
        txtTongCong = (TextView) findViewById(R.id.txtTongCong);
        imgThumb = (ImageView) findViewById(R.id.imgThumb);

        if (!CLocal.checkNetworkAvailable(ActivityDocSo_View.this)) {
            Toast.makeText(ActivityDocSo_View.this, "Không có Internet", Toast.LENGTH_LONG).show();
            return;
        }
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(ActivityDocSo_View.this);
        progressDialog.setTitle("Thông Báo");
        progressDialog.setMessage("Đang xử lý...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            //Background work here
            CWebservice ws = new CWebservice();
            String error = "";
            JSONObject jsonObject = null;
            try {
                String DanhBo = getIntent().getStringExtra("DanhBo");
                String Nam = getIntent().getStringExtra("Nam");
                String Ky = getIntent().getStringExtra("Ky");
                jsonObject = new JSONObject(ws.get_ThongTin(DanhBo, Nam, Ky));
            } catch (Exception ex) {
                error = ex.getMessage();
            }

            String finalError = error;
            JSONObject finalJsonObject = jsonObject;
            handler.post(() -> {
                //UI Thread work here
                try {
                    if (!finalError.equals(""))
                        CLocal.showPopupMessage(ActivityDocSo_View.this, finalError, "center");
                    else {
                        if (Boolean.parseBoolean(finalJsonObject.getString("success").replace("null", ""))) {
                            JSONObject jsonObjectC = new JSONObject(finalJsonObject.getString("message").replace("null", ""));
                            txtKy.setText(getIntent().getStringExtra("Ky") + "/" + getIntent().getStringExtra("Nam"));
                            txtTuNgay.setText(jsonObjectC.getString("TuNgay").replace("null", ""));
                            txtDenNgay.setText(jsonObjectC.getString("DenNgay").replace("null", ""));
                            txtMLT.setText(jsonObjectC.getString("MLT").replace("null", ""));
                            txtDanhBo.setText(jsonObjectC.getString("DanhBo").replace("null", ""));
                            txtHoTen.setText(jsonObjectC.getString("HoTen").replace("null", ""));
                            txtDiaChi.setText(jsonObjectC.getString("DiaChi").replace("null", ""));
                            txtGiaBieu.setText(jsonObjectC.getString("GiaBieu").replace("null", ""));
                            txtDinhMuc.setText(jsonObjectC.getString("DinhMuc").replace("null", ""));
                            txtCode.setText(jsonObjectC.getString("CodeMoi").replace("null", ""));
                            txtChiSoCu.setText(jsonObjectC.getString("CSC").replace("null", ""));
                            txtChiSo.setText(jsonObjectC.getString("ChiSoMoi").replace("null", ""));
                            txtTieuThu.setText(jsonObjectC.getString("TieuThuMoi").replace("null", ""));
                            txtTienNuoc.setText(CLocal.numberVN(Double.parseDouble(jsonObjectC.getString("TienNuoc").replace("null", ""))));
                            txtThueGTGT.setText(CLocal.numberVN(Double.parseDouble(jsonObjectC.getString("ThueGTGT").replace("null", ""))));
                            txtPhiBVMT.setText(CLocal.numberVN(Double.parseDouble(jsonObjectC.getString("PhiBVMT").replace("null", ""))));
                            txtPhiBVMT_Thue.setText(CLocal.numberVN(Double.parseDouble(jsonObjectC.getString("PhiBVMT_Thue").replace("null", ""))));
                            txtTongCong.setText(CLocal.numberVN(Double.parseDouble(jsonObjectC.getString("TongCong").replace("null", ""))));
                            if (!finalJsonObject.getString("alert").replace("null", "").equals(""))
                                imgThumb.setImageBitmap(CBitmap.base64ToImage(CLocal.convertStandardJSONString(finalJsonObject.getString("alert").replace("null", ""))));
                            if (progressDialog != null) {
                                progressDialog.dismiss();
                            }
                        }
                    }
                } catch (Exception ex) {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    CLocal.showPopupMessage(ActivityDocSo_View.this, ex.getMessage(), "center");
                }
            });
        });

    }
}