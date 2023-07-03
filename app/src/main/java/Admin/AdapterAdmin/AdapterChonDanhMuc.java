package Admin.AdapterAdmin;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

import Admin.ThemSanPham;

public class AdapterChonDanhMuc extends RecyclerView.Adapter<AdapterChonDanhMuc.ChonDanhMucView> {

    ArrayList<LoaiSP> arrayList;
    Context context;
    // bundle.putSerializable("ObJect", loaiSP);

    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;

    public AdapterChonDanhMuc(ArrayList<LoaiSP> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public AdapterChonDanhMuc.ChonDanhMucView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chondanhmuc, parent, false);

        return new AdapterChonDanhMuc.ChonDanhMucView(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChonDanhMuc.ChonDanhMucView holder, int position) {
        final LoaiSP loaiSP=this.arrayList.get(position);
        holder.tenloai.setText(loaiSP.getTenLoai());
        Glide.with(context).load(loaiSP.getLinkAnh()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ThemSanPham.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("LoaiSP", loaiSP);
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

    public class ChonDanhMucView extends RecyclerView.ViewHolder {

        TextView tenloai;
        CardView cardView;
        ImageView imageView;
        public ChonDanhMucView(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.layout_loaisp);
            imageView = itemView.findViewById(R.id.img);
            tenloai = itemView.findViewById(R.id.tenloai);
        }
    }
}
