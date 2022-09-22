package com.example.apptrasua.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptrasua.Comon;
import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.Models.LoaiSP;
import com.example.apptrasua.Models.SanPham;
import com.example.apptrasua.R;
import com.example.apptrasua.TrangChu.SanPhamTimKiem;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterTrangChu extends RecyclerView.Adapter<AdapterTrangChu.TrangChuView>  {

    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;
    Context context;
    public AdapterTrangChu(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public TrangChuView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_trang_chu, parent, false);

        return new TrangChuView(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTrangChu.TrangChuView holder, int position) {

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        holder.listLSP.setLayoutManager(linearLayoutManager);
        AdapterLoaiSP adapterLoaiSP=new AdapterLoaiSP(getListLoaiSP(),context);
        holder.listLSP.setAdapter(adapterLoaiSP);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(context,2);
        holder.listSP.setLayoutManager(gridLayoutManager);
        AdapterSanPham useAdapter=new AdapterSanPham(getListSP(),context);
        holder.listSP.setAdapter(useAdapter);

        AdapterTimKiemTrangChu countryadapter=new AdapterTimKiemTrangChu(context, R.layout.item_timkiem,getSPTimKiem());
        holder.timkiem.setAdapter(countryadapter);


        holder.gotimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a= holder.timkiem.getText().toString().toLowerCase().trim();
                if(Comon.DinhDangTimkiem(a)){
                    Intent intent1 = new Intent(context, SanPhamTimKiem.class);
                    intent1.putExtra("abc", a);
                    context.startActivity(intent1);
                }
            }
        });
        ViewAnh(holder);

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public void oppen(){
        database = DatabaseHandler.initDatabase((Activity) context, DATABASE_NAME);
    }
    public void close(){
        database.close();
    }

    private ArrayList<LoaiSP> getListLoaiSP(){

        ArrayList<LoaiSP> List=new ArrayList<>();
        Cursor cursor;
        try {
            oppen();
            cursor = database.rawQuery("select * from LoaiSP ", null);
            cursor.moveToFirst();
            do{
                LoaiSP loaiSP=new LoaiSP(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                List.add(loaiSP);

            }while (cursor.moveToNext());

            close();
        }catch (Exception e){
            AlertDialog.Builder al = new AlertDialog.Builder(context);
            al.setTitle("Database Demo");
            al.setMessage("Dữ liệu lỗi");
            al.create().show();
        }

        return List;
    }

    private ArrayList<SanPham> getListSP(){

        ArrayList<SanPham> List=new ArrayList<>();
        Cursor cursor;
        String hot="Hot";
        try {
            oppen();
            cursor = database.rawQuery("select * from SanPham where TinhTrang ='"+hot+"'", null);
            cursor.moveToFirst();
            do{
                SanPham sanPham=new SanPham(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
                List.add(sanPham);

            }while (cursor.moveToNext());

            close();
        }catch (Exception e){
            AlertDialog.Builder al = new AlertDialog.Builder(context);
            al.setTitle("Database Demo");
            al.setMessage("Dữ liệu lỗi");
            al.create().show();
        }

        return List;
    }
    private ArrayList<SanPham> getSPTimKiem(){

        ArrayList<SanPham> List=new ArrayList<>();
        Cursor cursor;
        try {
            oppen();
            cursor = database.rawQuery("select * from SanPham ", null);
            cursor.moveToFirst();
            do{
                SanPham sanPham=new SanPham(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
                List.add(sanPham);

            }while (cursor.moveToNext());

            close();
        }catch (Exception e){
            AlertDialog.Builder al = new AlertDialog.Builder(context);
            al.setTitle("Database Demo");
            al.setMessage("Dữ liệu lỗi");
            al.create().show();
        }

        return List;
    }

    public void ViewAnh(TrangChuView holder){
        Cursor cursor;
        try {
            oppen();
            cursor = database.rawQuery("select * from NguoiDung where id = " + Comon.id , null);
            cursor.moveToFirst();
            int count = cursor.getCount();
            if (count == 1) {
                byte[] hinhanh=cursor.getBlob(6);
                Bitmap bitmap= BitmapFactory.decodeByteArray(hinhanh,0,hinhanh.length);
                holder.profile_image.setImageBitmap(bitmap);
            }
            close();
        }catch (Exception e){
            AlertDialog.Builder al = new AlertDialog.Builder(context);
            al.setTitle("Database Demo");
            al.setMessage("Mã không tồn tại");
            al.create().show();
        }
    }

    public static class TrangChuView extends RecyclerView.ViewHolder {

        public RecyclerView listSP,listLSP;

        public AutoCompleteTextView timkiem;

        public static FrameLayout layout_nen;
        public TextView gotimkiem;
        public CircleImageView profile_image;
        public TrangChuView(@NonNull View itemView) {
            super(itemView);
            listLSP=itemView.findViewById(R.id.listLSP);
            listSP=itemView.findViewById(R.id.listSP);
            timkiem=itemView.findViewById(R.id.timkiem);
            layout_nen =itemView.findViewById(R.id.layout_nen);
            gotimkiem =itemView.findViewById(R.id.gotimkiem);
            profile_image =itemView.findViewById(R.id.profile_image);
        }
    }
}
