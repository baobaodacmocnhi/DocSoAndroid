package vn.com.capnuoctanhoa.docsoandroid.DocSo;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vn.com.capnuoctanhoa.docsoandroid.Class.CEntityChild;
import vn.com.capnuoctanhoa.docsoandroid.Class.CEntityParent;
import vn.com.capnuoctanhoa.docsoandroid.Class.CLocal;
import vn.com.capnuoctanhoa.docsoandroid.Class.CViewParent;
import vn.com.capnuoctanhoa.docsoandroid.Class.CWebservice;
import vn.com.capnuoctanhoa.docsoandroid.Class.CustomAdapterRecyclerViewDienThoai;
import vn.com.capnuoctanhoa.docsoandroid.R;

public class ActivityDownDataDocSo extends AppCompatActivity {
    private Button btnDownload, btnShowMess;
    private Spinner spnDot, spnTo, spnNhanVien, spnNam, spnKy;
    private ArrayList<CViewParent> lstOriginal, lstDisplayed;
    private LinearLayout layoutTo, layoutNhanVien;
    private ArrayList<String> spnID_To, spnName_To, spnID_NhanVien, spnName_NhanVien, spnName_Nam;
    private RecyclerView recyclerView;
    private String selectedMaNV = "";
    private CustomAdapterRecyclerViewDienThoai customAdapterRecyclerViewDienThoai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_data_doc_so);

        btnDownload = (Button) findViewById(R.id.btnDownload);
        btnShowMess = (Button) findViewById(R.id.btnShowMess);
        spnDot = (Spinner) findViewById(R.id.spnDot);
        spnTo = (Spinner) findViewById(R.id.spnTo);
        spnNhanVien = (Spinner) findViewById(R.id.spnNhanVien);
        spnNam = (Spinner) findViewById(R.id.spnNam);
        spnKy = (Spinner) findViewById(R.id.spnKy);
        layoutTo = (LinearLayout) findViewById(R.id.layoutTo);
        layoutNhanVien = (LinearLayout) findViewById(R.id.layoutNhanVien);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        try {
            if (CLocal.jsonNam != null && CLocal.jsonNam.length() > 0) {
                spnName_Nam = new ArrayList<>();
                for (int i = 0; i < CLocal.jsonNam.length(); i++) {
                    JSONObject jsonObject = CLocal.jsonNam.getJSONObject(i);
                    spnName_Nam.add(jsonObject.getString("Nam"));
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, spnName_Nam);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnNam.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //cast to an ArrayAdapter
        ArrayAdapter spnNamAdapter = (ArrayAdapter) spnNam.getAdapter();
        int spnNamPosition = spnNamAdapter.getPosition(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        //set the default according to value
        spnNam.setSelection(spnNamPosition);

        //cast to an ArrayAdapter
        ArrayAdapter spnKyAdapter = (ArrayAdapter) spnKy.getAdapter();
        int Ky = Calendar.getInstance().get(Calendar.MONTH) + 1;
        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) > 15)
            Ky++;
        int spnKyPosition = spnKyAdapter.getPosition((Ky < 10 ? "0" : "") + Ky);
        //set the default according to value
        spnKy.setSelection(spnKyPosition);

        if (CLocal.Doi == true) {
            layoutTo.setVisibility(View.VISIBLE);
            try {
                if (CLocal.jsonTo != null && CLocal.jsonTo.length() > 0) {
                    spnID_To = new ArrayList<>();
                    spnName_To = new ArrayList<>();
                    for (int i = 0; i < CLocal.jsonTo.length(); i++) {
                        JSONObject jsonObject = CLocal.jsonTo.getJSONObject(i);
                        if (Boolean.parseBoolean(jsonObject.getString("HanhThu")) == true) {
                            spnID_To.add(jsonObject.getString("MaTo"));
                            spnName_To.add(jsonObject.getString("TenTo"));
                        }
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, spnName_To);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnTo.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            layoutTo.setVisibility(View.GONE);
            if (CLocal.ToTruong == true) {
                layoutNhanVien.setVisibility(View.VISIBLE);
                try {
                    if (CLocal.jsonNhanVien != null && CLocal.jsonNhanVien.length() > 0) {
                        spnID_NhanVien = new ArrayList<>();
                        spnName_NhanVien = new ArrayList<>();
                        spnID_NhanVien.add("0");
                        spnName_NhanVien.add("Tất Cả");
                        for (int i = 0; i < CLocal.jsonNhanVien.length(); i++) {
                            JSONObject jsonObject = CLocal.jsonNhanVien.getJSONObject(i);
                            if (jsonObject.getString("May").equals("null") == false) {
                                spnID_NhanVien.add(jsonObject.getString("May"));
                                spnName_NhanVien.add(jsonObject.getString("May"));
                            }
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, spnName_NhanVien);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnNhanVien.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else
                layoutNhanVien.setVisibility(View.GONE);
        }

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CLocal.checkNetworkAvailable(ActivityDownDataDocSo.this) == false) {
                    Toast.makeText(getApplicationContext(), "Không có Internet", Toast.LENGTH_LONG).show();
                    return;
                }
                MyAsyncTask myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute();
            }
        });

        btnShowMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(ActivityDownDataDocSo.this);
                builderSingle.setIcon(R.mipmap.ic_launcher);
                builderSingle.setTitle("Tin nhắn đã nhận");
                builderSingle.setCancelable(false);

                ListView lstMessage = new ListView(getApplicationContext());

                //hiện thị k tô mầu
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ActivityDownDataHanhThu.this, android.R.layout.select_dialog_item);
//                try {
//                    if (CLocal.jsonMessage != null && CLocal.jsonMessage.length() > 0) {
//                        for (int i = 0; i < CLocal.jsonMessage.length(); i++) {
//                            JSONObject jsonObject = CLocal.jsonMessage.getJSONObject(i);
//                            arrayAdapter.add(jsonObject.getString("NgayNhan") + " - " + jsonObject.getString("Title") + " - " + jsonObject.getString("Content"));
//                        }
//                    }
//                } catch (Exception ex) {
//                }
//                lstMessage.setAdapter(arrayAdapter);

                //hiện thị tô mầu
                ArrayList<String> mylist = new ArrayList<String>();
                try {
                    if (CLocal.jsonMessage != null && CLocal.jsonMessage.length() > 0) {
                        for (int i = 0; i < CLocal.jsonMessage.length(); i++) {
                            JSONObject jsonObject = CLocal.jsonMessage.getJSONObject(i);
                            mylist.add(jsonObject.getString("NgayNhan") + " - " + jsonObject.getString("Title") + " - " + jsonObject.getString("Content"));
                        }
                    }
                } catch (Exception ex) {
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ActivityDownDataDocSo.this, android.R.layout.select_dialog_item, mylist) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        // Get the current item from ListView
                        View view = super.getView(position, convertView, parent);
                        if (position % 2 == 1) {
                            // Set a background color for ListView regular row/item
                            view.setBackgroundColor(Color.TRANSPARENT);
                        } else {
                            // Set the background color for alternate row/item
                            view.setBackgroundColor(getResources().getColor(R.color.colorListView));
                        }
                        return view;
                    }
                };
                lstMessage.setAdapter(arrayAdapter);

                builderSingle.setView(lstMessage);

                builderSingle.setNegativeButton(
                        "Thoát",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                builderSingle.setPositiveButton(
                        "Xóa Tất Cả",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDownDataDocSo.this);
                                builder.setMessage("Bạn có chắc chắn xóa?")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                CLocal.jsonMessage = new JSONArray();
                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        });

                //hàm này khi click row sẽ bị ẩn
                /*builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(ActivityDanhSachHanhThu3.this);
                        builderInner.setMessage(strName);
                        builderInner.setTitle("Your Selected Item is");
                        builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int which) {
                                dialog.dismiss();
                            }
                        });
                        builderInner.show();
                    }
                });*/

                final Dialog dialog = builderSingle.create();
                builderSingle.show();
            }
        });

        spnTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (CLocal.jsonNhanVien != null && CLocal.jsonNhanVien.length() > 0) {
                        spnID_NhanVien = new ArrayList<>();
                        spnName_NhanVien = new ArrayList<>();
                        spnID_NhanVien.add("0");
                        spnName_NhanVien.add("Tất Cả");
                        for (int i = 0; i < CLocal.jsonNhanVien.length(); i++) {
                            JSONObject jsonObject = CLocal.jsonNhanVien.getJSONObject(i);
                            if (jsonObject.getString("MaTo") == spnID_To.get(position) && jsonObject.getString("May").equals("null") == false) {
                                spnID_NhanVien.add(jsonObject.getString("May"));
                                spnName_NhanVien.add(jsonObject.getString("May"));
                            }
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, spnName_NhanVien);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnNhanVien.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnNhanVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMaNV =  (Integer.parseInt(spnID_NhanVien.get(position)) < 10 ? "0" : "") + Integer.parseInt(spnID_NhanVien.get(position)) ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        loadFileDownDocSo();
    }

    private void loadFileDownDocSo() {
        try {
            ArrayList<CEntityParent> lstDienThoai = new ArrayList<>();
            File[] files = CLocal.getFilesInFolder(CLocal.pathAppDownload);
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    CEntityParent entityParent = new CEntityParent();
                    entityParent.setDanhBo("");
                    entityParent.setDienThoai(files[i].getName());
                    lstDienThoai.add(entityParent);
                }
                customAdapterRecyclerViewDienThoai = new CustomAdapterRecyclerViewDienThoai(ActivityDownDataDocSo.this, lstDienThoai);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(customAdapterRecyclerViewDienThoai);
            }
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public class MyAsyncTask extends AsyncTask<Void, Void, String[]> {
        ProgressDialog progressDialog;
        CWebservice ws = new CWebservice();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ActivityDownDataDocSo.this);
            progressDialog.setTitle("Thông Báo");
            progressDialog.setMessage("Đang xử lý...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String[] doInBackground(Void... voids) {
            try {
                CLocal.jsonDocSo = new JSONArray();
                CLocal.jsonHoaDonTon = new JSONArray();
                if (CLocal.Doi == false && CLocal.ToTruong == false)
                    selectedMaNV = CLocal.May;
                if (selectedMaNV.equals("0")) {
                    for (int i = 1; i < spnID_NhanVien.size(); i++) {
                        JSONArray jsonResult = new JSONArray(ws.getDS_DocSo(spnNam.getSelectedItem().toString(), spnKy.getSelectedItem().toString(), spnDot.getSelectedItem().toString(), (Integer.parseInt(spnID_NhanVien.get(i)) < 10 ? "0" : "") + Integer.parseInt(spnID_NhanVien.get(i))));
                        for (int j = 0; j < jsonResult.length(); j++) {
                            JSONObject jsonObject = jsonResult.getJSONObject(j);
                            CLocal.jsonDocSo.put(jsonObject);
                        }
                        jsonResult = new JSONArray(ws.getDS_HoaDonTon(spnNam.getSelectedItem().toString(), spnKy.getSelectedItem().toString(), spnDot.getSelectedItem().toString(), (Integer.parseInt(spnID_NhanVien.get(i)) < 10 ? "0" : "") + Integer.parseInt(spnID_NhanVien.get(i))));
                        for (int j = 0; j < jsonResult.length(); j++) {
                            JSONObject jsonObject = jsonResult.getJSONObject(j);
                            CLocal.jsonHoaDonTon.put(jsonObject);
                        }
                    }
                } else {
                    CLocal.jsonDocSo = new JSONArray(ws.getDS_DocSo(spnNam.getSelectedItem().toString(), spnKy.getSelectedItem().toString(), spnDot.getSelectedItem().toString(), selectedMaNV));
                    CLocal.jsonHoaDonTon = new JSONArray(ws.getDS_HoaDonTon(spnNam.getSelectedItem().toString(), spnKy.getSelectedItem().toString(), spnDot.getSelectedItem().toString(), selectedMaNV));
                }

                if (CLocal.jsonDocSo != null) {
                    //khởi tạo ArrayList CEntityParent
                    CLocal.listDocSo = new ArrayList<CEntityParent>();
                    for (int i = 0; i < CLocal.jsonDocSo.length(); i++) {
                        JSONObject jsonObject = CLocal.jsonDocSo.getJSONObject(i);
                        CEntityParent enParent = new CEntityParent();
                        ///thiết lập khởi tạo 1 lần đầu để sort
//                        if (jsonObject.has("ModifyDate") == false)
//                        enParent.setModifyDate(CLocal.DateFormat.format(new Date()));
//                        else
//                            enParent.setModifyDate(jsonObject.getString("ModifyDate"));
                        enParent.setID(jsonObject.getString("DocSoID").replace("null", ""));

                        String strMLT = new StringBuffer(jsonObject.getString("MLT").replace("null", "")).insert(4, " ").insert(2, " ").toString();
                        enParent.setMLT(strMLT);

                        String strDanhBo = new StringBuffer(jsonObject.getString("DanhBo").replace("null", "")).insert(7, " ").insert(4, " ").toString();
                        enParent.setDanhBo(strDanhBo);

                        enParent.setHoTen(jsonObject.getString("HoTen").replace("null", ""));
                        enParent.setDiaChi(jsonObject.getString("DiaChi").replace("null", ""));
                        enParent.setSoNha(jsonObject.getString("SoNha").replace("null", ""));
                        enParent.setTenDuong(jsonObject.getString("TenDuong").replace("null", ""));
                        enParent.setHieu(jsonObject.getString("Hieu").replace("null", ""));
                        enParent.setCo(jsonObject.getString("Co").replace("null", ""));
                        enParent.setSoThan(jsonObject.getString("SoThan").replace("null", ""));
                        enParent.setViTri1(jsonObject.getString("ViTri1").replace("null", ""));
                        enParent.setViTri2(jsonObject.getString("ViTri2").replace("null", ""));
                        enParent.setGiaBieu(jsonObject.getString("GiaBieu").replace("null", ""));
                        enParent.setDinhMuc(jsonObject.getString("DinhMuc").replace("null", ""));
                        enParent.setDinhMucHN(jsonObject.getString("DinhMucHN").replace("null", ""));
                        enParent.setSH(Integer.parseInt(jsonObject.getString("SH").replace("null", "")));
                        enParent.setSX(Integer.parseInt(jsonObject.getString("SX").replace("null", "")));
                        enParent.setDV(Integer.parseInt(jsonObject.getString("DV").replace("null", "")));
                        enParent.setHCSN(Integer.parseInt(jsonObject.getString("HCSN").replace("null", "")));
                        enParent.setTBTT(jsonObject.getString("TBTT").replace("null", ""));
                        enParent.setChiSoMoi(jsonObject.getString("CSMoi").replace("null", ""));
                        enParent.setCodeMoi(jsonObject.getString("CodeMoi").replace("null", ""));
                        enParent.setTieuThuMoi(jsonObject.getString("TieuThuMoi").replace("null", ""));
                        enParent.setTienNuoc(jsonObject.getString("TienNuoc").replace("null", ""));
                        enParent.setThueGTGT(jsonObject.getString("ThueGTGT").replace("null", ""));
                        enParent.setPhiBVMT(jsonObject.getString("PhiBVMT").replace("null", ""));
                        enParent.setPhiBVMT_Thue(jsonObject.getString("PhiBVMT_Thue").replace("null", ""));
                        enParent.setTongCong(jsonObject.getString("TongCong").replace("null", ""));
                        enParent.setChiSo0(jsonObject.getString("ChiSo0").replace("null", ""));
                        enParent.setCode0(jsonObject.getString("Code0").replace("null", ""));
                        enParent.setTieuThu0(jsonObject.getString("TieuThu0").replace("null", ""));
                        enParent.setChiSo1(jsonObject.getString("ChiSo1").replace("null", ""));
                        enParent.setCode1(jsonObject.getString("Code1").replace("null", ""));
                        enParent.setTieuThu1(jsonObject.getString("TieuThu1").replace("null", ""));
                        enParent.setChiSo2(jsonObject.getString("ChiSo2").replace("null", ""));
                        enParent.setCode2(jsonObject.getString("Code2").replace("null", ""));
                        enParent.setTieuThu2(jsonObject.getString("TieuThu2").replace("null", ""));
                        enParent.setGieng(Boolean.parseBoolean(jsonObject.getString("Gieng").replace("null", "")));
                        enParent.setDienThoai(jsonObject.getString("DienThoai").replace("null", ""));
                        enParent.setID(jsonObject.getString("DocSoID").replace("null", ""));
                        enParent.setNam(jsonObject.getString("Nam").replace("null", ""));
                        enParent.setKy(jsonObject.getString("Ky").replace("null", ""));
                        enParent.setDot(jsonObject.getString("Dot").replace("null", ""));
                        enParent.setNgayThuTien(jsonObject.getString("NgayThuTien").replace("null", ""));
                        if (jsonObject.has("CuaHangThuHo") == true)
                            enParent.setCuaHangThuHo(jsonObject.getString("CuaHangThuHo").replace("null", ""));
                        enParent.setTuNgay(jsonObject.getString("TuNgay").replace("null", ""));
                        enParent.setDenNgay(jsonObject.getString("DenNgay").replace("null", ""));
                        enParent.setGhiChu(jsonObject.getString("GhiChu").replace("null", ""));
                        enParent.setPhanMay(jsonObject.getString("PhanMay").replace("null", ""));
                        if (CLocal.jsonHoaDonTon != null && CLocal.jsonHoaDonTon.length() > 0)
                            for (int k = 0; k < CLocal.jsonHoaDonTon.length(); k++) {
                                JSONObject jsonObjectChild = CLocal.jsonHoaDonTon.getJSONObject(k);
                                if (jsonObjectChild.getString("DanhBo").equals(enParent.getDanhBo().replace(" ", "")) == true) {
                                    CEntityChild entityChild = new CEntityChild();
                                    entityChild.setMaHD(jsonObjectChild.getString("MaHD").replace("null", ""));
                                    entityChild.setKy(jsonObjectChild.getString("KyHD").replace("null", ""));
                                    entityChild.setTongCong(jsonObjectChild.getString("TongCong").replace("null", ""));
                                    enParent.getLstHoaDon().add(entityChild);
                                }
                            }
                        CLocal.listDocSo.add(enParent);
                    }
                    SharedPreferences.Editor editor = CLocal.sharedPreferencesre.edit();
                    editor.putString("jsonDocSo", new Gson().toJsonTree(CLocal.listDocSo).getAsJsonArray().toString());
                    editor.commit();
                    //ghi file
                    CLocal.writeFile(CLocal.pathAppDownload, spnNam.getSelectedItem().toString() + "_" + spnKy.getSelectedItem().toString() + "_" + spnDot.getSelectedItem().toString() + ".txt", CLocal.sharedPreferencesre.getString("jsonDocSo", ""));
                    CLocal.writeFile(CLocal.pathAppPicture, "", "");
                    CLocal.writeFile(CLocal.pathAppPicture + "/" + spnNam.getSelectedItem().toString() + "_" + spnKy.getSelectedItem().toString() + "_" + spnDot.getSelectedItem().toString(), "", "");
                }
                return new String[]{"true", ""};
            } catch (Exception ex) {
                return new String[]{"false", ex.getMessage()};
            }
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            if (Boolean.parseBoolean(strings[0]) == true) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            } else {
                CLocal.showPopupMessage(ActivityDownDataDocSo.this, strings[1], "center");
            }
        }
    }


}
