package vn.com.capnuoctanhoa.docsoandroid.Class;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import vn.com.capnuoctanhoa.docsoandroid.DocSo.ActivityDocSo_GhiChiSo;
import vn.com.capnuoctanhoa.docsoandroid.R;

public class CustomAdapterRecyclerViewDienThoai extends RecyclerView.Adapter<CustomAdapterRecyclerViewDienThoai.RecyclerViewHolder> {
    private Activity activity;
    private ArrayList<CEntityParent> mDisplayedValues;

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
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog("Xác nhận", "", "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            mDisplayedValues.remove(ID);
                            if (DanhBo.length() == 11) {
                                MyAsyncTask myAsyncTask = new MyAsyncTask();
                                myAsyncTask.execute(new String[]{entityParent.getDanhBo(), entityParent.getDienThoai()});
                            } else {
                                CLocal.listDownDocSo.remove(ID);
                                SharedPreferences.Editor editor = CLocal.sharedPreferencesre.edit();
                                editor.putString("jsonDownDocSo", new Gson().toJsonTree(CLocal.listDownDocSo).getAsJsonArray().toString());
                                editor.commit();
                            }
                            notifyDataSetChanged();
                        }
                    }, "Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    }, false);

                }
            });
            if (DanhBo.length() != 11)
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = CLocal.sharedPreferencesre.edit();
                        editor.putString("jsonDocSo", CLocal.listDownDocSo.get(ID).getDiaChi());
                        editor.commit();
                        CLocal.listDocSo = new Gson().fromJson(CLocal.sharedPreferencesre.getString("jsonDocSo", ""), new TypeToken<ArrayList<CEntityParent>>() {
                        }.getType());
                        if (CLocal.listDocSo.size() > 2000)
                            CLocal.listDocSo = null;
                        CLocal.showToastMessage(activity, "Đã load dữ liệu " + CLocal.listDownDocSo.get(ID).getHoTen());
                        Intent returnIntent = new Intent();
                        activity.setResult(Activity.RESULT_OK, returnIntent);
                        activity.finish();
                    }
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

    public AlertDialog showDialog(String title, String msg, String positiveLabel, DialogInterface.OnClickListener positiveOnClick, String negativeLabel, DialogInterface.OnClickListener negativeOnClick, boolean isCancelAble) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setCancelable(isCancelAble);
        builder.setMessage(msg);
        builder.setPositiveButton(positiveLabel, positiveOnClick);
        builder.setNegativeButton(negativeLabel, negativeOnClick);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }

    public class MyAsyncTask extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        JSONObject jsonObject = null;

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
            try {
                CWebservice ws = new CWebservice();
                String result = ws.delete_DienThoai(strings[0], strings[1]);

                jsonObject = new JSONObject(result);

                if (jsonObject != null && Boolean.parseBoolean(jsonObject.getString("success").replace("null", "")) == true) {
                    return "THÀNH CÔNG";
                } else
                    return "THẤT BẠI";
            } catch (Exception ex) {
                return "THẤT BẠI";
            }
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
            try {
                CLocal.showPopupMessage(activity, s + "\r\n" + jsonObject.getString("error").replace("null", ""), "center");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
