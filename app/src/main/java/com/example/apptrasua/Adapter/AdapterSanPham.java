package com.example.apptrasua.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptrasua.Comon;
import com.example.apptrasua.Models.SanPham;
import com.example.apptrasua.R;
import com.example.apptrasua.TrangChu.ChiTietSanPham;

import java.util.ArrayList;

public class AdapterSanPham extends RecyclerView.Adapter<AdapterSanPham.SanPhamView> {


    ArrayList<SanPham> arrayList;
    Context context;


    public AdapterSanPham(ArrayList<SanPham> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public SanPhamView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sanpham, parent, false);

        return new SanPhamView(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamView holder, int position) {
        final SanPham sanPham=this.arrayList.get(position);
        holder.tensp.setText(sanPham.getTenSP());
        holder.gia.setText(Comon.formatMoney(sanPham.getDonGia())+" VND");

        Glide.with(context).load(sanPham.getLinkAnh()).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ChiTietSanPham.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("ObJect", sanPham);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(arrayList !=null){
            return arrayList.size();
        }

        return 1;
    }

        public class SanPhamView extends RecyclerView.ViewHolder {

        TextView tensp;
        TextView gia;
        ImageView imageView;
        CardView cardView;

        public SanPhamView(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            tensp = itemView.findViewById(R.id.tenSP);
            gia=itemView.findViewById(R.id.gia);
            cardView=itemView.findViewById(R.id.layout_sanpham);

        }

    }
}
