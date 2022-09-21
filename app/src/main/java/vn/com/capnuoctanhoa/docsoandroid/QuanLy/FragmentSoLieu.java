package vn.com.capnuoctanhoa.docsoandroid.QuanLy;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import vn.com.capnuoctanhoa.docsoandroid.Class.CLocal;
import vn.com.capnuoctanhoa.docsoandroid.Class.CWebservice;
import vn.com.capnuoctanhoa.docsoandroid.R;

public class FragmentSoLieu extends Fragment {
    private Spinner spnNam;
    private Spinner spnKy;
    private Spinner spnDot;
    private ListView lstView;
    private FloatingActionButton floatingActionButton;
    private TextView txtTongHD;
    private RadioButton radSanLuong, radHD0;
    private ArrayList<String> spnName_Nam;
    private int S1 = 0, S2 = 0, S3 = 0, S4 = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_so_lieu, container, false);

        spnNam = (Spinner) rootView.findViewById(R.id.spnNam);
        spnKy = (Spinner) rootView.findViewById(R.id.spnKy);
        spnDot = (Spinner) rootView.findViewById(R.id.spnDot);
        Button btnXem = (Button) rootView.findViewById(R.id.btnXem);
        lstView = (ListView) rootView.findViewById(R.id.lstView);
        txtTongHD = (TextView) rootView.findViewById(R.id.txtTongHD);
        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);
        radSanLuong = (RadioButton) rootView.findViewById(R.id.radSanLuong);
        radHD0 = (RadioButton) rootView.findViewById(R.id.radHD0);
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

        floatingActionButton.setOnClickListener(v -> lstView.smoothScrollToPosition(0));

        btnXem.setOnClickListener(v -> {
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
                    if (radSanLuong.isChecked())
                        jsonArray = new JSONArray(ws.getDS_SoLieu_SanLuong(spnNam.getSelectedItem().toString(), spnKy.getSelectedItem().toString(), spnDot.getSelectedItem().toString()));
                    else if (radHD0.isChecked())
                        jsonArray = new JSONArray(ws.getDS_SoLieu_HD0(spnNam.getSelectedItem().toString(), spnKy.getSelectedItem().toString(), spnDot.getSelectedItem().toString()));
                } catch (Exception ex) {
                    error = ex.getMessage();
                }

                String finalError = error;
                JSONArray finalJsonArray = jsonArray;
                handler.post(() -> {
                    //UI Thread work here
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    try {
                        if (!finalError.equals(""))
                            CLocal.showPopupMessage(getActivity(), finalError, "center");
                        else {
                            S1 = S2 = S3 = S4 = 0;
                            List<String> data = new ArrayList<>();
                            for (int i = 0; i < finalJsonArray.length(); i++) {
                                JSONObject jsonObject = finalJsonArray.getJSONObject(i);
                                String stringBuffer = "Tổ: " + jsonObject.getString("To").replace("null", "");
                                if (radSanLuong.isChecked()) {
                                    stringBuffer += "\n\n    ĐHN: " + jsonObject.getString("DHN").replace("null", "")
                                            + " | " + jsonObject.getString("DHNTruoc").replace("null", "")
                                            + " | " + (Integer.parseInt(jsonObject.getString("DHN").replace("null", "")) - Integer.parseInt(jsonObject.getString("DHNTruoc").replace("null", "")))
                                            + "\n    Sản Lượng: " + jsonObject.getString("SanLuong").replace("null", "")
                                            + " | " + jsonObject.getString("SanLuongTruoc").replace("null", "")
                                            + " | " + (Integer.parseInt(jsonObject.getString("SanLuong").replace("null", "")) - Integer.parseInt(jsonObject.getString("SanLuongTruoc").replace("null", "")))+"\n";
                                    S1 += Integer.parseInt(jsonObject.getString("DHN").replace("null", ""));
                                    S2 += Integer.parseInt(jsonObject.getString("DHNTruoc").replace("null", ""));
                                    S3 += Integer.parseInt(jsonObject.getString("SanLuong").replace("null", ""));
                                    S4 += Integer.parseInt(jsonObject.getString("SanLuongTruoc").replace("null", ""));
                                } else if (radHD0.isChecked()) {
                                    stringBuffer += "\n\n    0m3: " + jsonObject.getString("HD0").replace("null", "")
                                            + " | " + jsonObject.getString("HD0Truoc").replace("null", "")
                                            + " | " + (Integer.parseInt(jsonObject.getString("HD0").replace("null", "")) - Integer.parseInt(jsonObject.getString("HD0Truoc").replace("null", "")))
                                            + "\n    1->4m3: " + jsonObject.getString("HD4").replace("null", "")
                                            + " | " + jsonObject.getString("HD4Truoc").replace("null", "")
                                            + " | " + (Integer.parseInt(jsonObject.getString("HD4").replace("null", "")) - Integer.parseInt(jsonObject.getString("HD4Truoc").replace("null", "")))+"\n";
                                    S1 += Integer.parseInt(jsonObject.getString("HD0").replace("null", ""));
                                    S2 += Integer.parseInt(jsonObject.getString("HD0Truoc").replace("null", ""));
                                    S3 += Integer.parseInt(jsonObject.getString("HD4").replace("null", ""));
                                    S4 += Integer.parseInt(jsonObject.getString("HD4Truoc").replace("null", ""));
                                }
                                data.add(stringBuffer);
                            }
                            //dòng tổng
                            String stringBuffer = "Tổng Cộng: ";
                            if (radSanLuong.isChecked())
                                stringBuffer += "\n\n    ĐHN: " + S1
                                        + " | " + S2
                                        + " | " + (S1 - S2)
                                        + "\n    Sản Lượng: " + S3
                                        + " | " + S4
                                        + " | " + (S3 - S4);
                            else if (radHD0.isChecked())
                                stringBuffer += "\n\n    0m3: " + S1
                                        + " | " + S2
                                        + " | " + (S1 - S2)
                                        + "\n    1->4m3: " + S3
                                        + " | " + S4
                                        + " | " + (S3 - S4);
                            data.add(stringBuffer);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_list_item_1,
                                    data);
                            lstView.setAdapter(adapter);
                        }
                    } catch (Exception ex) {
                        CLocal.showPopupMessage(getActivity(), ex.getMessage(), "center");
                    }
                });
            });
        });

        return rootView;
    }
}