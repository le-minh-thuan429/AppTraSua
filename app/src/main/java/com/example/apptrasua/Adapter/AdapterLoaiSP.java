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
import com.example.apptrasua.Models.LoaiSP;
import com.example.apptrasua.R;
import com.example.apptrasua.TrangChu.SanPhamTheoLoai;

import java.util.ArrayList;

public class AdapterLoaiSP extends RecyclerView.Adapter<AdapterLoaiSP.LoaiSPView> {

    ArrayList<LoaiSP> arrayList;
    Context context;

    public AdapterLoaiSP(ArrayList<LoaiSP> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }
    
    @NonNull
    @Override
    public LoaiSPView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_loaisp, parent, false);

        return new LoaiSPView(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSPView holder, int position) {

        final LoaiSP loaiSP=this.arrayList.get(position);
        holder.tenloai.setText(loaiSP.getTenLoai());
        Glide.with(context).load(loaiSP.getLinkAnh()).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, SanPhamTheoLoai.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("ObJect", loaiSP);
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

    public class LoaiSPView extends RecyclerView.ViewHolder {

        TextView tenloai;
        CardView cardView;
        ImageView imageView;

        public LoaiSPView(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.layout_loaisp);
            imageView = itemView.findViewById(R.id.img);
            tenloai = itemView.findViewById(R.id.tenloai);
        }

    }
}
