package com.example.apptrasua.GioHang;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apptrasua.Comon;
import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.MainActivity;
import com.example.apptrasua.Models.DonHang;
import com.example.apptrasua.Models.GiaoHang;
import com.example.apptrasua.Models.GioHang;
import com.example.apptrasua.Models.Tooping;
import com.example.apptrasua.R;

public class HoanThanh extends AppCompatActivity {

    TextView quaylai;
    Button gưi;
    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;
    DonHang donHang;
    GiaoHang giaoHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoan_thanh);
        Bundle bundle=getIntent().getExtras();
        donHang=(DonHang) bundle.get("DonHang");
        giaoHang=(GiaoHang) bundle.get("ThongTinGiaoHang");

        quaylai=findViewById(R.id.quaylai);
        gưi=findViewById(R.id.gưi);

        gưi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Guiyeucau(Gravity.CENTER);
            }
        });
        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); 
            }
        });
    }
    public void oppen(){
        database = DatabaseHandler.initDatabase(HoanThanh.this, DATABASE_NAME);
    }
    public void close(){
        database.close();
    }
    public void insertDonHang(){
       oppen();
        try {
            String sql="insert into DonHang(MaDH,HoTen, PTThanhToan,DiaChi,PhiVanChuyen,TienHang,ThanhTien,TrangThai,MaKH,id) values(?,?,?,?,?,?,?,?,?,?)";
            SQLiteStatement sqLiteStatement=database.compileStatement(sql);
            sqLiteStatement.clearBindings();
            sqLiteStatement.bindString(1,donHang.getMaDH());
            sqLiteStatement.bindString(2,donHang.getHoTen());
            sqLiteStatement.bindString(3,donHang.getPTThanhToan());
            sqLiteStatement.bindString(4,donHang.getDiaChi());
            sqLiteStatement.bindDouble(5,donHang.getPhiVanChuyen());
            sqLiteStatement.bindDouble(6,donHang.getTienHang());
            sqLiteStatement.bindDouble(7,donHang.getThanhTien());
            sqLiteStatement.bindString(8,donHang.getTrangThai());
            sqLiteStatement.bindString(9,donHang.getMaKH());
            sqLiteStatement.bindDouble(10,donHang.getId());
            sqLiteStatement.executeInsert();
            InsertChiTietDonHang();
            close();
        }catch (Exception e){
            AlertDialog.Builder al = new AlertDialog.Builder(HoanThanh.this);
            al.setTitle("Database Demo");
            al.setMessage("Dữ liệu lỗi Đơn hàng");
            al.create().show();
        }

    }
    public void InsertChiTietDonHang(){
        int a=0;
        oppen();
        String sql="insert into ChiTietDonHang(MCTHD,MaDH,MaSP,TenSP,DonGia,SoLuongMua,Tong) values(?,?,?,?,?,?,?)";
        SQLiteStatement sqLiteStatement=database.compileStatement(sql);
        for (GioHang e: Comon.gioHangArrayList){
            try {
                sqLiteStatement.clearBindings();
                sqLiteStatement.bindString(1,e.getMaCTHD());
                sqLiteStatement.bindString(2,e.getMaDH());
                sqLiteStatement.bindString(3,e.getMaSP());
                sqLiteStatement.bindString(4,e.getTenSP());
                sqLiteStatement.bindDouble(5,e.getDonGia());
                sqLiteStatement.bindDouble(6,e.getSoLuongMua());
                sqLiteStatement.bindDouble(7,e.getTong());
                sqLiteStatement.executeInsert();
                a++;
            }catch (Exception exception){
                AlertDialog.Builder al = new AlertDialog.Builder(HoanThanh.this);
                al.setTitle("Database Demo");
                al.setMessage("Dữ liệu lỗi chi tiết đơn hàng");
                al.create().show();
            }
        }
        if(a==Comon.gioHangArrayList.size()){
            InsertTooping();
        }
        close();
    }

    public void InsertTooping(){
        int a=0;
        oppen();
        String sql="insert into Topping(MaTP,ChonDuong, Size,ChonDa,ViPhu,MCTHD) values(?,?,?,?,?,?)";
        SQLiteStatement sqLiteStatement=database.compileStatement(sql);
        for (Tooping e: Comon.toopingArrayList){

            try {
                sqLiteStatement.clearBindings();
                sqLiteStatement.bindString(1,e.getMaTP());
                sqLiteStatement.bindString(2,e.getChonDuong());
                sqLiteStatement.bindString(3,e.getSize());
                sqLiteStatement.bindString(4,e.getChonDa());
                sqLiteStatement.bindString(5,e.getViPhu());
                sqLiteStatement.bindString(6,e.getMCTHD());
                sqLiteStatement.executeInsert();
                a++;
            }catch (Exception exception){
                AlertDialog.Builder al = new AlertDialog.Builder(HoanThanh.this);
                al.setTitle("Database Demo");
                al.setMessage("Dữ liệu lỗi Tooping");
                al.create().show();
            }
        }
        if(a==Comon.toopingArrayList.size()){
            ProgressDialog dialog1 = new ProgressDialog(HoanThanh.this);
            dialog1.setTitle("Gửi yêu cầu");
            dialog1.setMessage("Vui lòng chờ");
            dialog1.setCancelable(true);
            dialog1.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog1.dismiss();
                    Intent intent = new Intent(HoanThanh.this, MainActivity.class);
                    startActivity(intent);
                    Comon.gioHangArrayList.clear();
                    Comon.toopingArrayList.clear();
                    Toast.makeText(HoanThanh.this, "Gửi yêu cầu thành công", Toast.LENGTH_LONG).show();
                }
            }, 3000);
        }
        close();
    }
    public void insertThongTinGiaoHang(){
        oppen();
            try {
                String sql="insert into ThongTinGiaoHang(MaKH,HoTen, SoDienThoai, Email, DiaChi, id, DiaChiCuThe) values(?,?,?,?,?,?,?)";
                SQLiteStatement sqLiteStatement=database.compileStatement(sql);
                sqLiteStatement.clearBindings();
                sqLiteStatement.bindString(1,giaoHang.getMaKH());
                sqLiteStatement.bindString(2,giaoHang.getHoTen());
                sqLiteStatement.bindDouble(3,giaoHang.getSoDienThoai());
                sqLiteStatement.bindString(4,giaoHang.getEmail());
                sqLiteStatement.bindString(5,giaoHang.getDiaChi());
                sqLiteStatement.bindDouble(6,giaoHang.getId());
                sqLiteStatement.bindString(7,giaoHang.getDiaChiCuThe());
                sqLiteStatement.executeInsert();
                close();
                insertDonHang();
            }catch (Exception exception){
                AlertDialog.Builder al = new AlertDialog.Builder(HoanThanh.this);
                al.setTitle("Database Demo");
                al.setMessage("Dữ liệu lỗi Thong Tin Giao Hang");
                al.create().show();
            }
        close();
    }
    public void Guiyeucau(int gravity){
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.itemguiyeucau);
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

        Button trolai=dialog.findViewById(R.id.trolai);
        Button Ok=dialog.findViewById(R.id.Ok);

        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                insertThongTinGiaoHang();
            }
        });
        trolai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
