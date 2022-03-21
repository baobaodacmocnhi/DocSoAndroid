package vn.com.capnuoctanhoa.docsoandroid.DocSo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONObject;

import java.util.ArrayList;

import vn.com.capnuoctanhoa.docsoandroid.Class.CLocal;
import vn.com.capnuoctanhoa.docsoandroid.Class.CWebservice;
import vn.com.capnuoctanhoa.docsoandroid.R;

public class ActivityDocSo_PhieuChuyen extends AppCompatActivity {
    private Spinner spnPhieuChuyen;
    private EditText edtGhiChu;
    private Button btnCapNhat;
    private ListView lstView;
    private CWebservice ws;
    private ArrayList<String> spnName_PhieuChuyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_so_phieu_chuyen);

        spnPhieuChuyen = (Spinner) findViewById(R.id.spnPhieuChuyen);
        edtGhiChu = (EditText) findViewById(R.id.edtGhiChu);
        btnCapNhat = (Button) findViewById(R.id.btnCapNhat);
        lstView = (ListView) findViewById(R.id.lstView);

        ws = new CWebservice();

        try {
            if (CLocal.jsonViTriDHN != null && CLocal.jsonViTriDHN.length() > 0) {
                spnName_PhieuChuyen = new ArrayList<>();
                for (int i = 0; i < CLocal.jsonViTriDHN.length(); i++) {
                    JSONObject jsonObject = CLocal.jsonViTriDHN.getJSONObject(i);
                    spnName_PhieuChuyen.add(jsonObject.getString("Name").replace("null", ""));
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, spnName_PhieuChuyen);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnPhieuChuyen.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}