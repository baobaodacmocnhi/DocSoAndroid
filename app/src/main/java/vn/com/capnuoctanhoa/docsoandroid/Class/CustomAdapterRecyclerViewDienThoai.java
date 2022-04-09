package vn.com.capnuoctanhoa.docsoandroid.Class;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

import vn.com.capnuoctanhoa.docsoandroid.R;

public class CustomAdapterRecyclerViewDienThoai extends RecyclerView.Adapter<CustomAdapterRecyclerViewDienThoai.RecyclerViewHolder> {
    private final Activity activity;
    private final ArrayList<CEntityParent> mDisplayedValues;
    private entityParentListener entityParentListener;

    public interface entityParentListener {
        void onClick(CEntityParent entityParent);
    }

    public void setClickItemListener(entityParentListener entityParentListener) {
        this.entityParentListener = entityParentListener;
    }

    public CustomAdapterRecyclerViewDienThoai(Activity activity, ArrayList<CEntityParent> mDisplayedValues) {
        super();
        this.activity = activity;
        this.mDisplayedValues = mDisplayedValues;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_listview_dienthoai, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        if (getItemCount() > 0) {
            final CEntityParent entityParent = mDisplayedValues.get(position);
            final int ID = position;
            final String DanhBo = entityParent.getDanhBo();
            holder.txtDanhBo.setText(entityParent.getDanhBo());
            holder.txtDienThoai.setText(entityParent.getDienThoai());
            holder.txtHoTen.setText(entityParent.getHoTen());
            if (DanhBo.length() != 11)
                holder.chkSoChinh.setVisibility(View.INVISIBLE);
            holder.chkSoChinh.setChecked(entityParent.isSoChinh());
            holder.txtGhiChu.setText(entityParent.getDiaChi());
            if (entityParent.isAnXoa())
                holder.imageButton.setVisibility(View.INVISIBLE);
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CLocal.showDialog(activity, "Xác nhận", ""
                            , "Cancel", (dialog, which) -> dialog.dismiss(), "Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    mDisplayedValues.remove(ID);
                                    //sđt
                                    if (DanhBo.length() == 11) {
                                        MyAsyncTask myAsyncTask = new MyAsyncTask();
                                        myAsyncTask.execute("Xoa", entityParent.getDanhBo(), entityParent.getDienThoai());
                                    } else {
                                        try {//downfile
                                            String Nam = "", Ky = "", Dot = "";
                                            if (CLocal.listDocSo != null && CLocal.listDocSo.size() > 0) {
                                                Nam = CLocal.listDocSo.get(0).getNam();
                                                Ky = CLocal.listDocSo.get(0).getKy();
                                                Dot = CLocal.listDocSo.get(0).getDot();
                                            }
                                            if ((Nam + "_" + Ky + "_" + Dot + ".txt").equals(entityParent.getDienThoai())) {
                                                CLocal.listDocSo = null;
                                                SharedPreferences.Editor editor = CLocal.sharedPreferencesre.edit();
                                                editor.putString("jsonDocSo", "");
                                                editor.apply();
                                            }
                                            CLocal.deleteFile(CLocal.pathAppDownload, entityParent.getDienThoai());
                                            CLocal.deleteFile(CLocal.pathAppPicture + "/" + entityParent.getDienThoai().replace(".txt", ""), "");
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    notifyDataSetChanged();
                                }
                            }, false);
                }
            });
            holder.itemView.setOnClickListener(v -> {
                try {
                    if (entityParent.getDanhBo().length() != 11)//downfile
                    {
                        String Nam = "", Ky = "", Dot = "";
                        if (CLocal.listDocSo != null && CLocal.listDocSo.size() > 0) {
                            Nam = CLocal.listDocSo.get(0).getNam();
                            Ky = CLocal.listDocSo.get(0).getKy();
                            Dot = CLocal.listDocSo.get(0).getDot();
                            CLocal.writeFile(CLocal.pathAppDownload, Nam + "_" + Ky + "_" + Dot + ".txt", new Gson().toJsonTree(CLocal.listDocSo).getAsJsonArray().toString());
                        }
                        SharedPreferences.Editor editor = CLocal.sharedPreferencesre.edit();
                        editor.putString("jsonDocSo", CLocal.readFile(CLocal.pathAppDownload, entityParent.getDienThoai()));
                        editor.apply();
                        CLocal.listDocSo = new Gson().fromJson(CLocal.sharedPreferencesre.getString("jsonDocSo", ""), new TypeToken<ArrayList<CEntityParent>>() {
                        }.getType());
                        if (CLocal.listDocSo.size() > 2000)
                            CLocal.listDocSo = null;

                        CLocal.showToastMessage(activity, "Đã load dữ liệu " + entityParent.getDienThoai());
                        Intent returnIntent = new Intent();
                        activity.setResult(Activity.RESULT_OK, returnIntent);
                        activity.finish();
                    } else if (entityParent.getDienThoai().length() == 10) {
                        entityParentListener.onClick(entityParent);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            holder.chkSoChinh.setOnCheckedChangeListener((buttonView, isChecked) -> {
                MyAsyncTask myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute("Sua", entityParent.getDanhBo(), entityParent.getDienThoai(), entityParent.getHoTen(), String.valueOf(isChecked));
            });

        }
    }

    @Override
    public int getItemCount() {
        return mDisplayedValues == null ? 0 : mDisplayedValues.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView ID, txtDanhBo, txtDienThoai, txtHoTen, txtGhiChu;
        ImageButton imageButton;
        CheckBox chkSoChinh;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ID = (TextView) itemView.findViewById(R.id.ID);
            txtDanhBo = (TextView) itemView.findViewById(R.id.txtDanhBo);
            txtDienThoai = (TextView) itemView.findViewById(R.id.txtDienThoai);
            txtHoTen = (TextView) itemView.findViewById(R.id.txtHoTen);
            chkSoChinh = (CheckBox) itemView.findViewById(R.id.chkSoChinh);
            txtGhiChu = (TextView) itemView.findViewById(R.id.txtGhiChu);
            imageButton = (ImageButton) itemView.findViewById(R.id.imageButton);
        }
    }

    public class MyAsyncTask extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(activity);
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
                    case "Xoa":
                        result = ws.delete_DienThoai(strings[1], strings[2]);
                        break;
                    case "Sua":
                        result = ws.update_DienThoai(strings[1], strings[2], strings[3], strings[4], CLocal.MaNV);
                        break;
                }
                if (!result.equals(""))
                    jsonObject = new JSONObject(result);
                if (jsonObject != null)
                    if (Boolean.parseBoolean(jsonObject.getString("success").replace("null", ""))) {

                    } else
                        error = "THẤT BẠI\r\n" + jsonObject.getString("error").replace("null", "");
            } catch (Exception ex) {
                error = ex.getMessage();
            }
            return error;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            if (!s.equals(""))
                CLocal.showPopupMessage(activity, s, "center");
        }

    }
}
