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
import com.example.apptrasua.Comon;
import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.Models.SanPham;
import com.example.apptrasua.R;
import com.example.apptrasua.TrangChu.ChiTietSanPham;

import java.util.ArrayList;

import Admin.SuaSanPham;

public class AdapterSanPhamQT extends RecyclerView.Adapter<AdapterSanPhamQT.SanPhamQTView> {

    ArrayList<SanPham> arrayList;
    Context context;
    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;

    public AdapterSanPhamQT(ArrayList<SanPham> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterSanPhamQT.SanPhamQTView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sanphamqt, parent, false);

        return new AdapterSanPhamQT.SanPhamQTView(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSanPhamQT.SanPhamQTView holder, @SuppressLint("RecyclerView") int position) {
        final SanPham sanPham=this.arrayList.get(position);
        holder.tensp.setText(sanPham.getTenSP());
        holder.gia.setText(Comon.formatMoney(sanPham.getDonGia())+" VND");

        Glide.with(context).load(sanPham.getLinkAnh()).into(holder.imageView);

       holder.Sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, SuaSanPham.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("SanPham", sanPham);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder al = new AlertDialog.Builder(context);
                al.setTitle("Xóa danh mục");
                al.setMessage("Bạn muốn xóa danh mục");
                al.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        oppen();
                        try {
                            String sql="delete from SanPham where MaSP = ?";
                            SQLiteStatement sqLiteStatement=database.compileStatement(sql);
                            sqLiteStatement.clearBindings();
                            sqLiteStatement.bindString(1,sanPham.getMaSP());
                            sqLiteStatement.executeUpdateDelete();
                            sqLiteStatement.execute();
                            Toast.makeText(context,"Xoá danh sản phẩm thành công",Toast.LENGTH_SHORT).show();
                            arrayList.remove(position);
                            notifyDataSetChanged();

                            close();
                        }catch (Exception exception){
                            Toast.makeText(context,"Xoá sản phẩm thất bại",Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public int getItemCount() {
        if(arrayList !=null){
            return arrayList.size();
        }
        return 1;
    }

    public class SanPhamQTView extends RecyclerView.ViewHolder {
        TextView tensp;
        TextView gia;
        ImageView imageView;
        CardView cardView;
        Button Sua,xoa;
        public SanPhamQTView(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            tensp = itemView.findViewById(R.id.tenSP);
            gia=itemView.findViewById(R.id.gia);
            cardView=itemView.findViewById(R.id.layout_sanpham);
            Sua = itemView.findViewById(R.id.Sua);
            xoa = itemView.findViewById(R.id.xoa);
            ;
        }
    }
    public void oppen(){
        database = DatabaseHandler.initDatabase((Activity)context, DATABASE_NAME);
    }
    public void close(){
        database.close();
    }
}
