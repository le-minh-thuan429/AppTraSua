package com.example.apptrasua.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptrasua.Comon;
import com.example.apptrasua.Models.GioHang;
import com.example.apptrasua.R;

import java.util.ArrayList;

public class AdapterXacNhanGioHang extends RecyclerView.Adapter<AdapterXacNhanGioHang.XacNhanGioHangView> {

    ArrayList<GioHang> arrayList;
    Context context;
    // bundle.putSerializable("ObJect", loaiSP);

    public AdapterXacNhanGioHang(ArrayList<GioHang> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }
    
    @NonNull
    @Override
    public XacNhanGioHangView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_xac_nhan_gio_hang, parent, false);

        return new XacNhanGioHangView(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull XacNhanGioHangView holder, int position) {

        final GioHang gioHang=this.arrayList.get(position);
        holder.tenSP.setText(gioHang.getTenSP());
        Glide.with(context).load(gioHang.getLinkAnh()).into(holder.img);
        holder.gia.setText(Comon.formatMoney(gioHang.getDonGia())+" VND");
        holder.soluong.setText(gioHang.getSoLuongMua()+"");
        holder.Tong.setText(Comon.formatMoney(gioHang.getTong())+" VND");
    }

    @Override
    public int getItemCount() {
        if(arrayList !=null){
            return arrayList.size();
        }
        return 1;
    }

    public class XacNhanGioHangView extends RecyclerView.ViewHolder {

        TextView tenSP, gia,soluong,Tong;
        ImageView img;

        public XacNhanGioHangView(@NonNull View itemView) {
            super(itemView);
            gia=itemView.findViewById(R.id.gia);
            soluong=itemView.findViewById(R.id.soluong);
            img = itemView.findViewById(R.id.img);
            tenSP = itemView.findViewById(R.id.tenSP);
            Tong = itemView.findViewById(R.id.Tong);
        }

    }
}
