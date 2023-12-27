package vn.com.capnuoctanhoa.docsoandroid.QuanLy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import vn.com.capnuoctanhoa.docsoandroid.Class.CEntityParent;
import vn.com.capnuoctanhoa.docsoandroid.Class.CLocal;
import vn.com.capnuoctanhoa.docsoandroid.Class.CViewParent;
import vn.com.capnuoctanhoa.docsoandroid.Class.CWebservice;
import vn.com.capnuoctanhoa.docsoandroid.Class.CustomAdapterListView;
import vn.com.capnuoctanhoa.docsoandroid.DocSo.ActivityDocSo_GhiChiSo;
import vn.com.capnuoctanhoa.docsoandroid.R;

public class FragmentBatThuong extends Fragment {
    private Spinner spnNam, spnKy, spnDot, spnFilter;
    private EditText edtTieuThu;
    private ListView lstView;
    private ArrayList<CViewParent> lstVParent;
    private ArrayList<CEntityParent> lstEParent;
    private FloatingActionButton floatingActionButton;
    private TextView txtTongHD;
    private ArrayList<String> spnID_To, spnName_To, spnName_Nam;
    private String selectedTo = "";
    private int Tong = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bat_thuong, container, false);

        LinearLayout layoutTo = (LinearLayout) rootView.findViewById(R.id.layoutTo);
        spnNam = (Spinner) rootView.findViewById(R.id.spnNam);
        spnKy = (Spinner) rootView.findViewById(R.id.spnKy);
        spnDot = (Spinner) rootView.findViewById(R.id.spnDot);
        edtTieuThu = (EditText) rootView.findViewById(R.id.edtTieuThu);
        Spinner spnTo = (Spinner) rootView.findViewById(R.id.spnTo);
        spnFilter = (Spinner) rootView.findViewById(R.id.spnFilter);
        Button btnXem = (Button) rootView.findViewById(R.id.btnXem);
        lstView = (ListView) rootView.findViewById(R.id.lstView);
        txtTongHD = (TextView) rootView.findViewById(R.id.txtTongHD);
        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);

        try {
            if (CLocal.jsonNam != null && CLocal.jsonNam.length() > 0) {
                spnName_Nam = new ArrayList<>();
                for (int i = 0; i < CLocal.jsonNam.length(); i++) {
                    JSONObject jsonObject = CLocal.jsonNam.getJSONObject(i);
                    spnName_Nam.add(jsonObject.getString("Nam"));
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spnName_Nam);
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

        if (CLocal.Doi) {
            layoutTo.setVisibility(View.VISIBLE);
            try {
                if (CLocal.jsonTo != null && CLocal.jsonTo.length() > 0) {
                    spnID_To = new ArrayList<>();
                    spnName_To = new ArrayList<>();
                    spnID_To.add("0");
                    spnName_To.add("Tất Cả");
                    for (int i = 0; i < CLocal.jsonTo.length(); i++) {
                        JSONObject jsonObject = CLocal.jsonTo.getJSONObject(i);
                        if (Boolean.parseBoolean(jsonObject.getString("HanhThu"))) {
                            spnID_To.add(jsonObject.getString("MaTo"));
                            spnName_To.add(jsonObject.getString("TenTo"));
                        }
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spnName_To);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnTo.setAdapter(adapter);
                //loaddata đợt
                if (CLocal.MaTo.equals("1") || CLocal.MaTo.equals("2")) {
                    //tân bình
                    ArrayAdapter<CharSequence> adapterDot = ArrayAdapter.createFromResource(getActivity(), R.array.dotTB_array, android.R.layout.simple_spinner_item);
                    adapterDot.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnDot.setAdapter(adapterDot);
                } else if (CLocal.MaTo.equals("3") || CLocal.MaTo.equals("4")) {
                    //tân phú
                    ArrayAdapter<CharSequence> adapterDot = ArrayAdapter.createFromResource(getActivity(), R.array.dotTP_array, android.R.layout.simple_spinner_item);
                    adapterDot.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnDot.setAdapter(adapterDot);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            layoutTo.setVisibility(View.GONE);
        }

        spnTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTo = spnID_To.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

        lstView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 1)
                    floatingActionButton.show();
                else
                    floatingActionButton.hide();
            }
        });

        lstView.setOnItemLongClickListener((parent, view, position, id) -> {
            TextView STT = (TextView) view.findViewById(R.id.lvSTT);
            int i = Integer.parseInt(STT.getText().toString()) - 1;
            CLocal.indexPosition = i;
            Intent intent;
            intent = new Intent(getActivity(), ActivityDocSo_GhiChiSo.class);
            CLocal.STT = i;
            intent.putExtra("QuanLy", "true");
            startActivity(intent);
            return false;
        });

        floatingActionButton.setOnClickListener(v -> lstView.smoothScrollToPosition(0));

        btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CLocal.checkNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "Không có Internet", Toast.LENGTH_LONG).show();
                    return;
                }
                ProgressDialog progressDialog;
                progressDialog = new ProgressDialog(getActivity());
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
                    try {
                        if (CLocal.Doi)
                            if (selectedTo.equals("0")) {
                                for (int i = 1; i < spnID_To.size(); i++) {
                                    JSONArray jsonResult = new JSONArray(ws.getDS_BatThuong(spnID_To.get(i), spnNam.getSelectedItem().toString(), spnKy.getSelectedItem().toString(), spnDot.getSelectedItem().toString()));
                                    for (int j = 0; j < jsonResult.length(); j++) {
                                        JSONObject jsonObject = jsonResult.getJSONObject(j);
                                        jsonArray.put(jsonObject);
                                    }
                                }
                            } else {
                                jsonArray = new JSONArray(ws.getDS_BatThuong(selectedTo, spnNam.getSelectedItem().toString(), spnKy.getSelectedItem().toString(), spnDot.getSelectedItem().toString()));
                            }
                        else
                            jsonArray = new JSONArray(ws.getDS_BatThuong(CLocal.MaTo, spnNam.getSelectedItem().toString(), spnKy.getSelectedItem().toString(), spnDot.getSelectedItem().toString()));
                        //khởi tạo ArrayList CEntityParent
                        lstEParent = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            CEntityParent enParent = new CEntityParent();
                            ///thiết lập khởi tạo 1 lần đầu để sort
                            enParent.setID(jsonObject.getString("DocSoID").replace("null", ""));

                            if (!jsonObject.getString("MLT").replace("null", "").equals("")) {
                                String strMLT = new StringBuffer(jsonObject.getString("MLT").replace("null", "")).insert(4, " ").insert(2, " ").toString();
                                enParent.setMLT(strMLT);
                            }
                            if (!jsonObject.getString("DanhBo").replace("null", "").equals("")) {
                                String strDanhBo = new StringBuffer(jsonObject.getString("DanhBo").replace("null", "")).insert(7, " ").insert(4, " ").toString();
                                enParent.setDanhBo(strDanhBo);
                            }

                            enParent.setHoTen(jsonObject.getString("HoTen").replace("null", ""));
                            enParent.setDiaChi(jsonObject.getString("DiaChi").replace("null", ""));
                            enParent.setSoNha(jsonObject.getString("SoNha").replace("null", ""));
                            enParent.setTenDuong(jsonObject.getString("TenDuong").replace("null", ""));
                            enParent.setHieu(jsonObject.getString("Hieu").replace("null", ""));
                            enParent.setCo(jsonObject.getString("Co").replace("null", ""));
                            enParent.setSoThan(jsonObject.getString("SoThan").replace("null", ""));
                            enParent.setViTri(jsonObject.getString("ViTri").replace("null", ""));
                            enParent.setViTriNgoai(Boolean.parseBoolean(jsonObject.getString("ViTriNgoai").replace("null", "")));
                            enParent.setViTriHop(Boolean.parseBoolean(jsonObject.getString("ViTriHop").replace("null", "")));
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
                            enParent.setChiSo0In(enParent.getChiSo0());
                            enParent.setCode0(jsonObject.getString("Code0").replace("null", ""));
                            enParent.setTieuThu0(jsonObject.getString("TieuThu0").replace("null", ""));
                            enParent.setChiSo1(jsonObject.getString("ChiSo1").replace("null", ""));
                            enParent.setCode1(jsonObject.getString("Code1").replace("null", ""));
                            enParent.setTieuThu1(jsonObject.getString("TieuThu1").replace("null", ""));
                            enParent.setChiSo2(jsonObject.getString("ChiSo2").replace("null", ""));
                            enParent.setCode2(jsonObject.getString("Code2").replace("null", ""));
                            enParent.setTieuThu2(jsonObject.getString("TieuThu2").replace("null", ""));
                            enParent.setGieng(Boolean.parseBoolean(jsonObject.getString("Gieng").replace("null", "")));
                            enParent.setKhoaTu(Boolean.parseBoolean(jsonObject.getString("KhoaTu").replace("null", "")));
                            enParent.setAmSau(Boolean.parseBoolean(jsonObject.getString("AmSau").replace("null", "")));
                            enParent.setXayDung(Boolean.parseBoolean(jsonObject.getString("XayDung").replace("null", "")));
                            enParent.setDutChi_Goc(Boolean.parseBoolean(jsonObject.getString("DutChi_Goc").replace("null", "")));
                            enParent.setDutChi_Than(Boolean.parseBoolean(jsonObject.getString("DutChi_Than").replace("null", "")));
                            enParent.setNgapNuoc(Boolean.parseBoolean(jsonObject.getString("NgapNuoc").replace("null", "")));
                            enParent.setKetTuong(Boolean.parseBoolean(jsonObject.getString("KetTuong").replace("null", "")));
                            enParent.setLapKhoaGoc(Boolean.parseBoolean(jsonObject.getString("LapKhoaGoc").replace("null", "")));
                            enParent.setBeHBV(Boolean.parseBoolean(jsonObject.getString("BeHBV").replace("null", "")));
                            enParent.setBeNapMatNapHBV(Boolean.parseBoolean(jsonObject.getString("BeNapMatNapHBV").replace("null", "")));
                            enParent.setGayTayVan(Boolean.parseBoolean(jsonObject.getString("GayTayVan").replace("null", "")));
                            enParent.setMauSacChiGoc(jsonObject.getString("MauSacChiGoc").replace("null", ""));
                            enParent.setDienThoai(jsonObject.getString("DienThoai").replace("null", ""));
                            enParent.setID(jsonObject.getString("DocSoID").replace("null", ""));
                            enParent.setNam(jsonObject.getString("Nam").replace("null", ""));
                            enParent.setKy(jsonObject.getString("Ky").replace("null", ""));
                            enParent.setDot(jsonObject.getString("Dot").replace("null", ""));
                            enParent.setNgayThuTien(jsonObject.getString("NgayThuTien").replace("null", ""));
                            if (jsonObject.has("CuaHangThuHo"))
                                enParent.setCuaHangThuHo(jsonObject.getString("CuaHangThuHo").replace("null", ""));
                            enParent.setTuNgay(jsonObject.getString("TuNgay").replace("null", ""));
                            enParent.setDenNgay(jsonObject.getString("DenNgay").replace("null", ""));
                            enParent.setGhiChu(jsonObject.getString("GhiChu").replace("null", ""));
                            enParent.setKinhDoanh(jsonObject.getString("KinhDoanh").replace("null", ""));
                            enParent.setPhanMay(jsonObject.getString("PhanMay").replace("null", ""));
                            enParent.setChuBao(Boolean.parseBoolean(jsonObject.getString("ChuBao").replace("null", "")));
                            enParent.setTinhTrang(jsonObject.getString("TinhTrang").replace("null", ""));
                            lstEParent.add(enParent);
                        }
                    } catch (Exception ex) {
                        error = ex.getMessage();
                    }

                    String finalError = error;
                    handler.post(() -> {
                        //UI Thread work here
                        progressDialog.dismiss();
                        try {
                            assert finalError != null;
                            if (!finalError.equals(""))
                                CLocal.showPopupMessage(getActivity(), finalError, "center");
                            else {
                                loadListView();
                            }
                        } catch (Exception ex) {
                            CLocal.showPopupMessage(getActivity(), ex.getMessage(), "center");
                        }
                    });
                });
            }
        });

        return rootView;
    }

    public void loadListView() {
        try {
            Tong = 0;
            CLocal.listDocSoView = new ArrayList<>();
            lstVParent = new ArrayList<>();
            switch (spnFilter.getSelectedItem().toString()) {
                case "HĐ=0":
                    if (lstEParent != null && lstEParent.size() > 0) {
                        for (int i = 0; i < lstEParent.size(); i++) {
                            if (lstEParent.get(i).getTieuThuMoi().equals("0")) {
                                CLocal.listDocSoView.add(lstEParent.get(i));
                                addViewParent(lstEParent.get(i));
                            }
                        }
                    }
                    break;
                case "Bất Thường Tăng":
                    if (lstEParent != null && lstEParent.size() > 0) {
                        for (int i = 0; i < lstEParent.size(); i++) {
                            if (!lstEParent.get(i).getTieuThuMoi().equals("0")
                                    && Integer.parseInt(lstEParent.get(i).getTieuThuMoi()) >= Integer.parseInt(edtTieuThu.getText().toString())
                                    && Integer.parseInt(lstEParent.get(i).getTieuThuMoi()) >= Integer.parseInt(lstEParent.get(i).getTBTT()) * 1.3) {
                                CLocal.listDocSoView.add(lstEParent.get(i));
                                addViewParent(lstEParent.get(i));
                            }
                        }
                    }
                    break;
                case "Bất Thường Giảm":
                    if (lstEParent != null && lstEParent.size() > 0) {
                        for (int i = 0; i < lstEParent.size(); i++) {
                            if (!lstEParent.get(i).getTieuThuMoi().equals("0")
                                    && Integer.parseInt(lstEParent.get(i).getTieuThuMoi()) >= Integer.parseInt(edtTieuThu.getText().toString())
                                    && Integer.parseInt(lstEParent.get(i).getTieuThuMoi()) <= Integer.parseInt(lstEParent.get(i).getTBTT()) * (1 - 0.3)
                            ) {
                                CLocal.listDocSoView.add(lstEParent.get(i));
                                addViewParent(lstEParent.get(i));
                            }
                        }
                    }
                    break;
                default:
                    if (lstEParent != null && lstEParent.size() > 0) {
                        for (int i = 0; i < lstEParent.size(); i++) {
                            CLocal.listDocSoView.add(lstEParent.get(i));
                            addViewParent(lstEParent.get(i));
                        }
                    }
                    break;
            }
            CustomAdapterListView customAdapterListView = new CustomAdapterListView(getActivity(), lstVParent);
            lstView.setAdapter(customAdapterListView);
            txtTongHD.setText("ĐC:" + CLocal.formatMoney(String.valueOf(Tong), ""));
            lstView.setSelection(CLocal.indexPosition);
        } catch (Exception ex) {
            CLocal.showToastMessage(getActivity(), ex.getMessage());
        }
    }

    public void addViewParent(CEntityParent enParent) {
        try {
            CViewParent enViewParent = new CViewParent();
            enViewParent.setModifyDate(enParent.getModifyDate());
            enViewParent.setSTT(String.valueOf(lstVParent.size() + 1));
            enViewParent.setID(String.valueOf(enParent.getID()));
            enViewParent.setRow1a(enParent.getMLT());
            if (!enParent.getCodeMoi().equals(""))
                enViewParent.setRow1b(enParent.getCodeMoi() + "-" + enParent.getChiSoMoi() + "-" + enParent.getTieuThuMoi());
            enViewParent.setRow2a(enParent.getDanhBo());
            enViewParent.setRow2b(enParent.getModifyDate());
            enViewParent.setRow3a(enParent.getHoTen());
            enViewParent.setRow4a(enParent.getSoNha() + " " + enParent.getTenDuong());
            Tong++;
            lstVParent.add(enViewParent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
