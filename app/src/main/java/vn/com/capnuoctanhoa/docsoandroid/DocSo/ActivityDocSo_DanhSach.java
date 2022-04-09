package vn.com.capnuoctanhoa.docsoandroid.DocSo;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONObject;

import vn.com.capnuoctanhoa.docsoandroid.Class.CBitmap;
import vn.com.capnuoctanhoa.docsoandroid.Class.CEntityParent;
import vn.com.capnuoctanhoa.docsoandroid.Class.CLocal;
import vn.com.capnuoctanhoa.docsoandroid.Class.CSort;
import vn.com.capnuoctanhoa.docsoandroid.Class.CViewParent;
import vn.com.capnuoctanhoa.docsoandroid.Class.CWebservice;
import vn.com.capnuoctanhoa.docsoandroid.Class.CustomAdapterListView;
import vn.com.capnuoctanhoa.docsoandroid.R;

public class ActivityDocSo_DanhSach extends AppCompatActivity {
    private Spinner spnFilter,spnSort;
    private ListView lstView;
    private CustomAdapterListView customAdapterListView;
    private TextView txtTongHD, txtNotSync;
    private long TongDC, TongNotSync;
    private ArrayList<CViewParent> lstParent;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_so_danh_sach);

        ///clear notifications
        NotificationManager notificationManager = (NotificationManager) ActivityDocSo_DanhSach.this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spnFilter = (Spinner) findViewById(R.id.spnFilter);
        spnSort = (Spinner) findViewById(R.id.spnSort);
        Spinner spnNhanVien = (Spinner) findViewById(R.id.spnNhanVien);
        lstView = (ListView) findViewById(R.id.lstView);
        txtTongHD = (TextView) findViewById(R.id.txtTongHD);
        txtNotSync = (TextView) findViewById(R.id.txtNotSync);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        ImageView ivSync = (ImageView) findViewById(R.id.ivSync);

        ivSync.setOnClickListener(v -> {
            try {
                if (!CLocal.checkNetworkAvailable(ActivityDocSo_DanhSach.this)) {
                    CLocal.showToastMessage(ActivityDocSo_DanhSach.this, "Không có Internet");
                    return;
                }
                CLocal.showDialog(ActivityDocSo_DanhSach.this, "Đồng hộ Lệch so với server", ""
                        , "Sync Code", (dialog, which) -> {
                            dialog.dismiss();
                            if (CLocal.listDocSo != null && CLocal.listDocSo.size() > 0) {
                                MyAsyncTaskSyncCodeTon myAsyncTaskSyncCodeTon = new MyAsyncTaskSyncCodeTon();
                                myAsyncTaskSyncCodeTon.execute();
                            }
                        }, "Sync Hình", (dialog, which) -> {
                            dialog.dismiss();
                            if (CLocal.listDocSo != null && CLocal.listDocSo.size() > 0) {
                                MyAsyncTaskSyncHinhTon myAsyncTaskSyncHinhTon = new MyAsyncTaskSyncHinhTon();
                                myAsyncTaskSyncHinhTon.execute();
                            }
                        }, true);
            } catch (Exception ex) {
                CLocal.showPopupMessage(ActivityDocSo_DanhSach.this, ex.getMessage(), "left");
            }
        });

        spnFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadListView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (lstParent != null && lstParent.size() > 0)
                    switch (spnSort.getSelectedItem().toString()) {
                        case "Thời Gian Tăng":
                            lstParent.sort(new CSort("ModifyDate", -1));
                            break;
                        case "Thời Gian Giảm":
                            lstParent.sort(new CSort("ModifyDate", 1));
                            break;
                        default:
                            lstParent.sort(new CSort("", -1));
                            break;
                    }
                if (customAdapterListView != null)
                    customAdapterListView.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        lstView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            private int mLastFirstVisibleItem;

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 1)
                    floatingActionButton.show();
                else
                    floatingActionButton.hide();
//                if(mLastFirstVisibleItem<firstVisibleItem)
//                {
//                    floatingActionButton.show();
//                }
//                if(mLastFirstVisibleItem>firstVisibleItem)
//                {
//                    floatingActionButton.hide();
//                }
//                mLastFirstVisibleItem=firstVisibleItem;
            }
        });

        lstView.setOnItemLongClickListener((parent, view, position, id) -> {
            TextView STT = (TextView) view.findViewById(R.id.lvSTT);
            int i = Integer.parseInt(STT.getText().toString()) - 1;
            CLocal.indexPosition = i;
            Intent intent;
            intent = new Intent(getApplicationContext(), ActivityDocSo_GhiChiSo2.class);
            CLocal.STT = i;
            activityResultLauncher_GhiChiSo.launch(intent);
            return false;
        });

        floatingActionButton.setOnClickListener(v -> lstView.smoothScrollToPosition(0));

        loadListView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dsdocso, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnSearchClickListener(v -> {

        });
        searchView.setOnCloseListener(() -> false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                cViewAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapterListView.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_down_data:
                Intent intent = new Intent(getApplicationContext(), ActivityDownDataDocSo.class);
//                startActivityForResult(intent, 1);
                activityResultLauncher_GhiChiSo.launch(intent);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadListView() {
        try {
            lstParent = new ArrayList<>();
            CLocal.listDocSoView = new ArrayList<>();
            TongDC = 0;
            TongNotSync = 0;
            switch (spnFilter.getSelectedItem().toString()) {
                case "Chưa Đọc":
                    if (CLocal.listDocSo != null && CLocal.listDocSo.size() > 0) {
                        for (int i = 0; i < CLocal.listDocSo.size(); i++) {
                            if (CLocal.listDocSo.get(i).getCodeMoi().equals("")) {
                                CLocal.listDocSoView.add(CLocal.listDocSo.get(i));
                                addViewParent(CLocal.listDocSo.get(i));
                            }
                        }
                    }
                    break;
                case "Đã Đọc":
                    if (CLocal.listDocSo != null && CLocal.listDocSo.size() > 0) {
                        for (int i = 0; i < CLocal.listDocSo.size(); i++) {
                            if (!CLocal.listDocSo.get(i).getCodeMoi().equals("")) {
                                CLocal.listDocSoView.add(CLocal.listDocSo.get(i));
                                addViewParent(CLocal.listDocSo.get(i));
                            }
                        }
                    }
                    break;
                case "F":
                    if (CLocal.listDocSo != null && CLocal.listDocSo.size() > 0) {
                        for (int i = 0; i < CLocal.listDocSo.size(); i++) {
                            if (!CLocal.listDocSo.get(i).getCodeMoi().equals("") && CLocal.listDocSo.get(i).getCodeMoi().charAt(0) == 'F') {
                                CLocal.listDocSoView.add(CLocal.listDocSo.get(i));
                                addViewParent(CLocal.listDocSo.get(i));
                            }
                        }
                    }
                    break;
                case "Bất Thường":
                    if (CLocal.listDocSo != null && CLocal.listDocSo.size() > 0) {
                        for (int i = 0; i < CLocal.listDocSo.size(); i++) {
                            if (!CLocal.listDocSo.get(i).getCodeMoi().equals("")
                                    && (Integer.parseInt(CLocal.listDocSo.get(i).getTieuThuMoi()) <= Integer.parseInt(CLocal.listDocSo.get(i).getTBTT()) - Integer.parseInt(CLocal.listDocSo.get(i).getTBTT()) * 0.4
                                    || Integer.parseInt(CLocal.listDocSo.get(i).getTieuThuMoi()) >= Integer.parseInt(CLocal.listDocSo.get(i).getTBTT()) * 1.4)) {
                                CLocal.listDocSoView.add(CLocal.listDocSo.get(i));
                                addViewParent(CLocal.listDocSo.get(i));
                            }
                        }
                    }
                    break;
                case "Chủ Báo":
                    if (CLocal.listDocSo != null && CLocal.listDocSo.size() > 0) {
                        for (int i = 0; i < CLocal.listDocSo.size(); i++) {
                            if (!CLocal.listDocSo.get(i).getCodeMoi().equals("") && CLocal.listDocSo.get(i).isChuBao()) {
                                CLocal.listDocSoView.add(CLocal.listDocSo.get(i));
                                addViewParent(CLocal.listDocSo.get(i));
                            }
                        }
                    }
                    break;
                default:
                    if (CLocal.listDocSo != null && CLocal.listDocSo.size() > 0) {
                        for (int i = 0; i < CLocal.listDocSo.size(); i++) {
                            CLocal.listDocSoView.add(CLocal.listDocSo.get(i));
                            addViewParent(CLocal.listDocSo.get(i));
                        }
                    }
                    break;
            }
            customAdapterListView = new CustomAdapterListView(this, lstParent);
            lstView.setAdapter(customAdapterListView);
            txtTongHD.setText("ĐC:" + CLocal.formatMoney(String.valueOf(TongDC), ""));
            txtNotSync.setText(String.valueOf(TongNotSync));
            lstView.setSelection(CLocal.indexPosition);
        } catch (Exception ex) {
            CLocal.showToastMessage(ActivityDocSo_DanhSach.this, ex.getMessage());
        }
    }

    public void addViewParent(CEntityParent enParent) {
        try {
            CViewParent enViewParent = new CViewParent();
            enViewParent.setModifyDate(enParent.getModifyDate());
            enViewParent.setSTT(String.valueOf(lstParent.size() + 1));
            enViewParent.setID(String.valueOf(enParent.getID()));
            enViewParent.setRow1a(enParent.getMLT());
            if (!enParent.getCodeMoi().equals(""))
                enViewParent.setRow1b(enParent.getCodeMoi() + "-" + enParent.getChiSoMoi() + "-" + enParent.getTieuThuMoi());
            enViewParent.setRow2a(enParent.getDanhBo());
            enViewParent.setRow2b(enParent.getModifyDate());
            enViewParent.setRow3a(enParent.getHoTen());
            enViewParent.setRow4a(enParent.getSoNha() + " " + enParent.getTenDuong());
            if (!enParent.getCodeMoi().equals("") && !enParent.isSync())
                TongNotSync++;
            TongDC++;
            lstParent.add(enViewParent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ActivityResultLauncher<Intent> activityResultLauncher_GhiChiSo = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    loadListView();
                }
            });

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1) {
//            if (resultCode == Activity.RESULT_OK)
//                loadListView();
//        }
//    }

    public class MyAsyncTaskSyncCodeTon extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        CWebservice ws;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ActivityDocSo_DanhSach.this);
            progressDialog.setTitle("Thông Báo");
            progressDialog.setMessage("Đang Xử Lý...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setIndeterminate(false);
//            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            ws = new CWebservice();
            String error = "";
            try {
                JSONArray jsonDocSoTon = new JSONArray(ws.getDS_DocSo_Ton(CLocal.listDocSoView.get(0).getNam(), CLocal.listDocSoView.get(0).getKy(), CLocal.listDocSoView.get(0).getDot(), CLocal.May));
                int total = jsonDocSoTon.length();
                progressDialog.setMax(total);
                if (jsonDocSoTon.length() > 0)
                    for (int i = 0; i < jsonDocSoTon.length(); i++) {
                        JSONObject jsonObjectDocSoTon = jsonDocSoTon.getJSONObject(i);
                        if (CLocal.listDocSo.get(i).getID().equals(jsonObjectDocSoTon.getString("DocSoID").replace("null", ""))
                                && !CLocal.listDocSo.get(i).getCodeMoi().equals("")
                                && (!CLocal.listDocSo.get(i).getCodeMoi().equals(jsonObjectDocSoTon.getString("CodeMoi").replace("null", ""))
                                || Integer.parseInt(CLocal.listDocSo.get(i).getChiSoMoi()) != Integer.parseInt(jsonObjectDocSoTon.getString("CSMoi").replace("null", ""))
                                || !CLocal.listDocSo.get(i).isGhiHinh())) {
                            String HinhDHN = "";
                            Bitmap bitmap = BitmapFactory.decodeFile(CLocal.pathAppPicture + "/" + CLocal.listDocSo.get(i).getNam() + "_" + CLocal.listDocSo.get(i).getKy() + "_" + CLocal.listDocSo.get(i).getDot() + "/" + CLocal.listDocSo.get(i).getDanhBo().replace(" ", "") + ".jpg");
                            if (bitmap != null) {
                                HinhDHN = CBitmap.convertBitmapToString(bitmap);
                            }
                            String result = ws.ghiChiSo_GianTiep(CLocal.listDocSo.get(i).getID(), CLocal.listDocSo.get(i).getCodeMoi(), CLocal.listDocSo.get(i).getChiSoMoi(), CLocal.listDocSo.get(i).getTieuThuMoi()
                                    , CLocal.listDocSo.get(i).getTienNuoc(), CLocal.listDocSo.get(i).getThueGTGT(), CLocal.listDocSo.get(i).getPhiBVMT(), CLocal.listDocSo.get(i).getPhiBVMT_Thue(), CLocal.listDocSo.get(i).getTongCong(),
                                    HinhDHN, CLocal.listDocSo.get(i).getDot(), CLocal.May, CLocal.listDocSo.get(i).getModifyDate());
                            JSONObject jsonObject = null;
                            if (!result.equals(""))
                                jsonObject = new JSONObject(result);
                            if (jsonObject != null && Boolean.parseBoolean(jsonObject.getString("success").replace("null", ""))) {
                                CLocal.listDocSo.get(i).setSync(true);
                                CLocal.listDocSo.get(i).setGhiHinh(true);
                            }
                        }
                        publishProgress(String.valueOf(i));
                    }
            } catch (Exception e) {
                error = e.getMessage();
            }
            return error;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(Integer.parseInt(values[0]));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            if (!s.equals(""))
                CLocal.showToastMessage(ActivityDocSo_DanhSach.this, s);
            else
                CLocal.showToastMessage(ActivityDocSo_DanhSach.this, "Đã Xử Lý");
        }

    }

    public class MyAsyncTaskSyncHinhTon extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        CWebservice ws;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ActivityDocSo_DanhSach.this);
            progressDialog.setTitle("Thông Báo");
            progressDialog.setMessage("Đang Xử Lý...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setIndeterminate(false);
//            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            ws = new CWebservice();
            String error = "";
            try {
                JSONArray jsonDocSoTon = new JSONArray(ws.getDS_Hinh_Ton(CLocal.listDocSoView.get(0).getNam(), CLocal.listDocSoView.get(0).getKy(), CLocal.listDocSoView.get(0).getDot(), CLocal.May));
                int total = jsonDocSoTon.length();
                progressDialog.setMax(total);
                if (jsonDocSoTon.length() > 0)
                    for (int i = 0; i < jsonDocSoTon.length(); i++) {
                        JSONObject jsonObjectDocSoTon = jsonDocSoTon.getJSONObject(i);
                        if (CLocal.listDocSo.get(i).getID().equals(jsonObjectDocSoTon.getString("DocSoID").replace("null", ""))
                                && !CLocal.listDocSo.get(i).getCodeMoi().equals("")) {
                            Bitmap bitmap = BitmapFactory.decodeFile(CLocal.pathAppPicture + "/" + CLocal.listDocSo.get(i).getNam() + "_" + CLocal.listDocSo.get(i).getKy() + "_" + CLocal.listDocSo.get(i).getDot() + "/" + CLocal.listDocSo.get(i).getDanhBo().replace(" ", "") + ".jpg");
                            if (bitmap != null) {
                                String HinhDHN = CBitmap.convertBitmapToString(bitmap);
                                String result = ws.ghi_Hinh(CLocal.listDocSo.get(i).getID(), HinhDHN);
                                if (Boolean.parseBoolean(result))
                                    CLocal.listDocSo.get(i).setGhiHinh(true);
                            }
                        }
                        publishProgress(String.valueOf(i));
                    }
            } catch (Exception e) {
                error = e.getMessage();
            }
            return error;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(Integer.parseInt(values[0]));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            if (!s.equals(""))
                CLocal.showToastMessage(ActivityDocSo_DanhSach.this, s);
            else
                CLocal.showToastMessage(ActivityDocSo_DanhSach.this, "Đã Xử Lý");
        }

    }

}
