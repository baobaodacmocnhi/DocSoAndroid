package vn.com.capnuoctanhoa.docsoandroid.DocSo;

import androidx.appcompat.app.AppCompatActivity;
import vn.com.capnuoctanhoa.docsoandroid.Class.CCode;
import vn.com.capnuoctanhoa.docsoandroid.Class.CEntityParent;
import vn.com.capnuoctanhoa.docsoandroid.Class.CLocal;
import vn.com.capnuoctanhoa.docsoandroid.Class.CWebservice;
import vn.com.capnuoctanhoa.docsoandroid.Class.CustomAdapterSpinner;
import vn.com.capnuoctanhoa.docsoandroid.R;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityDocSo_GhiChu extends AppCompatActivity {
    private Integer STT = -1;
    private EditText edtSoNha, edtTenDuong;
    private Spinner spnViTri1, spnViTri2;
    private ImageView ivLuu;
    private CheckBox chkGieng;
    private CWebservice ws;
    private ArrayList<String> spnName_ViTriDHN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_so_ghi_chu);
        edtSoNha = (EditText) findViewById(R.id.edtSoNha);
        edtTenDuong = (EditText) findViewById(R.id.edtTenDuong);
        spnViTri1 = (Spinner) findViewById(R.id.spnViTri1);
        spnViTri2 = (Spinner) findViewById(R.id.spnViTri2);
        chkGieng = (CheckBox) findViewById(R.id.chkGieng);
        ivLuu = (ImageView) findViewById(R.id.ivLuu);

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

        ivLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CLocal.checkNetworkAvailable(ActivityDocSo_GhiChu.this) == false) {
                    CLocal.showToastMessage(ActivityDocSo_GhiChu.this, "Không có Internet");
                    return;
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            STT = Integer.parseInt(getIntent().getStringExtra("STT"));
            if (STT > -1) {
                if (CLocal.listDocSoView != null && CLocal.listDocSoView.size() > 0) {
                    ArrayList<String> arrayList = new ArrayList<String>();
                    if (STT >= 0 && STT < CLocal.listDocSoView.size()) {
                        CEntityParent item = CLocal.listDocSoView.get(STT);
                        edtSoNha.setText(item.getSoNha());
                        edtTenDuong.setText(item.getTenDuong());
                        if (item.getViTri1().equals("") == false)
                            spnViTri1.setSelection(((ArrayAdapter) spnViTri1.getAdapter()).getPosition(item.getViTri1()));
                        if (item.getViTri2().equals("") == false)
                            spnViTri2.setSelection(((ArrayAdapter) spnViTri2.getAdapter()).getPosition(item.getViTri2()));
                        chkGieng.setChecked(item.isGieng());
                    }
                }
            }
        } catch (Exception ex) {
            CLocal.showToastMessage(ActivityDocSo_GhiChu.this, ex.getMessage());
        }
    }
}