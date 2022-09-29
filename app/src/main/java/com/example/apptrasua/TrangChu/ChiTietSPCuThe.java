package com.example.apptrasua.TrangChu;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.apptrasua.Comon;
import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.Models.SanPham;
import com.example.apptrasua.R;

public class ChiTietSPCuThe extends AppCompatActivity {

    TextView gia, tensanpham, mota, huongvi, nguyenlieu,  dinhduong, quaylai;
    ImageView imgchitiet;
    SanPham sanPham;
    Button Ok;
    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_s_p_cu_the);
        tensanpham=findViewById(R.id.tensanpham);
        gia=findViewById(R.id.gia);
        quaylai=findViewById(R.id.quaylai);
        Ok=findViewById(R.id.Ok);

        mota=findViewById(R.id.mota);
        huongvi=findViewById(R.id.huongvi);
        nguyenlieu=findViewById(R.id.nguyenlieu);
        imgchitiet=findViewById(R.id.imgchitiet);
        dinhduong=findViewById(R.id.dinhduong);

        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Bundle bundle=getIntent().getExtras();
        sanPham=(SanPham)bundle.get("SanPham");

        tensanpham.setText(sanPham.getTenSP());
        gia.setText(Comon.formatMoney(sanPham.getDonGia())+" VND");
        Glide.with(ChiTietSPCuThe.this).load(sanPham.getLinkAnh()).into(imgchitiet);
        ViewChiTietSP(sanPham.getMaSP());
    }
    public void oppen(){
        database = DatabaseHandler.initDatabase(ChiTietSPCuThe.this, DATABASE_NAME);
    }
    public void close(){
        database.close();
    }
    public void ViewChiTietSP(String maSP){

        Cursor cursor;
        try {
            oppen();
            cursor = database.rawQuery("select * from ChiTietSanPham where MaSP = '" + maSP+"'" , null);
            cursor.moveToFirst();
            int count = cursor.getCount();
            if (count == 1) {
                mota.setText(cursor.getString(1));
                huongvi.setText(cursor.getString(2));
                nguyenlieu.setText(cursor.getString(3));
                dinhduong.setText(cursor.getString(4));
            }
            else {
                AlertDialog.Builder al = new AlertDialog.Builder(ChiTietSPCuThe.this);
                al.setTitle("Database Demo");
                al.setMessage("Không tìm thấy có dữ liệu");
                al.create().show();
            }
            close();
        }catch (Exception e){
            AlertDialog.Builder al = new AlertDialog.Builder(ChiTietSPCuThe.this);
            al.setTitle("Database Demo");
            al.setMessage("Mã không tồn tại");
            al.create().show();
        }
    }
}