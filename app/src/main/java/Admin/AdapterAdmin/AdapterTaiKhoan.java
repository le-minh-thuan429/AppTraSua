package Admin.AdapterAdmin;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptrasua.Models.Taikhoan;
import com.example.apptrasua.R;

import java.util.ArrayList;

import Admin.DonHangQT;
import Admin.ThongTinUseQT;

public class AdapterTaiKhoan extends RecyclerView.Adapter<AdapterTaiKhoan.TaiKhoanView> {

    ArrayList<Taikhoan> arrayList;
    Context context;

    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;

    public AdapterTaiKhoan(ArrayList<Taikhoan> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public AdapterTaiKhoan.TaiKhoanView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_taikhoan, parent, false);
        return new AdapterTaiKhoan.TaiKhoanView(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTaiKhoan.TaiKhoanView holder, int position) {
        final Taikhoan taikhoan=this.arrayList.get(position);
        holder.taikhoan.setText(taikhoan.getTenTK());
        holder.email.setText(taikhoan.getEmail());

        holder.donhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DonHangQT.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("Taikhoan",taikhoan);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
        holder.thongtinkhach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ThongTinUseQT.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("Taikhoan",taikhoan);
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

    public class TaiKhoanView extends RecyclerView.ViewHolder {

        TextView taikhoan,email;
        Button donhang,thongtinkhach;
        public TaiKhoanView(@NonNull View itemView) {
            super(itemView);
            taikhoan = itemView.findViewById(R.id.taikhoan);
            email = itemView.findViewById(R.id.email);
            donhang = itemView.findViewById(R.id.donhang);
            thongtinkhach = itemView.findViewById(R.id.thongtinkhach);

        }
    }
}
