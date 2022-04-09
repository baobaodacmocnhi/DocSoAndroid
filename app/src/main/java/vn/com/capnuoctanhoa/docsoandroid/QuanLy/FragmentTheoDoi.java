package vn.com.capnuoctanhoa.docsoandroid.QuanLy;

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
import android.widget.LinearLayout;
import android.widget.ListView;
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

import androidx.fragment.app.Fragment;

import vn.com.capnuoctanhoa.docsoandroid.Class.CLocal;
import vn.com.capnuoctanhoa.docsoandroid.Class.CWebservice;
import vn.com.capnuoctanhoa.docsoandroid.R;

public class FragmentTheoDoi extends Fragment {
    private View rootView;
    private Spinner spnNam, spnKy, spnDot, spnTo;
    private Button btnXem;
    private ListView lstView;
    private LinearLayout layoutTo;
    private FloatingActionButton floatingActionButton;
    private TextView txtTongHD;
    private ArrayList<String> spnID_To, spnName_To, spnName_Nam;
    private String selectedTo = "";
    private int Tong = 0, DaDoc = 0, ChuaDoc = 0, CodeF = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_theo_doi, container, false);

        layoutTo = (LinearLayout) rootView.findViewById(R.id.layoutTo);
        spnNam = (Spinner) rootView.findViewById(R.id.spnNam);
        spnKy = (Spinner) rootView.findViewById(R.id.spnKy);
        spnDot = (Spinner) rootView.findViewById(R.id.spnDot);
        spnTo = (Spinner) rootView.findViewById(R.id.spnTo);
        btnXem = (Button) rootView.findViewById(R.id.btnXem);
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

        btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!CLocal.checkNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "Không có Internet", Toast.LENGTH_LONG).show();
                    return;
                }
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
                                    JSONArray jsonResult = new JSONArray(ws.getDS_TheoDoi(spnID_To.get(i), spnNam.getSelectedItem().toString(), spnKy.getSelectedItem().toString(), spnDot.getSelectedItem().toString()));
                                    for (int j = 0; j < jsonResult.length(); j++) {
                                        JSONObject jsonObject = jsonResult.getJSONObject(j);
                                        jsonArray.put(jsonObject);
                                    }
                                }
                            } else {
                                jsonArray = new JSONArray(ws.getDS_TheoDoi(selectedTo, spnNam.getSelectedItem().toString(), spnKy.getSelectedItem().toString(), spnDot.getSelectedItem().toString()));
                            }
                        else
                            jsonArray = new JSONArray(ws.getDS_TheoDoi(CLocal.MaTo, spnNam.getSelectedItem().toString(), spnKy.getSelectedItem().toString(), spnDot.getSelectedItem().toString()));
                    } catch (Exception ex) {
                        error = ex.getMessage();
                    }

                    String finalError = error;
                    JSONArray finalJsonArray = jsonArray;
                    handler.post(() -> {
                        //UI Thread work here
                        try {
                            if (!finalError.equals(""))
                                CLocal.showPopupMessage(getActivity(), finalError, "center");
                            else {
                                List<String> data = new ArrayList<>();
                                for (int i = 0; i < finalJsonArray.length(); i++) {
                                    JSONObject jsonObject = finalJsonArray.getJSONObject(i);
                                    StringBuffer stringBuffer = new StringBuffer();
                                    stringBuffer.append("Máy: " + jsonObject.getString("May").replace("null", ""));
                                    stringBuffer.append(" | Tổng: " + jsonObject.getString("Tong").replace("null", ""));
                                    stringBuffer.append("\nĐã Đọc: " + jsonObject.getString("DaDoc").replace("null", ""));
                                    stringBuffer.append(" | Chưa Đọc: " + jsonObject.getString("ChuaDoc").replace("null", ""));
                                    stringBuffer.append(" | Code F: " + jsonObject.getString("CodeF").replace("null", ""));
                                    data.add(stringBuffer.toString());
                                    Tong += Integer.parseInt(jsonObject.getString("Tong").replace("null", ""));
                                    DaDoc += Integer.parseInt(jsonObject.getString("DaDoc").replace("null", ""));
                                    ChuaDoc += Integer.parseInt(jsonObject.getString("ChuaDoc").replace("null", ""));
                                    CodeF += Integer.parseInt(jsonObject.getString("CodeF").replace("null", ""));
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                        android.R.layout.simple_list_item_1,
                                        data);
                                lstView.setAdapter(adapter);
                                txtTongHD.setText(Tong+" | "+DaDoc+" | "+ChuaDoc+" | "+CodeF);
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

}
