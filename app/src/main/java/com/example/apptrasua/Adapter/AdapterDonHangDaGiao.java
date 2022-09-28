package com.example.apptrasua.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptrasua.Comon;
import com.example.apptrasua.Models.DonHang;
import com.example.apptrasua.R;

import java.util.ArrayList;

public class AdapterDonHangDaHuy extends RecyclerView.Adapter<AdapterDonHangDaHuy.HuyDonHangView> {

    ArrayList<DonHang> arrayList;
    Context context;

    public AdapterDonHangDaHuy(ArrayList<DonHang> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }
    
    @NonNull
    @Override
    public HuyDonHangView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_don_hang_da_huy, parent, false);

        return new HuyDonHangView(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull HuyDonHangView holder, int position) {

        final DonHang donHang=this.arrayList.get(position);
        holder.MaHD.setText(donHang.getMaDH());
        holder.HoTen.setText(donHang.getHoTen());
        holder.Tong.setText(Comon.formatMoney(donHang.getThanhTien())+" VND");

        holder.ChiTietDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChiTietDonHang(Gravity.CENTER,donHang);
            }
        });

    }

    public void ChiTietDonHang(int gravity,DonHang donHang){
        final Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.itemhoadon);
        Window window=dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes=window.getAttributes();
        windowAttributes.gravity=gravity;
        window.setAttributes(windowAttributes);
        if(Gravity.CENTER==gravity){
            dialog.setCancelable(false);
        }
        TextView MAHD,HoTen,PTTT,Diachi,TienHang;
        TextView Ok;

        MAHD=dialog.findViewById(R.id.MaHD);
        Ok=dialog.findViewById(R.id.Ok);
        HoTen=dialog.findViewById(R.id.HoTen);
        PTTT=dialog.findViewById(R.id.PTTT);
        Diachi=dialog.findViewById(R.id.Diachi);
        TienHang=dialog.findViewById(R.id.TienHang);

        MAHD.setText(donHang.getMaDH());
        HoTen.setText(donHang.getHoTen());
        PTTT.setText(donHang.getPTThanhToan());
        Diachi.setText(donHang.getDiaChi());
        TienHang.setText(Comon.formatMoney(donHang.getTienHang())+" VND");

        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    @Override
    public int getItemCount() {
        if(arrayList !=null){
            return arrayList.size();
        }
        return 1;
    }

    public class HuyDonHangView extends RecyclerView.ViewHolder {

        TextView  MaHD, HoTen, Tong, ChiTietDonHang;

        CardView layout_donhang;

        public HuyDonHangView(@NonNull View itemView) {
            super(itemView);
            MaHD=itemView.findViewById(R.id.MaHD);
            HoTen = itemView.findViewById(R.id.HoTen);
            Tong = itemView.findViewById(R.id.Tong);
            ChiTietDonHang = itemView.findViewById(R.id.ChiTietDonHang);
            layout_donhang = itemView.findViewById(R.id.layout_donhang);
        }

    }
}
