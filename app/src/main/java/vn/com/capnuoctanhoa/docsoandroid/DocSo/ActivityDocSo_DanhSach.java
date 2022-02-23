package vn.com.capnuoctanhoa.docsoandroid.DocSo;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import vn.com.capnuoctanhoa.docsoandroid.Class.CEntityChild;
import vn.com.capnuoctanhoa.docsoandroid.Class.CEntityParent;
import vn.com.capnuoctanhoa.docsoandroid.Class.CLocal;
import vn.com.capnuoctanhoa.docsoandroid.Class.CLocation;
import vn.com.capnuoctanhoa.docsoandroid.Class.CSort;
import vn.com.capnuoctanhoa.docsoandroid.Class.CViewChild;
import vn.com.capnuoctanhoa.docsoandroid.Class.CViewParent;
import vn.com.capnuoctanhoa.docsoandroid.Class.CWebservice;
import vn.com.capnuoctanhoa.docsoandroid.Class.CustomAdapterListView;
import vn.com.capnuoctanhoa.docsoandroid.R;

public class ActivityDocSo_DanhSach extends AppCompatActivity {
    private Spinner spnFilter, spnSort, spnNhanVien;
    private ListView lstView;
    private CustomAdapterListView customAdapterListView;
    private TextView txtTongHD;
    private long TongDC;
    private ArrayList<CViewParent> listParent;
    private ArrayList<CViewChild> listChild;
    private FloatingActionButton floatingActionButton;
    private ImageView imgviewThuTienHD0, imgviewThongKe;

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
        spnNhanVien = (Spinner) findViewById(R.id.spnNhanVien);
        lstView = (ListView) findViewById(R.id.lstView);
        txtTongHD = (TextView) findViewById(R.id.txtTongHD);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        imgviewThuTienHD0 = (ImageView) findViewById(R.id.imgviewThuTienHD0);
        imgviewThongKe = (ImageView) findViewById(R.id.imgviewThongKe);

//        imgviewThuTienHD0.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDocSo_DanhSach.this);
//                    builder.setMessage("Bạn có chắc chắn Thu Tiền HĐ=0?")
//                            .setCancelable(false)
//                            .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    if (CLocal.listDocSoView != null && CLocal.listDocSoView.size() > 0) {
//                                        MyAsyncTask_XuLyTrucTiep_Extra myAsyncTask_xuLyTrucTiep_hd0 = new MyAsyncTask_XuLyTrucTiep_Extra();
//                                        myAsyncTask_xuLyTrucTiep_hd0.execute("HD0");
//                                    }
//                                }
//                            })
//                            .setPositiveButton("No", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            });
//                    AlertDialog alert = builder.create();
//                    alert.show();
//                    Button btnPositive = alert.getButton(AlertDialog.BUTTON_POSITIVE);
//                    Button btnNegative = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
//                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
//                    layoutParams.weight = 10;
//                    layoutParams.gravity = Gravity.CENTER;
//                    btnPositive.setLayoutParams(layoutParams);
//                    btnNegative.setLayoutParams(layoutParams);
//                } catch (Exception ex) {
//                    CLocal.showPopupMessage(ActivityDocSo_DanhSach.this, ex.getMessage(), "left");
//                }
//            }
//        });
//
//        imgviewThongKe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int TongHD = 0, TongHDThuHo = 0, TongHDDaThu = 0, TongHDTon = 0;
//                long TongCong = 0, TongCongThuHo = 0, TongCongDaThu = 0, TongCongTon = 0;
//                if (CLocal.listDocSo != null && CLocal.listDocSo.size() > 0) {
//                    for (int i = 0; i < CLocal.listDocSo.size(); i++) {
//                        for (int j = 0; j < CLocal.listDocSo.get(i).getLstHoaDon().size(); j++) {
//                            //tổng
//                            TongHD++;
//                            TongCong += Long.parseLong(CLocal.listDocSo.get(i).getLstHoaDon().get(j).getTongCong());
//                            //thu hộ
//                            if ((CLocal.listDocSo.get(i).getLstHoaDon().get(j).isDangNgan_DienThoai() == true && CLocal.listDocSo.get(i).getLstHoaDon().get(j).getMaNV_DangNgan().equals(CLocal.MaNV) == false)
//                                    || (CLocal.listDocSo.get(i).getLstHoaDon().get(j).isGiaiTrach() == true && CLocal.listDocSo.get(i).getLstHoaDon().get(j).isDangNgan_DienThoai() == false)
//                                    || CLocal.listDocSo.get(i).getLstHoaDon().get(j).isTamThu() == true || CLocal.listDocSo.get(i).getLstHoaDon().get(j).isThuHo() == true) {
//                                TongHDThuHo++;
//                                TongCongThuHo += Long.parseLong(CLocal.listDocSo.get(i).getLstHoaDon().get(j).getTongCong());
//                            }
//                            //đã thu
//                            if (CLocal.listDocSo.get(i).getLstHoaDon().get(j).isDangNgan_DienThoai() == true && CLocal.listDocSo.get(i).getLstHoaDon().get(j).getMaNV_DangNgan().equals(CLocal.MaNV) == true) {
//                                TongHDDaThu++;
//                                TongCongDaThu += Long.parseLong(CLocal.listDocSo.get(i).getLstHoaDon().get(j).getTongCong());
//                            }
//                            //tồn
//                            if ((CLocal.listDocSo.get(i).getLstHoaDon().get(j).isDangNgan_DienThoai() == false)
//                                    && CLocal.listDocSo.get(i).getLstHoaDon().get(j).isGiaiTrach() == false && CLocal.listDocSo.get(i).getLstHoaDon().get(j).isTamThu() == false && CLocal.listDocSo.get(i).getLstHoaDon().get(j).isThuHo() == false) {
//                                TongHDTon++;
//                                TongCongTon += Long.parseLong(CLocal.listDocSo.get(i).getLstHoaDon().get(j).getTongCong());
//                            }
//                        }
//                    }
//                }
//                CLocal.showPopupMessage(ActivityDocSo_DanhSach.this, "Tổng: " + CLocal.formatMoney(String.valueOf(TongHD), "hđ") + " = " + CLocal.formatMoney(String.valueOf(TongCong), "đ")
//                        + "\n\nThu Hộ: " + CLocal.formatMoney(String.valueOf(TongHDThuHo), "hđ") + " = " + CLocal.formatMoney(String.valueOf(TongCongThuHo), "đ")
//                        + "\n\nĐã Thu: " + CLocal.formatMoney(String.valueOf(TongHDDaThu), "hđ") + " = " + CLocal.formatMoney(String.valueOf(TongCongDaThu), "đ")
//                        + "\n\nTồn: " + CLocal.formatMoney(String.valueOf(TongHDTon), "hđ") + " = " + CLocal.formatMoney(String.valueOf(TongCongTon), "đ"), "right");
//            }
//        });

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
                if (listParent != null && listParent.size() > 0)
                    switch (spnSort.getSelectedItem().toString()) {
                        case "Thời Gian Tăng":
                            Collections.sort(listParent, new CSort("ModifyDate", -1));
                            break;
                        case "Thời Gian Giảm":
                            Collections.sort(listParent, new CSort("ModifyDate", 1));
                            break;
                        default:
                            Collections.sort(listParent, new CSort("", -1));
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

        lstView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
                TextView STT = (TextView) view.findViewById(R.id.lvSTT);
                int i = Integer.parseInt(STT.getText().toString()) - 1;
                CLocal.indexPosition = i;
                Intent intent;
                intent = new Intent(getApplicationContext(), ActivityDocSo_GhiChiSo.class);
                intent.putExtra("STT", String.valueOf(i));
                startActivityForResult(intent, 1);
                return false;
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lstView.smoothScrollToPosition(0);
            }
        });

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
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                return false;
            }
        });
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
                startActivityForResult(intent, 1);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadListView() {
        try {
            listParent = new ArrayList<CViewParent>();
            CLocal.listDocSoView = new ArrayList<CEntityParent>();
            TongDC  = 0;
            switch (spnFilter.getSelectedItem().toString()) {
                case "Chưa Đọc":
//                    if (CLocal.listDocSo != null && CLocal.listDocSo.size() > 0) {
//                        for (int i = 0; i < CLocal.listDocSo.size(); i++) {
//                            if (CLocal.listDocSo.get(i).getLstHoaDon().get(0).getInPhieuBao_Ngay().equals("") == true
//                                    && CLocal.listDocSo.get(i).getLstHoaDon().get(0).getInPhieuBao2_Ngay().equals("") == true
//                                    && CLocal.listDocSo.get(i).getLstHoaDon().get(0).getTBDongNuoc_Ngay().equals("") == true) {
//                                CLocal.listDocSoView.add(CLocal.listDocSo.get(i));
//                                addViewParent(CLocal.listDocSo.get(i));
//                            }
//                        }
//                    }
                    break;
                case "Đã Đọc":
//                    if (CLocal.listDocSo != null && CLocal.listDocSo.size() > 0) {
//                        for (int i = 0; i < CLocal.listDocSo.size(); i++) {
//                            if (CLocal.listDocSo.get(i).isDangNgan_DienThoai() == true) {
//                                CLocal.listDocSoView.add(CLocal.listDocSo.get(i));
//                                addViewParent(CLocal.listDocSo.get(i));
//                            }
//                        }
//                    }
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
            customAdapterListView = new CustomAdapterListView(this, listParent);
            lstView.setAdapter(customAdapterListView);
            txtTongHD.setText("ĐC:" + CLocal.formatMoney(String.valueOf(TongDC), ""));
            lstView.setSelection(CLocal.indexPosition);
        } catch (Exception ex) {
            CLocal.showToastMessage(ActivityDocSo_DanhSach.this, ex.getMessage());
        }
    }

    public void addViewParent(CEntityParent enParent) {
        try {
            CViewParent enViewParent = new CViewParent();
            enViewParent.setModifyDate(enParent.getModifyDate());
            enViewParent.setSTT(String.valueOf(listParent.size() + 1));
            enViewParent.setID(String.valueOf(enParent.getID()));

            enViewParent.setRow1a(enParent.getMLT());
            enViewParent.setRow2a(enParent.getDanhBo());
            enViewParent.setRow3a(enParent.getHoTen());
            enViewParent.setRow4a(enParent.getDiaChi());

            TongDC++;

            listParent.add(enViewParent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK)
                loadListView();
        }
    }

}
