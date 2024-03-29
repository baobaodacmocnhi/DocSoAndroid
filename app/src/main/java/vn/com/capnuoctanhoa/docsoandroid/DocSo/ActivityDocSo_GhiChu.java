package vn.com.capnuoctanhoa.docsoandroid.DocSo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vn.com.capnuoctanhoa.docsoandroid.Class.CCode;
import vn.com.capnuoctanhoa.docsoandroid.Class.CEntityParent;
import vn.com.capnuoctanhoa.docsoandroid.Class.CLocal;
import vn.com.capnuoctanhoa.docsoandroid.Class.CWebservice;
import vn.com.capnuoctanhoa.docsoandroid.Class.CustomAdapterRecyclerViewDienThoai;
import vn.com.capnuoctanhoa.docsoandroid.R;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityDocSo_GhiChu extends AppCompatActivity {
    private EditText edtSoNha, edtTenDuong, edtDienThoai, edtHoTen, edtGhiChu;
    private Spinner spnViTri, spnMauSacChiGoc, spnKinhDoanh;
    private CheckBox chkViTriNgoai, chkViTriHop, chkGieng, chkKhoaTu, chkSoChinh;
    private ArrayList<String> spnName_ViTriDHN, spnName_KinhDoanh;
    private JSONArray jsonDSDienThoai;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_so_ghi_chu);
        edtSoNha = (EditText) findViewById(R.id.edtSoNha);
        edtTenDuong = (EditText) findViewById(R.id.edtTenDuong);
        edtGhiChu = (EditText) findViewById(R.id.edtGhiChu);
        spnViTri = (Spinner) findViewById(R.id.spnViTri);
        spnMauSacChiGoc = (Spinner) findViewById(R.id.spnMauSacChiGoc);
        spnKinhDoanh = (Spinner) findViewById(R.id.spnKinhDoanh);
        chkViTriNgoai = (CheckBox) findViewById(R.id.chkViTriNgoai);
        chkViTriHop = (CheckBox) findViewById(R.id.chkViTriHop);
        chkGieng = (CheckBox) findViewById(R.id.chkGieng);
        chkKhoaTu = (CheckBox) findViewById(R.id.chkKhoaTu);
        Button btnCapNhat = (Button) findViewById(R.id.btnCapNhat);
        edtDienThoai = (EditText) findViewById(R.id.edtDienThoai);
        edtHoTen = (EditText) findViewById(R.id.edtHoTen);
        chkSoChinh = (CheckBox) findViewById(R.id.chkSoChinh);
        Button btnCapNhatDT = (Button) findViewById(R.id.btnCapNhatDT);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        try {
            if (CLocal.jsonViTriDHN != null && CLocal.jsonViTriDHN.length() > 0) {
                spnName_ViTriDHN = new ArrayList<>();
                for (int i = 0; i < CLocal.jsonViTriDHN.length(); i++) {
                    JSONObject jsonObject = CLocal.jsonViTriDHN.getJSONObject(i);
                    spnName_ViTriDHN.add(jsonObject.getString("KyHieu").replace("null", ""));
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, spnName_ViTriDHN);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnViTri.setAdapter(adapter);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        try {
            if (CLocal.jsonKinhDoanh != null && CLocal.jsonKinhDoanh.length() > 0) {
                spnName_KinhDoanh = new ArrayList<>();
                for (int i = 0; i < CLocal.jsonKinhDoanh.length(); i++) {
                    JSONObject jsonObject = CLocal.jsonKinhDoanh.getJSONObject(i);
                    spnName_KinhDoanh.add(jsonObject.getString("Name").replace("null", ""));
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, spnName_KinhDoanh);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnKinhDoanh.setAdapter(adapter);
        } catch (Exception e) {
//            e.printStackTrace();
        }

        btnCapNhat.setOnClickListener(view -> {
            if (!CLocal.checkNetworkGood(ActivityDocSo_GhiChu.this)) {
                CLocal.showToastMessage(ActivityDocSo_GhiChu.this, "Internet yếu");
                return;
            }
            MyAsyncTask myAsyncTask = new MyAsyncTask();
            myAsyncTask.execute("CapNhat");
        });

        btnCapNhatDT.setOnClickListener(view -> {
            if (!CLocal.checkNetworkGood(ActivityDocSo_GhiChu.this)) {
                CLocal.showToastMessage(ActivityDocSo_GhiChu.this, "Internet yếu");
                return;
            }
            MyAsyncTask myAsyncTask = new MyAsyncTask();
            myAsyncTask.execute("CapNhatDT");
        });

        try {
            if (CLocal.STT > -1) {
                if (CLocal.listDocSoView != null && CLocal.listDocSoView.size() > 0) {
                    if (CLocal.STT < CLocal.listDocSoView.size()) {
                        CEntityParent entityParent = CLocal.listDocSoView.get(CLocal.STT);
                        edtSoNha.setText(entityParent.getSoNha());
                        edtTenDuong.setText(entityParent.getTenDuong());
                        edtGhiChu.setText(entityParent.getGhiChu());
                        if (!entityParent.getViTri().equals(""))
                            spnViTri.setSelection(((ArrayAdapter) spnViTri.getAdapter()).getPosition(entityParent.getViTri()));
                        chkViTriNgoai.setChecked(entityParent.isViTriNgoai());
                        chkViTriHop.setChecked(entityParent.isViTriHop());
                        chkGieng.setChecked(entityParent.isGieng());
                        chkKhoaTu.setChecked(entityParent.isKhoaTu());
                        spinnerSelectValue(spnMauSacChiGoc, entityParent.getMauSacChiGoc());
                        spinnerSelectValue(spnKinhDoanh, entityParent.getKinhDoanh());
                    }
                }
                if (CLocal.checkNetworkGood(ActivityDocSo_GhiChu.this)) {
                    MyAsyncTaskDisapper myAsyncTaskDisapper = new MyAsyncTaskDisapper();
                    myAsyncTaskDisapper.execute();
                } else
                    CLocal.showPopupMessage(ActivityDocSo_GhiChu.this, "Internet yếu", "center");
            }
        } catch (Exception ex) {
            CLocal.showToastMessage(ActivityDocSo_GhiChu.this, ex.getMessage());
        }

    }

    private void spinnerSelectValue(Spinner spinner, Object value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    public void fillDSDienThoai() {
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
                CustomAdapterRecyclerViewDienThoai customAdapterRecyclerViewDienThoai = new CustomAdapterRecyclerViewDienThoai(ActivityDocSo_GhiChu.this, lstDienThoai);
                customAdapterRecyclerViewDienThoai.setClickItemListener(entityParent -> {
                    edtDienThoai.setText(entityParent.getDienThoai());
                    edtHoTen.setText(entityParent.getHoTen());
                    chkSoChinh.setChecked(entityParent.isSoChinh());
                });
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(customAdapterRecyclerViewDienThoai);
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StringBuilder sdt = new StringBuilder();
        for (int x = recyclerView.getChildCount(), i = 0; i < x; i++) {
            RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
            TextView txtDienThoai = (TextView) holder.itemView.findViewById(R.id.txtDienThoai);
            TextView txtHoTen = (TextView) holder.itemView.findViewById(R.id.txtHoTen);
            CheckBox chkSoChinh = (CheckBox) holder.itemView.findViewById(R.id.chkSoChinh);
            if (chkSoChinh.isChecked())
                if (sdt.toString().equals(""))
                    sdt = new StringBuilder(txtDienThoai.getText().toString() + " " + txtHoTen.getText().toString());
                else
                    sdt.append(" | ").append(txtDienThoai.getText().toString()).append(" ").append(txtHoTen.getText().toString());
        }
        CLocal.listDocSoView.get(CLocal.STT).setDienThoai(sdt.toString());
    }

    public class MyAsyncTask extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;

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
            String error = "";
            try {
                CWebservice ws = new CWebservice();
                JSONObject jsonObject = null;
                String result = "";
                switch (strings[0]) {
                    case "CapNhat":
                        result = ws.update_GhiChu(CLocal.listDocSoView.get(CLocal.STT).getDanhBo().replace(" ", ""), edtSoNha.getText().toString(), edtTenDuong.getText().toString()
                                , spnViTri.getSelectedItem().toString(), String.valueOf(chkViTriNgoai.isChecked()), String.valueOf(chkViTriHop.isChecked()), String.valueOf(chkGieng.isChecked())
                                , String.valueOf(chkKhoaTu.isChecked())
                                , spnMauSacChiGoc.getSelectedItem().toString(), edtGhiChu.getText().toString(), spnKinhDoanh.getSelectedItem().toString(), CLocal.MaNV);
                        break;
                    case "CapNhatDT":
                        result = ws.update_DienThoai(CLocal.listDocSoView.get(CLocal.STT).getDanhBo().replace(" ", ""), edtDienThoai.getText().toString(), edtHoTen.getText().toString()
                                , String.valueOf(chkSoChinh.isChecked()), CLocal.MaNV);
                        break;
                }
                if (!result.equals(""))
                    jsonObject = new JSONObject(result);
                else
                    error = "lỗi api";
                if (jsonObject != null)
                    if (Boolean.parseBoolean(jsonObject.getString("success").replace("null", ""))) {
                        switch (strings[0]) {
                            case "CapNhat":
                                CLocal.listDocSoView.get(CLocal.STT).setSoNha(edtSoNha.getText().toString());
                                CLocal.listDocSoView.get(CLocal.STT).setTenDuong(edtTenDuong.getText().toString());
                                CLocal.listDocSoView.get(CLocal.STT).setViTri(spnViTri.getSelectedItem().toString());
                                CLocal.listDocSoView.get(CLocal.STT).setViTriNgoai(chkViTriNgoai.isChecked());
                                CLocal.listDocSoView.get(CLocal.STT).setViTriHop(chkViTriHop.isChecked());
                                CLocal.listDocSoView.get(CLocal.STT).setGieng(chkGieng.isChecked());
                                CLocal.listDocSoView.get(CLocal.STT).setMauSacChiGoc(spnMauSacChiGoc.getSelectedItem().toString());
                                CLocal.listDocSoView.get(CLocal.STT).setGhiChu(edtGhiChu.getText().toString());
                                CLocal.listDocSoView.get(CLocal.STT).setKinhDoanh(spnKinhDoanh.getSelectedItem().toString());
                                break;
                            case "CapNhatDT":
                                StringBuilder sdt = new StringBuilder();
                                if (chkSoChinh.isChecked())
                                    if (sdt.toString().equals(""))
                                        sdt = new StringBuilder(edtDienThoai.getText().toString() + " " + edtHoTen.getText().toString());
                                    else
                                        sdt.append(" | ").append(edtDienThoai.getText().toString()).append(" ").append(edtHoTen.getText().toString());
                                CLocal.listDocSoView.get(CLocal.STT).setDienThoai(sdt.toString());
                                break;
                        }
                        CLocal.updateTinhTrangParent(CLocal.listDocSo, CLocal.listDocSoView.get(CLocal.STT));
                    } else
                        error = "THẤT BẠI\r\n" + jsonObject.getString("error").replace("null", "");
            } catch (Exception e) {
                error = e.getMessage();
            }
            return error;
        }

      /*  @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            MyAsyncTaskDisapper myAsyncTaskDisapper = new MyAsyncTaskDisapper();
            myAsyncTaskDisapper.execute();
        }*/

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            if (!s.equals(""))
                CLocal.showPopupMessage(ActivityDocSo_GhiChu.this, s, "center");
            else {
                CLocal.showToastMessage(ActivityDocSo_GhiChu.this, "Thành Công");
                finish();
            }
        }
    }

    public class MyAsyncTaskDisapper extends AsyncTask<String, String, String> {
//        ProgressDialog progressDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog = new ProgressDialog(ActivityDocSo_GhiChu.this);
//            progressDialog.setTitle("Thông Báo");
//            progressDialog.setMessage("Đang xử lý...");
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();
//        }

        @Override
        protected String doInBackground(String... strings) {
            String error = "";
            try {
                CWebservice ws = new CWebservice();
                JSONObject jsonObject = null;
                String result = ws.getDS_DienThoai(CLocal.listDocSoView.get(CLocal.STT).getDanhBo().replace(" ", ""));
                if (!result.equals(""))
                    jsonObject = new JSONObject(result);
                if (jsonObject != null)
                    if (Boolean.parseBoolean(jsonObject.getString("success").replace("null", ""))) {
                        publishProgress(jsonObject.getString("message").replace("null", ""));
                    } else
                        error = "THẤT BẠI\r\n" + jsonObject.getString("error").replace("null", "");
            } catch (Exception e) {
                error = e.getMessage();
            }
            return error;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            if (values != null) {
                try {
                    jsonDSDienThoai = new JSONArray(values[0]);
                    fillDSDienThoai();
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
            if (!s.equals(""))
                CLocal.showPopupMessage(ActivityDocSo_GhiChu.this, s, "center");
        }

    }

}