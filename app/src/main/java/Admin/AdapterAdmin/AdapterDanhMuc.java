package Admin.AdapterAdmin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptrasua.Adapter.AdapterLoaiSP;
import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.Models.LoaiSP;
import com.example.apptrasua.R;
import com.example.apptrasua.TrangChu.SanPhamTheoLoai;

import java.util.ArrayList;

import Admin.SuaDanhMuc;
import Admin.ThemDanhM;

public class AdapterDanhMuc extends RecyclerView.Adapter<AdapterDanhMuc.DanhMucView> {

    ArrayList<LoaiSP> arrayList;
    Context context;
    // bundle.putSerializable("ObJect", loaiSP);

    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;

    public AdapterDanhMuc(ArrayList<LoaiSP> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterDanhMuc.DanhMucView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_danhmuc, parent, false);

        return new AdapterDanhMuc.DanhMucView(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDanhMuc.DanhMucView holder, @SuppressLint("RecyclerView") int position) {

        final LoaiSP loaiSP=this.arrayList.get(position);
        holder.tenloai.setText(loaiSP.getTenLoai());
        Glide.with(context).load(loaiSP.getLinkAnh()).into(holder.imageView);

        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent=new Intent(context, SanPhamTheoLoai.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("LoaiSP", loaiSP);
                intent.putExtras(bundle);
                context.startActivity(intent);*/
                AlertDialog.Builder al = new AlertDialog.Builder(context);
                al.setTitle("Xóa danh mục");
                al.setMessage("Bạn muốn xóa danh mục");
                al.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        oppen();
                        try {
                            String sql="delete from LoaiSP where MaLoai = ?";
                            SQLiteStatement sqLiteStatement=database.compileStatement(sql);
                            sqLiteStatement.clearBindings();
                            sqLiteStatement.bindString(1,loaiSP.getMaLoai());
                            sqLiteStatement.executeUpdateDelete();
                            sqLiteStatement.execute();
                            Toast.makeText(context,"Xoá danh mục thành công",Toast.LENGTH_SHORT).show();
                            arrayList.remove(position);
                            notifyDataSetChanged();

                            close();
                        }catch (Exception exception){
                            Toast.makeText(context,"Xoá danh mục thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                al.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                al.create().show();

            }
        });

        holder.Sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, SuaDanhMuc.class);
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
    public class DanhMucView extends RecyclerView.ViewHolder {

        TextView tenloai;
        CardView cardView;
        ImageView imageView;
        Button Sua,xoa;

        public DanhMucView(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.layout_loaisp);
            imageView = itemView.findViewById(R.id.img);
            tenloai = itemView.findViewById(R.id.tenloai);
            Sua = itemView.findViewById(R.id.Sua);
            xoa = itemView.findViewById(R.id.xoa);
        }

    }
    public void oppen(){
        database = DatabaseHandler.initDatabase((Activity)context, DATABASE_NAME);
    }
    public void close(){
        database.close();
    }
}
