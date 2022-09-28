package com.example.apptrasua.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.apptrasua.Models.DiaChi;
import com.example.apptrasua.R;

import java.util.List;

public class AdapterDiaChi extends ArrayAdapter<DiaChi> {

//thuannnnnn
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diachi,parent,false);
        TextView diachi=convertView.findViewById(R.id.diachi);
        TextView diachicuthe=convertView.findViewById(R.id.diachicuthe);
        DiaChi DiaChi=this.getItem(position);
        if(DiaChi!=null){
            diachi.setText(DiaChi.getDiaChi());
            diachicuthe.setText(DiaChi.getDiaChiCuThe());
        }
        return convertView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected,parent,false);
        }
        TextView tvselect=convertView.findViewById(R.id.select);
        DiaChi DiaChi=this.getItem(position);
        if(DiaChi!=null){
            tvselect.setText(DiaChi.getDiaChi()+"\n"+DiaChi.getDiaChiCuThe());

        }
        return convertView;
    }

    public AdapterDiaChi(@NonNull Context context, int resource, @NonNull List<DiaChi> objects) {
        super(context, resource, objects);

    }

}
