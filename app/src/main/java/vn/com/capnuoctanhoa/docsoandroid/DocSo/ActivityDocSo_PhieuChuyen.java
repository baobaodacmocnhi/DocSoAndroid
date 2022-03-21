package vn.com.capnuoctanhoa.docsoandroid.DocSo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import vn.com.capnuoctanhoa.docsoandroid.Class.CEntityParent;
import vn.com.capnuoctanhoa.docsoandroid.Class.CLocal;
import vn.com.capnuoctanhoa.docsoandroid.Class.CWebservice;
import vn.com.capnuoctanhoa.docsoandroid.Class.CustomAdapterRecyclerViewDienThoai;
import vn.com.capnuoctanhoa.docsoandroid.R;

public class ActivityDocSo_PhieuChuyen extends AppCompatActivity {
    private Spinner spnPhieuChuyen;
    private EditText edtGhiChu;
    private Button btnCapNhat;
    private RecyclerView recyclerView;
    private CWebservice ws;
    private ArrayList<String> spnName_PhieuChuyen;
    private CEntityParent entityParent;
    private JSONArray jsonDSDonTu;
    private CustomAdapterRecyclerViewDienThoai customAdapterRecyclerViewDienThoai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_so_phieu_chuyen);

        spnPhieuChuyen = (Spinner) findViewById(R.id.spnPhieuChuyen);
        edtGhiChu = (EditText) findViewById(R.id.edtGhiChu);
        btnCapNhat = (Button) findViewById(R.id.btnCapNhat);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        ws = new CWebservice();

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
                myAsyncTaskDisapper.execute();
            }
        } catch (Exception ex) {
            CLocal.showToastMessage(ActivityDocSo_PhieuChuyen.this, ex.getMessage());
        }

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAsyncTask myAsyncTask=new MyAsyncTask();
                myAsyncTask.execute();
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
                    entityParent.setDienThoai(jsonObject.getString("CreateDate").replace("null", "") + "-" + jsonObject.getString("NoiDung").replace("null", "") + "-" + jsonObject.getString("GhiChu").replace("null", ""));
                    lstDienThoai.add(entityParent);
                }
                customAdapterRecyclerViewDienThoai = new CustomAdapterRecyclerViewDienThoai(ActivityDocSo_PhieuChuyen.this, lstDienThoai);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(customAdapterRecyclerViewDienThoai);
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
            }
    }

    public class MyAsyncTask extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        JSONObject jsonObject = null;

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
                result = ws.ghi_DonTu(entityParent.getDanhBo(), spnPhieuChuyen.getSelectedItem().toString(), edtGhiChu.getText().toString(), CLocal.MaNV);
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
                if (jsonObject != null)
                    CLocal.showPopupMessage(ActivityDocSo_PhieuChuyen.this, s + "\r\n" + jsonObject.getString("error").replace("null", ""), "center");
                else
                    CLocal.showPopupMessage(ActivityDocSo_PhieuChuyen.this, s, "center");
            } catch (Exception e) {
                CLocal.showPopupMessage(ActivityDocSo_PhieuChuyen.this, e.getMessage(), "center");
            }
        }
    }

    public class MyAsyncTaskDisapper extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        JSONObject jsonObject = null;

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
                result = ws.getDS_DonTu(entityParent.getDanhBo());
                if (result.equals("") == false)
                    jsonObject = new JSONObject(result);
                if (jsonObject != null && Boolean.parseBoolean(jsonObject.getString("success").replace("null", "")) == true) {
                    publishProgress(new String[]{jsonObject.getString("message").replace("null", "")});
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
            if (values != null) {
                try {
                    jsonDSDonTu = new JSONArray(values[1]);
                    fillDSDonTu();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
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