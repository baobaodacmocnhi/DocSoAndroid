package vn.com.capnuoctanhoa.docsoandroid.DocSo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import vn.com.capnuoctanhoa.docsoandroid.Class.CEntityParent;
import vn.com.capnuoctanhoa.docsoandroid.Class.CLocal;
import vn.com.capnuoctanhoa.docsoandroid.Class.CWebservice;
import vn.com.capnuoctanhoa.docsoandroid.R;

public class ActivityDocSo_LichSu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_so_lich_su);
        ListView lstView = (ListView) findViewById(R.id.lstView);
        List<String> data = new ArrayList<>();
        if (!CLocal.checkNetworkAvailable(ActivityDocSo_LichSu.this)) {
            Toast.makeText(ActivityDocSo_LichSu.this, "Không có Internet", Toast.LENGTH_LONG).show();
            return;
        }
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(ActivityDocSo_LichSu.this);
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
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = null;
            try {
                String result = ws.getDS_LichSu_DocSo(CLocal.listDocSoView.get(CLocal.STT).getDanhBo().replace(" ", ""));
                if (!result.equals(""))
                    jsonObject = new JSONObject(result);
                if (jsonObject != null)
                    if (Boolean.parseBoolean(jsonObject.getString("success").replace("null", ""))) {
                        jsonArray = new JSONArray(jsonObject.getString("message").replace("null", ""));
                    } else
                        error = "THẤT BẠI\r\n" + jsonObject.getString("error").replace("null", "");
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObjectC = jsonArray.getJSONObject(i);
                        data.add(jsonObjectC.getString("Ky").replace("null", "")
                                + ": Code: " + jsonObjectC.getString("CodeMoi").replace("null", "")
                                + "  | CS: " + jsonObjectC.getString("CSMoi").replace("null", "")
                                + "  | TT: " + jsonObjectC.getString("TieuThuMoi").replace("null", "")
                                + "\n     Từ ngày "+jsonObjectC.getString("TuNgay").replace("null", "")
                                + "\n     Đến ngày "+jsonObjectC.getString("DenNgay").replace("null", "")
                                + "\n     Giá Biểu: "+jsonObjectC.getString("GiaBieu").replace("null", "")+" | Định Mức: "+jsonObjectC.getString("DinhMuc").replace("null", "")
                                + "\n     Tiền Nước: "+ CLocal.numberVN(Double.parseDouble(jsonObjectC.getString("TienNuoc").replace("null", "")))
                                + "\n     Thuế GTGT: "+CLocal.numberVN(Double.parseDouble(jsonObjectC.getString("ThueGTGT").replace("null", "")))
                                + "\n     TDVTN: "+CLocal.numberVN(Double.parseDouble(jsonObjectC.getString("PhiBVMT").replace("null", "")))
                                + "\n     Thuế TDVTN: "+CLocal.numberVN(Double.parseDouble(jsonObjectC.getString("PhiBVMT_Thue").replace("null", "")))
                                + "\n     Tổng Cộng: "+CLocal.numberVN(Double.parseDouble(jsonObjectC.getString("TongCong").replace("null", "")))
                                + "\n");
                    }
                }
            } catch (Exception ex) {
                error = ex.getMessage();
            }

            String finalError = error;
            handler.post(() -> {
                //UI Thread work here
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                try {
                    if (!finalError.equals(""))
                        CLocal.showPopupMessage(ActivityDocSo_LichSu.this, finalError, "center");
                    else {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
                        lstView.setAdapter(adapter);
                    }
                } catch (Exception ex) {
                    CLocal.showPopupMessage(ActivityDocSo_LichSu.this, ex.getMessage(), "center");
                }
            });
        });
    }
}