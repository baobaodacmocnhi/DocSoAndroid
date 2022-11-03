package vn.com.capnuoctanhoa.docsoandroid;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import vn.com.capnuoctanhoa.docsoandroid.Class.CLocal;
import vn.com.capnuoctanhoa.docsoandroid.Class.CWebservice;

public class ActivityDangNhap extends AppCompatActivity {
    TextView txtUser;
    EditText edtUsername, edtPassword;
    Button btnDangNhap, btnDangXuat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtUser = (TextView) findViewById(R.id.txtUser);
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
        btnDangXuat = (Button) findViewById(R.id.btnDangXuat);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!CLocal.checkNetworkAvailable(ActivityDangNhap.this)) {
                    Toast.makeText(ActivityDangNhap.this, "Không có Internet", Toast.LENGTH_LONG).show();
                    return;
                }
                MyAsyncTask myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute("DangNhap");
            }
        });

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!CLocal.checkNetworkAvailable(ActivityDangNhap.this)) {
                    Toast.makeText(ActivityDangNhap.this, "Không có Internet", Toast.LENGTH_LONG).show();
                    return;
                }
                MyAsyncTask myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute("DangXuat");

                Reload();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Reload();
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

    public void Reload() {
        edtUsername.setText("");
        edtPassword.setText("");
        if (CLocal.sharedPreferencesre.getBoolean("Login", false)) {
            txtUser.setText("Xin chào " + CLocal.sharedPreferencesre.getString("HoTen", ""));
            txtUser.setTextColor(getResources().getColor(R.color.colorLogin));
            edtUsername.setVisibility(View.INVISIBLE);
            edtPassword.setVisibility(View.INVISIBLE);
            btnDangNhap.setVisibility(View.INVISIBLE);
            btnDangXuat.setVisibility(View.VISIBLE);
        } else {
            txtUser.setText("Xin hãy đăng nhập");
            txtUser.setTextColor(getResources().getColor(R.color.colorLogout));
            edtUsername.setVisibility(View.VISIBLE);
            edtPassword.setVisibility(View.VISIBLE);
            btnDangNhap.setVisibility(View.VISIBLE);
            btnDangXuat.setVisibility(View.INVISIBLE);
        }
    }

    public class MyAsyncTask extends AsyncTask<String, String, String[]> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ActivityDangNhap.this);
            progressDialog.setTitle("Thông Báo");
            progressDialog.setMessage("Đang xử lý...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String[] doInBackground(String... strings) {
            CWebservice ws = new CWebservice();
            String result = "";
            String[] results = new String[]{};
            switch (strings[0]) {
                case "DangNhap":
                    try {
                        result = ws.dangNhaps(edtUsername.getText().toString(), edtPassword.getText().toString(), CLocal.IDMobile, CLocal.sharedPreferencesre.getString("UID", ""));
                        results = result.split(";");
                        if (Boolean.parseBoolean(results[0])) {
                            CLocal.initialCLocal();
                            JSONArray jsonArray = new JSONArray(results[1]);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            SharedPreferences.Editor editor = CLocal.sharedPreferencesre.edit();
                            editor.putString("Username", jsonObject.getString("TaiKhoan"));
                            editor.putString("Password", jsonObject.getString("MatKhau"));
                            editor.putString("MaNV", jsonObject.getString("MaND"));
                            editor.putString("HoTen", jsonObject.getString("HoTen"));
                            editor.putString("May", jsonObject.getString("May"));
                            editor.putString("MaTo", jsonObject.getString("MaTo"));
                            editor.putString("DienThoai", jsonObject.getString("DienThoai"));
                            editor.putBoolean("Admin", Boolean.parseBoolean(jsonObject.getString("Admin")));
                            editor.putBoolean("ToTruong", Boolean.parseBoolean(jsonObject.getString("ToTruong")));
                            editor.putBoolean("Doi", Boolean.parseBoolean(jsonObject.getString("Doi")));
                            editor.putString("jsonDocSo", "");
                            String str = ws.getDS_Nam();
                            editor.putString("jsonNam", str);
                            str = ws.getDS_Code();
                            editor.putString("jsonCode", str);
                            editor.putString("jsonViTriDHN", ws.getDS_ViTriDHN());
                            editor.putString("jsonKinhDoanh", ws.getDS_NoiDung_KinhDoanh());
                            editor.putString("jsonPhieuChuyen", ws.getDS_PhieuChuyen());
                            editor.putString("jsonGiaNuoc", ws.getDS_GiaNuoc());
                            editor.putString("jsonKhongTinhPBVMT", ws.getDS_KhongTinhPBVMT());
                            if (Boolean.parseBoolean(jsonObject.getString("Doi"))) {
                                editor.putString("jsonTo", ws.getDSTo());
                                editor.putString("jsonNhanVien", ws.getDS_NhanVien());
                            } else if (Boolean.parseBoolean(jsonObject.getString("ToTruong"))) {
                                editor.putString("jsonNhanVien", ws.getDSNhanVienTo(jsonObject.getString("MaTo")));
                            }
                            editor.putBoolean("Login", true);
                            editor.putLong("LoginDate", new Date().getTime());
                            editor.apply();

                            publishProgress("DangNhap");
                        }
                        return results;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                case "DangXuat":
                    try {
                        result = ws.dangXuats_Person(CLocal.sharedPreferencesre.getString("Username", ""), CLocal.sharedPreferencesre.getString("UID", ""));
                        results = result.split(";");
                        if (Boolean.parseBoolean(results[0])) {
                            publishProgress("DangXuat");
                        }
                        return results;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            if (values != null) {
                switch (values[0]) {
                    case "DangNhap":
                        Reload();
                        break;
                    case "DangXuat":
                        CLocal.initialCLocal();
                        break;
                }
            }
        }

        @Override
        protected void onPostExecute(String[] s) {
            super.onPostExecute(s);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            if (Boolean.parseBoolean(s[0])) {
                finish();
            } else
                CLocal.showPopupMessage(ActivityDangNhap.this, "THẤT BẠI\n" + s[1], "center");
        }
    }
}
