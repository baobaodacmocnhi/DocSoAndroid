package vn.com.capnuoctanhoa.docsoandroid.DocSo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vn.com.capnuoctanhoa.docsoandroid.Class.CCode;
import vn.com.capnuoctanhoa.docsoandroid.Class.CEntityChild;
import vn.com.capnuoctanhoa.docsoandroid.Class.CEntityParent;
import vn.com.capnuoctanhoa.docsoandroid.Class.CLocal;
import vn.com.capnuoctanhoa.docsoandroid.Class.CWebservice;
import vn.com.capnuoctanhoa.docsoandroid.Class.CustomAdapterRecyclerViewDienThoai;
import vn.com.capnuoctanhoa.docsoandroid.Class.CustomAdapterRecyclerViewImage;
import vn.com.capnuoctanhoa.docsoandroid.Class.CustomAdapterSpinner;
import vn.com.capnuoctanhoa.docsoandroid.R;

import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityDocSo_GhiChu extends AppCompatActivity {
    //    private Integer STT = -1;
    private CEntityParent entityParent;
    private EditText edtSoNha, edtTenDuong, edtDienThoai, edtHoTen, edtGhiChu;
    private Spinner spnViTri1, spnViTri2;
    private Button btnCapNhat, btnCapNhatDT;
    private CheckBox chkGieng, chkSoChinh;
    private CWebservice ws;
    private ArrayList<String> spnName_ViTriDHN;
    private JSONArray jsonDSDienThoai;
    private RecyclerView recyclerView;
    private CustomAdapterRecyclerViewDienThoai customAdapterRecyclerViewDienThoai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_so_ghi_chu);
        edtSoNha = (EditText) findViewById(R.id.edtSoNha);
        edtTenDuong = (EditText) findViewById(R.id.edtTenDuong);
        edtGhiChu = (EditText) findViewById(R.id.edtGhiChu);
        spnViTri1 = (Spinner) findViewById(R.id.spnViTri1);
        spnViTri2 = (Spinner) findViewById(R.id.spnViTri2);
        chkGieng = (CheckBox) findViewById(R.id.chkGieng);
        btnCapNhat = (Button) findViewById(R.id.btnCapNhat);
        edtDienThoai = (EditText) findViewById(R.id.edtDienThoai);
        edtHoTen = (EditText) findViewById(R.id.edtHoTen);
        chkSoChinh = (CheckBox) findViewById(R.id.chkSoChinh);
        btnCapNhatDT = (Button) findViewById(R.id.btnCapNhatDT);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        CLocal.loadDienThoai = false;
        ws = new CWebservice();

        try {
            if (CLocal.jsonViTriDHN != null && CLocal.jsonViTriDHN.length() > 0) {
                spnName_ViTriDHN = new ArrayList<>();
                for (int i = 0; i < CLocal.jsonViTriDHN.length(); i++) {
                    JSONObject jsonObject = CLocal.jsonViTriDHN.getJSONObject(i);
                    spnName_ViTriDHN.add(jsonObject.getString("KyHieu").replace("null", ""));
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, spnName_ViTriDHN);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnViTri1.setAdapter(adapter);
            spnViTri2.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CLocal.checkNetworkAvailable(ActivityDocSo_GhiChu.this) == false) {
                    CLocal.showToastMessage(ActivityDocSo_GhiChu.this, "Không có Internet");
                    return;
                }
                MyAsyncTask myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute("CapNhat");
            }
        });

        btnCapNhatDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CLocal.checkNetworkAvailable(ActivityDocSo_GhiChu.this) == false) {
                    CLocal.showToastMessage(ActivityDocSo_GhiChu.this, "Không có Internet");
                    return;
                }
                MyAsyncTask myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute("CapNhatDT");
            }
        });

        try {
//            STT = Integer.parseInt(getIntent().getStringExtra("STT"));
            if (CLocal.STT > -1) {
                if (CLocal.listDocSoView != null && CLocal.listDocSoView.size() > 0) {
                    ArrayList<String> arrayList = new ArrayList<String>();
                    if (CLocal.STT >= 0 && CLocal.STT < CLocal.listDocSoView.size()) {
                        entityParent = CLocal.listDocSoView.get(CLocal.STT);
                        edtSoNha.setText(entityParent.getSoNha());
                        edtTenDuong.setText(entityParent.getTenDuong());
                        edtGhiChu.setText(entityParent.getGhiChu());
                        if (entityParent.getViTri1().equals("") == false)
                            spnViTri1.setSelection(((ArrayAdapter) spnViTri1.getAdapter()).getPosition(entityParent.getViTri1()));
                        if (entityParent.getViTri2().equals("") == false)
                            spnViTri2.setSelection(((ArrayAdapter) spnViTri2.getAdapter()).getPosition(entityParent.getViTri2()));
                        chkGieng.setChecked(entityParent.isGieng());
                    }
                }
                MyAsyncTaskDT myAsyncTaskDT = new MyAsyncTaskDT();
                myAsyncTaskDT.execute("getDSDienThoai");
            }
        } catch (Exception ex) {
            CLocal.showToastMessage(ActivityDocSo_GhiChu.this, ex.getMessage());
        }

    }

    private void fillDSDienThoai() {
        if (jsonDSDienThoai != null)
            try {
                ArrayList<CEntityParent> lstDienThoai = new ArrayList<>();
                for (int k = 0; k < jsonDSDienThoai.length(); k++) {
                    JSONObject jsonObject = jsonDSDienThoai.getJSONObject(k);
                    CEntityParent entityParent = new CEntityParent();
                    entityParent.setDanhBo(jsonObject.getString("DanhBo").replace("null", ""));
                    entityParent.setDienThoai(jsonObject.getString("DienThoai").replace("null", ""));
                    entityParent.setHoTen(jsonObject.getString("HoTen").replace("null", ""));
                    entityParent.setSoChinh(Boolean.parseBoolean(jsonObject.getString("SoChinh").replace("null", "")));
                    entityParent.setDiaChi(jsonObject.getString("GhiChu").replace("null", ""));
                    lstDienThoai.add(entityParent);
                }
                customAdapterRecyclerViewDienThoai = new CustomAdapterRecyclerViewDienThoai(ActivityDocSo_GhiChu.this, lstDienThoai);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
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
            progressDialog = new ProgressDialog(ActivityDocSo_GhiChu.this);
            progressDialog.setTitle("Thông Báo");
            progressDialog.setMessage("Đang xử lý...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String result = "";
                switch (strings[0]) {
                    case "CapNhat":
                        result = ws.update_GhiChu(entityParent.getDanhBo().replace(" ", ""), edtSoNha.getText().toString(), edtTenDuong.getText().toString()
                                , spnViTri1.getSelectedItem().toString(), spnViTri2.getSelectedItem().toString(), String.valueOf(chkGieng.isChecked()), edtGhiChu.getText().toString(), CLocal.MaNV);
                        break;
                    case "CapNhatDT":
                        result = ws.update_DienThoai(entityParent.getDanhBo().replace(" ", ""), edtDienThoai.getText().toString(), edtHoTen.getText().toString()
                                , String.valueOf(chkSoChinh.isChecked()), CLocal.MaNV);
                        break;
                    case "getDSDienThoai":
                        result = ws.getDS_DienThoai(entityParent.getDanhBo().replace(" ", ""));
                        break;
                }
                if (result.equals("") == false)
                    jsonObject = new JSONObject(result);
                if (jsonObject != null && Boolean.parseBoolean(jsonObject.getString("success").replace("null", "")) == true) {
                    switch (strings[0]) {
                        case "CapNhat":
                            entityParent.setSoNha(edtSoNha.getText().toString());
                            entityParent.setTenDuong(edtTenDuong.getText().toString());
                            entityParent.setViTri1(spnViTri1.getSelectedItem().toString());
                            entityParent.setViTri2(spnViTri2.getSelectedItem().toString());
                            entityParent.setGieng(chkGieng.isChecked());
                            break;
                        case "CapNhatDT":

                            break;
                        case "getDSDienThoai":
                            publishProgress(new String[]{"getDSDienThoai", jsonObject.getString("message").replace("null", "")});
                            break;
                    }
                    CLocal.listDocSoView.get(CLocal.STT).setCEntityParent(entityParent);
//                    CLocal.updateTinhTrangParent(CLocal.listDocSo, entityParent);
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
                switch (values[0]) {
                    case "getDSDienThoai":
                        try {
                            jsonDSDienThoai = new JSONArray(values[1]);
                            fillDSDienThoai();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
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
                if (jsonObject != null)
                    CLocal.showPopupMessage(ActivityDocSo_GhiChu.this, s + "\r\n" + jsonObject.getString("error").replace("null", ""), "center");
                else
                    CLocal.showPopupMessage(ActivityDocSo_GhiChu.this, s, "center");
            } catch (Exception e) {
                CLocal.showPopupMessage(ActivityDocSo_GhiChu.this, e.getMessage(), "center");
            }
        }
    }

    public class MyAsyncTaskDT extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        JSONObject jsonObject = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ActivityDocSo_GhiChu.this);
            progressDialog.setTitle("Thông Báo");
            progressDialog.setMessage("Đang xử lý...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String result = "";
                switch (strings[0]) {
                    case "getDSDienThoai":
                        result = ws.getDS_DienThoai(entityParent.getDanhBo().replace(" ", ""));
                        break;
                }
                if (result.equals("") == false)
                    jsonObject = new JSONObject(result);
                if (jsonObject != null && Boolean.parseBoolean(jsonObject.getString("success").replace("null", "")) == true) {
                    switch (strings[0]) {
                        case "getDSDienThoai":
                            publishProgress(new String[]{"getDSDienThoai", jsonObject.getString("message").replace("null", "")});
                            break;
                    }
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
                switch (values[0]) {
                    case "getDSDienThoai":
                        try {
                            jsonDSDienThoai = new JSONArray(values[1]);
                            fillDSDienThoai();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
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
                CLocal.showPopupMessage(ActivityDocSo_GhiChu.this, e.getMessage(), "center");
            }
        }

    }

}