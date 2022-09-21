package com.example.apptrasua;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Start extends AppCompatActivity {

    ImageView imageView;
    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        imageView = findViewById(R.id.anhmodau);

        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setBackgroundResource(R.drawable.anh3);
        Animation animFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hieuungmodau);
        imageView.startAnimation(animFade);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Start();

            }
        }, 4000);
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
                Intent intent = new Intent(Start.this, MainActivity.class);
                startActivity(intent);
                Comon.id=cursorTaiKhoan.getInt(0);
                finish();
            }
            if (countTK == 0) {
                Intent intent = new Intent(Start.this, DangNhap_DangKy.class);
                startActivity(intent);
                finish();
            }
            close();

        }catch (Exception e){
            AlertDialog.Builder al = new AlertDialog.Builder(Start.this);
            al.setTitle("Database Demo");
            al.setMessage("Mã không tồn tại  ");
            al.create().show();

        }
    }

}