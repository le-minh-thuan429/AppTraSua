package com.example.apptrasua;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import Admin.ThemDanhM;

public class PhanHeNguoiDung extends AppCompatActivity {

    final String DATABASE_NAME = "AppTraSua.db";
    ImageView anhmodau;
    SQLiteDatabase database;
    Button NguoiDung,QuanTri;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phan_he_nguoi_dung);
        NguoiDung=findViewById(R.id.NguoiDung);
        QuanTri=findViewById(R.id.QuanTri);
        anhmodau=findViewById(R.id.anhmodau);

        Animation animFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.lac);
        anhmodau.startAnimation(animFade);

        NguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Start();
                finish();
            }
        });

        QuanTri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Admin.DangNhap.class));
                finish();


            }
        });
    }
    public void oppen(){
        database = DatabaseHandler.initDatabase(this, DATABASE_NAME);
    }
    public void close(){
        database.close();
    }
    public void Start(){
        String TrangThai = "ON";
        Cursor cursorTaiKhoan;
        try {
            oppen();
            cursorTaiKhoan = database.rawQuery("select * from TaiKhoan where TrangThai = '" + TrangThai + "'" , null);
            cursorTaiKhoan.moveToFirst();
            int countTK = cursorTaiKhoan.getCount();
            if (countTK == 1) {
                Intent intent = new Intent(PhanHeNguoiDung.this, MainActivity.class);
                startActivity(intent);
                Comon.id=cursorTaiKhoan.getInt(0);
                finish();
            }
            if (countTK == 0) {
                Intent intent = new Intent(PhanHeNguoiDung.this, DangNhap_DangKy.class);
                startActivity(intent);
                finish();
            }
            close();

        }catch (Exception e){
            AlertDialog.Builder al = new AlertDialog.Builder(PhanHeNguoiDung.this);
            al.setTitle("Database Demo");
            al.setMessage("Mã không tồn tại  ");
            al.create().show();

        }
    }
}