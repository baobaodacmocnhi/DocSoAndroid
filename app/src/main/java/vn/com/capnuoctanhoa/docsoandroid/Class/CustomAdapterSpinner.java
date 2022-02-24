package vn.com.capnuoctanhoa.docsoandroid.Class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import vn.com.capnuoctanhoa.docsoandroid.R;

public class CustomAdapterSpinner extends ArrayAdapter {
    public CustomAdapterSpinner(Context context, ArrayList<CCode> list) {
        super(context, 0, list);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View converView, ViewGroup parent) {
        if (converView == null) {
            converView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner_code, parent, false);
        }
        TextView txtCode=converView.findViewById(R.id.txtCode);
        TextView txtMoTa=converView.findViewById(R.id.txtMoTa);
        CCode item= (CCode) getItem(position);
        if(item!=null) {
            txtCode.setText(item.getCode());
            txtMoTa.setText(item.getMoTa());
        }
        return  converView;
    }

}
